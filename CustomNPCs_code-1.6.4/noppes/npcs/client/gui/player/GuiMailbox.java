package noppes.npcs.client.gui.player;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiCustomScrollActionListener;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.constants.EnumPlayerPacket;
import noppes.npcs.controllers.PlayerMail;
import noppes.npcs.controllers.PlayerMailData;

public class GuiMailbox extends GuiNPCInterface implements IGuiData, GuiCustomScrollActionListener {
   private GuiCustomScroll scroll;
   private PlayerMailData data;
   private PlayerMail selected;

   public GuiMailbox() {
      this.xSize = 256;
      this.setBackground("menubg.png");
      NoppesUtilPlayer.sendData(EnumPlayerPacket.MailGet);
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      if (this.scroll == null) {
         this.scroll = new GuiCustomScroll(this, 0);
         this.scroll.setSize(165, 186);
         this.scroll.guiLeft = this.guiLeft + 4;
         this.scroll.guiTop = this.guiTop + 4;
      }

      this.addScroll(this.scroll, this.field_73882_e);
      String title = StatCollector.func_74838_a("mailbox.name");
      int x = (this.xSize - this.field_73886_k.func_78256_a(title)) / 2;
      this.addLabel(new GuiNpcLabel(0, title, this.guiLeft + x, this.guiTop - 8, 16777215));
      if (this.selected != null) {
         this.addLabel(new GuiNpcLabel(3, StatCollector.func_74838_a("mailbox.sender") + ":", this.guiLeft + 170, this.guiTop + 6, 4210752));
         this.addLabel(new GuiNpcLabel(1, this.selected.sender, this.guiLeft + 174, this.guiTop + 18, 4210752));
         this.addLabel(new GuiNpcLabel(2, StatCollector.func_74837_a("mailbox.timesend", new Object[]{this.getTimePast()}), this.guiLeft + 174, this.guiTop + 30, 4210752));
      }

      this.addButton(new GuiNpcButton(0, this.guiLeft + 4, this.guiTop + 192, 82, 20, "mailbox.read"));
      this.addButton(new GuiNpcButton(1, this.guiLeft + 88, this.guiTop + 192, 82, 20, "selectWorld.deleteButton"));
      this.getButton(1).field_73742_g = this.selected != null;
   }

   private String getTimePast() {
      if (this.selected.timePast > 86400000L) {
         int days = (int)(this.selected.timePast / 86400000L);
         return days == 1 ? days + " " + StatCollector.func_74838_a("mailbox.day") : days + " " + StatCollector.func_74838_a("mailbox.days");
      } else if (this.selected.timePast > 3600000L) {
         int hours = (int)(this.selected.timePast / 3600000L);
         return hours == 1 ? hours + " " + StatCollector.func_74838_a("mailbox.hour") : hours + " " + StatCollector.func_74838_a("mailbox.hours");
      } else {
         int minutes = (int)(this.selected.timePast / 60000L);
         return minutes == 1 ? minutes + " " + StatCollector.func_74838_a("mailbox.minutes") : minutes + " " + StatCollector.func_74838_a("mailbox.minutes");
      }
   }

   public void func_73878_a(boolean flag, int i) {
      if (flag && this.selected != null) {
         NoppesUtilPlayer.sendData(EnumPlayerPacket.MailDelete, this.selected.time, this.selected.sender);
         this.selected = null;
      }

      NoppesUtil.openGUI(this.player, this);
   }

   protected void func_73875_a(GuiButton guibutton) {
      if (this.scroll.selected >= 0) {
         if (guibutton.field_73741_f == 0) {
            this.field_73882_e.func_71373_a(new GuiMailmanWrite(this, this.selected.message, false));
         }

         if (guibutton.field_73741_f == 1) {
            GuiYesNo guiyesno = new GuiYesNo(this, "Confirm", StatCollector.func_74838_a("gui.delete"), 0);
            this.field_73882_e.func_71373_a(guiyesno);
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

   public void setGuiData(NBTTagCompound compound) {
      PlayerMailData data = new PlayerMailData();
      data.readNBT(compound);
      List<String> list = new ArrayList();

      for(PlayerMail mail : data.playermail) {
         list.add(mail.subject);
      }

      this.data = data;
      this.scroll.clear();
      this.selected = null;
      this.scroll.setUnsortedList(list);
   }

   public void customScrollClicked(int i, int j, int k, GuiCustomScroll guiCustomScroll) {
      this.selected = (PlayerMail)this.data.playermail.get(guiCustomScroll.selected);
      this.func_73866_w_();
      if (this.selected != null && !this.selected.beenRead) {
         this.selected.beenRead = true;
         NoppesUtilPlayer.sendData(EnumPlayerPacket.MailRead, this.selected.time, this.selected.sender);
      }

   }
}
