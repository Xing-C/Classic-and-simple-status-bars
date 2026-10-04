package cn.mcxkly.classicandsimplestatusbars.other;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import java.util.Locale;

public class helper {
    public static String KeepOneDecimal(float d) {
        if ( d > 0 ) {
            //d = (float) (Math.ceil(d * 10) / 10); // Rounding is a vanilla approach, and if you pursue perfection, it may be foolish to do so
            String s = String.format(Locale.ROOT, "%.1f", d);
            return s.replace(".0", "");
        } else {
            return "0";
        }
    }
    public static void blit(GuiGraphics guiGraphics, ResourceLocation Icon, int PicsX, int PicsY, int IconStartingPointX, int IconStartingPointY,int IconWide,int IconHigh, int PicsWidth, int PicsHigh,int w1) {
        guiGraphics.blit(Icon,
                PicsX, PicsY,
                IconStartingPointX, IconStartingPointY,
                IconWide, IconHigh,
                PicsWidth, PicsHigh,1);
    }
    public static void blit(GuiGraphics guiGraphics, ResourceLocation Icon, int PicsX, int PicsY, int IconStartingPointX, int IconStartingPointY,int IconWide,int IconHigh, int PicsWidth, int PicsHigh,int w1,int w2) {
        guiGraphics.blit(Icon,
                PicsX, PicsY,
                IconStartingPointX, IconStartingPointY,
                IconWide, IconHigh,
                PicsWidth, PicsHigh,1,1);
    }

    private final ResourceLocation McIcon = new ResourceLocation("minecraft", "textures/gui/icons.png");

    // 渲染图标
    public static void blit(GuiGraphics guiGraphics, ResourceLocation Icon, int PicsX, int PicsY, int IconStartingPointX, int IconStartingPointY,int IconWide,int IconHigh, int PicsWidth, int PicsHigh) {
        guiGraphics.blit(Icon,
                PicsX, PicsY,
                IconStartingPointX, IconStartingPointY,
                IconWide, IconHigh,
                PicsWidth, PicsHigh);
    }
    // 渲染文本
    public static void drawString(GuiGraphics guiGraphics, String string, int leftX, int topY, int color, boolean shadow) {
        guiGraphics.drawString(Minecraft.getInstance().font, string, leftX, topY, color, shadow);
    }
}