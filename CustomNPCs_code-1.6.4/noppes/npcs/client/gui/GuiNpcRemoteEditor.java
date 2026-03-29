package noppes.npcs.client.gui;

import java.util.HashMap;
import java.util.Vector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.entity.Entity;
import net.minecraft.util.StatCollector;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.IScrollData;
import noppes.npcs.constants.EnumPacketType;

public class GuiNpcRemoteEditor extends GuiNPCInterface implements IScrollData {
   private GuiCustomScroll scroll;
   private HashMap data = new HashMap();

   public GuiNpcRemoteEditor() {
      this.xSize = 256;
      this.setBackground("menubg.png");
      NoppesUtil.sendData(EnumPacketType.RemoteNpcsGet);
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      if (this.scroll == null) {
         this.scroll = new GuiCustomScroll(this, 0);
         this.scroll.setSize(165, 208);
         this.scroll.guiLeft = this.guiLeft + 4;
         this.scroll.guiTop = this.guiTop + 4;
      }

      this.addScroll(this.scroll, this.field_73882_e);
      String title = StatCollector.func_74838_a("remote.title");
      int x = (this.xSize - this.field_73886_k.func_78256_a(title)) / 2;
      this.addLabel(new GuiNpcLabel(0, title, this.guiLeft + x, this.guiTop - 8, 16777215));
      this.addButton(new GuiNpcButton(0, this.guiLeft + 170, this.guiTop + 6, 82, 20, "selectServer.edit"));
      this.addButton(new GuiNpcButton(1, this.guiLeft + 170, this.guiTop + 28, 82, 20, "selectWorld.deleteButton"));
      this.addButton(new GuiNpcButton(2, this.guiLeft + 170, this.guiTop + 50, 82, 20, "remote.reset"));
      this.addButton(new GuiNpcButton(4, this.guiLeft + 170, this.guiTop + 72, 82, 20, "remote.tp"));
      this.addButton(new GuiNpcButton(5, this.guiLeft + 170, this.guiTop + 110, 82, 20, "remote.resetall"));
      this.addButton(new GuiNpcButton(3, this.guiLeft + 170, this.guiTop + 132, 82, 20, "remote.freeze"));
   }

   public void func_73878_a(boolean flag, int i) {
      if (flag) {
         NoppesUtil.sendData(EnumPacketType.RemoteDelete, this.data.get(this.scroll.getSelected()));
      }

      NoppesUtil.openGUI(this.player, this);
   }

   protected void func_73875_a(GuiButton guibutton) {
      if (guibutton.field_73741_f == 3) {
         NoppesUtil.sendData(EnumPacketType.RemoteFreeze);
      }

      if (guibutton.field_73741_f == 5) {
         for(int id : this.data.values()) {
            NoppesUtil.sendData(EnumPacketType.RemoteReset, id);
            Entity entity = this.player.field_70170_p.func_73045_a(id);
            if (entity != null && entity instanceof EntityNPCInterface) {
               ((EntityNPCInterface)entity).reset();
            }
         }
      }

      if (this.data.containsKey(this.scroll.getSelected())) {
         if (guibutton.field_73741_f == 0) {
            NoppesUtil.sendData(EnumPacketType.RemoteMainMenu, this.data.get(this.scroll.getSelected()));
         }

         if (guibutton.field_73741_f == 1) {
            GuiYesNo guiyesno = new GuiYesNo(this, "Confirm", StatCollector.func_74838_a("gui.delete"), 0);
            this.field_73882_e.func_71373_a(guiyesno);
         }

         if (guibutton.field_73741_f == 2) {
            NoppesUtil.sendData(EnumPacketType.RemoteReset, this.data.get(this.scroll.getSelected()));
            Entity entity = this.player.field_70170_p.func_73045_a((Integer)this.data.get(this.scroll.getSelected()));
            if (entity != null && entity instanceof EntityNPCInterface) {
               ((EntityNPCInterface)entity).reset();
            }
         }

         if (guibutton.field_73741_f == 4) {
            NoppesUtil.sendData(EnumPacketType.RemoteTpToNpc, this.data.get(this.scroll.getSelected()));
            this.close();
         }

      }
   }

   public void func_73864_a(int i, int j, int k) {
      super.func_73864_a(i, j, k);
      this.scroll.func_73864_a(i, j, k);
   }

   public void func_73869_a(char c, int i) {
      if (i == 1 || i == this.field_73882_e.field_71474_y.field_74315_B.field_74512_d) {
         this.close();
      }

   }

   public void save() {
   }

   public void setData(Vector list, HashMap data) {
      this.scroll.setList(list);
      this.data = data;
   }

   public void setSelected(String selected) {
      this.getButton(3).field_73744_e = selected;
   }
}
