package noppes.npcs.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.client.gui.global.GuiNPCManageQuest;
import noppes.npcs.controllers.Quest;
import noppes.npcs.quests.QuestItem;

public class ContainerNpcQuestTypeItem extends Container {
   public ContainerNpcQuestTypeItem(EntityPlayer player) {
      Quest quest = NoppesUtilServer.getEditingQuest(player);
      if (player.field_70170_p.field_72995_K) {
         quest = GuiNPCManageQuest.quest;
      }

      for(int i1 = 0; i1 < 3; ++i1) {
         this.func_75146_a(new Slot(((QuestItem)quest.questInterface).items, i1, 44, 39 + i1 * 25));
      }

      for(int i1 = 0; i1 < 3; ++i1) {
         for(int l1 = 0; l1 < 9; ++l1) {
            this.func_75146_a(new Slot(player.field_71071_by, l1 + i1 * 9 + 9, 8 + l1 * 18, 113 + i1 * 18));
         }
      }

      for(int j1 = 0; j1 < 9; ++j1) {
         this.func_75146_a(new Slot(player.field_71071_by, j1, 8 + j1 * 18, 171));
      }

   }

   public ItemStack func_82846_b(EntityPlayer par1EntityPlayer, int i) {
      return null;
   }

   public boolean func_75145_c(EntityPlayer entityplayer) {
      return true;
   }
}
