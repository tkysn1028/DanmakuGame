package game.entity;
import game.bullet.BulletType;

public class Bullet extends Entity {
    
    private BulletType bulletType;
    public BulletType bulletType() { return bulletType; }

    private double vx, vy;

    public Bullet(double x, double y, double speed, double angle, BulletType bulletType) {
        this.x = x;
        this.y = y;
        this.radius = bulletType.radius();
        this.bulletType = bulletType;
        this.vx = speed * Math.cos(angle);
        this.vy = speed * Math.sin(angle);
    }

    public void update() {
        x += vx;
        y += vy;
    }
}
