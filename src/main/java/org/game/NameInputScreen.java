package org.game;

import javax.swing.*;
import java.awt.*;

public class NameInputScreen extends JPanel {
    private Game game;
    private Window window;

    public NameInputScreen(Game game, Window window) {
        this.game = game;
        this.window = window;

        setLayout(new BorderLayout());

        JLabel nameLabel = new JLabel("Enter your name:");
        add(nameLabel, BorderLayout.NORTH);

        JTextField nameField = new JTextField();
        add(nameField, BorderLayout.CENTER);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            String name = nameField.getText();
            game.setPlayerName(name);

            window.changeState(1);
        });
        add(confirmButton, BorderLayout.SOUTH);
    }
}