import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.Arrays.asList;

class ContactManager {
    private HashMap<String, Contact> contacts = new HashMap<>();
    private ReentrantLock lock = new ReentrantLock();


    // @TODO
    public void update(Contact c) {
        this.lock.lock();
        try{
            this.contacts.put(c.name(), c);
        } finally {
            this.lock.unlock();
        }
    }

    // @TODO
    public ContactList getContacts() {
        ContactList contList = new ContactList();
        this.lock.lock();
        try{
            contList.addAll(contacts.values());
            return contList;
        } finally {
            this.lock.unlock();
        }
    }
}

class ServerWorker implements Runnable {
    private Socket socket;
    private ContactManager manager;

    public ServerWorker(Socket socket, ContactManager manager) {
        this.socket = socket;
        this.manager = manager;
    }

    // @TODO
    @Override
    public void run() {
        try{
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());
            
            String contacts = this.clientConnected();
            System.out.println(contacts);
            out.writeUTF(contacts);
            out.flush();

            boolean open = true;
            while(open){
                Contact c = Contact.deserialize(in);

                if(c == null){
                    open = false;
                } else {
                    this.manager.update(c);
                    out.writeUTF("CLIENT SENT");
                    out.flush();
                }
            }

            this.socket.shutdownInput();
            this.socket.shutdownOutput();
            this.socket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private String clientConnected(){
        String text = "";
        for (Contact contact : this.manager.getContacts()) {
            text = "{" + contact.name() + " " + contact.age() + " " + contact.phoneNumber() + " " + contact.company() + " " ;
            Iterator<String> it = contact.emails().iterator();
            while(it.hasNext()){
                Object c = it.next();
                if(it.hasNext()) text += c + " ";
                else{
                    text += c;
                }
            }
            text += "}\n";
        }
        return text;
    }
}



public class Server {

    public static void main (String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        ContactManager manager = new ContactManager();
        // example pre-population
        manager.update(new Contact("John", 20, 253123321, null, asList("john@mail.com")));
        manager.update(new Contact("Alice", 30, 253987654, "CompanyInc.", asList("alice.personal@mail.com", "alice.business@mail.com")));
        manager.update(new Contact("Bob", 40, 253123456, "Comp.Ld", asList("bob@mail.com", "bob.work@mail.com")));

        System.out.println(manager.getContacts());

        while (true) {
            Socket socket = serverSocket.accept();
            Thread worker = new Thread(new ServerWorker(socket, manager));
            worker.start();
        }
    }

}
