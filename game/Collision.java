package game;

public class Collision {
    public static boolean check(double x1, double y1, double r1, double x2, double y2, double r2) {
        double dx = x1 - x2;
        double dy = y1 - y2;
        double distanceSquared = dx * dx + dy * dy;
        double radiusSum = r1 + r2;
        return distanceSquared <= radiusSum * radiusSum;
    }
}
