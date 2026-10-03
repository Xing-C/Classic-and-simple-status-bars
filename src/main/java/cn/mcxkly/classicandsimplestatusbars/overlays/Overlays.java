package cn.mcxkly.classicandsimplestatusbars.overlays;

import cn.mcxkly.classicandsimplestatusbars.ClassicAndSimpleStatusBars;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;

public class Overlays {
    public static final ResourceLocation HEALTH_BAR_ID = ResourceLocation.fromNamespaceAndPath(ClassicAndSimpleStatusBars.MOD_ID, "health_bar");
    public static final ResourceLocation FOOD_LEVEL_ID = ResourceLocation.fromNamespaceAndPath(ClassicAndSimpleStatusBars.MOD_ID, "food_level");
    public static final ResourceLocation THIRST_LEVEL_ID = ResourceLocation.fromNamespaceAndPath(ClassicAndSimpleStatusBars.MOD_ID, "thirst_level");

    public static final LayeredDraw.Layer HEALTH_BAR = new HealthBar();
    public static final LayeredDraw.Layer FOOD_LEVEL = new FoodLevel();
    public static final LayeredDraw.Layer THIRST_LEVEL = new ThirstWasTakenUse();
}
