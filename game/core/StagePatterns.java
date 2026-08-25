package game.core;
import game.entity.Enemy;
import game.patterns.BulletPatterns;
import game.patterns.MovePatterns;
import game.scheduler.Coroutine;
import game.util.GameUtil;

public class StagePatterns {
    public static Coroutine stage1(Game game) {
        return new Coroutine((yielder) -> {
            // １回目のボス
            var boss = new Enemy(5, 30);
            game.boss = boss;
            game.scheduler.add(MovePatterns.movePendulum(boss));
            game.scheduler.add(BulletPatterns.ringSpiral(game.bulletPool, boss));
            game.scheduler.add(BulletPatterns.aimedSpread(game.bulletPool, game.player, boss));
            while (!GameUtil.isGone(boss.x, boss.y)) yielder.pause(1);
            game.boss = null;

            // 雑魚敵２０体
            for (int i = 0; i < 10; i++) {
                var enemy = new Enemy(2, 10);
                game.enemies.add(enemy);
                var fromX = 500;
                game.scheduler.add(MovePatterns.moveDownAndCurveRight(enemy, fromX));
                game.scheduler.add(BulletPatterns.aimedSpread(game.bulletPool, game.player, enemy));
                yielder.pause(20);
            }

            for (int i = 0; i < 10; i++) {
                var enemy = new Enemy(2, 10);
                game.enemies.add(enemy);
                var fromX = 800;
                game.scheduler.add(MovePatterns.moveDownAndCurveLeft(enemy, fromX));
                game.scheduler.add(BulletPatterns.aimedSpread(game.bulletPool, game.player, enemy));
                yielder.pause(20);
            }

            while (!game.enemies.isEmpty()) {
                yielder.pause(1);
            }
            // １回目のボス
            var boss2 = new Enemy(15, 30);
            game.enemies.add(boss2);
            game.boss = boss2;
            game.scheduler.add(MovePatterns.movePendulum(boss2));
            game.scheduler.add(BulletPatterns.ringSpiral(game.bulletPool, boss2));
            game.scheduler.add(BulletPatterns.aimedSpread(game.bulletPool, game.player, boss2));
            while (!GameUtil.isGone(boss2.x, boss2.y)) yielder.pause(1);
            game.enemies.remove(boss2);
            game.boss = null;
            game.gameClear();
        });
    }
}
