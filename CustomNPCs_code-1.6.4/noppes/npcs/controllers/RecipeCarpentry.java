package noppes.npcs.controllers;

import java.util.HashMap;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import noppes.npcs.NBTTags;
import noppes.npcs.NoppesUtilPlayer;

public class RecipeCarpentry implements IRecipe {
   public int id = -1;
   public String name = "";
   public int recipeWidth = 4;
   public int recipeHeight = 4;
   private ItemStack[] recipeItems = new ItemStack[16];
   public ItemStack recipeOutput;
   public Availability availability = new Availability();
   public boolean isGlobal = false;
   public boolean ignoreDamage = false;

   public RecipeCarpentry() {
   }

   public RecipeCarpentry(int id, String name) {
      this.id = id;
      this.name = name;
   }

   public void readNBT(NBTTagCompound compound) {
      this.id = compound.func_74762_e("ID");
      this.recipeWidth = compound.func_74762_e("Width");
      this.recipeHeight = compound.func_74762_e("Height");
      this.recipeOutput = ItemStack.func_77949_a(compound.func_74775_l("Item"));
      this.recipeItems = NBTTags.getItemStackArray(compound.func_74761_m("Materials"));
      this.availability.readFromNBT(compound.func_74775_l("Availability"));
      this.ignoreDamage = compound.func_74767_n("IgnoreDamage");
      this.name = compound.func_74779_i("Name");
      this.isGlobal = compound.func_74767_n("Global");
   }

   public NBTTagCompound writeNBT() {
      NBTTagCompound compound = new NBTTagCompound();
      compound.func_74768_a("ID", this.id);
      compound.func_74768_a("Width", this.recipeWidth);
      compound.func_74768_a("Height", this.recipeHeight);
      if (this.recipeOutput != null) {
         compound.func_74766_a("Item", this.recipeOutput.func_77955_b(new NBTTagCompound()));
      }

      compound.func_74782_a("Materials", NBTTags.nbtItemStackArray(this.recipeItems));
      compound.func_74766_a("Availability", this.availability.writeToNBT(new NBTTagCompound()));
      compound.func_74778_a("Name", this.name);
      compound.func_74757_a("Global", this.isGlobal);
      compound.func_74757_a("IgnoreDamage", this.ignoreDamage);
      return compound;
   }

   public boolean func_77569_a(InventoryCrafting par1InventoryCrafting, World world) {
      for(int var2 = 0; var2 <= 4 - this.recipeWidth; ++var2) {
         for(int var3 = 0; var3 <= 4 - this.recipeHeight; ++var3) {
            if (this.checkMatch(par1InventoryCrafting, var2, var3, true)) {
               return true;
            }

            if (this.checkMatch(par1InventoryCrafting, var2, var3, false)) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean checkMatch(InventoryCrafting par1InventoryCrafting, int par2, int par3, boolean par4) {
      for(int var5 = 0; var5 < 4; ++var5) {
         for(int var6 = 0; var6 < 4; ++var6) {
            int var7 = var5 - par2;
            int var8 = var6 - par3;
            ItemStack var9 = null;
            if (var7 >= 0 && var8 >= 0 && var7 < this.recipeWidth && var8 < this.recipeHeight) {
               if (par4) {
                  var9 = this.recipeItems[this.recipeWidth - var7 - 1 + var8 * this.recipeWidth];
               } else {
                  var9 = this.recipeItems[var7 + var8 * this.recipeWidth];
               }
            }

            ItemStack var10 = par1InventoryCrafting.func_70463_b(var5, var6);
            if ((var10 != null || var9 != null) && !NoppesUtilPlayer.compareItems(var9, var10, this.ignoreDamage)) {
               return false;
            }
         }
      }

      return true;
   }

   public ItemStack func_77572_b(InventoryCrafting var1) {
      return this.recipeOutput == null ? null : this.recipeOutput.func_77946_l();
   }

   public int func_77570_a() {
      return 16;
   }

   public ItemStack func_77571_b() {
      return this.recipeOutput;
   }

   public void addRecipe(ItemStack par1ItemStack, Object... par2ArrayOfObj) {
      String var3 = "";
      int var4 = 0;
      int var5 = 0;
      int var6 = 0;
      if (par2ArrayOfObj[var4] instanceof String[]) {
         String[] var7 = (String[])par2ArrayOfObj[var4++];

         for(String var11 : var7) {
            ++var6;
            var5 = var11.length();
            var3 = var3 + var11;
         }
      } else {
         while(par2ArrayOfObj[var4] instanceof String) {
            String var13 = (String)par2ArrayOfObj[var4++];
            ++var6;
            var5 = var13.length();
            var3 = var3 + var13;
         }
      }

      HashMap var14;
      for(var14 = new HashMap(); var4 < par2ArrayOfObj.length; var4 += 2) {
         Character var16 = (Character)par2ArrayOfObj[var4];
         ItemStack var17 = null;
         if (par2ArrayOfObj[var4 + 1] instanceof Item) {
            var17 = new ItemStack((Item)par2ArrayOfObj[var4 + 1]);
         } else if (par2ArrayOfObj[var4 + 1] instanceof Block) {
            var17 = new ItemStack((Block)par2ArrayOfObj[var4 + 1], 1, -1);
         } else if (par2ArrayOfObj[var4 + 1] instanceof ItemStack) {
            var17 = (ItemStack)par2ArrayOfObj[var4 + 1];
         }

         var14.put(var16, var17);
      }

      ItemStack[] var15 = new ItemStack[var5 * var6];

      for(int var9 = 0; var9 < var5 * var6; ++var9) {
         char var18 = var3.charAt(var9);
         if (var14.containsKey(var18)) {
            var15[var9] = ((ItemStack)var14.get(var18)).func_77946_l();
         } else {
            var15[var9] = null;
         }
      }

      this.recipeOutput = par1ItemStack;
      this.recipeItems = var15;
      this.recipeWidth = var5;
      this.recipeHeight = var6;
      if (var5 == 4 || var6 == 4) {
         this.isGlobal = false;
      }

   }

   public ItemStack getCraftingItem(int i) {
      return this.recipeItems != null && i < this.recipeItems.length ? this.recipeItems[i] : null;
   }

   public void setCraftingItem(int i, ItemStack item) {
      if (i < this.recipeItems.length) {
         this.recipeItems[i] = item;
      }

   }

   public void clear() {
      this.recipeOutput = null;
      this.recipeItems = new ItemStack[16];
   }
}
