package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.controllers.Faction;

public class SubGuiNpcFactionPoints extends SubGuiInterface implements ITextfieldListener {
   private Faction faction;

   public SubGuiNpcFactionPoints(Faction faction) {
      this.faction = faction;
      this.setBackground("menubg.png");
      this.xSize = 256;
      this.ySize = 216;
      this.closeOnEsc = true;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addTextField(new GuiNpcTextField(2, this, this.field_73886_k, this.guiLeft + 48, this.guiTop + 48, 70, 20, this.faction.defaultPoints + ""));
      this.addLabel(new GuiNpcLabel(2, "faction.default", this.guiLeft + 8, this.guiTop + 48 + 5, 4210752));
      this.getTextField(2).func_73804_f(6);
      this.getTextField(2).numbersOnly = true;
      this.addLabel(new GuiNpcLabel(3, "faction.unfriendly", this.guiLeft + 48, this.guiTop + 72, 16711680));
      this.addTextField(new GuiNpcTextField(3, this, this.field_73886_k, this.guiLeft + 48, this.guiTop + 82, 70, 20, this.faction.neutralPoints + ""));
      this.addLabel(new GuiNpcLabel(4, "faction.neutral", this.guiLeft + 48, this.guiTop + 104, 15924992));
      this.addTextField(new GuiNpcTextField(4, this, this.field_73886_k, this.guiLeft + 48, this.guiTop + 114, 70, 20, this.faction.friendlyPoints + ""));
      this.addLabel(new GuiNpcLabel(5, "faction.friendly", this.guiLeft + 48, this.guiTop + 136, 65280));
      this.getTextField(3).numbersOnly = true;
      this.getTextField(4).numbersOnly = true;
      this.getLabel(3).center(70);
      this.getLabel(4).center(70);
      this.getLabel(5).center(70);
      this.addButton(new GuiNpcButton(66, this.guiLeft + 20, this.guiTop + 192, 90, 20, "gui.done"));
   }

   public void unFocused(GuiNpcTextField textfield) {
      if (textfield.id == 2) {
         this.faction.defaultPoints = textfield.getInteger();
      } else if (textfield.id == 3) {
         this.faction.neutralPoints = textfield.getInteger();
      } else if (textfield.id == 4) {
         this.faction.friendlyPoints = textfield.getInteger();
      }

   }

   protected void func_73875_a(GuiButton guibutton) {
      if (guibutton.field_73741_f == 66) {
         this.close();
      }

   }
}
