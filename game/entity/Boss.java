package game.entity;

public class Boss extends Entity {
    private int hitPoints = 5;
    public int hitPoints() { return hitPoints; }
    public void hit() { if (hitPoints > 0) hitPoints--; }

    public boolean isGone() { return y < -radius * 2; }

    public Boss() {
        this.radius = 30;
    }
}