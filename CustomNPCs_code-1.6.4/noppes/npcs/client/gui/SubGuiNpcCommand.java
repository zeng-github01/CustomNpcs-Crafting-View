package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.client.gui.util.SubGuiInterface;

public class SubGuiNpcCommand extends SubGuiInterface implements ITextfieldListener {
   public String command;

   public SubGuiNpcCommand(String command) {
      this.command = command;
      this.setBackground("menubg.png");
      this.xSize = 256;
      this.ySize = 216;
      this.closeOnEsc = true;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addTextField(new GuiNpcTextField(4, this, this.field_73886_k, this.guiLeft + 4, this.guiTop + 84, 248, 20, this.command));
      this.getTextField(4).func_73804_f(32767);
      this.addLabel(new GuiNpcLabel(4, "advMode.command", this.guiLeft + 4, this.guiTop + 110, 4210752));
      this.addLabel(new GuiNpcLabel(5, "advMode.nearestPlayer", this.guiLeft + 4, this.guiTop + 125, 4210752));
      this.addLabel(new GuiNpcLabel(6, "advMode.randomPlayer", this.guiLeft + 4, this.guiTop + 140, 4210752));
      this.addLabel(new GuiNpcLabel(7, "advMode.allPlayers", this.guiLeft + 4, this.guiTop + 155, 4210752));
      this.addLabel(new GuiNpcLabel(8, "dialogcommandoptionplayer", this.guiLeft + 4, this.guiTop + 170, 4210752));
      this.addButton(new GuiNpcButton(66, this.guiLeft + 82, this.guiTop + 190, 98, 20, "gui.done"));
   }

   protected void func_73875_a(GuiButton guibutton) {
      if (guibutton.field_73741_f == 66) {
         this.close();
      }

   }

   public void unFocused(GuiNpcTextField textfield) {
      if (textfield.id == 4) {
         this.command = textfield.func_73781_b();
      }

   }
}
