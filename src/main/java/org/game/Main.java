package org.game;

import org.game.Window;

public class Main {

    public static void main(String[] args) {
        Window window = Window.getWindow();

        Thread thread = new Thread(window);
        thread.start();

    }


}