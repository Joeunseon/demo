package com.project.demo.api.encrypt.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JasyptAlgorithm {

    PBE_WITH_MD5_AND_DES("PBEWithMD5AndDES", "기본 (MD5 + DES)"),
    PBE_WITH_SHA1_AND_DESEDE("PBEWithSHA1AndDESede", "SHA1 + Triple DES"),
    PBE_WITH_SHA256_AND_AES_128("PBEWithSHA256And128BitAES-CBC-BC", "SHA256 + AES 128 (BC)"),
    PBE_WITH_SHA256_AND_AES_256("PBEWithSHA256And256BitAES-CBC-BC", "SHA256 + AES 256 (BC)");

    private final String algorithm;
    private final String description;
}
