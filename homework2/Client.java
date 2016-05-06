import java.net.*;
import java.io.*;

public class Client {
    private Socket client;
    private boolean connected;
    public Client(String host,int port){
        try {
            client = new Socket(host,port);
            System.out.println("Connection successed...");
            this.connected = true;
        } catch (UnknownHostException e) {
            System.out.println("Unknown host...");
            this.connected = false;
        } catch (IOException e) {
            System.out.println("Connection failed...");
            this.connected = false;
            closeSocket();
        }
    }
    public boolean isConnected(){
        return connected;
    }
    public void setConnected(boolean connected){
        this.connected = connected;
    }

    public void sendFile(String localFileName, String remoteFileName){
        DataOutputStream dos = null;
        DataInputStream dis = null;
        if(client==null) return;
        File file = new File(localFileName);
        if(!file.exists()){
            System.out.println("Local file error...");
            this.connected = false;
            this.closeSocket();
            return;
        }
        try {
            dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
            dos = new DataOutputStream(client.getOutputStream());
            dos.writeUTF(remoteFileName);
            dos.flush();
            int bufferSize = 10240;
            byte[] buf = new byte[bufferSize];
            int num =0;
            while((num=dis.read(buf))!=-1){
                dos.write(buf, 0, num);
            }
            dos.flush();
            System.out.println("File sent...");
        } catch (IOException e) {
            System.out.println("Transfer error...");
            closeSocket();
        } finally{
            try {
                dis.close();
                dos.close();
            } catch (IOException e) {
                System.out.println("Input error...");
            }
        }
    }
    //关闭端口
    public void closeSocket(){
        if(client!=null){
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        if(args.length!=3){
            System.out.println("Args error...");
            return;
        }
        String hostName = args[0];
        int port = 10241;
        String localFileName = "";
        String remoteFileName = "";
        Client client = null;

        client = new Client(hostName,port);
        localFileName = args[1];
        remoteFileName = args[2];
        if(client.isConnected()){
            client.sendFile(localFileName, remoteFileName);
            client.closeSocket();
        }

    }
}