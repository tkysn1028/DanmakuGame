package game;
public class Game {
    public Player player = new Player();
    public void step(Input input) {
        player.update(input);
    }
}
