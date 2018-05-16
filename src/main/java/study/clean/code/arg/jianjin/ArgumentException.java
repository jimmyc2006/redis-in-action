package study.clean.code.arg.jianjin;
/**
 * @author shuwei
 * @version 创建时间：2017年5月9日 上午9:03:07
 * 类说明
 */
public class ArgumentException extends RuntimeException{

    public ArgumentException() {
        super();
    }

    public ArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArgumentException(String message) {
        super(message);
    }

    public ArgumentException(Throwable cause) {
        super(cause);
    }
    
}