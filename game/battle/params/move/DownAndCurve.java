package game.battle.params.move;

public class DownAndCurve {
    public double fromX;
    public double turnY;
    public double speed;
    public double turnRate;
    public double targetAngle;

    public DownAndCurve fromX(double fromX) { this.fromX = fromX; return this; }
    public DownAndCurve turnY(double turnY) { this.turnY = turnY; return this; }
    public DownAndCurve speed(double speed) { this.speed = speed; return this; }
    public DownAndCurve turnRate(double turnRate) { this.turnRate = turnRate; return this; }
}
