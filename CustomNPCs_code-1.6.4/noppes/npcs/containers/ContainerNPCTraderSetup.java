package noppes.npcs.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.roles.RoleTrader;

public class ContainerNPCTraderSetup extends Container {
   private RoleTrader role;

   public ContainerNPCTraderSetup(EntityNPCInterface npc, EntityPlayer player) {
      this.role = (RoleTrader)npc.roleInterface;

      for(int i = 0; i < 18; ++i) {
         int x = 194;
         x += i % 3 * 59;
         int y = 14;
         y += i / 3 * 22;
         this.func_75146_a(new Slot(this.role.inventoryCurrency, i, x, y));
         this.func_75146_a(new Slot(this.role.inventorySold, i, x + 26, y));
      }

      for(int i1 = 0; i1 < 3; ++i1) {
         for(int l1 = 0; l1 < 9; ++l1) {
            this.func_75146_a(new Slot(player.field_71071_by, l1 + i1 * 9 + 9, 8 + l1 * 18, 103 + i1 * 18));
         }
      }

      for(int j1 = 0; j1 < 9; ++j1) {
         this.func_75146_a(new Slot(player.field_71071_by, j1, 8 + j1 * 18, 161));
      }

   }

   public ItemStack func_82846_b(EntityPlayer par1EntityPlayer, int i) {
      return null;
   }

   public boolean func_75145_c(EntityPlayer entityplayer) {
      return true;
   }
}
