package game.battle.util;

import game.battle.entity.Entity;

public class GameUtil {
    public static boolean isGone(double x, double y) {
        final int m = 32;
            return x < -m || x > ConfigConst.WIDTH + m || y < -m || y > ConfigConst.HEIGHT + m;
    }

    public static boolean collisionCheck(Entity e1, Entity e2) {
        double dx = e1.x - e2.x;
        double dy = e1.y - e2.y;
        double radiusSum = e1.radius() + e1.radius();
        return dx * dx + dy * dy <= radiusSum * radiusSum;
    }    
}
