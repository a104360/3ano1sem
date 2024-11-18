
import java.net.ServerSocket;
import java.net.Socket;

//import static TaggedConnection.Frame;

public class SimpleServerWithWorkers {
    final static int WORKERS_PER_CONNECTION = 3;

    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(12345);

        while(true) {
            Socket s = ss.accept();
            FramedConnection c = new FramedConnection(s);
            
            Runnable worker = () -> {
                try (c) {
                    for (;;) {
                        byte[] b = c.receive();
                        System.out.println("A MESSAGE WAS RECEIVED");
                        String msg = new String(b);
                        System.out.println("Replying to: " + msg);
                        c.send(msg.toUpperCase().getBytes());
                        System.out.println("SHOULD HAVE SENT");
                    }
                } catch (Exception ignored) { }
            };

            for (int i = 0; i < WORKERS_PER_CONNECTION; ++i)
                new Thread(worker).start();
        }
    }
}

