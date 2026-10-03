package cn.mcxkly.classicandsimplestatusbars.overlays;

import cn.mcxkly.classicandsimplestatusbars.ClassicAndSimpleStatusBars;
import cn.mcxkly.classicandsimplestatusbars.Config;
import cn.mcxkly.classicandsimplestatusbars.other.HudIcons;
import cn.mcxkly.classicandsimplestatusbars.other.LsoHealth;
import cn.mcxkly.classicandsimplestatusbars.other.helper;
import mekanism.api.energy.IEnergyContainer;
import mekanism.common.item.gear.ItemMekaSuitArmor;
import mekanism.common.util.StorageUtils;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class HealthBar implements LayeredDraw.Layer {

    private static final ResourceLocation fullHealthBarLocation = ResourceLocation.fromNamespaceAndPath(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/healthbars/full.png");
    private static final ResourceLocation witherBarLocation = ResourceLocation.fromNamespaceAndPath(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/healthbars/wither.png");
    private static final ResourceLocation poisonBarLocation = ResourceLocation.fromNamespaceAndPath(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/healthbars/poison.png");
    private static final ResourceLocation frozenBarLocation = ResourceLocation.fromNamespaceAndPath(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/healthbars/frozen.png");
    private ResourceLocation currentBarLocation = fullHealthBarLocation;
    private static final ResourceLocation intermediateHealthBarLocation = ResourceLocation.fromNamespaceAndPath(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/healthbars/intermediate.png");
    private static final ResourceLocation emptyHealthBarLocation = ResourceLocation.fromNamespaceAndPath(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/healthbars/empty.png");
    private static final ResourceLocation absorptionBarLocation = ResourceLocation.fromNamespaceAndPath(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/healthbars/absorption.png");

    private static final ResourceLocation POWER_BAR = ResourceLocation.fromNamespaceAndPath("mekanism", "gui/icons/basic_universal_cable.png");

    private float intermediateHealth = 0;

    // 传说生存: 破碎心颗数与护盾血量, 每帧读一次
    private int lsoBrokenHearts = 0;
    private float lsoShieldHealth = 0F;
    // 实际参与显示的吸收量: 有护盾时用护盾(实测护盾覆盖了伤害吸收)
    private float absorption = 0F;
    // 破碎心扣掉的生命上限, 等于当前上限与未扣上限之差
    private float lsoMaxHealthLoss = 0F;

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();
        if ( mc.options.hideGui ) return;
        if ( mc.gameMode == null || !mc.gameMode.canHurtPlayer() ) return;
        if ( !(mc.getCameraEntity() instanceof Player player) ) return;
        updateLsoState(player);

        Font font = mc.font;
        int width = guiGraphics.guiWidth();
        int height = guiGraphics.guiHeight();
        int x = width / 2 - 91;
        int y = height - 39;
        y += 4;
        float partialTick = deltaTracker.getGameTimeDeltaPartialTick(false);
        if ( Config.All_On && Config.Health_On ) {
            updateBarTextures(player);
            renderHealthBar(guiGraphics, partialTick, x, y, player);
            renderHealthValue(font, guiGraphics, x, y, player);
        } else if ( Config.EasyMode_Text_On ) {
            renderHealthValue_Easy(font, guiGraphics, x, y, player);
        }
    }

    private void updateLsoState(Player player) {
        if ( ClassicAndSimpleStatusBars.legendarysurvivaloverhaul && LsoHealth.overhaulEnabled() ) {
            lsoBrokenHearts = LsoHealth.brokenHearts(player);
            lsoShieldHealth = LsoHealth.shieldHealth(player);
            // 破碎心扣掉的生命上限 = 未扣上限 - 当前上限
            lsoMaxHealthLoss = (float) Math.max(0D, LsoHealth.stableMaxHealth(player) - player.getMaxHealth());
        } else {
            lsoBrokenHearts = 0;
            lsoShieldHealth = 0F;
            lsoMaxHealthLoss = 0F;
        }
        absorption = lsoShieldHealth > 0F ? lsoShieldHealth : player.getAbsorptionAmount();
    }

    /**
     * 数值左边的心形图标, 有破碎心/护盾时按半边拼接:
     * 左半取破碎心(没有就红心), 右半取护盾心(没有就红心), 所以破碎与护盾能同时显示。
     */
    private void renderHeartIcon(GuiGraphics guiGraphics, int iconX, int iconY) {
        boolean broken = lsoBrokenHearts > 0;
        boolean shield = absorption > 0F; // 护盾与原版伤害吸收共用金心
        if ( !broken && !shield ) {
            guiGraphics.blitSprite(HudIcons.HEART_FULL, iconX, iconY, 9, 9);
            return;
        }
        guiGraphics.enableScissor(iconX, iconY, iconX + 4, iconY + 9);
        if ( broken ) {
            guiGraphics.blit(HudIcons.LSO_ICONS, iconX, iconY,
                    HudIcons.LSO_BROKEN_HEART_U, HudIcons.LSO_BROKEN_HEART_V, 9, 9,
                    HudIcons.LSO_ICONS_SIZE, HudIcons.LSO_ICONS_SIZE);
        } else {
            guiGraphics.blitSprite(HudIcons.HEART_FULL, iconX, iconY, 9, 9);
        }
        guiGraphics.disableScissor();
        guiGraphics.enableScissor(iconX + 4, iconY, iconX + 9, iconY + 9);
        guiGraphics.blitSprite(shield ? HudIcons.HEART_ABSORBING_FULL : HudIcons.HEART_FULL, iconX, iconY, 9, 9);
        guiGraphics.disableScissor();
    }

    private void renderHealthValue_Easy(Font font, GuiGraphics guiGraphics, int x, int y, Player player) {
        y -= 2;
        float MaxHealth = player.getMaxHealth(); // 最大血量
        float Health = Math.min(player.getHealth(), MaxHealth); // 当前血量
        float Absorption = absorption; // 吸收量(有护盾时是护盾)
        int xx = x - 2;
        // 破碎心扣掉的生命上限: 本分支由右向左拼, 先画的落在最大生命值右侧
        if ( lsoMaxHealthLoss > 0F ) {
            String lossText = Config.Interval_YYY + helper.KeepOneDecimal(lsoMaxHealthLoss);
            xx = xx - font.width(lossText);
            guiGraphics.drawString(font, lossText, xx, y - 1, Config.Color_Health_Broken, false);
        }
        String text = helper.KeepOneDecimal(MaxHealth);
        xx = xx - font.width(text); // 要向左
        guiGraphics.drawString(font, text, xx, y - 1, Config.Color_Health, false);
        text = Config.Interval_lll;
        xx = xx - font.width(text); // '/'
        guiGraphics.drawString(font, text, xx, y - 1, Config.Color_Interval_lll, false);
        if ( Absorption > 0 ) {
            text = helper.KeepOneDecimal(Absorption);
            xx = xx - font.width(text);
            guiGraphics.drawString(font, text, xx, y - 1, Config.Color_Health_Absorb, false);

            text = Config.Interval_TTT;
            xx = xx - font.width(text); // '+'
            guiGraphics.drawString(font, text, xx, y - 1, Config.Color_Interval_TTT, false);
        }
        text = helper.KeepOneDecimal(Health);
        xx = xx - font.width(text);
        guiGraphics.drawString(font, text, xx, y - 1, Config.Color_Health_Tail, false);
    }

    public void updateBarTextures(Player player) {
        if ( player.hasEffect(MobEffects.WITHER) ) {
            currentBarLocation = witherBarLocation;
        } else if ( player.hasEffect(MobEffects.POISON) ) {
            currentBarLocation = poisonBarLocation;
        } else if ( player.isFullyFrozen() ) {
            currentBarLocation = frozenBarLocation;
        } else {
            currentBarLocation = fullHealthBarLocation;
        }
    }

    private void renderHealthValue(Font font, GuiGraphics guiGraphics, int x, int Y, Player player) {
        int y = Y + 1;
        float Absorption = absorption; // 吸收量(有护盾时是护盾)
        renderHeartIcon(guiGraphics, x, y - 10); // 心形图标: 破碎心占左半、护盾心占右半

        float MaxHealth = player.getMaxHealth(); // 最大血量
        float Health = Math.min(player.getHealth(), MaxHealth); // 当前血量
        float ARMOR = player.getArmorValue(); // 护甲值
        int xx = x + 10;
        String text;
        if ( Absorption > 0 ) {
            text = helper.KeepOneDecimal(Health);
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Health, false);

            xx = xx + font.width(text); // '+'
            text = Config.Interval_TTT;
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Interval_TTT, false);

            xx = xx + font.width(text);
            text = helper.KeepOneDecimal(Absorption);
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Health_Absorb, false);

            xx = xx + font.width(text); // '/'
            text = Config.Interval_lll;
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Interval_lll, false);

            xx = xx + font.width(text);
            text = helper.KeepOneDecimal(MaxHealth);
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Health_Tail, false);
        } else {
            text = helper.KeepOneDecimal(Health);
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Health, false);

            xx = xx + font.width(text); // '/'
            text = Config.Interval_lll;
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Interval_lll, false);

            xx = xx + font.width(text);
            text = helper.KeepOneDecimal(MaxHealth);
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Health_Tail, false);
        }
        // 破碎心扣掉的生命上限接在最大生命值后面
        if ( lsoMaxHealthLoss > 0F ) {
            String lossText = Config.Interval_YYY + helper.KeepOneDecimal(lsoMaxHealthLoss);
            guiGraphics.drawString(font, lossText, xx + font.width(text), y - 9, Config.Color_Health_Broken, false);
        }
        if ( ARMOR > 0 && Config.Armour_On ) {
            guiGraphics.drawString(font, helper.KeepOneDecimal(ARMOR), x + 10, y - 19, Config.Color_Armor, false);
            guiGraphics.blitSprite(HudIcons.ARMOR_FULL, x, y - 20, 9, 9); // 护甲图标
        }
        boolean onmek = false;
        if ( ClassicAndSimpleStatusBars.mekanism ) {
            long capacity = 0L;
            long stored = 0L;
            for (ItemStack stack : player.getArmorSlots()) {
                if ( stack.getItem() instanceof ItemMekaSuitArmor ) {
                    IEnergyContainer container = StorageUtils.getEnergyContainer(stack, 0);
                    if ( container != null ) {
                        capacity += container.getMaxEnergy();
                        stored += container.getEnergy();
                    }
                }
            }
            if ( capacity > 0 ) { // 如果有能量要渲染
                onmek = true;
                int mektext = (int) Math.round((double) stored / (double) capacity * 100.0);
                guiGraphics.blit(POWER_BAR, x + 72, y - 10, 0, 0, 8, 9, 16, 16);
                guiGraphics.drawString(font, mektext + "%", x + 74 - font.width(mektext + "%"), y - 9, Config.Color_Mekanism, false);
            }
        }
    }

    private void renderHealthBar(GuiGraphics guiGraphics, float partialTick, int x, int y, Player player) {
        float maxHealth = player.getMaxHealth();
        float health = Math.min(player.getHealth(), maxHealth);
        // Calculate bar proportions
        float healthProportion;
        float intermediateProportion;
        if ( intermediateHealth > maxHealth ) intermediateHealth = maxHealth;
        if ( health < intermediateHealth ) {
            //healthProportion = health / maxHealth;
            intermediateProportion = (intermediateHealth - health) / maxHealth;
        } else {
            //healthProportion = intermediateHealth / maxHealth;
            intermediateProportion = 0;
        }
        //if (healthProportion > 1) healthProportion = 1F;
        healthProportion = health / maxHealth;
        //if (healthProportion + intermediateProportion > 1) intermediateProportion = 1 - healthProportion;
        int healthWidth = (int) Math.ceil(80 * healthProportion);
        int intermediateWidth = (int) Math.ceil(80 * intermediateProportion);
        // 夹住：血量段 + 过渡段之和不许超过整条宽度 80。
        if ( healthWidth + intermediateWidth > 80 ) intermediateWidth = 80 - healthWidth;
        if ( intermediateWidth < 0 ) intermediateWidth = 0;
        // Display empty part
        guiGraphics.blit(emptyHealthBarLocation,
                x + healthWidth + intermediateWidth, y,
                healthWidth + intermediateWidth, 0,
                80 - healthWidth - intermediateWidth, 5,
                80, 5);

        // Display full part
        guiGraphics.blit(currentBarLocation,
                x, y,
                0, 0,
                healthWidth, 5,
                80, 5);

        float absorption = Math.min(this.absorption, maxHealth);
        float absorptionProportion = absorption / maxHealth;
        if ( absorptionProportion > 1 ) absorptionProportion = 1F;
        int absorptionWidth = (int) Math.ceil(80 * absorptionProportion);
        if ( absorption > 0 ) {
            guiGraphics.blit(absorptionBarLocation,
                    x, y,
                    0, 0,
                    absorptionWidth, 5,
                    80, 5);
        }
        // 动画目标值：有吸收时跟随吸收条顶端，否则跟随当前血量
        float Inshealth = absorption > 0 ? absorption : health;
        // 过渡条锚点用当前血量段右端 healthWidth
        guiGraphics.blit(intermediateHealthBarLocation,
                x + healthWidth, y,
                healthWidth, 0,
                intermediateWidth, 5,
                80, 5);
        // Update intermediate health
        this.intermediateHealth += (Inshealth - intermediateHealth) * partialTick * 0.08;
        if ( Math.abs(Inshealth - intermediateHealth) <= 0.25 ) {
            this.intermediateHealth = Inshealth;
        }
    }
}
