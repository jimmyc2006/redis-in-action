package study.clean.code.arg;

import java.util.Iterator;
import java.util.NoSuchElementException;

import study.clean.code.arg.ArgsException.ErrorCode;

/**
 * @author shuwei
 * @version 创建时间：2017年4月18日 上午8:38:25
 * 类说明
 */
public class IntegerArgumentMarshaler implements ArgumentMarshaler{
    private int intValue = 0;
    
    @Override
    public void set(Iterator<String> currentArgument) throws ArgsException {
        String parameter = null;
        try{
            parameter = currentArgument.next();
            intValue = Integer.parseInt(parameter);
        } catch (NoSuchElementException e) {
            throw new ArgsException(ErrorCode.MISSING_INTEGER);
        } catch (NumberFormatException e) {
            throw new ArgsException(ErrorCode.INVALID_INTEGER, parameter);
        }
    }

    public static int getValue(ArgumentMarshaler am) {
        if(am != null && am instanceof IntegerArgumentMarshaler) {
            return ((IntegerArgumentMarshaler) am).intValue;
        } else {
            return 0;
        }
    }
}
