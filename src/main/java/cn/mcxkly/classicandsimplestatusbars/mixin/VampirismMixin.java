package cn.mcxkly.classicandsimplestatusbars.mixin;

import cn.mcxkly.classicandsimplestatusbars.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "de.teamlapen.vampirism.client.gui.overlay.BloodBarOverlay", remap = false)
public class VampirismMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render(CallbackInfo ci) {
        // 让我替你渲染吧，这是个意外，可以在不关闭其他显示的情况下渲染他。
        if ( Config.All_On && Config.Bloodsucker_On ) {
            ci.cancel();
        }
    }
}
