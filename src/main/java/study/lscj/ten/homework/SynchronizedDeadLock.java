package study.lscj.ten.homework;

public class SynchronizedDeadLock extends Thread {
    private Object lock1;
    private Object lock2;
    
    public SynchronizedDeadLock(String name, Object lock1, Object lock2) {
        super(name);
        this.lock1 = lock1;
        this.lock2 = lock2;
    }

    @Override
    public void run() {
        synchronized(lock1) {
            try {
                Thread.sleep(500);
                synchronized(lock2) {
                    System.out.println("ok");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        Object obj1 = new Object();
        Object obj2 = new Object();
        new SynchronizedDeadLock("t1", obj1, obj2).start();
        new SynchronizedDeadLock("t2", obj2, obj1).start();
    }
}
