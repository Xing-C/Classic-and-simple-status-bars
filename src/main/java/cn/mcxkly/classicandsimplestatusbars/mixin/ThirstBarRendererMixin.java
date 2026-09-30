package cn.mcxkly.classicandsimplestatusbars.mixin;

import cn.mcxkly.classicandsimplestatusbars.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "dev.ghen.thirst.foundation.gui.ThirstBarRenderer", remap = false)
abstract class ThirstBarRendererMixin {
    @Inject(method = "registerThirstOverlay", at = @At("HEAD"), cancellable = true)
    private static void registerThirstOverlay(CallbackInfo ci) {
        if ( Config.All_On ) {
            ci.cancel();
        }
    }

}
