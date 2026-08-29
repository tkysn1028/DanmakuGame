package game.battle.patterns;

import game.battle.entities.Enemy;
import game.battle.params.move.DownAndCurve;
import game.battle.params.move.PendulumAndRetrieve;
import game.core.scheduler.Coroutine;
import game.core.util.GameUtil;

public class MovePatterns {
    public static Coroutine movePendulumAndRetrieve(Enemy enemy, PendulumAndRetrieve param) {
        return new Coroutine((yielder) -> {
            double phase = 0.0;
            double enterSpeed = param.initEnterSpeed;
            while (enemy.y < param.baseYPosition) {
                phase += param.swaySpeed;
                enemy.x = param.baseXPosition + Math.sin(phase) * param.amplitude;
                enemy.y += enterSpeed;
                enterSpeed *= param.enterDecelSpeed;
                yielder.pause(1);
            }
            enemy.y = param.baseYPosition;
            
            while (enemy.hitPoints() > 0) {
                phase += param.swaySpeed;
                enemy.x = param.baseXPosition + Math.sin(phase) * param.amplitude;
                yielder.pause(1);
            }

            double retreatSpeed = 0.0;
            while (!GameUtil.isGone(enemy.x, enemy.y)) {
                phase += param.swaySpeed;
                enemy.x = param.baseXPosition + Math.sin(phase) * param.amplitude;
                retreatSpeed += param.retreatAccelSpeed;
                enemy.y -= retreatSpeed;
                yielder.pause(1);
            }
        });
    }

    public static Coroutine moveDownAndCurveLeft(Enemy enemy, DownAndCurve param) {
        return new Coroutine((yielder) -> {
            enemy.x = param.fromX;
            enemy.y = -enemy.radius();
            double angle = Math.PI / 2;
            while (!GameUtil.isGone(enemy.x, enemy.y)) {
                if (enemy.y > param.turnY && angle < Math.PI) {
                    angle = Math.min(angle + param.turnRate, Math.PI);
                }
                enemy.x += Math.cos(angle) * param.speed;
                enemy.y += Math.sin(angle) * param.speed;
                yielder.pause(1);
            }
        });
    }

    public static Coroutine moveDownAndCurveRight(Enemy enemy, DownAndCurve param) {
        return new Coroutine((yielder) -> {
            enemy.x = param.fromX;
            enemy.y = -enemy.radius();
            double angle = Math.PI / 2;
            while (!GameUtil.isGone(enemy.x, enemy.y)) {
                if (enemy.y > param.turnY && angle > 0) {
                    angle = Math.max(angle - param.turnRate, 0);
                }
                enemy.x += Math.cos(angle) * param.speed;
                enemy.y += Math.sin(angle) * param.speed;
                yielder.pause(1);
            }
        });
    }
}
