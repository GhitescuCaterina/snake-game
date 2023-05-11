package org.game;
import java.awt.event.KeyEvent;

public enum Direction {
    UP(KeyEvent.VK_UP),
    DOWN(KeyEvent.VK_DOWN),
    LEFT(KeyEvent.VK_LEFT),
    RIGHT(KeyEvent.VK_RIGHT);

    private int keyCode;

    Direction(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public static Direction fromKeyCode(int keyCode) {
        for (Direction direction : values()) {
            if (direction.keyCode == keyCode) {
                return direction;
            }
        }
        return null;
    }
}