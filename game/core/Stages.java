package game.core;

import game.patterns.BulletPatterns;
import game.patterns.MovePatterns;
import game.scheduler.Coroutine;

public class Stages {
    public static Coroutine stage1_1(Game game) {
        return new Coroutine((yielder) -> {
            var scheduler = game.scheduler;
            var player = game.player;
            var boss = game.boss;
            var bulletPool = game.bulletPool;

            scheduler.add(MovePatterns.moveBossPendulum(boss));
            scheduler.add(BulletPatterns.ringSpiral(bulletPool, boss));
            scheduler.add(BulletPatterns.aimedSpread(bulletPool, player, boss));
            while(!boss.isGone()) yielder.pause(1);
            game.gameClear();
        });
    }
}
