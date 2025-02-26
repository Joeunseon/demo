package com.project.demo.web.signup;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignupController {

    @GetMapping("/signup")
    public String signup(ModelMap model) {

        return "/signup/signup";
    }
}
