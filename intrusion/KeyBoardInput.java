package intrusion;

import java.awt.event.KeyEvent;

public class KeyBoardInput {
    private final boolean[] keys = new boolean[256];
    private final boolean[] prev = new boolean[256];
    public void press(int keyCode) { if (keyCode >= 0 && keyCode < keys.length) keys[keyCode] = true; }

    public void release(int keyCode) { if (keyCode >= 0 && keyCode < keys.length) keys[keyCode] = false; }

    public void endFrame() { System.arraycopy(keys, 0, prev, 0, keys.length); }

    private boolean held(int keyCode)    { return keys[keyCode]; }

    private boolean pressed(int keyCode) { return keys[keyCode] && !prev[keyCode]; }

    public Input input() {
        var input = new Input();
        input.up       = held(KeyEvent.VK_UP);
        input.down     = held(KeyEvent.VK_DOWN);
        input.left     = held(KeyEvent.VK_LEFT);
        input.right    = held(KeyEvent.VK_RIGHT);
        input.shot     = held(KeyEvent.VK_Z);
        input.slowDown = held(KeyEvent.VK_SHIFT);

        input.confirmPressed = pressed(KeyEvent.VK_Z);
        input.upPressed = pressed(KeyEvent.VK_UP);
        input.downPressed = pressed(KeyEvent.VK_DOWN);
        input.leftPressed = pressed(KeyEvent.VK_LEFT);
        input.rightPressed = pressed(KeyEvent.VK_RIGHT);
        return input;
    }
}
