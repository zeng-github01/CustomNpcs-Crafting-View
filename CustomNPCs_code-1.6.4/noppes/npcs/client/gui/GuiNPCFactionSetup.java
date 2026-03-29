package noppes.npcs.client.gui;

import java.util.HashMap;
import java.util.Vector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiCustomScrollActionListener;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.IScrollData;
import noppes.npcs.constants.EnumPacketType;

public class GuiNPCFactionSetup extends GuiNPCInterface2 implements IScrollData, GuiCustomScrollActionListener {
   private GuiCustomScroll scrollFactions;
   private HashMap data = new HashMap();

   public GuiNPCFactionSetup(EntityNPCInterface npc) {
      super(npc);
      NoppesUtil.sendData(EnumPacketType.FactionsGet);
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addLabel(new GuiNpcLabel(0, "faction.attackHostile", this.guiLeft + 4, this.guiTop + 25, 4210752));
      this.addButton(new GuiNpcButton(0, this.guiLeft + 144, this.guiTop + 20, 40, 20, new String[]{"gui.no", "gui.yes"}, this.npc.advanced.attackOtherFactions ? 1 : 0));
      this.addLabel(new GuiNpcLabel(1, "faction.defend", this.guiLeft + 4, this.guiTop + 47, 4210752));
      this.addButton(new GuiNpcButton(1, this.guiLeft + 144, this.guiTop + 42, 40, 20, new String[]{"gui.no", "gui.yes"}, this.npc.advanced.defendFaction ? 1 : 0));
      this.addLabel(new GuiNpcLabel(12, "faction.ondeath", this.guiLeft + 4, this.guiTop + 69, 4210752));
      this.addButton(new GuiNpcButton(12, this.guiLeft + 90, this.guiTop + 64, 80, 20, "faction.points"));
      this.scrollFactions = new GuiCustomScroll(this, 0);
      this.scrollFactions.func_73872_a(this.field_73882_e, 350, 250);
      this.scrollFactions.setSize(180, 200);
      this.scrollFactions.guiLeft = this.guiLeft + 200;
      this.scrollFactions.guiTop = this.guiTop + 4;
   }

   public void func_73863_a(int x, int y, float f) {
      super.func_73863_a(x, y, f);
      this.scrollFactions.func_73863_a(x, y, f);
   }

   public void buttonEvent(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if (guibutton.field_73741_f == 0) {
         this.npc.advanced.attackOtherFactions = button.getValue() == 1;
      }

      if (guibutton.field_73741_f == 1) {
         this.npc.advanced.defendFaction = button.getValue() == 1;
      }

      if (button.field_73741_f == 12) {
         this.setSubGui(new SubGuiNpcFactionOptions(this.npc.advanced.factions));
      }

   }

   public void setData(Vector list, HashMap data) {
      String name = this.npc.getFaction().name;
      this.data = data;
      this.scrollFactions.setList(list);
      if (name != null) {
         this.setSelected(name);
      }

   }

   public void func_73864_a(int i, int j, int k) {
      super.func_73864_a(i, j, k);
      if (k == 0 && this.scrollFactions != null) {
         this.scrollFactions.func_73864_a(i, j, k);
      }

   }

   public void setSelected(String selected) {
      this.scrollFactions.setSelected(selected);
   }

   public void customScrollClicked(int i, int j, int k, GuiCustomScroll guiCustomScroll) {
      if (guiCustomScroll.id == 0) {
         NoppesUtil.sendData(EnumPacketType.FactionSet, this.data.get(this.scrollFactions.getSelected()));
      }

   }

   public void save() {
      NoppesUtil.sendData(EnumPacketType.MainmenuAdvancedSave, this.npc.advanced.writeToNBT(new NBTTagCompound()));
   }
}
