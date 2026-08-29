package game.battle.core;

public class GameSession {
    private Scene scene;
    public Scene scene() { return scene; }

    private DanmakuMode danmaku;
    public DanmakuMode danmaku() { return danmaku; }

    private BattleType battleType = BattleType.REGIST_NORMAL_MOONSHOT; // TODO:別ゲームから注入するように要修正

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
                    danmaku = new DanmakuMode(battleType, 11);  // TODO:別ゲームから注入するように要修正
                    scene = Scene.DANMAKU_PLAYING;
                }
            }
            case DANMAKU_PLAYING -> {
                switch (danmaku.update(input, frame)) {
                    case FAILED  -> scene = Scene.GAMEOVER;
                    case CLEARED -> scene = Scene.GAMEOVER;
                    case RUNNING -> { }
                }
            }
            case GAMEOVER -> {
                if(input.confirmPressed) { init(); }
            }
        }
    }
}
