package noppes.npcs.client.gui.roles;

import net.minecraft.client.gui.GuiButton;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.GuiNpcMusicSelection;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.client.gui.util.SubGuiInterface;

public class SubGuiNpcConversationLine extends SubGuiInterface implements ITextfieldListener {
   public String line;
   public String sound;
   private GuiNpcMusicSelection gui;

   public SubGuiNpcConversationLine(String line, String sound) {
      this.line = line;
      this.sound = sound;
      this.setBackground("menubg.png");
      this.xSize = 256;
      this.ySize = 216;
      this.closeOnEsc = true;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addLabel(new GuiNpcLabel(0, "Line", this.guiLeft + 4, this.guiTop + 10, 4210752));
      this.addTextField(new GuiNpcTextField(0, this, this.field_73886_k, this.guiLeft + 4, this.guiTop + 22, 200, 20, this.line));
      this.addButton(new GuiNpcButton(1, this.guiLeft + 4, this.guiTop + 55, 90, 20, "Select Sound"));
      this.addButton(new GuiNpcButton(2, this.guiLeft + 96, this.guiTop + 55, 20, 20, "X"));
      this.addLabel(new GuiNpcLabel(1, this.sound, this.guiLeft + 4, this.guiTop + 81, 4210752));
      this.addButton(new GuiNpcButton(66, this.guiLeft + 162, this.guiTop + 192, 90, 20, "gui.done"));
   }

   public void unFocused(GuiNpcTextField textfield) {
      this.line = textfield.func_73781_b();
   }

   public void elementClicked() {
      this.sound = this.gui.getSelected();
   }

   protected void func_73875_a(GuiButton guibutton) {
      if (guibutton.field_73741_f == 1) {
         NoppesUtil.openGUI(this.player, this.gui = new GuiNpcMusicSelection(this.npc, this.parent, this.sound));
      }

      if (guibutton.field_73741_f == 2) {
         this.sound = "";
         this.func_73866_w_();
      }

      if (guibutton.field_73741_f == 66) {
         this.close();
      }

   }
}
