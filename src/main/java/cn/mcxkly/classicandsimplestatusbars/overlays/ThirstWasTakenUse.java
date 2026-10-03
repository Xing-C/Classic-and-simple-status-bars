package cn.mcxkly.classicandsimplestatusbars.overlays;

import cn.mcxkly.classicandsimplestatusbars.Config;
import cn.mcxkly.classicandsimplestatusbars.other.HudIcons;
import dev.ghen.thirst.content.thirst.PlayerThirst;
import dev.ghen.thirst.foundation.common.capability.ModAttachment;
import homeostatic.common.attachments.WaterData;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import sfiomn.legendarysurvivaloverhaul.api.thirst.ThirstUtil;
import sfiomn.legendarysurvivaloverhaul.registry.MobEffectRegistry;
import sfiomn.legendarysurvivaloverhaul.common.attachments.thirst.ThirstAttachment;
import sfiomn.legendarysurvivaloverhaul.util.AttachmentUtil;
import toughasnails.api.thirst.ThirstHelper;

import java.util.concurrent.atomic.AtomicInteger;

public class ThirstWasTakenUse implements LayeredDraw.Layer {
    public static boolean StopConflictRendering = true; // 口渴

    public static void StopConflictRenderingIDEA(boolean is) {
        StopConflictRendering = is;
    }

    public static final ResourceLocation THIRST_ICONS = ResourceLocation.parse("thirst:textures/gui/thirst_icons.png");

    public static boolean toughasnailsIS = true; // 支持意志坚定

    public static void toughasnailsIDEA(boolean is) {
        toughasnailsIS = is;
    }

    public static final ResourceLocation Toughasnails_Icons = ResourceLocation.parse("toughasnails:textures/gui/icons.png");


    public static boolean HomeostaticIS = true; // 稳态

    public static void HomeostaticIDEA(boolean is) {
        HomeostaticIS = is;
    }

    public static final ResourceLocation Homeostatic_Icons = ResourceLocation.parse("homeostatic:textures/gui/icons.png");


    public static boolean LsoThirstIS = true; // 传说生存

    public static void LsoThirstIDEA(boolean is) {
        LsoThirstIS = is;
    }


    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();
        if ( mc.options.hideGui ) return;
        if ( mc.gameMode == null || !mc.gameMode.canHurtPlayer() ) return;
        if ( !(mc.getCameraEntity() instanceof Player player) ) return;

        Font font = mc.font;
        int x = guiGraphics.guiWidth() / 2 + 11;
        int y = guiGraphics.guiHeight() - 39;
        y += 5; //4+1
        if ( Config.All_On ) {
            renderThirstLevelBar(font, guiGraphics, deltaTracker.getGameTimeDeltaPartialTick(false), x, y, player);
        }
    }

    private void renderThirstLevelBar(Font font, GuiGraphics guiGraphics, float partialTick, int x, int y, Player player) {
        AtomicInteger Quenched = new AtomicInteger();
        AtomicInteger Thirst = new AtomicInteger();
        if ( !StopConflictRendering ) { // 如果口渴存在，不管意志坚定是否存在.
            PlayerThirst playThirst = player.getData(ModAttachment.PLAYER_THIRST);
            Thirst.set(playThirst.getThirst());
            Quenched.set(playThirst.getQuenched());
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
            WaterData.getData(player).ifPresent((data) -> {
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
            ThirstAttachment thirstData = AttachmentUtil.getThirstAttachment(player);
            if ( thirstData == null || !ThirstUtil.isThirstActive(player) ) return;
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
        boolean thirst = player.hasEffect(MobEffectRegistry.THIRST);
        boolean heatThirst = player.hasEffect(MobEffectRegistry.HEAT_THIRST);
        if ( thirst && heatThirst ) {
            guiGraphics.enableScissor(iconX, iconY, iconX + 9, iconY + 5);
            blitLsoThirstIcon(guiGraphics, iconX, iconY, HudIcons.LSO_THIRST_GREEN_U, HudIcons.LSO_THIRST_GREEN_V);
            guiGraphics.disableScissor();
            guiGraphics.enableScissor(iconX, iconY + 5, iconX + 9, iconY + 9);
            blitLsoThirstIcon(guiGraphics, iconX, iconY, HudIcons.LSO_THIRST_FLAME_U, HudIcons.LSO_THIRST_FLAME_V);
            guiGraphics.disableScissor();
            return;
        }
        if ( thirst ) {
            blitLsoThirstIcon(guiGraphics, iconX, iconY, HudIcons.LSO_THIRST_GREEN_U, HudIcons.LSO_THIRST_GREEN_V);
        } else if ( heatThirst ) {
            blitLsoThirstIcon(guiGraphics, iconX, iconY, HudIcons.LSO_THIRST_FLAME_U, HudIcons.LSO_THIRST_FLAME_V);
        } else {
            blitLsoThirstIcon(guiGraphics, iconX, iconY, HudIcons.LSO_THIRST_U, HudIcons.LSO_THIRST_V);
        }
    }

    private void blitLsoThirstIcon(GuiGraphics guiGraphics, int iconX, int iconY, int u, int v) {
        guiGraphics.blit(HudIcons.LSO_ICONS, iconX, iconY, (float) u, (float) v, 9, 9,
                HudIcons.LSO_ICONS_SIZE, HudIcons.LSO_ICONS_SIZE);
    }
}
