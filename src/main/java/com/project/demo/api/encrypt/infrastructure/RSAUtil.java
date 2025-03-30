package com.project.demo.api.encrypt.infrastructure;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class RSAUtil {

    public static String encrypt(String plainText, String publicKeyPem) throws Exception {
        PublicKey publicKey = getPublicKeyFromPem(publicKeyPem);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(encrypted);
    }

    private static PublicKey getPublicKeyFromPem(String pem) throws Exception {
        String cleanPem = pem.replaceAll("\\s", "");

        byte[] decode = Base64.getDecoder().decode(cleanPem);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decode);
        return KeyFactory.getInstance("RSA").generatePublic(keySpec);
    }
}