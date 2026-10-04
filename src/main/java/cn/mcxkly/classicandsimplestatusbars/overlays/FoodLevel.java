package cn.mcxkly.classicandsimplestatusbars.overlays;

import arcaios26.supersaturation.data.CapabilitySuperSat;
import artifacts.Artifacts;
import artifacts.component.SwimData;
import artifacts.platform.PlatformServices;
import artifacts.registry.ModGameRules;
import cn.mcxkly.classicandsimplestatusbars.ClassicAndSimpleStatusBars;
import cn.mcxkly.classicandsimplestatusbars.Config;
import cn.mcxkly.classicandsimplestatusbars.other.LsoIcons;
import cn.mcxkly.classicandsimplestatusbars.other.helper;
import com.alrex.parcool.ParCool;
import com.alrex.parcool.client.hud.impl.HUDType;
import com.alrex.parcool.common.capability.IStamina;
import com.alrex.parcool.common.capability.Parkourability;
import com.alrex.parcool.config.ParCoolConfig;
import com.elenai.feathers.Feathers;
import com.elenai.feathers.client.ClientFeathersData;
import com.elenai.feathers.effect.FeathersEffects;
import de.teamlapen.vampirism.REFERENCE;
import de.teamlapen.vampirism.entity.player.vampire.VampirePlayer;
import de.teamlapen.vampirism.util.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import sfiomn.legendarysurvivaloverhaul.registry.MobEffectRegistry;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class FoodLevel implements IGuiOverlay {
    private final ResourceLocation Vampires_Icons = new ResourceLocation(REFERENCE.MODID, "textures/gui/icons.png");
    private static final ResourceLocation vampiresBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/vampires.png");
    private static final ResourceLocation fullHealthBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/foodeeg.png");
    private static final ResourceLocation emptyHealthBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/empty.png");
    private static final ResourceLocation saturationBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/saturation.png");
    private ResourceLocation currentBarLocation = fullHealthBarLocation;
    private static final ResourceLocation emmmmnBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/debuff-hunger.png");
    private static final ResourceLocation intermediateHealthBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/intermediate.png");
    private static final ResourceLocation guiIconsLocation = new ResourceLocation("textures/gui/icons.png");
    private float intermediateFood = 0;
    public static boolean StopConflictRendering = true; // 如果 false ，抬高渲染，为需要水分的模组兼容.

    public static void StopConflictRenderingIDEA(boolean is) {
        StopConflictRendering = is;
    }

    public static boolean ArtifactsAir = false; // 奇异饰品-火烈鸟
    private static final ResourceLocation HELIUM_FLAMINGO_ICON = new ResourceLocation(Artifacts.MOD_ID, "textures/gui/icons.png");

    public static void ArtifactsIDEA(boolean b) {
        ArtifactsAir = b;
    }

    private static final ResourceLocation STAMINA = new ResourceLocation(ParCool.MOD_ID, "textures/gui/stamina_bar.png");

    private static final ResourceLocation feathers = new ResourceLocation(Feathers.MODID, "textures/gui/icons.png");

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int width, int height) {
        if ( gui.shouldDrawSurvivalElements() ) {
            Font font = gui.getFont();
            font.width(String.valueOf(10));

            Player player = (Player) Minecraft.getInstance().cameraEntity;
            if ( player == null ) return;
            int x = width / 2 + 11;
            int y = height - 39;
            y += 4;

            // y -= 70; // test
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
    }

    private void renderFoodValue_Easy(Font font, GuiGraphics guiGraphics, int x, int y, Player player) {
        AtomicInteger AddedHunger = new AtomicInteger();
        AtomicReference<Float> AddedSat = new AtomicReference<>(0.0f);
        if ( ClassicAndSimpleStatusBars.supersaturation ) {
            player.getCapability(CapabilitySuperSat.SUPER_SAT, (Direction) null).ifPresent((c) -> {
                AddedHunger.set(c.getHunger());
                AddedSat.set(c.getSat());
            });
        }

        y -= 2;
        String text;
        text = helper.KeepOneDecimal(player.getFoodData().getFoodLevel() + AddedHunger.get());
        int xx = x + 82; // 右侧
        guiGraphics.drawString(font, text, xx, y - 1, Config.Color_Food, false);
        if ( player.getFoodData().getSaturationLevel() + AddedSat.get() > 0 ) {
            //第二部分
            xx = xx + font.width(text); // '+'
            text = Config.Interval_TTT;
            guiGraphics.drawString(font, text, xx, y - 1, Config.Color_Interval_TTT, false);

            xx = xx + font.width(text);
            text = helper.KeepOneDecimal(player.getFoodData().getSaturationLevel() + AddedSat.get());
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
            guiGraphics.blit(Vampires_Icons,
                    x, finalY - 10,
                    9, 9,
                    9, 9); // 血液
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
        boolean cold = ClassicAndSimpleStatusBars.legendarysurvivaloverhaul
                && player.hasEffect(MobEffectRegistry.COLD_HUNGER.get());
        if ( cold ) {
            guiGraphics.blit(LsoIcons.LSO_ICONS, iconX, iconY,
                    LsoIcons.FOOD_COLD_U, LsoIcons.FOOD_COLD_V, 9, 9, LsoIcons.SIZE, LsoIcons.SIZE); // 冷冻鸡腿
            if ( player.getFoodData().getSaturationLevel() > 0 ) {
                guiGraphics.blit(LsoIcons.LSO_ICONS, iconX, iconY,
                        LsoIcons.FOOD_GOLD_U, LsoIcons.FOOD_GOLD_V, 9, 9, LsoIcons.SIZE, LsoIcons.SIZE); // 冷冻鸡腿-金边
            }
            return;
        }
        guiGraphics.blit(guiIconsLocation,
                iconX, iconY,
                16, 27,
                9, 9,
                256, 256); // 鸡腿图标-背景
        guiGraphics.blit(guiIconsLocation,
                iconX, iconY,
                52, 27,
                9, 9,
                256, 256); // 鸡腿图标
    }

    private void renderFood(Font font, GuiGraphics guiGraphics, int x, int y, Player player) {
        y += 1;
        String text;
        renderFoodIcon(guiGraphics, x, y - 10, player); // 鸡腿图标

        AtomicInteger AddedHunger = new AtomicInteger();
        AtomicReference<Float> AddedSat = new AtomicReference<>(0.0f);
        if ( ClassicAndSimpleStatusBars.supersaturation ) {
            player.getCapability(CapabilitySuperSat.SUPER_SAT, (Direction) null).ifPresent((c) -> {
                AddedHunger.set(c.getHunger());
                AddedSat.set(c.getSat());
            });
        }

        text = helper.KeepOneDecimal(player.getFoodData().getFoodLevel() + AddedHunger.get());
        int xx = x + 10;
        guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Food, false);
        if ( player.getFoodData().getSaturationLevel() + AddedSat.get() > 0 ) {
            //第二部分
            xx = xx + font.width(text); // '+'
            text = Config.Interval_TTT;
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Interval_TTT, false);

            xx = xx + font.width(text);
            text = helper.KeepOneDecimal(player.getFoodData().getSaturationLevel() + AddedSat.get());
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
            guiGraphics.blit(guiIconsLocation,
                    x + 70, y2 - 10,
                    16, 18,
                    9, 9,
                    256, 256); // 气泡图标
        }
        int isArtifactsAir = 0;
        if ( ArtifactsAir && Config.Artifacts_On ) {
            SwimData swimData = PlatformServices.platformHelper.getSwimData(player);
            if ( swimData != null ) {
                int swimTime = swimData.getSwimTime(); // 正 = 本次飞行已用刻数, 负 = 回充剩余刻数, 0 = 已充满
                if ( swimTime != 0 ) { // 充满时不渲染
                    int AirY = y;
                    if ( Config.Air_On && player.getAirSupply() < 300 ) AirY -= 10; // 如果渲染了氧气值，在渲染时高度 + 10
                    if ( !StopConflictRendering ) AirY -= 10; // 如果口渴/意志坚定存在，在渲染时高度 + 10
                    boolean recharging = swimTime < 0; // 回充中 = 泳圈不可用
                    // GameRule 自 9.5.x 起已做 秒→刻 换算, 此处不再乘 20
                    int fullTime = recharging
                            ? Math.max(20, ModGameRules.HELIUM_FLAMINGO_RECHARGE_DURATION.get())
                            : Math.max(1, ModGameRules.HELIUM_FLAMINGO_FLIGHT_DURATION.get());
                    int swimTimes = 100 - Math.min(100, Math.abs(swimTime) * 100 / fullTime); // 剩余可用比例
                    String texts = Math.max(swimTimes, 0) + ""; //防止负数
                    guiGraphics.drawString(font, "%", x + 70 - font.width("%"), AirY - 9, Config.Color_Artifacts_Symbol, false);
                    guiGraphics.drawString(font, texts, x + 70 - font.width(texts) - font.width("%"), AirY - 9, Config.Color_Artifacts, false);
                    guiGraphics.blit(HELIUM_FLAMINGO_ICON,
                            x + 70, AirY - 10,
                            (recharging ? 9 : 0), 0,
                            9, 9,
                            32, 16); // 烈火鸟 泳圈: u=0 完整 / u=9 破裂
                    isArtifactsAir = font.width(texts);
                } else {
                    isArtifactsAir = 0;
                }
            }
        }

        if (ClassicAndSimpleStatusBars.parcool)   {
            if ( Objects.requireNonNull(ParCoolConfig.Client.StaminaHUDType.get()) != HUDType.Normal ) {
                if (player != null) {
                    if (!player.isCreative()) {
                        IStamina stamina = IStamina.get(player);
                        Parkourability parkourability = Parkourability.get(player);
                        if (stamina != null && parkourability != null) {
                            if ( !ParCoolConfig.Client.Booleans.HideStaminaHUDWhenStaminaIsInfinite.get() || !parkourability.getActionInfo().isStaminaInfinite(player.isCreative() || player.isSpectator()) ) {
                                float staminaScale = stamina.get();
                                boolean exhausted = stamina.isExhausted();
                                boolean justBecameMax = stamina.getOldValue() < stamina.get() && stamina.get() == stamina.getActualMaxStamina();
                                int textureX = exhausted ? 27 : 0;
                                if ( justBecameMax ) {
                                    textureX = 81;
                                } else if ( staminaScale <= (float) 1 ) {
                                    textureX += 18;
                                } else if ( staminaScale < (float) 1 + 0.5F ) {
                                    textureX += 9;
                                } // 跑酷，雷电图标
                                int AirY = y;
                                int AirX = x;
                                String texts;
                                if ( Config.Air_On && player.getAirSupply() < 300 ) AirY -= 10; // 如果渲染了氧气值，在渲染时高度 + 10
                                if ( !StopConflictRendering ) AirY -= 10; // 如果口渴/意志坚定存在，在渲染时高度 + 10
                                if ( isArtifactsAir != 0 ) AirX -= (font.width("99%") + 9/* 图标宽9 */); // isArtifactsAir 如果渲染泳圈, 为了美观手动改一下吧。
                                texts = String.valueOf((int)(staminaScale / 20)); // max 2000 / 20
                                guiGraphics.blit(STAMINA, AirX + 70, AirY - 10, (float) textureX, 119.0F, 9, 9, 129, 128);
                                guiGraphics.drawString(font, texts, AirX + 70 - font.width(texts), AirY - 9, Config.Color_Stamina, false);
                            }
                        }
                    }
                }
            }
        }

        Entity tsssmp = null;
        if ( player != null ) {
            tsssmp = player.getVehicle();
        }
        boolean NotAValidMount = false; // 防止玩家正在乘坐，但并不是有效的坐骑（比如船）.
        if ( tsssmp != null ) {
            if ( tsssmp.getType() == EntityType.SKELETON_HORSE ||
                    tsssmp.getType() == EntityType.PIG ||
                    tsssmp.getType() == EntityType.HORSE ||
                    tsssmp.getType() == EntityType.CAMEL ||
                    tsssmp.getType() == EntityType.MULE ||
                    tsssmp.getType() == EntityType.STRIDER ||
                    tsssmp.getType() == EntityType.TRADER_LLAMA ) {
                LivingEntity FsMount = (LivingEntity) tsssmp;
                float MountHealthsMax = FsMount.getMaxHealth();
                float MountHealths = Math.min(FsMount.getHealth(), MountHealthsMax);
                if ( MountHealths > 0 ) {
                    guiGraphics.blit(guiIconsLocation,
                            x, y - 20,
                            88, 9,
                            9, 9,
                            256, 256);
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
                NotAValidMount = true;
            }
        }
        if ( !NotAValidMount ) {
            int ARMORTOUGHNESS_X= 0;
            if ( Config.Armor_Toughness_On ) {
                float ARMORTOUGHNESS = 0;
                if ( player != null ) {
                    ARMORTOUGHNESS = (float) Objects.requireNonNull(player.getAttribute(Attributes.ARMOR_TOUGHNESS)).getValue();
                }
                if ( ARMORTOUGHNESS > 0 ) {
                    guiGraphics.blit(guiIconsLocation,
                            x, y - 20,
                            43, 9,
                            9, 9,
                            256, 256); // 护甲图标
                    guiGraphics.blit(guiIconsLocation,
                            x, y - 20,
                            43, 18,
                            9, 9,
                            256, 256); // 韧性图标
                    ARMORTOUGHNESS_X = font.width(String.valueOf(ARMORTOUGHNESS));
                    guiGraphics.drawString(font, helper.KeepOneDecimal(ARMORTOUGHNESS), x + 10, y - 19, Config.Color_Armor_Toughness, false);
                }
            }

            // 羽毛耐力 Mod
            int fx = 0;
            if (ARMORTOUGHNESS_X !=0 ) fx += (ARMORTOUGHNESS_X + 10);
            if(ClassicAndSimpleStatusBars.feathers && player != null) {
                String FeathersValue = String.valueOf(Math.max(0, ClientFeathersData.getFeathers() + ClientFeathersData.getEnduranceFeathers() - ClientFeathersData.getWeight()));
                if (player.hasEffect(FeathersEffects.COLD.get())){
                    // 无法重生羽毛
                    /* 背景*/guiGraphics.blit(feathers, x + fx, y - 20 , 16, 0, 9, 9, 256, 256);
                    guiGraphics.blit(feathers, x + fx, y - 20 , 34, 0, 9, 9, 256, 256);
                    guiGraphics.blit(feathers, x + fx, y - 20 , 61, 9, 9, 9, 256, 256);
                    guiGraphics.drawString(font, FeathersValue, x + fx + 10, y - 19, Config.Color_Armor, false);
                } else if ( player.hasEffect(FeathersEffects.ENDURANCE.get())) {
                    // 溢出羽毛
                    /* 背景*/guiGraphics.blit(feathers, x + fx, y - 20 , 16, 0, 9, 9, 256, 256);
                    guiGraphics.blit(feathers, x + fx, y - 20 , 34, 0, 9, 9, 256, 256);
                    guiGraphics.blit(feathers, x + fx, y - 20 , 61, 0, 9, 9, 256, 256);
                    guiGraphics.drawString(font, FeathersValue, x + fx + 10, y - 19, Config.Color_Armor, false);
                } else if ( player.hasEffect(FeathersEffects.ENERGIZED.get()) ) {
                    // 快速恢复
                    /* 背景*/guiGraphics.blit(feathers, x + fx, y - 20 , 16, 0, 9, 9, 256, 256);
                    guiGraphics.blit(feathers, x + fx, y - 20 , 34, 0, 9, 9, 256, 256);
                    guiGraphics.blit(feathers, x + fx, y - 20 , 25, 18, 9, 9, 256, 256);
                    guiGraphics.drawString(font, FeathersValue, x + fx + 10, y - 19, Config.Color_Armor, false);
                } else { // 默认图标
                    /* 背景*/
                    guiGraphics.blit(feathers, x + fx, y - 20, 16, 0, 9, 9, 256, 256);
                    guiGraphics.blit(feathers, x + fx, y - 20, 34, 0, 9, 9, 256, 256);
                    guiGraphics.drawString(font, FeathersValue, x + fx + 10, y - 19, Config.Color_Armor, false);
                }

                if (false) { // 测试图标用
                    // 数值文本
                    String texts =  "Feathers:" + (ClientFeathersData.getFeathers() + ClientFeathersData.getEnduranceFeathers() - ClientFeathersData.getWeight()) + " Weight:" + ClientFeathersData.getWeight();
                    guiGraphics.drawString(font, texts, x + 10, y - 29, Config.Color_Artifacts, false);
                    int iy = y - 100;
                    x += 80;

                    // 背景
                    guiGraphics.blit(feathers, x, iy - 29 , 16, 0, 9, 9, 256, 256);
                    iy+=10;

                    // 普通
                    /* 背景*/guiGraphics.blit(feathers, x, iy - 29 , 16, 0, 9, 9, 256, 256);
                    guiGraphics.blit(feathers, x, iy - 29 , 34, 0, 9, 9, 256, 256);
                    iy+=10;

                    // 快速
                    /* 背景*/guiGraphics.blit(feathers, x, iy - 29 , 16, 0, 9, 9, 256, 256);
                    guiGraphics.blit(feathers, x, iy - 29 , 34, 0, 9, 9, 256, 256);
                    guiGraphics.blit(feathers, x, iy - 29 , 25, 18, 9, 9, 256, 256);
                    iy+=10;

                    // 溢出
                    /* 背景*/guiGraphics.blit(feathers, x, iy - 29 , 16, 0, 9, 9, 256, 256);
                    guiGraphics.blit(feathers, x, iy - 29 , 34, 0, 9, 9, 256, 256);
                    guiGraphics.blit(feathers, x, iy - 29 , 61, 0, 9, 9, 256, 256);
                    iy+=10;

                    // 禁止恢复
                    /* 背景*/guiGraphics.blit(feathers, x, iy - 29 , 16, 0, 9, 9, 256, 256);
                    guiGraphics.blit(feathers, x, iy - 29 , 34, 0, 9, 9, 256, 256);
                    guiGraphics.blit(feathers, x, iy - 29 , 61, 9, 9, 9, 256, 256);
                    iy+=10;

                    // 重量
                    /* 背景*/guiGraphics.blit(feathers, x, iy - 29 , 16, 0, 9, 9, 256, 256);
                    guiGraphics.blit(feathers, x, iy - 29 , 52, 0, 9, 9, 256, 256);
                }
            }
        }
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
        if (Config.Food_ExhaustionLevel_On) {
            float exhaustionLevel = player.getFoodData().getExhaustionLevel();
            // Check if the exhaustion level is not at its maximum before proceeding
            if (exhaustionLevel == 40.0) {
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
