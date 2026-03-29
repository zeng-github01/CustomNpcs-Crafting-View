package noppes.npcs.client.gui;

import java.util.HashMap;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiSelectionListener;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.controllers.DialogOption;

public class GuiNPCDialogNpcOptions extends GuiNPCInterface2 implements GuiSelectionListener, IGuiData {
   private GuiScreen parent;
   private HashMap data = new HashMap();
   private int selectedSlot;

   public GuiNPCDialogNpcOptions(EntityNPCInterface npc, GuiScreen parent) {
      super(npc);
      this.parent = parent;
      this.drawDefaultBackground = true;
      NoppesUtil.sendData(EnumPacketType.DialogNpcGet);
   }

   public void func_73866_w_() {
      super.func_73866_w_();

      for(int i = 0; i < 12; ++i) {
         int offset = i >= 6 ? 200 : 0;
         this.addButton(new GuiNpcButton(i + 20, this.guiLeft + 20 + offset, this.guiTop + 13 + i % 6 * 22, 20, 20, "X"));
         this.addLabel(new GuiNpcLabel(i, "" + i, this.guiLeft + 6 + offset, this.guiTop + 18 + i % 6 * 22, 0));
         String title = "dialog.selectoption";
         if (this.data.containsKey(i)) {
            title = ((DialogOption)this.data.get(i)).title;
         }

         this.addButton(new GuiNpcButton(i, this.guiLeft + 44 + offset, this.guiTop + 13 + i % 6 * 22, 140, 20, title));
      }

   }

   public void func_73863_a(int i, int j, float f) {
      super.func_73863_a(i, j, f);
   }

   protected void func_73875_a(GuiButton guibutton) {
      if (guibutton.field_73741_f == 1) {
         NoppesUtil.openGUI(this.player, this.parent);
      }

      if (guibutton.field_73741_f >= 0 && guibutton.field_73741_f < 20) {
         this.close();
         this.selectedSlot = guibutton.field_73741_f;
         int id = -1;
         if (this.data.containsKey(guibutton.field_73741_f)) {
            id = ((DialogOption)this.data.get(guibutton.field_73741_f)).dialogId;
         }

         NoppesUtil.openGUI(this.player, new GuiNPCDialogSelection(this.npc, this, id));
      }

      if (guibutton.field_73741_f >= 20 && guibutton.field_73741_f < 40) {
         int slot = guibutton.field_73741_f - 20;
         this.data.remove(slot);
         NoppesUtil.sendData(EnumPacketType.DialogNpcRemove, slot);
         this.func_73866_w_();
      }

   }

   public void save() {
   }

   public void selected(int id) {
      NoppesUtil.sendData(EnumPacketType.DialogNpcSet, this.selectedSlot, id);
   }

   public void setGuiData(NBTTagCompound compound) {
      int pos = compound.func_74762_e("Position");
      DialogOption dialog = new DialogOption();
      dialog.readNBT(compound);
      this.data.put(pos, dialog);
      this.func_73866_w_();
   }
}
