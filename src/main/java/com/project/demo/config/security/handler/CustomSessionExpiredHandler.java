package com.project.demo.config.security.handler;

import java.io.IOException;

import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomSessionExpiredHandler implements SessionInformationExpiredStrategy, InvalidSessionStrategy {

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        //HttpServletRequest request = event.getRequest();
        HttpServletResponse response = event.getResponse();

        response.sendRedirect("/error/sessionTimeOut");
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.sendRedirect("/error/sessionTimeOut");
    }
}
