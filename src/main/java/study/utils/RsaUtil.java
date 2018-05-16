package study.utils;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @author shuwei
 * @version 创建时间：2017年6月25日 下午3:42:04 用于创建rsa私钥和公钥文件
 */

public class RsaUtil {
    /**
     * 创建指定bit的size的keyfile
     * 
     * @param privateKeyFileName 创建的私钥存放的文件
     * @param publicKeyFileName 创建的公钥存放的文件
     * @param keysize rsa算法的key的size
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static void generateKeyFile(String privateKeyFileName, String publicKeyFileName, int keysize)
            throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(keysize);
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        FileUtils.witeObject(privateKeyFileName, privateKey);
        FileUtils.witeObject(publicKeyFileName, publicKey);
    }

    /**
     * 从指定的私钥文件中获取私钥
     * 
     * @param privateKeyFileName
     * @return
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static RSAPrivateKey getRSAPrivateKey(String privateKeyFileName) throws ClassNotFoundException, IOException,
            NoSuchAlgorithmException {
        return (RSAPrivateKey) FileUtils.readObject(privateKeyFileName);
    }

    /**
     * 从指定的公钥文件中获取公钥
     * 
     * @param publicKeyFileName
     * @return
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static RSAPublicKey getRSAPublicKey(String publicKeyFileName) throws ClassNotFoundException, IOException,
            NoSuchAlgorithmException {
        return (RSAPublicKey) FileUtils.readObject(publicKeyFileName);
    }

    public static byte[] encrypt(RSAPublicKey publicKey, byte[] srcBytes) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        assert publicKey != null;
        // Cipher负责完成加密或解密工作，基于RSA
        Cipher cipher = Cipher.getInstance("RSA");
        // 根据公钥，对Cipher对象进行初始化
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] resultBytes = cipher.doFinal(srcBytes);
        return resultBytes;
    }

    public static byte[] encrypt(String publicKeyFileName, byte[] srcBytes) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException,
            ClassNotFoundException, IOException {
        RSAPublicKey publicKey = getRSAPublicKey(publicKeyFileName);
        return encrypt(publicKey, srcBytes);
    }

    public static byte[] decrypt(RSAPrivateKey privateKey, byte[] srcBytes) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        assert privateKey != null;
        // Cipher负责完成加密或解密工作，基于RSA
        Cipher cipher = Cipher.getInstance("RSA");
        // 根据公钥，对Cipher对象进行初始化
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] resultBytes = cipher.doFinal(srcBytes);
        return resultBytes;
    }

    public static byte[] decrypt(String privateKeyFileName, byte[] srcBytes) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException,
            ClassNotFoundException, IOException {
        RSAPrivateKey privateKey = getRSAPrivateKey(privateKeyFileName);
        return decrypt(privateKey, srcBytes);
    }
}
