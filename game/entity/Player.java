package game.entity;
import game.core.Input;
import game.util.ConfigConst;
import game.util.MathUtil;

public class Player extends Entity {
    public Player() {
        x = (ConfigConst.WIDTH - this.radius) / 2.0;
        y = ConfigConst.HEIGHT - this.radius;
        this.radius = 4;
    }

    private double speed = 5.0;
    public double speed() { return speed; }
    public void slowDown() { speed = 2.5; }
    public void fastUp() { speed = 5.0; }

    private int hitPoints = 1;
    public int hitPoints() { return hitPoints; }
    public void hit() { if( hitPoints > 0) hitPoints--; }

    private int iframes = 0;
    public int iframes() { return iframes; }
    public void setIframes() { iframes = 90; }

    private double diagonalSpeedRate = 0.7071;
    private double clampRate = 2.5;

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
