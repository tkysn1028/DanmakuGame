package game.core;

import java.util.List;

import game.bullet.BulletPool;
import game.entity.Boss;
import game.entity.Bullet;
import game.entity.Player;
import game.entity.Shot;
import game.patterns.BulletPatterns;
import game.patterns.MovePatterns;
import game.scheduler.Scheduler;
import game.shot.ShotPool;
import game.util.Collision;

public class Game {
    public Player player = new Player();
    public Boss boss = new Boss();
    public BulletPool bulletPool = new BulletPool();
    public Scheduler scheduler = new Scheduler();
    public ShotPool shotPool = new ShotPool();

    public Game() {
        scheduler.add(MovePatterns.moveBossPendulum(boss));
        scheduler.add(BulletPatterns.ringSpiral(bulletPool, boss));
        scheduler.add(BulletPatterns.aimedSpread(bulletPool, player, boss));
    }
    
    public void step(Input input, int frame) {
        scheduler.tick();
        player.update(input);
        bulletPool.update();
        shotPool.update(input, player.x, player.y, frame);
        collideWithBullets(player, bulletPool.all());
        collideWithShots(boss, shotPool.all());
    }

    public boolean isGameOver() {
        return player.hitPoints() == 0;
    }

    private void collideWithBullets(Player player, List<Bullet> bullets) {
        if(player.iframes() == 0) {
            bullets.forEach(bullet -> {
                if(Collision.check(player, bullet)) {
                    player.hit();
                    player.setIframes();
                    return;
                }
            });
        }
    }

    private void collideWithShots(Boss boss, List<Shot> shots) {
        shots.forEach((shot) -> {
            if(Collision.check(boss, shot)) {
                boss.hit();
                shot.expire();
            }
        });
    }
}
