package cn.mcxkly.classicandsimplestatusbars.mixin;

import cn.mcxkly.classicandsimplestatusbars.Config;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "artifacts.client.HeliumFlamingoOverlay", remap = false)
abstract class ArtifactsMixin {
    @Inject(method = "renderOverlay", at = @At("HEAD"), cancellable = true)
    private static void renderOverlay(int height, GuiGraphics guiGraphics, int screenWidth, int screenHeight, CallbackInfoReturnable<Boolean> cir) {
        if ( Config.All_On ) {
            cir.cancel();
        }
    }
}