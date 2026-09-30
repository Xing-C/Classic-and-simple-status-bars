package cn.mcxkly.classicandsimplestatusbars.mixin;

import cn.mcxkly.classicandsimplestatusbars.Config;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "com.github.L_Ender.cataclysm.client.event.ClientEvent", remap = false)
public class CataclysmMixin {
    @Inject(method = "renderSandstormOverlay", at = @At("HEAD"), cancellable = true)
    private void renderSandstormOverlay (RenderGuiOverlayEvent.Post event, CallbackInfo ci){
        if ( Config.All_On ) {
            ci.cancel();
        }
    }
}
