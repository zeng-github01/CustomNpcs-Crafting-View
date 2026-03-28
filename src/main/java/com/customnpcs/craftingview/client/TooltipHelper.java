package com.customnpcs.craftingview.client;

import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TooltipHelper extends GuiScreen {

    private TooltipHelper(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public static void drawHoveringText(GuiScreen gui, List lines, int x, int y, FontRenderer fr) {
        new TooltipHelper(gui.width, gui.height).drawHoveringText(lines, x, y, fr);
    }
}
