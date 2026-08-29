package game.battle.entities;

import java.util.ArrayList;
import java.util.List;

import game.battle.enums.BulletType;
import game.core.util.GameUtil;

public class BulletPool {
    private List<Bullet> bullets = new ArrayList<>();

    public void pool(double x, double y, double speed, double angle, BulletType bt) {
        bullets.add(new Bullet(x, y, speed, angle, bt));
    }

    public void update() {
        bullets.forEach(b -> b.update());
        bullets.removeIf((b) -> GameUtil.isGone(b.x, b.y));
    }

    public List<Bullet> all() {
        return bullets;
    }
}
