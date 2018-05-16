package study.clean.code;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author shuwei
 * @version 创建时间：2017年4月11日 上午8:13:32
 */
public class ComparisonCompactorTest {
    @Test
    public void testMessage() {
        String failure = new ComparisonCompactor(0, "b", "c").formatCompactComparison("a");
        Assert.assertTrue("a expected:<[b]> but was:<[c]>".equals(failure));
    }
    @Test
    public void testStartSame() {
        String failure = new ComparisonCompactor(1, "ba", "bc").formatCompactComparison(null);
        Assert.assertTrue("expected:<b[a]> but was:<b[c]>".equals(failure));
    }
    @Test
    public void testEndSame() {
        String failure = new ComparisonCompactor(1, "ab", "cb").formatCompactComparison(null);
        Assert.assertTrue("expected:<[a]b> but was:<[c]b>".equals(failure));
    }
    @Test
    public void testSame() {
        String failure = new ComparisonCompactor(1, "ab", "ab").formatCompactComparison(null);
        Assert.assertTrue("expected:<ab> but was:<ab>".equals(failure));
    }
    @Test
    public void testNoContextStartAndEndSame() {
        String failure = new ComparisonCompactor(0, "abc", "adc").formatCompactComparison(null);
        Assert.assertTrue("expected:<...[b]...> but was:<...[d]...>".equals(failure));
    }
    @Test
    public void testStartAndEndContext() {
        String failure = new ComparisonCompactor(1, "abc", "adc").formatCompactComparison(null);
        Assert.assertTrue("expected:<a[b]c> but was:<a[d]c>".equals(failure));
    }
    @Test
    public void testStartAndEndContextWithEllipses() {
        String failure = new ComparisonCompactor(1, "abcde", "abfde").formatCompactComparison(null);
        Assert.assertTrue("expected:<...b[c]d...> but was:<...b[f]d...>".equals(failure));
    }
    @Test
    public void testComparisonErrorStartSameComplete() {
        String failure = new ComparisonCompactor(2, "ab", "abc").formatCompactComparison(null);
        Assert.assertTrue("expected:<ab[]> but was:<ab[c]>".equals(failure));
    }
    @Test
    public void testComparisonErrorEndSameComplete() {
        String failure = new ComparisonCompactor(0, "bc", "abc").formatCompactComparison(null);
        Assert.assertTrue("expected:<[]...> but was:<[a]...>".equals(failure));
    }
    @Test
    public void testComparisonErrorEndSameCompleteContext() {
        String failure = new ComparisonCompactor(2, "bc", "abc").formatCompactComparison(null);
        Assert.assertTrue("expected:<[]bc> but was:<[a]bc>".equals(failure));
    }
    @Test
    public void testComparisonErrorOverlapingMatches() {
        String failure = new ComparisonCompactor(0, "abc", "abbc").formatCompactComparison(null);
        Assert.assertTrue("expected:<...[]...> but was:<...[b]...>".equals(failure));
    }
    @Test
    public void testComparisonErrorOverlapingMatchesContext() {
        String failure = new ComparisonCompactor(2, "abc", "abbc").formatCompactComparison(null);
        Assert.assertTrue("expected:<ab[]c> but was:<ab[b]c>".equals(failure));
    }
    @Test
    public void testComparisonErrorOverlapingMatches2() {
        String failure = new ComparisonCompactor(0, "abcdde", "abcde").formatCompactComparison(null);
        Assert.assertTrue("expected:<...[d]...> but was:<...[]...>".equals(failure));
    }
    @Test
    public void testComparisonErrorOverlapingMatches2Context() {
        String failure = new ComparisonCompactor(2, "abcdde", "abcde").formatCompactComparison(null);
        Assert.assertTrue("expected:<...cd[d]e> but was:<...cd[]e>".equals(failure));
    }
    @Test
    public void testComparisonErrorWithActualNull() {
        String failure = new ComparisonCompactor(0, "a", null).formatCompactComparison(null);
        Assert.assertTrue("expected:<a> but was:<null>".equals(failure));
    }
    @Test
    public void testComparisonErrorWithActualNullContext() {
        String failure = new ComparisonCompactor(2, "a", null).formatCompactComparison(null);
        Assert.assertTrue("expected:<a> but was:<null>".equals(failure));
    }
    @Test
    public void testComparisonErrorWithExpectedNull() {
        String failure = new ComparisonCompactor(0, null, "a").formatCompactComparison(null);
        Assert.assertTrue("expected:<null> but was:<a>".equals(failure));
    }
    @Test
    public void testComparisonErrorWithExpectedNullContext() {
        String failure = new ComparisonCompactor(2, null, "a").formatCompactComparison(null);
        Assert.assertTrue("expected:<null> but was:<a>".equals(failure));
    }
    @Test
    public void testBug609972() {
        String failure = new ComparisonCompactor(10, "S&P500", "0").formatCompactComparison(null);
        Assert.assertTrue("expected:<[S&P50]0> but was:<[]0>".equals(failure));
    }
}