package study.clean.code;

import junit.framework.Assert;

/**
 * @author shuwei
 * @version 创建时间：2017年4月1日 上午8:53:10 第一版
 * 代码整洁之道p238
 * 优化建议：
 * 1.删除所有成员变量的f前缀
 * 2.在compact函数开始处,有一个未封装的函数判断
 * 3.修改后compact函数中的变量名与成员变量名重名的解决
 * 4.否定式比肯定是难理解,修改为canBeCompacted()
 * 5.修改函数名compact为formatCompactComparison
 * 6.拆出一个名为compactExpectedAndActual
 * 7.修改findCommonPrefix和findCommonSuffix，返回前缀和后缀值
 * 8.仔细检查findCommonSufix，其中藏了个时序性耦合,需要将prefixIndex做成find的参数
 * 9.第8条的做法不合适，采取别的方式
 * 
 */
public class ComparisonCompactor {
    private static final String ELLIPSIS = "...";
    private static final String DELTA_END = "]";
    private static final String DELTA_START = "[";

    private int contextLength;
    private String expected;
    private String actual;
    private int prefixLength;
    private int suffixLength;
    private String compactExpected;
    private String compactActual;
    
    public ComparisonCompactor(int contextLength, String expected, String actual) {
        this.contextLength = contextLength;
        this.expected = expected;
        this.actual = actual;
    }

    public String formatCompactComparison(String message) {
        if (canBeCompacted()) {
            compactExpectedAndActual();
            return Assert.format(message, compactExpected, compactActual);
        } else {
            return Assert.format(message, expected, actual);
        }
    }
    
    private boolean canBeCompacted() {
        return !(expected == null || actual == null || areStringsEqual());
    }
    
    private void compactExpectedAndActual() {
        findCommonPrefixAndSuffix();
        compactExpected = compactString(expected);
        compactActual = compactString(actual);
    }
    
    private void findCommonPrefixAndSuffix() {
        findCommonPrefix();
        suffixLength = 0;
        for(; !suffixOverlapsPrefix(suffixLength); suffixLength++) {
            if(charFromEnd(expected, suffixLength) != charFromEnd(actual, suffixLength)) {
                break;
            }
        }
    }
    private char charFromEnd(String s, int i) {
        return s.charAt(s.length() - i - 1);
    }
    private boolean suffixOverlapsPrefix(int suffixLength) {
        return actual.length() - suffixLength <= prefixLength ||
                expected.length() - suffixLength <= prefixLength;
    }
    
    private String compactString(String source) {
        String result = DELTA_START + source.substring(prefixLength, source.length() - suffixLength) + DELTA_END;
        if(prefixLength > 0) {
            result = computeCommonPrefix() + result;
        }
        if(suffixLength > 0) {
            result = result + computeCommonSuffix();
        }
        return result;
    }
    
    // 找到公共的前缀
    private void findCommonPrefix() {
        prefixLength = 0;
        int end = Math.min(expected.length(), actual.length());
        for(; prefixLength < end; prefixLength++) {
            if(expected.charAt(prefixLength) != actual.charAt(prefixLength)) {
                break;
            }
        }
    }
    
    private String computeCommonPrefix() {
        return (this.prefixLength > contextLength ? ELLIPSIS : "") + 
                expected.substring(Math.max(0, this.prefixLength - contextLength), this.prefixLength);
    }
    
    private String computeCommonSuffix() {
        int end = Math.min(expected.length() - this.suffixLength + contextLength,
                expected.length());
        return expected.substring(expected.length() - this.suffixLength, end) + 
                (expected.length() - this.suffixLength < expected.length() - 
                        contextLength ? ELLIPSIS : "");
    
    }
    private boolean areStringsEqual() {
        return this.expected.equals(this.actual);
    }
}