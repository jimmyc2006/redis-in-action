package study.Mockito;

import static org.mockito.Mockito.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
/**
 * @author shuwei
 * @version 创建时间：2017年4月12日 上午11:42:26
 * 类说明
 */
public class MockObjectReally {
    @Test
    public void MockTest() {
        /* 创建真实对象 */
        List list = new LinkedList();
        List spy = spy(list);

        spy.add("hello");

        when(spy.get(0)).thenReturn("hello world");

        System.out.println(spy.get(0));
    }
}