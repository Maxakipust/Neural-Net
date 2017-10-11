
import com.google.gson.Gson;
import com.oracle.javafx.jmx.json.*;

import java.io.*;

public class ExportHelper {
    public static void ExportNetwork(Network network, String fileLoc){
        HelperNetwork helperNetwork = new HelperNetwork(network);
        Gson gson = new Gson();
        try {
            gson.toJson(helperNetwork, new FileWriter(fileLoc));
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public static Network ImportNetwork(String fileLoc){
        Gson gson = new Gson();
        HelperNetwork helperNetwork =null;
        try {
            helperNetwork = gson.fromJson(new FileReader(fileLoc),HelperNetwork.class);
        }catch (Exception e){
            System.out.println(e);
        }
        return helperNetwork.ToNetwork();
    }
}
