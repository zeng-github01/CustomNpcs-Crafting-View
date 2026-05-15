package com.customnpcs.craftingview;

import com.customnpcs.craftingview.network.PacketHandler;

import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.common.event.FMLInitializationEvent;

public class CommonProxy {

    public void init(FMLInitializationEvent event) {
        PacketHandler.init();
    }

    public void handleTwilightGlobalRecipes(NBTTagCompound compound) {
    }
}
