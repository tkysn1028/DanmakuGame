package game.entity;

import java.awt.Color;

public class Shot extends Entity {
    private Color color = Color.MAGENTA;
    public Color color() { return color; }

    private double vx, vy;
    private double speed = 10.0;

    private boolean expired;
    public boolean isExpired() { return expired; }
    public void expire() { this.expired = true; }

    public Shot(double x, double y) {
        this.x = x;
        this.y = y;
        this.vx = speed * Math.cos(Math.PI / -2);
        this.vy = speed * Math.sin(Math.PI / -2);
        this.radius = 4;
    }

    public void update() {
        x += vx;
        y += vy;
    }
}
