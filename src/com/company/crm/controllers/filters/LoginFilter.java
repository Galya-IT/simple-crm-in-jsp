package com.company.crm.controllers.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.company.crm.controllers.servlets.LoginPageController;

@WebFilter(filterName = "LoginFilter", urlPatterns = { "/*" }, initParams = {
        @WebInitParam(name = "excludeLogin", value = "/Login"), @WebInitParam(name = "excludeView", value = "/view") })
public class LoginFilter implements Filter {
    private static final String REDIRECT_SUCCESS_PAGE = "/CRM/Admin";
    private static final String REDIRECT_FAILURE_PAGE = "/CRM/Login?error=nologin";
    
    private String excludePatternLogin = null;
    private String excludePatternView = null;

    @Override
    public void init(FilterConfig cfg) throws ServletException {
        this.excludePatternLogin = cfg.getInitParameter("excludeLogin");
        this.excludePatternView = cfg.getInitParameter("excludeView");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String url = httpRequest.getRequestURL().toString();

        if (url.indexOf(excludePatternView) > -1) {
            chain.doFilter(request, response);
            return;
        }

        boolean isOnLoginPage = false;

        if (url.indexOf(excludePatternLogin) > -1) {
            isOnLoginPage = true;
        }

        HttpSession session = httpRequest.getSession();
        String sessionUsername = (String) session.getAttribute("username");

        boolean isValidSession = false;

        if (LoginPageController.USER.equals(sessionUsername)) {
            isValidSession = true;
        }

        if (isValidSession) {
            if (isOnLoginPage) {
                httpResponse.sendRedirect(REDIRECT_SUCCESS_PAGE);
                return;
            } else {
                chain.doFilter(request, response);
            }
        } else {
            if (isOnLoginPage) {
                chain.doFilter(request, response);
            } else {
                httpResponse.sendRedirect(REDIRECT_FAILURE_PAGE);
                return;
            }
        }
    }

    @Override
    public void destroy() {
        // do nothing.
    }
}
