package cn.mcxkly.classicandsimplestatusbars.mixin;

import cn.mcxkly.classicandsimplestatusbars.Config;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 传说生存（Legendary Survival Overhaul）：严寒(cold_hunger)的饥饿条渲染整条交给本模组。
 * 严寒图标已由本模组按 COLD_HUNGER 效果换成冷冻鸡腿, 所以这里直接掐掉整条绘制。
 * 连带跳过它内部的 gui.rightHeight += 10, 本模组不依赖该累计值。
 */
@Mixin(targets = "sfiomn.legendarysurvivaloverhaul.client.render.RenderTemperatureGui", remap = false)
public class LegendarySurvivalOverhaulColdHungerMixin {
    @Inject(method = "renderColdHunger", at = @At("HEAD"), cancellable = true, require = 0, remap = false)
    private static void cssb$cancelColdHunger(Gui gui, GuiGraphics guiGraphics, float partialTick, int width, int height, CallbackInfo ci) {
        if ( Config.All_On && Config.Food_On ) {
            ci.cancel(); // 严寒饥饿条的显示由本模组接管
        }
    }
}
