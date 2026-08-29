package game.battle.patterns;

import game.battle.bullet.BulletPool;
import game.battle.bullet.BulletType;
import game.battle.entity.Enemy;
import game.battle.entity.Player;
import game.battle.params.bullet.Spiral;
import game.battle.params.bullet.Spread;
import game.core.scheduler.Coroutine;
import game.core.util.GameUtil;

public class BulletPatterns {
    private BulletPatterns() {}

    public static Coroutine ringSpiral(BulletPool bulletPool, Enemy enemy, Spiral param) {
        return new Coroutine((yielder) -> {
            double baseAngle = 0;
            while (enemy.hitPoints() > 0) {
                yielder.pause(param.initInterval);
                for (int i = 0; i < param.cycles; i++) {
                    for (int j = 0; j < param.bulletsPerTick; j++) {
                        double angle = baseAngle + j * Math.PI * 2 / param.bulletsPerTick;
                        bulletPool.pool(enemy.x, enemy.y, param.bulletSpeed, angle, BulletType.SMALL_CIRCLE_PINK);
                    }
                    baseAngle += param.twistAngle;
                    yielder.pause(param.intervalPerTick);
                }
                yielder.pause(param.intervalPerLoop);
            }
        });
    }

    public static Coroutine aimedSpread(BulletPool bulletPool, Player player, Enemy enemy, Spread param) {
        return new Coroutine((yielder) -> {
            while (enemy.hitPoints() > 0 && !GameUtil.isGone(enemy.x, enemy.y)) {
                yielder.pause(param.intervalPerLoop);
                for (int i = 0; i < param.cycles; i++) {
                    double aim = Math.atan2(player.y - enemy.y, player.x - enemy.x);
                    for (int j = 0; j < param.bulletsPerTick; j++) {
                        double angle = aim + (j - param.bulletsPerTick / 2.0) * param.bulletSpread;
                        bulletPool.pool(enemy.x, enemy.y, param.bulletSpeed, angle, BulletType.SMALL_CIRCLE_LIGHT_BLUE);
                    }
                    yielder.pause(param.intervalPerTick);
                }
            }
        });
    }
}
