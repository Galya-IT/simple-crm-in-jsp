package com.company.crm.controllers.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.company.crm.exceptions.ClientNotFoundException;
import com.company.crm.exceptions.ClientPropertyValidationException;
import com.company.crm.exceptions.UserPropertyValidationException;
import com.company.crm.model.Client;
import com.company.crm.model.User;

public class DatabaseManipulator {
    private Connection dbConnection = null;
    private static DatabaseManipulator instance = null;

    private DatabaseManipulator() {
        initializeConnection();
    }

    public static DatabaseManipulator getInstance() {
        if (instance == null) {
            instance = new DatabaseManipulator();
        }
        return instance;
    }

    public boolean deleteClient(int id) {
        boolean isSuccessful = true;

        try {
            PreparedStatement stm = this.dbConnection.prepareStatement("UPDATE clients SET IsDeleted=? WHERE Id=?");
            stm.setInt(1, 1);
            stm.setInt(2, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            isSuccessful = false;
        }

        return isSuccessful;
    }

    public boolean updateClient(String name, String location, String notes, String contractDate, String logoFileName, int id)
            throws ClientPropertyValidationException, SQLException, ParseException {
        boolean isSuccessful = false;

        boolean isLogoUpdateRequired = false;
        
        if (logoFileName != null && !logoFileName.isEmpty()) {
            // Check for the logo in database
            PreparedStatement getLogoStmt = this.dbConnection
                    .prepareStatement("SELECT LogoImageName FROM clients WHERE Id=?");
            getLogoStmt.setInt(1, id);
            ResultSet rs = getLogoStmt.executeQuery();

            String logoImageNameInDb = "";
            
            if (rs.next()) {
                logoImageNameInDb = rs.getString("LogoImageName");
                isLogoUpdateRequired = logoImageNameInDb != null && !logoImageNameInDb.isEmpty() && !logoFileName.equals(logoImageNameInDb);
            }
        }
        
        if (name != null) {
            Client client = null;
            client = new Client(name);
            client.setId(id);
            client.setLocation(location);
            client.setContractDate(contractDate);
            client.setNotes(notes);
            client.setLogoFileName(logoFileName);
            
            String updateSqlRequest = "";
            
            List<String> parameters = new ArrayList<String>();
            parameters.add(client.getName());
            parameters.add(client.getLocation());
            parameters.add(client.getNotes());
            parameters.add(client.getContractDate("yyyy-MM-dd"));
            
            if (isLogoUpdateRequired) {
                updateSqlRequest = "UPDATE clients SET Name=?, Location=?, Notes=?, ContractDate=?, LogoImageName=? WHERE Id=?";
                parameters.add(client.getLogoFileName());
            } else {
                updateSqlRequest = "UPDATE clients SET Name=?, Location=?, Notes=?, ContractDate=? WHERE Id=?";
            }
            
            parameters.add(Integer.toString(client.getId()));

            PreparedStatement stm = this.dbConnection.prepareStatement(updateSqlRequest);
            
            for (int i = 0; i < parameters.size(); i++) {
                stm.setString(i + 1, parameters.get(i));
            }

            stm.executeUpdate();
            isSuccessful = true;
        }

        return isSuccessful;
    }

    public Client getClientById(int clientId) throws ClientPropertyValidationException, ClientNotFoundException {
        Client client = null;

        try {
            Statement stmt = this.dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM clients WHERE Id=" + clientId);

            if (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String location = rs.getString(3);
                String notes = rs.getString(4);
                String contractDate = rs.getString(5);
                String logoName = rs.getString(6);
                boolean isDeleted = rs.getBoolean(7);

                client = new Client(name, location, notes, contractDate, logoName, isDeleted, id);
            } else {
                throw new ClientNotFoundException("The selected client is not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return client;
    }

    public Set<Client> getAllClients() throws SQLException {
        Set<Client> clients = new HashSet<Client>();

        Statement stmt = this.dbConnection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM clients WHERE IsDeleted=0");

        while (rs.next()) {
            int currentClientId = rs.getInt("Id");
            String currentClientName = rs.getString("Name");
            String currentClientLocation = rs.getString("Location");
            String currentClientNotes = rs.getString("Notes");
            String currentClientContractDate = rs.getString("ContractDate");
            String currentClientLogo = rs.getString("LogoImageName");
            boolean currentClientIsDeleted = rs.getBoolean("IsDeleted");

            Client currentClient = null;

            try {
                currentClient = new Client(currentClientName, currentClientLocation, currentClientNotes,
                        currentClientContractDate, currentClientLogo, currentClientIsDeleted, currentClientId);
            } catch (ClientPropertyValidationException e) {
                e.printStackTrace();
            }

            clients.add(currentClient);
        }

        return clients;
    }

    public Set<User> getUsers(Connection dbConnection) throws SQLException, UserPropertyValidationException {
        Set<User> users = new HashSet<User>();

        users = new HashSet<User>();

        Statement stmt = dbConnection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users");

        while (rs.next()) {
            int currentClientId = rs.getInt("Id");
            String currentClientName = rs.getString("Username");
            String currentClientPassword = rs.getString("Password");

            User currentUser = new User(currentClientName, currentClientPassword, currentClientId);

            users.add(currentUser);
        }

        return users;
    }

    public Connection getDatabaseConnection() {
        return this.dbConnection;
    }

    private void initializeConnection() {
        try {
            ConnectionCreator cc = ConnectionCreator.getInstance();
            this.dbConnection = cc.getDatabaseConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
