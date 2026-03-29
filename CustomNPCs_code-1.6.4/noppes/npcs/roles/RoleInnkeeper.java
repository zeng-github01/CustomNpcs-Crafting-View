package noppes.npcs.roles;

import java.util.HashMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.controllers.InnDoorData;

public class RoleInnkeeper extends RoleInterface {
   private String innName = "Inn";
   private HashMap doors = new HashMap();

   public RoleInnkeeper(EntityNPCInterface npc) {
      super(npc);
   }

   public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.func_74778_a("InnName", this.innName);
      nbttagcompound.func_74782_a("InnDoors", this.nbtInnDoors(this.doors));
   }

   private NBTBase nbtInnDoors(HashMap doors1) {
      NBTTagList nbttaglist = new NBTTagList();
      if (doors1 == null) {
         return nbttaglist;
      } else {
         HashMap<String, InnDoorData> doors2 = doors1;

         for(String name : doors1.keySet()) {
            InnDoorData door = (InnDoorData)doors2.get(name);
            if (door != null) {
               NBTTagCompound nbttagcompound = new NBTTagCompound();
               nbttagcompound.func_74778_a("Name", name);
               nbttagcompound.func_74768_a("posX", door.x);
               nbttagcompound.func_74768_a("posY", door.y);
               nbttagcompound.func_74768_a("posZ", door.z);
               nbttaglist.func_74742_a(nbttagcompound);
            }
         }

         return nbttaglist;
      }
   }

   public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
      this.innName = nbttagcompound.func_74779_i("InnName");
      this.doors = this.getInnDoors(nbttagcompound.func_74761_m("InnDoors"));
   }

   private HashMap getInnDoors(NBTTagList tagList) {
      HashMap<String, InnDoorData> list = new HashMap();

      for(int i = 0; i < tagList.func_74745_c(); ++i) {
         NBTTagCompound nbttagcompound = (NBTTagCompound)tagList.func_74743_b(i);
         String name = nbttagcompound.func_74779_i("Name");
         InnDoorData door = new InnDoorData();
         door.x = nbttagcompound.func_74762_e("posX");
         door.y = nbttagcompound.func_74762_e("posY");
         door.z = nbttagcompound.func_74762_e("posZ");
         list.put(name, door);
      }

      return list;
   }

   public boolean interact(EntityPlayer player) {
      this.npc.say(player, this.npc.advanced.getInteractLine());
      if (this.doors.isEmpty()) {
         player.func_71035_c("No Rooms available");
         return true;
      } else {
         return false;
      }
   }
}
