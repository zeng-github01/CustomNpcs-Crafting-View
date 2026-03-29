package noppes.npcs.quests;

import java.util.HashMap;
import java.util.Vector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.NBTTags;
import noppes.npcs.controllers.Dialog;
import noppes.npcs.controllers.DialogController;
import noppes.npcs.controllers.PlayerDataController;

public class QuestDialog extends QuestInterface {
   public HashMap dialogs = new HashMap();

   public void readEntityFromNBT(NBTTagCompound compound) {
      this.dialogs = NBTTags.getIntegerIntegerMap(compound.func_74761_m("QuestDialogs"));
   }

   public void writeEntityToNBT(NBTTagCompound compound) {
      compound.func_74782_a("QuestDialogs", NBTTags.nbtIntegerIntegerMap(this.dialogs));
   }

   public boolean isCompleted(EntityPlayer player) {
      for(int dialogId : this.dialogs.values()) {
         if (!PlayerDataController.instance.getPlayerData(player).dialogData.dialogsRead.contains(dialogId)) {
            return false;
         }
      }

      return true;
   }

   public void handleComplete(EntityPlayer player) {
   }

   public Vector getQuestLogStatus(EntityPlayer player) {
      Vector<String> vec = new Vector();

      for(int dialogId : this.dialogs.values()) {
         Dialog dialog = (Dialog)DialogController.instance.dialogs.get(dialogId);
         if (dialog != null) {
            String title = dialog.title;
            if (PlayerDataController.instance.getPlayerData(player).dialogData.dialogsRead.contains(dialogId)) {
               title = title + " (read)";
            } else {
               title = title + " (unread)";
            }

            vec.add(title);
         }
      }

      return vec;
   }
}
