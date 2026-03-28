package noppes.npcs.roles;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.NpcMiscInventory;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.controllers.PlayerDataController;

public class RolePostman extends RoleInterface {
   public NpcMiscInventory inventory = new NpcMiscInventory(1);
   private List recentlyChecked = new ArrayList();
   private List toCheck;

   public RolePostman(EntityNPCInterface npc) {
      super(npc);
   }

   public boolean aiShouldExecute() {
      if (this.npc.field_70173_aa % 20 != 0) {
         return false;
      } else {
         this.toCheck = this.npc.field_70170_p.func_72872_a(EntityPlayer.class, this.npc.field_70121_D.func_72314_b((double)10.0F, (double)10.0F, (double)10.0F));
         this.toCheck.removeAll(this.recentlyChecked);
         List<EntityPlayer> listMax = this.npc.field_70170_p.func_72872_a(EntityPlayer.class, this.npc.field_70121_D.func_72314_b((double)20.0F, (double)20.0F, (double)20.0F));
         this.recentlyChecked.retainAll(listMax);
         this.recentlyChecked.addAll(this.toCheck);

         for(EntityPlayer player : this.toCheck) {
            if (PlayerDataController.instance.hasMail(player)) {
               player.func_71035_c("You've got mail");
            }
         }

         return false;
      }
   }

   public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.func_74766_a("PostInv", this.inventory.getToNBT());
   }

   public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
      this.inventory.setFromNBT(nbttagcompound.func_74775_l("PostInv"));
   }

   public boolean interact(EntityPlayer player) {
      NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerMailman, this.npc);
      return true;
   }
}
