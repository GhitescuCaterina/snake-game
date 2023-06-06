package org.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {
    private char lastKeyPressed = '\0';
    private boolean newKeyPress = false;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        lastKeyPressed = e.getKeyChar();
        newKeyPress = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public boolean hasNewKeyPress() {
        return newKeyPress;
    }

    public char getLastKeyPressed() {
        newKeyPress = false;
        return lastKeyPressed;
    }
}