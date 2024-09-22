import java.lang.Runnable;
import java.lang.Thread;

class Increment implements Runnable {
    public void run() {
        final long I = 100;

        for (long i = 0; i < I; i++)
            System.out.println(i);
    }
}

public class exe1{
    public static void main(String[] args) throws InterruptedException{
        int size = Integer.parseInt(args[0]);
        Thread[] t = new Thread[size];
        for(int i = 0;i < size;i++){
            t[i] = new Thread(new Increment());
            t[i].start();
        }

        for(int i = 0;i < size;i++){
            try{
                t[i].join();
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        System.out.println("Fim");
    }
}
