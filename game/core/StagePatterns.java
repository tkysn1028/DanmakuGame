package game.core;
import game.entity.Enemy;
import game.params.bullet.Spiral;
import game.params.bullet.Spread;
import game.params.move.DownAndCurve;
import game.params.move.PendulumAndRetrieve;
import game.patterns.BulletPatterns;
import game.patterns.MovePatterns;
import game.scheduler.Coroutine;
import game.util.ConfigConst;
import game.util.GameUtil;

public class StagePatterns {
    public static Coroutine stage1(Game game) {
        return new Coroutine((yielder) -> {
            // １回目のボス
            var boss = new Enemy(5, 30);
            game.boss = boss;
            game.scheduler.add(MovePatterns.movePendulumAndRetrieve(boss, new PendulumAndRetrieve()
                    .baseXPosition(ConfigConst.WIDTH / 2.0)
                    .baseYPosition(ConfigConst.HEIGHT / 5.0)
                    .swaySpeed(0.02)
                    .amplitude(ConfigConst.WIDTH / 4.0)
                    .initEnterSpeed(3.5)
                    .enterDecelSpeed(0.985)
                    .retreatAccelSpeed(0.02)));
            game.scheduler.add(BulletPatterns.ringSpiral(game.bulletPool, boss, new Spiral()
                    .bulletsPerTick(32)
                    .cycles(24)
                    .speed(2.2)
                    .twistAngle(0.13)
                    .interval(6, 70)));
            game.scheduler.add(BulletPatterns.aimedSpread(game.bulletPool, game.player, boss, new Spread()
                    .bulletsPerTick(5)
                    .cycles(20)
                    .speed(2.2)
                    .spread(0.5)
                    .interval(6, 40)));
            while (!GameUtil.isGone(boss.x, boss.y)) yielder.pause(1);
            game.boss = null;

            // 雑魚敵２０体
            for (int i = 0; i < 10; i++) {
                var enemy = new Enemy(2, 10);
                game.enemies.add(enemy);
                game.scheduler.add(MovePatterns.moveDownAndCurveRight(enemy, new DownAndCurve()
                        .fromX(500)
                        .turnY(ConfigConst.HEIGHT / 4.0)
                        .speed(2.5)
                        .turnRate(0.03)));
                game.scheduler.add(BulletPatterns.aimedSpread(game.bulletPool, game.player, enemy, new Spread()
                        .bulletsPerTick(3)
                        .cycles(5)
                        .speed(5.0)
                        .spread(0.1)
                        .interval(3, 40)));
                yielder.pause(20);
            }

            for (int i = 0; i < 10; i++) {
                var enemy = new Enemy(2, 10);
                game.enemies.add(enemy);
                game.scheduler.add(MovePatterns.moveDownAndCurveLeft(enemy, new DownAndCurve()
                        .fromX(800)
                        .turnY(ConfigConst.HEIGHT / 4.0)
                        .speed(2.5)
                        .turnRate(0.03)
                        .targetAngle(Math.PI)));
                game.scheduler.add(BulletPatterns.aimedSpread(game.bulletPool, game.player, enemy, new Spread()
                        .bulletsPerTick(5)
                        .cycles(20)
                        .speed(2.2)
                        .spread(0.5)
                        .interval(6, 40)));
                yielder.pause(20);
            }

            while (!game.enemies.isEmpty()) {
                yielder.pause(1);
            }
            // １回目のボス
            var boss2 = new Enemy(15, 30);
            game.enemies.add(boss2);
            game.boss = boss2;
            game.scheduler.add(MovePatterns.movePendulumAndRetrieve(boss2, new PendulumAndRetrieve()
                    .baseXPosition(ConfigConst.WIDTH / 2.0)
                    .baseYPosition(ConfigConst.HEIGHT / 5.0)
                    .swaySpeed(0.02)
                    .amplitude(ConfigConst.WIDTH / 4.0)
                    .initEnterSpeed(3.5)
                    .enterDecelSpeed(0.985)
                    .retreatAccelSpeed(0.02)));
            game.scheduler.add(BulletPatterns.ringSpiral(game.bulletPool, boss2, new Spiral()
                    .bulletsPerTick(32)
                    .cycles(24)
                    .speed(2.2)
                    .twistAngle(0.13)
                    .interval(6, 70)));
            game.scheduler.add(BulletPatterns.aimedSpread(game.bulletPool, game.player, boss2, new Spread()
                    .bulletsPerTick(5)
                    .cycles(20)
                    .speed(2.2)
                    .spread(0.5)
                    .interval(6, 40)));
            while (!GameUtil.isGone(boss2.x, boss2.y)) yielder.pause(1);
            game.enemies.remove(boss2);
            game.boss = null;
            game.gameClear();
        });
    }
}
