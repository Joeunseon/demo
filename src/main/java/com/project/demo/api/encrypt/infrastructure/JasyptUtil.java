package com.project.demo.api.encrypt.infrastructure;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class JasyptUtil {

    public static String encrypt(String plainText, String password, String algorithm) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

        encryptor.setPassword(password);
        encryptor.setAlgorithm(algorithm);

        return encryptor.encrypt(plainText);
    }

    public static String decrypt(String cipherText, String password, String algorithm) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

        encryptor.setPassword(password);
        encryptor.setAlgorithm(algorithm);

        return encryptor.decrypt(cipherText);
    }
}
