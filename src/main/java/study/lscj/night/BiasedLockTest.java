package study.lscj.night;

import java.util.List;
import java.util.Vector;

public class BiasedLockTest {
    public static List<Integer> numberList = new Vector<Integer>();

    public static void test() {
        long start = System.currentTimeMillis();
        int count = 0;
        int startnum = 0;
        while (count < 10000000) {
            numberList.add(startnum);
            startnum += 2;
            count++;
        }
        System.out.println("耗时:" + (System.currentTimeMillis() - start));
    }

    public static void main(String[] args) {
        test();
    }
}
