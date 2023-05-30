package org.game;

import java.sql.*;

import static org.game.Database.c;
import static org.game.Database.stmt;

public class ScoreRepository {
    private static final String JDBC_DRIVER = "org.sqlite.JDBC";
    private static final String DB_URL = "jdbc:sqlite:highscores.db";

    public void insertScore(String name, int score) {
        try {
            Class.forName(JDBC_DRIVER);
            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                String sql = "INSERT INTO HIGHSCORES (NAME,SCORE) VALUES (?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, name);
                    pstmt.setInt(2, score);
                    pstmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printHighScores() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:highscores.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM HIGHSCORES ORDER BY SCORE DESC LIMIT 10;" );
            while ( rs.next() ) {
                String name = rs.getString("name");
                int score = rs.getInt("score");

                System.out.println( "NAME = " + name );
                System.out.println( "SCORE = " + score );
                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
}