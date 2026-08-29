package game.battle.enums;

public enum ShotType {
    SMALL_SQUARE_MAGENTA(4);
    private final int radius;
    ShotType(int radius) {
        this.radius = radius;
    }

    public int radius() {
        return radius;
    }
}
