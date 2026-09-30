package cn.mcxkly.classicandsimplestatusbars.mixin;

import cn.mcxkly.classicandsimplestatusbars.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "dev.ghen.thirst.foundation.gui.appleskin.HUDOverlayHandler", remap = false)
public class ThirstHUDOverlayHandlerMixin {
    @Inject(method = "onRenderGuiOverlayPre", at = @At("HEAD"), cancellable = true)
    private void onRenderGuiOverlayPre(CallbackInfo ci) {
        if ( Config.All_On ) {
            ci.cancel();
        }
    }

    @Inject(method = "renderThirstOverlay", at = @At("HEAD"), cancellable = true)
    private static void renderThirstOverlay(CallbackInfo ci) {
        if ( Config.All_On ) {
            ci.cancel();
        }
    }
}

