import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class myClient {
    public static void main(String[] args){
        try{
            Socket socket = new Socket("localhost",6969);
            System.out.println("Connection established");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

            String userInput;
            while ((userInput = stdin.readLine()) != null) {
                out.println(userInput);
                out.flush();

                String response = in.readLine();
                if(response.compareTo("shutdown") == 0){
                    System.err.println("CONNECTION ENDED");
                    socket.shutdownOutput();
                    socket.shutdownInput();
                    socket.close();
                    return;
                }
                System.out.println("Server response: " + response);
            }

            socket.shutdownOutput();
            socket.shutdownInput();
            socket.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }   
}
