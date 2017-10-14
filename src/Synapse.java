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

    @Override
    public String toString() {
        String ans = "";
        ans+= "\tId: "+ Id.toString()+"\n";
        ans+= "\tOutput Neuron Id: "+ OutputNeuron.Id.toString()+"\n";
        ans+= "\tInput Neuron Id: "+ InputNeuron.Id.toString()+"\n";
        ans+= "\tWeight: "+Weight+"\n";
        ans+= "\tWeight Delta: "+WeightDelta+"\n";
        return ans;
    }
}