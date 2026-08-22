package game.object;

import java.awt.Color;

import game.ConfigConst;

public class Bullet extends Entity {
    
    private Color color;
    public Color color() {
        return color;
    }

    private double vx, vy;

    public Bullet(double x, double y, double speed, double angle, int radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
        this.vx = speed * Math.cos(angle);
        this.vy = speed * Math.sin(angle);
    }

    public void update() {
        x += vx;
        y += vy;
    }

    public boolean isGone() {
        final int m = 32;
        return x < -m || x > ConfigConst.WIDTH + m || y < -m || y > ConfigConst.HEIGHT + m;
    }
}
