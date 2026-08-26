package game.core;

import java.util.List;
import java.util.function.Function;

import game.scheduler.Coroutine;

public class SceneManager {
    private Scene scene;
    public Scene scene() { return scene; }

    private Stage stage;
    public Stage stage() { return stage; }

    private int stageIndex;
    private int lives;
    public int lives() { return lives; }

    public void init() {
        scene = Scene.TITLE;
        lives = 5;
        stageIndex = 0;
    }

    public SceneManager() {
        init();
    }

    private static final List<Function<Stage, Coroutine>> STAGES = List.of(
        StagePatterns::stage1
    );

    public void step(Input input, int frame) {
        switch (scene) {
            case TITLE -> {
                if (input.confirmPressed) {
                    stage = new Stage(STAGES.get(stageIndex));
                    scene = Scene.PLAYING;
                }
            }
            case PLAYING -> {
                stage.step(input, frame);
                if(stage.isPlayerHit()) {
                    lives--;
                    if (lives <= 0) {
                        scene = Scene.GAMEOVER;
                        break;
                    }
                }
                if (stage.isStageCleared()) {
                    stageIndex++;
                    if(stageIndex < STAGES.size()) {
                        stage = new Stage(STAGES.get(stageIndex));
                        scene = Scene.PLAYING;
                    } else {
                        scene = Scene.GAMEOVER;
                    }
                }
            }
            case GAMEOVER -> {
                if(input.confirmPressed) { init(); }
            }
        }
    }
}
