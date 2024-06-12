package ru.otus.basic.yampolskiy.encription;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;

public class MessageEncryption {
    // Шифрование сообщения с использованием симметричного ключа AES
    public static String encryptMessage(String message, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] iv = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] encryptedBytes = cipher.doFinal(message.getBytes());
        byte[] encryptedMessageWithIv = new byte[iv.length + encryptedBytes.length];
        System.arraycopy(iv, 0, encryptedMessageWithIv, 0, iv.length);
        System.arraycopy(encryptedBytes, 0, encryptedMessageWithIv, iv.length, encryptedBytes.length);
        return Base64.getEncoder().encodeToString(encryptedMessageWithIv);
    }

    // Дешифрование сообщения с использованием симметричного ключа AES
    public static String decryptMessage(String encryptedMessage, SecretKey key) throws Exception {
        byte[] encryptedMessageWithIv = Base64.getDecoder().decode(encryptedMessage);
        byte[] iv = new byte[16];
        byte[] encryptedBytes = new byte[encryptedMessageWithIv.length - iv.length];
        System.arraycopy(encryptedMessageWithIv, 0, iv, 0, iv.length);
        System.arraycopy(encryptedMessageWithIv, iv.length, encryptedBytes, 0, encryptedBytes.length);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }
}
