package com.company.crm.controllers.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionCreator {
    private final String serverName = "localhost";
    private final String databaseName = "crm";
    private final int portNumber = 3306;
    private final String user = "root";
    private final String pass = "parola";
    private Connection connection = null;

    private static ConnectionCreator instance = null;

    private ConnectionCreator() throws SQLException {
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        InitializeConnection();
    }

    public static ConnectionCreator getInstance() throws SQLException {
        if (instance == null) {
            instance = new ConnectionCreator();
        }
        return instance;
    }

    public Connection getDatabaseConnection() throws SQLException {
        return this.connection;
    }

    private void InitializeConnection() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:mysql://" + this.serverName + ":" + this.portNumber + "/"
                + this.databaseName + "?" + "user=" + this.user + "&password=" + this.pass);
        System.out.println("Connection created");
    }
}
