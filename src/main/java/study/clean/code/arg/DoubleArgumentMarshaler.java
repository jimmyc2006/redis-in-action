package study.clean.code.arg;

import java.util.Iterator;
import java.util.NoSuchElementException;

import study.clean.code.arg.ArgsException.ErrorCode;

/**
 * @author shuwei
 * @version 创建时间：2017年4月18日 上午8:38:25
 * 类说明
 */
public class DoubleArgumentMarshaler implements ArgumentMarshaler{
    private double doubleValue = 0.0;
    
    @Override
    public void set(Iterator<String> currentArgument) throws ArgsException {
        String parameter = null;
        try{
            parameter = currentArgument.next();
            doubleValue = Double.parseDouble(parameter);
        } catch (NoSuchElementException e) {
            throw new ArgsException(ErrorCode.MISSING_DOUBLE);
        } catch (NumberFormatException e) {
            throw new ArgsException(ErrorCode.INVALID_DOUBLE, parameter);
        }
    }

    public static double getValue(ArgumentMarshaler am) {
        if(am != null && am instanceof DoubleArgumentMarshaler) {
            return ((DoubleArgumentMarshaler) am).doubleValue;
        } else {
            return 0;
        }
    }
}
