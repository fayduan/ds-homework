import java.io.*;
import java.net.*;

public class Server {
    private int port;
    private String host;
    private String dirPath;
    private static ServerSocket server;

    public Server(int port,String dirPath){
        this.port = port;
        this.dirPath = dirPath;
        this.server = null;
    }

    public void run(){
        if(server==null){
            try {
                server = new ServerSocket(port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Server started...");
        while(true){
            try {
                Socket client = server.accept();
                if(client==null) continue;
                new SocketConnection(client,this.dirPath).run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public class SocketConnection extends Thread{
        private Socket client;
        private String filePath;

        public SocketConnection(Socket client, String filePath){
            this.client = client;
            this.filePath = filePath;
        }

        public void run(){
            if(client==null) return;
            DataInputStream in= null;
            DataOutputStream dos = null;
            DataOutputStream out = null;
            DataInputStream dis = null;
            try {
                in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
                String recvInfo = in.readUTF();
                String[] info = recvInfo.split(" ");
                String fileName = info[0];
                if(filePath.endsWith("/")==false&&filePath.endsWith("\\")==false){
                    filePath+="\\";
                }
                filePath += fileName;
                //从客户端上传到服务器
                //开始接收文件
                dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(filePath))));
                int bufferSize = 10240;
                byte[] buf = new byte[bufferSize];
                int num =0;
                while((num=in.read(buf))!=-1){
                    dos.write(buf, 0, num);
                }
                dos.flush();
                System.out.println("File received...");

            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                try {
                    if(out!=null) out.close();
                    if(in!=null)  in.close();
                    if(dos!=null) dos.close();
                    if(dis!=null) dis.close();
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args){
        int port = 10241;
        String dirPath = "recv/";
        new Server(port,dirPath).run();
    }
}