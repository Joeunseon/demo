package com.project.demo.web.join;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JoinController {

    @GetMapping("/join")
    public String join(ModelMap model) {

        return "/join/join";
    }
}
