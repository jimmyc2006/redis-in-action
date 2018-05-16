package study.clean.code;

import org.junit.Assert;

import study.clean.code.arg.Args;
import study.clean.code.arg.ArgsException;


/**
 * @author shuwei
 * @version 创建时间：2017年4月25日 上午8:41:14
 * 类说明
 */
public class ArgsTest {
    
    public void testCreateWithNoSchemaOrArguments() throws ArgsException {
        Args args = new Args("", new String[0]);
        // Assert.assertEquals(0, args.);
    }
}