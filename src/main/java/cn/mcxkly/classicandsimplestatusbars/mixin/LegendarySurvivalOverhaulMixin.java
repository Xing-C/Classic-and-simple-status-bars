package cn.mcxkly.classicandsimplestatusbars.mixin;

import cn.mcxkly.classicandsimplestatusbars.Config;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 传说生存（Legendary Survival Overhaul）：血量改造的渲染整条交给本模组。
 * 与本模组的健康显示重复; 破碎心已由本模组的心形图标左半接管, 护盾走伤害吸收的条与数字。
 */
@Mixin(targets = "sfiomn.legendarysurvivaloverhaul.client.render.RenderHealthGui", remap = false)
public class LegendarySurvivalOverhaulMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true, require = 0, remap = false)
    private static void cssb$cancelHealthOverhaul(Gui gui, GuiGraphics guiGraphics, float partialTick, int width, int height, CallbackInfo ci) {
        if ( Config.All_On && Config.Health_On ) {
            ci.cancel(); // 血量改造的显示由本模组接管
        }
    }
}
