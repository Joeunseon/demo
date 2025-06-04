package com.project.demo.web.account;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.demo.common.constant.CommonConstant.ACCOUNT_KEY;
import com.project.demo.common.constant.CommonConstant.SESSION_KEY;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/account")
public class AccountController {

    @GetMapping("/verify-password")
    public String verify(ModelMap model) {

        return "/account/verify";
    }

    @GetMapping("/mypage")
    public String mypage(HttpSession session, ModelMap model) {
        Boolean verified = (Boolean) session.getAttribute(SESSION_KEY.ACCOUNT_VERIFY);
        LocalDateTime verifyTime = (LocalDateTime) session.getAttribute(SESSION_KEY.ACCOUNT_TIME);
        String accountKey = (String) session.getAttribute(SESSION_KEY.ACCOUNT);
        session.removeAttribute(SESSION_KEY.ACCOUNT);

        if ( verified == null || !verified || verifyTime == null || verifyTime.isBefore(LocalDateTime.now().minusMinutes(10)) ) {
            model.addAttribute("accountKey", null);
        } else {
            model.addAttribute("accountKey", accountKey);
        }

        return "/account/mypage";
    }

    @GetMapping("/password")
    public String password(HttpSession session, ModelMap model) {
        String accountKey = (String) session.getAttribute(SESSION_KEY.ACCOUNT);

        if ( StringUtils.hasText(accountKey) && (accountKey.equals(ACCOUNT_KEY.ACCOUNT_FORCE_PWD_CHANGE) || accountKey.equals(ACCOUNT_KEY.ACCOUNT_INITIAL_PWD_CHANGE)) ) {
            model.addAttribute("accountKey", accountKey);
        } else {
            Boolean verified = (Boolean) session.getAttribute(SESSION_KEY.ACCOUNT_VERIFY);
            LocalDateTime verifyTime = (LocalDateTime) session.getAttribute(SESSION_KEY.ACCOUNT_TIME);

            if ( verified != null && verified && verifyTime != null && !verifyTime.isBefore(LocalDateTime.now().minusMinutes(10)) ) {
                model.addAttribute("accountKey", ACCOUNT_KEY.ACCOUNT_VERIFY);
                session.setAttribute(SESSION_KEY.ACCOUNT, ACCOUNT_KEY.ACCOUNT_VERIFY);
            }
        }

        return "/account/password";
    }
}
