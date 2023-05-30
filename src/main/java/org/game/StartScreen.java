package org.game;

import javax.swing.*;

public class StartScreen extends JPanel {
    private Game game;
    private Window window;

    public StartScreen(Game game, Window window) {
        this.game = game;
        this.window = window;

        // Your start screen setup code...

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            // Change to the name input screen when the start button is clicked
            NameInputScreen nameInputScreen = new NameInputScreen(game, window);
            window.changePanel(nameInputScreen);
        });
    }

}