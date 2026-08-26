package game.params.move;

public class PendulumAndRetrieve {
    public double baseXPosition;
    public double baseYPosition;
    public double enterDecelSpeed;
    public double retreatAccelSpeed;
    public double swaySpeed;
    public double amplitude;
    public double initEnterSpeed;

    public PendulumAndRetrieve baseXPosition(double baseXPosition) { this.baseXPosition = baseXPosition; return this; }
    public PendulumAndRetrieve baseYPosition(double baseYPosition) { this.baseYPosition = baseYPosition; return this; }
    public PendulumAndRetrieve enterDecelSpeed(double enterDecelSpeed) { this.enterDecelSpeed = enterDecelSpeed; return this; }
    public PendulumAndRetrieve retreatAccelSpeed(double retreatAccelSpeed) { this.retreatAccelSpeed = retreatAccelSpeed; return this; }
    public PendulumAndRetrieve swaySpeed(double swaySpeed) { this.swaySpeed = swaySpeed; return this; }
    public PendulumAndRetrieve amplitude(double amplitude) { this.amplitude = amplitude; return this; }
    public PendulumAndRetrieve initEnterSpeed(double initEnterSpeed) { this.initEnterSpeed = initEnterSpeed; return this; }
}
