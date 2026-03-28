package noppes.npcs.client.renderer;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.client.model.ModelCarpentryBench;
import org.lwjgl.opengl.GL11;

public class BlockCarpentryBenchRenderer extends TileEntitySpecialRenderer {
   private ModelCarpentryBench model = new ModelCarpentryBench();
   private static final ResourceLocation field_110631_g = new ResourceLocation("customnpcs", "textures/misc/CarpentryBench.png");

   public void func_76894_a(TileEntity var1, double var2, double var4, double var6, float var8) {
      int meta = var1.field_70331_k.func_72805_g(var1.field_70329_l, var1.field_70330_m, var1.field_70327_n);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)var2 + 0.5F, (float)var4 + 1.4F, (float)var6 + 0.5F);
      GL11.glScalef(0.95F, 0.95F, 0.95F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef((float)(90 * meta), 0.0F, 1.0F, 0.0F);
      this.func_110628_a(field_110631_g);
      this.model.func_78088_a((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glPopMatrix();
   }
}
