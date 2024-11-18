import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;

class Users implements Runnable{
    private ServerSocket ss;
    private Socket socket;
    private Operations op;
    
    public Users(ServerSocket ss, Socket socket,Operations op){
        this.ss = ss;
        this.socket = socket;
        this.op = op;
    }

    public void run(){
        try{

            System.out.println("CONNECTION ACCEPTED FROM " + socket.getInetAddress() + ":" + socket.getPort());
            
            //int sum = 0;
            //int counter = 0;
            
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            
            String text;
            while((text = in.readLine()) != null){

                if(text.compareTo("shutdown") == 0){
                    /*try{
                        float average = sum/counter;
                        out.println(average);
                        out.flush();
                    } catch(Exception e){
                        
                    }*/

                    out.println(this.op.average());
                    out.flush();
                    socket.shutdownOutput();
                    socket.shutdownInput();
                    System.out.println("CONNECTION TO CLIENT ON " + socket.getInetAddress() + ":" + socket.getLocalPort());
                    socket.close();
                    System.err.println("SERVER OFFLINE");
                    ss.close();
                    return;
                };

                switch(text.charAt(0)){
                    case '+':
                        op.add(Integer.parseInt(text.substring(1)));
                        break;
                    case '-':
                        op.add(Integer.parseInt(text.substring(1)));
                        break;
                    case '*':
                        op.mul(Integer.parseInt(text.substring(1)));
                        break;
                    case '/':
                        op.div(Integer.parseInt(text.substring(1)));
                        break;
                    default:
                        break;
                }

                out.println(Integer.toString(op.getSum()));
                out.flush();
                /*try{
                    sum += Integer.parseInt(text);
                    counter++;
                } catch (NumberFormatException e){
                    //e.printStackTrace();
                }
                out.println(Integer.toString(sum));
                out.flush();*/
                
                
            }
            // User sent a Ctrl + D and will not write anymore on this connection
            //System.out.println(sum);
            //System.out.println(counter);
            //int average = sum/counter;
            //out.println(average);
            //out.flush();
            socket.shutdownInput();
            out.println(this.op.average());
            socket.shutdownOutput();
            System.out.println("CONNECTION CLOSED TO CLIENT ON " + socket.getInetAddress() + ":" + socket.getLocalPort());
            socket.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}


public class ServerSoma2 {

    public static void main(String[] args){
        try{
            int port = 0;
            try{
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("INVALID PORT");
            }

            ServerSocket ss = new ServerSocket(port);
            System.out.println("SERVER LISTENING ON PORT " + port);

            Thread t1 = null;

            Operations op = new Operations();

            while(true){
                try{
                    t1 = new Thread(new Users(ss, ss.accept(),op));
                    t1.start();
                } catch (IOException e){
                    System.err.println("CLOSED CONNECTION, SERVER GOING OFFLINE");
                    try{
                        t1.join();
                    } catch (InterruptedException s) {
                        System.err.println("Deu asneira no join");
                    }
                    break;
                }
            }
            

            
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
