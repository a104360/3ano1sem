import java.io.DataOutputStream;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;


public class FramedConnection implements AutoCloseable{
    private Socket sock;
    private DataInputStream input;
    private DataOutputStream output;
    private ReentrantLock lock = new ReentrantLock();

    public FramedConnection(Socket Socket) throws IOException {
        this.sock = Socket;
        this.input = new DataInputStream(new BufferedInputStream(this.sock.getInputStream()));
        this.output = new DataOutputStream(new BufferedOutputStream(this.sock.getOutputStream()));
    }

    public void send(byte[] data) throws IOException { 
        this.lock.lock();
        try{
            this.output.writeInt(data.length);
            this.output.write(data);
            this.output.flush();
        } finally {
            this.lock.unlock();
        }
    }

    public byte[] receive() throws IOException { 
        this.lock.lock();
        try{
            int size = this.input.readInt();
            byte[] data = this.input.readNBytes(size);
            return data;
        } finally {
            this.lock.unlock();
        }
    }

    public void close() throws IOException {
        this.sock.close();
    }
}
