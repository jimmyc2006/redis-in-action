package study.ios.zip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author shuwei
 * @version 创建时间：2017年6月22日 下午1:58:43 类说明
 */
public class GzipTest {
    public static void main(String[] args) throws IOException {
        test("这是一条聊天信息，说不同的字压缩效果可能会差一些，所以我写了这个来测试一下。");
        test("你好!");
        test("哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈");
    }

    static void test(String content) throws IOException {
        System.out.println("-------------------------------------------------");
        System.out.println("长度:" + content.getBytes("utf-8").length);
        byte[] afterZip = gzipBytes(content);
        System.out.println("压缩之后长度:" + afterZip.length);
        System.out.println("解压后的内容:" + ugzipBytes(afterZip));
    }

    static byte[] gzipBytes(String content) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPOutputStream gzipos = new GZIPOutputStream(baos);
        gzipos.write(content.getBytes("utf-8"));
        gzipos.finish();
        gzipos.close();
        byte[] afterGzip = baos.toByteArray();
        baos.close();
        return afterGzip;
    }

    static String ugzipBytes(byte[] content) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(content);
        GZIPInputStream gzis = new GZIPInputStream(bais);
        byte[] buf = new byte[1024];
        int num = -1;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((num = gzis.read(buf, 0, buf.length)) != -1) {
            bos.write(buf, 0, num);
        }
        gzis.close();
        bais.close();
        byte[] ret = bos.toByteArray();
        bos.flush();
        bos.close();
        return new String(ret, "utf-8");
    }
}
