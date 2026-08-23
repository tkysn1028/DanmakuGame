package game.patterns;

import game.entity.Boss;
import game.scheduler.Coroutine;
import game.util.ConfigConst;

public class MovePatterns {
    public static Coroutine moveBossPendulum(Boss boss) {
        double baseXPosition = ConfigConst.WIDTH / 2.0;
        double baseYPosition = ConfigConst.HEIGHT / 5.0;
        double enterDecelSpeed = 0.985;
        double retreatAccelSpeed = 0.02;
        double swaySpeed = 0.02; 
        double amplitude = ConfigConst.WIDTH / 4.0;

        return new Coroutine((yielder) -> {
            double phase = 0.0;
            double enterSpeed = 3.5;
            while (boss.y < baseYPosition) {
                phase += swaySpeed;
                boss.x = baseXPosition + Math.sin(phase) * amplitude;
                boss.y += enterSpeed;
                enterSpeed *= enterDecelSpeed;
                yielder.pause(1);
            }
            boss.y = baseYPosition;
            
            while (boss.hitPoints() > 0) {
                phase += swaySpeed;
                boss.x = baseXPosition + Math.sin(phase) * amplitude;
                yielder.pause(1);
            }

            double retreatSpeed = 0.0;
            while (!boss.isGone()) {
                phase += swaySpeed;
                boss.x = baseXPosition + Math.sin(phase) * amplitude;
                retreatSpeed += retreatAccelSpeed;
                boss.y -= retreatSpeed;
                yielder.pause(1);
            }
        });
    }
}
