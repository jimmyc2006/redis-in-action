package study.Mockito;

import java.util.List;

import org.junit.Test;
import static org.mockito.Mockito.*;
/**
 * @author shuwei
 * @version 创建时间：2017年4月12日 上午11:41:16
 * 类说明
 */
public class MockObject {
    @Test
    public void MockTest() {
        List list = mock(List.class);

        when(list.get(0)).thenReturn("hello world");

        System.out.println(list.get(0));

        System.out.println(list.size());
    }
}