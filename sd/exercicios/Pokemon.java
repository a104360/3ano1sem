import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Pokemon implements Manager {
    public final int R = 5;
    public int raids = 0;
    public int maxMinPlayers;
    public List<String> playersBuffer = new ArrayList<>();
    public Set<Raid> onGoingRaid = new HashSet<Raid>(R);
    ReentrantLock l = new ReentrantLock();
    Condition p = l.newCondition();

    /*
     * name : nome do player que está a entrar
     * minPlayers : mínimo de players para a raid começar
     */
    public Raid join(String name,int minPlayers) throws InterruptedException{
        this.l.lock();
        try{
            List<String> players = this.playersBuffer;
            players.add(name);
            this.maxMinPlayers = Math.max(minPlayers, maxMinPlayers); // Deter

            if(this.maxMinPlayers == players.size()){
                this.p.signalAll();
                this.maxMinPlayers = 0;
                this.playersBuffer = new ArrayList<>();
            } else {
                while(players == this.playersBuffer){
                    this.p.await();
                }
            }
            //if raids
            return new Charizard(players);//new Charizard(players);

        } catch (InterruptedException e){
            e.printStackTrace();
        } finally {
            this.l.unlock();
        }
        return null;
    }

    public void leaveRaid(Raid r){
        this.l.lock();
        try{
            r.leave();
            if(r.);
        } finally {

        }
    }
}
