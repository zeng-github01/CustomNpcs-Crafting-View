package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.Entity;
import noppes.npcs.client.controllers.CloneController;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;

public class GuiNpcMobSpawnerAdd extends GuiNPCInterface {
   private Entity toClone;

   public GuiNpcMobSpawnerAdd() {
      this.toClone = CloneController.toClone;
      this.setBackground("menubg.png");
      this.xSize = 256;
      this.ySize = 216;
      CloneController.toClone = null;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      String name = this.toClone.func_70023_ak();
      this.addLabel(new GuiNpcLabel(0, "Save as", this.guiLeft + 4, this.guiTop + 6, 4210752));
      this.addTextField(new GuiNpcTextField(0, this, this.field_73886_k, this.guiLeft + 4, this.guiTop + 18, 200, 20, name));
      this.addLabel(new GuiNpcLabel(1, "Tab", this.guiLeft + 10, this.guiTop + 50, 4210752));
      this.addButton(new GuiNpcButton(2, this.guiLeft + 40, this.guiTop + 45, 20, 20, new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"}, 0));
      this.addButton(new GuiNpcButton(0, this.guiLeft + 4, this.guiTop + 70, 80, 20, "gui.save"));
      this.addButton(new GuiNpcButton(1, this.guiLeft + 86, this.guiTop + 70, 80, 20, "gui.cancel"));
   }

   public void buttonEvent(GuiButton guibutton) {
      if (guibutton.field_73741_f == 0) {
         String name = this.getTextField(0).func_73781_b();
         if (name.isEmpty()) {
            return;
         }

         CloneController.addClone(this.toClone, name, this.getButton(2).getValue() + 1);
         this.close();
      }

      if (guibutton.field_73741_f == 1) {
         this.close();
      }

   }

   public void save() {
   }
}
