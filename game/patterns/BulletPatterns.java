package game.patterns;

import java.awt.Color;

import game.bullet.BulletPool;
import game.entity.Boss;
import game.entity.Player;
import game.scheduler.Coroutine;

public class BulletPatterns {
    private BulletPatterns() {}

    public static Coroutine ringSpiral(BulletPool bulletPool, Boss boss) {
        final int bulletsPerTick = 32;
        final int cycles = 24;
        final double bulletSpeed = 2.2, twistAngle = 0.13;
        final int intervalPerTick = 6;
        final int intervalPerLoop = 70;

        return new Coroutine((yielder) -> {
            double baseAngle = 0;
            while (boss.hitPoints() > 0) {
                for (int i = 0; i < cycles; i++) {
                    for (int j = 0; j < bulletsPerTick; j++) {
                        double angle = baseAngle + j * Math.PI * 2 / bulletsPerTick;
                        bulletPool.pool(boss.x, boss.y, bulletSpeed, angle, 4, Color.pink);
                    }
                    baseAngle += twistAngle;
                    yielder.pause(intervalPerTick);
                }
                yielder.pause(intervalPerLoop);
            }
        });
    }

    public static Coroutine aimedSpread(BulletPool bulletPool, Player player, Boss boss) {
        final int bulletsPerTick = 5;
        final int cycles = 20;
        final double bulletSpeed = 2.2, bulletSpread = 0.5;
        final int intervalPerTick = 6;
        final int intervalPerLoop = 40;

        return new Coroutine((yielder) -> {
            while (boss.hitPoints() > 0) {
                yielder.pause(intervalPerLoop);

                for (int i = 0; i < cycles; i++) {
                    double aim = Math.atan2(player.y - boss.y, player.x - boss.x);

                    for (int j = 0; j < bulletsPerTick; j++) {
                        double angle = aim + (j - bulletsPerTick / 2.0) * bulletSpread;
                        bulletPool.pool(boss.x, boss.y, bulletSpeed, angle, 4, new Color(120, 210, 255));
                    }
                    yielder.pause(intervalPerTick);
                }
            }
        });
    }
}
