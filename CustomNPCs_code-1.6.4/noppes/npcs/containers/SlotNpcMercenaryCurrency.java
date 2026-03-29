package noppes.npcs.containers;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import noppes.npcs.roles.RoleFollower;

class SlotNpcMercenaryCurrency extends Slot {
   RoleFollower role;

   public SlotNpcMercenaryCurrency(RoleFollower role, IInventory inv, int i, int j, int k) {
      super(inv, i, j, k);
      this.role = role;
   }

   public int func_75219_a() {
      return 64;
   }

   public boolean func_75214_a(ItemStack itemstack) {
      int id = itemstack.field_77993_c;

      for(ItemStack is : this.role.inventory.items.values()) {
         if (id == is.field_77993_c && (!itemstack.func_77981_g() || itemstack.func_77960_j() == is.func_77960_j())) {
            return true;
         }
      }

      return false;
   }
}
