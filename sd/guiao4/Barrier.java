import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Barrier {
    private int max;
    private int epoch;
    private int counter = 0;
    private ReentrantLock l;
    private Condition cl;

    public Barrier (int N) {
        this.max = N;
        this.l = new ReentrantLock();
        this.cl = l.newCondition();
    }

    // Method to be called by each thread
    public void await() throws InterruptedException {
        l.lock(); // Acquire the lock before accessing shared state
        try {
            int localEpoch = epoch; // Capture the current epoch
            counter++; // Increment the number of threads that have reached the barrier

            if (counter < max) {
                // Wait while the current epoch is still valid and not all threads have arrived
                while (localEpoch == epoch && counter < max) {
                    cl.await(); // Wait for signal that all threads have arrived
                }
            } else {
                // Last thread to reach the barrier signals all waiting threads
                cl.signalAll();
                // Reset for the next barrier round
                counter = 0;
                epoch++; // Move to the next round (epoch)
            }
        } finally {
            l.unlock(); // Ensure the lock is released even if an exception occurs
        }
    }
}
