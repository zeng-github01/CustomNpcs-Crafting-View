package noppes.npcs.quests;

import java.util.Vector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.PlayerQuestData;
import noppes.npcs.controllers.QuestData;

public class QuestLocation extends QuestInterface {
   public String location = "";
   public String location2 = "";
   public String location3 = "";

   public void readEntityFromNBT(NBTTagCompound compound) {
      this.location = compound.func_74779_i("QuestLocation");
      this.location2 = compound.func_74779_i("QuestLocation2");
      this.location3 = compound.func_74779_i("QuestLocation3");
   }

   public void writeEntityToNBT(NBTTagCompound compound) {
      compound.func_74778_a("QuestLocation", this.location);
      compound.func_74778_a("QuestLocation2", this.location2);
      compound.func_74778_a("QuestLocation3", this.location3);
   }

   public boolean isCompleted(EntityPlayer player) {
      PlayerQuestData playerdata = PlayerDataController.instance.getPlayerData(player).questData;
      QuestData data = (QuestData)playerdata.activeQuests.get(this.questId);
      return data == null ? false : this.getFound(data, 0);
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
         String found = StatCollector.func_74838_a("quest.found");
         String notfound = StatCollector.func_74838_a("quest.notfound");
         if (!this.location.isEmpty()) {
            vec.add(this.location + ": " + (this.getFound(data, 1) ? found : notfound));
         }

         if (!this.location2.isEmpty()) {
            vec.add(this.location2 + ": " + (this.getFound(data, 2) ? found : notfound));
         }

         if (!this.location3.isEmpty()) {
            vec.add(this.location3 + ": " + (this.getFound(data, 3) ? found : notfound));
         }

         return vec;
      }
   }

   public boolean getFound(QuestData data, int i) {
      if (i == 1) {
         return data.extraData.func_74767_n("LocationFound");
      } else if (i == 2) {
         return data.extraData.func_74767_n("Location2Found");
      } else if (i == 3) {
         return data.extraData.func_74767_n("Location3Found");
      } else if (!this.location.isEmpty() && !data.extraData.func_74767_n("LocationFound")) {
         return false;
      } else if (!this.location2.isEmpty() && !data.extraData.func_74767_n("Location2Found")) {
         return false;
      } else {
         return this.location3.isEmpty() || data.extraData.func_74767_n("Location3Found");
      }
   }

   public void setFound(QuestData data, String location) {
      if (location.equalsIgnoreCase(this.location)) {
         data.extraData.func_74757_a("LocationFound", true);
      }

      if (location.equalsIgnoreCase(this.location2)) {
         data.extraData.func_74757_a("Location2Found", true);
      }

      if (location.equalsIgnoreCase(this.location3)) {
         data.extraData.func_74757_a("Location3Found", true);
      }

   }
}
