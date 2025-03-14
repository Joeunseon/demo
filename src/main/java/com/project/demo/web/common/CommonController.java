package com.project.demo.web.common;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.demo.common.constant.CommonConstant.MODEL_KEY;

import jakarta.validation.constraints.Min;

@Controller
@Validated
public class CommonController {

    @GetMapping("/pagination")
    public String pagination(@RequestParam("currentPage") @Min(value = 1, message = "{error.basic}") int currentPage, @RequestParam("pageScale") @Min(value = 1, message = "{error.basic}") int pageScale, @RequestParam("totalCnt") @Min(value = 0, message = "{error.basic}") int totalCnt, ModelMap modelMap) {

        int totalPages = (int) Math.ceil((double) totalCnt / pageScale);

        modelMap.addAttribute(MODEL_KEY.CURRENT_PAGE, currentPage);
        modelMap.addAttribute(MODEL_KEY.TOTAL_CNT, totalCnt);

        Integer prevPage = (currentPage > 1) ? (currentPage - 1) : null;
        Integer nextPage = (currentPage < totalPages) ? (currentPage + 1) : null;

        modelMap.addAttribute("prevPage", prevPage);
        modelMap.addAttribute("nextPage", nextPage);

        int startPage = ((currentPage - 1) / pageScale) * pageScale + 1;
        int endPage = Math.min(startPage + pageScale - 1, totalPages);

        modelMap.addAttribute("startPage", startPage);
        modelMap.addAttribute("endPage", endPage);

        return "/common/page";
    }
}
