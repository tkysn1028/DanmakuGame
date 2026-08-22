package game.object;

import game.ConfigConst;
import game.MathUtil;

public class Player {

    public double x = (ConfigConst.WIDTH - ConfigConst.PLAYER_RADIUS) / 2.0;
    public double y = ConfigConst.HEIGHT - ConfigConst.PLAYER_RADIUS;
    public int hitPoints = 3;
    public int iframes = 0;

    private double diagonalSpeedRate = 0.7071;

    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public void update(Input input) {
        var dx = input.right ? 1 : 0 - (input.left ? 1 : 0);
        var dy = input.down ? 1 : 0 - (input.up ? 1 : 0);
        var speed = ConfigConst.PLAYER_SPEED;
        if (dx != 0 && dy != 0) speed *= diagonalSpeedRate;
        x = MathUtil.clamp(x + dx * speed, ConfigConst.PLAYER_RADIUS, ConfigConst.WIDTH - ConfigConst.PLAYER_RADIUS);
        y = MathUtil.clamp(y + dy * speed, ConfigConst.PLAYER_RADIUS, ConfigConst.HEIGHT - ConfigConst.PLAYER_RADIUS);
        if(iframes > 0) iframes--;
    }
}
