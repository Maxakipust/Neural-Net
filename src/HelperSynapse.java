import java.util.LinkedList;
import java.util.UUID;

public class HelperSynapse {
    public UUID id;
    public UUID OutputNeuronID;
    public UUID InputNeuronID;
    public double Weight;
    public double WeightDelta;

    public HelperSynapse(Synapse synapse){
        this.id = synapse.Id;
        this.OutputNeuronID = synapse.OutputNeuron.Id;
        this.InputNeuronID = synapse.InputNeuron.Id;
        this.Weight = synapse.Weight;
        this.WeightDelta = synapse.WeightDelta;
    }

    public Synapse ToSynapse(LinkedList<Neuron> neurons){
        Synapse ret = new Synapse();
        ret.Id=id;
        ret.Weight = Weight;
        ret.WeightDelta = WeightDelta;
        for(Neuron n:neurons){
            if(InputNeuronID==n.Id){
                ret.InputNeuron=n;
            }else if(OutputNeuronID==n.Id){
                ret.OutputNeuron=n;
            }
        }
        return ret;
    }
}
