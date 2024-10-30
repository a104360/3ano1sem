package readWriteLocks;


import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
class Bank {

    private ReentrantLock mainLock = new ReentrantLock();

    private static class Account {
        public ReentrantLock aLock = new ReentrantLock();
        private int balance;
        Account(int balance) { this.balance = balance; }
        int balance() {
            this.aLock.lock();
            try{
                return balance;
            } finally {
                this.aLock.unlock();
            }
        }
        boolean deposit(int value) {
            this.aLock.lock();
            try{
                balance += value;
                return true;
            } finally {
                this.aLock.unlock();
            }
        }
        boolean withdraw(int value) {
            this.aLock.lock();
            try{
                if (value > balance)
                    return false;
                balance -= value;
                return true;
            } finally {
                this.aLock.unlock();
            }
        }
    }

    private Map<Integer, Account> map = new HashMap<Integer, Account>();
    private int nextId = 0;

    // create account and return account id
    public int createAccount(int balance) {
        Account c = new Account(balance);
        this.mainLock.lock();
        try{
            int id = nextId;
            nextId += 1;
            map.put(id, c);
            return id;
        } finally {
            this.mainLock.unlock();
        }
    }

    // close account and return balance, or 0 if no such account
    public int closeAccount(int id) {
        this.mainLock.lock();
        try{
            Account c = map.remove(id);
            if (c == null)
                return 0;
            return c.balance();
        } finally {
            this.mainLock.unlock();
        }
    }

    // account balance; 0 if no such account
    public int balance(int id) {
        this.mainLock.lock();
        try{
            Account c = map.get(id);
            if (c == null)
                return 0;
            return c.balance();
        } finally {
            this.mainLock.unlock();
        }
    }

    // deposit; fails if no such account
    public boolean deposit(int id, int value) {
        this.mainLock.lock();
        try{
            Account c = map.get(id);
            if (c == null)
                return false;
            return c.deposit(value);
        } finally {
            this.mainLock.unlock();
        }
    }

    // withdraw; fails if no such account or insufficient balance
    public boolean withdraw(int id, int value) {
        this.mainLock.lock();
        try{
            Account c = map.get(id);
            if (c == null)
                return false;
            return c.withdraw(value);
        } finally {
            this.mainLock.unlock();
        }
    }

    // transfer value between accounts;
    // fails if either account does not exist or insufficient balance
    public boolean transfer(int from, int to, int value) {
        Account cfrom, cto;
        this.mainLock.lock();
        try{
            cfrom = map.get(from);
            cto = map.get(to);
            if (cfrom == null || cto ==  null)
                return false;
            return cfrom.withdraw(value) && cto.deposit(value);
        } finally {
            this.mainLock.unlock();
        }
    }

    // sum of balances in set of accounts; 0 if some does not exist
    public int totalBalance(int[] ids) {
        int total = 0;
        for (int i : ids) {
            this.mainLock.lock();
            try{
            Account c = map.get(i);
            if (c == null)
                return 0;
            total += c.balance();
            }finally {
                this.mainLock.unlock();
            }
        }
        return total;
    }

    public int totalBalance(){
        int totalBalance = 0;
        this.mainLock.lock(); // write lock
        for(Map.Entry<Integer,Account> entry : this.map.entrySet()){
            Account c = entry.getValue();
            c.aLock.lock(); // 
            //totalBalance += c.balance();
        }
        this.mainLock.unlock(); // write unlock
        for(Map.Entry<Integer,Account> entry : this.map.entrySet()){
            totalBalance += entry.getValue().balance();
            entry.getValue().aLock.unlock();
        }
        return totalBalance;
    }

}
