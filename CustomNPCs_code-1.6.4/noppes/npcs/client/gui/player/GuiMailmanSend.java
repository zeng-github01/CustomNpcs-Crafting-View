package noppes.npcs.client.gui.player;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.IGuiClose;
import noppes.npcs.client.gui.util.IGuiError;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.constants.EnumPlayerPacket;
import noppes.npcs.controllers.PlayerMail;

public class GuiMailmanSend extends GuiNPCInterface implements ITextfieldListener, IGuiError, IGuiClose {
   private GuiNpcLabel error;
   private PlayerMail mail;
   private String username = "";
   private NBTTagCompound compound = new NBTTagCompound();

   public GuiMailmanSend() {
      this.xSize = 256;
      this.setBackground("menubg.png");
      this.mail = new PlayerMail();
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addLabel(new GuiNpcLabel(0, "mailbox.username", this.guiLeft + 4, this.guiTop + 19, 4210752));
      this.addTextField(new GuiNpcTextField(0, this, this.field_73886_k, this.guiLeft + 60, this.guiTop + 14, 180, 20, this.username));
      this.addLabel(new GuiNpcLabel(1, "mailbox.subject", this.guiLeft + 4, this.guiTop + 49, 4210752));
      this.addTextField(new GuiNpcTextField(1, this, this.field_73886_k, this.guiLeft + 60, this.guiTop + 44, 180, 20, this.mail.subject));
      this.addButton(new GuiNpcButton(2, this.guiLeft + 29, this.guiTop + 100, "mailbox.write"));
      this.addLabel(this.error = new GuiNpcLabel(2, "", this.guiLeft + 4, this.guiTop + 160, 16711680));
      this.addButton(new GuiNpcButton(0, this.guiLeft + 26, this.guiTop + 190, 100, 20, "mailbox.send"));
      this.addButton(new GuiNpcButton(1, this.guiLeft + 130, this.guiTop + 190, 100, 20, "gui.cancel"));
   }

   public void buttonEvent(GuiButton guibutton) {
      if (guibutton.field_73741_f == 0) {
         this.mail.sender = this.player.field_71092_bJ;
         this.mail.message = this.compound;
         NoppesUtilPlayer.sendData(EnumPlayerPacket.MailSend, this.username, this.mail.writeNBT());
      }

      if (guibutton.field_73741_f == 1) {
         this.field_73882_e.func_71373_a((GuiScreen)null);
      }

      if (guibutton.field_73741_f == 2) {
         this.field_73882_e.func_71373_a(new GuiMailmanWrite(this, this.compound, true));
      }

   }

   public void save() {
   }

   public void unFocused(GuiNpcTextField textField) {
      if (textField.id == 0) {
         this.username = textField.func_73781_b();
      }

      if (textField.id == 1) {
         this.mail.subject = textField.func_73781_b();
      }

   }

   public void setError(int i, NBTTagCompound data) {
      if (i == 0) {
         this.error.label = StatCollector.func_74838_a("mailbox.errorUsername");
      }

      if (i == 1) {
         this.error.label = StatCollector.func_74838_a("mailbox.errorSubject");
      }

   }

   public void setClose(int i, NBTTagCompound data) {
      this.player.func_71035_c(StatCollector.func_74837_a("mailbox.succes", new Object[]{data.func_74779_i("username")}));
   }
}
