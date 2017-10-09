import java.util.Scanner;

public class Program {
    private static Scanner scanner;
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        println("Max's Neural Net");
        printSeperater();

        println("\t1. New Network");
        println("\t2. Import Network");
        println("\t3. Exit");

        String input = scanner.nextLine();
        if(input=="1"){

        }
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
