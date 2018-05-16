package study.clean.code.arg;

import java.util.Iterator;
import java.util.NoSuchElementException;

import study.clean.code.arg.ArgsException.ErrorCode;

/**
 * @author shuwei
 * @version 创建时间：2017年4月18日 上午8:38:25
 * 类说明
 */
public class StringArgumentMarshaler implements ArgumentMarshaler{
    private String stringValue = "";
    
    @Override
    public void set(Iterator<String> currentArgument) throws ArgsException {
        try{
            stringValue = currentArgument.next();
        } catch (NoSuchElementException e) {
            throw new ArgsException(ErrorCode.MISSING_STRING);
        }
    }

    public static String getValue(ArgumentMarshaler am) {
        if(am != null && am instanceof StringArgumentMarshaler) {
            return ((StringArgumentMarshaler) am).stringValue;
        } else {
            return "";
        }
    }
}
