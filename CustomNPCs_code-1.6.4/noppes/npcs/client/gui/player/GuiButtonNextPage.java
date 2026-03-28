package noppes.npcs.client.gui.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

class GuiButtonNextPage extends GuiButton {
   private static final ResourceLocation field_110405_a = new ResourceLocation("textures/gui/book.png");
   private final boolean nextPage;

   public GuiButtonNextPage(int par1, int par2, int par3, boolean par4) {
      super(par1, par2, par3, 23, 13, "");
      this.nextPage = par4;
   }

   public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
      if (this.field_73748_h) {
         boolean var4 = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         par1Minecraft.field_71446_o.func_110577_a(field_110405_a);
         int var5 = 0;
         int var6 = 192;
         if (var4) {
            var5 += 23;
         }

         if (!this.nextPage) {
            var6 += 13;
         }

         this.func_73729_b(this.field_73746_c, this.field_73743_d, var5, var6, 23, 13);
      }

   }
}
