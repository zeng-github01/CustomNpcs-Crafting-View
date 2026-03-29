package noppes.npcs.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.roles.RoleTrader;

public class ContainerNPCTrader extends Container {
   public InventoryNPC currencyMatrix;
   public RoleTrader role;

   public ContainerNPCTrader(EntityNPCInterface npc, EntityPlayer player) {
      this.role = (RoleTrader)npc.roleInterface;
      this.currencyMatrix = new InventoryNPC("currency", 1, this);
      this.func_75146_a(new SlotNpcTraderCurrency(this, this.currencyMatrix, 0, 15, 39));

      for(int i = 0; i < 18; ++i) {
         int x = 62;
         x += i % 3 * 45;
         int y = 9;
         y += i / 3 * 22;
         this.func_75146_a(new Slot(this.role.inventorySold, i, x, y));
      }

      for(int i1 = 0; i1 < 3; ++i1) {
         for(int l1 = 0; l1 < 9; ++l1) {
            this.func_75146_a(new Slot(player.field_71071_by, l1 + i1 * 9 + 9, 8 + l1 * 18, 144 + i1 * 18));
         }
      }

      for(int j1 = 0; j1 < 9; ++j1) {
         this.func_75146_a(new Slot(player.field_71071_by, j1, 8 + j1 * 18, 202));
      }

   }

   public ItemStack func_82846_b(EntityPlayer par1EntityPlayer, int i) {
      return null;
   }

   public boolean func_75145_c(EntityPlayer entityplayer) {
      return true;
   }

   public void func_75134_a(EntityPlayer entityplayer) {
      super.func_75134_a(entityplayer);
      ItemStack itemstack = this.currencyMatrix.func_70301_a(0);
      if (itemstack != null && !entityplayer.field_70170_p.field_72995_K) {
         this.currencyMatrix.func_70299_a(0, (ItemStack)null);
         entityplayer.func_70099_a(itemstack, 0.0F);
      }

   }

   public ItemStack func_75144_a(int i, int j, int par3, EntityPlayer entityplayer) {
      if (par3 == 6) {
         par3 = 0;
      }

      if (i > 0 && i < 19) {
         if (j == 1) {
            return null;
         } else {
            Slot slot = (Slot)this.field_75151_b.get(i);
            if (slot != null && slot.func_75211_c() != null) {
               ItemStack item = slot.func_75211_c();
               if (!this.canGivePlayer(item, entityplayer)) {
                  return null;
               } else if (!this.canBuy((ItemStack)this.role.inventoryCurrency.items.get(i - 1), entityplayer)) {
                  return null;
               } else {
                  ItemStack soldItem = item.func_77946_l();
                  this.givePlayer(soldItem, entityplayer);
                  return soldItem;
               }
            } else {
               return null;
            }
         }
      } else {
         return super.func_75144_a(i, j, par3, entityplayer);
      }
   }

   private boolean canBuy(ItemStack item, EntityPlayer entityplayer) {
      ItemStack currency = this.currencyMatrix.func_70301_a(0);
      if (currency != null && NoppesUtilPlayer.compareItems(currency, item, false)) {
         int price = item.field_77994_a;
         if (currency.field_77994_a < price) {
            return false;
         } else {
            if (currency.field_77994_a - price == 0) {
               this.currencyMatrix.func_70299_a(0, (ItemStack)null);
            } else {
               currency.func_77979_a(price);
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private boolean canGivePlayer(ItemStack item, EntityPlayer entityplayer) {
      ItemStack itemstack3 = entityplayer.field_71071_by.func_70445_o();
      if (itemstack3 == null) {
         return true;
      } else {
         if (NoppesUtilPlayer.compareItems(itemstack3, item, false)) {
            int k1 = item.field_77994_a;
            if (k1 > 0 && k1 + itemstack3.field_77994_a <= itemstack3.func_77976_d()) {
               return true;
            }
         }

         return false;
      }
   }

   private void givePlayer(ItemStack item, EntityPlayer entityplayer) {
      ItemStack itemstack3 = entityplayer.field_71071_by.func_70445_o();
      if (itemstack3 == null) {
         entityplayer.field_71071_by.func_70437_b(item);
      } else if (NoppesUtilPlayer.compareItems(itemstack3, item, false)) {
         int k1 = item.field_77994_a;
         if (k1 > 0 && k1 + itemstack3.field_77994_a <= itemstack3.func_77976_d()) {
            itemstack3.field_77994_a += k1;
         }
      }

   }
}
