package study.lscj.ten.homework;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDeadLock extends Thread {
    private ReentrantLock reentrantLock1;
    private ReentrantLock reentrantLock2;
    
    public ReentrantLockDeadLock(String name, ReentrantLock reentrantLock1, ReentrantLock reentrantLock2) {
        super(name);
        this.reentrantLock1 = reentrantLock1;
        this.reentrantLock2 = reentrantLock2;
    }

    @Override
    public void run() {
        reentrantLock1.lock();
        try {
            Thread.sleep(500);
            reentrantLock2.lock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        ReentrantLock rt1 = new ReentrantLock();
        ReentrantLock rt2 = new ReentrantLock();
        new ReentrantLockDeadLock("t1", rt1, rt2).start();
        new ReentrantLockDeadLock("t2", rt2, rt1).start();
    }
}
