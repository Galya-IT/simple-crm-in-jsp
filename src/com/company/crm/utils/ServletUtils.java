package com.company.crm.utils;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.company.crm.controllers.database.DatabaseManipulator;
import com.company.crm.exceptions.ClientNotFoundException;
import com.company.crm.exceptions.ClientPropertyValidationException;
import com.company.crm.model.Client;

public class ServletUtils {
    private static DatabaseManipulator databaseManipulator;

    static {
        // "static constructor"
        databaseManipulator = DatabaseManipulator.getInstance();
    }

    public static void forwardRequestedClient(HttpServletRequest request, HttpServletResponse response,
            ServletContext servletContext, String forwardResponseUrl, String errorResponseUrl) throws IOException,
            ServletException, NumberFormatException {

        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(forwardResponseUrl);

        Client client = null;

        if (request.getParameterMap().containsKey("clientId")) {
            String idAsString = request.getParameter("clientId").trim();

            int id = Integer.parseInt(idAsString);

            try {
                client = databaseManipulator.getClientById(id);
            } catch (ClientNotFoundException | ClientPropertyValidationException e) {
                client = null;
            }

            if (client == null) {
                response.sendRedirect(errorResponseUrl);
                return;
            }

            request.setAttribute("client", client);
        }

        requestDispatcher.forward(request, response);
        return;
    }
}
