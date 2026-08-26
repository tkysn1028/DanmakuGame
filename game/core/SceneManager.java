package game.core;

import java.util.List;
import java.util.function.Function;

import game.scheduler.Coroutine;

public class SceneManager {
    private Scene scene = Scene.TITLE;
    public Scene scene() { return scene; }

    private Stage stage;
    public Stage stage() { return stage; }

    private int stageIndex;

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
                if (stage.isStageCleared()) {
                    stageIndex++;
                    if(stageIndex < STAGES.size()) {
                        stage = new Stage(STAGES.get(stageIndex));
                        scene = Scene.PLAYING;
                    } else {
                        scene = Scene.GAMEOVER;
                    }
                }
                if (stage.isGameOver()) scene = Scene.GAMEOVER;
            }
            case GAMEOVER -> {
                if(input.confirmPressed) scene = Scene.TITLE;
                stageIndex = 0;
            }
        }
    }
}
