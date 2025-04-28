package com.project.demo.web.role;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/role")
public class RoleController {

    @GetMapping("/list")
    public String list(ModelMap model) {

        return "/role/list";
    }

    @GetMapping("/info")
    public String info(@RequestParam(value = "roleSeq", required = false) Long roleSeq, ModelMap model) {

        model.addAttribute("roleSeq", roleSeq);

        return "/role/info";
    }

    @GetMapping("/regist")
    public String regist(ModelMap model) {

        return "/role/regist";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam(value = "roleSeq", required = false) Long roleSeq, ModelMap model) {

        model.addAttribute("roleSeq", roleSeq);

        return "/role/edit";
    }
}
