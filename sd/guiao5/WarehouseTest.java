import java.util.*;

public class WarehouseTest {

    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();

        // Teste de supply
        warehouse.supply("item1", 10);
        warehouse.supply("item2", 5);

        // Teste de consume
        Set<String> items = new HashSet<>();
        items.add("item1");
        items.add("item2");

        Thread t1 = new Thread(() -> {
            warehouse.consume(items);
            System.out.println("Items consumed by Thread 1");
        });

        Thread t2 = new Thread(() -> {
            warehouse.consumeCoop(items);
            System.out.println("Items consumed cooperatively by Thread 2");
        });

        Thread t3 = new Thread(()->{
            warehouse.consumeCoop(items);
            System.out.println("Items consumed cooperatively by Thread 3");
        });
        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
