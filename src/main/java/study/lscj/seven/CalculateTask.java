package study.lscj.seven;

import java.util.concurrent.RecursiveTask;

/**
 * @author shuwei
 * @version 创建时间：2017年6月10日 上午10:26:21 类说明
 */
public class CalculateTask extends RecursiveTask<Double> {
    private static final long serialVersionUID = 1L;
    
    private int threshold = 20;
    public static final double step = 0.01;
    private double start;
    private double end;

    public CalculateTask(double start, double end, int threshold) {
        this.start = start;
        this.end = end;
        this.threshold = threshold;
    }
    
    private double calculateArea(double l, double h) {
        return l * h;
    }
    
    @Override
    protected Double compute() {
        double sum = 0;
        boolean canCompute = (end - start) <= threshold;
        if (canCompute) {
            for (double i = start + step; i <= end; i = i + step) {
                double s = calculateArea(step, 1/i);
                sum += s;
            }
        } else {
            // 如果任务大于阈值，就分裂成两个子任务计算
            double middle = (start + end) / 2;
            CalculateTask leftTask = new CalculateTask(start, middle, threshold);
            CalculateTask rightTask = new CalculateTask(middle + step, end, threshold);

            // 执行子任务
            leftTask.fork();
            rightTask.fork();

            // 等待任务执行结束合并其结果
            double leftResult = leftTask.join();
            double rightResult = rightTask.join();

            // 合并子任务
            sum = leftResult + rightResult;
        }
        return sum;
    }
}
