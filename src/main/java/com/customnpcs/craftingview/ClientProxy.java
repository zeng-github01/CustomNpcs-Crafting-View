package com.customnpcs.craftingview;

import net.minecraftforge.common.MinecraftForge;

import net.minecraft.nbt.NBTTagCompound;

import com.customnpcs.craftingview.client.GuiEventHandler;
import com.customnpcs.craftingview.client.TwilightRecipeSyncClient;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        MinecraftForge.EVENT_BUS.register(new GuiEventHandler());
    }

    @Override
    public void handleTwilightGlobalRecipes(NBTTagCompound compound) {
        TwilightRecipeSyncClient.handleGlobalRecipes(compound);
    }
}
