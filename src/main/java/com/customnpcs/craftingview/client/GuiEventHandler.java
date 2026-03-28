package com.customnpcs.craftingview.client;

import net.minecraft.client.Minecraft;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.event.GuiOpenEvent;
import noppes.npcs.client.gui.player.GuiNpcCarpentryBench;
import noppes.npcs.containers.ContainerCarpentryBench;

@SideOnly(Side.CLIENT)
public class GuiEventHandler {

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if (event.gui instanceof GuiNpcCarpentryBench
                && !(event.gui instanceof GuiCarpentryBenchWrapper)) {
            net.minecraft.client.gui.inventory.GuiContainer gc =
                (net.minecraft.client.gui.inventory.GuiContainer) event.gui;
            if (gc.inventorySlots instanceof ContainerCarpentryBench) {
                event.gui = new GuiCarpentryBenchWrapper(
                    (ContainerCarpentryBench) gc.inventorySlots);
            }
        }
    }
}
