package noppes.npcs.client.gui.mainmenu;

import net.minecraft.client.gui.GuiButton;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.global.GuiNpcManagePlayerData;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.constants.EnumGuiType;

public class GuiNPCGlobalMainMenu extends GuiNPCInterface2 {
   public GuiNPCGlobalMainMenu(EntityNPCInterface npc) {
      super(npc, 5);
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addButton(new GuiNpcButton(2, this.guiLeft + 85, this.guiTop + 20, "global.banks"));
      this.addButton(new GuiNpcButton(3, this.guiLeft + 85, this.guiTop + 42, "menu.factions"));
      this.addButton(new GuiNpcButton(4, this.guiLeft + 85, this.guiTop + 64, "dialog.dialogs"));
      this.addButton(new GuiNpcButton(11, this.guiLeft + 85, this.guiTop + 86, "quest.quests"));
      this.addButton(new GuiNpcButton(12, this.guiLeft + 85, this.guiTop + 108, "global.transport"));
      this.addButton(new GuiNpcButton(13, this.guiLeft + 85, this.guiTop + 130, "global.playerdata"));
      this.addButton(new GuiNpcButton(14, this.guiLeft + 85, this.guiTop + 152, "global.recipes"));
   }

   protected void func_73875_a(GuiButton guibutton) {
      if (guibutton.field_73741_f == 11) {
         NoppesUtil.requestOpenGUI(EnumGuiType.ManageQuests);
      }

      if (guibutton.field_73741_f == 2) {
         NoppesUtil.requestOpenGUI(EnumGuiType.ManageBanks);
      }

      if (guibutton.field_73741_f == 3) {
         NoppesUtil.requestOpenGUI(EnumGuiType.ManageFactions);
      }

      if (guibutton.field_73741_f == 4) {
         NoppesUtil.requestOpenGUI(EnumGuiType.ManageDialogs);
      }

      if (guibutton.field_73741_f == 12) {
         NoppesUtil.requestOpenGUI(EnumGuiType.ManageTransport);
      }

      if (guibutton.field_73741_f == 13) {
         NoppesUtil.openGUI(this.player, new GuiNpcManagePlayerData(this.npc, this));
      }

      if (guibutton.field_73741_f == 14) {
         NoppesUtil.requestOpenGUI(EnumGuiType.ManageRecipes, 4, 0, 0);
      }

   }

   public void save() {
   }
}
