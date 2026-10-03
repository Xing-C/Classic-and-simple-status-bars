package cn.mcxkly.classicandsimplestatusbars.mixin;

import cn.mcxkly.classicandsimplestatusbars.Config;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.mutable.MutableInt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 奇异饰品（Artifacts）：只跳过火烈鸟泳圈那一格冷却图标。
 * 其余饰品（合唱图腾、十字项链、黑曜石骷髅等）没有替代显示，保持原样。
 */
@Mixin(targets = "artifacts.client.CooldownOverlayRenderer", remap = false)
public class ArtifactsCooldownMixin {
    @Inject(method = "lambda$render$0", at = @At("HEAD"), cancellable = true, require = 0, remap = false)
    private static void cssb$skipFlamingo(Player player, int x, int yStep, MutableInt index, GuiGraphics guiGraphics, int itemY, ItemStack stack, CallbackInfo ci) {
        if ( Config.All_On && Config.Artifacts_On && !stack.isEmpty()
                && BuiltInRegistries.ITEM.getKey(stack.getItem()).toString().equals("artifacts:helium_flamingo") ) {
            ci.cancel(); // 火烈鸟的冷却图标由本模组的破裂图标接管
        }
    }
}
