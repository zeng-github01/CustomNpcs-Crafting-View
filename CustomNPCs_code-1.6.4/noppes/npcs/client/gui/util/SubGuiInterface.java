package noppes.npcs.client.gui.util;

import net.minecraft.client.gui.GuiScreen;

public class SubGuiInterface extends GuiNPCInterface {
   public GuiScreen parent;

   public void save() {
   }

   public void close() {
      if (this.parent instanceof GuiNPCInterface2) {
         ((GuiNPCInterface2)this.parent).closeSubGui(this);
      }

      if (this.parent instanceof GuiContainerNPCInterface2) {
         ((GuiContainerNPCInterface2)this.parent).closeSubGui(this);
      }

      if (this.parent instanceof ISubGuiListener) {
         ((ISubGuiListener)this.parent).subGuiClosed(this);
      }

   }

   protected void changeSubGui(SubGuiInterface gui) {
      if (this.parent instanceof GuiNPCInterface2) {
         ((GuiNPCInterface2)this.parent).setSubGui(gui);
      }

   }
}
