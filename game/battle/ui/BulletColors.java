package game.battle.ui;

import java.awt.Color;
import java.util.EnumMap;
import java.util.Map;

import game.battle.bullet.BulletType;

public final class BulletColors {

    private static final Map<BulletType, Color> COLORS = new EnumMap<>(BulletType.class);

    static {
        COLORS.put(BulletType.SMALL_CIRCLE_PINK, Color.PINK);
        COLORS.put(BulletType.SMALL_CIRCLE_LIGHT_BLUE, ColorConst.LIGHTBLUE);
    }

    private BulletColors() {}

    public static Color of(BulletType type) {
        return COLORS.getOrDefault(type, Color.WHITE);
    }
}