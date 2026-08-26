package game.patterns;

import game.bullet.BulletPool;
import game.entity.Enemy;
import game.entity.Player;
import game.params.bullet.Spiral;
import game.params.bullet.Spread;
import game.scheduler.Coroutine;
import game.util.GameUtil;

import java.awt.Color;

public class BulletPatterns {
    private BulletPatterns() {}

    public static Coroutine ringSpiral(BulletPool bulletPool, Enemy enemy, Spiral param) {
        return new Coroutine((yielder) -> {
            double baseAngle = 0;
            while (enemy.hitPoints() > 0) {
                for (int i = 0; i < param.cycles; i++) {
                    for (int j = 0; j < param.bulletsPerTick; j++) {
                        double angle = baseAngle + j * Math.PI * 2 / param.bulletsPerTick;
                        bulletPool.pool(enemy.x, enemy.y, param.bulletSpeed, angle, 4, Color.pink);
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
                        bulletPool.pool(enemy.x, enemy.y, param.bulletSpeed, angle, 4, new Color(120, 210, 255));
                    }
                    yielder.pause(param.intervalPerTick);
                }
            }
        });
    }

    public static Coroutine aimedSingle(BulletPool bulletPool, Player player, Enemy enemy) {
        final int bulletsPerTick = 3;
        final int cycles = 5;
        final double bulletSpeed = 5.0, bulletSpread = 0.1;
        final int intervalPerTick = 3;
        final int intervalPerLoop = 40;

        return new Coroutine((yielder) -> {
            while (enemy.hitPoints() > 0 && !GameUtil.isGone(enemy.x, enemy.y)) {
                yielder.pause(intervalPerLoop);

                for (int i = 0; i < cycles; i++) {
                    double aim = Math.atan2(player.y - enemy.y, player.x - enemy.x);

                    for (int j = 0; j < bulletsPerTick; j++) {
                        double angle = aim + (j - bulletsPerTick / 2.0) * bulletSpread;
                        bulletPool.pool(enemy.x, enemy.y, bulletSpeed, angle, 4, new Color(120, 210, 255));
                    }
                    yielder.pause(intervalPerTick);
                }
            }
        });
    }
}
