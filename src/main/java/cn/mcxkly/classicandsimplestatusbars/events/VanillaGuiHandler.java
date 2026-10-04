package cn.mcxkly.classicandsimplestatusbars.events;

import cn.mcxkly.classicandsimplestatusbars.ClassicAndSimpleStatusBars;
import cn.mcxkly.classicandsimplestatusbars.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ClassicAndSimpleStatusBars.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class VanillaGuiHandler {


    private static final Minecraft mc = Minecraft.getInstance();

    private static final ResourceLocation vanillaHealthBar = new ResourceLocation("minecraft", "player_health");
    private static final ResourceLocation vanillaFoodBar = new ResourceLocation("minecraft", "food_level");
    private static final ResourceLocation vanillaAir = new ResourceLocation("minecraft", "air_level");
    private static final ResourceLocation vanillaMount_health = new ResourceLocation("minecraft", "mount_health");
    private static final ResourceLocation vanillAarmor_level = new ResourceLocation("minecraft", "armor_level");
    private static final ResourceLocation ThirstWasTaken = new ResourceLocation("thirst", "thirst_level");
    private static final ResourceLocation toughasnailsl = new ResourceLocation("toughasnails", "thirst_level");
    private static final ResourceLocation feathers = new ResourceLocation("feathers", "feathers");
    private static final ResourceLocation blueskies = new ResourceLocation("blue_skies", "nature_arc_health_bonus");
    // 传说生存: 血量改造 / 严寒饥饿 / 水分, 三层的显示都由本模组接管
    private static final ResourceLocation lsoHealthOverhaul = new ResourceLocation("legendarysurvivaloverhaul", "health_overhaul");
    private static final ResourceLocation lsoColdHunger = new ResourceLocation("legendarysurvivaloverhaul", "cold_hunger");
    private static final ResourceLocation lsoThirst = new ResourceLocation("legendarysurvivaloverhaul", "thirst");

    @SubscribeEvent
    public static void disableVanillaAarmor(RenderGuiOverlayEvent.Pre event) {
        if ( Config.All_On ) {
            final ForgeGui gui = ((ForgeGui) mc.gui);
            // 兼容性测试-尝试让其他模组兼容 如果其他模组依赖这个.
            //gui.leftHeight += 2;
            //gui.rightHeight += 1; // 只是觉得合适,但恰恰相反.
            if ( !event.isCanceled() && !mc.options.hideGui && gui.shouldDrawSurvivalElements() && mc.cameraEntity instanceof Player ) {
                if ( event.getOverlay().id().equals(vanillAarmor_level) ) {
                    event.setCanceled(true);
                }
                if ( event.getOverlay().id().equals(vanillaMount_health) && Config.Food_On ) {
                    // 仅在食物区被本模组接管时掐掉原版骑乘血量
                    event.setCanceled(true);
                }
                if ( event.getOverlay().id().equals(vanillaHealthBar) ) {
                    event.setCanceled(true);
                }
                if ( event.getOverlay().id().equals(vanillaFoodBar) ) {
                    event.setCanceled(true);
                }
                if ( event.getOverlay().id().equals(vanillaAir) ) {
                    event.setCanceled(true);
                }
                // 意志坚定
                if ( event.getOverlay().id().equals(toughasnailsl) ) {
                    event.setCanceled(true);
                }
                // 口渴
                if ( event.getOverlay().id().equals(ThirstWasTaken) ) {
                    event.setCanceled(true);
                }
                // 羽毛耐力
                if ( event.getOverlay().id().equals(feathers) ) {
                    event.setCanceled(true);
                }
                // 蔚蓝皓空
                if ( event.getOverlay().id().equals(blueskies) ) {
                    event.setCanceled(true);
                }
                // 传说生存 - 血量改造(破碎心/护盾)
                if ( event.getOverlay().id().equals(lsoHealthOverhaul) && Config.Health_On ) {
                    event.setCanceled(true);
                }
                // 传说生存 - 严寒饥饿条
                if ( event.getOverlay().id().equals(lsoColdHunger) && Config.Food_On ) {
                    event.setCanceled(true);
                }
                // 传说生存 - 水分
                if ( event.getOverlay().id().equals(lsoThirst) ) {
                    event.setCanceled(true);
                }

            }
        }

    }
}
