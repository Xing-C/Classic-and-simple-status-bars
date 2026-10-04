package cn.mcxkly.classicandsimplestatusbars.other;

import net.minecraft.resources.ResourceLocation;

/**
 * 传说生存(Legendary Survival Overhaul) 的 HUD 贴图坐标。
 * 贴图为 256x256, 图标按 9px 网格排布。
 */
public final class LsoIcons {
    private LsoIcons() {}

    public static final ResourceLocation LSO_ICONS = new ResourceLocation("legendarysurvivaloverhaul", "textures/gui/overlay.png");
    public static final int SIZE = 256;

    /** 破碎心 */
    public static final int BROKEN_HEART_U = 144;
    public static final int BROKEN_HEART_V = 0;

    /** 水分 正常 */
    public static final int THIRST_U = 9;
    public static final int THIRST_V = 0;
    /** 水分 干渴(绿) */
    public static final int THIRST_GREEN_U = 36;
    public static final int THIRST_GREEN_V = 0;
    /** 水分 脱水(火焰) */
    public static final int THIRST_FLAME_U = 9;
    public static final int THIRST_FLAME_V = 9;

    /** 严寒 冷冻鸡腿 */
    public static final int FOOD_COLD_U = 63;
    public static final int FOOD_COLD_V = 9;
    /** 严寒 冷冻鸡腿 金边(饱食度>0 时叠加) */
    public static final int FOOD_GOLD_U = 108;
    public static final int FOOD_GOLD_V = 9;
}
