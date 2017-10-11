import java.util.List;
import java.util.Scanner;

public class Program {
    private static Scanner scanner;
    private static int numInputParams;
    private static int numHiddenLayers;
    private static int[] numHiddenNeurons;
    private static int numOutputParams;
    private static Network network;
    private static List<DataSet> dataSets;


    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        Greet();
        InitialMenu();
    }

    public static void Greet(){
        println("Max's First Neural Network");
        printSeperater();
        println("");
    }

    public static void InitialMenu(){
        println("Main Menu");
        printSeperater();
        println("\t1. Create New Network");
        println("\t2. Import Network");
        println("");
        println(">");
        String input = scanner.nextLine();
        if(input.trim().equals("1")){
            SetupNatwork();
            DatasetMenu();
        }else if(input.trim().equals("2")){
            ImportNetwork();
            DatasetMenu();
        }else{
            Exit();
        }
    }

    public static void DatasetMenu(){
        println("Dataset Menu");
        printSeperater();
        println("\t1. Type Dataset");
        println("\t2. Import Dataset");
        println
    }

    public static void SetupNatwork(){
        println("");
        System.out.println("Network Setup");
        printSeperater();

    }

    public static void println(String dat){
        System.out.println(dat);
    }

    public static void printSeperater(){
        String ans = "";
        for(int i =0; i<50;i++){
            ans+="-";
        }
        println(ans);
    }
}
