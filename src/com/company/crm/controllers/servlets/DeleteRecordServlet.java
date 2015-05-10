package com.company.crm.controllers.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.company.crm.controllers.database.DatabaseManipulator;

@SuppressWarnings("serial")
@WebServlet("/Delete")
public class DeleteRecordServlet extends HttpServlet {
    private static final String DELETE_OPERATION_KEY = "delete";
    private DatabaseManipulator databaseManipulator;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.databaseManipulator = DatabaseManipulator.getInstance();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // do nothing.
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // AJAX 
        boolean isDeleted = false;
        String result = "{ responseStatus: 'ok' }";
        
        if (request.getParameterMap().containsKey("operation")) {
            
            String operationType = request.getParameter("operation").trim();
            String idString = request.getParameter("clientId").trim();
            int id = Integer.parseInt(idString);

            if (DELETE_OPERATION_KEY.equals(operationType)) {
                isDeleted = databaseManipulator.deleteClient(id);
            }
        }

        if (!isDeleted) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(result); // Write response body.
        
    }
}