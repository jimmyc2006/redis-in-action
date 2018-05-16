package study.clean.code.arg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import study.clean.code.arg.ArgsException.ErrorCode;

/**
 * @author shuwei
 * @version 创建时间：2017年4月18日 上午8:38:25
 * 类说明
 */
public class StringArrayArgumentMarshaler implements ArgumentMarshaler{
    private String[] stringArrayValue = null;
    
    @Override
    public void set(Iterator<String> currentArgument) throws ArgsException {
        List<String> listValue = new ArrayList<String>();
        do{
            try{
                String stringValue = currentArgument.next();
                listValue.add(stringValue);
            } catch (NoSuchElementException e) {
                throw new ArgsException(ErrorCode.MISSING_STRING);
            }
        } while (currentArgument.hasNext());
        stringArrayValue = listValue.toArray(new String[]{});
    }

    public static String[] getValue(ArgumentMarshaler am) {
        if(am != null && am instanceof StringArrayArgumentMarshaler) {
            return ((StringArrayArgumentMarshaler) am).stringArrayValue;
        } else {
            return null;
        }
    }
}
