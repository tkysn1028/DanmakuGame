package game.battle.ui;

import java.awt.Color;
import java.util.EnumMap;
import java.util.Map;
import game.battle.shot.ShotType;

public class ShotColors {
    
    private static final Map<ShotType, Color> COLORS = new EnumMap<>(ShotType.class);

    static {
        COLORS.put(ShotType.SMALL_SQUARE_MAGENTA, Color.MAGENTA);
    }

    private ShotColors() {}

    public static Color of(ShotType type) {
        return COLORS.getOrDefault(type, Color.WHITE);
    }
}
