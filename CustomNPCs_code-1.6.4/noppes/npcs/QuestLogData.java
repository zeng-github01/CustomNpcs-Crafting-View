package noppes.npcs;

import java.util.HashMap;
import java.util.Vector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.controllers.PlayerQuestController;
import noppes.npcs.controllers.Quest;

public class QuestLogData {
   public HashMap categories = new HashMap();
   public String selectedQuest = "";
   public String selectedCategory = "";
   public HashMap questText = new HashMap();
   public HashMap questStatus = new HashMap();

   public NBTTagCompound writeNBT() {
      NBTTagCompound compound = new NBTTagCompound();
      compound.func_74782_a("Categories", NBTTags.nbtVectorMap(this.categories));
      compound.func_74782_a("Logs", NBTTags.nbtStringStringMap(this.questText));
      compound.func_74782_a("Status", NBTTags.nbtVectorMap(this.questStatus));
      return compound;
   }

   public void readNBT(NBTTagCompound compound) {
      this.categories = NBTTags.getVectorMap(compound.func_74761_m("Categories"));
      this.questText = NBTTags.getStringStringMap(compound.func_74761_m("Logs"));
      this.questStatus = NBTTags.getVectorMap(compound.func_74761_m("Status"));
   }

   public void setData(EntityPlayer player) {
      for(Quest quest : PlayerQuestController.getActiveQuests(player)) {
         String category = quest.category.title;
         if (!this.categories.containsKey(category)) {
            this.categories.put(category, new Vector());
         }

         Vector<String> list = (Vector)this.categories.get(category);
         list.add(quest.title);
         this.questText.put(category + ":" + quest.title, quest.logText);
         this.questStatus.put(category + ":" + quest.title, quest.questInterface.getQuestLogStatus(player));
      }

   }

   public boolean hasSelectedQuest() {
      return !this.selectedQuest.isEmpty();
   }

   public String getQuestText() {
      return (String)this.questText.get(this.selectedCategory + ":" + this.selectedQuest);
   }

   public Vector getQuestStatus() {
      return (Vector)this.questStatus.get(this.selectedCategory + ":" + this.selectedQuest);
   }
}
