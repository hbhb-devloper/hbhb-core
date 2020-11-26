package com.hbhb.core.utils;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author xiaokang
 * @since 2020-08-10
 */
public class AESCryptUtil {
    /**
     * 密钥
     */
    private static final String KEY = "1234567890ABCDEF1234567890ABCDEF";
    /**
     * 偏移量（CBC模式下必传）
     */
    private static final String IV = "0123456789ABCDEF";
    /**
     * 加密算法
     */
    private static final String ALGORITHM = "AES";
    /**
     * 算法/模式/补码方式
     */
    private static final String ALGORITHM_PROVIDER = "AES/CBC/PKCS5Padding";

    public static String encrypt(String data) {
        return encrypt(data, KEY, IV);
    }

    public static String decrypt(String data) {
        return decrypt(data, KEY, IV);
    }

    /**
     * 加密方法
     */
    public static String encrypt(String data, String key, String iv) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_PROVIDER);
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), ALGORITHM);
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);

            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密方法
     */
    public static String decrypt(String data, String key, String iv) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_PROVIDER);
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), ALGORITHM);
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(Base64.getDecoder().decode(data));
            return new String(original);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(decrypt("F44LWCdqyd4XN09T4pbKLA=="));
    }
}
