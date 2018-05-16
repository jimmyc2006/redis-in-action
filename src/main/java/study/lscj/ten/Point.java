package study.lscj.ten;

import java.util.concurrent.locks.StampedLock;

/**
 * @author shuwei
 * @version 创建时间：2017年7月2日 上午11:31:55
 * 类说明
 */
public class Point {
    private double x,y;
    private final StampedLock sl = new StampedLock();
    
    void move(double deltaX, double deltaY) {
        long stamp = sl.writeLock();
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            sl.unlockWrite(stamp);
        }
    }
    
    double distanceFromOrigin() {
        long stamp = sl.tryOptimisticRead();
        double currentX = x, currentY = y;
        if(!sl.validate(stamp)) {
            stamp = sl.readLock();
            try {
                currentX = x;
                currentY = y;
            } finally {
                sl.unlockRead(stamp);
            }
        }
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }
}
