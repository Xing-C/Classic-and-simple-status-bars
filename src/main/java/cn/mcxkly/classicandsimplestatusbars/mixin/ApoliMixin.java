package cn.mcxkly.classicandsimplestatusbars.mixin;

import cn.mcxkly.classicandsimplestatusbars.Config;
import io.github.apace100.apoli.util.ApoliConfigs;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "io.github.apace100.apoli.screen.PowerHudRenderer", remap = false)
public abstract class ApoliMixin {
    boolean Only = false;

    @OnlyIn(Dist.CLIENT)
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render(GuiGraphics context, float delta, CallbackInfo ci) {
        //if ( Config.All_On ) { // 这里就不受控制吧. 感觉是帮它解决冲突，没改变原本功能.
//        我们假设 apoli-client.toml 配置文件没有被修改，那我们就进行修改.
//        [resources_and_cooldowns]
//            hud_offset_x = 0
//            hud_offset_y = 0  // 如果这里没被改就说明可能冲突，我们增加高度.
        if ( !Only ) {
            Only = true; // 确定只改一次
            int sy = ApoliConfigs.CLIENT.resourcesAndCooldowns.hudOffsetY.get();
            int sx = ApoliConfigs.CLIENT.resourcesAndCooldowns.hudOffsetX.get();
            if ( sy == 0 || sx == 0 ) {
                // 似乎不需要修改 Y
                //ApoliConfigs.CLIENT.resourcesAndCooldowns.hudOffsetY.set(0);
                ApoliConfigs.CLIENT.resourcesAndCooldowns.hudOffsetX.set(85);
            }
        }
        // 极其残忍
//          Minecraft client = Minecraft.getInstance();
//          lient.getWindow().setHeight(client.getWindow().getGuiScaledHeight() + 1);
        if ( Config.All_On && Config.Origins_On ) {
            ci.cancel(); // 我滴 注入 任务完成啦.
        }// else {
//                // 提前修改，防止动来动去的.
//                暂时有点问题. 因为每次渲染，条件都会改变这个数值..
//
//                Minecraft pclient = Minecraft.getInstance();
//                LocalPlayer pplayer = pclient.player;
//                int y = ApoliConfigs.CLIENT.resourcesAndCooldowns.hudOffsetY.get();
//                if ( pplayer != null ) {
//                    // 啥？我不想骑马.
//                    Entity patt1361$temp = pplayer.getVehicle();
//                    if ( patt1361$temp instanceof LivingEntity vehicle ) {
//                        ApoliConfigs.CLIENT.resourcesAndCooldowns.hudOffsetY.set( y + (8 * (int) (vehicle.getMaxHealth() / 20.0F)));
//                    }
//                    // 不会在喝水的时候抬高了.
//                    if ( pplayer.isEyeInFluidType(ForgeMod.WATER_TYPE.get()) || pplayer.getAirSupply() < pplayer.getMaxAirSupply() ) {
//                        ApoliConfigs.CLIENT.resourcesAndCooldowns.hudOffsetY.set(y + 18);
//                    }
//                }
//            }
        //}
    }
}
