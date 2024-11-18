import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class clientSoma {
    public static void main(String[] args){
        try{
            int port = 0;
            try{
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            Socket socket = new Socket("localhost",port);
            System.out.println("Connection established".toUpperCase());

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

            String userInput;
            while ((userInput = stdin.readLine()) != null) {
                out.println(userInput);
                out.flush();

                String response = in.readLine();
                System.out.println("Server response: " + response);
                if(userInput.compareTo("shutdown") == 0){
                    System.err.println("CONNECTION ENDED");
                    socket.shutdownOutput();
                    socket.shutdownInput();
                    socket.close();
                    return;
                }
            }
            socket.shutdownOutput();

            String text = in.readLine();
            float average = -1;
            if(text != null) average = Float.parseFloat(text);
            System.out.println(average); 
            socket.shutdownInput();
            socket.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }   
}
