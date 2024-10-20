import java.io.*;
import java.net.*;

final class Server{

    ServerSocket server;
    Socket socket;
    BufferedReader  br;//br for reading
    PrintWriter out;//out for writing
    //constructor
    public Server()
    {
        try{
            server=new ServerSocket(7777);
            System.out.println("server is up and running");
            System.out.println("waiting");
            socket=server.accept();
            //accept client request and store in socket
            //using socket we can extract input string,output string etc.
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out=new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }


    public void startReading()
    {
        //this thread will keep reading data
        Runnable r1=()->{
            System.out.println("reading started");
            try{
            while(true){

                String msg=null;
                
                    msg=br.readLine();
                
                
                if(msg.equals("exit")){
                    System.out.println("Client terminated the chat");
                    
                    socket.close();
                    
                    break;
                }

                System.out.println("Client :"+msg);
            }
            }catch(IOException e){
                // e.printStackTrace();
                System.out.println("Connection closed");
            }
        };
        new Thread(r1).start();
    }
    public void startWriting()
    {
        //this thread will keep writing data
        Runnable r2=()->{
            System.out.println("writer started");
            try{
            while(!socket.isClosed()){
               

                    BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
                    String content=br1.readLine();
                    
                    out.println(content);
                    out.flush();
                    if(content.equals("exit")){
                        socket.close();
                        break;
                    }
                
            }
            }catch(IOException e){
                // e.printStackTrace();
                System.out.println("Connection is closed");
            }
        };
        new Thread(r2).start();
    }


    public static void main(String[] args) {
        System.out.println("this is server");
        new Server();
    }
}