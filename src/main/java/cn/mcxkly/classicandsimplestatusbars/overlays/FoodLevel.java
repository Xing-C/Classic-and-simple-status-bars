package cn.mcxkly.classicandsimplestatusbars.overlays;

import cn.mcxkly.classicandsimplestatusbars.ClassicAndSimpleStatusBars;
import cn.mcxkly.classicandsimplestatusbars.other.helper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;


public class FoodLevel implements IGuiOverlay {
    private static final ResourceLocation fullHealthBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/foodeeg.png");
    private static final ResourceLocation emptyHealthBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/empty.png");
    private static final ResourceLocation saturationBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/saturation.png");
    private ResourceLocation currentBarLocation = fullHealthBarLocation;
    private static final ResourceLocation emmmmnBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/debuff-hunger.png");
    private static final ResourceLocation intermediateHealthBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/intermediate.png");
    private static final ResourceLocation guiIconsLocation = new ResourceLocation("minecraft", "textures/gui/icons.png");
    private float intermediateFood = 0;
    public static boolean StopConflictRendering = true; // 支持口渴

    public static void StopConflictRenderingIDEA(boolean is) {
        StopConflictRendering = is;
    }

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int width, int height) {
        if (gui.shouldDrawSurvivalElements()) {
            Font font = gui.getFont();

            Player player = (Player) Minecraft.getInstance().cameraEntity;
            if (player == null) return;
            int x = width / 2 + 11;
            int y = height - 39;
            y += 4;
            //y -= 70; // test
            updateBarTextures(player);
            renderFoodBar(guiGraphics, partialTick, x, y, player);
            renderFoodValue(font, guiGraphics, x, y, player);
        }
    }

    public void updateBarTextures(Player player) {
        if (player.hasEffect(MobEffects.HUNGER)) {
            currentBarLocation = emmmmnBarLocation;
        } else {
            currentBarLocation = fullHealthBarLocation;
        }
    }

    private void renderFoodValue(Font font, GuiGraphics guiGraphics, int x, int y, Player player) {
        // getSaturationLevel饱食条 | getFoodLevel饥饿度 |  getLastFoodLevel饥饿最大值 | player.getFoodData().getExhaustionLevel(); 消耗度
        y += 1;
        guiGraphics.blit(guiIconsLocation,
                x, y - 10,
                16, 27,
                9, 9,
                256, 256); // 鸡腿图标-背景
        guiGraphics.blit(guiIconsLocation,
                x, y - 10,
                52, 27,
                9, 9,
                256, 256); // 鸡腿图标
        String text = helper.KeepOneDecimal(player.getFoodData().getFoodLevel());
        guiGraphics.drawString(font, text, x + 10, y - 9, 0xF4A460, false);
        if (player.getFoodData().getSaturationLevel() > 0) {
            //第二部分
            int xx = x + 10;
            xx = xx + font.width(text);
            text = "+" + helper.KeepOneDecimal(player.getFoodData().getSaturationLevel());
            guiGraphics.drawString(font, text, xx, y - 9, 0xEEEE00, false);
        }


        if (player.getAirSupply() < 300) { // max=300
            int siz = player.getAirSupply() / 3;
            siz = Math.max(siz, 0); //防止负数
            text = String.valueOf(siz);
            int Y2 = y;
            if (!StopConflictRendering) Y2 -= 10; // 如果口渴存在，在渲染时高度 + 10
            guiGraphics.drawString(font, "%", x + 70 - font.width("%"), Y2 - 9, 0x1E90FF, false);

            guiGraphics.drawString(font, text, x + 70 - font.width("99%"), Y2 - 9, 0x1E90FF, false);
            guiGraphics.blit(guiIconsLocation,
                    x + 70, Y2 - 10,
                    16, 18,
                    9, 9,
                    256, 256); // 气泡图标
        }
        Entity tsssmp = player.getVehicle();
        if (tsssmp != null) {
            if (tsssmp.getType() == EntityType.SKELETON_HORSE ||
                    tsssmp.getType() == EntityType.PIG ||
                    tsssmp.getType() == EntityType.HORSE ||
                    tsssmp.getType() == EntityType.CAMEL ||
                    tsssmp.getType() == EntityType.MULE ||
                    tsssmp.getType() == EntityType.STRIDER ||
                    tsssmp.getType() == EntityType.TRADER_LLAMA
            ) {
                LivingEntity FsMount = (LivingEntity) tsssmp;

                float MountHealthsMax = FsMount.getMaxHealth();
                float MountHealths = Math.min(FsMount.getHealth(), MountHealthsMax);
                if (MountHealths > 0) {
                    guiGraphics.blit(guiIconsLocation,
                            x, y - 19,
                            88, 9,
                            9, 9,
                            256, 256); // 骑乘血量
                    guiGraphics.drawString(font, helper.KeepOneDecimal(MountHealths) + "/" + helper.KeepOneDecimal(MountHealthsMax), x + 10, y - 19, 0xEE0000, false);
                }
            }
        } else {
            float ARMORTOUGHNESS = (float) player.getAttribute(Attributes.ARMOR_TOUGHNESS).getValue();
            if (ARMORTOUGHNESS > 0) {
                guiGraphics.blit(guiIconsLocation,
                        x, y - 19,
                        43, 9,
                        9, 9,
                        256, 256); // 护甲图标
                guiGraphics.blit(guiIconsLocation,
                        x, y - 19,
                        43, 18,
                        9, 9,
                        256, 256); // 韧性图标
                guiGraphics.drawString(font, helper.KeepOneDecimal(ARMORTOUGHNESS), x + 10, y - 19, 0x87CEEB, false);
            }
        }
    }

    private void renderFoodBar(GuiGraphics guiGraphics, float partialTick, int x, int y, Player player) {
        //float maxFood = 20; // 不，不能这样用。
        float maxFood = player.getFoodData().getLastFoodLevel();

        float Food = player.getFoodData().getFoodLevel();
        float saturationProportion = player.getFoodData().getSaturationLevel() / maxFood;

        // Calculate bar proportions
        float FoodProportion;
        if (Food > maxFood) maxFood = Food;
        float intermediateProportion;
        if (Food < intermediateFood) {
            //FoodProportion = Food / maxFood;
            intermediateProportion = (intermediateFood - Food) / maxFood;
        } else {
            //FoodProportion = intermediateFood / maxFood;
            intermediateProportion = 0;
        }
        FoodProportion = Food / maxFood;
        //if (FoodProportion > 1) FoodProportion = 1F;
        //if (FoodProportion + intermediateProportion > 1) intermediateProportion = 1 - FoodProportion;
        int FoodWidth = (int) Math.ceil(80 * FoodProportion);
        int saturationWidth = (int) Math.ceil(80 * saturationProportion);
        int intermediateWidth = (int) Math.ceil(80 * intermediateProportion);

        // Display empty part
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

        // Display intermediate part
        guiGraphics.blit(intermediateHealthBarLocation,
                x + 80 - FoodWidth - intermediateWidth, y,
                80 - FoodWidth - intermediateWidth, 0,
                intermediateWidth, 5,
                80, 5);

//        // Display intermediate part
//        guiGraphics.blit(intermediateHealthBarLocation,
//                x + FoodWidth, y,
//                FoodWidth, 0,
//                intermediateWidth, 5,
//                80, 5);
//        // Display empty part
//        guiGraphics.blit(emptyHealthBarLocation,
//                x + FoodWidth + intermediateWidth, y,
//                FoodWidth + intermediateWidth, 0,
//                80 - FoodWidth - intermediateWidth, 5,
//                80, 5);
        int InsWidth = 0;
        float InsFood = 0;
        if (player.getFoodData().getSaturationLevel() > 0) {
            InsWidth = (int) saturationProportion;
            InsFood = player.getFoodData().getSaturationLevel();
        } else {
            InsWidth = FoodWidth;
            InsFood = Food;
        }
        // Update intermediate health
        this.intermediateFood += (InsFood - intermediateFood) * partialTick * 0.08;
        //guiGraphics.drawString(font, intermediateFood+" - " + intermediateWidth, x, y - 69, 0x1E90FF, false);
        if (Math.abs(InsFood - intermediateFood) <= 0.25) {
            this.intermediateFood = InsFood;
        }
    }
}
