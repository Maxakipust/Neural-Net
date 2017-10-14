import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;

public class Network implements Serializable{
    public double LearnRate;
    public double Momentum;
    public LinkedList<Neuron> InputLayer;
    public LinkedList<LinkedList<Neuron>> HiddenLayers;
    public LinkedList<Neuron> OutputLayer;
    private static final Random R = new Random();

    public Network(){
        LearnRate = 0;
        Momentum = 0;
        InputLayer = new LinkedList<>();
        HiddenLayers = new LinkedList<>();
        OutputLayer = new LinkedList<>();
    }

    public Network(int inputSize, int[] hiddenSizes, int outputSize, Double learnRate, Double momentum){
        if(learnRate == null) {
            LearnRate = 0.4;
        }
        if(momentum == null){
            Momentum = 0.9;
        }
        InputLayer = new LinkedList<>();
        HiddenLayers = new LinkedList<>();
        OutputLayer = new LinkedList<>();

        for(int i = 0; i<inputSize;i++){
            InputLayer.add(new Neuron());
        }
        LinkedList<Neuron> firstHiddenLayor = new LinkedList<>();
        for (int i = 0; i<hiddenSizes[0];i++) {
            firstHiddenLayor.add(new Neuron(InputLayer));
        }

        HiddenLayers.add(firstHiddenLayor);

        for(int i = 1;i<hiddenSizes.length;i++){
            LinkedList<Neuron> hiddenLayer = new LinkedList<>();
            for(int j = 0; j< hiddenSizes[i];j++){
                hiddenLayer.add(new Neuron(HiddenLayers.get(i-1)));
            }
            HiddenLayers.add(hiddenLayer);
        }

        for(int i = 0; i< outputSize;i++){
            OutputLayer.add(new Neuron(HiddenLayers.getLast()));
        }
    }

    public void Train(LinkedList<DataSet> dataSets, int numEpocs){
        for(int i =0;i<numEpocs;i++){
            for(DataSet dataSet:dataSets){
                ForwardPropagate(dataSet.Values);
                BackPropagate(dataSet.Targets);
            }
        }
    }

    public void Train(LinkedList<DataSet> dataSets, double minimumError){
        double error = 1.0;
        int numEpochs = 0;

        while(error>minimumError && numEpochs<Integer.MAX_VALUE){
            LinkedList<Double> errors = new LinkedList<>();
            for(DataSet dataSet: dataSets){
                ForwardPropagate(dataSet.Values);
                BackPropagate(dataSet.Targets);
                errors.add(CalculateError(dataSet.Targets));
            }
            error = Average(errors);
            numEpochs+=1;
        }
        System.out.println("Epochs: "+numEpochs);
    }

    private void ForwardPropagate(double[] inputs){
        int i = 0;
        for(Neuron a:InputLayer){
            a.Value = inputs[i++];
        }
        HiddenLayers.forEach((a)->a.forEach((b)->b.CalculateValue()));
        OutputLayer.forEach((a)->a.CalculateValue());
    }

    private void BackPropagate(double[] targets){
        int i = 0;
        for(Neuron a:OutputLayer){
            a.CalculateGradient(targets[i++]);
        }
        HiddenLayers = Reverse(HiddenLayers);
        HiddenLayers.forEach((a)->a.forEach((b)->b.CalculateGradient(null)));
        HiddenLayers.forEach((a)->a.forEach((b)->b.UpdateWeights(LearnRate,Momentum)));
        HiddenLayers = Reverse(HiddenLayers);
        OutputLayer.forEach((a)->a.UpdateWeights(LearnRate,Momentum));
    }

    public double[] Compute(double[] inputs){
        ForwardPropagate(inputs);
        double[] ans = new double[OutputLayer.size()];
        for(int i = 0; i<OutputLayer.size();i++){
            ans[i]=OutputLayer.get(i).Value;
        }
        return ans;
    }

    private double CalculateError(double[] targets){
        int i = 0;
        double ans = 0;
        for(Neuron a: OutputLayer){
            ans+= Math.abs(a.CalculateError(targets[i++]));
        }
        return ans;
    }

    public static double GetRandom(){
        return 2* R.nextDouble()-1;
    }

    private LinkedList<LinkedList<Neuron>> Reverse(LinkedList<LinkedList<Neuron>> list){
        LinkedList<LinkedList<Neuron>> ans = new LinkedList<>();
        for(int i = 1; i<=list.size();i++){
            ans.add(list.get(list.size()-i));
        }
        return ans;
    }

    private Double Average(LinkedList<Double> list){
        double ans = 0.0;
        for(Double a:list){
            ans+=a;
        }
        return ans /list.size();
    }

    @Override
    public String toString() {
        String ans = "";
        ans+= "Learn Rate: "+ LearnRate+"\n";
        ans+= "Momentum: "+Momentum+"\n";
        ans+= "Input Layer: \n";
        for(int i = 0; i<InputLayer.size();i++){
            ans+= "Neuron "+i+"\n";
            ans+= InputLayer.get(i).toString();
        }
        ans+="Hidden Layers\n";
        for(int i = 0; i<HiddenLayers.size();i++){
            ans+= "Hidden Layer "+i+"\n";
            for(int x = 0;x<HiddenLayers.get(i).size();x++) {
                ans += "Neuron " + x+"\n";
                ans += HiddenLayers.get(i).get(x).toString();
            }
        }
        ans+="Output Layer\n";
        for(int i = 0; i<OutputLayer.size();i++){
            ans+= "Neuron "+i+"\n";
            ans+= OutputLayer.get(i).toString();
        }
        ans+= "Synapses: \n";
        LinkedList<Synapse> synapses = new LinkedList<>();
        for(Neuron n:InputLayer){
            synapses.addAll(n.InputSynapses);
            synapses.addAll(n.OutputSynapses);
        }
        for(LinkedList<Neuron> ll:HiddenLayers) {
            for (Neuron n : ll) {
                synapses.addAll(n.InputSynapses);
                synapses.addAll(n.OutputSynapses);
            }
        }
        for(Neuron n:OutputLayer){
            synapses.addAll(n.InputSynapses);
            synapses.addAll(n.OutputSynapses);
        }
        for(Synapse s:synapses){
            ans+="Synapse\n";
            ans+=s.toString();
        }
        return ans;
    }
}