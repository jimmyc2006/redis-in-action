package study.java.base.serializablestu;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * @author shuwei
 * @version 创建时间：2017年6月12日 下午9:47:17 类说明
 */
public class DataSpecial implements Serializable {
    private int a;
    private long b;
    private String name;

    public DataSpecial(int a, long b, String name) {
        this.a = a;
        this.b = b;
        this.name = name;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        System.out.println("www");
        out.writeInt(a);
        out.writeLong(b);
        out.writeUTF(name);
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        System.out.println("rrr");
        a = in.readInt();
        b = in.readLong();
        name = in.readUTF();
    }

    private void readObjectNoData() throws ObjectStreamException {
        System.out.println("OOO");
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        DataSpecial ds = new DataSpecial(1, 2, "shuwei");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("d.out"));    //输出流保存的文件名为 my.out ；ObjectOutputStream能把Object输出成Byte流
        oos.writeObject(ds);
        oos.flush();  //缓冲流 
        oos.close();
        
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("d.out"));
        DataSpecial d1 = (DataSpecial) ois.readObject();
        System.out.println(d1);
        System.out.println(ois.readObject());
        ois.close();
    }

    @Override
    public String toString() {
        return "DataSpecial [a=" + a + ", b=" + b + ", name=" + name + "]";
    }
}