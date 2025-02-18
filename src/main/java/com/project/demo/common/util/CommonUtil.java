package com.project.demo.common.util;

import java.util.Enumeration;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CommonUtil {

    @SuppressWarnings("rawtypes")
    public String getRequestParmeter(HttpServletRequest request) {
        /** 리턴할 request 파라미터 */
        StringBuffer sb = new StringBuffer();
        /** request 모든 파라미터 */
        Enumeration params = request.getParameterNames();

        log.info("----------------------------");
        
        /** Enumeration의 요소 */
        while ( params.hasMoreElements() ) {
            /** Enumeration 내의 다음 요소를 취득 */
            String name = (String) params.nextElement();
            /** 패스워드 통과 */
            if ( name.contains("Pwd") ) continue;

            /** value 취득 */
            String value = request.getParameter(name);
            log.info(name + " : " + value);
            sb.append("&" + name + "=" + value);
        }

        log.info("----------------------------");
        log.info(sb.toString());

        /** 결과: request 파라미터 */
        return sb.toString();
    }
}
