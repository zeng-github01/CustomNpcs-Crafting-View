package noppes.npcs.controllers;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import noppes.npcs.CustomNpcs;

public class RecipeController {
   private static Collection prevRecipes;
   public HashMap globalRecipes = new HashMap();
   public HashMap anvilRecipes = new HashMap();
   public static RecipeController instance;

   public RecipeController() {
      instance = this;
      this.loadCategories();
      reloadGlobalRecipes(this.globalRecipes);
   }

   public static void reloadGlobalRecipes(HashMap globalRecipes) {
      List list = CraftingManager.func_77594_a().func_77592_b();
      if (prevRecipes != null) {
         list.removeAll(prevRecipes);
      }

      prevRecipes = new HashSet(globalRecipes.values());
      list.addAll(prevRecipes);
   }

   private void loadCategories() {
      File saveDir = CustomNpcs.getWorldSaveDirectory();

      try {
         File file = new File(saveDir, "recipes.dat");
         if (file.exists()) {
            this.loadCategories(file);
         } else {
            this.loadDefaultRecipes();
         }
      } catch (Exception e) {
         e.printStackTrace();

         try {
            File file = new File(saveDir, "recipes.dat_old");
            if (file.exists()) {
               this.loadCategories(file);
            }
         } catch (Exception var4) {
            e.printStackTrace();
         }
      }

   }

   private void loadDefaultRecipes() {
      new RecipesDefault(this);
      this.saveCategories();
   }

   private void loadCategories(File file) throws Exception {
      NBTTagCompound nbttagcompound1 = CompressedStreamTools.func_74796_a(new FileInputStream(file));
      NBTTagList list = nbttagcompound1.func_74761_m("Data");
      HashMap<Integer, RecipeCarpentry> globalRecipes = new HashMap();
      HashMap<Integer, RecipeCarpentry> anvilRecipes = new HashMap();
      if (list != null) {
         for(int i = 0; i < list.func_74745_c(); ++i) {
            RecipeCarpentry recipe = new RecipeCarpentry();
            recipe.readNBT((NBTTagCompound)list.func_74743_b(i));
            if (recipe.isGlobal) {
               globalRecipes.put(recipe.id, recipe);
            } else {
               anvilRecipes.put(recipe.id, recipe);
            }
         }
      }

      this.anvilRecipes = anvilRecipes;
      this.globalRecipes = globalRecipes;
   }

   private void saveCategories() {
      try {
         File saveDir = CustomNpcs.getWorldSaveDirectory();
         NBTTagList list = new NBTTagList();

         for(RecipeCarpentry recipe : this.globalRecipes.values()) {
            list.func_74742_a(recipe.writeNBT());
         }

         for(RecipeCarpentry recipe : this.anvilRecipes.values()) {
            list.func_74742_a(recipe.writeNBT());
         }

         NBTTagCompound nbttagcompound = new NBTTagCompound();
         nbttagcompound.func_74782_a("Data", list);
         File file = new File(saveDir, "recipes.dat_new");
         File file1 = new File(saveDir, "recipes.dat_old");
         File file2 = new File(saveDir, "recipes.dat");
         CompressedStreamTools.func_74799_a(nbttagcompound, new FileOutputStream(file));
         if (file1.exists()) {
            file1.delete();
         }

         file2.renameTo(file1);
         if (file2.exists()) {
            file2.delete();
         }

         file.renameTo(file2);
         if (file.exists()) {
            file.delete();
         }
      } catch (Exception e) {
         e.printStackTrace();
      }

   }

   public RecipeCarpentry findMatchingRecipe(InventoryCrafting par1InventoryCrafting) {
      for(RecipeCarpentry recipe : this.anvilRecipes.values()) {
         if (recipe.func_77569_a(par1InventoryCrafting, (World)null)) {
            return recipe;
         }
      }

      return null;
   }

   public RecipeCarpentry getRecipe(int id) {
      if (this.globalRecipes.containsKey(id)) {
         return (RecipeCarpentry)this.globalRecipes.get(id);
      } else {
         return this.anvilRecipes.containsKey(id) ? (RecipeCarpentry)this.anvilRecipes.get(id) : null;
      }
   }

   public RecipeCarpentry saveRecipe(DataInputStream dis) throws IOException {
      RecipeCarpentry recipe = new RecipeCarpentry();
      recipe.readNBT(CompressedStreamTools.func_74794_a(dis));
      RecipeCarpentry current = this.getRecipe(recipe.id);
      if (current != null && !current.name.equals(recipe.name)) {
         while(this.containsRecipeName(recipe.name)) {
            recipe.name = recipe.name + "_";
         }
      }

      if (recipe.id == -1) {
         for(recipe.id = this.getUniqueId(); this.containsRecipeName(recipe.name); recipe.name = recipe.name + "_") {
         }
      }

      if (recipe.isGlobal) {
         this.anvilRecipes.remove(recipe.id);
         this.globalRecipes.put(recipe.id, recipe);
      } else {
         this.globalRecipes.remove(recipe.id);
         this.anvilRecipes.put(recipe.id, recipe);
      }

      this.saveCategories();
      reloadGlobalRecipes(this.globalRecipes);
      return recipe;
   }

   private int getUniqueId() {
      int id = 0;

      for(int key : this.globalRecipes.keySet()) {
         if (key > id) {
            id = key;
         }
      }

      for(int key : this.anvilRecipes.keySet()) {
         if (key > id) {
            id = key;
         }
      }

      ++id;
      return id;
   }

   private boolean containsRecipeName(String name) {
      name = name.toLowerCase();

      for(RecipeCarpentry recipe : this.globalRecipes.values()) {
         if (recipe.name.toLowerCase().equals(name)) {
            return true;
         }
      }

      for(RecipeCarpentry recipe : this.anvilRecipes.values()) {
         if (recipe.name.toLowerCase().equals(name)) {
            return true;
         }
      }

      return false;
   }

   public RecipeCarpentry removeRecipe(int id) {
      RecipeCarpentry recipe = this.getRecipe(id);
      this.globalRecipes.remove(recipe.id);
      this.anvilRecipes.remove(recipe.id);
      this.saveCategories();
      reloadGlobalRecipes(this.globalRecipes);
      return recipe;
   }
}
