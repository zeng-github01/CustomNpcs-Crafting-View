package noppes.npcs.client.gui;

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

public class GuiNPCFactionSelection extends GuiNPCInterface implements IScrollData {
   private GuiNPCStringSlot slot;
   private GuiScreen parent;
   private HashMap data = new HashMap();
   private int dialog;
   public GuiSelectionListener listener;

   public GuiNPCFactionSelection(EntityNPCInterface npc, GuiScreen parent, int dialog) {
      super(npc);
      this.drawDefaultBackground = false;
      this.title = "Select Dialog Category";
      this.parent = parent;
      this.dialog = dialog;
      NoppesUtil.sendData(EnumPacketType.FactionsGet);
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
         this.close();
         NoppesUtil.openGUI(this.player, this.parent);
      }

      if (guibutton.field_73741_f == 4) {
         this.doubleClicked();
      }

   }

   public void doubleClicked() {
      if (this.slot.selected != null && !this.slot.selected.isEmpty()) {
         this.dialog = (Integer)this.data.get(this.slot.selected);
         this.close();
         NoppesUtil.openGUI(this.player, this.parent);
      }
   }

   public void save() {
      if (this.dialog >= 0 && this.listener != null) {
         this.listener.selected(this.dialog);
      }

   }

   public void setData(Vector list, HashMap data) {
      this.data = data;
      this.slot.setList(list);
      if (this.dialog >= 0) {
         for(String name : data.keySet()) {
            if ((Integer)data.get(name) == this.dialog) {
               this.slot.selected = name;
            }
         }
      }

   }

   public void setSelected(String selected) {
   }
}
