package org.game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter implements java.awt.event.KeyListener {
    private boolean[] keyPressed = new boolean[128];
    private char lastKeyPressed = '\0';
    private boolean newKeyPress = false;
    private KeyboardListener keyboardListener;

    public KeyListener() {
        keyboardListener = new KeyboardListener();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (keyCode >= 0 && keyCode < keyPressed.length) {
            keyPressed[keyCode] = true;
            lastKeyPressed = keyEvent.getKeyChar();
            newKeyPress = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (keyCode >= 0 && keyCode < keyPressed.length) {
            keyPressed[keyCode] = false;
        }
    }

    public boolean isKeyPressed(int keyCode) {
        if (keyCode >= 0 && keyCode < keyPressed.length) {
            return keyPressed[keyCode];
        }
        return false;
    }

//    public boolean hasNewKeyPress() {
//        return newKeyPress;
//    }
//
//    public char getLastKeyPressed() {
//        newKeyPress = false;
//        return lastKeyPressed;
//    }
//
//    public KeyboardListener getKeyboardListener() {
//        return keyboardListener;
//    }
}