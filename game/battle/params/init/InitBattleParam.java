package game.battle.params.init;

import java.util.ArrayList;
import java.util.List;

import game.battle.entities.BulletPool;
import game.battle.entities.Enemy;
import game.battle.entities.Player;
import game.battle.entities.ShotPool;
import game.core.scheduler.Scheduler;
import game.core.util.ConfigConst;

public class InitBattleParam {
    public Player player;
    public List<Enemy> enemies;
    public Enemy boss;
    public BulletPool bulletPool;
    public Scheduler scheduler;
    public ShotPool shotPool;

    public InitBattleParam() {
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
