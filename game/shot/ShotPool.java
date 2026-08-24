package game.shot;

import java.util.ArrayList;
import java.util.List;

import game.core.Input;
import game.entity.Shot;
import game.util.GameUtil;

public class ShotPool {
    private List<Shot> shots = new ArrayList<>();
    private int shotInterval = 8;

    public void pool(double x, double y) {
        shots.add(new Shot(x, y));
    }

    public void update(Input input, double x, double y, int frame) {
        shots.forEach(s -> s.update());
        if(input.shot && frame % shotInterval == 0) shots.add(new Shot(x, y));
        shots.removeIf((s) -> GameUtil.isGone(s.x, s.y) || s.isExpired());
    }

    public List<Shot> all() {
        return shots;
    }
}
