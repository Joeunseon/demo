package com.project.demo.web.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.demo.common.constant.CommonConstant.RESULT;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(name = "msgKey", required = false) String msgKey, ModelMap model) {
        if ( msgKey != null ) {
            model.addAttribute(RESULT.MSG_KEY, msgKey);
        }

        return "/login/login";
    }
}
