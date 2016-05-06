/**
 * Created by fay on 16-5-5.
 */

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String args[])throws MalformedURLException,AlreadyBoundException{
        try {
            TranslatorImpl trans = new TranslatorImpl();
            LocateRegistry.createRegistry(10240);
            Naming.bind("rmi://192.168.0.104:10240/translate",trans);
            System.out.println("Server started...");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
