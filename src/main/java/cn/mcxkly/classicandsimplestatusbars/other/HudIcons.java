package cn.mcxkly.classicandsimplestatusbars.other;

import net.minecraft.resources.ResourceLocation;

/**
 * HUD 图标 sprite id 集中管理。
 * 1.21 起原版不再用 textures/gui/icons.png 裁切, 改为 textures/gui/sprites/ 下的独立贴图,
 * 用 blitSprite 按 sprite id 绘制
 * 护甲韧性原版没有图标, 用 ARMOR_FULL 配韧性色染色代替.
 */
public final class HudIcons {
    private HudIcons() {}

    /** 原版 sprite id (对应 assets/minecraft/textures/gui/sprites/hud/) */
    public static final ResourceLocation HEART_FULL = ResourceLocation.withDefaultNamespace("hud/heart/full");
    public static final ResourceLocation HEART_ABSORBING_FULL = ResourceLocation.withDefaultNamespace("hud/heart/absorbing_full");
    public static final ResourceLocation VEHICLE_FULL = ResourceLocation.withDefaultNamespace("hud/heart/vehicle_full");
    public static final ResourceLocation ARMOR_FULL = ResourceLocation.withDefaultNamespace("hud/armor_full");
    public static final ResourceLocation FOOD_EMPTY = ResourceLocation.withDefaultNamespace("hud/food_empty");
    public static final ResourceLocation FOOD_FULL = ResourceLocation.withDefaultNamespace("hud/food_full");
    public static final ResourceLocation AIR = ResourceLocation.withDefaultNamespace("hud/air");

    /** 吸血鬼血液: Vampirism 1.10 也已把 icons.png 拆成 sprites/blood_bar/ */
    public static final ResourceLocation VAMPIRISM_BLOOD =
            ResourceLocation.fromNamespaceAndPath("vampirism", "blood_bar/full");

    /** 传说生存的 HUD 贴图: 破碎心在 (144, 0) 9x9; 对方自己传的 v 是 9/45, 那里是空的 */
    public static final ResourceLocation LSO_ICONS =
            ResourceLocation.fromNamespaceAndPath("legendarysurvivaloverhaul", "textures/gui/overlay.png");
    public static final int LSO_ICONS_SIZE = 256;
    public static final int LSO_BROKEN_HEART_U = 144;
    public static final int LSO_BROKEN_HEART_V = 0;
    /** 传说生存的水分图标在 (9, 0) 9x9, (27, 0) 那份是描边空态 */
    public static final int LSO_THIRST_U = 9;
    public static final int LSO_THIRST_V = 0;
    /** 干渴: 绿色水滴 (36, 0) */
    public static final int LSO_THIRST_GREEN_U = 36;
    public static final int LSO_THIRST_GREEN_V = 0;
    /** 脱水: 火焰水滴 (9, 9) */
    public static final int LSO_THIRST_FLAME_U = 9;
    public static final int LSO_THIRST_FLAME_V = 9;
    /** 严寒(冷冻)鸡腿: 对方在 v=9 那一排, u=54 空 / 63 满 / 72 半 */
    public static final int LSO_FOOD_COLD_U = 63;
    public static final int LSO_FOOD_COLD_V = 9;
    /** 饱食度金边: 同一排 u=108 满 / 117 半 */
    public static final int LSO_FOOD_GOLD_U = 108;
    public static final int LSO_FOOD_GOLD_V = 9;
}
