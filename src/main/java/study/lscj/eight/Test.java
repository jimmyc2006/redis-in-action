package study.lscj.eight;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author shuwei
 * @version 创建时间：2017年6月16日 下午3:13:13 类说明
 */
public class Test {
    public static void main(String[] args) throws IOException {
        RandomAccessFile fromFile = new RandomAccessFile("a.txt", "rw");
        FileChannel fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("toFile.txt", "rw");
        FileChannel toChannel = toFile.getChannel();

        long position = 0;
        long count = fromChannel.size();

        toChannel.transferFrom(fromChannel, position, count);
        toChannel.close();
        fromChannel.close();
    }
}
