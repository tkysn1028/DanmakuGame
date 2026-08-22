package game.object;

import java.awt.Color;

import game.ConfigConst;

public class Bullet {
    public double x, y;

    public Bullet(double x, double y, double speed, double angle, int r, Color c) {
        this.x = x;
        this.y = y;
    }

    public void update() {

    }
    boolean isGone() {
        final int m = 32;
        return x < -m || x > ConfigConst.WIDTH + m || y < -m || y > ConfigConst.HEIGHT + m;
    }
}
