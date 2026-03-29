package noppes.npcs.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import noppes.npcs.EntityNPCInterface;

public class ContainerNPCInv extends Container {
   public ContainerNPCInv(EntityNPCInterface npc, EntityPlayer player) {
      for(int l = 0; l < 4; ++l) {
         this.func_75146_a(new SlotNPCArmor(this, npc.inventory, l, 9, 22 + l * 18, l));
      }

      this.func_75146_a(new Slot(npc.inventory, 4, 81, 22));
      this.func_75146_a(new Slot(npc.inventory, 5, 81, 40));
      this.func_75146_a(new Slot(npc.inventory, 6, 81, 58));

      for(int l = 0; l < 8; ++l) {
         this.func_75146_a(new Slot(npc.inventory, l + 7, 191, 17 + l * 22));
      }

      for(int i1 = 0; i1 < 3; ++i1) {
         for(int l1 = 0; l1 < 9; ++l1) {
            this.func_75146_a(new Slot(player.field_71071_by, l1 + i1 * 9 + 9, l1 * 18 + 8, 113 + i1 * 18));
         }
      }

      for(int j1 = 0; j1 < 9; ++j1) {
         this.func_75146_a(new Slot(player.field_71071_by, j1, j1 * 18 + 8, 171));
      }

   }

   public ItemStack func_82846_b(EntityPlayer par1EntityPlayer, int i) {
      return null;
   }

   public boolean func_75145_c(EntityPlayer entityplayer) {
      return true;
   }
}
