package study.clean.code.arg;

import org.junit.Assert;
import org.junit.Test;


/**
 * @author shuwei
 * @version 创建时间：2017年4月26日 上午9:09:30 类说明
 */
public class ArgsTest {
    @Test
    public void testCreateWithNoSchemaOrArguments() throws ArgsException {
        Args2 args = new Args2("", new String[0]);
        Assert.assertEquals(0, args.cardinality());
        Assert.assertTrue(args.isValid());
    }

    /*
    @Test
    public void testWithNoSchemaButWithOneArgument() {
        try {
            new Args1("", new String[] {"-x"});
            Assert.fail();
        } catch (ArgsException e) {
            Assert.assertEquals(ArgsException.ErrorCode.UNEXPECTED_ARGUMENT, e.getErrorCode());
            Assert.assertEquals('x', e.getErrorArgumentId());
        }
    }
    */
    @Test
    public void testWithNoSchemaButWithOneArgument() throws ArgsException {
        Args2 args = new Args2("", new String[] {"-x"});
        Assert.assertEquals(0, args.cardinality());
        Assert.assertTrue(!args.isValid());
    }
    /*
    @Test
    public void testWithNoSchemaButWithMultipleArguments() {
        try {
            new Args1("", new String[]{"-x", "-y"});
            Assert.fail();
        } catch (ArgsException e) {
            Assert.assertEquals(ArgsException.ErrorCode.UNEXPECTED_ARGUMENT, e.getErrorCode());
            Assert.assertEquals('x', e.getErrorArgumentId());
        }
    }
    @Test
    public void testNonLetterSchema() {
        try {
            new Args1("*", new String[]{});
            Assert.fail("Args constructor should have throw exception");
        } catch (ArgsException e) {
            Assert.assertEquals(ArgsException.ErrorCode.INVALID_ARGUMENT_NAME, e.getErrorCode());
            Assert.assertEquals('*', e.getErrorArgumentId());
        }
    }
    
    @Test
    public void testInvalidArgumentFormat() {
        try{
            new Args1("f~", new String[]{});
            Assert.fail("Args constructor should have throws exception");
        } catch (ArgsException e) {
            Assert.assertEquals(ArgsException.ErrorCode.INVALID_ARGUMENT_NAME, e.getErrorCode());
            Assert.assertEquals('f', e.getErrorArgumentId());
        }
    }
     */
    @Test
    public void testSimpleBooleanPresent() throws ArgsException {
        Args2 args = new Args2("xb", new String[]{"-x"});
        Assert.assertEquals(1, args.cardinality());
        Assert.assertEquals(true, args.getBoolean('x'));
        Assert.assertTrue(args.isValid());
    }
    
    @Test
    public void testSimpleBooleanAndStringPresent() throws ArgsException {
        Args2 args = new Args2("xb, ys", new String[]{"-xy", "abc "});
        Assert.assertEquals(2, args.cardinality());
        Assert.assertTrue(args.has('x'));
        Assert.assertTrue(args.has('y'));
        Assert.assertTrue(args.getBoolean('x'));
        Assert.assertTrue(!args.getBoolean('y'));
        Assert.assertNull(args.getString('x'));
        Assert.assertEquals("abc", args.getString('y'));
        Assert.assertTrue(args.isValid());
    }
    
    @Test
    public void testLessValue() throws ArgsException {
        Args2 args = new Args2("xb,ys", new String[]{"-x"});
        Assert.assertEquals(1, args.cardinality());
        Assert.assertTrue(args.getBoolean('x'));
        Assert.assertNull(args.getString('y'));
        Assert.assertTrue(args.isValid());
    }
    /*
    public void testSimpleStringPresent() {
        Args1 args = new Args1("x*", new String[]{"-x", "param"});
        Assert.assertEquals(1, args.cardinality());
        Assert.assertTrue(args.has('x'));
        Assert.assertEquals("param", args.getString('x'));
    }
    
    public void testMissiongStringArgument() {
        try {
            new Args1("x*", new String[]{"-x"});
            Assert.fail();
        } catch (ArgsException e) {
            Assert.assertEquals(ArgsException.ErrorCode.MISSING_STRING, e.getErrorCode());
            Assert.assertEquals('x', e.getErrorArgumentId());
        }
    }
    public void testSpacesinFormat() throws ArgsException {
        Args1 args = new Args1("x, y", new String[]{"-xy"});
        Assert.assertEquals(2, args.cardinality());
        Assert.assertTrue(args.has('x'));
        Assert.assertTrue(args.has('y'));
    }
    */
    @Test
    public void testNonLetterSchema() throws ArgsException {
        Args2 args = new Args2("*", new String[]{});
        Assert.assertEquals(0, args.cardinality());
        Assert.assertTrue(args.isValid());
    }
    @Test
    public void testMoreLetterschema() throws ArgsException {
        Args2 args = new Args2("ab,bb,cdb", new String[]{"-abcd"});
        Assert.assertTrue(args.getBoolean('a'));
        Assert.assertTrue(args.getBoolean('b'));
        Assert.assertEquals(2, args.cardinality());
        Assert.assertTrue(!args.getBoolean('c'));
        Assert.assertTrue(!args.getBoolean('d'));
        Assert.assertTrue(!args.isValid());
    }
    @Test
    public void testMoreLetterSchema2() throws ArgsException {
        Args2 args = new Args2("ab,bb,cb", new String[]{"-abc"});
        Assert.assertEquals(3, args.cardinality());
        Assert.assertTrue(args.getBoolean('a'));
        Assert.assertTrue(args.getBoolean('b'));
        Assert.assertTrue(args.getBoolean('c'));
        Assert.assertTrue(args.isValid());
    }
}