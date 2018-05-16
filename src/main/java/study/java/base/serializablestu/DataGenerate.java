package study.java.base.serializablestu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author shuwei
 * @version 创建时间：2017年6月12日 下午9:21:50
 * 类说明
 */
public class DataGenerate implements Serializable {
    private static final long serialVersionUID = 7247714666080613254L;
    public int n;
    public DataGenerate(int n) {
        this.n = n;
    }
    public String toString(){
        return Integer.toString(n);
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.out"));    //输出流保存的文件名为 my.out ；ObjectOutputStream能把Object输出成Byte流
        DataGenerate data1 = new DataGenerate(1);
        DataGenerate data2 = new DataGenerate(2);
        DataGenerate data3 = new DataGenerate(3);
        oos.writeObject(data1);
        oos.writeObject(data2);
        oos.writeObject(data3);
        oos.flush();  //缓冲流 
        oos.close();
        
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data.out"));
        DataGenerate d1 = (DataGenerate) ois.readObject();
        System.out.println(d1);
        DataGenerate d2 = (DataGenerate) ois.readObject();
        System.out.println(d2);
        DataGenerate d3 = (DataGenerate) ois.readObject();
        System.out.println(d3);
        DataGenerate d4 = (DataGenerate)ois.readObject();
        System.out.println(d4);
        ois.close();
    }
}