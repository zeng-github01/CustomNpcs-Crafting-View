package noppes.npcs.controllers;

import java.util.HashMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class QuestCategory {
   public HashMap quests = new HashMap();
   public int id = -1;
   public String title = "";

   public void readNBT(NBTTagCompound nbttagcompound) {
      this.id = nbttagcompound.func_74762_e("Slot");
      this.title = nbttagcompound.func_74779_i("Title");
      NBTTagList dialogsList = nbttagcompound.func_74761_m("Dialogs");
      if (dialogsList != null) {
         for(int ii = 0; ii < dialogsList.func_74745_c(); ++ii) {
            NBTTagCompound nbttagcompound2 = (NBTTagCompound)dialogsList.func_74743_b(ii);
            Quest quest = new Quest();
            quest.readNBT(nbttagcompound2);
            quest.category = this;
            this.quests.put(quest.id, quest);
         }
      }

   }

   public NBTTagCompound writeNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.func_74768_a("Slot", this.id);
      nbttagcompound.func_74778_a("Title", this.title);
      NBTTagList dialogs = new NBTTagList();

      for(int dialogId : this.quests.keySet()) {
         Quest quest = (Quest)this.quests.get(dialogId);
         dialogs.func_74742_a(quest.writeToNBT(new NBTTagCompound()));
      }

      nbttagcompound.func_74782_a("Dialogs", dialogs);
      return nbttagcompound;
   }
}
