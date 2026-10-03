package cn.mcxkly.classicandsimplestatusbars.mixin;

import cn.mcxkly.classicandsimplestatusbars.Config;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 口渴（Thirst Was Taken）：取消它在苹果皮 HUD 上叠加的水分覆盖层。
 */
@Mixin(targets = "dev.ghen.thirst.foundation.gui.appleskin.HUDOverlayHandler", remap = false)
public class ThirstHUDOverlayHandlerMixin {
    @Inject(method = "renderThirstOverlay", at = @At("HEAD"), cancellable = true)
    private static void renderThirstOverlay(GuiGraphics guiGraphics, CallbackInfo ci) {
        if ( Config.All_On ) {
            ci.cancel();
        }
    }
}
