package game;

import game.object.Boss;
import game.object.BulletPool;
import game.object.Input;
import game.object.Player;
import game.scheduler.Scheduler;

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
        if(player.iframes == 0) {
            bulletPool.bullets.stream().forEach(b -> {
                if(Collision.check(player.x, player.y, ConfigConst.PLAYER_RADIUS, b.x, b.y, b.radius)) {
                    if(player.hitPoints > 0) {
                        player.hitPoints--;
                        player.iframes = 90;
                    }
                }
            });
        }
    }
}
