package com.project.demo.common.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class MsgUtil {

    @Autowired
    private MessageSource messageSource;

    /**
     * 메시지 가져오기 (파라미터 없이)
     * @param key 메시지 키
     * @return 메시지 내용
     */
    public String getMessage(String key) {
        return messageSource.getMessage(key, null, Locale.getDefault());
    }

    /**
     * 메시지 가져오기 (파라미터 포함)
     * @param key 메시지 키
     * @param params 메시지에 삽입할 파라미터
     * @return 메시지 내용
     */
    public String getMessage(String key, Object... params) {
        return messageSource.getMessage(key, params, Locale.getDefault());
    }

    /**
     * 특정 Locale을 사용하여 메시지 가져오기
     * @param key 메시지 키
     * @param locale 사용할 Locale
     * @return 메시지 내용
     */
    public String getMessage(String key, Locale locale) {
        return messageSource.getMessage(key, null, locale);
    }

    /**
     * 특정 Locale과 파라미터를 사용하여 메시지 가져오기
     * @param key 메시지 키
     * @param params 메시지에 삽입할 파라미터
     * @param locale 사용할 Locale
     * @return 메시지 내용
     */
    public String getMessage(String key, Object[] params, Locale locale) {
        return messageSource.getMessage(key, params, locale);
    }
}
