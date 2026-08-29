package game.patterns;
import game.core.Stage;
import game.entity.Enemy;
import game.params.stage.Stage1;
import game.scheduler.Coroutine;
import game.util.GameUtil;

public class StagePatterns {
    public static Coroutine stage1(Stage stage, Stage1 stage1Param) {
        return new Coroutine((yielder) -> {
            // １回目のボス
            var boss = new Enemy(5, 30);
            stage.boss = boss;
            stage.scheduler.add(MovePatterns.movePendulumAndRetrieve(boss, stage1Param.pendulumAndRetrieve));
            stage.scheduler.add(BulletPatterns.ringSpiral(stage.bulletPool, boss, stage1Param.spiral1));
            stage.scheduler.add(BulletPatterns.aimedSpread(stage.bulletPool, stage.player, boss, stage1Param.spread1));
            while (!GameUtil.isGone(boss.x, boss.y)) yielder.pause(1);
            stage.boss = null;

            // 雑魚敵２０体
            for (int i = 0; i < 10; i++) {
                var enemy = new Enemy(2, 10);
                stage.enemies.add(enemy);
                stage.scheduler.add(MovePatterns.moveDownAndCurveRight(enemy, stage1Param.downAndCurve1));
                stage.scheduler.add(BulletPatterns.aimedSpread(stage.bulletPool, stage.player, enemy, stage1Param.spread2));
                yielder.pause(stage1Param.interval);
            }

            for (int i = 0; i < 10; i++) {
                var enemy = new Enemy(2, 10);
                stage.enemies.add(enemy);
                stage.scheduler.add(MovePatterns.moveDownAndCurveLeft(enemy, stage1Param.downAndCurve2));
                stage.scheduler.add(BulletPatterns.aimedSpread(stage.bulletPool, stage.player, enemy, stage1Param.spread1));
                yielder.pause(stage1Param.interval);
            }

            while (!stage.enemies.isEmpty()) yielder.pause(1);

            // １回目のボス
            var boss2 = new Enemy(15, 30);
            stage.boss = boss2;
            stage.scheduler.add(MovePatterns.movePendulumAndRetrieve(boss2, stage1Param.pendulumAndRetrieve));
            stage.scheduler.add(BulletPatterns.ringSpiral(stage.bulletPool, boss2, stage1Param.spiral2));
            stage.scheduler.add(BulletPatterns.aimedSpread(stage.bulletPool, stage.player, boss2, stage1Param.spread1));
            while (!GameUtil.isGone(boss2.x, boss2.y)) yielder.pause(1);
            stage.enemies.remove(boss2);
            stage.boss = null;
            stage.clear();
        });
    }
}
