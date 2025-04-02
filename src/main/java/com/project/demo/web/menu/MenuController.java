package com.project.demo.web.menu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @GetMapping("/list")
    public String list(ModelMap model) {

        return "/menu/list";
    }

    @GetMapping("/info")
    public String info(@RequestParam(value = "menuSeq", required = false) Long menuSeq, ModelMap model) {
    
        model.addAttribute("menuSeq", menuSeq);
        
        return "/menu/info";
    }

    @GetMapping("/regist")
    public String regist(ModelMap model) {

        return "/menu/regist";
    }
}
