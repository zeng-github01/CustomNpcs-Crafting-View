package noppes.npcs.containers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerMerchantAdd extends Container {
   private IMerchant theMerchant;
   private InventoryBasic merchantInventory;
   private final World theWorld;

   public ContainerMerchantAdd(InventoryPlayer par1InventoryPlayer, IMerchant par2IMerchant, World par3World) {
      this.theMerchant = par2IMerchant;
      this.theWorld = par3World;
      this.merchantInventory = new InventoryBasic("", false, 3);
      this.func_75146_a(new Slot(this.merchantInventory, 0, 36, 53));
      this.func_75146_a(new Slot(this.merchantInventory, 1, 62, 53));
      this.func_75146_a(new Slot(this.merchantInventory, 2, 120, 53));

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.func_75146_a(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
         }
      }

      for(int var6 = 0; var6 < 9; ++var6) {
         this.func_75146_a(new Slot(par1InventoryPlayer, var6, 8 + var6 * 18, 142));
      }

   }

   public void func_75132_a(ICrafting par1ICrafting) {
      super.func_75132_a(par1ICrafting);
   }

   public void func_75142_b() {
      super.func_75142_b();
   }

   public void func_75130_a(IInventory par1IInventory) {
      super.func_75130_a(par1IInventory);
   }

   public void setCurrentRecipeIndex(int par1) {
   }

   @SideOnly(Side.CLIENT)
   public void func_75137_b(int par1, int par2) {
   }

   public boolean func_75145_c(EntityPlayer par1EntityPlayer) {
      return true;
   }

   public ItemStack func_82846_b(EntityPlayer par1EntityPlayer, int par2) {
      ItemStack itemstack = null;
      Slot slot = (Slot)this.field_75151_b.get(par2);
      if (slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         if (par2 != 0 && par2 != 1 && par2 != 2) {
            if (par2 >= 3 && par2 < 30) {
               if (!this.func_75135_a(itemstack1, 30, 39, false)) {
                  return null;
               }
            } else if (par2 >= 30 && par2 < 39 && !this.func_75135_a(itemstack1, 3, 30, false)) {
               return null;
            }
         } else if (!this.func_75135_a(itemstack1, 3, 39, false)) {
            return null;
         }

         if (itemstack1.field_77994_a == 0) {
            slot.func_75215_d((ItemStack)null);
         } else {
            slot.func_75218_e();
         }

         if (itemstack1.field_77994_a == itemstack.field_77994_a) {
            return null;
         }

         slot.func_82870_a(par1EntityPlayer, itemstack1);
      }

      return itemstack;
   }

   public void func_75134_a(EntityPlayer par1EntityPlayer) {
      super.func_75134_a(par1EntityPlayer);
      this.theMerchant.func_70932_a_((EntityPlayer)null);
      super.func_75134_a(par1EntityPlayer);
      if (!this.theWorld.field_72995_K) {
         ItemStack itemstack = this.merchantInventory.func_70304_b(0);
         if (itemstack != null) {
            par1EntityPlayer.func_71021_b(itemstack);
         }

         itemstack = this.merchantInventory.func_70304_b(1);
         if (itemstack != null) {
            par1EntityPlayer.func_71021_b(itemstack);
         }
      }

   }
}
