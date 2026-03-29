package noppes.npcs.client.gui.global;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.constants.EnumPacketType;

public class GuiNPCQuestCategoryEdit extends GuiNPCInterface {
   private GuiScreen parent;
   private String name;
   private int id;

   public GuiNPCQuestCategoryEdit(EntityNPCInterface npc, GuiScreen parent, String name, int id) {
      super(npc);
      this.parent = parent;
      this.name = name;
      this.id = id;
      this.title = "Quest Category";
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addTextField(1, new GuiNpcTextField(1, this, this.field_73886_k, this.field_73880_f / 2 - 40, 100, 140, 20, this.name));
      this.addLabel(new GuiNpcLabel(1, "Title:", this.field_73880_f / 2 - 100 + 4, 105, 16777215));
      this.addButton(2, new GuiNpcButton(2, this.field_73880_f / 2 - 100, 210, 98, 20, "gui.back"));
      this.addButton(3, new GuiNpcButton(3, this.field_73880_f / 2 + 2, 210, 98, 20, "Save"));
   }

   public void func_73863_a(int i, int j, float f) {
      super.func_73863_a(i, j, f);
   }

   protected void func_73875_a(GuiButton guibutton) {
      if (guibutton.field_73741_f == 2) {
         NoppesUtil.openGUI(this.player, this.parent);
         NoppesUtil.sendData(EnumPacketType.QuestCategoriesGet);
      }

      if (guibutton.field_73741_f == 3) {
         this.save();
         NoppesUtil.openGUI(this.player, this.parent);
         NoppesUtil.sendData(EnumPacketType.QuestCategoriesGet);
      }

   }

   public void save() {
      String name = this.getTextField(1).func_73781_b();
      if (!name.trim().isEmpty()) {
         NoppesUtil.sendData(EnumPacketType.QuestCategorySave, name, this.id);
      }
   }

   public void func_73873_v_() {
      this.func_73871_c(0);
   }
}
