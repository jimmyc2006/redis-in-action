package study.lscj.five;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueTest {

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

        public Consumer(Queue queue, CountDownLatch cdl) {
            this.queue = queue;
            this.cdl = cdl;
        }

        public void comsume() {
            while (!queue.isEmpty()) {
                queue.poll();
            }
        }

        @Override
        public void run() {
            comsume();
            cdl.countDown();
        }

        public static void testPorducer(int sum, Queue queue) throws InterruptedException {
            CountDownLatch cdl = new CountDownLatch(sum);
            ExecutorService es = Executors.newFixedThreadPool(sum);
            long start = System.currentTimeMillis();
            for (int i = 0; i < sum; i++) {
                es.submit(new Producer(queue, cdl));
            }
            cdl.await();
            long end = System.currentTimeMillis();
            es.shutdown();
            System.out.println(queue.getClass().getName() + "生产" + sum + "个生产线程生产完毕，耗时" + (end - start));
        }
        
        public static void testConsumer(int sum, Queue queue) throws InterruptedException {
            CountDownLatch cdl = new CountDownLatch(sum);
            ExecutorService es = Executors.newFixedThreadPool(sum);
            long start = System.currentTimeMillis();
            for (int i = 0; i < sum; i++) {
                es.submit(new Consumer(queue, cdl));
            }
            cdl.await();
            long end = System.currentTimeMillis();
            es.shutdown();
            System.out.println(queue.getClass().getName() + "消费" + sum + "个生产线程生产完毕，耗时" + (end - start));
        }

        public static void main(String[] args) throws InterruptedException {
            LinkedBlockingQueue lbq = new LinkedBlockingQueue(4000000);
            ConcurrentLinkedQueue clq = new ConcurrentLinkedQueue();
            testPorducer(8, lbq);
            testPorducer(8, clq);
            testConsumer(8, lbq);
            testConsumer(8, clq);
        }
    }
}
