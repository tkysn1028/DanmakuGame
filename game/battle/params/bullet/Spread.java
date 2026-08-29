package game.battle.params.bullet;

public class Spread {
    public int bulletsPerTick;
    public int cycles;
    public double bulletSpeed;
    public double bulletSpread;
    public int intervalPerTick;
    public int intervalPerLoop;

    public Spread bulletsPerTick(int bulletsPerTick) { this.bulletsPerTick = bulletsPerTick; return this; }
    public Spread cycles(int cycles) { this.cycles = cycles; return this; }
    public Spread speed(double bulletSpeed) { this.bulletSpeed = bulletSpeed; return this; }
    public Spread spread(double bulletSpread) { this.bulletSpread = bulletSpread; return this; }
    public Spread interval(int intervalPerTick, int intervalPerLoop) { this.intervalPerTick = intervalPerTick; this.intervalPerLoop = intervalPerLoop; return this; }
}
