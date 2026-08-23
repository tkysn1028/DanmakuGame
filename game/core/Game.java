package game.core;

import game.bullet.BulletPool;
import game.entity.Boss;
import game.entity.Bullet;
import game.entity.Player;
import game.entity.Shot;
import game.scheduler.Coroutine;
import game.scheduler.Scheduler;
import game.shot.ShotPool;
import game.util.Collision;
import java.util.List;
import java.util.function.Function;

public class Game {
    public Player player = new Player();
    public Boss boss = new Boss();
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
