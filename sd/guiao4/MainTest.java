import java.lang.Runnable;
import java.lang.Thread;

public class MainTest extends Thread{

    private static class Carros implements Runnable{
        private static Barrier b;
        private int n;
        public Carros(Barrier barrier ,int n) {
            this.n = n;
            b = barrier;
        }

        public void run(){
            System.out.println("Carro " + this.n + "chegou à barreira");
            try{
                b.await();
                System.out.println("Carro " + this.n + "saiu da barreira");
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        int n = 10;
        Barrier b = new Barrier(10);
        Thread[] t = new Thread[n];
        System.out.println("Vão entrar " + n + " carros");
        for(int i = 0;i < 10;i++){
            t[i] = new Thread(new Carros(b,i));
            t[i].start();
        }
        for(int i = 0;i < 10;i++){
            try{
                t[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
