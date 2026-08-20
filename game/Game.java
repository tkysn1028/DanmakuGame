package game;

public class Game {
    public Player player = new Player();
    public Boss boss = new Boss();
    public BulletPool bulletPool = new BulletPool();
    
    public void step(Input input, int frame) {
        player.update(input);
        boss.update(frame);
        bulletPool.update();
    }
}
