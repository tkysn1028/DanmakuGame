import javax.swing.JPanel;
import javax.swing.Timer;

import game.ConfigConst;
import game.Game;
import game.object.Bullet;
import game.object.Input;

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
    int frame = 0;
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
            frame++;
            game.step(getInputFromKeys(), frame);
            repaint();
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        var graphics = (Graphics2D) g0;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw player
        graphics.setColor(Color.WHITE);
        drawCircle(graphics, game.player.x, game.player.y, ConfigConst.PLAYER_RADIUS);
        graphics.setColor(Color.RED);
        fillCircle(graphics, game.player.x, game.player.y, ConfigConst.PLAYER_RADIUS / 2.5);
        
        // Draw boss
        graphics.setColor(new Color(230, 200, 90));
        fillCircle(graphics, game.boss.x, game.boss.y, ConfigConst.BOSS_RADIUS);

        // Draw bullets
        graphics.setColor(Color.YELLOW);
        for (Bullet bullet : game.bulletPool.bullets) {
            fillCircle(graphics, bullet.x, bullet.y, 10);
        }
    }
    private void fillCircle(Graphics2D g, double cx, double cy, double r) {
        g.fillOval((int) (cx - r), (int) (cy - r), (int) (r * 2), (int) (r * 2));
    }

    private static void drawCircle(Graphics2D g, double cx, double cy, double r) {
        g.drawOval((int) (cx - r), (int) (cy - r), (int) (r * 2), (int) (r * 2));
    }    

    private Input getInputFromKeys() {
        var input = new Input();
        input.up = keys[KeyEvent.VK_UP];
        input.down = keys[KeyEvent.VK_DOWN];
        input.left = keys[KeyEvent.VK_LEFT];
        input.right = keys[KeyEvent.VK_RIGHT];
        return input;
    }
}
