package noppes.npcs.client.model.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

public class Model2DRenderer extends ModelRenderer {
   private boolean field_78812_q;
   private int field_78811_r;
   private float x1;
   private float x2;
   private float y1;
   private float y2;
   private int width;
   private int height;
   private float rotationOffsetX;
   private float rotationOffsetY;
   private float scaleX;
   private float scaleY;
   private float thickness;

   public Model2DRenderer(ModelBase par1ModelBase, float x, float y, int width, int height, float textureWidth, float textureHeight) {
      super(par1ModelBase);
      this.scaleX = 1.0F;
      this.scaleY = 1.0F;
      this.thickness = 1.0F;
      this.width = width;
      this.height = height;
      this.field_78801_a = textureWidth;
      this.field_78799_b = textureHeight;
      this.x1 = x / textureWidth;
      this.y1 = y / textureHeight;
      this.x2 = (x + (float)width) / textureWidth;
      this.y2 = (y + (float)height) / textureHeight;
   }

   public Model2DRenderer(ModelBase base, int x, int y, int width, int height) {
      this(base, (float)x, (float)y, width, height, (float)width, (float)height);
   }

   public void func_78785_a(float par1) {
      if (this.field_78806_j && !this.field_78807_k) {
         if (!this.field_78812_q) {
            this.func_78788_d(par1);
         }

         GL11.glPushMatrix();
         this.func_78794_c(par1);
         GL11.glCallList(this.field_78811_r);
         GL11.glPopMatrix();
      }
   }

   public void setRotationOffset(float x, float y) {
      this.rotationOffsetX = x;
      this.rotationOffsetY = y;
   }

   public void setScale(float scale) {
      this.scaleX = scale;
      this.scaleY = scale;
   }

   public void setScale(float x, float y) {
      this.scaleX = x;
      this.scaleY = y;
   }

   public void setThickness(float thickness) {
      this.thickness = thickness;
   }

   @SideOnly(Side.CLIENT)
   private void func_78788_d(float par1) {
      this.field_78811_r = GLAllocation.func_74526_a(1);
      GL11.glNewList(this.field_78811_r, 4864);
      GL11.glScalef(this.scaleX * (float)this.width / (float)this.height, this.scaleY, this.thickness);
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      if (this.field_78809_i) {
         GL11.glTranslatef(0.0F, 0.0F, -1.0F * par1);
         GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
      }

      GL11.glTranslated((double)(this.rotationOffsetX * par1), (double)(this.rotationOffsetY * par1), (double)0.0F);
      ItemRenderer.func_78439_a(Tessellator.field_78398_a, this.x1, this.y1, this.x2, this.y2, this.width, this.height, par1);
      GL11.glEndList();
      this.field_78812_q = true;
   }
}
