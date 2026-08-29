package game.params.stage;

import game.params.bullet.Spiral;
import game.params.bullet.Spread;
import game.params.move.DownAndCurve;
import game.params.move.PendulumAndRetrieve;
import game.util.ConfigConst;

public class Stage1 {
    public PendulumAndRetrieve pendulumAndRetrieve;
    public DownAndCurve downAndCurve1;
    public DownAndCurve downAndCurve2;
    public Spiral spiral1;
    public Spiral spiral2;
    public Spread spread1;
    public Spread spread2;
    public int interval;

    public static Stage1 normal() {
        var p = new Stage1();
        p.pendulumAndRetrieve = new PendulumAndRetrieve()
                    .baseXPosition(ConfigConst.WIDTH / 2.0)
                    .baseYPosition(ConfigConst.HEIGHT / 5.0)
                    .swaySpeed(0.02)
                    .amplitude(ConfigConst.WIDTH / 4.0)
                    .initEnterSpeed(3.5)
                    .enterDecelSpeed(0.985)
                    .retreatAccelSpeed(0.02);
        p.downAndCurve1 = new DownAndCurve()
                    .fromX(500)
                    .turnY(ConfigConst.HEIGHT / 2.0)
                    .speed(2.5)
                    .turnRate(0.03);
        p.downAndCurve2 = new DownAndCurve()
                    .fromX(800)
                    .turnY(ConfigConst.HEIGHT / 4.0)
                    .speed(2.5)
                    .turnRate(0.03);
        p.spiral1 = new Spiral()
                    .bulletsPerTick(32)
                    .cycles(24)
                    .speed(2.2)
                    .twistAngle(0.13)
                    .interval(0, 6, 70);
        p.spiral2 = new Spiral()
                    .bulletsPerTick(32)
                    .cycles(24)
                    .speed(2.2)
                    .twistAngle(0.13)
                    .interval(90, 6, 70);
        p.spread1 = new Spread()
                    .bulletsPerTick(5)
                    .cycles(20)
                    .speed(2.2)
                    .spread(0.5)
                    .interval(6, 40);
        p.spread2 =  new Spread()
                    .bulletsPerTick(3)
                    .cycles(5)
                    .speed(5.0)
                    .spread(0.1)
                    .interval(3, 40);
        p.interval = 20;
        return p;
    }
}
