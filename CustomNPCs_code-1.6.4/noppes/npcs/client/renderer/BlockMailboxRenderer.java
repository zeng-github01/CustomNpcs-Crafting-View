package noppes.npcs.client.renderer;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import noppes.npcs.CustomItems;
import noppes.npcs.client.model.ModelMailboxUS;
import noppes.npcs.client.model.ModelMailboxWow;
import org.lwjgl.opengl.GL11;

public class BlockMailboxRenderer extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler {
   private ModelMailboxUS model = new ModelMailboxUS();
   private ModelMailboxWow model2 = new ModelMailboxWow();
   private static final ResourceLocation text1 = new ResourceLocation("customnpcs", "textures/misc/mailbox1.png");
   private static final ResourceLocation text2 = new ResourceLocation("customnpcs", "textures/misc/mailbox2.png");

   public void func_76894_a(TileEntity var1, double var2, double var4, double var6, float var8) {
      int meta = var1.field_70331_k.func_72805_g(var1.field_70329_l, var1.field_70330_m, var1.field_70327_n) | 4;
      int type = var1.field_70331_k.func_72805_g(var1.field_70329_l, var1.field_70330_m, var1.field_70327_n) >> 2;
      GL11.glPushMatrix();
      GL11.glTranslatef((float)var2 + 0.5F, (float)var4 + 1.5F, (float)var6 + 0.5F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef((float)(90 * meta), 0.0F, 1.0F, 0.0F);
      if (type == 0) {
         this.func_110628_a(text1);
         this.model.func_78088_a((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      }

      if (type == 1) {
         this.func_110628_a(text2);
         this.model2.func_78088_a((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      }

      GL11.glPopMatrix();
   }

   public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 0.8F, 0.0F);
      GL11.glScalef(0.9F, 0.9F, 0.9F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
      if (metadata == 0) {
         this.func_110628_a(text1);
         this.model.func_78088_a((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      }

      if (metadata == 1) {
         this.func_110628_a(text2);
         this.model2.func_78088_a((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      }

      GL11.glPopMatrix();
   }

   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
      return false;
   }

   public boolean shouldRender3DInInventory() {
      return true;
   }

   public int getRenderId() {
      return CustomItems.mailbox.func_71857_b();
   }
}
