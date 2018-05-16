package study.clean.code.arg;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author shuwei
 * @version 创建时间：2017年4月26日 上午8:44:07 
 * 最早版本，只支持boolean类型
 */

public class Args1 {
    private String schema;
    private String[] args;
    private boolean valid;
    private Set<Character> unexpectedArguments = new TreeSet<Character>();
    private Map<Character, Boolean> booleanArgs = new HashMap<Character, Boolean>();
    private int numberOfArguments = 0;
    
    public Args1(String schema, String[] args) throws ArgsException {
        this.schema = schema;
        this.args = args;
        valid = parse();
    }
    
    public boolean isValid() {
        return valid;
    }
    
    private boolean parse() {
        if(schema.length() == 0 && args.length == 0) {
            return true;
        }
        parseSchema();
        parseArguments();
        return unexpectedArguments.size() == 0;
    }
    
    private boolean parseSchema() {
        for(String element : schema.split(",")) {
            parseSchemaElement(element);
        }
        return true;
    }
    
    private void parseSchemaElement(String element) {
        if(element.length() == 1) {
            parseBooleanSchemaElement(element);
        }
    }
    
    private void parseBooleanSchemaElement(String element) {
        char c = element.charAt(0);
        if(Character.isLetter(c)) {
            booleanArgs.put(c, false);
        }
    }
    private boolean parseArguments() {
        for(String arg : args) {
            parseArgument(arg);
        }
        return true;
    }
    private void parseArgument(String arg) {
        if(arg.startsWith("-")) {
            parseElements(arg);
        }
    }
    private void parseElements(String arg) {
        for(int i = 1; i < arg.length(); i++) {
            parseElement(arg.charAt(i));
        }
    }
    private void parseElement(char argChar) {
        if(isBoolean(argChar)) {
            numberOfArguments++;
            setBooleanArg(argChar, true);
        } else {
            unexpectedArguments.add(argChar);
        }
    }
    private boolean isBoolean(char argChar) {
        return booleanArgs.containsKey(argChar);
    }
    private void setBooleanArg(char argChar, boolean value) {
        booleanArgs.put(argChar,  value);
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
        Boolean value = booleanArgs.get(arg);
        if(value == null) {
            return false;
        } else {
            return value;
        }
    }
    public boolean has(char c) {
        return booleanArgs.containsKey(c);
    }
}