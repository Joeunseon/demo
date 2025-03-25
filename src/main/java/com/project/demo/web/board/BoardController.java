package com.project.demo.web.board;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.demo.common.constant.PagePlugin;
import com.project.demo.common.constant.CommonConstant.MODEL_KEY;

@Controller
@RequestMapping("/board")
public class BoardController {

    @GetMapping("/list")
    public String list(ModelMap model) {

        return "/board/list";
    }

    @GetMapping("/regist")
    public String regist(ModelMap model) {

        List<PagePlugin> plugins = new ArrayList<>();
        plugins.add(PagePlugin.EDITOR);
        plugins.add(PagePlugin.FILE);

        model.addAttribute(MODEL_KEY.PAGE_PLUGIN, plugins);

        return "/board/regist";
    }

    @GetMapping("/info")
    public String info(@RequestParam(value = "boardSeq", required = false) Long boardSeq, ModelMap model) {

        List<PagePlugin> plugins = new ArrayList<>();
        plugins.add(PagePlugin.EDITOR);

        model.addAttribute(MODEL_KEY.PAGE_PLUGIN, plugins);

        model.addAttribute("boardSeq", boardSeq);

        return "/board/info";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam(value = "boardSeq", required = false) Long boardSeq, ModelMap model) {

        List<PagePlugin> plugins = new ArrayList<>();
        plugins.add(PagePlugin.EDITOR);
        plugins.add(PagePlugin.FILE);

        model.addAttribute(MODEL_KEY.PAGE_PLUGIN, plugins);

        model.addAttribute("boardSeq", boardSeq);

        return "/board/edit";
    }

}
