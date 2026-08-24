package game.util;

public class GameUtil {
    public static boolean isGone(double x, double y) {
        final int m = 32;
            return x < -m || x > ConfigConst.WIDTH + m || y < -m || y > ConfigConst.HEIGHT + m;
    }
}
