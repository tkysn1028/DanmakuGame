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
    
    public void step(Input input, int frame) {
        scheduler.tick();
        player.update(input);
        boss.update(frame);
        bulletPool.update();
    }
}
