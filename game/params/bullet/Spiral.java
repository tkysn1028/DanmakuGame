package game.params.bullet;

public class Spiral {
    public int bulletsPerTick;
    public int cycles;
    public double bulletSpeed;
    public double twistAngle;
    public int intervalPerTick;
    public int intervalPerLoop;

    public Spiral bulletsPerTick(int bulletsPerTick) { this.bulletsPerTick = bulletsPerTick; return this; }
    public Spiral cycles(int cycles) { this.cycles = cycles; return this; }
    public Spiral speed(double bulletSpeed) { this.bulletSpeed = bulletSpeed; return this; }
    public Spiral twistAngle(double twistAngle) { this.twistAngle = twistAngle; return this; }
    public Spiral interval(int intervalPerTick, int intervalPerLoop) { this.intervalPerTick = intervalPerTick; this.intervalPerLoop = intervalPerLoop; return this; }
}
