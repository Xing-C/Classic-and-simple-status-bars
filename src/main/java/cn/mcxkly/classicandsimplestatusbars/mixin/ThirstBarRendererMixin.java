package cn.mcxkly.classicandsimplestatusbars.mixin;

import cn.mcxkly.classicandsimplestatusbars.Config;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 口渴（Thirst Was Taken）：取消它自己往原版 HUD 上画的水平条。
 */
@Mixin(targets = "dev.ghen.thirst.foundation.gui.ThirstBarRenderer", remap = false)
abstract class ThirstBarRendererMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private static void render(int width, int height, GuiGraphics guiGraphics, CallbackInfo ci) {
        if ( Config.All_On ) {
            ci.cancel();
        }
    }
}
