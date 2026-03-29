package noppes.npcs.containers;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotNpcBankCurrency extends Slot {
   public ItemStack item;

   public SlotNpcBankCurrency(ContainerNPCBankInterface containerplayer, IInventory iinventory, int i, int j, int k) {
      super(iinventory, i, j, k);
   }

   public int func_75219_a() {
      return 64;
   }

   public boolean func_75214_a(ItemStack itemstack) {
      if (this.item == null) {
         return false;
      } else {
         return this.item.field_77993_c == itemstack.field_77993_c && (!this.item.func_77981_g() || this.item.func_77960_j() == itemstack.func_77960_j());
      }
   }
}
