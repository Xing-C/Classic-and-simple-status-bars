package cn.mcxkly.classicandsimplestatusbars.other;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import java.util.HashMap;
import java.util.Map;

public class IconManager {
    private final Map<String, Icon> icons = new HashMap<>();
    private final TextureManager textureManager;

    public IconManager(TextureManager textureManager) {
        this.textureManager = textureManager;
    }

    public void registerIcon(String name, ResourceLocation resourceLocation, int textureWidth, int textureHeight, int u, int v, int width, int height) {
        if (!icons.containsKey(name)) { // 避免重复
            Icon icon = new Icon(resourceLocation, textureWidth, textureHeight, u, v, width, height);
            icons.put(name, icon);
        }
    }

    public void renderIcon(GuiGraphics guiGraphics, String name, int x, int y) {
        Icon icon = icons.get(name);
        if (icon != null) {
            guiGraphics.blit(icon.getResourceLocation(), x, y, icon.getU(), icon.getV(), icon.getWidth(), icon.getHeight(), icon.getTextureWidth(), icon.getTextureHeight());
        }
    }

    public boolean isIconRegistered(String name) {
        return icons.containsKey(name);
    }
}