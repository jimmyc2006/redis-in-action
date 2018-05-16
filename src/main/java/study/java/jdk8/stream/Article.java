package study.java.jdk8.stream;

import java.util.List;

/**
 * @author shuwei
 * @version 创建时间：2017年4月19日 下午3:15:03 类说明
 */
public class Article {
    private final String title;
    private final String author;
    private final List<String> tags;

    public Article(String title, String author, List<String> tags) {
        this.title = title;
        this.author = author;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public List<String> getTags() {
        return tags;
    }
}
