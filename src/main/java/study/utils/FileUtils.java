package study.utils;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author shuwei
 * @version 创建时间：2017年6月25日 下午3:09:29
 * 操作文件的工具类
 */

public class FileUtils {
    /**
     * 将object写入指定文件
     * @param fileName
     * @param object
     * @throws IOException
     */
    public static void witeObject(String fileName, Object object) throws IOException {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try{
            fos = new FileOutputStream(fileName);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
        } finally {
            close(oos);
            close(fos);
        }
        
    }
    
    /**
     * 从指定文件中读取object
     * @param fileName
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object readObject(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            fis = new FileInputStream(fileName);
            ois = new ObjectInputStream(fis);
            return ois.readObject();
        } finally {
            close(fis);
            close(ois);
        }
    }
    
    public static void close(Closeable closeable) {
        try{
            if (closeable != null) {
                closeable.close();
            }
        } catch(Exception e) {
            // pass
        }
    }
}