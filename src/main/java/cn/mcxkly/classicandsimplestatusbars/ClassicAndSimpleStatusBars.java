package cn.mcxkly.classicandsimplestatusbars;

import arcaios26.supersaturation.SuperSaturation;
import artifacts.Artifacts;
import cn.mcxkly.classicandsimplestatusbars.overlays.AppleSkinEventHandler;
import cn.mcxkly.classicandsimplestatusbars.overlays.FoodLevel;
import cn.mcxkly.classicandsimplestatusbars.overlays.ThirstWasTakenUse;
import com.alrex.parcool.ParCool;
import com.elenai.feathers.Feathers;
import com.legacy.blue_skies.BlueSkies;
import com.mojang.logging.LogUtils;
import de.teamlapen.vampirism.REFERENCE;
import dev.ghen.thirst.Thirst;
import homeostatic.Homeostatic;
import io.github.apace100.origins.Origins;
import mekanism.common.Mekanism;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import squeek.appleskin.ModInfo;
import toughasnails.core.ToughAsNails;

@Mod(ClassicAndSimpleStatusBars.MOD_ID)
public class ClassicAndSimpleStatusBars {

    public static final String MOD_ID = "classicandsimplestatusbars";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static boolean vampirism = false;
    public static boolean origins = false;
    public static boolean supersaturation = false;
    public static boolean parcool = false;
    public static boolean feathers = false;
    public static boolean mekanism = false;
    public static boolean blueSkies = false;
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
            if ( Config.Food_On && ModList.get().isLoaded(ModInfo.MODID) ) {
                LOGGER.info("CSSB: " + "Enable the Modifications to the appleskin");
                MinecraftForge.EVENT_BUS.register(new AppleSkinEventHandler());
            }
            if ( Config.Thirst_On && ModList.get().isLoaded(Thirst.ID) ) {
                LOGGER.info("CSSB: " + "Enable For thirst the Thirst value");
                ThirstWasTakenUse.StopConflictRenderingIDEA(false);
                FoodLevel.StopConflictRenderingIDEA(false);
            }
            if ( Config.Thirst_On && ModList.get().isLoaded(ToughAsNails.MOD_ID) ) {
                LOGGER.info("CSSB: " + "Enable For toughasnails, the Thirst value");
                ThirstWasTakenUse.toughasnailsIDEA(false);
                FoodLevel.StopConflictRenderingIDEA(false);
            }
            if ( Config.Thirst_On && ModList.get().isLoaded(Homeostatic.MODID) ) {
                LOGGER.info("CSSB: " + "Enable For homeostatic, the Thirst value");
                ThirstWasTakenUse.HomeostaticIDEA(false);
                FoodLevel.StopConflictRenderingIDEA(false);
            }
            if ( Config.Artifacts_On && ModList.get().isLoaded(Artifacts.MOD_ID) ) {
                LOGGER.info("CSSB: " + "Enable the flamingo swimming ring");
                FoodLevel.ArtifactsIDEA(true);
            }
            if ( Config.Bloodsucker_On && ModList.get().isLoaded(REFERENCE.MODID) ) {
                LOGGER.info("CSSB: " + "Enable the vampirism blood value");
                vampirism = true;
            }
            if ( Config.Origins_On && ModList.get().isLoaded(Origins.MODID) ) {
                LOGGER.info("CSSB: " + "Enable the origins power value");
                origins = true;
            }
            if ( ModList.get().isLoaded(SuperSaturation.MODID) ) {
                LOGGER.info("CSSB: " + "Enable the SuperSaturation Added Value");
                supersaturation = true;
            }
            if ( ModList.get().isLoaded(ParCool.MOD_ID) ) {
                LOGGER.info("CSSB: " + "Enable the ParCool Stamina Value");
                parcool = true;
            }
            if ( ModList.get().isLoaded(Feathers.MODID) ) {
                LOGGER.info("CSSB: " + "Enable the Feathers StaminaFeather Value");
                feathers = true;
            }
            if ( ModList.get().isLoaded(Mekanism.MODID) ) {
                LOGGER.info("CSSB: " + "Enable the Mekanism Armor Energy Value");
                mekanism = true;
            }
            if ( ModList.get().isLoaded(BlueSkies.MODID) ) {
                LOGGER.info("CSSB: " + "Enable the BlueSkies ExtraHealth Value");
                blueSkies = true;
            }
        }
    }
}
