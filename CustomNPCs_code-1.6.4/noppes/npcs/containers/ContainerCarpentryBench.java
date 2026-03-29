package noppes.npcs.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet103SetSlot;
import net.minecraft.world.World;
import noppes.npcs.CustomItems;
import noppes.npcs.controllers.RecipeCarpentry;
import noppes.npcs.controllers.RecipeController;

public class ContainerCarpentryBench extends Container {
   public InventoryCrafting craftMatrix = new InventoryCrafting(this, 4, 4);
   public IInventory craftResult = new InventoryCraftResult();
   private EntityPlayer player;
   private World worldObj;
   private int posX;
   private int posY;
   private int posZ;

   public ContainerCarpentryBench(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5) {
      this.worldObj = par2World;
      this.posX = par3;
      this.posY = par4;
      this.posZ = par5;
      this.player = par1InventoryPlayer.field_70458_d;
      this.func_75146_a(new SlotCrafting(par1InventoryPlayer.field_70458_d, this.craftMatrix, this.craftResult, 0, 132, 35));

      for(int var6 = 0; var6 < 4; ++var6) {
         for(int var7 = 0; var7 < 4; ++var7) {
            this.func_75146_a(new Slot(this.craftMatrix, var7 + var6 * 4, 17 + var7 * 18, 8 + var6 * 18));
         }
      }

      for(int var8 = 0; var8 < 3; ++var8) {
         for(int var7 = 0; var7 < 9; ++var7) {
            this.func_75146_a(new Slot(par1InventoryPlayer, var7 + var8 * 9 + 9, 8 + var7 * 18, 84 + var8 * 18));
         }
      }

      for(int var9 = 0; var9 < 9; ++var9) {
         this.func_75146_a(new Slot(par1InventoryPlayer, var9, 8 + var9 * 18, 142));
      }

      this.func_75130_a(this.craftMatrix);
   }

   public void func_75130_a(IInventory par1IInventory) {
      if (!this.worldObj.field_72995_K) {
         RecipeCarpentry recipe = RecipeController.instance.findMatchingRecipe(this.craftMatrix);
         ItemStack item = null;
         if (recipe != null && recipe.availability.isAvailable(this.player)) {
            item = recipe.func_77572_b(this.craftMatrix);
         }

         this.craftResult.func_70299_a(0, item);
         EntityPlayerMP plmp = (EntityPlayerMP)this.player;
         plmp.field_71135_a.func_72567_b(new Packet103SetSlot(this.field_75152_c, 0, item));
      }

   }

   public void func_75134_a(EntityPlayer par1EntityPlayer) {
      super.func_75134_a(par1EntityPlayer);
      if (!this.worldObj.field_72995_K) {
         for(int var2 = 0; var2 < 16; ++var2) {
            ItemStack var3 = this.craftMatrix.func_70304_b(var2);
            if (var3 != null) {
               par1EntityPlayer.func_71021_b(var3);
            }
         }
      }

   }

   public boolean func_75145_c(EntityPlayer par1EntityPlayer) {
      return this.worldObj.func_72798_a(this.posX, this.posY, this.posZ) != CustomItems.carpentyBench.field_71990_ca ? false : par1EntityPlayer.func_70092_e((double)this.posX + (double)0.5F, (double)this.posY + (double)0.5F, (double)this.posZ + (double)0.5F) <= (double)64.0F;
   }

   public ItemStack func_82846_b(EntityPlayer par1EntityPlayer, int par1) {
      ItemStack var2 = null;
      Slot var3 = (Slot)this.field_75151_b.get(par1);
      if (var3 != null && var3.func_75216_d()) {
         ItemStack var4 = var3.func_75211_c();
         var2 = var4.func_77946_l();
         if (par1 == 0) {
            if (!this.func_75135_a(var4, 17, 53, true)) {
               return null;
            }

            var3.func_75220_a(var4, var2);
         } else if (par1 >= 17 && par1 < 44) {
            if (!this.func_75135_a(var4, 44, 53, false)) {
               return null;
            }
         } else if (par1 >= 44 && par1 < 53) {
            if (!this.func_75135_a(var4, 17, 44, false)) {
               return null;
            }
         } else if (!this.func_75135_a(var4, 17, 53, false)) {
            return null;
         }

         if (var4.field_77994_a == 0) {
            var3.func_75215_d((ItemStack)null);
         } else {
            var3.func_75218_e();
         }

         if (var4.field_77994_a == var2.field_77994_a) {
            return null;
         }

         var3.func_82870_a(par1EntityPlayer, var4);
      }

      return var2;
   }
}
