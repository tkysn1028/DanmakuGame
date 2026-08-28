package game.core;

import game.bullet.BulletPool;
import game.entity.Bullet;
import game.entity.Enemy;
import game.entity.Player;
import game.entity.Shot;
import game.scheduler.Coroutine;
import game.scheduler.Scheduler;
import game.shot.ShotPool;
import game.util.GameUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Stage {
    public Player player = new Player();
    public List<Enemy> enemies = new ArrayList<>();
    public Enemy boss = null;
    public BulletPool bulletPool = new BulletPool();
    public Scheduler scheduler = new Scheduler();
    public ShotPool shotPool = new ShotPool();

    private boolean cleared = false;
    public void clear() { cleared = true; }
    public boolean isCleared() { return cleared; }

    private boolean isPlayerHit = false;
    public boolean isPlayerHit() { 
        var out = isPlayerHit;
        isPlayerHit = false;
        return out;
    }
    public void hit() { isPlayerHit = true; }

    public Stage(Function<Stage, Coroutine> stageScript) {
        scheduler.add(stageScript.apply(this));
    }
    
    public void update(Input input, int frame) {
        scheduler.tick();
        player.update(input);
        bulletPool.update();
        shotPool.update(input, player.x, player.y, frame);
        collideWithBullets(player, bulletPool.all());
        collideWithShots(enemies, boss, shotPool.all());
        enemies.removeIf(e -> e.hitPoints() == 0 || GameUtil.isGone(e.x, e.y));
    }

    private void collideWithBullets(Player player, List<Bullet> bullets) {
        if(player.iframes() == 0) {
            bullets.forEach(bullet -> {
                if(GameUtil.collisionCheck(player, bullet)) {
                    hit();
                    player.setIframes();
                }
            });
        }
    }

    private void collideWithShots(List<Enemy> enemies, Enemy boss, List<Shot> shots) {
       for (Shot shot : shots) {
            if (shot.isExpired()) continue;
            if (boss != null && GameUtil.collisionCheck(boss, shot)) {
                boss.hit();
                shot.expire();
                continue;
            }
            for (Enemy e : enemies) {
                if (GameUtil.collisionCheck(e, shot)) {
                    e.hit();
                    shot.expire();
                    break;
                }
            }
        }
    }
}
