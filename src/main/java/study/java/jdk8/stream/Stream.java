package study.java.jdk8.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Assert;

/**
 * @author shuwei
 * @version 创建时间：2017年4月19日 下午3:14:38 类说明
 */
public class Stream {
    public static Article getFirstJavaArticle(List<Article> articles) {
        for (Article article : articles) {
            if (article.getTags().contains("Java")) {
                return article;
            }
        }
        return null;
    }

    public static Article getFirstJavaArticle2(List<Article> articles) {
        return articles.stream().filter(article -> article.getTags().contains("Java")).findFirst().get();
    }

    public static List<Article> getAllJavaArticles(List<Article> articles) {
        List<Article> result = new ArrayList<>();
        for (Article article : articles) {
            if (article.getTags().contains("Java")) {
                result.add(article);
            }
        }
        return result;
    }

    public static List<Article> getAllJavaArticles2(List<Article> articles) {
        return articles.stream().filter(article -> article.getTags().contains("Java")).collect(Collectors.toList());
    }

    public static Map<String, List<Article>> groupByAuthor(List<Article> articles) {
        Map<String, List<Article>> result = new HashMap<>();
        for (Article article : articles) {
            if (result.containsKey(article.getAuthor())) {
                result.get(article.getAuthor()).add(article);
            } else {
                ArrayList<Article> articlesList = new ArrayList<>();
                articlesList.add(article);
                result.put(article.getAuthor(), articlesList);
            }
        }
        return result;
    }
    
    public static Map<String, List<Article>> groupByAuthor2(List<Article> articles) {  
        return articles.stream()
            .collect(Collectors.groupingBy(Article::getAuthor));
    }

    public static void main(String[] args) {
        List<Article> testList = new ArrayList<Article>();
        testList.add(new Article("a1", "sw1", Arrays.asList("aaa", "bbb", "ccc")));
        testList.add(new Article("a2", "sw2", Arrays.asList("aaa", "bbb", "ccc")));
        testList.add(new Article("a3", "sw3", Arrays.asList("aaa", "bbb", "ccc")));
        testList.add(new Article("a6", "sw6", Arrays.asList("xxx", "Java", "yyy")));
        testList.add(new Article("a4", "sw4", Arrays.asList("aaa", "bbb", "ccc")));
        testList.add(new Article("a5", "sw5", Arrays.asList("aaa", "bbb", "ccc")));
        testList.add(new Article("a7", "sw7", Arrays.asList("aaa", "Java", "ddd")));
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            // List<Article> a = Stream.getAllJavaArticles(testList);
            Map<String, List<Article>> a = Stream.groupByAuthor(testList);
            /*
             * Assert.assertEquals("a6", a.getTitle()); Assert.assertEquals("sw6", a.getAuthor());
             * Assert.assertTrue(a.getTags().contains("Java"));
             * Assert.assertTrue(a.getTags().contains("xxx"));
             * Assert.assertTrue(a.getTags().contains("yyy")); Assert.assertTrue(a.getTags().size()
             * == 3);
             */
        }
        System.out.println("method1 comsume: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            // Article a = Stream.getFirstJavaArticle2(testList);
            // List<Article> a = Stream.getAllJavaArticles2(testList);
            Map<String, List<Article>> a = Stream.groupByAuthor2(testList);
            /*
             * Assert.assertEquals("a6", a.getTitle()); Assert.assertEquals("sw6", a.getAuthor());
             * Assert.assertTrue(a.getTags().contains("Java"));
             * Assert.assertTrue(a.getTags().contains("xxx"));
             * Assert.assertTrue(a.getTags().contains("yyy")); Assert.assertTrue(a.getTags().size()
             * == 3);
             */
        }
        System.out.println("method2 comsume: " + (System.currentTimeMillis() - start));
    }
}
