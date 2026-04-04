package com.customnpcs.craftingview;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(
    modid = CraftingViewMod.MODID,
    version = "1.0.0",
    name = "CustomNPCs Crafting View",
    acceptedMinecraftVersions = "[1.6.4]",
    dependencies = "required-after:customnpcs")
public class CraftingViewMod {

    public static final String MODID = "customnpcs_crafting_view";
    public static final Logger LOG = LogManager.getLogger(MODID);

    @SidedProxy(
        clientSide = "com.customnpcs.craftingview.ClientProxy",
        serverSide = "com.customnpcs.craftingview.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.load(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }
}
