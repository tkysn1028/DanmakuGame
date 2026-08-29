package game.core;

import java.util.List;
import java.util.function.Function;

import game.params.battle.Battle1;
import game.patterns.BattlePatterns;
import game.scheduler.Coroutine;

public class DanmakuMode {
    private static final List<Function<Battle, Coroutine>> BATTLES = List.of(
        s -> BattlePatterns.battle1(s, Battle1.normal())
    );

    private Battle battle;
    public Battle battle() { return battle; }

    private int battleIndex;

    private int lives;
    public int lives() { return lives; }

    public DanmakuMode(int battleIndex, int initialLives) {
        this.lives = initialLives;
        this.battleIndex = battleIndex;
        this.battle = new Battle(BATTLES.get(battleIndex));
    }

    public Result update(Input input, int frame) {
        battle.update(input, frame);
        if (battle.isPlayerHit()) {
            lives--;
            if (lives <= 0) {
                return Result.FAILED;
            }
        }
        if (battle.isCleared()) {
            battleIndex++;
            if (battleIndex < BATTLES.size()) {
                battle = new Battle(BATTLES.get(battleIndex));
            } else {
                return Result.FAILED;
            }
        }
        return Result.RUNNING;
    }
}
