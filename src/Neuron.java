import sun.awt.im.InputMethodJFrame;

import java.io.Serializable;
import java.util.*;

public class Neuron implements Serializable {
    public UUID Id;
    public double Bias;
    public double BiasDelta;
    public double Gradient;
    public double Value;
    public LinkedList<Synapse> InputSynapses;
    public LinkedList<Synapse> OutputSynapses;


    public Neuron(){
        Id = UUID.randomUUID();
        InputSynapses = new LinkedList<Synapse>();
        OutputSynapses = new LinkedList<Synapse>();
        Bias = Network.GetRandom();
    }

    public Neuron(LinkedList<Neuron> inputNeurons){
        InputSynapses = new LinkedList<Synapse>();
        OutputSynapses = new LinkedList<Synapse>();
        for(Neuron n : inputNeurons){
            Synapse s = new Synapse(n,this);
            n.OutputSynapses.add(s);
            InputSynapses.add(s);
        }
    }

    public double CalculateValue(){
        double ret = 0;
        for(Synapse a: InputSynapses){
            ret=ret+a.Weight*a.InputNeuron.Value;
        }
        ret += Bias;
        ret = Sigmoid.Output(ret);
        Value = ret;
        return ret;
    }

    public double CalculateError(double target){
        return target-Value;
    }

    public double CalculateGredient(Double target){
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
        Bias += BiasDelta +momentum*prevDelta;

        for(Synapse s:InputSynapses){
            prevDelta = s.WeightDelta;
            s.WeightDelta = learnRate*Gradient*s.InputNeuron.Value;
            s.Weight += s.WeightDelta+momentum*prevDelta;
        }
    }


    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Neuron){
            return ((Neuron)obj).Id == this.Id;
        }
        return false;
    }
}
