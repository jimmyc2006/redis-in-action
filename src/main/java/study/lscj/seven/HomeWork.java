package study.lscj.seven;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * @author shuwei
 * @version 创建时间：2017年6月10日 上午9:58:46
 * 类说明
 */
public class HomeWork {
    // 基于精度问题，forkNum应该能被upperLimit整除
    public static double calculateUseForkJoin(int start, int end, int threshold) {
        ForkJoinPool forkjoinPool = new ForkJoinPool();
        CalculateTask task = new CalculateTask(1, 100, threshold);
        //执行一个任务
        Future<Double> result = forkjoinPool.submit(task);
        try {
            return result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        double result = calculateUseForkJoin(1, 100, 100);
        System.out.println("单线程计算耗时[" + (System.currentTimeMillis() - start) + "]结果[" + result +"]");
        start = System.currentTimeMillis();
        result = calculateUseForkJoin(1, 100, 20);
        System.out.println("多线程计算耗时[" + (System.currentTimeMillis() - start) + "]结果[" + result +"]");
    }
}