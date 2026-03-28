package com.customnpcs.craftingview.network;

import com.customnpcs.craftingview.CraftingViewMod;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {

    public static SimpleNetworkWrapper CHANNEL;

    public static void init() {
        CHANNEL = NetworkRegistry.INSTANCE.newSimpleChannel(CraftingViewMod.MODID);
        CHANNEL.registerMessage(PacketFillCraftingGrid.Handler.class, PacketFillCraftingGrid.class, 0, Side.SERVER);
    }
}
