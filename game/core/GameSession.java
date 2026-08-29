package game.core;

public class GameSession {
    private Scene scene;
    public Scene scene() { return scene; }

    private DanmakuMode danmaku;
    public DanmakuMode danmaku() { return danmaku; }

    public void init() {
        scene = Scene.TITLE;
    }

    public GameSession() {
        init();
    }

    public void update(Input input, int frame) {
        switch (scene) {
            case TITLE -> {
                if (input.confirmPressed) {
                    danmaku = new DanmakuMode(0, 11);
                    scene = Scene.DANMAKU_PLAYING;
                }
            }
            case DANMAKU_PLAYING -> {
                if (danmaku.update(input, frame) == Result.FAILED) {
                    scene = Scene.GAMEOVER;
                }
            }
            case GAMEOVER -> {
                if(input.confirmPressed) { init(); }
            }
        }
    }
}
