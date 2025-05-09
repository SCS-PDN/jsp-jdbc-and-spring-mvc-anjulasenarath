package com.example.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.logging.Logger;

public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = Logger.getLogger(LoggingInterceptor.class.getName());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String uri = request.getRequestURI();

        if (uri.equals("/login") && request.getMethod().equals("POST")) {
            logger.info("Login attempt at " + LocalDateTime.now());
        } else if (uri.startsWith("/register")) {
            logger.info("Course registration attempt at " + LocalDateTime.now());
        }

        return true; 
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
        String uri = request.getRequestURI();

        if (uri.equals("/login") && request.getMethod().equals("POST")) {
            boolean success = (Boolean) request.getAttribute("loginSuccess");
            logger.info("Login " + (success ? "successful" : "failed") + " at " + LocalDateTime.now());
        } else if (uri.startsWith("/register")) {
            logger.info("Course registration completed at " + LocalDateTime.now());
        }
    }
}
