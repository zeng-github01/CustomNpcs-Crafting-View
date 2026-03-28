package noppes.npcs.controllers;

import java.util.Vector;
import net.minecraft.entity.player.EntityPlayer;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.constants.EnumQuestRepeat;
import noppes.npcs.constants.EnumQuestType;
import noppes.npcs.quests.QuestDialog;

public class PlayerQuestController {
   public static boolean hasActiveQuests(EntityPlayer player) {
      PlayerQuestData data = PlayerDataController.instance.getPlayerData(player).questData;
      return !data.activeQuests.isEmpty();
   }

   public static boolean isQuestActive(EntityPlayer player, int quest) {
      PlayerQuestData data = PlayerDataController.instance.getPlayerData(player).questData;
      return data.activeQuests.containsKey(quest);
   }

   public static boolean isQuestFinished(EntityPlayer player, int questid) {
      PlayerQuestData data = PlayerDataController.instance.getPlayerData(player).questData;
      return data.finishedQuests.containsKey(questid);
   }

   public static void addActiveQuest(Quest quest, EntityPlayer player) {
      PlayerQuestData data = PlayerDataController.instance.getPlayerData(player).questData;
      if (canQuestBeAccepted(quest, player)) {
         data.activeQuests.put(quest.id, new QuestData(quest));
         NoppesUtilServer.sendData(player, EnumPacketType.Message, "quest.newquest", quest.title);
         NoppesUtilServer.sendData(player, EnumPacketType.Chat, "quest.newquest", ": ", quest.title);
      }

   }

   public static void setQuestFinished(Quest quest, EntityPlayer player) {
      PlayerData playerdata = PlayerDataController.instance.getPlayerData(player);
      PlayerQuestData data = playerdata.questData;
      data.activeQuests.remove(quest.id);
      data.finishedQuests.put(quest.id, player.field_70170_p.func_72820_D());
      if (quest.repeat != EnumQuestRepeat.None && quest.type == EnumQuestType.Dialog) {
         QuestDialog questdialog = (QuestDialog)quest.questInterface;

         for(int dialog : questdialog.dialogs.values()) {
            playerdata.dialogData.dialogsRead.remove(dialog);
         }
      }

   }

   public static boolean canQuestBeAccepted(Quest quest, EntityPlayer player) {
      if (quest == null) {
         return false;
      } else {
         PlayerQuestData data = PlayerDataController.instance.getPlayerData(player).questData;
         if (data.activeQuests.containsKey(quest.id)) {
            return false;
         } else if (data.finishedQuests.containsKey(quest.id) && quest.repeat != EnumQuestRepeat.Repeatable) {
            if (quest.repeat == EnumQuestRepeat.None) {
               return false;
            } else {
               long questTime = (Long)data.finishedQuests.get(quest.id);
               long worldTime = player.field_70170_p.func_72820_D();
               if (worldTime < questTime) {
                  return true;
               } else {
                  long timePassed = worldTime - questTime;
                  if (quest.repeat == EnumQuestRepeat.Daily) {
                     return timePassed >= 24000L;
                  } else if (quest.repeat == EnumQuestRepeat.Weekly) {
                     return timePassed >= 168000L;
                  } else {
                     return false;
                  }
               }
            }
         } else {
            return true;
         }
      }
   }

   public static Vector getActiveQuests(EntityPlayer player) {
      Vector<Quest> quests = new Vector();
      PlayerQuestData data = PlayerDataController.instance.getPlayerData(player).questData;

      for(QuestData questdata : data.activeQuests.values()) {
         if (questdata.quest != null) {
            quests.add(questdata.quest);
         }
      }

      return quests;
   }
}
