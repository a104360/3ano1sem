import java.util.concurrent.locks.ReentrantLock;

public class Operations{
    private ReentrantLock l;
    private int counter;
    private int sum;

    public Operations(){
        this.l = new ReentrantLock();
        this.counter = 0;
        this.sum = 0;
    }

    public int getSum(){
        return this.sum;
    }

    public void add(int n){
        this.l.lock();
        try{
            this.sum += n;
            this.counter++;
        } finally {
            this.l.unlock();
        }
    }
    public void sub(int n){
        this.l.lock();
        try{
            this.sum -= n;
            this.counter++;
        } finally {
            this.l.unlock();
        }
    }
    public void mul(int n){
        this.l.lock();
        try{
            this.sum *= n;
            this.counter++;
        } finally {
            this.l.unlock();
        }
    }
    public void div(int n){
        this.l.lock();
        try{
            this.sum /= n;
            this.counter++;
        } catch (ArithmeticException e){
            e.printStackTrace();
        } finally {
            this.l.unlock();
        }
    }

    public double average(){
        try{
            float average = this.sum / this.counter;
            return average;
        } catch (ArithmeticException e){
            e.printStackTrace();
        }
        return -1;
    }
}