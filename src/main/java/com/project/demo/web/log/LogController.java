package com.project.demo.web.log;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/log")
public class LogController {

    @GetMapping("/list")
    public String list(ModelMap model) {

        return "/log/list";
    }
}
