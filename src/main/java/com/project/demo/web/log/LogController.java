package com.project.demo.web.log;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/log")
public class LogController {

    @GetMapping("/list")
    public String list(ModelMap model) {

        return "/log/list";
    }

    @GetMapping("/info")
    public String info(@RequestParam(value = "logSeq", required = false) Long logSeq, ModelMap model) {

        model.addAttribute("logSeq", logSeq);

        return "/log/info";
    }
}
