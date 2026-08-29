package game.core;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import game.params.stage.Stage1;
import game.patterns.StagePatterns;
import game.scheduler.Coroutine;

public class GameSession {
    private Scene scene;
    public Scene scene() { return scene; }

    private Stage stage;
    public Stage stage() { return stage; }

    private int stageIndex;

    private int lives;
    public int lives() { return lives; }

    public void init() {
        scene = Scene.TITLE;
        lives = 3;
        stageIndex = 0;
    }

    public GameSession() {
        init();
    }

    private static final List<Function<Stage, Coroutine>> STAGES = List.of(
            s -> StagePatterns.stage1(s, Stage1.normal())
    );

    public void update(Input input, int frame) {
        switch (scene) {
            case TITLE -> {
                if (input.confirmPressed) {
                    stage = new Stage(STAGES.get(stageIndex));
                    scene = Scene.PLAYING;
                }
            }
            case PLAYING -> {
                stage.update(input, frame);
                if(stage.isPlayerHit()) {
                    lives--;
                    if (lives <= 0) {
                        scene = Scene.GAMEOVER;
                        break;
                    }
                }
                if (stage.isCleared()) {
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
