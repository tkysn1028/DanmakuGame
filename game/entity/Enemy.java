package game.entity;

public class Enemy extends Entity{
    private int hitPoints;
    public int hitPoints() { return hitPoints; }
    public void hit() { if (hitPoints > 0) hitPoints--; }

    private int maxHitPoints;
    public int maxHitPoints() { return maxHitPoints; }

    public boolean isGone() { 
        return y < -radius * 2;
    }

    public Enemy(int hitPoints, int radius) {
        System.out.println(hitPoints);
        this.hitPoints = hitPoints;
        this.maxHitPoints = hitPoints;
        System.out.println(this.hitPoints);
        this.radius = radius;
    }
}
