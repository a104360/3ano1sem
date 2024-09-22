import java.lang.Runnable;
import java.lang.Thread;

class ThreadPrinter implements Runnable{
    public void run(){
        System.out.println("Thread : " + Thread.currentThread().getId());
    }
}


public class PrintThread{
    public static void main(String[] args) throws InterruptedException{
        System.out.println("Inicio");

        Thread t = new Thread(new ThreadPrinter());
        Thread c = new Thread(new ThreadPrinter());
        t.start();
        t.join();
        c.start();
        c.join();
    }
}
