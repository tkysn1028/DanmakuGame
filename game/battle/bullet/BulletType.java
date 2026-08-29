package game.battle.bullet;

public enum BulletType {
    SMALL_CIRCLE_PINK(4),
    SMALL_CIRCLE_LIGHT_BLUE(4);

    private final int radius;

    BulletType(int radius) {
        this.radius = radius;
    }

    public int radius() {
        return radius;
    }
}