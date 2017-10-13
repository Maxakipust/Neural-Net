import java.io.Serializable;
import java.util.UUID;

class Synapse implements Serializable {
    private UUID Id;
    public Neuron InputNeuron;
    public Neuron OutputNeuron;
    public double Weight;
    public double WeightDelta;

    public Synapse(Neuron inputNeruon, Neuron outputNeuron){
        this.Id = UUID.randomUUID();
        this.InputNeuron = inputNeruon;
        this.OutputNeuron = outputNeuron;
        this.Weight = Network.GetRandom();
    }
}