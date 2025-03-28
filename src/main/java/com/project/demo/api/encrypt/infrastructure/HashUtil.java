package com.project.demo.api.encrypt.infrastructure;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class HashUtil {

    public static String encrypt(String plainText, String algorithm) throws Exception {

        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] hashedBytes = md.digest(plainText.getBytes(StandardCharsets.UTF_8));
    
            return bytesToHex(hashedBytes);
        } catch (Exception e) {
            throw new IllegalArgumentException("Hash 암호화 실패 - 입력값이 올바르지 않습니다.");
        }
    }

    public static boolean matches(String plainText, String targetHash, String algorithm) throws Exception {

        String hashStr = encrypt(plainText, algorithm);

        return hashStr.equalsIgnoreCase(targetHash);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}
