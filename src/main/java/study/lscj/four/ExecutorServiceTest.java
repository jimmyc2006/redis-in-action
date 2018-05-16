package study.lscj.four;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author shuwei
 * @version 创建时间：2017年6月2日 下午4:36:43
 * 类说明
 */
public class ExecutorServiceTest extends Thread {

    @Override
    public void run() {
        for(int i = 0; i < 10; i++) {
            System.out.println(i);
        }
        try {
            Thread.sleep(1000 * 60 * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(5);
        for(int i = 0; i < 5; i++) {
            es.submit(new ExecutorServiceTest());
        }
        es.shutdown();
        System.out.println("over");
    }
    
}
