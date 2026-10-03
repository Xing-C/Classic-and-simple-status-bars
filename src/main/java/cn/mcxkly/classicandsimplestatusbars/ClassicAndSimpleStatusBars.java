package cn.mcxkly.classicandsimplestatusbars;

import cn.mcxkly.classicandsimplestatusbars.overlays.AppleSkinEventHandler;
import cn.mcxkly.classicandsimplestatusbars.overlays.FoodLevel;
import cn.mcxkly.classicandsimplestatusbars.overlays.ThirstWasTakenUse;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(ClassicAndSimpleStatusBars.MOD_ID)
public class ClassicAndSimpleStatusBars {

    public static final String MOD_ID = "classicandsimplestatusbars";
    public static final Logger LOGGER = LogUtils.getLogger();
    private static final String APPLESKIN_ID = "appleskin";
    private static final String THIRST_ID = "thirst";
    private static final String TOUGHASNAILS_ID = "toughasnails";
    private static final String HOMEOSTATIC_ID = "homeostatic";
    private static final String ARTIFACTS_ID = "artifacts";
    private static final String VAMPIRISM_ID = "vampirism";
    private static final String PARCOOL_ID = "parcool";
    private static final String MEKANISM_ID = "mekanism";
    private static final String LEGENDARYSURVIVALOVERHAUL_ID = "legendarysurvivaloverhaul";

    public static boolean vampirism = false;
    public static boolean parcool = false;
    public static boolean mekanism = false;
    public static boolean legendarysurvivaloverhaul = false;

    public ClassicAndSimpleStatusBars(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        // 注册客户端配置文件
        modContainer.registerConfig(ModConfig.Type.CLIENT, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        if ( !Config.All_On ) return;
        LOGGER.info("CSSB: " + "Now 'CSSB' Has Loaded.");
        if ( Config.Food_On && ModList.get().isLoaded(APPLESKIN_ID) ) {
            LOGGER.info("CSSB: " + "Enable the Modifications to the appleskin");
            NeoForge.EVENT_BUS.register(new AppleSkinEventHandler());
        }
        if ( Config.Thirst_On && ModList.get().isLoaded(THIRST_ID) ) {
            LOGGER.info("CSSB: " + "Enable For thirst the Thirst value");
            ThirstWasTakenUse.StopConflictRenderingIDEA(false);
            FoodLevel.StopConflictRenderingIDEA(false);
        }
        if ( Config.Thirst_On && ModList.get().isLoaded(TOUGHASNAILS_ID) ) {
            LOGGER.info("CSSB: " + "Enable For toughasnails, the Thirst value");
            ThirstWasTakenUse.toughasnailsIDEA(false);
            FoodLevel.StopConflictRenderingIDEA(false);
        }
        if ( Config.Thirst_On && ModList.get().isLoaded(HOMEOSTATIC_ID) ) {
            LOGGER.info("CSSB: " + "Enable For homeostatic, the Thirst value");
            ThirstWasTakenUse.HomeostaticIDEA(false);
            FoodLevel.StopConflictRenderingIDEA(false);
        }
        if ( Config.Artifacts_On && ModList.get().isLoaded(ARTIFACTS_ID) ) {
            LOGGER.info("CSSB: " + "Enable the flamingo swimming ring");
            FoodLevel.ArtifactsIDEA(true);
        }
        if ( Config.Bloodsucker_On && ModList.get().isLoaded(VAMPIRISM_ID) ) {
            LOGGER.info("CSSB: " + "Enable the vampirism blood value");
            vampirism = true;
        }
        if ( Config.ParCool_On && ModList.get().isLoaded(PARCOOL_ID) ) {
            LOGGER.info("CSSB: " + "Enable the ParCool Stamina Value");
            parcool = true;
        }
        if ( Config.Mekanism_On && ModList.get().isLoaded(MEKANISM_ID) ) {
            LOGGER.info("CSSB: " + "Enable the Mekanism Armor Energy Value");
            mekanism = true;
        }
        if ( ModList.get().isLoaded(LEGENDARYSURVIVALOVERHAUL_ID) ) {
            LOGGER.info("CSSB: " + "Enable the Legendary Survival Overhaul health value");
            legendarysurvivaloverhaul = true;
            if ( Config.Thirst_On ) {
                LOGGER.info("CSSB: " + "Enable For legendarysurvivaloverhaul, the Thirst value");
                ThirstWasTakenUse.LsoThirstIDEA(false);
                // 水分占掉饥饿条右侧那一行, 氧气/泳圈/耐力要整体抬高一行避开
                FoodLevel.StopConflictRenderingIDEA(false);
            }
        }
    }
}
