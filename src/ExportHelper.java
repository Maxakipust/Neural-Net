import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

class ExportHelper implements Serializable{
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
                e.printStackTrace();
                System.out.println("please try again");
            }
        }
    }
    public static Network ImportNetwork(String fileLoc){

        //HelperNetwork helperNetwork =null;
        Network net = null;
        try {
            FileInputStream fin = new FileInputStream(fileLoc);
            ObjectInputStream ois = new ObjectInputStream(fin);
            net = (Network)ois.readObject();
            ois.close();
            fin.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return net;
    }
    public static LinkedList<DataSet> ImportDatasets(String loc){
        LinkedList<DataSet> ret = new LinkedList<>();
        File f = new File(loc);
        File[] list = f.listFiles();
        if(list == null){
            while(list==null){
                System.out.println("Enter a valid location of the datasets: ");
                Scanner s = new Scanner(System.in);
                f = new File(s.nextLine());
                list = f.listFiles();
            }
        }
        for (File aList : list) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(aList));

                String[] vs = reader.readLine().split(",");
                String[] ts = reader.readLine().split(",");

                double[] vals = new double[vs.length];
                double[] targs = new double[vs.length];

                for (int x = 0; x < vs.length; x++) {
                    vals[x] = Double.parseDouble(vs[x]);
                }
                for (int x = 0; x < ts.length; x++) {
                    targs[x] = Double.parseDouble(ts[x]);
                }
                ret.add(new DataSet(vals, targs));

            } catch (Exception e) {
                System.err.println("ERROR READING DATASET FROM FILE \""+aList+"\"");
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
