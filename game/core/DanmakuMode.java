package game.core;

import java.util.List;
import java.util.function.Function;

import game.params.stage.Stage1;
import game.patterns.StagePatterns;
import game.scheduler.Coroutine;

public class DanmakuMode {
    private static final List<Function<Stage, Coroutine>> STAGES = List.of(
        s -> StagePatterns.stage1(s, Stage1.normal())
    );

    private Stage stage;
    public Stage stage() { return stage; }

    private int stageIndex;

    private int lives;
    public int lives() { return lives; }

    public DanmakuMode(int stageIndex, int initialLives) {
        this.lives = initialLives;
        this.stageIndex = stageIndex;
        this.stage = new Stage(STAGES.get(stageIndex));
    }

    public Result update(Input input, int frame) {
        stage.update(input, frame);
        if (stage.isPlayerHit()) {
            lives--;
            if (lives <= 0) {
                return Result.FAILED;
            }
        }
        if (stage.isCleared()) {
            stageIndex++;
            if (stageIndex < STAGES.size()) {
                stage = new Stage(STAGES.get(stageIndex));
            } else {
                return Result.FAILED;
            }
        }
        return Result.RUNNING;
    }
}
