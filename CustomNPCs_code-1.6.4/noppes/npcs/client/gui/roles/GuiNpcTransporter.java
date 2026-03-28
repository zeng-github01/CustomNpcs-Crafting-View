package noppes.npcs.client.gui.roles;

import java.util.HashMap;
import java.util.Vector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.mainmenu.GuiNpcAdvanced;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNPCStringSlot;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.client.gui.util.IScrollData;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.controllers.TransportLocation;

public class GuiNpcTransporter extends GuiNPCInterface implements IScrollData, IGuiData {
   public TransportLocation location = new TransportLocation();
   private GuiNPCStringSlot slot;
   private HashMap data = new HashMap();

   public GuiNpcTransporter(EntityNPCInterface npc) {
      super(npc);
      this.title = "Npc Transporter";
      NoppesUtil.sendData(EnumPacketType.TransportCategoriesGet);
      NoppesUtil.sendData(EnumPacketType.TransportGetLocation);
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      Vector<String> list = new Vector();
      list.addAll(this.data.keySet());
      this.slot = new GuiNPCStringSlot(list, this, this.npc, false, 18);
      this.slot.func_77220_a(4, 5);
      this.addLabel(new GuiNpcLabel(0, "Name:", this.guiLeft - 30, this.field_73881_g - 49, 16777215));
      this.addTextField(new GuiNpcTextField(0, this, this.field_73886_k, this.guiLeft + 1, this.field_73881_g - 54, 198, 20, this.location.name));
      this.addButton(new GuiNpcButton(0, this.guiLeft, this.field_73881_g - 31, new String[]{"Available when discovered", "Available from the start", "Available after interaction"}, this.location.type));
      this.addButton(new GuiNpcButton(4, this.guiLeft + 210, this.field_73881_g - 42, 98, 20, "Back"));
   }

   public void func_73863_a(int i, int j, float f) {
      this.slot.func_77211_a(i, j, f);
      super.func_73863_a(i, j, f);
   }

   protected void func_73875_a(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if (button.field_73741_f == 0) {
         this.location.type = button.getValue();
      }

      if (button.field_73741_f == 4) {
         this.close();
         NoppesUtil.openGUI(this.player, new GuiNpcAdvanced(this.npc));
      }

   }

   public void save() {
      if (this.slot.selected != null && !this.slot.selected.isEmpty()) {
         String name = this.getTextField(0).func_73781_b();
         if (!name.isEmpty()) {
            this.location.name = name;
         }

         this.location.npcX = this.npc.startPos[0];
         this.location.npcY = this.npc.startPos[1];
         this.location.npcZ = this.npc.startPos[2];
         this.location.posX = this.player.field_70165_t;
         this.location.posY = this.player.field_70163_u;
         this.location.posZ = this.player.field_70161_v;
         this.location.dimension = this.player.field_71093_bK;
         int cat = (Integer)this.data.get(this.slot.selected);
         NoppesUtil.sendData(EnumPacketType.TransportSave, cat, this.location.writeNBT());
      }
   }

   public void func_73873_v_() {
   }

   public void setData(Vector list, HashMap data) {
      this.data = data;
      this.slot.setList(list);
   }

   public void setSelected(String selected) {
      this.slot.selected = selected;
   }

   public void setGuiData(NBTTagCompound compound) {
      TransportLocation loc = new TransportLocation();
      loc.readNBT(compound);
      this.location = loc;
      System.out.println(this.location.name);
      this.func_73866_w_();
   }
}
