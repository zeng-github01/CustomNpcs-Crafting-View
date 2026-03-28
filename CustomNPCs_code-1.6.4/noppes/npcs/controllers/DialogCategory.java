package noppes.npcs.controllers;

import java.util.HashMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class DialogCategory {
   public int id = -1;
   public String title = "";
   public HashMap dialogs = new HashMap();

   public void readNBT(NBTTagCompound compound) {
      this.id = compound.func_74762_e("Slot");
      this.title = compound.func_74779_i("Title");
      NBTTagList dialogsList = compound.func_74761_m("Dialogs");
      if (dialogsList != null) {
         for(int ii = 0; ii < dialogsList.func_74745_c(); ++ii) {
            Dialog dialog = new Dialog();
            dialog.category = this;
            dialog.readNBT((NBTTagCompound)dialogsList.func_74743_b(ii));
            this.dialogs.put(dialog.id, dialog);
         }
      }

   }

   public NBTTagCompound writeNBT(NBTTagCompound nbtfactions) {
      nbtfactions.func_74768_a("Slot", this.id);
      nbtfactions.func_74778_a("Title", this.title);
      NBTTagList dialogs = new NBTTagList();

      for(Dialog dialog : this.dialogs.values()) {
         dialogs.func_74742_a(dialog.writeToNBT(new NBTTagCompound()));
      }

      nbtfactions.func_74782_a("Dialogs", dialogs);
      return nbtfactions;
   }
}
