package cn.mcxkly.classicandsimplestatusbars.overlays;

import artifacts.component.SwimData;
import artifacts.platform.PlatformServices;
import cn.mcxkly.classicandsimplestatusbars.ClassicAndSimpleStatusBars;
import cn.mcxkly.classicandsimplestatusbars.Config;
import cn.mcxkly.classicandsimplestatusbars.other.HudIcons;
import cn.mcxkly.classicandsimplestatusbars.other.helper;
import com.alrex.parcool.client.hud.impl.HUDType;
import com.alrex.parcool.common.attachment.client.LocalStamina;
import com.alrex.parcool.config.ParCoolConfig;
import de.teamlapen.vampirism.entity.player.vampire.VampirePlayer;
import de.teamlapen.vampirism.util.Helper;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import sfiomn.legendarysurvivaloverhaul.registry.MobEffectRegistry;

import java.util.Objects;

public class FoodLevel implements LayeredDraw.Layer {
    private static final String ARTIFACTS_ID = "artifacts";
    // 火烈鸟饰品, 用来查它是否在冷却
    private static final Item HELIUM_FLAMINGO_ITEM = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(ARTIFACTS_ID, "helium_flamingo"));
    private static final String PARCOOL_ID = "parcool";
    private static final ResourceLocation vampiresBarLocation = ResourceLocation.fromNamespaceAndPath(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/vampires.png");
    private static final ResourceLocation fullHealthBarLocation = ResourceLocation.fromNamespaceAndPath(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/foodeeg.png");
    private static final ResourceLocation emptyHealthBarLocation = ResourceLocation.fromNamespaceAndPath(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/empty.png");
    private static final ResourceLocation saturationBarLocation = ResourceLocation.fromNamespaceAndPath(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/saturation.png");
    private ResourceLocation currentBarLocation = fullHealthBarLocation;
    private static final ResourceLocation emmmmnBarLocation = ResourceLocation.fromNamespaceAndPath(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/debuff-hunger.png");
    private static final ResourceLocation intermediateHealthBarLocation = ResourceLocation.fromNamespaceAndPath(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/intermediate.png");
    private float intermediateFood = 0;
    public static boolean StopConflictRendering = true; // 如果 false ，抬高渲染，为需要水分的模组兼容.

    public static void StopConflictRenderingIDEA(boolean is) {
        StopConflictRendering = is;
    }

    public static boolean ArtifactsAir = false; // 奇异饰品-火烈鸟
    private static final ResourceLocation HELIUM_FLAMINGO_ICON = ResourceLocation.fromNamespaceAndPath(ARTIFACTS_ID, "textures/gui/icons.png");

    public static void ArtifactsIDEA(boolean b) {
        ArtifactsAir = b;
    }

    private static final ResourceLocation STAMINA = ResourceLocation.fromNamespaceAndPath(PARCOOL_ID, "textures/gui/stamina_bar.png");

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();
        if ( mc.options.hideGui ) return;
        if ( mc.gameMode == null || !mc.gameMode.canHurtPlayer() ) return;
        if ( !(mc.getCameraEntity() instanceof Player player) ) return;

        Font font = mc.font;
        int x = guiGraphics.guiWidth() / 2 + 11;
        int y = guiGraphics.guiHeight() - 39;
        y += 4;

        float partialTick = deltaTracker.getGameTimeDeltaPartialTick(false);
        if ( Config.Food_On && Config.All_On ) {
            updateBarTextures(player);
            // 其他元素
            renderFoodValue(font, guiGraphics, x, y, player);
            if ( ClassicAndSimpleStatusBars.vampirism && Helper.isVampire(player) ) {
                // 如果当前是吸血鬼.
                if ( Config.Bloodsucker_On ) {
                    // 如果开启功能，将渲染血条,否则仅把鸡腿饱食度文本替换成吸血鬼血液文本
                    renderVampiresBar(guiGraphics, partialTick, x, y, player);
                }
                // 吸血鬼血液文本
                renderInfectedVampires(font, guiGraphics, x, y, player);
            } else {
                // 文本
                renderFood(font, guiGraphics, x, y, player);
                // 状态栏图
                renderFoodBar(guiGraphics, partialTick, x, y, player);
            }
        } else if ( Config.EasyMode_Text_On ) {
            renderFoodValue_Easy(font, guiGraphics, x, y, player);
        }
    }

    private void renderFoodValue_Easy(Font font, GuiGraphics guiGraphics, int x, int y, Player player) {
        y -= 2;
        String text;
        text = helper.KeepOneDecimal(player.getFoodData().getFoodLevel());
        int xx = x + 82; // 右侧
        guiGraphics.drawString(font, text, xx, y - 1, Config.Color_Food, false);
        if ( player.getFoodData().getSaturationLevel() > 0 ) {
            //第二部分
            xx = xx + font.width(text); // '+'
            text = Config.Interval_TTT;
            guiGraphics.drawString(font, text, xx, y - 1, Config.Color_Interval_TTT, false);

            xx = xx + font.width(text);
            text = helper.KeepOneDecimal(player.getFoodData().getSaturationLevel());
            guiGraphics.drawString(font, text, xx, y - 1, Config.Color_Food_Saturation, false);
        }
        boolean OnMaxFood = (Config.MaxFood_On == 1) || (Config.MaxFood_On == 0 && player.getFoodData().getLastFoodLevel() > 20);
        if ( OnMaxFood ) {
            //第三部分  Max
            xx = xx + font.width(text); // '/'
            text = Config.Interval_lll;
            guiGraphics.drawString(font, text, xx, y - 1, Config.Color_Interval_lll, false);

            xx = xx + font.width(text);
            text = helper.KeepOneDecimal(player.getFoodData().getLastFoodLevel());
            guiGraphics.drawString(font, text, xx, y - 1, Config.Color_Food_Tail, false);
        }
    }

    float intermediate = 0;

    private void renderVampiresBar(GuiGraphics guiGraphics, float partialTick, int x, int y, Player player) {
        VampirePlayer.getOpt(player).map(VampirePlayer :: getBloodStats).ifPresent((stats) -> {
            float BloodValue = stats.getBloodLevel();
            float maxBlood = stats.getMaxBlood();
            float BloodProportion = BloodValue / maxBlood;
            float Proportion;
            float intermediateProportion;

            if ( BloodValue < intermediate ) {
                intermediateProportion = (intermediate - BloodValue) / maxBlood;
            } else {
                intermediateProportion = 0;
            }
            Proportion = BloodValue / maxBlood;
            int Width = (int) Math.ceil(80 * Proportion);
            int saturationWidth = (int) Math.ceil(80 * BloodProportion);
            int intermediateWidth = (int) Math.ceil(80 * intermediateProportion);

            // Display empty part
            guiGraphics.blit(emptyHealthBarLocation,
                    x, y,
                    0, 0,
                    80 - Width - intermediateWidth, 5,
                    80, 5);

            // 血条
            guiGraphics.blit(vampiresBarLocation,
                    x + 80 - Width, y,
                    80 - Width, 0,
                    Width, 5,
                    80, 5);

            // Display intermediate part
            guiGraphics.blit(intermediateHealthBarLocation,
                    x + 80 - Width - intermediateWidth, y,
                    80 - Width - intermediateWidth, 0,
                    intermediateWidth, 5,
                    80, 5);

            // Update intermediate health
            intermediate += (BloodValue - intermediate) * partialTick * 0.08;
            if ( Math.abs(BloodValue - intermediate) <= 0.25 ) {
                intermediate = BloodValue;
            }
        });
    }

    private void renderInfectedVampires(Font font, GuiGraphics guiGraphics, int x, int y, Player player) {
        y += 1;
        int finalY = y;
        VampirePlayer.getOpt(player).map(VampirePlayer :: getBloodStats).ifPresent((stats) -> {
            String text;
            int blood = stats.getBloodLevel();
            int maxBlood = stats.getMaxBlood();
            guiGraphics.blitSprite(HudIcons.VAMPIRISM_BLOOD, x, finalY - 10, 9, 9); // 血液
            text = helper.KeepOneDecimal(blood);
            int xx = x + 10;
            guiGraphics.drawString(font, text, xx, finalY - 9, Config.Color_Vampires_Blood, false);
            xx = xx + font.width(text);
            text = Config.Interval_lll;
            guiGraphics.drawString(font, text, xx, finalY - 9, Config.Color_Interval_lll, false);
            xx = xx + font.width(text);
            text = helper.KeepOneDecimal(maxBlood);
            guiGraphics.drawString(font, text, xx, finalY - 9, Config.Color_Vampires_MaxBlood, false);
        });
    }

    public void updateBarTextures(Player player) {
        if ( player.hasEffect(MobEffects.HUNGER) ) {
            currentBarLocation = emmmmnBarLocation;
        } else {
            currentBarLocation = fullHealthBarLocation;
        }
    }

    /** 鸡腿图标: 严寒时换成传说生存的冷冻鸡腿, 对方自带金边素材; 其余情况用原版图标 */
    private void renderFoodIcon(GuiGraphics guiGraphics, int iconX, int iconY, Player player) {
        boolean lso = ClassicAndSimpleStatusBars.legendarysurvivaloverhaul;
        boolean cold = lso && player.hasEffect(MobEffectRegistry.COLD_HUNGER);
        boolean saturation = player.getFoodData().getSaturationLevel() > 0;
        if ( cold ) {
            guiGraphics.blit(HudIcons.LSO_ICONS, iconX, iconY,
                    HudIcons.LSO_FOOD_COLD_U, HudIcons.LSO_FOOD_COLD_V, 9, 9); // 冷冻鸡腿
            if ( saturation ) {
                guiGraphics.blit(HudIcons.LSO_ICONS, iconX, iconY,
                        HudIcons.LSO_FOOD_GOLD_U, HudIcons.LSO_FOOD_GOLD_V, 9, 9); // 冷冻鸡腿-金边
            }
            return;
        }
        guiGraphics.blitSprite(HudIcons.FOOD_EMPTY, iconX, iconY, 9, 9); // 鸡腿图标-背景
        guiGraphics.blitSprite(HudIcons.FOOD_FULL, iconX, iconY, 9, 9); // 鸡腿图标
    }

    private void renderFood(Font font, GuiGraphics guiGraphics, int x, int y, Player player) {
        y += 1;
        String text;
        renderFoodIcon(guiGraphics, x, y - 10, player); // 鸡腿图标

        text = helper.KeepOneDecimal(player.getFoodData().getFoodLevel());
        int xx = x + 10;
        guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Food, false);
        if ( player.getFoodData().getSaturationLevel() > 0 ) {
            //第二部分
            xx = xx + font.width(text); // '+'
            text = Config.Interval_TTT;
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Interval_TTT, false);

            xx = xx + font.width(text);
            text = helper.KeepOneDecimal(player.getFoodData().getSaturationLevel());
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Food_Saturation, false);
        }
        boolean OnMaxFood = (Config.MaxFood_On == 1) || (Config.MaxFood_On == 0 && player.getFoodData().getLastFoodLevel() > 20);
        if ( OnMaxFood ) {
            //第三部分  Max
            xx = xx + font.width(text); // '/'
            text = Config.Interval_lll;
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Interval_lll, false);

            xx = xx + font.width(text);
            text = helper.KeepOneDecimal(player.getFoodData().getLastFoodLevel());
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Food_Tail, false);
        }
    }

    private void renderFoodValue(Font font, GuiGraphics guiGraphics, int x, int y, Player player) {
        // getSaturationLevel饱食条 | getFoodLevel饥饿度 |  getLastFoodLevel饥饿最大值 | player.getFoodData().getExhaustionLevel(); 消耗度
        y += 1;
        String text;
        if ( Config.Air_On && player.getAirSupply() < 300 ) { // max=300
            int siz = player.getAirSupply() / 3;
            siz = Math.max(siz, 0); //防止负数
            text = String.valueOf(siz);
            int y2 = y;
            if ( !StopConflictRendering ) y2 -= 10; // 如果口渴/意志坚定存在，在渲染时高度 + 10
            guiGraphics.drawString(font, "%", x + 70 - font.width("%"), y2 - 9, Config.Color_Air_Symbol, false);

            guiGraphics.drawString(font, text, x + 70 - font.width(text) - font.width("%"), y2 - 9, Config.Color_Air, false);
            guiGraphics.blitSprite(HudIcons.AIR, x + 70, y2 - 10, 9, 9); // 气泡图标
        }
        int isArtifactsAir = 0;
        if ( ArtifactsAir && Config.Artifacts_On ) {
            SwimData swimData = PlatformServices.getPlatformHelper().getSwimData(player);
            if ( swimData != null ) {
                // 13.x 的 getSwimProgress 是已消耗比例(0=满,1=空)，取反换算回剩余比例
                double progress = 1.0D - swimData.getSwimProgress();
                // 冷却期间该饰品被判定为不可用, 这里的破裂图标与破裂音效同一刻出现
                boolean broken = player.getCooldowns().isOnCooldown(HELIUM_FLAMINGO_ITEM);
                if ( progress < 1.0D ) { // 满值时不渲染
                    int AirY = y;
                    if ( Config.Air_On && player.getAirSupply() < 300 ) AirY -= 10; // 如果渲染了氧气值，在渲染时高度 + 10
                    if ( !StopConflictRendering ) AirY -= 10; // 如果口渴/意志坚定存在，在渲染时高度 + 10
                    int swimTimes = (int) Math.round(progress * 100);
                    swimTimes = Math.max(swimTimes, 0); //防止负数
                    String texts = swimTimes + "";
                    guiGraphics.drawString(font, "%", x + 70 - font.width("%"), AirY - 9, Config.Color_Artifacts_Symbol, false);
                    guiGraphics.drawString(font, texts, x + 70 - font.width(texts) - font.width("%"), AirY - 9, Config.Color_Artifacts, false);
                    guiGraphics.blit(HELIUM_FLAMINGO_ICON,
                            x + 70, AirY - 10,
                            (broken ? 9 : 0), 0,
                            9, 9,
                            32, 16); // 烈火鸟 泳圈: u=0 完整 / u=9 破裂
                    isArtifactsAir = font.width(texts);
                }
            }
        }

        if ( ClassicAndSimpleStatusBars.parcool ) {
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            if ( localPlayer != null && !localPlayer.isCreative() ) {
                if ( ParCoolConfig.Client.getInstance().StaminaHUDType.get() != HUDType.Normal ) {
                    LocalStamina stamina = LocalStamina.get(localPlayer);
                    if ( stamina != null && stamina.isAvailable() ) {
                        int value = stamina.getValue(localPlayer);
                        int max = stamina.getMax(localPlayer);
                        float staminaScale = max > 0 ? (float) value / (float) max : 0F;
                        int textureX = stamina.isExhausted(localPlayer) ? 27 : 0;
                        if ( staminaScale <= 0.3F ) {
                            textureX += 18;
                        } else if ( staminaScale <= 0.5F ) {
                            textureX += 9;
                        } // 跑酷，雷电图标
                        int AirY = y;
                        int AirX = x;
                        if ( Config.Air_On && player.getAirSupply() < 300 ) AirY -= 10; // 如果渲染了氧气值，在渲染时高度 + 10
                        if ( !StopConflictRendering ) AirY -= 10; // 如果口渴/意志坚定存在，在渲染时高度 + 10
                        if ( isArtifactsAir != 0 ) AirX -= (font.width("99%") + 9/* 图标宽9 */); // isArtifactsAir 如果渲染泳圈, 为了美观手动改一下吧。
                        String texts = String.valueOf(value / 20); // max 2000 / 20
                        guiGraphics.blit(STAMINA, AirX + 70, AirY - 10, (float) textureX, 119.0F, 9, 9, 129, 128);
                        guiGraphics.drawString(font, texts, AirX + 70 - font.width(texts), AirY - 9, Config.Color_Stamina, false);
                    }
                }
            }
        }

        // 骑乘血量: 用原版判据(实体是否生物 + 该生物是否显示骑乘血条),
        // 船 /矿车这类非生物载具已被 instanceof 挡掉
        Entity vehicle = player != null ? player.getVehicle() : null;
        boolean MountRow = false; // 该行是否已被坐骑占用(占用则护甲韧性让位)
        if ( vehicle instanceof LivingEntity mount && mount.showVehicleHealth() ) {
            float MountHealthsMax = mount.getMaxHealth();
            float MountHealths = Math.min(mount.getHealth(), MountHealthsMax);
            if ( Config.Mounts_On && MountHealths > 0 ) {
                MountRow = true;
                guiGraphics.blitSprite(HudIcons.VEHICLE_FULL, x, y - 20, 9, 9);
                // 骑乘血量
                String text_Mount = helper.KeepOneDecimal(MountHealths);
                int X_Mount = x + 10;
                guiGraphics.drawString(font, text_Mount, X_Mount, y - 19, Config.Color_Mount, false);
                X_Mount += font.width(text_Mount);
                text_Mount = Config.Interval_lll;
                guiGraphics.drawString(font, text_Mount, X_Mount, y - 19, Config.Color_Interval_lll, false);
                X_Mount += font.width(text_Mount);
                text_Mount = helper.KeepOneDecimal(MountHealthsMax);
                guiGraphics.drawString(font, text_Mount, X_Mount, y - 19, Config.Color_Mount_Tail, false);
            }
        }
        if ( !MountRow ) {
            if ( Config.Armor_Toughness_On ) {
                float ARMORTOUGHNESS = (float) Objects.requireNonNull(player.getAttribute(Attributes.ARMOR_TOUGHNESS)).getValue();
                if ( ARMORTOUGHNESS > 0 ) {
                    // 韧性图标: 原版没有该图标, 用护甲图标配韧性色染色代替
                    setTint(guiGraphics, Config.Color_Armor_Toughness);
                    guiGraphics.blitSprite(HudIcons.ARMOR_FULL, x, y - 20, 9, 9);
                    guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F); // 还原染色, 否则会影响后面的文字
                    guiGraphics.drawString(font, helper.KeepOneDecimal(ARMORTOUGHNESS), x + 10, y - 19, Config.Color_Armor_Toughness, false);
                }
            }
        }
    }

    /** 把配置里的 0xRRGGBB 转成 shader 染色, 用完要 setColor(1,1,1,1) 还原 */
    private static void setTint(GuiGraphics guiGraphics, int rgb) {
        guiGraphics.setColor(((rgb >> 16) & 0xFF) / 255.0F, ((rgb >> 8) & 0xFF) / 255.0F, (rgb & 0xFF) / 255.0F, 1.0F);
    }

    private void renderFoodBar(GuiGraphics guiGraphics, float partialTick, int x, int y, Player player) {
        //float maxFood = 20; // 不，不能这样用。
        float maxFood = player.getFoodData().getLastFoodLevel();

        float Food = Math.min(player.getFoodData().getFoodLevel(), maxFood);
        float saturationProportion = player.getFoodData().getSaturationLevel() / maxFood;

        // 计算状态线比例
        float FoodProportion;
        float intermediateProportion;
        if ( Food < intermediateFood ) {
            //FoodProportion = Food / maxFood;
            intermediateProportion = (intermediateFood - Food) / maxFood;
        } else {
            //FoodProportion = intermediateFood / maxFood;
            intermediateProportion = 0;
        }
        FoodProportion = Food / maxFood;
        //if (FoodProportion > 1) FoodProportion = 1F;
        //if (FoodProportion + intermediateProportion > 1) intermediateProportion = 1 - FoodProportion;
        int FoodWidth = (int) Math.ceil(80 * Math.min(FoodProportion, 1));
        int saturationWidth = (int) Math.ceil(80 * Math.min(saturationProportion, 1));
        int intermediateWidth = (int) Math.ceil(80 * intermediateProportion);

        // 显示空部分
        guiGraphics.blit(emptyHealthBarLocation,
                x, y,
                0, 0,
                80 - FoodWidth - intermediateWidth, 5,
                80, 5);

        // 饱食度
        guiGraphics.blit(currentBarLocation,
                x + 80 - FoodWidth, y,
                80 - FoodWidth, 0,
                FoodWidth, 5,
                80, 5);

        // 额外 饱和度
        guiGraphics.blit(saturationBarLocation,
                x + 80 - saturationWidth, y,
                80 - saturationWidth, 0,
                saturationWidth, 5,
                80, 5);

        // 显示中间部分
        guiGraphics.blit(intermediateHealthBarLocation,
                x + 80 - FoodWidth - intermediateWidth, y,
                80 - FoodWidth - intermediateWidth, 0,
                intermediateWidth, 5,
                80, 5);

        // 疲劳值 某些情况下无法获取，当满的时候隐藏
        if ( Config.Food_ExhaustionLevel_On ) {
            float exhaustionLevel = player.getFoodData().getExhaustionLevel();
            // Check if the exhaustion level is not at its maximum before proceeding
            if ( exhaustionLevel == 40.0 ) {
                int visualLength = Math.max(1, (int) ((4f - exhaustionLevel) * 78f) / 4);
                guiGraphics.hLine(x + 79 - visualLength, x + 78, y + 4, Config.Color_Food_ExhaustionLevel);
            }
        }

        float InsFood;
        if ( player.getFoodData().getSaturationLevel() > 0 ) {
            InsFood = player.getFoodData().getSaturationLevel();
        } else {
            InsFood = Food;
        }
        // 更新中间状态
        this.intermediateFood += (InsFood - intermediateFood) * partialTick * 0.08;
        if ( Math.abs(InsFood - intermediateFood) <= 0.25 ) {
            this.intermediateFood = InsFood;
        }
    }
}
