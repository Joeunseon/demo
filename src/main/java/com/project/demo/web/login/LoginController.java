package com.project.demo.web.login;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.demo.common.constant.CommonConstant.MODEL_KEY;
import com.project.demo.common.constant.CommonConstant.RESULT;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(name = "msgKey", required = false) String msgKey, HttpServletRequest request, ModelMap model) {
        if ( StringUtils.hasText(msgKey) )
            model.addAttribute(RESULT.MSG_KEY, msgKey);

        try {
            if ( StringUtils.hasText(request.getHeader("Referer")) )
                model.addAttribute(MODEL_KEY.REDIRECT_URL, new URI(request.getHeader("Referer")).getPath());
        } catch (URISyntaxException e) {
            log.error(">>>> login: ", e);
        }

        return "/login/login";
    }
}
