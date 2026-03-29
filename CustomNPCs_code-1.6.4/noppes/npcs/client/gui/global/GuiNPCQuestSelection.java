package noppes.npcs.client.gui.global;

import java.util.HashMap;
import java.util.Vector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNPCStringSlot;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiSelectionListener;
import noppes.npcs.client.gui.util.IScrollData;
import noppes.npcs.constants.EnumPacketType;

public class GuiNPCQuestSelection extends GuiNPCInterface implements IScrollData {
   private GuiNPCStringSlot slot;
   private GuiScreen parent;
   private HashMap data;
   private boolean selectCategory = true;
   public GuiSelectionListener listener;
   private int quest;

   public GuiNPCQuestSelection(EntityNPCInterface npc, GuiScreen parent, int quest) {
      super(npc);
      this.drawDefaultBackground = false;
      this.title = "Select Quest Category";
      this.parent = parent;
      this.data = new HashMap();
      this.quest = quest;
      if (quest >= 0) {
         NoppesUtil.sendData(EnumPacketType.QuestsGetFromQuest, quest);
         this.selectCategory = false;
         this.title = "Select Dialog";
      } else {
         NoppesUtil.sendData(EnumPacketType.QuestCategoriesGet, quest);
      }

      if (parent instanceof GuiSelectionListener) {
         this.listener = (GuiSelectionListener)parent;
      }

   }

   public void func_73866_w_() {
      super.func_73866_w_();
      Vector<String> list = new Vector();
      this.slot = new GuiNPCStringSlot(list, this, this.npc, false, 18);
      this.slot.func_77220_a(4, 5);
      this.addButton(2, new GuiNpcButton(2, this.field_73880_f / 2 - 100, this.field_73881_g - 41, 98, 20, "gui.back"));
      this.addButton(4, new GuiNpcButton(4, this.field_73880_f / 2 + 2, this.field_73881_g - 41, 98, 20, "mco.template.button.select"));
   }

   public void func_73863_a(int i, int j, float f) {
      this.slot.func_77211_a(i, j, f);
      super.func_73863_a(i, j, f);
   }

   protected void func_73875_a(GuiButton guibutton) {
      if (guibutton.field_73741_f == 2) {
         if (this.selectCategory) {
            this.close();
            NoppesUtil.openGUI(this.player, this.parent);
         } else {
            this.title = "Select Dialog Category";
            this.selectCategory = true;
            NoppesUtil.sendData(EnumPacketType.QuestCategoriesGet, this.quest);
         }
      }

      if (guibutton.field_73741_f == 4) {
         if (this.slot.selected == null || this.slot.selected.isEmpty()) {
            return;
         }

         this.doubleClicked();
      }

   }

   public String getSelected() {
      return this.slot.selected;
   }

   public void doubleClicked() {
      if (this.slot.selected != null && !this.slot.selected.isEmpty()) {
         if (this.selectCategory) {
            this.selectCategory = false;
            this.title = "Select Quest";
            NoppesUtil.sendData(EnumPacketType.QuestsGet, this.data.get(this.slot.selected));
         } else {
            this.quest = (Integer)this.data.get(this.slot.selected);
            this.close();
            NoppesUtil.openGUI(this.player, this.parent);
         }

      }
   }

   public void save() {
      if (this.quest >= 0 && this.listener != null) {
         this.listener.selected(this.quest);
      }

   }

   public void setData(Vector list, HashMap data) {
      this.data = data;
      this.slot.setList(list);
      if (this.quest >= 0) {
         for(String name : data.keySet()) {
            if ((Integer)data.get(name) == this.quest) {
               this.slot.selected = name;
            }
         }
      }

   }

   public void setSelected(String selected) {
   }
}
