import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

class ContactList extends ArrayList<Contact> {

    // @TODO
    public void serialize(DataOutputStream out) throws IOException {
        out.writeInt(this.size());
        Iterator<Contact> it = this.iterator();
        while(it.hasNext()){
            it.next().serialize(out);
        }
    }

    // @TODO
    public static ContactList deserialize(DataInputStream in) throws IOException {
        int size = in.readInt();
        ContactList c = new ContactList();
        for(int i = 0;i < size;i++){
            c.add(Contact.deserialize(in));
        }
        return c;
    }  

}
