package com.project.demo.api.encrypt.infrastructure;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Util {

    public static String encode(String planText) {

        return Base64.getEncoder().encodeToString(planText.getBytes(StandardCharsets.UTF_8));
    }

    public static String decode(String base64Text) throws Exception {

        byte[] decodeBytes = Base64.getDecoder().decode(base64Text);

        if ( !isValidUtf8(decodeBytes) )
            throw new IllegalArgumentException("Base64 디코딩 실패 - 입력값이 올바르지 않습니다.");

        return new String(decodeBytes, StandardCharsets.UTF_8);
    }

    private static boolean isValidUtf8(byte[] bytes) {
        CharsetDecoder decoder = StandardCharsets.UTF_8
                                    .newDecoder()
                                    .onMalformedInput(CodingErrorAction.REPORT)
                                    .onUnmappableCharacter(CodingErrorAction.REPORT);

        try {
            decoder.decode(ByteBuffer.wrap(bytes));
            return true;
        } catch (CharacterCodingException e) {
            return false;
        }

    }
}
