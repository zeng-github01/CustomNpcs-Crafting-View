package noppes.npcs.client.model.util;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;
import noppes.npcs.ModelPartConfig;
import org.lwjgl.opengl.GL11;

public class ModelScaleRenderer extends ModelRenderer {
   public boolean field_78812_q;
   public int field_78811_r;
   protected ModelPartConfig config;
   public float x;
   public float y;
   public float z;

   public ModelScaleRenderer(ModelBase par1ModelBase) {
      super(par1ModelBase);
   }

   public ModelScaleRenderer(ModelBase par1ModelBase, int par2, int par3) {
      this(par1ModelBase);
      this.func_78784_a(par2, par3);
   }

   public void setConfig(ModelPartConfig config, float x, float y, float z) {
      this.config = config;
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public void setRotation(ModelRenderer model, float x, float y, float z) {
      model.field_78795_f = x;
      model.field_78796_g = y;
      model.field_78808_h = z;
   }

   public void renderChilderen(float par1) {
      if (this.field_78806_j && !this.field_78807_k) {
         if (!this.field_78812_q) {
            this.func_78788_d(par1);
         }

         GL11.glPushMatrix();
         GL11.glTranslatef(this.x, this.y, this.z);
         GL11.glTranslatef(this.config.transX, this.config.transY, this.config.transZ);
         this.func_78794_c(par1);
         GL11.glScalef(this.config.scaleX, this.config.scaleY, this.config.scaleZ);
         GL11.glCallList(this.field_78811_r);
         if (this.field_78805_m != null) {
            for(int i = 0; i < this.field_78805_m.size(); ++i) {
               ((ModelRenderer)this.field_78805_m.get(i)).func_78785_a(par1);
            }
         }

         GL11.glPopMatrix();
      }
   }

   public void renderChild(float par1, ModelRenderer model) {
      if (this.field_78806_j && !this.field_78807_k) {
         GL11.glPushMatrix();
         GL11.glTranslatef(this.x, this.y, this.z);
         GL11.glTranslatef(this.config.transX, this.config.transY, this.config.transZ);
         this.func_78794_c(par1);
         GL11.glScalef(this.config.scaleX, this.config.scaleY, this.config.scaleZ);
         model.func_78785_a(par1);
         GL11.glPopMatrix();
      }
   }

   public void func_78785_a(float par1) {
      if (this.field_78806_j && !this.field_78807_k) {
         if (!this.field_78812_q) {
            this.func_78788_d(par1);
         }

         GL11.glPushMatrix();
         GL11.glTranslatef(this.x, this.y, this.z);
         GL11.glTranslatef(this.config.transX, this.config.transY, this.config.transZ);
         this.func_78794_c(par1);
         GL11.glScalef(this.config.scaleX, this.config.scaleY, this.config.scaleZ);
         GL11.glCallList(this.field_78811_r);
         if (this.field_78805_m != null) {
            for(int i = 0; i < this.field_78805_m.size(); ++i) {
               ((ModelRenderer)this.field_78805_m.get(i)).func_78785_a(par1);
            }
         }

         GL11.glPopMatrix();
      }
   }

   public void parentRender(float par1) {
      super.func_78785_a(par1);
   }

   public void func_78788_d(float par1) {
      this.field_78811_r = GLAllocation.func_74526_a(1);
      GL11.glNewList(this.field_78811_r, 4864);
      Tessellator tessellator = Tessellator.field_78398_a;

      for(int i = 0; i < this.field_78804_l.size(); ++i) {
         ((ModelBox)this.field_78804_l.get(i)).func_78245_a(tessellator, par1);
      }

      GL11.glEndList();
      this.field_78812_q = true;
   }
}
