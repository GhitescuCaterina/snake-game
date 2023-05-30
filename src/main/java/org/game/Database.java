package org.game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Database {
    static Connection c = null;
    static Statement stmt = null;

    public static void main( String args[] ) {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:highscores.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE HIGHSCORES " +
                    "(ID INT PRIMARY KEY NOT NULL," +
                    " NAME TEXT NOT NULL, " +
                    " SCORE INT NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }
}