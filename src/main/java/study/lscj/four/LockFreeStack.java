package study.lscj.four;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author shuwei
 *
 * 开始自己写了一个，没写好，比较粗糙，后来看到网上这个。把代码读懂后觉得这个甚好，所以借鉴了一下。
 * 测试代码完全自己写的
 */
public class LockFreeStack<V> {

    private AtomicReference<Node<V>> head = new AtomicReference<Node<V>>(null);
    private AtomicInteger size = new AtomicInteger(0);

    private static class Node<V> {
        public V value;
        AtomicReference<Node<V>> next;

        public Node(V value, Node<V> next) {
            this.value = value;
            this.next = new AtomicReference<Node<V>>(next);
        }
    }

    public V pop() {
        Node<V> oldHead = null;
        Node<V> next = null;

        do {
            oldHead = head.get();
            if (oldHead == null) {
                return null;
            }
            next = oldHead.next.get();
        } while (!head.compareAndSet(oldHead, next));

        size.getAndDecrement();
        return oldHead.value;
    }

    public void push(V value) {
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        // 创建一个新的节点
        Node<V> newNode = new Node<V>(value, null);
        Node<V> oldHead;
        do {
            // 获取老的节点
            oldHead = head.get();
            // 将新的节点的下一个节点指向老的节点,这样在pop的时候，才能把第二个节点拿到第一个
            newNode.next.set(oldHead);
        } while (!head.compareAndSet(oldHead, newNode));
        size.getAndIncrement();
    }

    public int size() {
        return size.get();
    }

    public boolean isEmpty() {
        return head.get() == null;
    }
   static class ProducerThread extends Thread {
        private LockFreeStack lfs;
        int producerCount = 0;
        int produceSum = 0;
        private CountDownLatch cdl;
        public ProducerThread(LockFreeStack lfs, CountDownLatch cdl) {
            this.lfs = lfs;
            this.cdl = cdl;
        }
        @Override
        public void run() {
            for(int i = 0; i < 10000; i++) {
                lfs.push(i);
                producerCount++;
                produceSum += i;
            }
            cdl.countDown();
        }
    }
    static class ConsumerThread extends Thread {
        // 所有消费者线程执行完毕的时候修改状态
        public volatile static boolean isProducerOver = false;
        
        private LockFreeStack lfs;
        int consumerCount = 0;
        int consumerSum = 0;
        private CountDownLatch cdl;
        
        public ConsumerThread(LockFreeStack lfs, CountDownLatch cdl) {
            this.lfs = lfs;
            this.cdl = cdl;
        }
        @Override
        public void run() {
            while(true) {
                Object objValue = lfs.pop();
                if(objValue == null) {
                    if(isProducerOver) {
                        break;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    int intValue = (int) objValue;
                    consumerCount++;
                    consumerSum+= intValue;
                }
            }
            this.cdl.countDown();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        LockFreeStack lfs = new LockFreeStack();
        int producerNum = 10;
        int consumerNum = 5;
        CountDownLatch cdlP = new CountDownLatch(producerNum);
        List<ProducerThread> pList = new ArrayList<>();
        for(int i = 0; i < producerNum; i++) {
            ProducerThread pt = new ProducerThread(lfs, cdlP);
            pt.start();
            pList.add(pt);
            
        }
        CountDownLatch cdlC = new CountDownLatch(consumerNum);
        List<ConsumerThread> cList = new ArrayList<>();
        for(int i = 0; i < consumerNum; i++) {
            ConsumerThread ct = new ConsumerThread(lfs, cdlC);
            ct.start();
            cList.add(ct);
        }
        System.out.println("开始等待生产全结束");
        cdlP.await();
        ConsumerThread.isProducerOver = true;
        System.out.println("开始等待消费全结束");
        cdlC.await();
        System.out.println("运行完成，开始计算");
        int pCount = 0;
        int pSum = 0;
        for(ProducerThread ele : pList) {
            pCount += ele.producerCount;
            pSum += ele.produceSum;
        }
        System.out.println("共生产次数[" + pCount + "]总数[" + pSum +"]");
        int cCount = 0;
        int cSum = 0;
        for(ConsumerThread ele : cList) {
            cCount += ele.consumerCount;
            cSum += ele.consumerSum;
        }
        System.out.println("共消费次数[" + cCount +"]总数[" + cSum +"]");
    }
}