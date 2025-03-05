package com.project.demo.web.board;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping("/board/list")
    public String list(ModelMap model) {

        return "/board/list";
    }
}
