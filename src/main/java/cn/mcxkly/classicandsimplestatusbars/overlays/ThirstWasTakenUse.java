package cn.mcxkly.classicandsimplestatusbars.overlays;

import cn.mcxkly.classicandsimplestatusbars.Config;
import cn.mcxkly.classicandsimplestatusbars.other.LsoIcons;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.ghen.thirst.foundation.common.capability.IThirst;
import dev.ghen.thirst.foundation.common.capability.ModCapabilities;
import homeostatic.common.capabilities.CapabilityRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import sfiomn.legendarysurvivaloverhaul.api.thirst.ThirstUtil;
import sfiomn.legendarysurvivaloverhaul.common.capabilities.thirst.ThirstCapability;
import sfiomn.legendarysurvivaloverhaul.registry.MobEffectRegistry;
import sfiomn.legendarysurvivaloverhaul.util.CapabilityUtil;
import toughasnails.api.thirst.ThirstHelper;

import java.util.concurrent.atomic.AtomicInteger;

public class ThirstWasTakenUse implements IGuiOverlay {
    public static boolean StopConflictRendering = true; // 口渴

    public static void StopConflictRenderingIDEA(boolean is) {
        StopConflictRendering = is;
    }

    public static final ResourceLocation THIRST_ICONS = new ResourceLocation("thirst:textures/gui/thirst_icons.png");

    public static boolean toughasnailsIS = true; // 支持意志坚定

    public static void toughasnailsIDEA(boolean is) {
        toughasnailsIS = is;
    }

    public static final ResourceLocation Toughasnails_Icons = new ResourceLocation("toughasnails:textures/gui/icons.png");


    public static boolean HomeostaticIS = true; // 稳态

    public static void HomeostaticIDEA(boolean is) {
        HomeostaticIS = is;
    }

    public static final ResourceLocation Homeostatic_Icons = new ResourceLocation("homeostatic:textures/gui/icons.png");


    public static boolean LsoThirstIS = true; // 传说生存

    public static void LsoThirstIDEA(boolean is) {
        LsoThirstIS = is;
    }


    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int width, int height) {
        Font font = gui.getFont();
        if ( gui.shouldDrawSurvivalElements() ) {
            Player player = (Player) Minecraft.getInstance().cameraEntity;
            if ( player == null ) return;
            int x = width / 2 + 11;
            int y = height - 39;
            y += 5; //4+1
            if ( Config.All_On ) {
                renderThirstLevelBar(font, guiGraphics, partialTick, x, y, player);
            }
        }
    }

    private void renderThirstLevelBar(Font font, GuiGraphics guiGraphics, float partialTick, int x, int y, Player player) {
        AtomicInteger Quenched = new AtomicInteger();
        AtomicInteger Thirst = new AtomicInteger();
        if ( !StopConflictRendering ) { // 如果口渴存在，不管意志坚定是否存在.
            IThirst Play_THIRST = player.getCapability(ModCapabilities.PLAYER_THIRST).orElse(null);
            Thirst.set(Play_THIRST.getThirst());
            Quenched.set(Play_THIRST.getQuenched());
            guiGraphics.blit(THIRST_ICONS,
                    x + 70, y - 10,
                    16.0F, 0.0F,
                    9, 9,
                    25, 9);
        } else if ( !toughasnailsIS ) { // 否则 如果意志坚定是存在的.
            toughasnails.api.thirst.IThirst thirst = ThirstHelper.getThirst(player);
            Thirst.set(thirst.getThirst());
            Quenched.set((int) thirst.getHydration());
            guiGraphics.blit(Toughasnails_Icons,
                    x + 70, y - 10,
                    0, 41,
                    9, 9);
        } else if ( !HomeostaticIS ) { // 优先级最低，迫不得已.
            player.getCapability(CapabilityRegistry.WATER_CAPABILITY).ifPresent((data) -> {
                RenderSystem.enableBlend();
                Thirst.set(data.getWaterLevel());
                Quenched.set((int) data.getWaterSaturationLevel());
                // 底图
                guiGraphics.blit(Homeostatic_Icons,
                        x + 70, y - 10,
                        0, 0,
                        9, 9);
                // 附加1
                guiGraphics.blit(Homeostatic_Icons,
                        x + 70, y - 10,
                        9, 0,
                        9, 9);
                // 附加2
                guiGraphics.blit(Homeostatic_Icons,
                        x + 70, y - 10,
                        0, 9,
                        9, 9);
            });
        } else if ( !LsoThirstIS ) { // 传说生存: 水分与饱和度
            if ( !ThirstUtil.isThirstActive(player) ) return;
            ThirstCapability thirstData = CapabilityUtil.getThirstCapability(player);
            if ( thirstData == null ) return;
            Thirst.set(thirstData.getHydrationLevel());
            Quenched.set((int) thirstData.getSaturationLevel());
            renderLsoThirstIcon(guiGraphics, x + 70, y - 10, player);
        } else return; // 如果两者都不在，并且也没有稳态，跳过渲染.
        if ( Quenched.get() > 0 ) { // 如果Quenched大于0渲染.
            int x2 = x + 70 - font.width(Quenched + Config.Interval_TTT) - font.width(String.valueOf(Thirst.get())); // 计算长度
            guiGraphics.drawString(font, Quenched + "", x2, y - 9, Config.Color_Thirst_Quenched, false);
            x2 += font.width(Quenched + "");
            guiGraphics.drawString(font, Config.Interval_TTT, x2, y - 9, Config.Color_Interval_TTT, false);
        }
        guiGraphics.drawString(font, String.valueOf(Thirst.get()), x + 70 - font.width(String.valueOf(Thirst.get())), y - 9, Config.Color_Thirst, false);
    }

    /** 水分图标: 干渴转绿, 脱水转火焰; 两者同时存在时绿占上半、火焰占下半 */
    private void renderLsoThirstIcon(GuiGraphics guiGraphics, int iconX, int iconY, Player player) {
        boolean thirst = player.hasEffect(MobEffectRegistry.THIRST.get());
        boolean heatThirst = player.hasEffect(MobEffectRegistry.HEAT_THIRST.get());
        if ( thirst && heatThirst ) {
            guiGraphics.enableScissor(iconX, iconY, iconX + 9, iconY + 5);
            blitLsoThirstIcon(guiGraphics, iconX, iconY, LsoIcons.THIRST_GREEN_U, LsoIcons.THIRST_GREEN_V);
            guiGraphics.disableScissor();
            guiGraphics.enableScissor(iconX, iconY + 5, iconX + 9, iconY + 9);
            blitLsoThirstIcon(guiGraphics, iconX, iconY, LsoIcons.THIRST_FLAME_U, LsoIcons.THIRST_FLAME_V);
            guiGraphics.disableScissor();
            return;
        }
        if ( thirst ) {
            blitLsoThirstIcon(guiGraphics, iconX, iconY, LsoIcons.THIRST_GREEN_U, LsoIcons.THIRST_GREEN_V);
        } else if ( heatThirst ) {
            blitLsoThirstIcon(guiGraphics, iconX, iconY, LsoIcons.THIRST_FLAME_U, LsoIcons.THIRST_FLAME_V);
        } else {
            blitLsoThirstIcon(guiGraphics, iconX, iconY, LsoIcons.THIRST_U, LsoIcons.THIRST_V);
        }
    }

    private void blitLsoThirstIcon(GuiGraphics guiGraphics, int iconX, int iconY, int u, int v) {
        guiGraphics.blit(LsoIcons.LSO_ICONS, iconX, iconY, (float) u, (float) v, 9, 9, LsoIcons.SIZE, LsoIcons.SIZE);
    }
}
