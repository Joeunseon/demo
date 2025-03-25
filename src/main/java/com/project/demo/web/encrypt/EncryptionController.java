package com.project.demo.web.encrypt;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EncryptionController {

    @GetMapping("/encryption")
    public String encrption(ModelMap model) {

        return "/encrypt/encryption";
    }
}