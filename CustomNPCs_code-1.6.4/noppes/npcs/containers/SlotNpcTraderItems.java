package noppes.npcs.containers;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

class SlotNpcTraderItems extends Slot {
   public SlotNpcTraderItems(IInventory iinventory, int i, int j, int k) {
      super(iinventory, i, j, k);
   }

   public void onPickupFromSlot(ItemStack itemstack) {
      if (itemstack != null) {
         if (this.func_75211_c() != null) {
            if (itemstack.field_77993_c == this.func_75211_c().field_77993_c) {
               --itemstack.field_77994_a;
            }
         }
      }
   }

   public int func_75219_a() {
      return 64;
   }

   public boolean func_75214_a(ItemStack itemstack) {
      return false;
   }
}
