package com.project.demo.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/user/list")
    public String list(ModelMap model) {

        return "/user/list";
    }
}
