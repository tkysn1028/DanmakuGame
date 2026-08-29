package game.battle.core;

import game.battle.entities.Bullet;
import game.battle.entities.BulletPool;
import game.battle.entities.Enemy;
import game.battle.entities.Player;
import game.battle.entities.Shot;
import game.battle.entities.ShotPool;
import game.battle.params.init.InitBattleParam;
import game.battle.util.BattleUtil;
import game.common.Input;
import game.core.scheduler.Coroutine;
import game.core.scheduler.Scheduler;
import game.core.util.GameUtil;

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
                if(BattleUtil.collisionCheck(player, bullet)) {
                    hit();
                    player.setIframes();
                }
            });
        }
    }

    private void collideWithShots(List<Enemy> enemies, Enemy boss, List<Shot> shots) {
       for (Shot shot : shots) {
            if (shot.isExpired()) continue;
            if (boss != null && BattleUtil.collisionCheck(boss, shot)) {
                boss.hit();
                shot.expire();
                continue;
            }
            for (Enemy e : enemies) {
                if (BattleUtil.collisionCheck(e, shot)) {
                    e.hit();
                    shot.expire();
                    break;
                }
            }
        }
    }
}
