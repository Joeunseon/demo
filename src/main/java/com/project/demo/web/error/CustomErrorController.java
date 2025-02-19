package com.project.demo.web.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/error")
public class CustomErrorController {

    @GetMapping
    public String handleError(HttpServletRequest request) {
        // 에러 상태 코드 가져오기
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if ( status != null ) {
            int statusCode = Integer.parseInt(status.toString());

            if ( statusCode == 404 ) {
                return "/error/404";
            } else if ( statusCode == 500 ) {
                return "/error/500";
            } else if ( statusCode == 403 ) {
                return "/error/403";
            }
        }
        return "/error/default";
    }

    @GetMapping("/sessionTimeOut")
    public String sessionTimeOut() {
        
        return "/error/sessionTimeOut";
    }
}
