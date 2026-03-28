package com.customnpcs.craftingview;

import com.customnpcs.craftingview.network.PacketHandler;

import cpw.mods.fml.common.event.FMLInitializationEvent;

public class CommonProxy {

    public void init(FMLInitializationEvent event) {
        PacketHandler.init();
    }
}
