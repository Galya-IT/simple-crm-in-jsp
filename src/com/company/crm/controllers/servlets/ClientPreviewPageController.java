package com.company.crm.controllers.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.company.crm.utils.ServletUtils;

@SuppressWarnings("serial")
@WebServlet("/ClientPreview")
public class ClientPreviewPageController extends HttpServlet {
    private final static String FORWARD_RESPONSE_URL = "/WEB-INF/client_preview.jsp";
    private final static String ERROR_RESPONSE_URL = "/CRM/Admin?error=true";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        ServletUtils.forwardRequestedClient(request, response, servletContext, FORWARD_RESPONSE_URL, ERROR_RESPONSE_URL);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // do nothing.
    }
}