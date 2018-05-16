package study.lscj.three;
/**
 * @author shuwei
 * @version 创建时间：2017年6月7日 下午8:20:19
 * 类说明
 */
public class Test extends Thread {
    private long testValue = 7247483647L;
    @Override
    public void run() {
        while(true) {
            if(testValue == 7247483647L){
                testValue = 8247483647L;
            } else {
                testValue = 7247483647L;
            }
        }
    }
    
    public static void main(String[] args) {
        Test t = new Test();
        t.setDaemon(true);
        t.start();
        while(true) {
            long value = t.testValue;
            if((value != 7247483647L) && (value != 8247483647L)) {
                break;
            }
        }
        System.out.println("over!~");
    }
}
