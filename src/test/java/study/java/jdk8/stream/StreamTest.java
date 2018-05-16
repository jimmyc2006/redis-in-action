package study.java.jdk8.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author shuwei
 * @version 创建时间：2017年4月19日 下午4:10:30
 * 类说明
 */
public class StreamTest {
    private List<Article> testList;
    
    @Before
    public void init() {
        testList = new ArrayList<Article>();
        testList.add(new Article("a1", "sw1", Arrays.asList("aaa", "bbb", "ccc")));
        testList.add(new Article("a2", "sw2", Arrays.asList("aaa", "bbb", "ccc")));
        testList.add(new Article("a3", "sw3", Arrays.asList("aaa", "bbb", "ccc")));
        testList.add(new Article("a4", "sw4", Arrays.asList("aaa", "bbb", "ccc")));
        testList.add(new Article("a5", "sw5", Arrays.asList("aaa", "bbb", "ccc")));
        testList.add(new Article("a6", "sw6", Arrays.asList("xxx", "Java", "yyy")));
        testList.add(new Article("a7", "sw7", Arrays.asList("aaa", "Java", "ddd")));
    }
    
    @Test
    public void testGetFirstJavaArticle() {
        Article a = Stream.getFirstJavaArticle(testList);
        Assert.assertEquals("a6", a.getTitle());
        Assert.assertEquals("sw6", a.getAuthor());
        Assert.assertTrue(a.getTags().contains("Java"));
        Assert.assertTrue(a.getTags().contains("xxx"));
        Assert.assertTrue(a.getTags().contains("yyy"));
        Assert.assertTrue(a.getTags().size() == 3);
    }
    
    @Test
    public void testGetFirstJavaArticle2() {
        Article a = Stream.getFirstJavaArticle2(testList);
        Assert.assertEquals("a6", a.getTitle());
        Assert.assertEquals("sw6", a.getAuthor());
        Assert.assertTrue(a.getTags().contains("Java"));
        Assert.assertTrue(a.getTags().contains("xxx"));
        Assert.assertTrue(a.getTags().contains("yyy"));
        Assert.assertTrue(a.getTags().size() == 3);
    }
    
    @Test
    public void testGetFirstJavaArticlePerformance() {
        long start = System.currentTimeMillis();
        for(int i = 0; i < 10000; i++) {
            Article a = Stream.getFirstJavaArticle(testList);
            Assert.assertEquals("a6", a.getTitle());
            Assert.assertEquals("sw6", a.getAuthor());
            Assert.assertTrue(a.getTags().contains("Java"));
            Assert.assertTrue(a.getTags().contains("xxx"));
            Assert.assertTrue(a.getTags().contains("yyy"));
            Assert.assertTrue(a.getTags().size() == 3);
        }
        System.out.println("method1 comsume: " + (System.currentTimeMillis() - start));
    }
    @Test
    public void testGetFirstJavaArticle2Performance() {
        long start = System.currentTimeMillis();
        for(int i = 0; i < 10000; i++) {
            Article a = Stream.getFirstJavaArticle2(testList);
            Assert.assertEquals("a6", a.getTitle());
            Assert.assertEquals("sw6", a.getAuthor());
            Assert.assertTrue(a.getTags().contains("Java"));
            Assert.assertTrue(a.getTags().contains("xxx"));
            Assert.assertTrue(a.getTags().contains("yyy"));
            Assert.assertTrue(a.getTags().size() == 3);
        }
        System.out.println("method2 comsume: " + (System.currentTimeMillis() - start));
    }
}
