package study.clean.code.arg;

import java.util.Iterator;

/**
 * @author shuwei
 * @version 创建时间：2017年4月18日 上午8:38:25
 * 类说明
 */
public class BooleanArgumentMarshaler implements ArgumentMarshaler{
    private boolean booleanValue = false;
    
    @Override
    public void set(Iterator<String> currentArgument) throws ArgsException {
        booleanValue = true;
    }

    public static boolean getValue(ArgumentMarshaler am) {
        if(am != null && am instanceof BooleanArgumentMarshaler) {
            return ((BooleanArgumentMarshaler) am).booleanValue;
        } else {
            return false;
        }
    }
}
