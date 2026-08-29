package game.entity;
import game.core.Input;
import game.util.ConfigConst;
import game.util.MathUtil;

public class Player extends Entity {
    public Player() { this.radius = 4; this.diagonalSpeedRate = 0.7071; this.speed = 5.0; this.iframes = 0; this.clampRate = 2.5; }

    public Player initX(double x) { this.x = x - this.radius; return this; }
    public Player initY(double y) { this.y = y - this.radius; return this; }

    private double speed;
    public Player slowDown() { speed = 2.5; return this; }
    public Player fastUp() { speed = 5.0; return this; }

    private int iframes;
    public int iframes() { return iframes; }
    public Player resetIframes() { iframes = 0; return this; }
    public Player setIframes() { iframes = 90; return this;  }

    private double diagonalSpeedRate;

    private double clampRate;
    public Player clampRate(double clampRate) { this.clampRate = clampRate; return this; }

    public void update(Input input) {
        var dx = input.right ? 1 : 0 - (input.left ? 1 : 0);
        var dy = input.down ? 1 : 0 - (input.up ? 1 : 0);
        if (input.slowDown) {
            this.slowDown();
        } else {
            this.fastUp();
        }
        var speed = this.speed;
        var radius = this.radius * clampRate;
        if (dx != 0 && dy != 0) speed *= diagonalSpeedRate;
        x = MathUtil.clamp(x + dx * speed, radius, ConfigConst.WIDTH - radius);
        y = MathUtil.clamp(y + dy * speed, radius, ConfigConst.HEIGHT - radius);
        if(iframes > 0) iframes--;
    }
}
