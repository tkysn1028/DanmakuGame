package game.core;

public class SceneManager {
    private Scene scene = Scene.TITLE;
    public Scene scene() { return scene; }

    private Game game;
    public Game game() { return game; }

    public void step(Input input, int frame) {
        switch (scene) {
            case TITLE -> {
                if(input.confirmPressed) {
                    game = new Game();
                    scene = Scene.PLAYING;
                }
            }
            case PLAYING -> {
                game.step(input, frame);
                if(game.isGameOver()) scene = Scene.GAMEOVER;
            }
            case GAMEOVER -> {
                if(input.confirmPressed) scene = Scene.TITLE;
            }
        }
    }
}
