package com.company.crm.controllers.servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import com.company.crm.controllers.database.DatabaseManipulator;
import com.company.crm.exceptions.ClientAlreadyExistsException;
import com.company.crm.exceptions.ClientPropertyValidationException;
import com.company.crm.exceptions.ImageAlreadyExistsException;
import com.company.crm.exceptions.NotCorrectImageFormatException;
import com.company.crm.model.Client;
import com.company.crm.model.ClientFactory;
import com.company.crm.utils.ServletUtils;

@SuppressWarnings("serial")
@WebServlet("/AddEdit")
public class AddEditPageController extends HttpServlet {
    private final static String FORWARD_RESPONSE_URL = "/WEB-INF/add_edit_client.jsp";
    private final static String ERROR_RESPONSE_URL = "/CRM/AddEdit?error=true";

    private DatabaseManipulator databaseManipulator;
    private int currentEditableClientId;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.databaseManipulator = DatabaseManipulator.getInstance();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        ServletUtils.forwardRequestedClient(request, response, servletContext, FORWARD_RESPONSE_URL, ERROR_RESPONSE_URL);

        if (request.getParameterMap().containsKey("clientId")) {
            String clientId = request.getParameter("clientId").trim();
            this.currentEditableClientId = Integer.parseInt(clientId);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean isSaved = false;
        String responseUrl = null;

        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        String name = null;
        String location = null;
        String notes = null;
        String contractDate = null;
        String logoFileName = null;

        // Parse the request
        try {
            List<FileItem> items = upload.parseRequest(request);
            boolean isEditRequest = false;

            for (FileItem item : items) {
                if (item.isFormField()) {
                    // Process regular form field
                    String fieldName = item.getFieldName();
                    String fieldValue = item.getString();

                    switch (fieldName) {
                    case "requestType":
                        if (fieldValue.equals("edit")) {
                            isEditRequest = true;
                        }
                    case "name":
                        name = fieldValue;
                        break;
                    case "location":
                        location = fieldValue;
                        break;
                    case "notes":
                        notes = fieldValue;
                        break;
                    case "contractDate":
                        contractDate = fieldValue;
                        break;
                    }

                } else if (item.getFieldName().equals("logoFile") && item.getSize() > 0) {
                    // Process form FILE field
                    boolean isValidImage = checkIfValidImageFormat(item);

                    if (isValidImage) {
                        logoFileName = FilenameUtils.getName(item.getName());
                        String fileAbsolutePath = getAbsolutePath(request, logoFileName);
                        saveFileToDisk(item, fileAbsolutePath);
                    } else {
                        throw new NotCorrectImageFormatException("Not a valid image format.");
                    }
                }
            }

            if (isEditRequest) {
                isSaved = databaseManipulator.updateClient(name, location, notes, contractDate, logoFileName,
                        this.currentEditableClientId);
            } else {
                ClientFactory clientFactory = new ClientFactory(this.databaseManipulator.getDatabaseConnection());
                Client client = clientFactory.createClient(name, location, notes, contractDate, logoFileName);

                if (client != null) {
                    isSaved = true;
                }
            }
        } catch (FileUploadException e) {
            exceptionLoggingFunction(e, "Cannot parse multipart request.");
            // responseUrl = "";
        } catch (ClientPropertyValidationException e) {
            exceptionLoggingFunction(e, "Provided data is not correct.");
        } catch (SQLException e) {
            exceptionLoggingFunction(e, "Record is not saved. Please try again later.");
        } catch (ParseException e) {
            exceptionLoggingFunction(e, "Date is not in the correct format.");
        } catch (ClientAlreadyExistsException e) {
            exceptionLoggingFunction(e, "A client with the same name already exists.");
        } catch (NotCorrectImageFormatException e) {
            exceptionLoggingFunction(e, "");
        } catch (ImageAlreadyExistsException e) {
            exceptionLoggingFunction(e, "");
        } catch (IOException e) {
            exceptionLoggingFunction(e, "File was not successfully written to disk.");
        }

        if (isSaved) {
            responseUrl = "/CRM/AddEdit?success=true";
        } else {
            responseUrl = "/CRM/AddEdit?edit=" + this.currentEditableClientId + "&error=true";
        }

        response.sendRedirect(responseUrl);
    }

    private void saveFileToDisk(FileItem item, String fileAbsolutePath) throws ImageAlreadyExistsException, IOException {
        File file = new File(fileAbsolutePath);

        if (!file.exists()) {
            file.createNewFile();
        } else {
            throw new ImageAlreadyExistsException("Uploaded image already exists.");
        }

        InputStream in = item.getInputStream();
        FileOutputStream out = new FileOutputStream(file);
        IOUtils.copy(in, out);
    }

    private String getAbsolutePath(HttpServletRequest request, String logoFileName) {
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        String realContextPath = context.getRealPath(request.getContextPath());

        String parentDirectory = new File(realContextPath).getParent();
        String filePath = parentDirectory + File.separator + "WEB-INF" + File.separator + "logos" + File.separator;

        // TO DO: Rename Image File accordingly to company name
        String fileAbsolutePath = filePath.concat(logoFileName);

        return fileAbsolutePath;
    }

    private boolean checkIfValidImageFormat(FileItem item) {
        // TO DO: better image type check
        boolean isValidImage = true;

        String mimeType = item.getContentType();
        String type = mimeType.split("/")[0];

        if (!type.equals("image")) {
            isValidImage = false;
        }

        return isValidImage;
    }

    private void exceptionLoggingFunction(Exception e, String customMessage) {
        System.out.println(e.getMessage());
        System.out.println(customMessage);
        e.printStackTrace();
    }
}