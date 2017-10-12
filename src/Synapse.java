import java.io.Serializable;
import java.util.UUID;

public class Synapse implements Serializable {
    public UUID Id;
    public Neuron InputNeuron;
    public Neuron OutputNeuron;
    public double Weight;
    public double WeightDelta;

    public Synapse(){}

    public Synapse(Neuron inputNeruon, Neuron outputNeuron){
        this.Id = UUID.randomUUID();
        this.InputNeuron = inputNeruon;
        this.OutputNeuron = outputNeuron;
        this.Weight = Network.GetRandom();
    }
}
