package game.battle.entity;

public class Enemy extends Entity{
    private int hitPoints;
    public int hitPoints() { return hitPoints; }
    public void hit() { if (hitPoints > 0) hitPoints--; }
    public Enemy radius(int radius) { this.radius = radius; return this; }

    public Enemy hitPoints(int hitPoints) { this.hitPoints = hitPoints; return this;}
}
