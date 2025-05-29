package com.project.demo.web.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/account")
public class AccountController {

    @GetMapping("/password")
    public String password(@RequestParam(value = "accountKey", required = false) String accountKey, ModelMap model) {

        model.addAttribute("accountKey", accountKey);

        return "/account/password";
    }
}
