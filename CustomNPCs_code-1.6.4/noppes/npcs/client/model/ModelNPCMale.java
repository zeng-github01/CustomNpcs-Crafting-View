package noppes.npcs.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import noppes.npcs.CustomNpcs;
import org.lwjgl.opengl.GL11;

public class ModelNPCMale extends ModelBase {
   public ModelRenderer bipedHead;
   public ModelRenderer bipedHeadwear;
   public ModelRenderer bipedBody;
   public ModelRenderer bipedRightArm;
   public ModelRenderer bipedLeftArm;
   public ModelRenderer bipedRightLeg;
   public ModelRenderer bipedLeftLeg;
   public ModelRenderer bipedEars;
   public ModelRenderer bipedCloak;
   public int heldItemLeft;
   public int heldItemRight;
   public boolean isSneak;
   public boolean aimedBow;
   public boolean isDancing;
   public boolean isSleeping;
   public float animationTick;
   public float dancingTicks;

   public ModelNPCMale(float f) {
      this.init(f, 0.0F);
   }

   public ModelNPCMale(int width, int height, float f) {
      this.field_78089_u = height;
      this.field_78090_t = width;
      this.init(f, 0.0F);
   }

   public void func_78086_a(EntityLivingBase par1EntityLiving, float f6, float f5, float par9) {
      this.animationTick += par9;
      this.dancingTicks = (float)CustomNpcs.ticks / 3.978873F;
   }

