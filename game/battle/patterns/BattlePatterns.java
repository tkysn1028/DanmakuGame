package game.battle.patterns;
import game.battle.core.Battle;
import game.battle.entity.Enemy;
import game.battle.params.battle.Battle1;
import game.battle.scheduler.Coroutine;
import game.battle.util.GameUtil;

public class BattlePatterns {
    public static Coroutine battleRegistNormalMoonShot(Battle battle, Battle1 battle1Param) {
        return new Coroutine((yielder) -> {
            // １回目のボス
            var boss = new Enemy()
                    .hitPoints(5)
                    .radius(30);
            battle.boss = boss;
            battle.scheduler.add(MovePatterns.movePendulumAndRetrieve(boss, battle1Param.pendulumAndRetrieve));
            battle.scheduler.add(BulletPatterns.ringSpiral(battle.bulletPool, boss, battle1Param.spiral1));
            battle.scheduler.add(BulletPatterns.aimedSpread(battle.bulletPool, battle.player, boss, battle1Param.spread1));
            while (!GameUtil.isGone(boss.x, boss.y)) yielder.pause(1);
            battle.boss = null;

            // 雑魚敵２０体
            for (int i = 0; i < 10; i++) {
                var enemy = new Enemy()
                    .hitPoints(2)
                    .radius(10);
                battle.enemies.add(enemy);
                battle.scheduler.add(MovePatterns.moveDownAndCurveRight(enemy, battle1Param.downAndCurve1));
                battle.scheduler.add(BulletPatterns.aimedSpread(battle.bulletPool, battle.player, enemy, battle1Param.spread2));
                yielder.pause(battle1Param.interval);
            }

            for (int i = 0; i < 10; i++) {
                var enemy = new Enemy()
                    .hitPoints(2)
                    .radius(10);
                battle.enemies.add(enemy);
                battle.scheduler.add(MovePatterns.moveDownAndCurveLeft(enemy, battle1Param.downAndCurve2));
                battle.scheduler.add(BulletPatterns.aimedSpread(battle.bulletPool, battle.player, enemy, battle1Param.spread1));
                yielder.pause(battle1Param.interval);
            }

            while (!battle.enemies.isEmpty()) yielder.pause(1);

            // １回目のボス
            var boss2 = new Enemy()
                    .hitPoints(15)
                    .radius(30);
            battle.boss = boss2;
            battle.scheduler.add(MovePatterns.movePendulumAndRetrieve(boss2, battle1Param.pendulumAndRetrieve));
            battle.scheduler.add(BulletPatterns.ringSpiral(battle.bulletPool, boss2, battle1Param.spiral2));
            battle.scheduler.add(BulletPatterns.aimedSpread(battle.bulletPool, battle.player, boss2, battle1Param.spread1));
            while (!GameUtil.isGone(boss2.x, boss2.y)) yielder.pause(1);
            battle.enemies.remove(boss2);
            battle.boss = null;
            battle.clear();
        });
    }
}
