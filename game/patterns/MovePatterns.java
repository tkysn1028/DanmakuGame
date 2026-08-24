package game.patterns;

import game.entity.Enemy;
import game.scheduler.Coroutine;
import game.util.ConfigConst;
import game.util.GameUtil;

public class MovePatterns {
    public static Coroutine moveBossPendulum(Enemy boss) {
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
            while (!GameUtil.isGone(boss.x, boss.y)) {
                phase += swaySpeed;
                boss.x = baseXPosition + Math.sin(phase) * amplitude;
                retreatSpeed += retreatAccelSpeed;
                boss.y -= retreatSpeed;
                yielder.pause(1);
            }
        });
    }

    public static Coroutine moveStraightAndLeftCurveFromTop(Enemy enemy, double fromX) {
        final double speed = 2.5;
        final double turnY = ConfigConst.HEIGHT / 4.0;
        final double turnRate = 0.03;
        final double targetAngle = Math.PI;

        return new Coroutine((yielder) -> {
            enemy.x = fromX;
            enemy.y = -enemy.radius();
            double angle = Math.PI / 2;
            while (!GameUtil.isGone(enemy.x, enemy.y)) {
                if (enemy.y > turnY && angle < targetAngle) {
                    angle = Math.min(angle + turnRate, targetAngle);
                }
                enemy.x += Math.cos(angle) * speed;
                enemy.y += Math.sin(angle) * speed;
                yielder.pause(1);
            }
        });
    }

    public static Coroutine moveStraightAndRightCurveFromTop(Enemy enemy, double fromX) {
        final double speed = 2.5;
        final double turnY = ConfigConst.HEIGHT / 2.0;
        final double turnRate = 0.03;
        final double targetAngle = 0;

        return new Coroutine((yielder) -> {
            enemy.x = fromX;
            enemy.y = -enemy.radius();
            double angle = Math.PI / 2;
            while (!GameUtil.isGone(enemy.x, enemy.y)) {
                if (enemy.y > turnY && angle > targetAngle) {
                    angle = Math.max(angle - turnRate, targetAngle);
                }
                enemy.x += Math.cos(angle) * speed;
                enemy.y += Math.sin(angle) * speed;
                yielder.pause(1);
            }
        });
    }
}
