package game.entity;

import game.util.ConfigConst;

public class Boss extends Entity {
    private int hitPoints = 50;
    public int hitPoints() { return hitPoints; }
    public void hit() {
        if(hitPoints > 0) hitPoints--;
    }

    public Boss() {
        x = ConfigConst.WIDTH / 2.0;
        y = ConfigConst.HEIGHT / 5.0;
        this.radius = 30;
    }

    public void update(int frame) {
        x = ConfigConst.WIDTH / 2.0 + Math.sin(frame / 50.0) * ConfigConst.WIDTH / 4.0;
    }
}
