package org.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class InputDialog extends JDialog {
    private JTextField nameField;
    private Game game;
    private JButton submitButton;
    private Connection conn;

    public InputDialog(JFrame parent, Game game) {

        super(parent, "Whats your name", true);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(parent);

        this.game = game;

        nameField = new JTextField();
        submitButton = new JButton("Submit");

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/snake_database", "root", "Loseyourself!2");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        submitButton.addActionListener(e -> submitNameToDatabase());


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);

        add(nameField, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void submitNameToDatabase() {
        String playerName = nameField.getText();

        if (playerName != null && !playerName.isEmpty()) {
            int playerScore = game.getPlayerScore();
            int timeTaken = game.timeTaken();

            System.out.println("Player Name: " + playerName);
            System.out.println("Player Score: " + playerScore);
            System.out.println("Time Taken: " + timeTaken);

            Thread dbThread = new Thread(() -> {
                PreparedStatement statement = null;
                try {
                    String sql = "INSERT INTO player_scores (name, score, time_taken) VALUES (?, ?, ?)";
                    System.out.println("SQL statement: " + sql);
                    statement = conn.prepareStatement(sql);

                    statement.setString(1, playerName);
                    statement.setInt(2, playerScore);
                    statement.setInt(3, timeTaken); // Changed setLong to setInt

                    int result = statement.executeUpdate();
                    System.out.println("Rows affected : " + result);

                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(null, "Name saved successfully!");
                        Window.getWindow().changeState(1);
                    });

                    System.out.println("Executing SQL statement: " +
                            sql.replaceFirst("\\?", "\"" + playerName + "\"")
                                    .replaceFirst("\\?", String.valueOf(playerScore))
                                    .replaceFirst("\\?", String.valueOf(timeTaken)));

                } catch (SQLException e) {
                    e.printStackTrace();
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(null, "Error saving name!");
                    });
                } finally {
                    if (statement != null) {
                        try {
                            statement.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            dbThread.start();
        }
    }
}