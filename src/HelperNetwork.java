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
    public HelperNetwork(Network network){
        LearnRate = network.LearnRate;
        Momentum = network.Momentum;
        for(Neuron n:network.InputLayer){
            InputLayer.add(new HelperNeuron(n));
        }
        for(LinkedList<Neuron> list:network.HiddenLayers){
            LinkedList<HelperNeuron> temp = new LinkedList<HelperNeuron>();
            for(Neuron n:list){
                temp.add(new HelperNeuron(n));
            }
            HiddenLayers.add(temp);
        }
        for(Neuron n:network.OutputLayer){
            InputLayer.add(new HelperNeuron(n));
        }


        for(Neuron n:network.InputLayer){
            for(Synapse s:n.InputSynapses){
                Synapses.add(new HelperSynapse(s));
            }
            for(Synapse s:n.OutputSynapses){
                Synapses.add(new HelperSynapse(s));
            }
        }
        for(LinkedList<Neuron> l: network.HiddenLayers) {
            for (Neuron n : l) {
                for (Synapse s : n.InputSynapses) {
                    Synapses.add(new HelperSynapse(s));
                }
                for (Synapse s : n.OutputSynapses) {
                    Synapses.add(new HelperSynapse(s));
                }
            }
        }
        for(Neuron n:network.OutputLayer){
            for(Synapse s:n.InputSynapses){
                Synapses.add(new HelperSynapse(s));
            }
            for(Synapse s:n.OutputSynapses){
                Synapses.add(new HelperSynapse(s));
            }
        }
    }

    public Network ToNetwork(){
        Network ret = new Network();
        ret.LearnRate = this.LearnRate;
        ret.Momentum = this.Momentum;
        LinkedList<Neuron> nList = new LinkedList<Neuron>();
        for(HelperNeuron n:InputLayer){
            nList.add(n.ToNeuron());
        }
        for(LinkedList<HelperNeuron> nl:HiddenLayers){
            for(HelperNeuron n:nl) {
                nList.add(n.ToNeuron());
            }
        }
        for(HelperNeuron n:OutputLayer){
            nList.add(n.ToNeuron());
        }

        LinkedList<Synapse> sList = new LinkedList<Synapse>();
        for(HelperSynapse s:Synapses){
            sList.add(s.ToSynapse(nList));
        }

        for (Synapse s:sList) {
            for(Neuron n:nList){
                if(n.Id==s.InputNeuron.Id){
                    n.OutputSynapses.add(s);
                }else if(n.Id==s.OutputNeuron.Id){
                    n.InputSynapses.add(s);
                }
            }
        }


        for(HelperNeuron n:InputLayer){
            for(Neuron rn:nList){
                if(n.id == rn.Id){
                    ret.InputLayer.add(rn);
                }
            }
        }
        for(LinkedList<HelperNeuron> nl:HiddenLayers){
            LinkedList<Neuron> tempList = new LinkedList<Neuron>();
            for(HelperNeuron n:nl){
                for(Neuron rn:nList)
                if(rn.Id == n.id){
                    tempList.add(rn);
                }
            }
            ret.HiddenLayers.add(tempList);
        }

        for(HelperNeuron n:OutputLayer){
            for(Neuron rn:nList){
                if(n.id == rn.Id){
                    ret.OutputLayer.add(rn);
                }
            }
        }

        return ret;
    }
}