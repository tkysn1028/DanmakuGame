package game.battle.entities;

import java.util.ArrayList;
import java.util.List;

import game.common.Input;
import game.core.util.GameUtil;

public class ShotPool {
    private List<Shot> shots = new ArrayList<>();
    private int shotInterval = 8;
    public ShotPool shotInterval(int shotInterval) { this.shotInterval = shotInterval; return this; }

    private double shotSpeed;
    public ShotPool shotSpeed(double shotSpeed) { this.shotSpeed = shotSpeed; return this; }

    public void update(Input input, double x, double y, int frame) {
        shots.forEach(s -> s.update());
        if(input.shot && frame % shotInterval == 0) shots.add(new Shot(x, y, shotSpeed));
        shots.removeIf((s) -> GameUtil.isGone(s.x, s.y) || s.isExpired());
    }

    public List<Shot> all() {
        return shots;
    }
}
