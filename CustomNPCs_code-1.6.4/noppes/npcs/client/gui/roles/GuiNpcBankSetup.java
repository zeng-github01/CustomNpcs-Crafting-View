package noppes.npcs.client.gui.roles;

import java.util.HashMap;
import java.util.Vector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiCustomScrollActionListener;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.IScrollData;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.controllers.Bank;
import noppes.npcs.roles.RoleBank;

public class GuiNpcBankSetup extends GuiNPCInterface2 implements IScrollData, GuiCustomScrollActionListener {
   private GuiCustomScroll scroll;
   private HashMap data = new HashMap();
   private RoleBank role;

   public GuiNpcBankSetup(EntityNPCInterface npc) {
      super(npc);
      NoppesUtil.sendData(EnumPacketType.BanksGet);
      this.role = (RoleBank)npc.roleInterface;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.scroll = new GuiCustomScroll(this, 0);
      this.scroll.func_73872_a(this.field_73882_e, 350, 250);
      this.scroll.setSize(200, 152);
      this.scroll.guiLeft = this.guiLeft + 85;
      this.scroll.guiTop = this.guiTop + 20;
   }

   public void func_73863_a(int x, int y, float f) {
      super.func_73863_a(x, y, f);
      this.scroll.func_73863_a(x, y, f);
   }

   protected void func_73875_a(GuiButton guibutton) {
   }

   public void setData(Vector list, HashMap data) {
      String name = null;
      Bank bank = this.role.getBank();
      if (bank != null) {
         name = bank.name;
      }

      this.data = data;
      this.scroll.setList(list);
      if (name != null) {
         this.setSelected(name);
      }

   }

   public void func_73864_a(int i, int j, int k) {
      super.func_73864_a(i, j, k);
      if (k == 0 && this.scroll != null) {
         this.scroll.func_73864_a(i, j, k);
      }

   }

   public void setSelected(String selected) {
      this.scroll.setSelected(selected);
   }

   public void customScrollClicked(int i, int j, int k, GuiCustomScroll guiCustomScroll) {
      if (guiCustomScroll.id == 0) {
         this.role.bankId = (Integer)this.data.get(this.scroll.getSelected());
         this.save();
      }

   }

   public void save() {
      NoppesUtil.sendData(EnumPacketType.MainmenuAdvancedSave, this.npc.advanced.writeToNBT(new NBTTagCompound()));
   }
}
