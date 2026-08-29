package game.bullet;

import java.util.ArrayList;
import java.util.List;

import game.entity.Bullet;
import game.util.GameUtil;

public class BulletPool {
    private List<Bullet> bullets = new ArrayList<>();

    public void pool(double x, double y, double speed, double angle, int r, BulletType bt) {
        bullets.add(new Bullet(x, y, speed, angle, r, bt));
    }

    public void update() {
        bullets.forEach(b -> b.update());
        bullets.removeIf((b) -> GameUtil.isGone(b.x, b.y));
    }

    public List<Bullet> all() {
        return bullets;
    }
}
