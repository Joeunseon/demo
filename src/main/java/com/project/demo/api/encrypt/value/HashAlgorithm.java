package com.project.demo.api.encrypt.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HashAlgorithm {

    SHA256("SHA-256"),
    SHA512("SHA-512"),
    MD5("MD5");

    private final String algorithm;
}
