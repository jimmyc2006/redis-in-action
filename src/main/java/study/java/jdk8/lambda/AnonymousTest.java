package study.java.jdk8.lambda;

import java.util.Collections;
import java.util.List;

/**
 * @author shuwei
 * @version 创建时间：2017年6月24日 下午2:09:17 学习jdk8的匿名内部类的lambda表达式
 */
public class AnonymousTest {
    
    public static void test(AnonymousInterface ai) {
        System.out.println(ai.test(1, 2));
    }
    public static void connectToService(){}
    public static void sendNotification(){}
    
    public static void main(String[] args) {
        List<String> ls = Collections.emptyList();
        System.out.println(ls.size());
        test((a, b)->{return a + b;});
        new Thread(() -> {connectToService();sendNotification();}).start();
    }
}
