package com.codemock.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {
    
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Load properties file
            Properties props = new Properties();
            InputStream inputStream = DBConnection.class.getClassLoader().getResourceAsStream("db.properties");
            
            if (inputStream != null) {
                props.load(inputStream);
                
                // Load MS SQL Driver
                Class.forName(props.getProperty("db.driver"));
                
                // Establish Connection
                conn = DriverManager.getConnection(
                    props.getProperty("db.url"),
                    props.getProperty("db.username"),
                    props.getProperty("db.password")
                );
            } else {
                System.err.println("Error: db.properties file not found in the classpath.");
            }
            
        } catch (Exception e) {
            System.err.println("Database Connection Failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        return conn;
    }
}