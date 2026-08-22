package game.object;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

public class BulletPool {
    public List<Bullet> bullets = new ArrayList<>();

    public void pool(double x, double y, double speed, double angle, int r, Color c) {
        bullets.add(new Bullet(x, y, speed, angle, r, c));
    }

    public void update() {
        bullets.stream().forEach(b -> b.update());
    }

    public List<Bullet> all() {
        return bullets;
    }
}
