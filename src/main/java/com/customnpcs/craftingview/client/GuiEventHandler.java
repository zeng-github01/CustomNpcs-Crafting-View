package com.customnpcs.craftingview.client;

import net.minecraft.client.gui.inventory.GuiContainer;

import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.ForgeSubscribe;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import noppes.npcs.client.gui.player.GuiNpcCarpentryBench;
import noppes.npcs.containers.ContainerCarpentryBench;

@SideOnly(Side.CLIENT)
public class GuiEventHandler {

    @ForgeSubscribe
    public void onGuiOpen(GuiOpenEvent event) {
        if (event.gui instanceof GuiNpcCarpentryBench
                && !(event.gui instanceof GuiCarpentryBenchWrapper)) {
            GuiContainer gc = (GuiContainer) event.gui;
            if (gc.inventorySlots instanceof ContainerCarpentryBench) {
                event.gui = new GuiCarpentryBenchWrapper(
                    (ContainerCarpentryBench) gc.inventorySlots);
            }
        }
    }
}
