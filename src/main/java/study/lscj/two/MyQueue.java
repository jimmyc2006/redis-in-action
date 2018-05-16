package study.lscj.two;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyQueue {
    private List<String> queue = new LinkedList<String>();
    int maxSize = 10;
    
    public void add(String element) {
        synchronized(queue) {
            if(queue.size() == maxSize) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                }
            }
            queue.add(element);
            queue.notify();
        }
    }
    public String get() throws InterruptedException {
        synchronized(queue) {
            if(queue.size() == 0) {
                queue.wait();
            }
            String result = queue.remove(0);
            queue.notify();
            return result;
        }
    }
    
    static class ProduceThread extends Thread {
        private MyQueue mq;
        private String name;
        
        public ProduceThread(MyQueue mq, String name) {
            this.mq = mq;
            this.name = name;
        }

        @Override
        public void run() {
            for(int i = 0; i < 100; i++) {
                mq.add(name + i);
            }
        }
    }
    static class ConsumeThread extends Thread {
        private MyQueue mq;
        private String name;
        
        public ConsumeThread(MyQueue mq, String name) {
            this.mq = mq;
            this.name = name;
        }

        @Override
        public void run() {
            while(true) {
                try{
                    System.out.println(name + "消费" + mq.get());
                } catch(Exception e) {
                    break;
                }
            }
        }
    }
    public static void main(String[] args) {
        int produceCount = 5;
        int consumerCount = 2;
        MyQueue mq = new MyQueue();
        for(int i = 0; i < produceCount; i++) {
            Thread t = new ProduceThread(mq, "producer(" + i + ")");
            t.start();
        }
        List<Thread> consumers = new ArrayList<Thread>();
        for(int i = 0; i < consumerCount; i++) {
            Thread t = new ConsumeThread(mq, "consumer（" + i + ")");
            consumers.add(t);
            t.start();
        }
        try {
            Thread.sleep(1000 * 5);
        } catch (InterruptedException e) {
        }
        for (Thread t : consumers) {
            t.interrupt();
        }
    }
}