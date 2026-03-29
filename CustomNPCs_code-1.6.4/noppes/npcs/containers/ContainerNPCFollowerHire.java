package noppes.npcs.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.roles.RoleFollower;

public class ContainerNPCFollowerHire extends Container {
   public InventoryBasic currencyMatrix;
   public RoleFollower role;

   public ContainerNPCFollowerHire(EntityNPCInterface npc, EntityPlayer player) {
      this.role = (RoleFollower)npc.roleInterface;
      this.currencyMatrix = new InventoryBasic("currency", false, 1);
      this.func_75146_a(new SlotNpcMercenaryCurrency(this.role, this.currencyMatrix, 0, 44, 35));

      for(int i1 = 0; i1 < 3; ++i1) {
         for(int l1 = 0; l1 < 9; ++l1) {
            this.func_75146_a(new Slot(player.field_71071_by, l1 + i1 * 9 + 9, 8 + l1 * 18, 84 + i1 * 18));
         }
      }

      for(int j1 = 0; j1 < 9; ++j1) {
         this.func_75146_a(new Slot(player.field_71071_by, j1, 8 + j1 * 18, 142));
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
      if (!entityplayer.field_70170_p.field_72995_K) {
         ItemStack itemstack = this.currencyMatrix.func_70304_b(0);
         if (itemstack != null && !entityplayer.field_70170_p.field_72995_K) {
            entityplayer.func_70099_a(itemstack, 0.0F);
         }
      }

   }
}
