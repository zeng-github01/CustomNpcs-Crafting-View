package noppes.npcs.roles;

import java.util.HashMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.EntityNPCInterface;

public abstract class RoleInterface {
   public EntityNPCInterface npc;
   public HashMap dataString = new HashMap();

   public RoleInterface(EntityNPCInterface npc) {
      this.npc = npc;
   }

   public abstract void writeEntityToNBT(NBTTagCompound var1);

   public abstract void readEntityFromNBT(NBTTagCompound var1);

   public abstract boolean interact(EntityPlayer var1);

   public void killed() {
   }

   public void delete() {
   }

   public boolean aiShouldExecute() {
      return false;
   }

   public boolean aiContinueExecute() {
      return false;
   }

   public void aiStartExecuting() {
   }

   public void aiUpdateTask() {
   }
}
