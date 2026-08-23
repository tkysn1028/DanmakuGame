package game.core;

import java.util.List;
import java.util.function.Function;

import game.scheduler.Coroutine;

public class SceneManager {
    private Scene scene = Scene.TITLE;
    public Scene scene() { return scene; }

    private Game game;
    public Game game() { return game; }

    private int stageIndex = 0;

    private static final List<Function<Game, Coroutine>> STAGES = List.of(
        Stages::stage1_1
    );

    public void step(Input input, int frame) {
        switch (scene) {
            case TITLE -> {
                if (input.confirmPressed) {
                    game = new Game(STAGES.get(stageIndex));
                    scene = Scene.PLAYING;
                }
            }
            case PLAYING -> {
                game.step(input, frame);
                if (game.isGameCleared()) {
                    stageIndex++;
                    if(stageIndex < STAGES.size()) {
                        game = new Game(STAGES.get(stageIndex));
                        scene = Scene.PLAYING;
                    } else {
                        scene = Scene.GAMEOVER;
                    }
                }
                if (game.isGameOver()) scene = Scene.GAMEOVER;
            }
            case GAMEOVER -> {
                if(input.confirmPressed) scene = Scene.TITLE;
            }
        }
    }
}
