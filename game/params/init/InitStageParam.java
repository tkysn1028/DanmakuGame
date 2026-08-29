package game.params.init;

import java.util.ArrayList;
import java.util.List;

import game.bullet.BulletPool;
import game.entity.Enemy;
import game.entity.Player;
import game.scheduler.Scheduler;
import game.shot.ShotPool;
import game.util.ConfigConst;

public class InitStageParam {
    public Player player;
    public List<Enemy> enemies;
    public Enemy boss;
    public BulletPool bulletPool;
    public Scheduler scheduler;
    public ShotPool shotPool;

    public InitStageParam() {
        player = new Player()
                .initX(ConfigConst.WIDTH / 2.0)
                .initY(ConfigConst.HEIGHT)
                .fastUp()
                .resetIframes()
                .clampRate(2.5);
        enemies = new ArrayList<>();
        boss = null;
        bulletPool = new BulletPool();
        scheduler = new Scheduler();
        shotPool = new ShotPool()
                .shotInterval(8)
                .shotSpeed(20.0);
    }
}
