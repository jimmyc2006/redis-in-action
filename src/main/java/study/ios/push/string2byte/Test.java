package study.ios.push.string2byte;

import javapns.devices.exceptions.InvalidDeviceTokenFormatException;

/**
 * @author shuwei
 * @version 创建时间：2017年4月23日 上午10:46:06
 * 类说明
 */
public class Test {
    public static void main(String[] args) throws InvalidDeviceTokenFormatException {
        String content = "4729E4EA3C7EABC737E0D50B5BB050CC3A101CEDD72F3FE52459EC8BAEDCBD76";
        System.out.println(content.getBytes().length + ":" + content.getBytes());
        System.out.println(string2Byte(content).length + ":" + string2Byte(content));
    }
    public static byte[] string2Byte(String content) throws InvalidDeviceTokenFormatException {
        byte[] bytes = new byte[content.length() / 2];
        content = content.toUpperCase();
        int j = 0;
        try {
            for (int i = 0; i < content.length(); i += 2) {
                String t = content.substring(i, i + 2);
                int tmp = Integer.parseInt(t, 16);
                bytes[j++] = (byte) tmp;
            }
        } catch (NumberFormatException e1) {
            throw new InvalidDeviceTokenFormatException(content, e1.getMessage());
        }
        return bytes;
    }
}
