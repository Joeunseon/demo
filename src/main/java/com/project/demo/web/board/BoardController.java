package com.project.demo.web.board;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.demo.common.constant.PagePlugin;
import com.project.demo.common.constant.CommonConstant.MODEL_KEY;

@Controller
public class BoardController {

    @GetMapping("/board/list")
    public String list(ModelMap model) {

        return "/board/list";
    }

    @GetMapping("/board/regist")
    public String regist(ModelMap model) {

        List<PagePlugin> plugins = new ArrayList<>();
        plugins.add(PagePlugin.EDITOR);

        model.addAttribute(MODEL_KEY.PAGE_PLUGIN, plugins);

        return "/board/regist";
    }
}
