package intrusion;

public class GameSession {
    private Scene scene;
    public Scene scene() { return scene; }
    public void init() { scene = Scene.TITLE; }

    public GameSession() { init(); }
    public void update(Input input, int frame) {
        switch (scene) {
            case TITLE -> {
                if(input.confirmPressed) {
                    scene = Scene.PLAYING;
                }
            }
            case PLAYING -> {
                scene = Scene.GAMEOVER;
            }
            case GAMEOVER -> {
                init();
            }
        }
    }
}
