import java.security.DigestException;
import java.security.NoSuchAlgorithmException;

/**
 * @author shuwei
 * @version 创建时间：2017年6月7日 下午8:20:19 test
 */
public class Test extends Thread {

    public static void main(String[] args) throws NoSuchAlgorithmException, DigestException {
        System.out.println((131072 + 262144 + 262144 + 262144 + 32768 + 196608.0)/(1024*1024.0) * 500 + 169);
    }
    
    public void testDecodePer() {
        int count = 10000000;
        long start = System.currentTimeMillis();
        for(int i = 0; i < count; i++) {
            hex2Bytes("615e263838");
        }
        System.out.println(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();
        for(int i = 0; i < count; i++) {
            hexString2Bytes("615e263838");
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    public static String bytes2hex03(byte[] bytes) {
        final String HEX = "0123456789abcdef";
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            // 取出这个字节的高4位，然后与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
            sb.append(HEX.charAt((b >> 4) & 0x0f));
            // 取出这个字节的低位，与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
            sb.append(HEX.charAt(b & 0x0f));
        }
        return sb.toString();
    }
    
    public static String string2Hex(String src) {
        return bytes2hex03(src.getBytes());
    }
    
    public static String string2HexString(String strPart) {  
        StringBuffer hexString = new StringBuffer();  
        for (int i = 0; i < strPart.length(); i++) {  
            int ch = (int) strPart.charAt(i);  
            String strHex = Integer.toHexString(ch);  
            hexString.append(strHex);  
        }  
        return hexString.toString();  
    }

    /**
     * 将16进制的字符串转化为原字符串
     * 
     * @param hexString 16进制字符串
     * @return
     */
    public static byte[] hex2Bytes(String hexString) {
        byte[] result = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i += 2) {
            int leftValue = Character.digit(hexString.charAt(i), 16);
            int rightvalue = Character.digit(hexString.charAt(i + 1), 16);
            result[i / 2] = (byte) ((leftValue << 4) | rightvalue);
        }
        return result;
    }

    public static byte[] hexString2Bytes(String src) {
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            ret[i] = Integer.valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return ret;
    }
}
