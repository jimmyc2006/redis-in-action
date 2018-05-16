package base.java.study.codec;

import java.io.File;
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

import study.utils.FileUtils;

public class EncrypRSA {

    /**
     * 加密
     * 
     * @param publicKey
     * @param srcBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    protected byte[] encrypt(RSAPublicKey publicKey, byte[] srcBytes) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if (publicKey != null) {
            // Cipher负责完成加密或解密工作，基于RSA
            Cipher cipher = Cipher.getInstance("RSA");
            // 根据公钥，对Cipher对象进行初始化
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] resultBytes = cipher.doFinal(srcBytes);
            return resultBytes;
        }
        return null;
    }

    /**
     * 解密
     * 
     * @param privateKey
     * @param srcBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    protected byte[] decrypt(RSAPrivateKey privateKey, byte[] srcBytes) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if (privateKey != null) {
            // Cipher负责完成加密或解密工作，基于RSA
            Cipher cipher = Cipher.getInstance("RSA");
            // 根据公钥，对Cipher对象进行初始化
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] resultBytes = cipher.doFinal(srcBytes);
            return resultBytes;
        }
        return null;
    }

    /**
     * @param args
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, ClassNotFoundException, IOException {
        EncrypRSA rsa = new EncrypRSA();
        String msg = "郭XX-精品相声郭XX-精品相声郭XX";
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        
        // 用公钥加密
        byte[] srcBytes = msg.getBytes();
        byte[] resultBytes = rsa.encrypt(rsa.getRSAPublicKey(), srcBytes);

        // 用私钥解密
        byte[] decBytes = rsa.decrypt(rsa.getRSAPrivateKey(), resultBytes);

        System.out.println("明文是" + msg.getBytes().length + ":" + msg);
        System.out.println("加密后是" +  resultBytes.length + ":" + new String(resultBytes));
        System.out.println("解密后是" + decBytes.length + ":" + new String(decBytes));
    }
    
    private String privateKeyFileName = "privateKey";
    private String publicKeyFileName = "publicKey";
    
    void generateKeyPair() throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为1024位
        keyPairGen.initialize(1024);
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        FileUtils.witeObject(privateKeyFileName, privateKey);
        FileUtils.witeObject(publicKeyFileName, publicKey);
    }
    
    RSAPrivateKey getRSAPrivateKey() throws ClassNotFoundException, IOException, NoSuchAlgorithmException {
        if(!new File(privateKeyFileName).isFile()) {
            generateKeyPair(); 
        }
        return (RSAPrivateKey) FileUtils.readObject(privateKeyFileName);
    }
    
    RSAPublicKey getRSAPublicKey() throws ClassNotFoundException, IOException, NoSuchAlgorithmException {
        if(!new File(privateKeyFileName).isFile()) {
            generateKeyPair();
        }
        return (RSAPublicKey) FileUtils.readObject(publicKeyFileName);
    }
}