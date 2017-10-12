

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;

public class Network implements Serializable{
    public double LearnRate;
    public double Momentum;
    public LinkedList<Neuron> InputLayer;
    public LinkedList<LinkedList<Neuron>> HiddenLayers;
    public LinkedList<Neuron> OutputLayer;
    private static Random R = new Random();

    public Network(){
        LearnRate = 0;
        Momentum = 0;
        InputLayer = new LinkedList<Neuron>();
        HiddenLayers = new LinkedList<LinkedList<Neuron>>();
        OutputLayer = new LinkedList<Neuron>();
    }

    public Network(int inputSize, int[] hiddenSizes, int outputSize, Double learnRate, Double momentum){
        if(learnRate == null) {
            LearnRate = 0.4;
        }
        if(momentum == null){
            Momentum = 0.9;
        }
        InputLayer = new LinkedList<Neuron>();
        HiddenLayers = new LinkedList<LinkedList<Neuron>>();
        OutputLayer = new LinkedList<Neuron>();

        for(int i = 0; i<inputSize;i++){
            InputLayer.add(new Neuron());
        }
        LinkedList<Neuron> firstHiddenLayor = new LinkedList<Neuron>();
        for(int i = 0; i<hiddenSizes.length;i++){
            firstHiddenLayor.add(new Neuron(InputLayer));
        }

        HiddenLayers.add(firstHiddenLayor);

        for(int i = 1;i<hiddenSizes.length;i++){
            LinkedList<Neuron> hiddenLayer = new LinkedList<Neuron>();
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
            LinkedList<Double> errors = new LinkedList<Double>();
            for(DataSet dataSet: dataSets){
                ForwardPropagate(dataSet.Values);
                BackPropagate(dataSet.Targets);
                errors.add(CalculateError(dataSet.Targets));
            }
            error = Average(errors);
            numEpochs+=1;
        }
    }

    public void ForwardPropagate(double[] inputs){
        int i = 0;
        for(Neuron a:InputLayer){
            a.Value = inputs[i++];
        }
        for(LinkedList<Neuron> a:HiddenLayers){
            for(Neuron b:a){
                b.CalculateValue();
            }
        }
        for(Neuron a:OutputLayer){
            a.CalculateValue();
        }
    }

    public void BackPropagate(double[] targets){
        int i = 0;
        for(Neuron a:OutputLayer){
            a.CalculateGredient(targets[i++]);
        }
        Reverse(HiddenLayers);
        for(LinkedList<Neuron> a:HiddenLayers){
            for(Neuron b: a){
                b.CalculateGredient(null);
            }
        }
        for(LinkedList<Neuron> a:HiddenLayers){
            for(Neuron b: a){
                b.UpdateWeights(LearnRate,Momentum);
            }
        }
        Reverse(HiddenLayers);
        for(Neuron a:OutputLayer){
            a.UpdateWeights(LearnRate,Momentum);
        }
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

    public LinkedList<LinkedList<Neuron>> Reverse(LinkedList<LinkedList<Neuron>> list){
        LinkedList<LinkedList<Neuron>> ans = new LinkedList<LinkedList<Neuron>>();
        for(int i = 1; i<=list.size();i++){
            ans.add(list.get(list.size()-i));
        }
        return ans;
    }

    public Double Average(LinkedList<Double> list){
        double ans = 0;
        for(Double a:list){
            ans+=a;
        }
        return ans /list.size();
    }

    @Override
    public String toString() {
        String ans = "";
        ans += "Learn Rate: " + LearnRate+"\n";
        ans += "Momentum: " + Momentum+"\n";
        ans += "Input Layer: "+"\n";
        for(int i =0; i<InputLayer.size();i++){
            String[] t = InputLayer.get(i).toString().split("\n");
            for(String s:t) {
                ans += "\t"+s+"\n";
            }
        }
        ans += "Hidden Layers: \n";
        for(int i =0; i<HiddenLayers.size();i++){
            ans += "Hidden Layer "+i+": \n";
            for(int j = 0; j<HiddenLayers.get(i).size();j++) {
                String[] t = HiddenLayers.get(i).get(j).toString().split("\n");
                for(String s:t) {
                    ans += "\t\t"+s+"\n";
                }
            }
        }
        ans += "Output Layers: ";
        for(int i =0;i<OutputLayer.size();i++){
            String[] t = OutputLayer.get(i).toString().split("\n");
            for(String s:t) {
                ans += "\t"+s+"\n";
            }
        }

        ans += "Synapses: \n";
        LinkedList<Synapse> synapses = new LinkedList<Synapse>();
        for(Neuron a:InputLayer){
            synapses.addAll(a.InputSynapses);
            synapses.addAll(a.OutputSynapses);
        }
        for(LinkedList<Neuron> a:HiddenLayers){
            for(Neuron b:a) {
                synapses.addAll(b.InputSynapses);
                synapses.addAll(b.OutputSynapses);
            }
        }
        for(Neuron a:OutputLayer){
            synapses.addAll(a.InputSynapses);
            synapses.addAll(a.OutputSynapses);
        }
        for(Synapse s:synapses){

        }
        return ans;
    }
}
