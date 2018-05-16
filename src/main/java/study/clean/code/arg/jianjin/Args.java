package study.clean.code.arg.jianjin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import study.clean.code.arg.ArgsException;

/**
 * @author shuwei
 * @version 创建时间：2017年4月26日 上午8:44:07 
 * 渐进版
 */

public class Args {
    private String schema;
    private boolean valid;
    private Set<Character> unexpectedArguments = new TreeSet<Character>();
    private Set<String> errorSchema = new TreeSet<String>();
    private Map<Character, ArgumentMarshaler> marshalers = new HashMap<>();
    private int numberOfArguments = 0;
    
    private List<String> args;
    
    public Args(String schema, String[] args) throws ArgsException {
        this.schema = schema;
        this.args = Arrays.asList(args);
        valid = parse();
    }
    
    public boolean isValid() {
        return valid;
    }
    
    private boolean parse() {
        if(schema.length() == 0 && args.size() == 0) {
            return true;
        }
        parseSchema();
        parseArguments();
        return unexpectedArguments.size() == 0;
    }
    
    private boolean parseSchema() {
        for(String element : schema.split(",")) {
            parseSchemaElement(element.trim());
        }
        return true;
    }
    
    private void parseSchemaElement(String element) {
        if(element.length() == 2) {
                parseXSchemaElement(element);
        } else {
            errorSchema.add(element);
        }
    }
    
    private void parseXSchemaElement(String element) {
        char c = element.charAt(0);
        char type = element.charAt(1);
        if(Character.isLetter(c)) {
            if(type == 'b') {
                marshalers.put(c, new BooleanArgumentMarshaler());
            } else if(type == 's') {
                marshalers.put(c, new StringArgumentMarshaler());
            } else if(type == 'i') {
                marshalers.put(c, new IntegerArgumentMarshaler());
            } else if(type == 'd') {
                marshalers.put(c, new DoubleArgumentMarshaler());
            } else {
                errorSchema.add(element);
            }
        }
    }
    
    private boolean parseArguments() {
        for(String arg : args) {
            parseArgument(arg.trim());
        }
        return true;
    }
    private void parseArgument(String arg) {
        if(arg.startsWith("-") && arg.length() > 1) {
            parseElement(arg.substring(1));
        }
    }
    /*
    private void parseElements(String arg) {
        char argName = arg.charAt(0);
        parseElement(arg.charAt(i));
    }
    */
    private void parseElement(String arg2) {
        Iterator<String> iterator = this.args.iterator();
        for(char schema : arg2.toCharArray()) {
            if(iterator.hasNext()) {
                ArgumentMarshaler am = marshalers.get(schema);
                if(am != null) {
                    am.set(iterator);
                }
            }
        }
    }
    
    public int cardinality() {
        return numberOfArguments;
    }
    public String usage() {
        if(schema.length() > 0) {
            return "-[" + schema +"]";
        } else {
            return "";
        }
    }
    public String errorMessage() {
        if(unexpectedArguments.size() > 0) {
            return unexpectedArgumentMessage();
        } else {
            return "";
        }
    }
    private String unexpectedArgumentMessage() {
        StringBuffer message = new StringBuffer("Argument(s) -");
        for(char c : unexpectedArguments) {
            message.append(c);
        }
        message.append(" unexpected.");
        return message.toString();
    }
    public boolean getBoolean(char arg) {
        ArgumentMarshaler value = marshalers.get(arg);
        try {
            return (boolean) value.get();
        } catch (Exception e) {
            return false;
        }
    }
    public String getString(char arg) {
        ArgumentMarshaler am = marshalers.get(arg);
        try {
            return (String) am.get();
        } catch (Exception e) {
            return "";
        }
    }
    // TODO 这里0不合适，应该有异常
    public int getInteger(char arg) {
        ArgumentMarshaler am = marshalers.get(arg);
        if(am == null) {
            throw new ArgumentException("ArgumentNotExist");
        } else {
            if(am instanceof IntegerArgumentMarshaler) {
               return (int) am.get(); 
            } else {
                throw new ArgumentException("NotIngegerArgumentType");
            }
        }
    }
    public boolean has(char c) {
        return marshalers.containsKey(c) || marshalers.containsKey(c);
    }
    
    // 渐进添加类
    private abstract class ArgumentMarshaler {
        public abstract void set(Iterator<String> args);
        public abstract Object get();
    }
    private class BooleanArgumentMarshaler extends ArgumentMarshaler {
        private boolean booleanValue;
        @Override
        public void set(Iterator<String> args) {
            booleanValue = true;
        }
        @Override
        public Object get() {
            return booleanValue;
        }
    }
    private class StringArgumentMarshaler extends ArgumentMarshaler {
        private String stringValue;
        @Override
        public void set(Iterator<String> args) {
            this.stringValue = args.next();
        }

        @Override
        public Object get() {
            return this.stringValue == null ? "" : stringValue;
        }
    }
    private class IntegerArgumentMarshaler extends ArgumentMarshaler {
        private int integerValue;
        @Override
        public void set(Iterator<String> args) {
            this.integerValue = Integer.parseInt(args.next());
        }

        @Override
        public Object get() {
            return this.integerValue;
        }
    }
    private class DoubleArgumentMarshaler extends ArgumentMarshaler {
        private double doubleValue;
        @Override
        public void set(Iterator<String> args) {
            this.doubleValue = Double.parseDouble(args.next());
        }

        @Override
        public Object get() {
            return this.doubleValue;
        }
    }
}