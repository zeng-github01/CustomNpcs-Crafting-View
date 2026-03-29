package noppes.npcs.controllers;

import java.util.HashMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.constants.EnumQuestCompletion;
import noppes.npcs.constants.EnumQuestType;
import noppes.npcs.quests.QuestInterface;

public class PlayerQuestData implements IPlayerData {
   public HashMap activeQuests = new HashMap();
   public HashMap finishedQuests = new HashMap();

   public void readNBT(NBTTagCompound mainCompound) {
      if (mainCompound != null) {
         NBTTagCompound compound = mainCompound.func_74775_l("QuestData");
         NBTTagList list = compound.func_74761_m("CompletedQuests");
         if (list != null) {
            HashMap<Integer, Long> finishedQuests = new HashMap();

            for(int i = 0; i < list.func_74745_c(); ++i) {
               NBTTagCompound nbttagcompound = (NBTTagCompound)list.func_74743_b(i);
               finishedQuests.put(nbttagcompound.func_74762_e("Quest"), nbttagcompound.func_74763_f("Date"));
            }

            this.finishedQuests = finishedQuests;
         }

         NBTTagList list2 = compound.func_74761_m("ActiveQuests");
         if (list2 != null) {
            HashMap<Integer, QuestData> activeQuests = new HashMap();

            for(int i = 0; i < list2.func_74745_c(); ++i) {
               NBTTagCompound nbttagcompound = (NBTTagCompound)list2.func_74743_b(i);
               int id = nbttagcompound.func_74762_e("Quest");
               Quest quest = (Quest)QuestController.instance.quests.get(id);
               if (quest != null) {
                  QuestData data = new QuestData(quest);
                  data.readEntityFromNBT(nbttagcompound);
                  activeQuests.put(id, data);
               }
            }

            this.activeQuests = activeQuests;
         }

      }
   }

   public NBTTagCompound writeNBT(NBTTagCompound maincompound) {
      NBTTagCompound compound = new NBTTagCompound();
      NBTTagList list = new NBTTagList();

      for(int quest : this.finishedQuests.keySet()) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         nbttagcompound.func_74768_a("Quest", quest);
         nbttagcompound.func_74772_a("Date", (Long)this.finishedQuests.get(quest));
         list.func_74742_a(nbttagcompound);
      }

      compound.func_74782_a("CompletedQuests", list);
      NBTTagList list2 = new NBTTagList();

      for(int quest : this.activeQuests.keySet()) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         nbttagcompound.func_74768_a("Quest", quest);
         ((QuestData)this.activeQuests.get(quest)).writeEntityToNBT(nbttagcompound);
         list2.func_74742_a(nbttagcompound);
      }

      compound.func_74782_a("ActiveQuests", list2);
      maincompound.func_74766_a("QuestData", compound);
      return maincompound;
   }

   public QuestData getQuestCompletion(EntityPlayer player, EntityNPCInterface npc) {
      for(QuestData data : this.activeQuests.values()) {
         Quest quest = data.quest;
         if (quest != null && quest.completion == EnumQuestCompletion.Npc && quest.completerNpc.equals(npc.func_70023_ak()) && quest.questInterface.isCompleted(player)) {
            return data;
         }
      }

      return null;
   }

   public boolean checkQuestCompletion(EntityPlayer player, EnumQuestType type) {
      boolean bo = false;

      for(QuestData data : this.activeQuests.values()) {
         if (data.quest.type == type || type == null) {
            QuestInterface inter = data.quest.questInterface;
            if (inter.isCompleted(player)) {
               if (!data.isCompleted) {
                  if (!data.quest.complete(player, data)) {
                     NoppesUtilServer.sendData(player, EnumPacketType.Message, "quest.completed", data.quest.title);
                     NoppesUtilServer.sendData(player, EnumPacketType.Chat, "quest.completed", ": ", data.quest.title);
                  }

                  data.isCompleted = true;
                  bo = true;
               }
            } else {
               data.isCompleted = false;
            }
         }
      }

      return bo;
   }
}
