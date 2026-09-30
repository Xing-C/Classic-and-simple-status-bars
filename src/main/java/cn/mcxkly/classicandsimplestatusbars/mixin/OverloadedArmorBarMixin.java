package cn.mcxkly.classicandsimplestatusbars.mixin;

import cn.mcxkly.classicandsimplestatusbars.Config;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 过载盔甲条（Overloaded Armor Bar / modid: overloadedarmorbar）兼容：
 * 取消它画的护甲条 overlay（护甲值/韧性本模组已用文字显示）。
 */
@Mixin(targets = "tfar.overpoweredarmorbar.overlay.OverlayEventHandler", remap = false)
public class OverloadedArmorBarMixin {

    /** 取消 OAB 护甲条渲染（其唯一渲染入口）。 */
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void classicandsimplestatusbars$cancelArmorBarOverlay(ForgeGui gui, GuiGraphics guiGraphics,
                                                                 float partialTick, int screenWidth, int screenHeight,
                                                                 CallbackInfo ci) {
        if (Config.All_On) {
            ci.cancel();
        }
    }
}
