package study.clean.code;

import junit.framework.Assert;

/**
 * @author shuwei
 * @version 创建时间：2017年4月1日 上午8:53:10 第一版
 * 代码整洁之道p238
 * 优化建议：
 * 1.删除所有成员变量的f前缀
 * 2.在compact函数开始处,有一个为封装的函数判断
 * 3.修改后compact函数中的变量名与成员变量名重名的解决
 * 4.否定式比肯定是难理解,修改为canBeCompacted()
 * 5.修改函数名compact为formatCompactComparison
 * 6.拆出一个名为compactExpectedAndActual
 * 7.修改findCommonPrefix和findCommonSuffix，返回前缀和后缀值
 * 8.仔细检查findCommonSufix，其中藏了个时序性耦合,需要将prefixIndex做成find的参数
 * 9.第8条的做法不合适，采取别的方式
 * 
 */
public class ComparisonCompactorOld {
    private static final String ELLIPSIS = "...";
    private static final String DELTA_END = "]";
    private static final String DELTA_START = "[";

    private int fContextLength;
    private String fExpected;
    private String fActual;
    private int fPrefix;
    private int fSuffix;

    public ComparisonCompactorOld(int contextLength, String expected, String actual) {
        fContextLength = contextLength;
        fExpected = expected;
        fActual = actual;
    }

    public String compact(String message) {
        if (fExpected == null || fActual == null || areStringsEqual()) {
            return Assert.format(message, fExpected, fActual);
        }
        findCommonPrefix();
        findCommonSuffix();
        String expected = compactString(fExpected);
        String actual = compactString(fActual);
        return Assert.format(message, expected, actual);
    }
    
    private String compactString(String source) {
        String result = DELTA_START + source.substring(fPrefix, source.length() - fSuffix + 1) + DELTA_END;
        if(fPrefix > 0) {
            result = computeCommonPrefix() + result;
        }
        if(fSuffix > 0) {
            result = result + computeCommonSuffix();
        }
        return result;
    }
    
    // 找到公共的前缀
    private void findCommonPrefix() {
        this.fPrefix = 0;
        int end = Math.min(fExpected.length(), fActual.length());
        for(; fPrefix < end; fPrefix++) {
            if(fExpected.charAt(fPrefix) != fActual.charAt(fPrefix)) {
                break;
            }
        }
    }
    
    private void findCommonSuffix() {
        int expectedSuffix = fExpected.length() - 1;
        int actualSuffix = fActual.length() - 1;
        for(; 
                actualSuffix >= this.fPrefix && expectedSuffix >= fPrefix;
                actualSuffix--, expectedSuffix--) {
            if(fExpected.charAt(expectedSuffix) != fActual.charAt(actualSuffix)) {
                break;
            }
        }
        this.fSuffix = fExpected.length() - expectedSuffix;
    }
    
    private String computeCommonPrefix() {
        return (fPrefix > fContextLength ? ELLIPSIS : "") + 
                fExpected.substring(Math.max(0, fPrefix - fContextLength), fPrefix);
    }
    
    private String computeCommonSuffix() {
        int end = Math.min(fExpected.length() - fSuffix + 1 + fContextLength,
                fExpected.length());
        return fExpected.substring(fExpected.length() - fSuffix + 1, end) + 
                (fExpected.length() - fSuffix + 1 < fExpected.length() - 
                        fContextLength ? ELLIPSIS : "");
    
    }
    private boolean areStringsEqual() {
        return this.fExpected.equals(this.fActual);
    }
}