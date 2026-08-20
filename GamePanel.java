import javax.swing.JPanel;
import javax.swing.Timer;

import game.ConfigConst;
import game.Game;
import game.Input;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel {
    static final Color backgroundColor = new Color(14, 12, 24);
    final boolean[] keys = new boolean[256];
    private Game game = new Game();

    public GamePanel() {
        setPreferredSize(new Dimension(ConfigConst.WIDTH, ConfigConst.HEIGHT));
        setBackground(backgroundColor);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                var c = e.getKeyCode();
                if(c == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
                if (c >= 0 && c < keys.length) {
                    keys[c] = true;
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                var c = e.getKeyCode();
                if (c >= 0 && c < keys.length) {
                    keys[c] = false;
                }
            }
        });
        new Timer(16, e -> {
            game.step(getInputFromKeys());
            repaint();
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        var player = game.player;
        var graphics = (Graphics2D) g0;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(new Color(230, 200, 90));
        var drawX = (int) Math.round(player.x);
        var drawY = (int) Math.round(ConfigConst.HEIGHT - player.y);
        graphics.fillOval(drawX, drawY, ConfigConst.PLAYER_RADIUS, ConfigConst.PLAYER_RADIUS);
    }

    public Input getInputFromKeys() {
        var input = new Input();
        input.up = keys[KeyEvent.VK_UP];
        input.down = keys[KeyEvent.VK_DOWN];
        input.left = keys[KeyEvent.VK_LEFT];
        input.right = keys[KeyEvent.VK_RIGHT];
        return input;
    }
}
