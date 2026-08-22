package game.core;

import java.util.List;

import game.bullet.BulletPatterns;
import game.bullet.BulletPool;
import game.entity.Boss;
import game.entity.Bullet;
import game.entity.Player;
import game.scheduler.Scheduler;
import game.util.Collision;

public class Game {
    public Player player = new Player();
    public Boss boss = new Boss();
    public BulletPool bulletPool = new BulletPool();
    public Scheduler scheduler = new Scheduler();

    public Game() {
        scheduler.add(BulletPatterns.ringSpiral(this));
        scheduler.add(BulletPatterns.aimedSpread(this));
    }
    
    public void step(Input input, int frame) {
        scheduler.tick();
        player.update(input);
        boss.update(frame);
        bulletPool.update();
        collideWithBullets(player, bulletPool.bullets);
    }

    private void collideWithBullets(Player player, List<Bullet> bullets) {
        if(player.iframes() == 0) {
            bullets.stream().forEach(bullet -> {
                if(Collision.check(player, bullet)) {
                    player.hit();
                    player.setIframes();
                }
            });
        }
    }
}
