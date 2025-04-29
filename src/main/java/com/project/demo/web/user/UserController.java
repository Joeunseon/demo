package com.project.demo.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @GetMapping("/user/list")
    public String list(ModelMap model) {

        return "/user/list";
    }

    @GetMapping("/user/info")
    public String info(@RequestParam(value = "userSeq", required = false) Long userSeq, ModelMap model) {

        model.addAttribute("userSeq", userSeq);

        return "/user/info";
    }
}
