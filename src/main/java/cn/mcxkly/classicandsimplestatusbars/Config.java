package cn.mcxkly.classicandsimplestatusbars;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.awt.*;

@Mod.EventBusSubscriber(modid = ClassicAndSimpleStatusBars.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.BooleanValue AllOn = BUILDER
            .push("All_On")
            .comment("在修改任何功能后都建议重启游戏以便正确生效。\n" +
                    "It is advisable to restart the game after any feature has been modified for it to take effect correctly。\n" +
                    "我不是很推荐关闭功能，因为某种情况下无法解决冲突，比如本模组的图片高度是5像素点，但原版图标是9像素点，如果你有兼容性的问题建议联系我。" +
                    "\n因需要保持显示内容正常显示，大部分联动渲染功能关闭后仍然被阻止此模组的渲染." +
                    "\nI don't really recommend turning off the function, because there is no way to resolve the conflict in some cases, for example, the image height of this module is 5 pixels, but the original icon is 9 pixels, if you have compatibility problems, please contact me." +
                    "\nDue to the need to keep the display content displayed normally, the rendering of they Mods is still blocked after most of the linkage rendering function is turned off." +
                    "\nIf you can help me, feel free to make a merge request." +
                    "\n你可以在网络上查找你需要的颜色代码，其中之一: https://www.ysdaima.com/hexbiao" +
                    "\nYou can check the color code you need on the web. (HEXADECIMAL)" +
                    "\n" +
                    "\n启用本模组。" +
                    "\nTurn All Features On Or Off | what-a-painful-realization." +
                    "\n默认值(Default)：true")
            .define("All-functional-status", true);
    /*************************************/
    private static final ForgeConfigSpec.BooleanValue EasyMode = BUILDER
            .push("EasyMode")
            // 或单独关闭左测/右测功能时生效， 暂未完成.
            .comment("如果设置true,在你关闭全部功能时生效，会在健康值左侧/饱食度右侧显示文本.如果设置为false则什么都不做。" +
                    "\nIf set to true, it will take effect when you turn off all functions or turn off the left/right test function separately, and the text will be displayed to the left of the health value/to the right of satiety. If set to false, nothing is done." +
                    "\n默认值(Default)：true")
            .define("EasyMode-status", true);
    /*************************************/
    private static final ForgeConfigSpec.ConfigValue<String> Interval_Textis = BUILDER
            .pop(2)
            .push("Interval_1")
            .comment("定义'当前值和最大值'间隔所使用的符号。" +
                    "\nDefines the symbol used for the 'Current' and 'Maximum' intervals." +
                    "\n默认值(Default)：Interval_String_1=\"/\"")
            .define("Interval_String_1", "/");
    private static final ForgeConfigSpec.ConfigValue<String> Color_Interval_String_1_ = BUILDER
            .comment("\n" + "符号'/'的颜色。" +
                    "\nThe color of the symbol '/'." +
                    "\n默认值(Default)：\"#5A5A5A\"")
            .define("Color_Interval_String_1", "#5A5A5A");
    private static final ForgeConfigSpec.ConfigValue<String> Interval__Text1 = BUILDER
            .pop(1)
            .push("Interval_2")
            .comment("定义'当前值'附加内容所使用的符号，如：+16伤害吸收。" +
                    "\n\"Defines the symbol used for 'Current Value Add-on'." +
                    "\n默认值(Default)：Interval_String_2=\"+\"")
            .define("Interval_String_2", "+");
    /* 感觉完全没必要...
private static final ForgeConfigSpec.ConfigValue<String> Prefix_Health1 = BUILDER
    .comment("\n" + "定义血量的前缀，如：‘血量:’20+5/40" +
            "\n\"A prefix that defines the amount of health, e.g. 'HP:'20+5/40" +
            "\n默认值(Default)：Prefix_String=\"\"")
    .define("Prefix_String", "");

private static final ForgeConfigSpec.ConfigValue<String> Prefix_Food1 = BUILDER
    .comment("\n" + "定义饱食度的前缀，如：‘蛋糕:’16+5" +
            "\n\"A prefix that defines the amount of Food, e.g. 'Food:'16+5" +
            "\n默认值(Default)：Prefix_String=\"\"")
    .define("Prefix_String", "");
*/
    private static final ForgeConfigSpec.ConfigValue<String> Color_Interval_String_2_ = BUILDER
            .comment("\n" + "符号'+'的颜色。" +
                    "\nThe color of the symbol '+'." +
                    "\n默认值(Default)：\"#F6E58D\"")
            .define("Color_Interval_String_2", "#F6E58D");
    private static final ForgeConfigSpec.ConfigValue<String> Interval__Text3 = BUILDER
            .pop(1)
            .push("Interval_3")
            .comment("定义'破碎心扣减的生命上限'所使用的符号，如：-4.0。" +
                    "\nDefines the symbol used for the 'max health lost to broken hearts', e.g. -4.0." +
                    "\n符号颜色沿用 Color_Health_Broken。" +
                    "\nIts color follows Color_Health_Broken." +
                    "\n默认值(Default)：Interval_String_3=\"-\"")
            .define("Interval_String_3", "-");
    /*************************************/
/*    private static final ForgeConfigSpec.BooleanValue supersaturation_On1 = BUILDER
            .pop(1)
            .push("linkage_function.SuperSaturation")
            .comment("如果设置false，超级饱食度模组所允许的溢出，将不会被额外计算，不建议关闭此选项。" +
                    "\nIf set to false, The overflow allowed by the SuperSaturation mod will not be additionally calculated, and it is not recommended to turn this option off." +
                    "\n默认值(Default)：true")
            .define("SuperSaturation-functional-status", true);
    private static final ForgeConfigSpec.BooleanValue feathers_On1 = BUILDER
            .pop(1)
            .push("linkage_function.SuperSaturation")
            .comment("如果设置false，该模组的耐力条“蓝色羽毛”将不会有更好的渲染也不会有原来的样子，不建议关闭此选项。" +
                    "\nIf set to false, the Feathers Mod stamina bar 'Blue Feather' will not render better and will not look like it was, and it is not recommended to turn this option off." +
                    "\n默认值(Default)：true")
            .define("Feathers-functional-status", true); */
    private static final ForgeConfigSpec.BooleanValue Bloodsucker_On1 = BUILDER
            .pop(1)
            .push("linkage_function.Vampires")
            .comment("如果设置false,当玩家被感染成吸血鬼时，关闭本模组的饱食度显示。如果为ture，将代替吸血鬼模组的血条进行显示。" +
                    "\nIf set to false, when the player is infected as a vampire, the saturation display of this mod will be turned off. If it is TURE, it will be displayed in place of the Vampires mod's health bar." +
                    "\n默认值(Default)：true")
            .define("Vampires-functional-status", true);
    private static final ForgeConfigSpec.ConfigValue<String> Color_Vampires_Blood1 = BUILDER
            .comment("\n" + "吸血鬼血量文本颜色。" +
                    "\nVampires Blood value text color." +
                    "\n默认值(Default)：\"#AC2019\"")
            .define("Color_Vampires_Blood", "#AC2019");
    private static final ForgeConfigSpec.ConfigValue<String> Color_Vampires_MaxBlood1 = BUILDER
            .comment("\n" + "吸血鬼最大血量文本颜色。" +
                    "\nVampires Max Blood value text color." +
                    "\n默认值(Default)：\"#600000\"")
            .define("Color_Vampires_MaxBlood", "#600000");
    private static final ForgeConfigSpec.BooleanValue Thirst_On1 = BUILDER
            .pop(1)
            .push("Thirst")
            .comment("如果设置为false，将关闭对于模组'口渴'/'意志坚定'、'稳态'的水分值渲染覆盖。" +
                    "\n" + "If set to false, the rendering override of the moisture value for the mod's 'thirst' and 'toughasnails' or 'homeostatic' will be turned off." +
                    "\n默认值(Default)：true")
            .define("Thirst-functional-status", true);
    private static final ForgeConfigSpec.ConfigValue<String> Color_Thirst1 = BUILDER
            .comment("\n" + "水分值文本颜色。" +
                    "\nThirst Value text color." +
                    "\n默认值(Default)：\"#0048AD\"")
            .define("Color_Thirst", "#0048AD");
    private static final ForgeConfigSpec.ConfigValue<String> Color_Thirst_Quenched1 = BUILDER
            .comment("\n" + "止渴水分值文本颜色。" +
                    "\nThirst Quenched value text color." +
                    "\n默认值(Default)：\"#82DAFD\"")
            .define("Color_Thirst_Quenched", "#82DAFD");
    private static final ForgeConfigSpec.BooleanValue Origins_On1 = BUILDER
            .pop(1)
            .push("Origins")
            .comment("如果设置为false,将关闭起源能力槽的显示。" +
                    "\nIf set to false, the display of Origins Power values will be turned off." +
                    "\n默认值(Default)：true")
            .define("Origins-functional-status", true);
    private static final ForgeConfigSpec.ConfigValue<String> Color_Origins1 = BUILDER
            .comment("\n" + "起源能力文本颜色。" +
                    "\nOrigins Power value text color." +
                    "\n默认值(Default)：\"#EBE994\"")
            .define("Color_Origins", "#EBE994");
    private static final ForgeConfigSpec.ConfigValue<String> Color_Origins_Symbol1 = BUILDER
            .comment("\n" + "符号'%'文本颜色。" +
                    "\nSymbol value text color." +
                    "\n默认值(Default)：\"#D2DAA4\"")
            .define("Color_Origins_Symbol", "#D2DAA4");
    private static final ForgeConfigSpec.BooleanValue Artifact_On1 = BUILDER
            .pop(1)
            .push("Artifacts")
            .comment("如果设置为false,将关闭奇异饰品模组-火烈鸟泳圈的显示。" +
                    "\nIf set to false, the display of the Artifacts Mod, Flamingo Swimming Ring, will be turned off." +
                    "\n默认值(Default)：true")
            .define("Artifacts-functional-status", true);
    private static final ForgeConfigSpec.ConfigValue<String> Color_Artifacts1 = BUILDER
            .comment("\n" + "火烈鸟饰品飞行时间文本颜色。" +
                    "\nFlamingo ornament flight time text color." +
                    "\n默认值(Default)：\"#BE3D6F\"")
            .define("Color_Artifacts", "#BE3D6F");
    private static final ForgeConfigSpec.ConfigValue<String> Color_Artifacts_Symbol1 = BUILDER
            .comment("\n" + "符号'%'文本颜色。" +
                    "\nSymbol value text color." +
                    "\n默认值(Default)：\"#DC6788\"")
            .define("Color_Artifacts_Symbol", "#DC6788");
    /*************************************/
    private static final ForgeConfigSpec.BooleanValue Food_On1 = BUILDER
            .pop(2)
            .push("FEATURES.RIGHT_SIDE_FEATURES")
            .comment("如果设置false,将关闭以经验等级右侧的所有功能修改。" +
                    "\nIf set to false, all feature modifications to the right of the experience level will be turned off." +
                    "\n默认值(Default)：true")
            .define("Food-functional-status", true);
    private static final ForgeConfigSpec.IntValue MaxFood_On1 = BUILDER
            .comment("\n" + "如果设置为'0',将在最大饱食度大于20时自动渲染、如果设置为'1',将始终显示最大饱食度、如果设置为'2',将始终隐藏最大饱食度。" +
                    "\nIf set to '0', auto the maximum satiety greater than 20 will be displayed, if set to '1', the maximum satiety will always be displayed, and if set to '2', the maximum satiety will always be hidden." +
                    "\n默认值(Default)：0")
            .defineInRange("MaxFood-functional-status", 0, 0, 2);
    //      .defineInRange("magicNumber", 42, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.BooleanValue Food_ExhaustionLevel_On1 = BUILDER
            .comment("\n" + "如果设置为false,将关闭食物消耗(疲劳度)的显示。" +
                    "\nIf set to false, the display of Food ExhaustionLevel values is turned off" +
                    "\n默认值(Default)：true")
            .define("Food_ExhaustionLevel-functional-status", true);

    private static final ForgeConfigSpec.BooleanValue Armor_Toughness_On1 = BUILDER
            .comment("\n" + "如果设置为false,将关闭盔甲韧性的显示。" +
                    "\nIf set to false, the display of armor toughness values is turned off" +
                    "\n默认值(Default)：true")
            .define("Armor_Toughness-functional-status", true);
    private static final ForgeConfigSpec.BooleanValue Air_On1 = BUILDER
            .comment("\n" + "如果设置为false,将关闭氧气值的显示。" +
                    "\nIf Set To false The Displayed Of Oxygen Value Is Turned Off | I can't breathe, but I seem to be alive." +
                    "\n默认值(Default)：true")
            .define("Air-functional-status", true);
    private static final ForgeConfigSpec.BooleanValue Mounts_On1 = BUILDER
            .comment("\n" + "如果设置为false,将关闭坐骑健康值的显示。" +
                    "\nIf set to false, the display of mount health values will be turned off." +
                    "\n默认值(Default)：true")
            .define("Mounts-functional-status", true);
    private static final ForgeConfigSpec.BooleanValue ParCool_On1 = BUILDER
            .comment("\n" + "如果设置为false,将关闭跑酷模组体力值的显示。" +
                    "\nIf set to false, the display of the ParCool stamina value will be turned off." +
                    "\n默认值(Default)：true")
            .define("ParCool-functional-status", true);
    /*************************************/
    private static final ForgeConfigSpec.BooleanValue Health_On1 = BUILDER
            .pop(1)
            .push("LEFT_SIDE_FEATURES")
            .comment("如果设置false,将关闭以经验等级左侧的所有功能修改。" +
                    "\nIf set to false, all feature modifications to the left of the experience level will be turned off." +
                    "\n默认值(Default)：true")
            .define("Health-functional-status", true);
    private static final ForgeConfigSpec.BooleanValue Armour_On1 = BUILDER
            .comment("\n" + "如果设置为false,将关闭盔甲值的显示。 " +
                    "\nIf set to false, the display of armor values is turned off" +
                    "\n默认值(Default)：true")
            .define("Armour-functional-status", true);
    private static final ForgeConfigSpec.BooleanValue Mekanism_On1 = BUILDER
            .comment("\n" + "如果设置为false,将关闭通用机械装备能量值的显示。" +
                    "\nIf set to false, the display of the Mekanism equipment energy value will be turned off." +
                    "\n默认值(Default)：true")
            .define("Mekanism-functional-status", true);
    /*************************************/
    private static final ForgeConfigSpec.ConfigValue<String> Color_Health1 = BUILDER
            .pop(2)
            .push("Color")
            .comment("血量值文本颜色。" +
                    "\nHealth value text color." +
                    "\n默认值(Default)：\"#B3473C\"")
            .define("Color_Health", "#B3473C");
    private static final ForgeConfigSpec.ConfigValue<String> Color_Health_Absorb1 = BUILDER
            .comment("\n" + "伤害吸收文本颜色。" +
                    "\nHealth Absorb value text color." +
                    "\n默认值(Default)：\"#BAB363\"")
            .define("Color_Health_Absorb", "#BAB363");
    private static final ForgeConfigSpec.ConfigValue<String> Color_Health_Tail1 = BUILDER
            .comment("\n" + "最大血量文本颜色。" +
                    "\nMax Health value text color." +
                    "\n默认值(Default)：\"#FF1313\"")
            .define("Color_Health_Tail", "#FF1313");
    private static final ForgeConfigSpec.ConfigValue<String> Color_Health_Broken1 = BUILDER
            .comment("\n" + "破碎心扣减的生命上限文本颜色。" +
                    "\nBroken heart max health penalty text color." +
                    "\n默认值(Default)：\"#8C4A4A\"")
            .define("Color_Health_Broken", "#8C4A4A");
    private static final ForgeConfigSpec.ConfigValue<String> Color_Mount1 = BUILDER
            .comment("\n" + "坐骑当前血量文本颜色。" +
                    "\nMount health value text color." +
                    "\n默认值(Default)：\"#B3473C\"")
            .define("Color_Mount", "#B3473C");
    private static final ForgeConfigSpec.ConfigValue<String> Color_Mount_Tail1 = BUILDER
            .comment("\n" + "坐骑最大血量文本颜色。" +
                    "\nMount Max Health value text color." +
                    "\n默认值(Default)：\"#FF1313\"")
            .define("Color_Mount_Tail", "#FF1313");
    private static final ForgeConfigSpec.ConfigValue<String> Color_Food1 = BUILDER
            .comment("\n" + "饱食度文本颜色。" +
                    "\nFood value text color." +
                    "\n默认值(Default)：\"#C6C677\"")
            .define("Color_Food", "#C6C677");
    private static final ForgeConfigSpec.ConfigValue<String> Color_Food_Saturation1 = BUILDER
            .comment("\n" + "饱和度文本颜色。" +
                    "\nSaturation value text color." +
                    "\n默认值(Default)：\"#C1805D\"")
            .define("Color_Food_Saturation", "#C1805D");
    private static final ForgeConfigSpec.ConfigValue<String> Color_Food_Tail1 = BUILDER
            .comment("\n" + "最大饱食度文本颜色。" +
                    "\nMax Food value text color." +
                    "\n默认值(Default)：\"#DFB18F\"")
            .define("Color_Food_Tail", "#DFB18F");
    private static final ForgeConfigSpec.ConfigValue<String> Color_Armor1 = BUILDER
            .comment("\n" + "盔甲文本颜色。" +
                    "\nArmor value text color." +
                    "\n默认值(Default)：\"#DDEBEB\"")
            .define("Color_Armor", "#DDEBEB");
    private static final ForgeConfigSpec.ConfigValue<String> Color_Armor_Toughness1 = BUILDER
            .comment("\n" + "盔甲韧性文本颜色。" +
                    "\nArmor Toughness value text color." +
                    "\n默认值(Default)：\"#BDECFC\"")
            .define("Color_Armor_Toughness", "#BDECFC");
    private static final ForgeConfigSpec.ConfigValue<String> Color_Mekanism1 = BUILDER
            .comment("\n" + "通用机械装备能量文本颜色。" +
                    "\nMekanism equipment energy value text color." +
                    "\n默认值(Default)：\"#DDEBEB\"")
            .define("Color_Mekanism", "#DDEBEB");
    private static final ForgeConfigSpec.ConfigValue<String> Color_Air1 = BUILDER
            .comment("\n" + "氧气文本颜色。" +
                    "\nAir value text color." +
                    "\n默认值(Default)：\"#0094FF\"")
            .define("Color_Air", "#0094FF");
    private static final ForgeConfigSpec.ConfigValue<String> Color_Air_Symbol1 = BUILDER
            .comment("\n" + "氧气符号'%'文本颜色。" +
                    "\nAir Symbol value text color." +
                    "\n默认值(Default)：\"#D1EBFF\"")
            .define("Color_Air_Symbol", "#D1EBFF");
    private static final ForgeConfigSpec.ConfigValue<String> Color_Stamina1 = BUILDER
            .comment("\n" + "跑酷模组体力值文本颜色。" +
                    "\nParCool stamina value text color." +
                    "\n默认值(Default)：\"#BE3D6F\"")
            .define("Color_Stamina", "#BE3D6F");

    private static final ForgeConfigSpec.ConfigValue<String> Color_Food_ExhaustionLevel1 = BUILDER
            .comment("\n" + "食物消耗进度（疲劳值）的文本颜色。" +
                    "\nFood ExhaustionLevel value text color." +
                    "\n默认值(Default)：\"#66C3CC\"")
            .define("Color_Food_ExhaustionLevel", "#66C3CC");
    static final ForgeConfigSpec SPEC = BUILDER.build();
    public static String Interval_lll, Interval_TTT, Interval_YYY, Prefix_Health, Prefix_Food;
    public static boolean /*feathers_On,supersaturation_On,*/ Food_ExhaustionLevel_On, Thirst_On, Artifacts_On, Origins_On, All_On, Bloodsucker_On, Food_On, Health_On, EasyMode_Text_On, Armour_On, Armor_Toughness_On, Air_On, Mounts_On, ParCool_On, Mekanism_On;
    public static int Color_Food_ExhaustionLevel, MaxFood_On, Color_Origins_Symbol, Color_Thirst_Quenched, Color_Thirst, Color_Artifacts, Color_Air, Color_Artifacts_Symbol, Color_Air_Symbol, Color_Vampires_Blood, Color_Vampires_MaxBlood, Color_Origins, Color_Health, Color_Health_Absorb, Color_Health_Tail, Color_Health_Broken, Color_Mount, Color_Mount_Tail, Color_Food, Color_Food_Saturation, Color_Food_Tail, Color_Armor, Color_Armor_Toughness, Color_Mekanism, Color_Stamina, Color_Interval_lll, Color_Interval_TTT;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        All_On = AllOn.get();
        Interval_lll = Interval_Textis.get();
        Interval_TTT = Interval__Text1.get();
        Interval_YYY = Interval__Text3.get();
            /* 感觉完全没必要...
            Prefix_Health = Prefix_Health1.get();
            Prefix_Food = Prefix_Food1.get();
             */
        MaxFood_On = MaxFood_On1.get();
        Bloodsucker_On = Bloodsucker_On1.get();
        Food_On = Food_On1.get();
        Health_On = Health_On1.get();
        EasyMode_Text_On = EasyMode.get();
        Armour_On = Armour_On1.get();
        Armor_Toughness_On = Armor_Toughness_On1.get();
        Air_On = Air_On1.get();
        Mounts_On = Mounts_On1.get();
        ParCool_On = ParCool_On1.get();
        Mekanism_On = Mekanism_On1.get();
        Origins_On = Origins_On1.get();
        Artifacts_On = Artifact_On1.get();
        Thirst_On = Thirst_On1.get();
        Food_ExhaustionLevel_On = Food_ExhaustionLevel_On1.get();
/*      supersaturation_On =  supersaturation_On1.get();
        feathers_On = feathers_On1.get();*/
        
        Color color_Food_ExhaustionLevel = Color.decode(Color_Food_ExhaustionLevel1.get().toLowerCase());
        Color_Food_ExhaustionLevel = color_Food_ExhaustionLevel.getRGB();
        Color_Artifacts = Integer.parseInt(Color_Artifacts1.get().substring(1), 16);
        Color_Artifacts_Symbol = Integer.parseInt(Color_Artifacts_Symbol1.get().substring(1), 16);
        Color_Air = Integer.parseInt(Color_Air1.get().substring(1), 16);
        Color_Air_Symbol = Integer.parseInt(Color_Air_Symbol1.get().substring(1), 16);
        Color_Vampires_Blood = Integer.parseInt(Color_Vampires_Blood1.get().substring(1), 16);
        Color_Vampires_MaxBlood = Integer.parseInt(Color_Vampires_MaxBlood1.get().substring(1), 16);
        Color_Thirst_Quenched = Integer.parseInt(Color_Thirst_Quenched1.get().substring(1), 16);
        Color_Thirst = Integer.parseInt(Color_Thirst1.get().substring(1), 16);
        Color_Health = Integer.parseInt(Color_Health1.get().substring(1), 16);
        Color_Health_Absorb = Integer.parseInt(Color_Health_Absorb1.get().substring(1), 16);
        Color_Health_Tail = Integer.parseInt(Color_Health_Tail1.get().substring(1), 16);
        Color_Health_Broken = Integer.parseInt(Color_Health_Broken1.get().substring(1), 16);
        Color_Mount = Integer.parseInt(Color_Mount1.get().substring(1), 16);
        Color_Mount_Tail = Integer.parseInt(Color_Mount_Tail1.get().substring(1), 16);
        Color_Food = Integer.parseInt(Color_Food1.get().substring(1), 16);
        Color_Food_Saturation = Integer.parseInt(Color_Food_Saturation1.get().substring(1), 16);
        Color_Food_Tail = Integer.parseInt(Color_Food_Tail1.get().substring(1), 16);
        Color_Armor = Integer.parseInt(Color_Armor1.get().substring(1), 16);
        Color_Armor_Toughness = Integer.parseInt(Color_Armor_Toughness1.get().substring(1), 16);
        Color_Mekanism = Integer.parseInt(Color_Mekanism1.get().substring(1), 16);
        Color_Stamina = Integer.parseInt(Color_Stamina1.get().substring(1), 16);
        Color_Interval_lll = Integer.parseInt(Color_Interval_String_1_.get().substring(1), 16);
        Color_Interval_TTT = Integer.parseInt(Color_Interval_String_2_.get().substring(1), 16);
        Color_Origins = Integer.parseInt(Color_Origins1.get().substring(1), 16);
        Color_Origins_Symbol = Integer.parseInt(Color_Origins_Symbol1.get().substring(1), 16);
    }
}
