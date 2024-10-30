import java.lang.Runnable;
import java.lang.Thread;
import java.util.concurrent.locks.ReentrantLock;

public class Milionario {
    private static Bank banco;

    private static class Deposita implements Runnable{
        public void run() {
            int value = 100;
            for (int i = 0; i < 1000; i++){
                banco.deposit(value);
            }

        }
    }
    public static void main(String[] args) throws InterruptedException{
        banco = new Bank();
        int size = 10;
        Thread[] t = new Thread[size];
        for(int i = 0;i < size;i++){
            t[i] = new Thread(new Deposita());
            t[i].start();
        }

        for(int i = 0;i < size;i++){
            try{
                t[i].join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println(banco.balance());
    }
}
