package noppes.npcs.quests;

import java.util.HashMap;
import java.util.Vector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.NBTTags;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.PlayerQuestData;
import noppes.npcs.controllers.QuestData;

public class QuestKill extends QuestInterface {
   public HashMap targets = new HashMap();

   public void readEntityFromNBT(NBTTagCompound compound) {
      this.targets = NBTTags.getStringIntegerMap(compound.func_74761_m("QuestDialogs"));
   }

   public void writeEntityToNBT(NBTTagCompound compound) {
      compound.func_74782_a("QuestDialogs", NBTTags.nbtStringIntegerMap(this.targets));
   }

   public boolean isCompleted(EntityPlayer player) {
      PlayerQuestData playerdata = PlayerDataController.instance.getPlayerData(player).questData;
      QuestData data = (QuestData)playerdata.activeQuests.get(this.questId);
      if (data == null) {
         return false;
      } else {
         HashMap<String, Integer> killed = this.getKilled(data);
         if (killed.size() != this.targets.size()) {
            return false;
         } else {
            for(String entity : killed.keySet()) {
               if (!this.targets.containsKey(entity) || (Integer)this.targets.get(entity) > (Integer)killed.get(entity)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public void handleComplete(EntityPlayer player) {
   }

   public Vector getQuestLogStatus(EntityPlayer player) {
      Vector<String> vec = new Vector();
      PlayerQuestData playerdata = PlayerDataController.instance.getPlayerData(player).questData;
      QuestData data = (QuestData)playerdata.activeQuests.get(this.questId);
      if (data == null) {
         return vec;
      } else {
         HashMap<String, Integer> killed = this.getKilled(data);

         for(String entityName : this.targets.keySet()) {
            int amount = 0;
            if (killed.containsKey(entityName)) {
               amount = (Integer)killed.get(entityName);
            }

            String state = amount + "/" + this.targets.get(entityName);
            vec.add(entityName + " " + state);
         }

         return vec;
      }
   }

   public HashMap getKilled(QuestData data) {
      return NBTTags.getStringIntegerMap(data.extraData.func_74761_m("Killed"));
   }

   public void setKilled(QuestData data, HashMap killed) {
      data.extraData.func_74782_a("Killed", NBTTags.nbtStringIntegerMap(killed));
   }
}
