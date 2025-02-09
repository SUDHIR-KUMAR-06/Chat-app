import java.io.*;
import java.net.*;
final class Client{
    Socket socket;
    BufferedReader  br;//br for reading
    PrintWriter out;
    public Client()
    {

        //out for writing

        try {
            System.out.println("sending request to server");
            socket=new Socket("127.0.0.1",7777);
            System.out.println("connecction done");
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out=new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        } catch(IOException e) {
            // e.printStackTrace();
            System.out.println("Connection is closed");
        }
    }
    
    public void startReading()
    {
        //this thread will keep reading data
        Runnable r1=()->{
            System.out.println("reading started");
            try {
    
            while(true){

                String msg=br.readLine();
               
               
                if(msg.equals("exit")){
                    System.out.println("Server terminated the chat");
                    socket.close();
                    break;
                }

                System.out.println("Sever :"+msg);
            }
            } catch (IOException e) {
                // e.printStackTrace();
                System.out.println("Connection is closed");
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
            e.printStackTrace();
           
            }
        };
        new Thread(r2).start();
    }

   
    public static void main(String[] args) {
        System.out.println("this is client");
        new Client();
    }
}