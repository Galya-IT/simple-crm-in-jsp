package com.company.crm.controllers.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet("/Login")
public class LoginPageController extends HttpServlet {
    public static final String USER = "admin";
    public static final String PASSWORD = "qwerty";
    public static final String COOKIE_NAME = "loginCookie";

    private static final String LOGIN_PAGE = "/WEB-INF/login.jsp";
    private static final String INITIAL_ADMIN_PAGE = "/CRM/Admin";
    private static final String ERROR_LOGIN_PAGE = "/CRM/Login?error=login";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(LOGIN_PAGE);

        requestDispatcher.forward(request, response);
        return;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String userFromInput = request.getParameter("user");
        String passFromInput = request.getParameter("pass");

        if (userFromInput.equals(USER) && passFromInput.equals(PASSWORD)) {
            HttpSession session = request.getSession();
            session.setAttribute("username", USER);
            response.sendRedirect(INITIAL_ADMIN_PAGE);
        } else {
            response.sendRedirect(ERROR_LOGIN_PAGE);
        }
    }
}
