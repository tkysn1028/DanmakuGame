package game.battle.core;

import java.util.Map;
import java.util.function.Function;

import game.battle.params.battle.Battle1;
import game.battle.patterns.BattlePatterns;
import game.battle.scheduler.Coroutine;

public class DanmakuMode {
    private static final Map<BattleType, Function<Battle, Coroutine>> BATTLES = Map.of(
        BattleType.REGIST_NORMAL_MOONSHOT, s -> BattlePatterns.battleRegistNormalMoonShot(s, Battle1.normal())
    );

    private Battle battle;
    public Battle battle() { return battle; }

    private int lives;
    public int lives() { return lives; }

    public DanmakuMode(BattleType battleType, int initialLives) {
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
