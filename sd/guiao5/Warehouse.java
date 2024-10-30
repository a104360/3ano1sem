import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;



class Warehouse {
    private Map<String, Product> map =  new HashMap<String, Product>();
    private ReentrantLock l = new ReentrantLock();
    


    private class Product { 
        int quantity = 0; 
        ReentrantLock pLock = new ReentrantLock(); 
        private Condition empty = l.newCondition();
    }

    private Product get(String item) {
        Product p = map.get(item);
        if (p != null) return p;
        p = new Product();
        map.put(item, p);
        return p;
    }

    public void supply(String item, int quantity) {
        l.lock();
        try{
            Product p = get(item);
            p.quantity += quantity;
            p.empty.signal();
        } finally {
            l.unlock();
        }
    }

    public void consume(Set<String> items) {
        this.l.lock();
        try{
            for (String s : items){
                Product p = this.get(s);
                while (p.quantity == 0){
                    p.empty.await();
                }
                //p.pLock.lock();
                //try{
                    p.quantity--;
                    //} finally {
                        //    p.pLock.unlock();
                        //}
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.l.unlock();
        }
    }

    public void consumeCoop(Set<String> itens) {
        this.l.lock();
        try{
            Iterator<String> it = itens.iterator();
            while(it.hasNext()){
                Product p = get(it.next());
                while(p.quantity == 0){
                    p.empty.await();
                    it = itens.iterator();
                }
            }

            for(String s : itens){
                Product p = get(s);
                p.quantity--;
            }

        } catch(InterruptedException e){
            e.printStackTrace();
        } finally {
            this.l.unlock();
        }
    }
}
