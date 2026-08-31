package intrusion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel{
    final boolean[] keys = new boolean[256];
    int frame = 0;
    private GameSession gameSession = new GameSession();
    private final KeyBoardInput keyBoard = new KeyBoardInput();
    public GamePanel() {
        setPreferredSize(new Dimension(1280, 800));
        setBackground(new Color(14, 12, 24));
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) System.exit(0);
                keyBoard.press(e.getKeyCode());
            }
            @Override public void keyReleased(KeyEvent e) {
                keyBoard.release(e.getKeyCode());
            }
        });

        new Timer(16, e -> {
            frame++;
            gameSession.update(keyBoard.input(), frame);
            keyBoard.endFrame();
            repaint();
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        var graphics = (Graphics2D) g0;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
}
