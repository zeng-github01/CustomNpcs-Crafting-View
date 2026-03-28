package noppes.npcs.roles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.NpcMiscInventory;
import noppes.npcs.constants.EnumGuiType;

public class RoleTrader extends RoleInterface {
   public NpcMiscInventory inventoryCurrency = new NpcMiscInventory(18);
   public NpcMiscInventory inventorySold = new NpcMiscInventory(18);

   public RoleTrader(EntityNPCInterface npc) {
      super(npc);
   }

   public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.func_74766_a("TraderCurrency", this.inventoryCurrency.getToNBT());
      nbttagcompound.func_74766_a("TraderSold", this.inventorySold.getToNBT());
   }

   public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
      this.inventoryCurrency.setFromNBT(nbttagcompound.func_74775_l("TraderCurrency"));
      this.inventorySold.setFromNBT(nbttagcompound.func_74775_l("TraderSold"));
   }

   public boolean interact(EntityPlayer player) {
      this.npc.say(player, this.npc.advanced.getInteractLine());
      NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerTrader, this.npc);
      return false;
   }

   public boolean hasCurrency(ItemStack itemstack) {
      if (itemstack == null) {
         return false;
      } else {
         for(ItemStack item : this.inventoryCurrency.items.values()) {
            if (item != null && NoppesUtilPlayer.compareItems(item, itemstack, false)) {
               return true;
            }
         }

         return false;
      }
   }
}
