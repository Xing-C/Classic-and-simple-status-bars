package cn.mcxkly.classicandsimplestatusbars.other;

import net.minecraft.world.entity.player.Player;
import sfiomn.legendarysurvivaloverhaul.api.health.HealthUtil;
import sfiomn.legendarysurvivaloverhaul.util.AttachmentUtil;

/**
 * 传说生存(Legendary Survival Overhaul)的血量数据读取。
 */
public final class LsoHealth {
    private LsoHealth() {}

    /** 血量改造是否启用 */
    public static boolean overhaulEnabled() {
        return sfiomn.legendarysurvivaloverhaul.config.Config.Baked.healthOverhaulEnabled;
    }

    /** 破碎心颗数 */
    public static int brokenHearts(Player player) {
        return HealthUtil.getEffectiveBrokenHearts(player);
    }

    /** 未扣破碎心的生命上限(含额外生命加成) */
    public static double stableMaxHealth(Player player) {
        return HealthUtil.getPlayerStableMaxHealth(player);
    }

    /** 护盾血量(点) */
    public static float shieldHealth(Player player) {
        return AttachmentUtil.getHealthAttachment(player).getShieldHealth();
    }
}
