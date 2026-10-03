package cn.mcxkly.classicandsimplestatusbars.events;

import cn.mcxkly.classicandsimplestatusbars.ClassicAndSimpleStatusBars;
import cn.mcxkly.classicandsimplestatusbars.overlays.Overlays;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

@EventBusSubscriber(modid = ClassicAndSimpleStatusBars.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class OverlayRegisteringHandler {

    private static final ResourceLocation CHAT_LAYER = ResourceLocation.withDefaultNamespace("chat");

    @SubscribeEvent
    public static void registerHealthBar(RegisterGuiLayersEvent event) {
        event.registerBelow(CHAT_LAYER, Overlays.HEALTH_BAR_ID, Overlays.HEALTH_BAR);
    }

    @SubscribeEvent
    public static void registerFood(RegisterGuiLayersEvent event) {
        event.registerBelow(CHAT_LAYER, Overlays.FOOD_LEVEL_ID, Overlays.FOOD_LEVEL);
    }

    @SubscribeEvent
    public static void registerThirst(RegisterGuiLayersEvent event) {
        event.registerBelow(CHAT_LAYER, Overlays.THIRST_LEVEL_ID, Overlays.THIRST_LEVEL);
    }
}
