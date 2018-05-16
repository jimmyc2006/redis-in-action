package study.lscj.six;

import java.util.concurrent.ExecutorService;

public class CaughtExceptionInPool {
    
    static class ExceptionThread implements Runnable {
        @Override
        public void run() {
            throw new RuntimeException("catch me");
        }
    }
    public static void main(String[] args) {
        ExecutorService es = ThreadPoolFactory.newFixedThreadPool(3);
        for(int i = 0; i < 3; i++) {
            es.submit(new ExceptionThread());
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        es.shutdown();
    }
}
