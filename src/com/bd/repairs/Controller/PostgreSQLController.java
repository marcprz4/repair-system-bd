package com.bd.repairs.Controller;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Marcin Przybylski, Bartosz Prusak
 * @version 1.0
 */
public class PostgreSQLController {
    public static Connection connect() {
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/postgres",
                            "postgres", "root");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        return c;
    }
}