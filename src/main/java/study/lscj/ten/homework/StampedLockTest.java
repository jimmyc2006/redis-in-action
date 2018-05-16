package study.lscj.ten.homework;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StampedLockTest{
    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();
        int readerCount = 20;
        List<PointReader> readers = new ArrayList<>(readerCount);
        Point point = new Point();
        for(int i = 0; i < readerCount; i++) {
            PointReader pr = new PointReader(point);
            readers.add(pr);
            es.execute(pr);
        }
        PointWritter pw = new PointWritter(point);
        es.execute(pw);
        Thread.sleep(1000 * 10);
        for (PointReader ele : readers) {
            ele.setOver(true);
        }
        pw.setOver(true);
        long readTimes = 0;
        for(PointReader ele : readers) {
            readTimes += ele.getReadCount();
        }
        System.out.println("20秒内, 1个写线程，20读取线程");
        System.out.println("总读取次数:" + readTimes);
        System.out.println("总写入次数:" + pw.getWriteCount());
        es.shutdown();
    }
}
class PointReader extends Thread {
    private long readCount = 0;
    private volatile boolean isOver = false;
    private Point point;
    
    public PointReader(Point point) {
        this.point = point;
    }

    @Override
    public void run() {
        while(!isOver) {
            point.distanceFromOrigin();
            readCount++;
        }
    }

    public long getReadCount() {
        return readCount;
    }

    public void setOver(boolean isOver) {
        this.isOver = isOver;
    }
}
class PointWritter extends Thread {
    private long writeCount = 0;
    private volatile boolean isOver = false;
    private Point point;
    
    public PointWritter(Point point) {
        this.point = point;
    }

    @Override
    public void run() {
        while(!isOver) {
            point.move(1, 1);
            writeCount++;
        }
    }

    public void setOver(boolean isOver) {
        this.isOver = isOver;
    }

    public long getWriteCount() {
        return writeCount;
    }
}