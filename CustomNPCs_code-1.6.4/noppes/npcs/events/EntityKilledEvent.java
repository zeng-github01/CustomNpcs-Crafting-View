package noppes.npcs.events;

import java.util.HashMap;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import noppes.npcs.CustomItems;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.constants.EnumQuestType;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.PlayerQuestData;
import noppes.npcs.controllers.QuestData;
import noppes.npcs.items.ItemExcalibur;
import noppes.npcs.quests.QuestKill;

public class EntityKilledEvent {
   @ForgeSubscribe
   public void invoke(LivingDeathEvent event) {
      if (!event.entityLiving.field_70170_p.field_72995_K) {
         this.entityDied(event.entityLiving, event);
      }

   }

   private void entityDied(EntityLivingBase entity, LivingDeathEvent hurt) {
      if (hurt.source.func_76346_g() != null && hurt.source.func_76346_g() instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)hurt.source.func_76346_g();
         this.doExcalibur(player, entity);
         this.doQuest(player, entity);
         if (entity instanceof EntityNPCInterface) {
            this.doFactionPoints(player, (EntityNPCInterface)entity);
         }

      }
   }

   private void doExcalibur(EntityPlayer player, EntityLivingBase entity) {
      ItemStack item = player.func_71045_bC();
      if (item != null && item.func_77973_b() == CustomItems.excalibur) {
         NoppesUtilServer.sendData(player, EnumPacketType.PlayMusic, "Failboat103 - Excalibuuur");
         player.func_71035_c("<" + item.func_77973_b().func_77635_s() + "> " + ItemExcalibur.quotes[player.func_70681_au().nextInt(ItemExcalibur.quotes.length)]);
      }
   }

   private void doFactionPoints(EntityPlayer player, EntityNPCInterface npc) {
      npc.advanced.factions.addPoints(player);
   }

   private void doQuest(EntityPlayer player, EntityLivingBase entity) {
      PlayerQuestData playerdata = PlayerDataController.instance.getPlayerData(player).questData;
      boolean change = false;
      String entityName = EntityList.func_75621_b(entity);

      for(QuestData data : playerdata.activeQuests.values()) {
         if (data.quest.type == EnumQuestType.Kill) {
            String name = entityName;
            QuestKill quest = (QuestKill)data.quest.questInterface;
            if (quest.targets.containsKey(entity.func_70023_ak())) {
               name = entity.func_70023_ak();
            } else if (!quest.targets.containsKey(entityName)) {
               continue;
            }

            HashMap<String, Integer> killed = quest.getKilled(data);
            if (!killed.containsKey(name) || (Integer)killed.get(name) < (Integer)quest.targets.get(name)) {
               int amount = 0;
               if (killed.containsKey(name)) {
                  amount = (Integer)killed.get(name);
               }

               killed.put(name, amount + 1);
               quest.setKilled(data, killed);
               change = true;
            }
         }
      }

      if (change) {
         playerdata.checkQuestCompletion(player, EnumQuestType.Kill);
      }
   }
}
