package game.battle.ui;
import game.battle.core.Input;
import game.battle.core.BattleType;
import game.battle.core.GameSession;
import game.battle.util.ConfigConst;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel {
    static final Color backGroundColor = ColorConst.DARKBLUE;
    final boolean[] keys = new boolean[256];
    int frame = 0;
    GameSession gameSession = new GameSession();
    private boolean prevConfirm = false;

    public GamePanel() {
        setPreferredSize(new Dimension(ConfigConst.WIDTH, ConfigConst.HEIGHT));
        setBackground(backGroundColor);
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
            gameSession.update(getInputFromKeys(), frame);
            repaint();
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        var graphics = (Graphics2D) g0;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        switch (gameSession.scene()) {
            case TITLE -> drawTitle(graphics);
            case DANMAKU_PLAYING -> drawDanmakuGame(graphics);
            case GAMEOVER -> {
                drawDanmakuGame(graphics);
                drawGameOver(graphics);
            }
        }
    }

    private void drawTitle(Graphics2D graphics) {
        int cx = ConfigConst.WIDTH / 2;
        int cy = ConfigConst.HEIGHT / 2;
        graphics.setColor(ColorConst.WHITEBLUE);
        for (int i = 0; i < 40; i++) {
            double angle = i * 0.618 * Math.PI * 2;
            double radius = 60 + ((frame * 0.6 + i * 14) % 1280);
            fillCircle(graphics, cx + Math.cos(angle) * radius, cy + Math.sin(angle) * radius, 3);
        }

        graphics.setColor(ColorConst.WHITE);
        graphics.setFont(new Font("SansSerif", Font.BOLD, 40));
        drawCentered(graphics, "DANMAKU", cy - 40);
        graphics.setFont(new Font("SansSerif", Font.PLAIN, 14));
        drawCentered(graphics, "- shooting -", cy - 10);
        if ((frame / 20) % 2 == 0) {
            graphics.setColor(ColorConst.WHITEYELLOW);
            graphics.setFont(new Font("SansSerif", Font.BOLD, 16));
            drawCentered(graphics, "PRESS Z TO START", cy + 70);
        }
        graphics.setColor(ColorConst.WHITEBLUE);
        graphics.setFont(new Font("SansSerif", Font.PLAIN, 11));
        drawCentered(graphics, "ARROW: MOVE    SHIFT: SLOW    Z: SHOT", ConfigConst.HEIGHT - 40);
    }

    private void drawDanmakuGame(Graphics2D graphics) {
        var danmaku = gameSession.danmaku();
        var player = danmaku.battle().player;
        var enemies = danmaku.battle().enemies;
        var boss = danmaku.battle().boss;
        var shots = danmaku.battle().shotPool.all();
        var bullets = danmaku.battle().bulletPool.all();
        
        // Draw player
        if(player.iframes() == 0 || (frame / 2) % 2 == 0) {
            graphics.setColor(ColorConst.WHITE);
            drawCircle(graphics, player.x, player.y, player.radius() * 2.5);
            graphics.setColor(ColorConst.RED);
            fillCircle(graphics, player.x, player.y, player.radius());
        }
    
        // Draw Enemy
        graphics.setColor(ColorConst.WHITEYELLOW);
        enemies.forEach(enemy -> {
            fillCircle(graphics, enemy.x, enemy.y, enemy.radius());
        });

        // Draw Boss
        if (boss != null) {
            fillCircle(graphics, boss.x, boss.y, boss.radius());
        }

        // Draw bullets
        bullets.forEach((bullet) -> {
            graphics.setColor(BulletColors.of(bullet.bulletType()));
            fillCircle(graphics, bullet.x, bullet.y, bullet.radius());
        });

        // Draw Shots
        shots.forEach((shot) -> {
            graphics.setColor(ShotColors.of(shot.shotType()));
            graphics.fillRect((int)(shot.x - 2), (int)(shot.y - 8), shot.radius(), shot.radius());
        });

        // Draw ScoreBoard
        graphics.setColor(ColorConst.WHITEBLUE);
        graphics.setFont(new Font("SansSerif", Font.PLAIN, 12));
        var scoreBoard = String.format("bullets %4d   PlayerHP %d", bullets.size(), danmaku.lives());
        var bossHp = boss != null ? String.format(" BossHP %d", boss.hitPoints()) : "";
        graphics.drawString(scoreBoard + bossHp, 8, ConfigConst.HEIGHT - 12);
    }

    private void drawGameOver(Graphics2D graphics) {
        graphics.setColor(new Color(0, 0, 0, 160));
        graphics.fillRect(0, 0, ConfigConst.WIDTH, ConfigConst.HEIGHT);
        int cy = ConfigConst.HEIGHT / 2;
        graphics.setColor(ColorConst.RED);
        graphics.setFont(new Font("SansSerif", Font.BOLD, 36));
        drawCentered(graphics, "GAME OVER", cy - 10);
        if ((frame / 20) % 2 == 0) {
            graphics.setColor(ColorConst.WHITE);
            graphics.setFont(new Font("SansSerif", Font.PLAIN, 14));
            drawCentered(graphics, "PRESS Z TO RETURN", cy + 40);
        }
    }

    private void fillCircle(Graphics2D graphics, double cx, double cy, double r) {
        graphics.fillOval((int) (cx - r), (int) (cy - r), (int) (r * 2), (int) (r * 2));
    }

    private static void drawCircle(Graphics2D graphics, double cx, double cy, double r) {
        graphics.drawOval((int) (cx - r), (int) (cy - r), (int) (r * 2), (int) (r * 2));
    }

    private void drawCentered(Graphics2D g, String text, int y) {
        int w = g.getFontMetrics().stringWidth(text);
        g.drawString(text, (ConfigConst.WIDTH - w) / 2, y);
    }    

    private Input getInputFromKeys() {
        var input = new Input();
        input.up = keys[KeyEvent.VK_UP];
        input.down = keys[KeyEvent.VK_DOWN];
        input.left = keys[KeyEvent.VK_LEFT];
        input.right = keys[KeyEvent.VK_RIGHT];
        input.shot = keys[KeyEvent.VK_Z];
        input.slowDown = keys[KeyEvent.VK_SHIFT];
        var confirm = keys[KeyEvent.VK_Z];
        input.confirmPressed = confirm && !prevConfirm;
        prevConfirm = confirm;
        return input;
    }
}
