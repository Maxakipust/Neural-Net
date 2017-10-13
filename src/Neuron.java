import java.io.Serializable;
import java.util.*;

public class Neuron implements Serializable {
    private UUID Id;
    private double Bias;
    private double BiasDelta;
    private double Gradient;
    public double Value;
    private final LinkedList<Synapse> InputSynapses;
    private final LinkedList<Synapse> OutputSynapses;


    public Neuron(){
        Id = UUID.randomUUID();
        InputSynapses = new LinkedList<>();
        OutputSynapses = new LinkedList<>();
        Bias = Network.GetRandom();
    }

    public Neuron(LinkedList<Neuron> inputNeurons){
        InputSynapses = new LinkedList<>();
        OutputSynapses = new LinkedList<>();
        for(Neuron n : inputNeurons){
            Synapse s = new Synapse(n,this);
            n.OutputSynapses.add(s);
            InputSynapses.add(s);
        }
    }

    public double CalculateValue(){
        double ret = 0;
        for(Synapse a: InputSynapses){
            ret+=a.Weight*a.InputNeuron.Value;
        }
        ret = Sigmoid.Output(ret+Bias);
        return Value=ret;
    }

    public double CalculateError(double target){
        return target-Value;
    }

    public double CalculateGradient(Double target){
        if(target==null){
            double ret = 0;
            for(Synapse a: OutputSynapses){
                ret+=a.OutputNeuron.Gradient*a.Weight;
            }
            ret*=Sigmoid.Derivative(Value);
            return Gradient = ret;
        }
        return Gradient = CalculateError(target) * Sigmoid.Derivative(Value);
    }

    public void UpdateWeights(double learnRate, double momentum){
        double prevDelta = BiasDelta;
        BiasDelta = learnRate * Gradient;
        Bias += BiasDelta +momentum * prevDelta;

        for(Synapse s:InputSynapses){
            prevDelta = s.WeightDelta;
            s.WeightDelta = learnRate*Gradient*s.InputNeuron.Value;
            s.Weight += s.WeightDelta+momentum*prevDelta;
        }
    }


    @Override
    public boolean equals(Object obj) {
        return obj instanceof Neuron && ((Neuron) obj).Id == this.Id;
    }
}
