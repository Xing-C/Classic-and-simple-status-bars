package cn.mcxkly.classicandsimplestatusbars.mixin;

import cn.mcxkly.classicandsimplestatusbars.Config;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "homeostatic.util.WaterHelper", remap = false)
public class HomeostaticMixin {
    @Inject(method = "drawWaterBar", at = @At("HEAD"), cancellable = true)
    private static void drawWaterBar(ResourceLocation sprite, int scaledWidth, int scaledHeight, MobEffectInstance effectInstance, Gui gui, GuiGraphics guiGraphics, float waterSaturationLevel, int waterLevel, int tickCount, CallbackInfo ci) {
        if ( Config.All_On ) {
            ci.cancel();
        }
    }
}
