package cn.mcxkly.classicandsimplestatusbars.overlays;

import cn.mcxkly.classicandsimplestatusbars.ClassicAndSimpleStatusBars;
import cn.mcxkly.classicandsimplestatusbars.Config;
import cn.mcxkly.classicandsimplestatusbars.other.helper;
import com.elenai.feathers.Feathers;
import com.elenai.feathers.client.ClientFeathersData;
import com.legacy.blue_skies.BlueSkies;
import com.legacy.blue_skies.capability.SkiesPlayer;
import com.legacy.blue_skies.capability.util.ISkiesPlayer;
import io.github.apace100.apoli.util.HudRender;
import io.github.edwinmindcraft.apoli.api.component.IPowerContainer;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import mekanism.api.energy.IEnergyContainer;
import mekanism.api.math.FloatingLong;
import mekanism.common.Mekanism;
import mekanism.common.item.gear.ItemMekaSuitArmor;
import mekanism.common.util.StorageUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class HealthBar implements IGuiOverlay {

    private static final ResourceLocation fullHealthBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/healthbars/full.png");
    private static final ResourceLocation witherBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/healthbars/wither.png");
    private static final ResourceLocation poisonBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/healthbars/poison.png");
    private static final ResourceLocation frozenBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/healthbars/frozen.png");
    private ResourceLocation currentBarLocation = fullHealthBarLocation;
    private static final ResourceLocation intermediateHealthBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/healthbars/intermediate.png");
    private static final ResourceLocation emptyHealthBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/healthbars/empty.png");
    private static final ResourceLocation absorptionBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/healthbars/absorption.png");
    private static final ResourceLocation guiIconsLocation = new ResourceLocation( "textures/gui/icons.png");

    private static final ResourceLocation feathers = new ResourceLocation(Feathers.MODID, "textures/gui/icons.png");

    private static final ResourceLocation POWER_BAR = new ResourceLocation(Mekanism.MODID, "gui/icons/basic_universal_cable.png");

    private static final ResourceLocation BLUE_SKIES_ICONS = new ResourceLocation(BlueSkies.MODID,"textures/gui/icons.png");

    private float intermediateHealth = 0;

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int width, int height) {
        if ( gui.shouldDrawSurvivalElements() ) {
            Font font = gui.getFont();
            Player player = (Player) Minecraft.getInstance().cameraEntity;
            if ( player == null ) return;
            int x = width / 2 - 91;
            int y = height - 39;
            y += 4;
            if ( Config.All_On && Config.Health_On ) {
                updateBarTextures(player);
                renderHealthBar(guiGraphics, partialTick, x, y, player);
                renderHealthValue(font, guiGraphics, x, y, player, gui);
            } else if ( Config.EasyMode_Text_On ) {
                renderHealthValue_Easy(font, guiGraphics, x, y, player);
            }
        }
    }

    private void renderHealthValue_Easy(Font font, GuiGraphics guiGraphics, int x, int y, Player player) {
        y -= 2;
        float MaxHealth = player.getMaxHealth(); // 最大血量
        float Health = Math.min(player.getHealth(), MaxHealth); // 当前血量
        float Absorption = player.getAbsorptionAmount(); // 吸收量
        int xx = x - 2;
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

    private void renderHealthValue(Font font, GuiGraphics guiGraphics, int x, int Y, Player player,ForgeGui gui) {
        int y = Y + 1;
        float blueSkiesHealth = 0.0f;
        float Absorption = player.getAbsorptionAmount(); // 吸收量
        if ( ClassicAndSimpleStatusBars.blueSkies ) {
            blueSkiesHealth = SkiesPlayer.getIfPresent(player, ISkiesPlayer ::getNatureHealth, () -> 0.0F);
        }
        if (blueSkiesHealth != 0.0f) {
            guiGraphics.blit(BLUE_SKIES_ICONS,
                    x, y - 10,
                    0, 0,
                    9, 9); // 绿心图标
            Absorption += blueSkiesHealth;
        } else {
            guiGraphics.blit(guiIconsLocation,
                    x, y - 10,
                    52, 0,
                    9, 9,
                    256, 256); // 红心图标
        }

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
        if ( ARMOR > 0 && Config.Armour_On ) {
            guiGraphics.drawString(font, helper.KeepOneDecimal(ARMOR), x + 10, y - 19, Config.Color_Armor, false);
            // 重量
            if(ClassicAndSimpleStatusBars.feathers && ClientFeathersData.getWeight() != 0) { // 当护甲和重量都在时
                if ( ClientFeathersData.getWeight() == ARMOR) { // 大部分时候重量和护甲一样，图标渲染在一起，不渲染文字
                    guiGraphics.blit(guiIconsLocation,
                            x, y - 19,
                            43, 9,
                            4, 9,
                            256, 256); // 护甲图标
                    // 重量图标
                    guiGraphics.blit(feathers, x + 4, y - 19 , 56, 9, 5, 9, 256, 256);
                } else { // 如果不一样的话，渲染文字
                    int fx = x + 6 + font.width(String.valueOf(ARMOR));
                    guiGraphics.blit(guiIconsLocation,
                            x, y - 19,
                            43, 9,
                            9, 9,
                            256, 256); // 护甲图标
                    // 重量
                    /* 背景*/guiGraphics.blit(feathers, fx, y - 19 , 16, 0, 9, 9, 256, 256);
                    guiGraphics.blit(feathers, fx, y - 19 , 52, 0, 9, 9, 256, 256);
                    guiGraphics.drawString(font, String.valueOf(ClientFeathersData.getWeight()), fx + 10, y - 19, Config.Color_Armor, false);
                }
            } else if (!ClassicAndSimpleStatusBars.feathers) {
                guiGraphics.blit(guiIconsLocation,
                        x, y - 19,
                        43, 9,
                        9, 9,
                        256, 256); // 护甲图标
            }
        } else if (ClassicAndSimpleStatusBars.feathers && ClientFeathersData.getWeight() != 0) { // 当没有护甲并且也有重量时，一般不会触发。
            /* 背景*/guiGraphics.blit(feathers, x, y - 19 , 16, 0, 9, 9, 256, 256);
            guiGraphics.blit(feathers, x, y - 19 , 52, 0, 9, 9, 256, 256);
            guiGraphics.drawString(font, String.valueOf(ClientFeathersData.getWeight()), x + 10, y - 19, Config.Color_Armor, false);
        }
        boolean onmek = false;
        if ( ClassicAndSimpleStatusBars.mekanism ) {
            gui.setupOverlayRenderState(true, false);
            FloatingLong capacity = FloatingLong.ZERO;
            FloatingLong stored = FloatingLong.ZERO;

            for (ItemStack stack : Objects.requireNonNull(gui.getMinecraft().player).getArmorSlots()) {
                if ( stack.getItem() instanceof ItemMekaSuitArmor ) {
                    IEnergyContainer container = StorageUtils.getEnergyContainer(stack, 0);
                    if ( container != null ) {
                        capacity = capacity.plusEqual(container.getMaxEnergy());
                        stored = stored.plusEqual(container.getEnergy());
                    }
                }
            }
            if ( !capacity.isZero() ) { // 如果有能量要渲染
                onmek = true;
                int mektext = (int) Math.round(stored.divide(capacity).doubleValue() * 100.0);
                guiGraphics.blit(POWER_BAR, x + 72, y - 10 , 0, 0, 8, 9, 16, 16);
                guiGraphics.drawString(font, mektext + "%", x + 74 - font.width(mektext + "%"), y - 9, Config.Color_Armor, false);
            }
        }
        int finalY = Y - 8;
        int finalX = x + 72; // 这是起源用的
        if ( onmek ) {
            finalY -= 10; // 避让 Mekanism 能量条
        }

        if ( ClassicAndSimpleStatusBars.origins ) {
            int finalX1 = finalX;
            int finalY1 = finalY;
            IPowerContainer.get(player).ifPresent((component) -> {
                int iconSize = 8;
                List<? extends ConfiguredPower<?, ?>> configuredPowers = component.getPowers().stream().map(Holder :: value).filter((power) -> power.asHudRendered().isPresent()).sorted(Comparator.comparing((power) -> power.getRenderSettings(player).orElse(HudRender.DONT_RENDER).spriteLocation())).toList();
                for (ConfiguredPower<?, ?> hudPower : configuredPowers) {
                    HudRender render = hudPower.getRenderSettings(player).orElse(HudRender.DONT_RENDER);
                    if ( render.shouldRender(player) && hudPower.shouldRender(player).orElse(false) ) {
                        ResourceLocation currentLocation = render.spriteLocation();
                        int v = 8 + render.barIndex() * 10;
                        float fill = hudPower.getFill(player).orElse(0.0F);
                        if ( render.isInverted() ) {
                            fill = 1.0F - fill;
                        }
                        String tex = helper.KeepOneDecimal((int) (fill * (float) 100));
                        /*
                        int finalY2 = finalY;
                        if ( Absorption > 0 ) {
                            // 避免血量文本太长. 在拥有吸收值时，提高高度.
                            finalY2 -= 10;
                        } */
                        guiGraphics.drawString(font, "%", finalX1 - font.width("%"), finalY1, Config.Color_Origins_Symbol, false);
                        guiGraphics.drawString(font, tex, finalX1 - font.width(tex) - font.width("%"), finalY1, Config.Color_Origins, false);
                        // 渲染图标
                        guiGraphics.blit(currentLocation,
                                finalX1, finalY1,
                                73, v,
                                iconSize, iconSize);
                    }
                }
            });
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
        // 两段各自 Math.ceil 会多算 1px，不夹的话空条宽度会变成负数、过渡条也会画到条外。
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

        float absorption1 = player.getAbsorptionAmount();
        // 绿心
        float blueSkiesHealth = 0.0f;
        if ( ClassicAndSimpleStatusBars.blueSkies ) {
            blueSkiesHealth = SkiesPlayer.getIfPresent(player, ISkiesPlayer ::getNatureHealth, () -> 0.0F);
        }
        if (blueSkiesHealth > 0.0f) { // 计算绿心，防止宽度不正确。
            absorption1 += blueSkiesHealth;
        }

        float absorption = Math.min(absorption1, maxHealth);
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
        // 过渡条的锚点必须是"当前血量条"的右端 healthWidth，而不是吸收条宽度。
        // 原来用 InsWidth（= absorptionWidth）做锚点，吸收顶满时它等于 80，
        // 过渡段就被画到 x+80 之外，血条看起来凭空长出一截。
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
