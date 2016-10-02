package com.online.quiz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Try Connecting to DB
 * Generate Error if Failed
 */

public class DatabaseConnectionFactory {

    private static String dbURL = "jdbc:mysql://localhost:8889/3st";
    private static String dbUser = "root";
    private static String dbPassword = "password";

    public static Connection createConnection() {
        Connection con = null;
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println("Error: unable to load driver class!");
                System.exit(1);
            }
            con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
        } catch (SQLException sqe) {
            System.out.println("Error: While Creating connection to database");
            sqe.printStackTrace();
        }
        return con;
    }

}
