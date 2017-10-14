import java.util.LinkedList;
import java.util.Scanner;

class Program {
    private static Scanner scanner;
    private static int numInputParams;
    private static int numHiddenLayers;
    private static int[] numHiddenNeurons;
    private static int numOutputParams;
    private static Network network;
    private static LinkedList<DataSet> dataSets;


    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        Greet();
        InitialMenu();
    }

    private static void Greet(){
        println("Max's First Neural Network");
        printSeperater();
        println("");
    }

    private static void InitialMenu(){
        println("Main Menu");
        printSeperater();
        println("\t1. Create New Network");
        println("\t2. Import Network");
        println("");
        System.out.print(">");
        String input = scanner.nextLine();
        if(input.trim().equals("1")){
            if(SetupNatwork())
                DatasetMenu();
            InitialMenu();
        }else if(input.trim().equals("2")){
            ImportNetwork();
            DatasetMenu();
        }else{
            Exit();
        }
    }

    private static void DatasetMenu(){
        println("Dataset Menu");
        printSeperater();
        println("\t1. Type Dataset");
        println("\t2. Import Dataset");
        println("\t3. Test Network");
        println("\t4. Export Network");
        println("\t5. Main Menu");
        println("\t6. Exit");
        println("");
        System.out.print(">");
        String input = scanner.nextLine();
        if(input.trim().equals("1")){
            GetTrainingData();
            NetworkMenu();
        }else if(input.trim().equals("2")){
            ImportDatasets();
            NetworkMenu();
        }else if(input.trim().equals("3")){
            TestNetwork();
            DatasetMenu();
        }else if(input.trim().equals("4")){
            ExportNetwork();
            DatasetMenu();
        }else if(input.trim().equals("5")){
            InitialMenu();
        }else{
            Exit();
        }
    }

    private static void NetworkMenu(){
        println("Network Menu");
        printSeperater();
        println("\t1. Train Network");
        println("\t2. Test Network");
        println("\t3. Export Network");
        println("\t4. Export Dataset");
        println("\t5. Dataset Menu");
        println("\t6. Main Menu");
        println("\t7. Exit");
        println("");
        System.out.print(">");
        String input = scanner.nextLine();
        if(input.trim().equals("1")){
            Train();
            NetworkMenu();
        }else if(input.trim().equals("2")){
            TestNetwork();
            NetworkMenu();
        }else if(input.trim().equals("3")){
            ExportNetwork();
            NetworkMenu();
        }else if(input.trim().equals("4")){
            ExportDatasets();
            NetworkMenu();
        }else if(input.trim().equals("5")){
            DatasetMenu();
        }else if(input.trim().equals("6")){
            InitialMenu();
        }else if(input.trim().equals("7")){
            Exit();
        }
    }

    private static boolean SetupNatwork(){
        println("");
        System.out.println("Network Setup");
        printSeperater();
        SetNumInputParameters();
        if(numInputParams==0)
            return false;
        SetNumNeuronsInHidden();
        if(numHiddenLayers==0)
            return false;
        SetNumOutputParameters();
        if(numInputParams==0)
            return false;

        println("\tCreating Network...");
        network = new Network(numInputParams,numHiddenNeurons,numOutputParams,null,null);
        println("\t**Network Created!**");
        println("");
        return true;
    }

    private static void SetNumInputParameters(){
        println("\tHow Many input parameters will there be? (at least 2)");
        System.out.print(">");
        numInputParams = Integer.parseInt(scanner.nextLine());
        if(numInputParams<2){
            numInputParams = 0;
        }
        println("");
        println("");
    }

    private static void SetNumNeuronsInHidden(){
        println("\tHow many hidden layers? (at least 1)");
        System.out.print(">");
        numHiddenLayers = Integer.parseInt(scanner.nextLine().trim());

        println("How many neurons in the hidden layers (at least 2 seperated by \",\"");
        System.out.print(">");
        String[] input = scanner.nextLine().trim().split(",");
        numHiddenNeurons = new int[input.length];
        for(int i = 0; i< numHiddenNeurons.length;i++){
            numHiddenNeurons[i]=Integer.parseInt(input[i]);
        }
        println("");
        println("");
    }

    private static void SetNumOutputParameters(){
        println("\tHow Many output parameters will there be? (at least 1)");
        System.out.print(">");
        numOutputParams = Integer.parseInt(scanner.nextLine());
        if(numOutputParams<2){
            numOutputParams = 0;
        }
        println("");
        println("");
    }

    private static boolean GetTrainingData(){
        printSeperater();
        println("\tManually enter the datasets. Type'menu at any time to go back.");
        println("");
        println("\tHow many datasets are you going to enter?");
        System.out.print(">");
        int numDatasets = Integer.parseInt(scanner.nextLine());
        LinkedList<DataSet> newDatasets = new LinkedList<>();
        for(int i = 0; i<numDatasets;i++){
            double[] values = GetInputData("\tData Set "+i+"'s Values: ");
            if(values==null){
                println("");
                return false;
            }

            double[] expectedResults = GetExpectedResults("Expected Results for Data Set "+(i)+": ");
            if(expectedResults == null){
                println("");
                return false;
            }

            newDatasets.add(new DataSet(values, expectedResults));
        }
        dataSets = newDatasets;
        println("");
        return true;
    }

    private static double[] GetInputData(String message)
    {
        println(message);
        System.out.print(">");
        String line = scanner.nextLine();

        if (line.equals("menu"))
            return null;

        while (line == null || line.split(",").length != numInputParams)
        {
            println("\t"+numInputParams+" inputs are required.");
            println("");
            println(message);
            System.out.print(">");
            line = scanner.nextLine();
            if (line.equals("menu"))
                return null;
        }

        double[] values = new double[numInputParams];
        String[] lineNums = line.split(",");
        for (int i = 0; i < lineNums.length; i++)
        {
            double num;
            num = Double.parseDouble(lineNums[i]);
            values[i] = num;
        }

        return values;
    }

    private static double[] GetExpectedResults(String message)
    {
        println(message);
        System.out.print(">");
        String line = scanner.nextLine();

        if (line != null && line.equals("menu"))
            return null;

        while (line == null || line.split(",").length != numOutputParams)
        {
            println("\t"+numOutputParams+" outputs are required.");
            println(" ");
            println(message);
            System.out.println(">");
            line = scanner.nextLine();
        }

        double[] values = new double[numOutputParams];
        String[] lineNums = line.split(",");
        for (int i = 0; i < lineNums.length; i++)
        {
            int num = Integer.parseInt(lineNums[i]);
            if (num == 0 || num == 1)
            {
                values[i] = num;
            }
            else
            {
                println("\tYou must enter 1s and 0s!");
                println("");
                println("");
                return GetExpectedResults(message);
            }
        }
        return values;
    }

    private static void TestNetwork()
    {
        println("\tTesting Network");
        println("\tType 'menu' at any time to return to the previous menu.");
        println("");

        while (true)
        {
           printSeperater();
            double[] values = GetInputData("\tType "+numInputParams+" inputs (or 'menu' to exit): ");
            if (values == null)
            {
                println("");
                return;
            }

            double[] results = network.Compute(values);
            println("");

            for (double result:results)
            {
                println("\tOutput: "+result);
            }

            println("");
        }
    }

    private static void Train()
    {
        println("Network Training");
        printSeperater();
        println("\t1. Train to minimum error");
        println("\t2. Train to max epoch");
        println("\t3. Network Menu");
        println("");

        println("\tYour Choice: ");
        System.out.print(">");
        String input = scanner.nextLine().trim();
        if(input.equals("1")) {
            println("\tMinimum Error from 0.000000001 to 1.0: ");
            System.out.print(">");
            Double minError = Double.parseDouble(scanner.nextLine());
            println("");
            println("\tTraining...");
            network.Train(dataSets, minError);
            println("\t**Training Complete**");
            println("");
            NetworkMenu();
        }else if(input.equals("2")) {
            println("\tMax Epoch greater then 1: ");
            System.out.print(">");
            String maxEpoch = scanner.nextLine();
            if (maxEpoch.equals("")) {
                println("");
                NetworkMenu();
                return;
            }
            println("");
            println("\tTraining...");
            network.Train(dataSets, Integer.parseInt(maxEpoch));
            println("\t**Training Complete**");
            println("");
        }else if(input.equals("3")){
            NetworkMenu();
        }
        println("");
    }

    private static void ImportNetwork()
    {
        println("");
        println("Enter the location of the network");
        System.out.println(">");
        String location = scanner.nextLine();
        network = ExportHelper.ImportNetwork(location);
        if (network == null)
        {
            println("\t****Something went wrong while importing your network.****");
            Exit();
        }

        numInputParams = network.InputLayer.size();
        numHiddenNeurons = new int[network.HiddenLayers.size()];
        numOutputParams = network.OutputLayer.size();

        println("\t**Network successfully imported.**");
        println("");

        DumpNet();
    }

    private static void ExportNetwork()
    {
        println("");
        println("Enter the location of the network");
        System.out.print(">");
        String loc = scanner.nextLine();
        println("\tExporting Network...");
        ExportHelper.ExportNetwork(network,loc);
        println("\t**Exporting Complete!**");
        println("");
    }

    private static void ImportDatasets()
    {
        println("");
        println("Enter the location of the datasets");
        System.out.print(">");
        String loc = scanner.nextLine();
        dataSets = ExportHelper.ImportDatasets(loc);
        if (dataSets == null)
        {
            println("\t--Something went wrong while importing your datasets.--");
            return;
        }
        boolean temp = false;
        for(DataSet x:dataSets){
            if(x.Values.length != numOutputParams||x.Targets.length!=numOutputParams){
                temp = true;
                break;
            }
        }

        if (temp)
        {
            println("\t--The dataset does not fit the network.  Network requires datasets that have "+numInputParams+" inputs and "+numOutputParams+" outputs.--");
            return;
        }

        println("\t**Datasets successfully imported.**");
        println("");
    }

    private static void ExportDatasets()
    {
        println("");
        println("Enter the location of the network");
        System.out.print(">");
        String loc = scanner.nextLine();
        println("\tExporting Datasets...");
        ExportHelper.ExportDatasets(dataSets,loc);
        println("\t**Exporting Complete!**");
        println("");
    }

    private static void Exit(){
        println("Exiting...");
        println("Press ENTER to continue");
        scanner.nextLine();
        System.exit(0);
    }

    private static void DumpNet(){
        println("Printing Network");
        printSeperater();
        println(network.toString());
    }


    private static void println(String dat){
        System.out.println(dat);
    }

    private static void printSeperater(){
        String ans = "";
        for(int i =0; i<50;i++){
            ans+="-";
        }
        println(ans);
    }
}
