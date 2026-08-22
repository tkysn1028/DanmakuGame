package game.object;

import game.ConfigConst;

public class Boss {
    public double x = ConfigConst.WIDTH / 2.0;
    public double y = ConfigConst.HEIGHT / 5.0;

    private int radius = 30;
    public int radius() {
        return radius;
    }

    public void update(int frame) {
        x = ConfigConst.WIDTH / 2.0 + Math.sin(frame / 90.0) * ConfigConst.WIDTH / 4.0;
    }
}
