package game.core;

import game.bullet.BulletPool;
import game.entity.Boss;
import game.entity.Bullet;
import game.entity.Enemy;
import game.entity.Player;
import game.entity.Shot;
import game.scheduler.Coroutine;
import game.scheduler.Scheduler;
import game.shot.ShotPool;
import game.util.Collision;
import game.util.GameUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Game {
    public Player player = new Player();
    public List<Enemy> enemies = new ArrayList<>();
    public Enemy boss = null;
    public BulletPool bulletPool = new BulletPool();
    public Scheduler scheduler = new Scheduler();
    public ShotPool shotPool = new ShotPool();

    private boolean gameCleared = false;
    public void gameClear() { gameCleared = true; }
    public boolean isGameCleared() { return gameCleared; }

    public Game(Function<Game, Coroutine> stageScript) {
        scheduler.add(stageScript.apply(this));
    }
    
    public void step(Input input, int frame) {
        scheduler.tick();
        player.update(input);
        bulletPool.update();
        shotPool.update(input, player.x, player.y, frame);
        collideWithBullets(player, bulletPool.all());
        collideWithShots(enemies, boss, shotPool.all());
        enemies.removeIf(e -> e.hitPoints() == 0 || GameUtil.isGone(e.x, e.y));
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
                }
            });
        }
    }

    private void collideWithShots(List<Enemy> enemies, Enemy boss, List<Shot> shots) {
       for (Shot shot : shots) {
            if (shot.isExpired()) continue;
            if (boss != null && Collision.check(boss, shot)) {
                boss.hit();
                shot.expire();
                continue;
            }
            for (Enemy e : enemies) {
                if (Collision.check(e, shot)) {
                    e.hit();
                    shot.expire();
                    break;
                }
            }
        }
    }
}
