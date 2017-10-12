
import com.oracle.javafx.jmx.json.*;
import sun.awt.image.ImageWatched;

import java.io.*;
import java.util.LinkedList;

public class ExportHelper implements Serializable{
    public static void ExportNetwork(Network network, String fileLoc){
        //HelperNetwork helperNetwork = new HelperNetwork(network);

        while(true) {
            try {
                FileOutputStream fout = new FileOutputStream(fileLoc);
                ObjectOutputStream oos = new ObjectOutputStream(fout);
                oos.writeObject(network);
                oos.close();
                fout.close();
                break;
            } catch (IOException e) {
                System.out.println("ERROR file not found");
                System.out.println(e);
                System.out.println("please try again");
            }
        }
    }
    public static Network ImportNetwork(String fileLoc){

        //HelperNetwork helperNetwork =null;
        Network helperNetwork = null;
        try {
            FileInputStream fin = new FileInputStream(fileLoc);
            ObjectInputStream ois = new ObjectInputStream(fin);
            helperNetwork = (Network)ois.readObject();
            ois.close();
            fin.close();
        }catch (Exception e){
            System.out.println(e);
        }
        return helperNetwork;//.ToNetwork();
    }
    public static LinkedList<DataSet> ImportDatasets(String loc){
        LinkedList<DataSet> ret = new LinkedList<DataSet>();
        File f = new File(loc);
        File[] list = f.listFiles();
        for(int i = 0; i< list.length;i++){
            try{
                BufferedReader reader = new BufferedReader(new FileReader(list[i]));

                String[] vs = reader.readLine().split(",");
                String[] ts = reader.readLine().split(",");

                double[] vals = new double[vs.length];
                double[] targs = new double[vs.length];

                for(int x = 0;x<vs.length;x++){
                    vals[x] = Double.parseDouble(vs[x]);
                }
                for(int x = 0;x<ts.length;x++){
                    targs[x] = Double.parseDouble(ts[x]);
                }
                ret.add(new DataSet(vals,targs));

            }catch(Exception e){
                System.out.println(e);
            }
        }
        return ret;
    }
    public static void ExportDatasets(LinkedList<DataSet> sets, String loc){
        for(int i = 0; i< sets.size();i++){
            try{
                PrintWriter writer = new PrintWriter(loc+"/dataset"+i+".txt");
                for(int x = 0;x<sets.get(i).Values.length;x++){
                    writer.print(sets.get(i).Values[x]);
                    if(x<sets.get(i).Values.length-1){
                        writer.print(",");
                    }
                }
                writer.println("");

                for(int x = 0;x<sets.get(i).Targets.length;x++){
                    writer.print(sets.get(i).Targets[x]);
                    if(x<sets.get(i).Targets.length-1){
                        writer.print(",");
                    }
                }
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
}
