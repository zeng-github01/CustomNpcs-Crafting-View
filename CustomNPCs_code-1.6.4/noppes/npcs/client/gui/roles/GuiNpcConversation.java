package noppes.npcs.client.gui.roles;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.SubGuiNpcAvailability;
import noppes.npcs.client.gui.global.GuiNPCQuestSelection;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.GuiSelectionListener;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.roles.JobConversation;

public class GuiNpcConversation extends GuiNPCInterface2 implements ITextfieldListener, GuiSelectionListener {
   private JobConversation job;
   private int slot = -1;
   private GuiNPCQuestSelection questSelection;

   public GuiNpcConversation(EntityNPCInterface npc) {
      super(npc);
      this.job = (JobConversation)npc.jobInterface;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addLabel(new GuiNpcLabel(40, "gui.name", this.guiLeft + 40, this.guiTop + 4, 4210752));
      this.addLabel(new GuiNpcLabel(41, "gui.name", this.guiLeft + 240, this.guiTop + 4, 4210752));
      this.addLabel(new GuiNpcLabel(42, "conversation.delay", this.guiLeft + 164, this.guiTop + 4, 4210752));
      this.addLabel(new GuiNpcLabel(43, "conversation.delay", this.guiLeft + 364, this.guiTop + 4, 4210752));

      for(int i = 0; i < 14; ++i) {
         JobConversation.ConversationLine line = this.job.getLine(i);
         int offset = i >= 7 ? 200 : 0;
         this.addLabel(new GuiNpcLabel(i, "" + i, this.guiLeft + 5 + offset - (i > 9 ? 6 : 0), this.guiTop + 18 + i % 7 * 22, 0));
         this.addTextField(new GuiNpcTextField(i, this, this.field_73886_k, this.guiLeft + 13 + offset, this.guiTop + 13 + i % 7 * 22, 100, 20, line.npc));
         this.addButton(new GuiNpcButton(i, this.guiLeft + 115 + offset, this.guiTop + 13 + i % 7 * 22, 46, 20, "conversation.line"));
         if (i == 13) {
            break;
         }

         this.addTextField(new GuiNpcTextField(i + 14, this, this.field_73886_k, this.guiLeft + 164 + offset, this.guiTop + 13 + i % 7 * 22, 30, 20, line.delay + ""));
         this.getTextField(i + 14).numbersOnly = true;
         this.getTextField(i + 14).setMinMaxDefault(5, 1000, 40);
      }

      this.addLabel(new GuiNpcLabel(50, "conversation.delay", this.guiLeft + 205, this.guiTop + 175, 4210752));
      this.addTextField(new GuiNpcTextField(50, this, this.field_73886_k, this.guiLeft + 270, this.guiTop + 170, 50, 20, this.job.generalDelay + ""));
      this.getTextField(50).numbersOnly = true;
      this.getTextField(50).setMinMaxDefault(10, 1000000, 12000);
      this.addLabel(new GuiNpcLabel(54, "gui.range", this.guiLeft + 205, this.guiTop + 196, 4210752));
      this.addTextField(new GuiNpcTextField(54, this, this.field_73886_k, this.guiLeft + 270, this.guiTop + 191, 50, 20, this.job.range + ""));
      this.getTextField(54).numbersOnly = true;
      this.getTextField(54).setMinMaxDefault(4, 60, 20);
      this.addLabel(new GuiNpcLabel(51, "quest.quest", this.guiLeft + 13, this.guiTop + 175, 4210752));
      String title = this.job.questTitle;
      if (title.isEmpty()) {
         title = "gui.select";
      }

      this.addButton(new GuiNpcButton(51, this.guiLeft + 70, this.guiTop + 170, 100, 20, title));
      this.addButton(new GuiNpcButton(52, this.guiLeft + 171, this.guiTop + 170, 20, 20, "X"));
      this.addLabel(new GuiNpcLabel(53, "availability.name", this.guiLeft + 13, this.guiTop + 196, 4210752));
      this.addButton(new GuiNpcButton(53, this.guiLeft + 110, this.guiTop + 191, 60, 20, "selectServer.edit"));
   }

   protected void func_73875_a(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if (button.field_73741_f >= 0 && button.field_73741_f < 14) {
         this.slot = button.field_73741_f;
         JobConversation.ConversationLine line = this.job.getLine(this.slot);
         this.setSubGui(new SubGuiNpcConversationLine(line.text, line.sound));
      }

      if (button.field_73741_f == 51) {
         NoppesUtil.openGUI(this.player, this.questSelection = new GuiNPCQuestSelection(this.npc, this, this.job.quest));
      }

      if (button.field_73741_f == 52) {
         this.job.quest = -1;
         this.job.questTitle = "";
         this.func_73866_w_();
      }

      if (button.field_73741_f == 53) {
         this.setSubGui(new SubGuiNpcAvailability(this.job.availability));
      }

   }

   public void selected(int ob) {
      this.job.quest = ob;
      this.job.questTitle = this.questSelection.getSelected();
      this.func_73866_w_();
   }

   public void closeSubGui(SubGuiInterface gui) {
      super.closeSubGui(gui);
      if (gui instanceof SubGuiNpcConversationLine) {
         SubGuiNpcConversationLine sub = (SubGuiNpcConversationLine)gui;
         JobConversation.ConversationLine line = this.job.getLine(this.slot);
         line.text = sub.line;
         line.sound = sub.sound;
      }

   }

   public void save() {
      NoppesUtil.sendData(EnumPacketType.MainmenuAdvancedSave, this.npc.advanced.writeToNBT(new NBTTagCompound()));
   }

   public void unFocused(GuiNpcTextField textfield) {
      if (textfield.id >= 0 && textfield.id < 14) {
         JobConversation.ConversationLine line = this.job.getLine(textfield.id);
         line.npc = textfield.func_73781_b();
      }

      if (textfield.id >= 14 && textfield.id < 28) {
         JobConversation.ConversationLine line = this.job.getLine(textfield.id - 14);
         line.delay = textfield.getInteger();
      }

      if (textfield.id == 50) {
         this.job.generalDelay = textfield.getInteger();
      }

      if (textfield.id == 54) {
         this.job.range = textfield.getInteger();
      }

   }
}
