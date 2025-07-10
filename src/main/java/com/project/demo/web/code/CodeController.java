package com.project.demo.web.code;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/code")
public class CodeController {

    @GetMapping("/list")
    public String list(ModelMap model) {

        return "/code/list";
    }

    @GetMapping("/info")
    public String info(@RequestParam(value = "grpSeq", required = false) Integer grpSeq, @RequestParam(value = "cdSeq", required = false) Integer cdSeq, ModelMap model) {

        model.addAttribute("grpSeq", grpSeq);
        model.addAttribute("cdSeq", cdSeq);

        return "/code/info";
    }

    @GetMapping("/regist")
    public String regist(ModelMap model) {

        return "/code/regist";
    }
}
