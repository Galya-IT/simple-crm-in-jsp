package com.company.crm.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.company.crm.exceptions.ClientAlreadyExistsException;
import com.company.crm.exceptions.ClientPropertyValidationException;

public class ClientFactory {
    private Connection dbConnection = null;

    public ClientFactory(Connection databaseConnection) {
        this.dbConnection = databaseConnection;
    }

    public Client createClient(String name, String location, String notes, String contractDate, String logoName)
            throws ClientAlreadyExistsException, ClientPropertyValidationException {
        Client client = null;

        try {
            String sqlQuery = "SELECT * FROM clients WHERE Name=?";
            PreparedStatement stmt = this.dbConnection.prepareStatement(sqlQuery);
            stmt.setString(1, name);
            ResultSet clientsWithSameName = stmt.executeQuery();

            if (!clientsWithSameName.next()) {
                client = new Client(name, location, notes, contractDate, logoName, false);

                // create new record
                String sqlInsert = "INSERT INTO clients (Name, Location, Notes, ContractDate, LogoImageName) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stm = this.dbConnection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
                stm.setString(1, name);
                stm.setString(2, location);
                stm.setString(3, notes);
                stm.setString(4, contractDate);
                stm.setString(5, logoName);
                stm.executeUpdate();

                ResultSet generatedKeys = stm.getGeneratedKeys();
                if (generatedKeys.next()) {
                    client.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no generated key obtained.");
                }
            } else {
                throw new ClientAlreadyExistsException("A client with the same name already exists.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return client;
    }
}
