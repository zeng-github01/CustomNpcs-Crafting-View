package com.customnpcs.craftingview;

import net.minecraftforge.common.MinecraftForge;

import com.customnpcs.craftingview.client.GuiEventHandler;

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
}
