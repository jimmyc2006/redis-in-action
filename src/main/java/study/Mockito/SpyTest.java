package study.Mockito;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import static org.mockito.Mockito.*;
/**
 * @author shuwei
 * @version 创建时间：2017年4月12日 上午11:46:18
 * 类说明
 */
public class SpyTest {
    @Test  
    public void spyTest() {  
        List list = new LinkedList();  
        List spy = spy(list);  
        // optionally, you can stub out some methods:  
        when(spy.size()).thenReturn(100);  
        // using the spy calls real methods  
        spy.add("one");  
        spy.add("two");  
        // prints "one" - the first element of a list  
        System.out.println(spy.get(0));  
        // size() method was stubbed - 100 is printed  
        System.out.println(spy.size());  
        // optionally, you can verify  
        verify(spy).add("one");  
        verify(spy).add("two");  
    }
}
