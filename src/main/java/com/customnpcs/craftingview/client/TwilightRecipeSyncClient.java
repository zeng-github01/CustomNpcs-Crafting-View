package com.customnpcs.craftingview.client;

import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import noppes.npcs.controllers.RecipeCarpentry;
import noppes.npcs.controllers.RecipeController;

@SideOnly(Side.CLIENT)
public class TwilightRecipeSyncClient {

    private static final HashMap syncedGlobalRecipes = new HashMap();

    public static void handleGlobalRecipes(NBTTagCompound compound) {
        NBTTagList list = compound.getTagList("recipes");
        HashMap receivedRecipes = new HashMap();
        HashMap recipes = new HashMap();
        if (RecipeController.instance != null) {
            recipes.putAll(RecipeController.instance.globalRecipes);
        }
        if (list != null) {
            for (int i = 0; i < list.tagCount(); i++) {
                RecipeCarpentry recipe = new RecipeCarpentry();
                recipe.readNBT((NBTTagCompound) list.tagAt(i));
                if (recipe.recipeWidth <= 3 && recipe.recipeHeight <= 3) {
                    Integer id = Integer.valueOf(recipe.id);
                    receivedRecipes.put(id, recipe);
                    recipes.put(id, recipe);
                }
            }
        }

        syncedGlobalRecipes.clear();
        syncedGlobalRecipes.putAll(receivedRecipes);

        RecipeController.reloadGlobalRecipes(recipes);
        if (RecipeController.instance != null) {
            RecipeController.instance.globalRecipes = recipes;
        }

        GuiScreen gui = Minecraft.getMinecraft().currentScreen;
        if (gui instanceof GuiTwilightUncraftingWrapper) {
            ((GuiTwilightUncraftingWrapper) gui).reloadRecipes();
        }
    }

    public static HashMap getSyncedGlobalRecipes() {
        return new HashMap(syncedGlobalRecipes);
    }
}
