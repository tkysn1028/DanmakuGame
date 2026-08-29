package game.common;

import game.battle.core.BattleMode;
import game.common.enums.BattleType;
import game.common.enums.Scene;

public class GameSession {
    private Scene scene;
    public Scene scene() { return scene; }

    private BattleMode battleMode;
    public BattleMode battleMode() { return battleMode; }

    private BattleType battleType = BattleType.REGIST_NORMAL_MOONSHOT; // TODO:別ゲームから注入するように要修正

    public void init() { scene = Scene.TITLE; }

    public GameSession() { init(); }

    public void update(Input input, int frame) {
        switch (scene) {
            case TITLE -> {
                if (input.confirmPressed) {
                    battleMode = new BattleMode(battleType, 11);  // TODO:別ゲームから注入するように要修正
                    scene = Scene.BATTLE_PLAYING;
                }
            }
            case BATTLE_PLAYING -> {
                switch (battleMode.update(input, frame)) {
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
