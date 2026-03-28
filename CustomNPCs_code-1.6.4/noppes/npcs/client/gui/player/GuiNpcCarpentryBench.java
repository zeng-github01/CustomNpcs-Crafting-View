package noppes.npcs.client.gui.player;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface;
import noppes.npcs.containers.ContainerCarpentryBench;
import org.lwjgl.opengl.GL11;

public class GuiNpcCarpentryBench extends GuiContainerNPCInterface {
   private final ResourceLocation resource = new ResourceLocation("customnpcs", "textures/gui/anvil.png");
   private ContainerCarpentryBench container;

   public GuiNpcCarpentryBench(ContainerCarpentryBench container) {
      super((EntityNPCInterface)null, container);
      this.container = container;
      this.title = "";
      this.field_73885_j = false;
      this.closeOnEsc = true;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
   }

   public void func_73875_a(GuiButton guibutton) {
      super.func_73875_a(guibutton);
   }

   protected void func_74185_a(float f, int i, int j) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_73882_e.field_71446_o.func_110577_a(this.resource);
      int l = (this.field_73880_f - this.field_74194_b) / 2;
      int i1 = (this.field_73881_g - this.field_74195_c) / 2;
      this.func_73729_b(l, i1, 0, 0, this.field_74194_b, this.field_74195_c);
      super.func_74185_a(f, i, j);
   }

   public void save() {
   }
}
