import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class myServer {
    public static void main(String[] args){
        try{
            int port = 0;
            try{
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            ServerSocket ss = new ServerSocket(port);
            System.out.println("SERVER LISTENING ON PORT " + port);
            while(true){
                Socket socket = ss.accept();
                
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                
                String text;
                while((text = in.readLine()) != null){
                    out.println(text);
                    out.flush();

                    if(text.compareTo("shutdown") == 0){
                        socket.shutdownOutput();
                        socket.shutdownInput();
                        socket.close();
                        ss.close();
                        return;
                    };
                }
                out.println("shutdown");
                out.flush();
                socket.shutdownOutput();
                socket.shutdownInput();
                socket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
