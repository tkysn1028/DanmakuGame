package game;

import game.object.Entity;

public class Collision {
    public static boolean check(Entity e1, Entity e2) {
        double dx = e1.x - e2.x;
        double dy = e1.y - e2.y;
        double radiusSum = e1.radius() + e1.radius();
        return dx * dx + dy * dy <= radiusSum * radiusSum;
    }
}
