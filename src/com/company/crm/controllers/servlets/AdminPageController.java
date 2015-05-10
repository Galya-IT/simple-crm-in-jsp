package com.company.crm.controllers.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.company.crm.controllers.database.DatabaseManipulator;
import com.company.crm.model.Client;

@SuppressWarnings("serial")
@WebServlet("/Admin")
public class AdminPageController extends HttpServlet {
    private static final String FORWARD_ALLCLIENTS_PAGE = "/WEB-INF/all_clients.jsp";

    private DatabaseManipulator databaseManipulator;
    private Set<Client> clients;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.databaseManipulator = DatabaseManipulator.getInstance();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            this.clients = this.databaseManipulator.getAllClients();

            request.setAttribute("clientsList", this.clients);

            ServletContext servletContext = getServletContext();
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(FORWARD_ALLCLIENTS_PAGE);
            requestDispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Records cannot be retrieved.");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // do nothing.
    }
}
