package noppes.npcs.controllers;

import java.util.HashSet;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class PlayerDialogData implements IPlayerData {
   public HashSet dialogsRead = new HashSet();

   public void readNBT(NBTTagCompound compound) {
      HashSet<Integer> dialogsRead = new HashSet();
      if (compound != null) {
         NBTTagList list = compound.func_74761_m("DialogData");
         if (list != null && list.func_74745_c() != 0) {
            for(int i = 0; i < list.func_74745_c(); ++i) {
               NBTTagCompound nbttagcompound = (NBTTagCompound)list.func_74743_b(i);
               dialogsRead.add(nbttagcompound.func_74762_e("Dialog"));
            }

            this.dialogsRead = dialogsRead;
         }
      }
   }

   public NBTTagCompound writeNBT(NBTTagCompound compound) {
      NBTTagList list = new NBTTagList();

      for(int dia : this.dialogsRead) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         nbttagcompound.func_74768_a("Dialog", dia);
         list.func_74742_a(nbttagcompound);
      }

      compound.func_74782_a("DialogData", list);
      return compound;
   }
}
