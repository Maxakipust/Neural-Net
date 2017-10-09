import java.util.LinkedList;

public class HelperNetwork {
    public double LearnRate;
    public double Momentum;
    public LinkedList<HelperNeuron> InputLayer;
    public LinkedList<LinkedList<HelperNeuron>> HiddenLayers;
    public LinkedList<HelperNeuron> OutputLayer;
    public LinkedList<HelperSynapse> Synapses;

    public HelperNetwork(){
        InputLayer = new LinkedList<HelperNeuron>();
        HiddenLayers = new LinkedList<LinkedList<HelperNeuron>>();
        OutputLayer = new LinkedList<HelperNeuron>();
        Synapses = new LinkedList<HelperSynapse>();
    }
}