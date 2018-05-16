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

public class Args2 {
    private String schema;
    private String[] args;
    private boolean valid;
    private Set<Character> unexpectedArguments = new TreeSet<Character>();
    private Set<String> errorSchema = new TreeSet<String>();
    private Map<Character, Boolean> booleanArgs = new HashMap<Character, Boolean>();
    private Map<Character, String> stringArgs = new HashMap<Character, String>();
    private int numberOfArguments = 0;
    private int currentArgsIndex = 1;
    
    public Args2(String schema, String[] args) throws ArgsException {
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
                booleanArgs.put(c, false);
            } else if(type == 's') {
                stringArgs.put(c, null);
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
        for(char schema : arg2.toCharArray()) {
            if(isBoolean(schema)) {
                numberOfArguments++;
                setBooleanArg(schema, true);
            } else if(isString(schema)) {
                String value = getNextStringArgument();
                if(value != null) {
                    numberOfArguments++;
                    setStringArg(schema, value);
                }
            } else {
                unexpectedArguments.add(schema);
            }
        }
    }
    private boolean isBoolean(char argChar) {
        return booleanArgs.containsKey(argChar);
    }
    private boolean isString(char argChar) {
        return stringArgs.containsKey(argChar);
    }
    private void setBooleanArg(char argChar, boolean value) {
        booleanArgs.put(argChar,  value);
    }
    private String getNextStringArgument() {
        try{
            return this.args[this.currentArgsIndex++].trim();
        } catch(Exception e) {
            // missing String argument
            return null;
        }
    }
    private void setStringArg(char argChar, String value) {
        stringArgs.put(argChar, value);
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
    public String getString(char arg) {
        return stringArgs.get(arg);
    }
    public boolean has(char c) {
        return booleanArgs.containsKey(c) || stringArgs.containsKey(c);
    }
}