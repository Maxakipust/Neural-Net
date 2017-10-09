import javax.xml.stream.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class ExportHelper {
    public static void ExportNetwork(Network network, String fileLoc){
        File f = new File(fileLoc);
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(f));
            out.write(network.toString());
        }catch (Exception ex){

        }
    }
}
