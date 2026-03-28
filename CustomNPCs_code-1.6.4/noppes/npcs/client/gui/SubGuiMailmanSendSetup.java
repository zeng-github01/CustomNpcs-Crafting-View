package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.global.GuiNPCQuestSelection;
import noppes.npcs.client.gui.player.GuiMailmanWrite;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.GuiSelectionListener;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.controllers.PlayerMail;

public class SubGuiMailmanSendSetup extends SubGuiInterface implements ITextfieldListener, GuiSelectionListener {
   private PlayerMail mail;
   private GuiNPCQuestSelection questSelection;

   public SubGuiMailmanSendSetup(PlayerMail mail, GuiScreen parent) {
      this.parent = parent;
      this.xSize = 256;
      this.setBackground("menubg.png");
      this.mail = mail;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addLabel(new GuiNpcLabel(1, "mailbox.subject", this.guiLeft + 4, this.guiTop + 19, 4210752));
      this.addTextField(new GuiNpcTextField(1, this, this.field_73886_k, this.guiLeft + 60, this.guiTop + 14, 180, 20, this.mail.subject));
      this.addLabel(new GuiNpcLabel(0, "mailbox.sender", this.guiLeft + 4, this.guiTop + 41, 4210752));
      this.addTextField(new GuiNpcTextField(0, this, this.field_73886_k, this.guiLeft + 60, this.guiTop + 36, 180, 20, this.mail.sender));
      this.addButton(new GuiNpcButton(2, this.guiLeft + 29, this.guiTop + 100, "mailbox.write"));
      this.addLabel(new GuiNpcLabel(3, "quest.quest", this.guiLeft + 13, this.guiTop + 135, 4210752));
      String title = this.mail.questTitle;
      if (title.isEmpty()) {
         title = "gui.select";
      }

      this.addButton(new GuiNpcButton(3, this.guiLeft + 70, this.guiTop + 130, 100, 20, title));
      this.addButton(new GuiNpcButton(4, this.guiLeft + 171, this.guiTop + 130, 20, 20, "X"));
      this.addButton(new GuiNpcButton(0, this.guiLeft + 26, this.guiTop + 190, 100, 20, "gui.done"));
      this.addButton(new GuiNpcButton(1, this.guiLeft + 130, this.guiTop + 190, 100, 20, "gui.cancel"));
   }

   public void buttonEvent(GuiButton guibutton) {
      if (guibutton.field_73741_f == 0) {
         this.close();
      }

      if (guibutton.field_73741_f == 1) {
         this.mail.questId = -1;
         this.mail.questTitle = "";
         this.mail.message = new NBTTagCompound();
         this.close();
      }

      if (guibutton.field_73741_f == 2) {
         this.field_73882_e.func_71373_a(new GuiMailmanWrite(this.parent, this.mail.message, true));
      }

      if (guibutton.field_73741_f == 3) {
         NoppesUtil.openGUI(this.player, this.questSelection = new GuiNPCQuestSelection(this.npc, this.parent, this.mail.questId));
         this.questSelection.listener = this;
      }

      if (guibutton.field_73741_f == 4) {
         this.mail.questId = -1;
         this.mail.questTitle = "";
         this.func_73866_w_();
      }

   }

   public void selected(int ob) {
      this.mail.questId = ob;
      this.mail.questTitle = this.questSelection.getSelected();
      this.func_73866_w_();
   }

   public void save() {
   }

   public void unFocused(GuiNpcTextField textField) {
      if (textField.id == 0) {
         this.mail.sender = textField.func_73781_b();
      }

      if (textField.id == 1) {
         this.mail.subject = textField.func_73781_b();
      }

   }
}
