package cn.mcxkly.classicandsimplestatusbars.events;

import cn.mcxkly.classicandsimplestatusbars.ClassicAndSimpleStatusBars;
import cn.mcxkly.classicandsimplestatusbars.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;

import java.util.Set;

/**
 * 屏蔽原版与联动模组自带的血条/饱食/氧气/护甲/骑乘/水分/能量/体力显示层。
 */
@EventBusSubscriber(modid = ClassicAndSimpleStatusBars.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class VanillaGuiHandler {

    private static final Minecraft mc = Minecraft.getInstance();

    // 原版层，命名空间固定为 minecraft
    private static final Set<String> VANILLA_LAYERS = Set.of(
            "player_health",
            "food_level",
            "air_level",
            "armor_level"
    );

    private static final String VEHICLE_HEALTH = "vehicle_health";

    // 联动模组层
    private static final ResourceLocation THIRST_LAYER = id("thirst", "thirst_level");
    private static final ResourceLocation TOUGHASNAILS_LAYER = id("toughasnails", "thirst_level");
    private static final ResourceLocation OVERLOADED_ARMOR_LAYER = id("overloadedarmorbar", "overloadedarmorbar");
    private static final ResourceLocation MEKANISM_ENERGY_LAYER = id("mekanism", "energy_level");
    private static final ResourceLocation PARCOOL_STAMINA_LAYER = id("parcool", "hud.stamina");
    private static final ResourceLocation ARTIFACTS_FLAMINGO_LAYER = id("artifacts", "helium_flamingo_charge");
    // 传说生存: 水分条与本模组的饥饿条同区间, 直接掐掉
    private static final ResourceLocation LSO_THIRST_LAYER = id("legendarysurvivaloverhaul", "thirst");

    private static ResourceLocation id(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }

    @SubscribeEvent
    public static void hideLayers(RenderGuiLayerEvent.Pre event) {
        if ( !Config.All_On ) return;
        if ( mc.options.hideGui ) return;
        if ( !(mc.getCameraEntity() instanceof Player) ) return;

        ResourceLocation name = event.getName();
        if ( ResourceLocation.DEFAULT_NAMESPACE.equals(name.getNamespace()) ) {
            if ( VANILLA_LAYERS.contains(name.getPath()) ) {
                event.setCanceled(true);
            } else if ( VEHICLE_HEALTH.equals(name.getPath()) && Config.Food_On ) {
                // 本模组接管食物区时才掐掉原版骑乘血量; 食物总开关关闭则整块交还原版
                event.setCanceled(true);
            }
            return;
        }
        if ( name.equals(THIRST_LAYER)
                || name.equals(TOUGHASNAILS_LAYER)
                || name.equals(LSO_THIRST_LAYER)
                || name.equals(OVERLOADED_ARMOR_LAYER)
                || name.equals(MEKANISM_ENERGY_LAYER)
                || name.equals(PARCOOL_STAMINA_LAYER) ) {
            event.setCanceled(true);
            return;
        }
        if ( name.equals(ARTIFACTS_FLAMINGO_LAYER) && Config.Artifacts_On ) {
            event.setCanceled(true);
        }
    }
}
