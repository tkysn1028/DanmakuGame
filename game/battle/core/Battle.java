package game.battle.core;

import game.battle.bullet.BulletPool;
import game.battle.entity.Bullet;
import game.battle.entity.Enemy;
import game.battle.entity.Player;
import game.battle.entity.Shot;
import game.battle.params.init.InitBattleParam;
import game.battle.scheduler.Coroutine;
import game.battle.scheduler.Scheduler;
import game.battle.shot.ShotPool;
import game.battle.util.GameUtil;

import java.util.List;
import java.util.function.Function;

public class Battle {
    private InitBattleParam initBattleParam = new InitBattleParam();
    public Player player = initBattleParam.player;
    public List<Enemy> enemies = initBattleParam.enemies;
    public Enemy boss = initBattleParam.boss;
    public BulletPool bulletPool = initBattleParam.bulletPool;
    public Scheduler scheduler = initBattleParam.scheduler;
    public ShotPool shotPool = initBattleParam.shotPool;

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

    public Battle(Function<Battle, Coroutine> battleScript) {
        scheduler.add(battleScript.apply(this));
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
