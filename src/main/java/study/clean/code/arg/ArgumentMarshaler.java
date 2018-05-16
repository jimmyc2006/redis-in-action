package study.clean.code.arg;

import java.util.Iterator;

/**
 * @author shuwei
 * @version 创建时间：2017年4月14日 上午9:02:22
 * 类说明
 */
public interface ArgumentMarshaler {
    void set(Iterator<String> currentArgument) throws ArgsException;
}
