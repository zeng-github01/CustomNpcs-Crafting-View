package noppes.npcs.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.roles.RoleFollower;

public class ContainerNPCFollowerSetup extends Container {
   private RoleFollower role;

   public ContainerNPCFollowerSetup(EntityNPCInterface npc, EntityPlayer player) {
      this.role = (RoleFollower)npc.roleInterface;

      for(int i1 = 0; i1 < 3; ++i1) {
         this.func_75146_a(new Slot(this.role.inventory, i1, 44, 29 + i1 * 25));
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
      ItemStack itemstack = null;
      Slot slot = (Slot)this.field_75151_b.get(i);
      if (slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         if (i >= 0 && i < 3) {
            if (!this.func_75135_a(itemstack1, 3, 38, true)) {
               return null;
            }
         } else if (i >= 3 && i < 30) {
            if (!this.func_75135_a(itemstack1, 30, 38, false)) {
               return null;
            }
         } else if (i >= 30 && i < 38) {
            if (!this.func_75135_a(itemstack1, 3, 29, false)) {
               return null;
            }
         } else if (!this.func_75135_a(itemstack1, 3, 38, false)) {
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
}