   public void init(float f, float f1) {
      this.heldItemLeft = 0;
      this.heldItemRight = 0;
      this.isSneak = false;
      this.aimedBow = false;
      this.bipedCloak = new ModelRenderer(this, 0, 0);
      this.bipedCloak.field_78799_b = 32.0F;
      this.bipedCloak.func_78790_a(-5.0F, 0.0F, -1.0F, 10, 16, 1, f);
      this.bipedEars = new ModelRenderer(this, 24, 0);
      this.bipedEars.func_78790_a(-3.0F, -6.0F, -1.0F, 6, 6, 1, f);
      this.bipedHead = new ModelRenderer(this, 0, 0);
      this.bipedHead.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, f);
      this.bipedHead.func_78793_a(0.0F, 0.0F + f1, 0.0F);
      this.bipedHeadwear = new ModelRenderer(this, 32, 0);
      this.bipedHeadwear.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, f + 0.5F);
      this.bipedHeadwear.func_78793_a(0.0F, 0.0F + f1, 0.0F);
      this.bipedBody = new ModelRenderer(this, 16, 16);
      this.bipedBody.func_78790_a(-4.0F, 0.0F, -2.0F, 8, 12, 4, f);
      this.bipedBody.func_78793_a(0.0F, 0.0F + f1, 0.0F);
      this.bipedRightArm = new ModelRenderer(this, 40, 16);
      this.bipedRightArm.func_78790_a(-3.0F, -2.0F, -2.0F, 4, 12, 4, f);
      this.bipedRightArm.func_78793_a(-5.0F, 2.0F + f1, 0.0F);
      this.bipedLeftArm = new ModelRenderer(this, 40, 16);
      this.bipedLeftArm.field_78809_i = true;
      this.bipedLeftArm.func_78790_a(-1.0F, -2.0F, -2.0F, 4, 12, 4, f);
      this.bipedLeftArm.func_78793_a(5.0F, 2.0F + f1, 0.0F);
      this.bipedRightLeg = new ModelRenderer(this, 0, 16);
      this.bipedRightLeg.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, f);
      this.bipedRightLeg.func_78793_a(-2.0F, 12.0F + f1, 0.0F);
      this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
      this.bipedLeftLeg.field_78809_i = true;
      this.bipedLeftLeg.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, f);
      this.bipedLeftLeg.func_78793_a(2.0F, 12.0F + f1, 0.0F);
   }

   public void func_78088_a(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
      this.setRotationAngles(par2, par3, par4, par5, par6, par7);
      if (!this.isDancing) {
         this.bipedHead.func_78785_a(par7);
         this.bipedBody.func_78785_a(par7);
         this.bipedRightArm.func_78785_a(par7);
         this.bipedLeftArm.func_78785_a(par7);
         this.bipedRightLeg.func_78785_a(par7);
         this.bipedLeftLeg.func_78785_a(par7);
         this.bipedHeadwear.func_78785_a(par7);
      } else {
         this.renderHead(par1Entity, par7);
         this.renderArms(par1Entity, par7);
         this.renderBody(par1Entity, par7);
         this.renderLegs(par1Entity, par7);
      }

   }

   public void renderHead(Entity entityliving, float par7) {
      if (this.isDancing) {
         GL11.glPushMatrix();
         GL11.glTranslatef((float)Math.sin((double)this.dancingTicks) * 0.075F, (float)Math.abs(Math.cos((double)this.dancingTicks)) * 0.125F - 0.02F, (float)(-Math.abs(Math.cos((double)this.dancingTicks))) * 0.075F);
         this.bipedHead.func_78785_a(par7);
         this.bipedHeadwear.func_78785_a(par7);
         GL11.glPopMatrix();
      } else {
         this.bipedHead.func_78785_a(par7);
         this.bipedHeadwear.func_78785_a(par7);
      }

   }

   public void renderLeftArm(Entity entityliving, float par7) {
      if (this.isDancing) {
         GL11.glPushMatrix();
         GL11.glTranslatef((float)Math.sin((double)this.dancingTicks) * 0.025F, (float)Math.abs(Math.cos((double)this.dancingTicks)) * 0.125F - 0.02F, 0.0F);
         this.bipedLeftArm.func_78785_a(par7);
         GL11.glPopMatrix();
      } else {
         this.bipedLeftArm.func_78785_a(par7);
      }

   }

   public void renderArms(Entity entity, float par7) {
      this.renderLeftArm(entity, par7);
      this.renderRightArm(entity, par7);
   }

   public void renderRightArm(Entity entityliving, float par7) {
      if (this.isDancing) {
         GL11.glPushMatrix();
         GL11.glTranslatef((float)Math.sin((double)this.dancingTicks) * 0.025F, (float)Math.abs(Math.cos((double)this.dancingTicks)) * 0.125F - 0.02F, 0.0F);
         this.bipedRightArm.func_78785_a(par7);
         GL11.glPopMatrix();
      } else {
         this.bipedRightArm.func_78785_a(par7);
      }

   }

   public void renderBody(Entity entityliving, float par7) {
      if (this.isDancing) {
         GL11.glPushMatrix();
         GL11.glTranslatef((float)Math.sin((double)this.dancingTicks) * 0.015F, 0.0F, 0.0F);
         this.bipedBody.func_78785_a(par7);
         GL11.glPopMatrix();
      } else {
         this.bipedBody.func_78785_a(par7);
      }

   }

   public void renderLegs(Entity entityliving, float par7) {
      this.bipedRightLeg.func_78785_a(par7);
      this.bipedLeftLeg.func_78785_a(par7);
   }

   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6) {
      this.bipedHead.field_78796_g = par4 / (180F / (float)Math.PI);
      this.bipedHead.field_78795_f = par5 / (180F / (float)Math.PI);
      this.bipedHeadwear.field_78796_g = this.bipedHead.field_78796_g;
      this.bipedHeadwear.field_78795_f = this.bipedHead.field_78795_f;
      this.bipedRightArm.field_78795_f = MathHelper.func_76134_b(par1 * 0.6662F + (float)Math.PI) * 2.0F * par2 * 0.5F;
      this.bipedLeftArm.field_78795_f = MathHelper.func_76134_b(par1 * 0.6662F) * 2.0F * par2 * 0.5F;
      this.bipedRightArm.field_78808_h = 0.0F;
      this.bipedLeftArm.field_78808_h = 0.0F;
      this.bipedRightLeg.field_78795_f = MathHelper.func_76134_b(par1 * 0.6662F) * 1.4F * par2;
      this.bipedLeftLeg.field_78795_f = MathHelper.func_76134_b(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
      this.bipedRightLeg.field_78796_g = 0.0F;
      this.bipedLeftLeg.field_78796_g = 0.0F;
      if (this.field_78093_q) {
         ModelRenderer var10000 = this.bipedRightArm;
         var10000.field_78795_f += (-(float)Math.PI / 5F);
         var10000 = this.bipedLeftArm;
         var10000.field_78795_f += (-(float)Math.PI / 5F);
         this.bipedRightLeg.field_78795_f = -1.2566371F;
         this.bipedLeftLeg.field_78795_f = -1.2566371F;
         this.bipedRightLeg.field_78796_g = ((float)Math.PI / 10F);
         this.bipedLeftLeg.field_78796_g = (-(float)Math.PI / 10F);
      }

      if (this.heldItemLeft != 0) {
         this.bipedLeftArm.field_78795_f = this.bipedLeftArm.field_78795_f * 0.5F - ((float)Math.PI / 10F) * (float)this.heldItemLeft;
      }

      if (this.heldItemRight != 0) {
         this.bipedRightArm.field_78795_f = this.bipedRightArm.field_78795_f * 0.5F - ((float)Math.PI / 10F) * (float)this.heldItemRight;
      }

      this.bipedRightArm.field_78796_g = 0.0F;
      this.bipedLeftArm.field_78796_g = 0.0F;
      if (this.field_78095_p > -9990.0F) {
         float f = this.field_78095_p;
         this.bipedBody.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f) * (float)Math.PI * 2.0F) * 0.2F;
         this.bipedRightArm.field_78798_e = MathHelper.func_76126_a(this.bipedBody.field_78796_g) * 5.0F;
         this.bipedRightArm.field_78800_c = -MathHelper.func_76134_b(this.bipedBody.field_78796_g) * 5.0F;
         this.bipedLeftArm.field_78798_e = -MathHelper.func_76126_a(this.bipedBody.field_78796_g) * 5.0F;
         this.bipedLeftArm.field_78800_c = MathHelper.func_76134_b(this.bipedBody.field_78796_g) * 5.0F;
         ModelRenderer var17 = this.bipedRightArm;
         var17.field_78796_g += this.bipedBody.field_78796_g;
         var17 = this.bipedLeftArm;
         var17.field_78796_g += this.bipedBody.field_78796_g;
         var17 = this.bipedLeftArm;
         var17.field_78795_f += this.bipedBody.field_78796_g;
         f = 1.0F - this.field_78095_p;
         f *= f;
         f *= f;
         f = 1.0F - f;
         float f2 = MathHelper.func_76126_a(f * (float)Math.PI);
         float f4 = MathHelper.func_76126_a(this.field_78095_p * (float)Math.PI) * -(this.bipedHead.field_78795_f - 0.7F) * 0.75F;
         var17 = this.bipedRightArm;
         var17.field_78795_f = (float)((double)var17.field_78795_f - ((double)f2 * 1.2 + (double)f4));
         var17 = this.bipedRightArm;
         var17.field_78796_g += this.bipedBody.field_78796_g * 2.0F;
         this.bipedRightArm.field_78808_h = MathHelper.func_76126_a(this.field_78095_p * (float)Math.PI) * -0.4F;
      }

      if (this.isSneak) {
         this.bipedBody.field_78795_f = 0.5F;
         ModelRenderer var22 = this.bipedRightLeg;
         var22.field_78795_f -= 0.0F;
         var22 = this.bipedLeftLeg;
         var22.field_78795_f -= 0.0F;
         var22 = this.bipedRightArm;
         var22.field_78795_f += 0.4F;
         var22 = this.bipedLeftArm;
         var22.field_78795_f += 0.4F;
         this.bipedRightLeg.field_78798_e = 4.0F;
         this.bipedLeftLeg.field_78798_e = 4.0F;
         this.bipedRightLeg.field_78797_d = 9.0F;
         this.bipedLeftLeg.field_78797_d = 9.0F;
         this.bipedHead.field_78797_d = 1.0F;
      } else {
         this.bipedBody.field_78795_f = 0.0F;
         this.bipedRightLeg.field_78798_e = 0.0F;
         this.bipedLeftLeg.field_78798_e = 0.0F;
         this.bipedRightLeg.field_78797_d = 12.0F;
         this.bipedLeftLeg.field_78797_d = 12.0F;
         this.bipedHead.field_78797_d = 0.0F;
      }

      ModelRenderer var26 = this.bipedRightArm;
      var26.field_78808_h += MathHelper.func_76134_b(par3 * 0.09F) * 0.05F + 0.05F;
      var26 = this.bipedLeftArm;
      var26.field_78808_h -= MathHelper.func_76134_b(par3 * 0.09F) * 0.05F + 0.05F;
      var26 = this.bipedRightArm;
      var26.field_78795_f += MathHelper.func_76126_a(par3 * 0.067F) * 0.05F;
      var26 = this.bipedLeftArm;
      var26.field_78795_f -= MathHelper.func_76126_a(par3 * 0.067F) * 0.05F;
      if (this.aimedBow) {
         float f1 = 0.0F;
         float f3 = 0.0F;
         this.bipedRightArm.field_78808_h = 0.0F;
         this.bipedLeftArm.field_78808_h = 0.0F;
         this.bipedRightArm.field_78796_g = -(0.1F - f1 * 0.6F) + this.bipedHead.field_78796_g;
         this.bipedLeftArm.field_78796_g = 0.1F - f1 * 0.6F + this.bipedHead.field_78796_g + 0.4F;
         this.bipedRightArm.field_78795_f = (-(float)Math.PI / 2F) + this.bipedHead.field_78795_f;
         this.bipedLeftArm.field_78795_f = (-(float)Math.PI / 2F) + this.bipedHead.field_78795_f;
         var26 = this.bipedRightArm;
         var26.field_78795_f -= f1 * 1.2F - f3 * 0.4F;
         var26 = this.bipedLeftArm;
         var26.field_78795_f -= f1 * 1.2F - f3 * 0.4F;
         var26 = this.bipedRightArm;
         var26.field_78808_h += MathHelper.func_76134_b(par3 * 0.09F) * 0.05F + 0.05F;
         var26 = this.bipedLeftArm;
         var26.field_78808_h -= MathHelper.func_76134_b(par3 * 0.09F) * 0.05F + 0.05F;
         var26 = this.bipedRightArm;
         var26.field_78795_f += MathHelper.func_76126_a(par3 * 0.067F) * 0.05F;
         var26 = this.bipedLeftArm;
         var26.field_78795_f -= MathHelper.func_76126_a(par3 * 0.067F) * 0.05F;
      }

   }

   public void renderEars(float f) {
      this.bipedEars.field_78796_g = this.bipedHead.field_78796_g;
      this.bipedEars.field_78795_f = this.bipedHead.field_78795_f;
      this.bipedEars.field_78800_c = 0.0F;
      this.bipedEars.field_78797_d = 0.0F;
      this.bipedEars.func_78785_a(f);
   }

   public void setRotation(ModelRenderer model, float x, float y, float z) {
      model.field_78795_f = x;
      model.field_78796_g = y;
      model.field_78808_h = z;
   }

   public void renderCloak(float f) {
      this.bipedCloak.func_78785_a(f);
   }
}
