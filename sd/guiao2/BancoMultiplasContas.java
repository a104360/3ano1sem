import java.util.concurrent.locks.ReentrantLock;

public class BancoMultiplasContas {

    private static class Account {
        private ReentrantLock l = new ReentrantLock();
        private int balance;
        Account (int balance) { this.balance = balance; }
        int balance () {
            this.l.lock();
            try{
                return balance;
            } finally {
                this.l.unlock();
            }
        }
        boolean deposit (int value) {
            this.l.lock();
            try{
                balance += value;
                return true;
            } finally {
                this.l.unlock();
            }
        }
        boolean withdraw (int value) {
            this.l.lock();
            try{
                if (value > balance)
                    return false;
                balance -= value;
                return true;
            } finally {
                this.l.unlock();
            }
        }
    }

    // Bank slots and vector of accounts
    private final int slots;
    private Account[] av;
    private ReentrantLock l = new ReentrantLock();

    public BancoMultiplasContas (int n) {
        slots=n;
        av=new Account[slots];
        for (int i=0; i<slots; i++)
            av[i]=new Account(0);
    }


    // Account balance
    public int balance (int id) {
            if (id < 0 || id >= slots)
                return 0;
            return av[id].balance();
    }

    // Deposit
    public boolean deposit (int id, int value) {
            if (id < 0 || id >= slots)
                return false;
            return av[id].deposit(value);
    }

    // Withdraw; fails if no such account or insufficient balance
    public boolean withdraw (int id, int value) {
            if (id < 0 || id >= slots)
                return false;
            return av[id].withdraw(value);
    }

    // Transfer
    public boolean transfer (int from, int to, int value) {
        this.l.lock();
        try{
            return this.withdraw(from,value) && this.deposit(to, value);
        } finally {
            this.l.unlock();
        }
    }

    // TotalBalance
    public int totalBalance () {
        this.l.lock();
        try{
            int total = 0;
            for(int i = 0;i < this.slots;i++){
                total += av[i].balance();
            }
            return total;
        } finally {
            this.l.unlock();
        }
    }
}

