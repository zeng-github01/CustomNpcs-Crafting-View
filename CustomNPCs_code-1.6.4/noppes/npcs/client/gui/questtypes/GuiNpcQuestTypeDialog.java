package noppes.npcs.client.gui.questtypes;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.GuiNPCDialogSelection;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiSelectionListener;
import noppes.npcs.controllers.Quest;
import noppes.npcs.quests.QuestDialog;

public class GuiNpcQuestTypeDialog extends GuiNPCInterface implements GuiSelectionListener {
   private GuiScreen parent;
   private QuestDialog quest;
   private int selectedSlot;

   public GuiNpcQuestTypeDialog(EntityNPCInterface npc, Quest q, GuiScreen parent) {
      super(npc);
      this.parent = parent;
      this.title = "Quest Dialog Setup";
      this.quest = (QuestDialog)q.questInterface;
   }

   public void func_73866_w_() {
      super.func_73866_w_();

      for(int i = 0; i < 3; ++i) {
         String title = "dialog.selectoption";
         this.addButton(i + 9, new GuiNpcButton(i + 9, this.field_73880_f / 2 - 100, 55 + i * 22, 20, 20, "X"));
         this.addButton(i + 3, new GuiNpcButton(i + 3, this.field_73880_f / 2 - 78, 55 + i * 22, 140, 20, title));
      }

      this.addButton(0, new GuiNpcButton(0, this.field_73880_f / 2 - 100, 215, 98, 20, "gui.back"));
   }

   public void func_73863_a(int i, int j, float f) {
      super.func_73863_a(i, j, f);
   }

   protected void func_73875_a(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if (guibutton.field_73741_f == 0) {
         NoppesUtil.openGUI(this.player, this.parent);
      }

      if (guibutton.field_73741_f >= 3 && guibutton.field_73741_f < 9) {
         this.close();
         this.selectedSlot = guibutton.field_73741_f - 3;
         int id = -1;
         if (this.quest.dialogs.containsKey(this.selectedSlot)) {
            id = (Integer)this.quest.dialogs.get(this.selectedSlot);
         }

         NoppesUtil.openGUI(this.player, new GuiNPCDialogSelection(this.npc, this, id));
      }

      if (guibutton.field_73741_f >= 9 && guibutton.field_73741_f < 15) {
         int slot = guibutton.field_73741_f - 9;
         this.quest.dialogs.remove(slot);
         this.save();
         this.func_73866_w_();
      }

   }

   public void save() {
   }

   public void selected(int id) {
      this.quest.dialogs.put(this.selectedSlot, id);
   }
}
