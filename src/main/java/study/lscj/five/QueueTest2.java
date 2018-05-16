package study.lscj.five;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import study.lscj.five.QueueTest.Producer;

public class QueueTest2 {

    static class Producer implements Runnable {
        private Queue queue;
        private CountDownLatch cdl;

        public Producer(Queue queue, CountDownLatch cdl) {
            this.queue = queue;
            this.cdl = cdl;
        }

        public void produce() {
            for (int i = 0; i < 500000; i++) {
                queue.offer(i);
            }
        }

        @Override
        public void run() {
            produce();
            cdl.countDown();
            
        }
    }

    static class Consumer implements Runnable {
        private Queue queue;
        private CountDownLatch cdl;
        
        public static volatile boolean produceAllOver = false;  

        public Consumer(Queue queue, CountDownLatch cdl) {
            this.queue = queue;
            this.cdl = cdl;
        }

        public void comsume() {
            while (!(queue.isEmpty() && produceAllOver)) {
                queue.poll();
            }
        }

        @Override
        public void run() {
            comsume();
            cdl.countDown();
        }

        public static void test(int sum, Queue queue) throws InterruptedException {
            CountDownLatch cdlP = new CountDownLatch(sum);
            ExecutorService esP = Executors.newFixedThreadPool(sum);
            long start = System.currentTimeMillis();
            for (int i = 0; i < sum; i++) {
                esP.submit(new Producer(queue, cdlP));
            }
            
            CountDownLatch cdlC = new CountDownLatch(sum);
            ExecutorService esC = Executors.newFixedThreadPool(sum);
            for (int i = 0; i < sum; i++) {
                esC.submit(new Consumer(queue, cdlC));
            }
            cdlP.await();
            esP.shutdown();
            Consumer.produceAllOver = true;
            cdlC.await();
            esC.shutdown();
            long end = System.currentTimeMillis();
            System.out.println(queue.getClass().getName() + "耗时" + (end - start));
        }

        public static void main(String[] args) throws InterruptedException {
            LinkedBlockingQueue lbq = new LinkedBlockingQueue();
            ConcurrentLinkedQueue clq = new ConcurrentLinkedQueue();
            test(8, lbq);
            Consumer.produceAllOver = false;
            test(8, clq);
        }
    }
}
