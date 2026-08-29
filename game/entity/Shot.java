package game.entity;
import game.shot.ShotType;

public class Shot extends Entity {
    private ShotType shotType = ShotType.smallSquareMagenta;
    public ShotType shotType() { return shotType; }

    private double vx, vy;
    private double speed = 20.0;

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
