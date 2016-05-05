import java.rmi.Naming;
import java.util.Scanner;

public class Client{
    public static void main(String[] args){
        try{
            Scanner in = new Scanner(System.in);
            System.out.print("Input IP:");
            String uri = in.nextLine();
            Translator trans =
                    (Translator)Naming.lookup("rmi://"+uri+":10240/translate");
            System.out.println("Connect success...");
            while(true){
                System.out.print("Input word: ");
                String word = in.nextLine();
                String ret = trans.translate(word);
                System.out.println("Result: "+ret);

            }

        }catch (Exception e){
            System.out.println("Error.....");
            e.printStackTrace();
        }
    }
}