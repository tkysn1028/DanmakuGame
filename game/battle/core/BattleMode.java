package game.battle.core;

import java.util.Map;
import java.util.function.Function;

import game.battle.enums.Result;
import game.battle.params.battle.Battle1;
import game.battle.patterns.BattlePatterns;
import game.common.BattleType;
import game.common.Input;
import game.core.scheduler.Coroutine;

public class BattleMode {
    private static final Map<BattleType, Function<Battle, Coroutine>> BATTLES = Map.of(
        BattleType.REGIST_NORMAL_MOONSHOT, s -> BattlePatterns.battleRegistNormalMoonShot(s, Battle1.normal())
    );

    private Battle battle;
    public Battle battle() { return battle; }

    private int lives;
    public int lives() { return lives; }

    public BattleMode(BattleType battleType, int initialLives) {
        this.lives = initialLives;
        this.battle = new Battle(BATTLES.get(battleType));
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
            return Result.FAILED;
        }
        return Result.RUNNING;
    }
}
