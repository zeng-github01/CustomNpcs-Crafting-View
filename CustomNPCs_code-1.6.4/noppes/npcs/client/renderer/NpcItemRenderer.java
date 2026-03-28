package noppes.npcs.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import noppes.npcs.items.ItemRenderInterface;
import org.lwjgl.opengl.GL11;

public class NpcItemRenderer implements IItemRenderer {
   private static final ResourceLocation field_110930_b = new ResourceLocation("textures/misc/enchanted_item_glint.png");

   public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
      return type == ItemRenderType.EQUIPPED;
   }

   public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
      return false;
   }

   public void renderItem(IItemRenderer.ItemRenderType type, ItemStack par2ItemStack, Object... data) {
      if (par2ItemStack.func_77973_b() instanceof ItemRenderInterface) {
         GL11.glTranslatef(0.9375F, 0.0625F, 0.0F);
         GL11.glRotatef(-335.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
         ((ItemRenderInterface)par2ItemStack.func_77973_b()).renderSpecial();
         this.renderItem3d((EntityLivingBase)data[1], par2ItemStack);
      }
   }

   public void renderItem3d(EntityLivingBase par1EntityLiving, ItemStack par2ItemStack) {
      Minecraft mc = Minecraft.func_71410_x();
      TextureManager texturemanager = mc.func_110434_K();
      int par3 = 0;
      texturemanager.func_110577_a(texturemanager.func_130087_a(par2ItemStack.func_94608_d()));
      Tessellator tessellator = Tessellator.field_78398_a;
      Icon icon = par1EntityLiving.func_70620_b(par2ItemStack, par3);
      if (icon != null) {
         float f = icon.func_94209_e();
         float f1 = icon.func_94212_f();
         float f2 = icon.func_94206_g();
         float f3 = icon.func_94210_h();
         float f4 = 0.0F;
         float f5 = 0.3F;
         GL11.glEnable(32826);
         GL11.glTranslatef(-f4, -f5, 0.0F);
         float f6 = 1.5F;
         GL11.glScalef(f6, f6, f6);
         GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
         GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
         ItemRenderer.func_78439_a(tessellator, f1, f2, f, f3, icon.func_94211_a(), icon.func_94216_b(), 0.0625F);
         if (par2ItemStack.hasEffect(par3)) {
            GL11.glDepthFunc(514);
            GL11.glDisable(2896);
            texturemanager.func_110577_a(field_110930_b);
            GL11.glEnable(3042);
            GL11.glBlendFunc(768, 1);
            float f7 = 0.76F;
            GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
            GL11.glMatrixMode(5890);
            GL11.glPushMatrix();
            float f8 = 0.125F;
            GL11.glScalef(f8, f8, f8);
            float f9 = (float)(Minecraft.func_71386_F() % 3000L) / 3000.0F * 8.0F;
            GL11.glTranslatef(f9, 0.0F, 0.0F);
            GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
            ItemRenderer.func_78439_a(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(f8, f8, f8);
            f9 = (float)(Minecraft.func_71386_F() % 4873L) / 4873.0F * 8.0F;
            GL11.glTranslatef(-f9, 0.0F, 0.0F);
            GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
            ItemRenderer.func_78439_a(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
            GL11.glDisable(3042);
            GL11.glEnable(2896);
            GL11.glDepthFunc(515);
         }

         GL11.glDisable(32826);
      }
   }
}
