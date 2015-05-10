package com.company.crm.controllers.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.IIOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet("/Image")
public class ImageController extends HttpServlet {
    private static final String LOGOS_FOLDER = "/WEB-INF/logos/";

    public void init() throws ServletException {
        // do nothing.
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getImage(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getImage(request, response);
    }

    public void destroy() {
        // do nothing.
    }

    private void getImage(HttpServletRequest request, HttpServletResponse response) throws IOException, IllegalArgumentException {

        String imageFileName = request.getParameter("name");

        if (imageFileName == null) {
            throw new IllegalArgumentException("The name of the requested image file is not provided!");
        }

        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();

        String filePath = LOGOS_FOLDER.concat(imageFileName);

        byte[] buffer = new byte[1024];

        try (InputStream inputStream = context.getResourceAsStream(filePath); OutputStream output = response.getOutputStream();) {
            int length = 0;
            while ((length = inputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        }
    }
}
