package noppes.npcs.client.gui;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcTextArea;
import noppes.npcs.client.gui.util.SubGuiInterface;

public class SubGuiNpcTextArea extends SubGuiInterface {
   public String text;

   public SubGuiNpcTextArea(String text) {
      this.text = text;
      this.setBackground("menubg.png");
      this.xSize = 256;
      this.ySize = 216;
      this.closeOnEsc = true;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addTextField(new GuiNpcTextArea(2, this, this.field_73886_k, this.guiLeft + 4, this.guiTop + 4, 176, 182, this.text));
      this.field_73887_h.add(new GuiNpcButton(0, this.guiLeft + 196, this.guiTop + 160, 56, 20, "Close"));
      this.field_73887_h.add(new GuiNpcButton(102, this.guiLeft + 196, this.guiTop + 20, 56, 20, "Clear"));
      this.field_73887_h.add(new GuiNpcButton(101, this.guiLeft + 196, this.guiTop + 43, 56, 20, "Paste"));
      this.field_73887_h.add(new GuiNpcButton(100, this.guiLeft + 196, this.guiTop + 66, 56, 20, "Copy"));
   }

   public void setClipboardContents(String aString) {
      StringSelection stringSelection = new StringSelection(aString);
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      clipboard.setContents(stringSelection, new ClipboardOwner() {
         public void lostOwnership(Clipboard arg0, Transferable arg1) {
         }
      });
   }

   public String getClipboardContents() {
      String result = "";
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      Transferable contents = clipboard.getContents((Object)null);
      boolean hasTransferableText = contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
      if (hasTransferableText) {
         try {
            result = (String)contents.getTransferData(DataFlavor.stringFlavor);
         } catch (UnsupportedFlavorException ex) {
            System.err.println(ex);
            ex.printStackTrace();
         } catch (IOException ex) {
            System.err.println(ex);
            ex.printStackTrace();
         }
      }

      return result;
   }

   public void close() {
      this.text = this.getTextField(2).func_73781_b();
      super.close();
   }

   public void buttonEvent(GuiButton guibutton) {
      if (guibutton.field_73741_f == 100) {
         this.setClipboardContents(this.getTextField(2).func_73781_b());
      }

      if (guibutton.field_73741_f == 101) {
         this.getTextField(2).func_73782_a(this.getClipboardContents());
      }

      if (guibutton.field_73741_f == 102) {
         this.getTextField(2).func_73782_a("");
      }

      if (guibutton.field_73741_f == 0) {
         this.close();
      }

   }
}
