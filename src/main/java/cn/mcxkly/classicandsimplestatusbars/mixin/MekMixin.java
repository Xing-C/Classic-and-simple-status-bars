package cn.mcxkly.classicandsimplestatusbars.mixin;

import cn.mcxkly.classicandsimplestatusbars.Config;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(targets = "mekanism.client.render.hud.MekaSuitEnergyLevel", remap = false)
public class MekMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void renderBarOverlay (ForgeGui gui, GuiGraphics guiGraphics, float partialTicks, int screenWidth, int screenHeight, CallbackInfo ci){
        if ( Config.All_On ) {
            ci.cancel();
        }
    }
}

