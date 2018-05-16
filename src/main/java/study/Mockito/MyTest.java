package study.Mockito;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author shuwei
 * @version 创建时间：2017年4月12日 上午10:53:04
 * Mockito入门
 */
public class MyTest {
    @Test
    public void myTest() {
        /* 创建 Mock 对象 */
        List list = Mockito.mock(List.class);
        
        /* 设置预期，当调用 get(0) 方法时返回 "111" */
        Mockito.when(list.get(0)).thenReturn("111");

        Assert.assertEquals("asd", 1, 1);
        /* 设置后返回期望的结果 */
        System.out.println(list.get(0));
        /* 没有设置则返回 null */
        System.out.println(list.get(1));

        /* 对 Mock 对象设置无效 */
        list.add("12");
        list.add("123");
        /* 返回之前设置的结果 */
        System.out.println(list.get(0));
        /* 返回 null */
        System.out.println(list.get(1));
        /* size 大小为 0 */
        System.out.println(list.size());

        /* 验证操作，验证 get(0) 调用了 2 次 */
        Mockito.verify(list, Mockito.times(2)).get(0);

        /* 验证返回结果 */
        String ret = (String)list.get(0);
        Assert.assertEquals(ret, "111");
    }
}