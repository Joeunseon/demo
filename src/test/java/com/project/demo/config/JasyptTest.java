package com.project.demo.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class JasyptTest {

    @Autowired
    private JasyptConfig jasyptConfig;

    @Test
    public void testEncryptAndPrint() {
        String plainText = "plain-text";
        jasyptConfig.encryptAndPrint(plainText);
    }
}
