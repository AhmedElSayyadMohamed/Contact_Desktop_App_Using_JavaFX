
package com.mycompany.contactdbapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.derby.jdbc.ClientDriver;


public class DBConnection {

    private static final String URL = "jdbc:derby://localhost:1527/ContactDb;create=true;currentSchema=APP";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() {
        try {
          
            // Register the Derby client driver
            DriverManager.registerDriver(new ClientDriver());

            // Establish and return the connection
            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}