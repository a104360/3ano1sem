import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class Charizard implements Raid {

    List<String> players;
    int playersOn;

    ReentrantLock l = new ReentrantLock();
    Condition p = l.newCondition();

    public Charizard(List<String> playersList){
        this.players = new ArrayList<>(playersList);
    }
    
    public List<String> players(){
        return players;
    }

    public boolean continueRaid(){
        if(this.playersOn <= 0) return false;
        return true;
    }

    public void waitStart() throws InterruptedException{
        this.l.lock();
        try{
            this.p.await();
        } finally {
            this.l.unlock();
        }
    }

    public void leave(){
        this.l.lock();
        try{
            this.playersOn--;
        } finally {
            this.l.unlock();
        }
    }
}
