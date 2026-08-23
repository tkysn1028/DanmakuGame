package game.entity;

import game.util.ConfigConst;

public class Boss extends Entity {
    private int hitPoints = 2;
    public int hitPoints() { return hitPoints; }
    public void hit() {
        if (hitPoints > 0) hitPoints--;
    }
    private double retreatSpeed = 0.0;
    private static final double retreatAccelSpeed = 0.02;

    public boolean isGone() { return y < -radius * 2; }
    
    public Boss() {
        x = ConfigConst.WIDTH / 2.0;
        y = ConfigConst.HEIGHT / 5.0;
        this.radius = 30;
    }

    public void update(int frame) {
        x = ConfigConst.WIDTH / 2.0 + Math.sin(frame / 50.0) * ConfigConst.WIDTH / 4.0;;

        if (hitPoints <= 0) {
            retreatSpeed += retreatAccelSpeed;
            y -= retreatSpeed;
        }
    }
}