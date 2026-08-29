package game.battle.entity;
import game.battle.shot.ShotType;

public class Shot extends Entity {
    private ShotType shotType = ShotType.SMALL_SQUARE_MAGENTA;
    public ShotType shotType() { return shotType; }

    private double vx, vy;
    private double speed;

    private boolean expired;
    public boolean isExpired() { return expired; }
    public void expire() { this.expired = true; }

    public Shot(double x, double y, double shotSpeed) {
        this.x = x;
        this.y = y;
        this.speed = shotSpeed;
        this.vx = speed * Math.cos(Math.PI / -2);
        this.vy = speed * Math.sin(Math.PI / -2);
        this.radius = shotType.radius();
    }

    public void update() {
        x += vx;
        y += vy;
    }
}
