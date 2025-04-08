package com.project.demo.web.role;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/role")
public class RoleController {

    @GetMapping("/list")
    public String list(ModelMap model) {

        return "/role/list";
    }
}
