import java.util.UUID;

public class HelperNeuron{
    public UUID id;
    public double Bias;
    public double BiasDelta;
    public double Gradiant;
    public double Value;

    public HelperNeuron(Neuron neuron){
        this.id = neuron.Id;
        this.Bias = neuron.Bias;
        this.BiasDelta = neuron.BiasDelta;
        this.Gradiant = neuron.Gradient;
        this.Value = neuron.Value;
    }

    public Neuron ToNeuron(){
        Neuron ret = new Neuron();
        ret.Id = this.id;
        ret.Bias = this.Bias;
        ret.BiasDelta = this.BiasDelta;
        ret.Gradient = this.Gradiant;
        ret.Value = this.Value;
        return ret;
    }
}
