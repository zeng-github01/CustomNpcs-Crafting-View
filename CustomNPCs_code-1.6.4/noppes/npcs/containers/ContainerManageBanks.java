package noppes.npcs.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import noppes.npcs.controllers.Bank;

public class ContainerManageBanks extends Container {
   public Bank bank = new Bank();

   public ContainerManageBanks(EntityPlayer player) {
      for(int i = 0; i < 6; ++i) {
         int x = 36;
         int y = 21;
         y += i * 22;
         this.func_75146_a(new Slot(this.bank.currencyInventory, i, x, y));
      }

      for(int i = 0; i < 6; ++i) {
         int x = 142;
         int y = 21;
         y += i * 22;
         this.func_75146_a(new Slot(this.bank.upgradeInventory, i, x, y));
      }

      for(int j1 = 0; j1 < 9; ++j1) {
         this.func_75146_a(new Slot(player.field_71071_by, j1, 8 + j1 * 18, 154));
      }

   }

   public ItemStack func_82846_b(EntityPlayer par1EntityPlayer, int i) {
      ItemStack itemstack = null;
      Slot slot = (Slot)this.field_75151_b.get(i);
      if (slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         if (i >= 0 && i < 7) {
            if (!this.func_75135_a(itemstack1, 7, 43, true)) {
               return null;
            }
         } else if (i >= 20 && i < 47) {
            if (!this.func_75135_a(itemstack1, 36, 43, false)) {
               return null;
            }
         } else if (i >= 47 && i < 56) {
            if (!this.func_75135_a(itemstack1, 7, 34, false)) {
               return null;
            }
         } else if (!this.func_75135_a(itemstack1, 7, 43, false)) {
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

   public boolean func_75145_c(EntityPlayer entityplayer) {
      return true;
   }

   public void setBank(Bank bank2) {
      for(int i = 0; i < 6; ++i) {
         this.bank.currencyInventory.func_70299_a(i, bank2.currencyInventory.func_70301_a(i));
         this.bank.upgradeInventory.func_70299_a(i, bank2.upgradeInventory.func_70301_a(i));
      }

   }
}
