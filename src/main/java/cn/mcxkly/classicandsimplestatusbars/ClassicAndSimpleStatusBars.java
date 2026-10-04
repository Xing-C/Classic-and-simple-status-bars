package cn.mcxkly.classicandsimplestatusbars;

import cn.mcxkly.classicandsimplestatusbars.overlays.AppleSkinEventHandler;
import cn.mcxkly.classicandsimplestatusbars.overlays.FoodLevel;
import cn.mcxkly.classicandsimplestatusbars.overlays.ThirstWasTakenUse;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ClassicAndSimpleStatusBars.MOD_ID)
public class ClassicAndSimpleStatusBars {

    public static final String MOD_ID = "classicandsimplestatusbars";
    public static final Logger LOGGER = LogUtils.getLogger();

    // 联动模组的 modid，直接写字符串，避免编译期耦合对方的类常量
    private static final String APPLESKIN_ID = "appleskin";
    private static final String THIRST_ID = "thirst";
    private static final String TOUGHASNAILS_ID = "toughasnails";
    private static final String HOMEOSTATIC_ID = "homeostatic";
    private static final String ARTIFACTS_ID = "artifacts";
    private static final String VAMPIRISM_ID = "vampirism";
    private static final String ORIGINS_ID = "origins";
    private static final String SUPERSATURATION_ID = "supersaturation";
    private static final String PARCOOL_ID = "parcool";
    private static final String FEATHERS_ID = "feathers";
    private static final String MEKANISM_ID = "mekanism";
    private static final String BLUE_SKIES_ID = "blue_skies";
    private static final String LEGENDARYSURVIVALOVERHAUL_ID = "legendarysurvivaloverhaul";

    public static boolean vampirism = false;
    public static boolean origins = false;
    public static boolean supersaturation = false;
    public static boolean parcool = false;
    public static boolean feathers = false;
    public static boolean mekanism = false;
    public static boolean blueSkies = false;
    public static boolean legendarysurvivaloverhaul = false;

    public ClassicAndSimpleStatusBars() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this :: commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        // 注册客户端配置文件
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        if ( Config.All_On ) {
            LOGGER.info("CSSB: " + "Now 'CSSB' Has Loaded.");
            if ( Config.Food_On && ModList.get().isLoaded(APPLESKIN_ID) ) {
                LOGGER.info("CSSB: " + "Enable the Modifications to the appleskin");
                MinecraftForge.EVENT_BUS.register(new AppleSkinEventHandler());
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
            if ( Config.Origins_On && ModList.get().isLoaded(ORIGINS_ID) ) {
                LOGGER.info("CSSB: " + "Enable the origins power value");
                origins = true;
            }
            if ( ModList.get().isLoaded(SUPERSATURATION_ID) ) {
                LOGGER.info("CSSB: " + "Enable the SuperSaturation Added Value");
                supersaturation = true;
            }
            if ( Config.ParCool_On && ModList.get().isLoaded(PARCOOL_ID) ) {
                LOGGER.info("CSSB: " + "Enable the ParCool Stamina Value");
                parcool = true;
            }
            if ( ModList.get().isLoaded(FEATHERS_ID) ) {
                LOGGER.info("CSSB: " + "Enable the Feathers StaminaFeather Value");
                feathers = true;
            }
            if ( Config.Mekanism_On && ModList.get().isLoaded(MEKANISM_ID) ) {
                LOGGER.info("CSSB: " + "Enable the Mekanism Armor Energy Value");
                mekanism = true;
            }
            if ( ModList.get().isLoaded(BLUE_SKIES_ID) ) {
                LOGGER.info("CSSB: " + "Enable the BlueSkies ExtraHealth Value");
                blueSkies = true;
            }
            if ( ModList.get().isLoaded(LEGENDARYSURVIVALOVERHAUL_ID) ) {
                LOGGER.info("CSSB: " + "Enable the Legendary Survival Overhaul health value");
                legendarysurvivaloverhaul = true;
                if ( Config.Thirst_On ) {
                    LOGGER.info("CSSB: " + "Enable For legendarysurvivaloverhaul, the Thirst value");
                    ThirstWasTakenUse.LsoThirstIDEA(false);
                    // 水分占掉饥饿条上方那一行, 氧气/泳圈/耐力要整体抬高一行避开
                    FoodLevel.StopConflictRenderingIDEA(false);
                }
            }
        }
    }
}
