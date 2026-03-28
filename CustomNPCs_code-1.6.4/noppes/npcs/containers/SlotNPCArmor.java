package noppes.npcs.containers;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

class SlotNPCArmor extends Slot {
   final int armorType;
   final ContainerNPCInv field_75224_c;

   SlotNPCArmor(ContainerNPCInv containerplayer, IInventory iinventory, int i, int j, int k, int l) {
      super(iinventory, i, j, k);
      this.field_75224_c = containerplayer;
      this.armorType = l;
   }

   public int func_75219_a() {
      return 1;
   }

   public Icon func_75212_b() {
      return ItemArmor.func_94602_b(this.armorType);
   }

   public boolean func_75214_a(ItemStack itemstack) {
      if (itemstack.func_77973_b() instanceof ItemArmor) {
         return ((ItemArmor)itemstack.func_77973_b()).field_77881_a == this.armorType;
      } else if (itemstack.func_77973_b() instanceof ItemBlock) {
         return this.armorType == 0;
      } else {
         return false;
      }
   }
}
