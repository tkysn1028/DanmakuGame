/*
 * マップ画面プロトタイプ（単体で動く。既存プロジェクトとは独立）
 *
 *   実行: java MapScreen.java
 *
 * 操作: 矢印キーで隣接ノードへ移動 / Esc で終了
 *
 * ノードは 10x10 の格子状に並び、リンク（線）で繋がっている。
 * 全ノードを繋いだ格子だと経路選択が発生しないので、
 * ランダムDFSで全域木を作ってから余分な辺を確率的に戻している。
 * これで「連結は保証されるが、まっすぐには行けない」網ができる。
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class MapScreen extends JPanel {

    // ------------------------------------------------------------------
    static final int COLS = 10, ROWS = 10;
    static final int MARGIN = 46;
    static final int SPACING = 52;
    static final int WIDTH  = MARGIN * 2 + (COLS - 1) * SPACING;   // 560
    static final int HEIGHT = MARGIN * 2 + (ROWS - 1) * SPACING + 60;
    static final int FPS = 60;
    static final int MOVE_FRAMES = 14;      // 1ノード移動にかけるフレーム数
    static final long MAP_SEED = 20260830L; // 固定シード。毎回同じマップになる

    static final Color BG          = new Color(14, 12, 24);
    static final Color LINK        = new Color(52, 60, 92);
    static final Color LINK_ACTIVE = new Color(120, 210, 255);
    static final Color NODE        = new Color(70, 80, 110);
    static final Color NODE_SEEN   = new Color(120, 140, 180);
    static final Color CURRENT     = new Color(255, 70, 70);
    static final Color HUD         = new Color(150, 150, 170);

    // ------------------------------------------------------------------
    // リンクは「右隣へ」「下隣へ」の2枚で表現する。
    // これで全ての格子辺を重複なく持てる。
    private final boolean[][] linkRight = new boolean[ROWS][COLS];
    private final boolean[][] linkDown  = new boolean[ROWS][COLS];
    private final boolean[][] seen      = new boolean[ROWS][COLS];

    private final boolean[] keys     = new boolean[256];
    private final boolean[] prevKeys = new boolean[256];

    private int row = 0, col = 0;           // 現在地
    private int toRow = 0, toCol = 0;       // 移動先
    private boolean moving = false;
    private int moveFrame = 0;
    private int turns = 0;
    private int frame = 0;

    // ------------------------------------------------------------------
    public MapScreen() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(BG);
        setFocusable(true);

        generateLinks();
        seen[row][col] = true;

        addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                int c = e.getKeyCode();
                if (c == KeyEvent.VK_ESCAPE) System.exit(0);
                if (c >= 0 && c < keys.length) keys[c] = true;
            }
            @Override public void keyReleased(KeyEvent e) {
                int c = e.getKeyCode();
                if (c >= 0 && c < keys.length) keys[c] = false;
            }
        });

        new Timer(1000 / FPS, e -> {
            frame++;
            update();
            System.arraycopy(keys, 0, prevKeys, 0, keys.length);
            repaint();
        }).start();
    }

    private boolean pressed(int keyCode) {
        return keys[keyCode] && !prevKeys[keyCode];
    }

    // ------------------------------------------------------------------
    // マップ生成
    // ------------------------------------------------------------------
    private void generateLinks() {
        Random rng = new Random(MAP_SEED);

        // 1) ランダムDFSで全域木を作る（連結性の保証）
        boolean[][] visited = new boolean[ROWS][COLS];
        Deque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[]{0, 0});
        visited[0][0] = true;

        while (!stack.isEmpty()) {
            int[] cur = stack.peek();
            int r = cur[0], c = cur[1];

            List<int[]> cand = new ArrayList<>();
            if (r > 0        && !visited[r - 1][c]) cand.add(new int[]{r - 1, c});
            if (r < ROWS - 1 && !visited[r + 1][c]) cand.add(new int[]{r + 1, c});
            if (c > 0        && !visited[r][c - 1]) cand.add(new int[]{r, c - 1});
            if (c < COLS - 1 && !visited[r][c + 1]) cand.add(new int[]{r, c + 1});

            if (cand.isEmpty()) {
                stack.pop();
                continue;
            }
            Collections.shuffle(cand, rng);
            int[] next = cand.get(0);
            connect(r, c, next[0], next[1]);
            visited[next[0]][next[1]] = true;
            stack.push(next);
        }

        // 2) 余った辺を確率的に戻す（迂回路と分岐を作る）
        final double extraRate = 0.40;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (c < COLS - 1 && !linkRight[r][c] && rng.nextDouble() < extraRate) {
                    linkRight[r][c] = true;
                }
                if (r < ROWS - 1 && !linkDown[r][c] && rng.nextDouble() < extraRate) {
                    linkDown[r][c] = true;
                }
            }
        }
    }

    private void connect(int r1, int c1, int r2, int c2) {
        if (r1 == r2) {
            linkRight[r1][Math.min(c1, c2)] = true;
        } else {
            linkDown[Math.min(r1, r2)][c1] = true;
        }
    }

    private boolean hasLink(int r1, int c1, int r2, int c2) {
        if (r2 < 0 || r2 >= ROWS || c2 < 0 || c2 >= COLS) return false;
        if (r1 == r2) return linkRight[r1][Math.min(c1, c2)];
        if (c1 == c2) return linkDown[Math.min(r1, r2)][c1];
        return false;
    }

    // ------------------------------------------------------------------
    // 更新
    // ------------------------------------------------------------------
    private void update() {
        if (moving) {
            moveFrame++;
            if (moveFrame >= MOVE_FRAMES) {
                row = toRow;
                col = toCol;
                seen[row][col] = true;
                moving = false;
                turns++;
            }
            return;   // 移動中は入力を受け付けない
        }

        if      (pressed(KeyEvent.VK_UP))    tryMove(row - 1, col);
        else if (pressed(KeyEvent.VK_DOWN))  tryMove(row + 1, col);
        else if (pressed(KeyEvent.VK_LEFT))  tryMove(row, col - 1);
        else if (pressed(KeyEvent.VK_RIGHT)) tryMove(row, col + 1);
    }

    private void tryMove(int r, int c) {
        if (!hasLink(row, col, r, c)) return;
        toRow = r;
        toCol = c;
        moveFrame = 0;
        moving = true;
    }

    // ------------------------------------------------------------------
    // 描画
    // ------------------------------------------------------------------
    private static int px(int col) { return MARGIN + col * SPACING; }
    private static int py(int row) { return MARGIN + row * SPACING; }

    /** 現在のプレイヤー位置（移動中は補間する） */
    private double playerX() {
        if (!moving) return px(col);
        return lerp(px(col), px(toCol), progress());
    }

    private double playerY() {
        if (!moving) return py(row);
        return lerp(py(row), py(toRow), progress());
    }

    private double progress() {
        double t = (double) moveFrame / MOVE_FRAMES;
        return t * t * (3 - 2 * t);      // smoothstep。等速より自然に見える
    }

    private static double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D) g0;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                           RenderingHints.VALUE_ANTIALIAS_ON);

        drawLinks(g);
        drawNodes(g);
        drawPlayer(g);
        drawHud(g);
    }

    private void drawLinks(Graphics2D g) {
        g.setStroke(new BasicStroke(2f));
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (linkRight[r][c]) {
                    g.setColor(isTraversing(r, c, r, c + 1) ? LINK_ACTIVE : LINK);
                    g.drawLine(px(c), py(r), px(c + 1), py(r));
                }
                if (linkDown[r][c]) {
                    g.setColor(isTraversing(r, c, r + 1, c) ? LINK_ACTIVE : LINK);
                    g.drawLine(px(c), py(r), px(c), py(r + 1));
                }
            }
        }
        g.setStroke(new BasicStroke(1f));
    }

    /** 今まさに通過中のリンクかどうか（線を光らせるため） */
    private boolean isTraversing(int r1, int c1, int r2, int c2) {
        if (!moving) return false;
        return (r1 == row && c1 == col && r2 == toRow && c2 == toCol)
            || (r1 == toRow && c1 == toCol && r2 == row && c2 == col);
    }

    private void drawNodes(Graphics2D g) {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                boolean isCurrent = (!moving && r == row && c == col);
                g.setColor(isCurrent ? CURRENT : (seen[r][c] ? NODE_SEEN : NODE));
                int rad = isCurrent ? 8 : 5;
                fillCircle(g, px(c), py(r), rad);
            }
        }
    }

    private void drawPlayer(Graphics2D g) {
        double x = playerX(), y = playerY();

        // 移動中だけ、動いているマーカーを描く
        if (moving) {
            g.setColor(CURRENT);
            fillCircle(g, x, y, 6);
        }

        // 現在地を示す輪。呼吸するように脈動させる
        double pulse = 12 + Math.sin(frame / 8.0) * 2;
        g.setColor(new Color(255, 70, 70, 150));
        drawCircle(g, x, y, pulse);
    }

    private void drawHud(Graphics2D g) {
        g.setColor(HUD);
        g.setFont(new Font("Monospaced", Font.PLAIN, 12));
        String pos = moving
                ? String.format("NODE %02d-%02d -> %02d-%02d", row, col, toRow, toCol)
                : String.format("NODE %02d-%02d", row, col);
        g.drawString(String.format("TURN %3d    %s", turns, pos), 12, HEIGHT - 18);
    }

    private static void fillCircle(Graphics2D g, double cx, double cy, double r) {
        g.fillOval((int) (cx - r), (int) (cy - r), (int) (r * 2), (int) (r * 2));
    }

    private static void drawCircle(Graphics2D g, double cx, double cy, double r) {
        g.drawOval((int) (cx - r), (int) (cy - r), (int) (r * 2), (int) (r * 2));
    }

    // ------------------------------------------------------------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("network map");
            MapScreen panel = new MapScreen();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.add(panel);
            f.pack();
            f.setResizable(false);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
            panel.requestFocusInWindow();
        });
    }
}
