package noppes.npcs.controllers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import noppes.npcs.CustomItems;

public class RecipesDefault {
   Object[][] recipes;

   public RecipesDefault(RecipeController controller) {
      this.recipes = new Object[][]{{"XXXY", " ZM ", "  M ", 'Y', Block.field_72043_aJ, 'M', Item.field_77669_D, 'Z', Block.field_72034_aR, 'X', null}, {"Gun Wooden", Block.field_71988_x, CustomItems.gunWood, 1, 0, 0, "Gun Stone", Block.field_71978_w, CustomItems.gunStone, 1, 0, 0, "Gun Iron", Item.field_77703_o, CustomItems.gunIron, 1, 0, 0, "Gun Gold", Item.field_77717_p, CustomItems.gunGold, 1, 0, 0, "Gun Diamond", Item.field_77702_n, CustomItems.gunDiamond, 1, 0, 0, "Gun Emerald", Item.field_77817_bH, CustomItems.gunEmerald, 1, 0, 0}, {"X", 'X', null}, {"Bullet Wooden", Block.field_71988_x, CustomItems.bulletWood, 9, 0, 0, "Bullet Stone", Block.field_71978_w, CustomItems.bulletStone, 9, 0, 0, "Bullet Iron", Item.field_77703_o, CustomItems.bulletIron, 9, 0, 0, "Bullet Gold", Item.field_77717_p, CustomItems.bulletGold, 9, 0, 0, "Bullet Diamond", Item.field_77702_n, CustomItems.bulletDiamond, 9, 0, 0, "Bullet Emerald", Item.field_77817_bH, CustomItems.bulletEmerald, 9, 0, 0}, {"XX", " Y", " Y", 'X', Item.field_77684_U, 'Y', null}, {"Npc Wand", Item.field_77669_D, CustomItems.wand, 1, 0, 1}, {"XX", "XY", " Y", 'X', Item.field_77684_U, 'Y', null}, {"Npc Cloner", Item.field_77669_D, CustomItems.cloner, 1, 0, 1}, {"XYX", "Z Z", "Z Z", 'X', Block.field_71988_x, 'Z', Item.field_77669_D, 'Y', null}, {"Carpentry Bench", Block.field_72060_ay, CustomItems.carpentyBench, 1, 0, 1}, {"XY", 'X', Item.field_77767_aC, 'Y', null}, {"Mana", Item.field_77751_aT, CustomItems.mana, 1, 0, 1}};
      int id = 0;

      for(int i = 0; i < this.recipes.length; i += 2) {
         Object[] recipe = this.recipes[i];
         Object[] materials = this.recipes[i + 1];

         for(int j = 0; j < materials.length; ++id) {
            String name = (String)materials[j];
            Object material = materials[j + 1];
            Object item = materials[j + 2];
            if (item != null) {
               int shifted = -1;
               if (item instanceof Block) {
                  shifted = ((Block)item).field_71990_ca;
               } else {
                  shifted = ((Item)item).field_77779_bT;
               }

               ItemStack output = new ItemStack(shifted, (Integer)materials[j + 3], (Integer)materials[j + 4]);
               int type = (Integer)materials[j + 5];
               RecipeCarpentry recipeAnvil = new RecipeCarpentry(id, name);
               recipe[recipe.length - 1] = material;
               recipeAnvil.isGlobal = type == 1;
               recipeAnvil.addRecipe(output, recipe);
               if (recipeAnvil.isGlobal) {
                  RecipeController.instance.anvilRecipes.put(recipeAnvil.id, recipeAnvil);
               } else {
                  RecipeController.instance.globalRecipes.put(recipeAnvil.id, recipeAnvil);
               }
            }

            j += 6;
         }
      }

   }
}
