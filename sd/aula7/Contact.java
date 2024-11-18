import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

class Contact {
    private String name;
    private int age;
    private long phoneNumber;
    private String company;     // Pode ser null
    private ArrayList<String> emails;

    public Contact(String name, int age, long phoneNumber, String company, List<String> emails) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.company = company;
        this.emails = new ArrayList<>(emails);
    }

    public String name() { return name; }
    public int age() { return age; }
    public long phoneNumber() { return phoneNumber; }
    public String company() { return company; }
    public List<String> emails() { return new ArrayList<>(emails); }

    // @TODO
    public void serialize(DataOutputStream out) throws IOException {
        
        out.writeUTF(this.name);
        out.writeInt(this.age);
        out.writeLong(this.phoneNumber);
        if(this.company != null){
            out.writeBoolean(true);
            out.writeUTF(this.company);
        } else {
            out.writeBoolean(false);
        }
        out.writeInt(this.emails.size());
        for (String email : this.emails) {
            out.writeUTF(email);
        }
        out.flush();
    }

    // @TODO
    public static Contact deserialize(DataInputStream in) throws IOException {
        try{

            String localName = in.readUTF();
            int localAge = in.readInt();
            long localPhone = in.readLong();
            String localCompany = in.readUTF();
            int size = in.readInt();
            List<String> emailList = new ArrayList<>(size);
            for(int i = 0;i < size;i++){
                emailList.add(in.readUTF());
            }

            return new Contact(localName, localAge, localPhone, localCompany, emailList);

        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.name).append(";\n");
        builder.append(this.age).append(";\n");
        builder.append(this.phoneNumber).append(";\n");
        builder.append(this.company).append(";\n");
        builder.append(this.emails.toString() + "\n");
        builder.append("}\n");
        return builder.toString();
    }

}
