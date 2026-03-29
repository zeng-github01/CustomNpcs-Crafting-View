package com.customnpcs.craftingview.client;

import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TooltipHelper extends GuiContainer {

    private TooltipHelper(int width, int height) {
        super(null);
        this.width = width;
        this.height = height;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseY) {}

    public static void drawHoveringText(GuiScreen gui, List lines, int x, int y, FontRenderer fr) {
        new TooltipHelper(gui.width, gui.height).drawHoveringText(lines, x, y, fr);
    }
}
