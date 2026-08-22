package game;

import java.awt.Color;

import game.scheduler.Coroutine;

public class BulletPatterns {
    private BulletPatterns() {}

    public static Coroutine ringSpiral(Game g) {
        final int bulletsPerTick = 16;
        final int cycles = 24;
        final double bulletSpeed = 2.2, twistAngle = 0.13;
        final int intervalPerTick = 6;
        final int intervalPerLoop = 70;

        return new Coroutine((yielder) -> {
            double baseAngle = 0;
            while (true) {
                for (int i = 0; i < cycles; i++) {
                    for (int j = 0; j < bulletsPerTick; j++) {
                        double angle = baseAngle + j * Math.PI * 2 / bulletsPerTick;
                        g.bulletPool.pool(g.boss.x, g.boss.y, bulletSpeed, angle, 4, Color.pink);
                    }
                    baseAngle += twistAngle;
                    yielder.pause(intervalPerTick);
                }
                yielder.pause(intervalPerLoop);
            }
        });
    }

    public static Coroutine aimedSpread(Game g) {
        final int bulletsPerTick = 5;
        final int cycles = 3;
        final double bulletSpeed = 2.2, bulletSpread = 0.5;
        final int intervalPerTick = 6;
        final int intervalPerLoop = 90;

        return new Coroutine((yielder) -> {
            while (true) {
                yielder.pause(intervalPerLoop);

                for (int i = 0; i < cycles; i++) {
                    double aim = Math.atan2(g.player.y - g.boss.y, g.player.x - g.boss.x);

                    for (int j = 0; j < bulletsPerTick; j++) {
                        double angle = aim + (j - bulletsPerTick / 2.0) * bulletSpread;
                        g.bulletPool.pool(g.boss.x, g.boss.y, bulletSpeed, angle, 4, new Color(120, 210, 255));
                    }
                    yielder.pause(intervalPerTick);
                }
            }
        });
    }
}
