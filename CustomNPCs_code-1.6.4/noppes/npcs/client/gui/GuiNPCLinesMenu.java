package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;

public class GuiNPCLinesMenu extends GuiNPCInterface2 {
   public GuiNPCLinesMenu(EntityNPCInterface npc) {
      super(npc);
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addButton(new GuiNpcButton(0, this.guiLeft + 85, this.guiTop + 20, "World Lines"));
      this.addButton(new GuiNpcButton(1, this.guiLeft + 85, this.guiTop + 43, "Attack Lines"));
      this.addButton(new GuiNpcButton(2, this.guiLeft + 85, this.guiTop + 66, "Interact Lines"));
      this.addButton(new GuiNpcButton(5, this.guiLeft + 85, this.guiTop + 89, "Killed Lines"));
   }

   protected void func_73875_a(GuiButton guibutton) {
      if (guibutton.field_73741_f == 0) {
         NoppesUtil.openGUI(this.player, new GuiNPCLinesEdit(this.npc, this.npc.advanced.worldLines));
      }

      if (guibutton.field_73741_f == 1) {
         NoppesUtil.openGUI(this.player, new GuiNPCLinesEdit(this.npc, this.npc.advanced.attackLines));
      }

      if (guibutton.field_73741_f == 2) {
         NoppesUtil.openGUI(this.player, new GuiNPCLinesEdit(this.npc, this.npc.advanced.interactLines));
      }

      if (guibutton.field_73741_f == 5) {
         NoppesUtil.openGUI(this.player, new GuiNPCLinesEdit(this.npc, this.npc.advanced.killedLines));
      }

   }

   public void save() {
   }
}
