package game.bullet;

import java.util.ArrayList;
import java.util.List;

import game.entity.Bullet;
import game.util.GameUtil;

import java.awt.Color;

public class BulletPool {
    private List<Bullet> bullets = new ArrayList<>();

    public void pool(double x, double y, double speed, double angle, int r, Color c) {
        bullets.add(new Bullet(x, y, speed, angle, r, c));
    }

    public void update() {
        bullets.forEach(b -> b.update());
        bullets.removeIf((b) -> GameUtil.isGone(b.x, b.y));
    }

    public List<Bullet> all() {
        return bullets;
    }
}
