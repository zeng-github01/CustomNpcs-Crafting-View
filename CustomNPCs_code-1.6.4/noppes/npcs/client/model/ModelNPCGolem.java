package noppes.npcs.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelNPCGolem extends ModelNPCMale {
   private ModelRenderer bipedLowerBody;

   public ModelNPCGolem(float f) {
      super(f);
   }

   public void init(float f, float f1) {
      super.init(f, f1);
      short short1 = 128;
      short short2 = 128;
      float f2 = -7.0F;
      this.bipedHead = (new ModelRenderer(this)).func_78787_b(short1, short2);
      this.bipedHead.func_78793_a(0.0F, f2, -2.0F);
      this.bipedHead.func_78784_a(0, 0).func_78790_a(-4.0F, -12.0F, -5.5F, 8, 10, 8, f);
      this.bipedHead.func_78784_a(24, 0).func_78790_a(-1.0F, -5.0F, -7.5F, 2, 4, 2, f);
      this.bipedHeadwear = (new ModelRenderer(this)).func_78787_b(short1, short2);
      this.bipedHeadwear.func_78793_a(0.0F, f2, -2.0F);
      this.bipedHeadwear.func_78784_a(0, 85).func_78790_a(-4.0F, -12.0F, -5.5F, 8, 10, 8, f + 0.5F);
      this.bipedBody = (new ModelRenderer(this)).func_78787_b(short1, short2);
      this.bipedBody.func_78793_a(0.0F, 0.0F + f2, 0.0F);
      this.bipedBody.func_78784_a(0, 40).func_78790_a(-9.0F, -2.0F, -6.0F, 18, 12, 11, f + 0.2F);
      this.bipedBody.func_78784_a(0, 21).func_78790_a(-9.0F, -2.0F, -6.0F, 18, 8, 11, f);
      this.bipedLowerBody = (new ModelRenderer(this)).func_78787_b(short1, short2);
      this.bipedLowerBody.func_78793_a(0.0F, 0.0F + f2, 0.0F);
      this.bipedLowerBody.func_78784_a(0, 70).func_78790_a(-4.5F, 10.0F, -3.0F, 9, 5, 6, f + 0.5F);
      this.bipedLowerBody.func_78784_a(30, 70).func_78790_a(-4.5F, 6.0F, -3.0F, 9, 9, 6, f + 0.4F);
      this.bipedRightArm = (new ModelRenderer(this)).func_78787_b(short1, short2);
      this.bipedRightArm.func_78793_a(0.0F, f2, 0.0F);
      this.bipedRightArm.func_78784_a(60, 21).func_78790_a(-13.0F, -2.5F, -3.0F, 4, 30, 6, f + 0.2F);
      this.bipedRightArm.func_78784_a(80, 21).func_78790_a(-13.0F, -2.5F, -3.0F, 4, 20, 6, f);
      this.bipedRightArm.func_78784_a(100, 21).func_78790_a(-13.0F, -2.5F, -3.0F, 4, 20, 6, f + 1.0F);
      this.bipedLeftArm = (new ModelRenderer(this)).func_78787_b(short1, short2);
      this.bipedLeftArm.func_78793_a(0.0F, f2, 0.0F);
      this.bipedLeftArm.func_78784_a(60, 58).func_78790_a(9.0F, -2.5F, -3.0F, 4, 30, 6, f + 0.2F);
      this.bipedLeftArm.func_78784_a(80, 58).func_78790_a(9.0F, -2.5F, -3.0F, 4, 20, 6, f);
      this.bipedLeftArm.func_78784_a(100, 58).func_78790_a(9.0F, -2.5F, -3.0F, 4, 20, 6, f + 1.0F);
      this.bipedLeftLeg = (new ModelRenderer(this, 0, 22)).func_78787_b(short1, short2);
      this.bipedLeftLeg.func_78793_a(-4.0F, 18.0F + f2, 0.0F);
      this.bipedLeftLeg.func_78784_a(37, 0).func_78790_a(-3.5F, -3.0F, -3.0F, 6, 16, 5, f);
      this.bipedRightLeg = (new ModelRenderer(this, 0, 22)).func_78787_b(short1, short2);
      this.bipedRightLeg.field_78809_i = true;
      this.bipedRightLeg.func_78784_a(60, 0).func_78793_a(5.0F, 18.0F + f2, 0.0F);
      this.bipedRightLeg.func_78790_a(-3.5F, -3.0F, -3.0F, 6, 16, 5, f);
   }

   public void func_78088_a(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
      super.func_78088_a(par1Entity, par2, par3, par4, par5, par6, par7);
      this.bipedLowerBody.func_78785_a(par7);
   }

   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6) {
      this.bipedHead.field_78796_g = par4 / (180F / (float)Math.PI);
      this.bipedHead.field_78795_f = par5 / (180F / (float)Math.PI);
      this.bipedHeadwear.field_78796_g = this.bipedHead.field_78796_g;
      this.bipedHeadwear.field_78795_f = this.bipedHead.field_78795_f;
      this.bipedLeftLeg.field_78795_f = -1.5F * this.func_78172_a(par1, 13.0F) * par2;
      this.bipedRightLeg.field_78795_f = 1.5F * this.func_78172_a(par1, 13.0F) * par2;
      this.bipedLeftLeg.field_78796_g = 0.0F;
      this.bipedRightLeg.field_78796_g = 0.0F;
      float f6 = MathHelper.func_76126_a(this.field_78095_p * (float)Math.PI);
      float f7 = MathHelper.func_76126_a((16.0F - (1.0F - this.field_78095_p) * (1.0F - this.field_78095_p)) * (float)Math.PI);
      if ((double)this.field_78095_p > (double)0.0F) {
         this.bipedRightArm.field_78808_h = 0.0F;
         this.bipedLeftArm.field_78808_h = 0.0F;
         this.bipedRightArm.field_78796_g = -(0.1F - f6 * 0.6F);
         this.bipedLeftArm.field_78796_g = 0.1F - f6 * 0.6F;
         this.bipedRightArm.field_78795_f = 0.0F;
         this.bipedLeftArm.field_78795_f = 0.0F;
         this.bipedRightArm.field_78795_f = (-(float)Math.PI / 2F);
         this.bipedLeftArm.field_78795_f = (-(float)Math.PI / 2F);
         ModelRenderer var10000 = this.bipedRightArm;
         var10000.field_78795_f -= f6 * 1.2F - f7 * 0.4F;
         var10000 = this.bipedLeftArm;
         var10000.field_78795_f -= f6 * 1.2F - f7 * 0.4F;
      } else if (this.aimedBow) {
         float f1 = 0.0F;
         float f3 = 0.0F;
         this.bipedRightArm.field_78808_h = 0.0F;
         this.bipedRightArm.field_78795_f = (-(float)Math.PI / 2F) + this.bipedHead.field_78795_f;
         ModelRenderer var12 = this.bipedRightArm;
         var12.field_78795_f -= f1 * 1.2F - f3 * 0.4F;
         var12 = this.bipedRightArm;
         var12.field_78808_h += MathHelper.func_76134_b(par3 * 0.09F) * 0.05F + 0.05F;
         var12 = this.bipedRightArm;
         var12.field_78795_f += MathHelper.func_76126_a(par3 * 0.067F) * 0.05F;
         this.bipedLeftArm.field_78795_f = (-0.2F - 1.5F * this.func_78172_a(par1, 13.0F)) * par2;
         this.bipedBody.field_78796_g = -(0.1F - f1 * 0.6F) + this.bipedHead.field_78796_g;
         this.bipedRightArm.field_78796_g = -(0.1F - f1 * 0.6F) + this.bipedHead.field_78796_g;
         this.bipedLeftArm.field_78796_g = 0.1F - f1 * 0.6F + this.bipedHead.field_78796_g;
      } else {
         this.bipedRightArm.field_78795_f = (-0.2F + 1.5F * this.func_78172_a(par1, 13.0F)) * par2;
         this.bipedLeftArm.field_78795_f = (-0.2F - 1.5F * this.func_78172_a(par1, 13.0F)) * par2;
         this.bipedBody.field_78796_g = 0.0F;
         this.bipedRightArm.field_78796_g = 0.0F;
         this.bipedLeftArm.field_78796_g = 0.0F;
         this.bipedRightArm.field_78808_h = 0.0F;
         this.bipedLeftArm.field_78808_h = 0.0F;
      }

      if (this.field_78093_q) {
         ModelRenderer var15 = this.bipedRightArm;
         var15.field_78795_f += (-(float)Math.PI / 5F);
         var15 = this.bipedLeftArm;
         var15.field_78795_f += (-(float)Math.PI / 5F);
         this.bipedLeftLeg.field_78795_f = -1.2566371F;
         this.bipedRightLeg.field_78795_f = -1.2566371F;
         this.bipedLeftLeg.field_78796_g = ((float)Math.PI / 10F);
         this.bipedRightLeg.field_78796_g = (-(float)Math.PI / 10F);
      }

   }

   private float func_78172_a(float par1, float par2) {
      return (Math.abs(par1 % par2 - par2 * 0.5F) - par2 * 0.25F) / (par2 * 0.25F);
   }
}
