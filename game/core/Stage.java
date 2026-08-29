package game.core;

import game.bullet.BulletPool;
import game.entity.Bullet;
import game.entity.Enemy;
import game.entity.Player;
import game.entity.Shot;
import game.params.init.InitStageParam;
import game.scheduler.Coroutine;
import game.scheduler.Scheduler;
import game.shot.ShotPool;
import game.util.GameUtil;

import java.util.List;
import java.util.function.Function;

public class Stage {
    private InitStageParam initStageParam = new InitStageParam();
    public Player player = initStageParam.player;
    public List<Enemy> enemies = initStageParam.enemies;
    public Enemy boss = initStageParam.boss;
    public BulletPool bulletPool = initStageParam.bulletPool;
    public Scheduler scheduler = initStageParam.scheduler;
    public ShotPool shotPool = initStageParam.shotPool;

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
