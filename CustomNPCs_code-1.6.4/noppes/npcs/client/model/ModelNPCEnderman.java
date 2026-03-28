package noppes.npcs.client.model;

import net.minecraft.client.model.ModelRenderer;

public class ModelNPCEnderman extends ModelNPCMale {
   public ModelNPCEnderman(float f) {
      super(f);
   }

   public void init(float f, float f1) {
      super.init(f, f1);
      this.bipedHeadwear = new ModelRenderer(this, 0, 16);
      this.bipedHeadwear.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, f1 + 0.5F);
      this.bipedHeadwear.func_78793_a(0.0F, 0.0F + f1, 0.0F);
      this.bipedBody = new ModelRenderer(this, 40, 0);
      this.bipedBody.func_78790_a(-4.0F, 0.0F, -2.0F, 8, 12, 4, f1);
      this.bipedBody.func_78793_a(0.0F, -14.0F, 0.0F);
      this.bipedRightArm = new ModelRenderer(this, 32, 0);
      this.bipedRightArm.func_78790_a(-1.0F, -2.0F, -1.0F, 2, 30, 2, f1);
      this.bipedRightArm.func_78793_a(-3.0F, -12.0F, 0.0F);
      this.bipedLeftArm = new ModelRenderer(this, 32, 0);
      this.bipedLeftArm.field_78809_i = true;
      this.bipedLeftArm.func_78790_a(-1.0F, -2.0F, -1.0F, 2, 30, 2, f1);
      this.bipedLeftArm.func_78793_a(5.0F, -12.0F, 0.0F);
      this.bipedRightLeg = new ModelRenderer(this, 32, 0);
      this.bipedRightLeg.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 30, 2, f1);
      this.bipedRightLeg.func_78793_a(-2.0F, -2.0F, 0.0F);
      this.bipedLeftLeg = new ModelRenderer(this, 32, 0);
      this.bipedLeftLeg.field_78809_i = true;
      this.bipedLeftLeg.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 30, 2, f1);
      this.bipedLeftLeg.func_78793_a(2.0F, -2.0F, 0.0F);
      this.bipedCloak.func_78793_a(0.0F, -12.0F, -2.0F);
   }

   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6) {
      super.setRotationAngles(par1, par2, par3, par4, par5, par6);
      float f6 = -14.0F;
      this.bipedBody.field_78795_f = 0.0F;
      this.bipedBody.field_78797_d = f6;
      this.bipedBody.field_78798_e = -0.0F;
      ModelRenderer var10000 = this.bipedRightLeg;
      var10000.field_78795_f -= 0.0F;
      var10000 = this.bipedLeftLeg;
      var10000.field_78795_f -= 0.0F;
      this.bipedRightArm.field_78795_f = (float)((double)this.bipedRightArm.field_78795_f * (double)0.5F);
      this.bipedLeftArm.field_78795_f = (float)((double)this.bipedLeftArm.field_78795_f * (double)0.5F);
      this.bipedRightLeg.field_78795_f = (float)((double)this.bipedRightLeg.field_78795_f * (double)0.5F);
      this.bipedLeftLeg.field_78795_f = (float)((double)this.bipedLeftLeg.field_78795_f * (double)0.5F);
      float f7 = 0.4F;
      if (this.bipedRightLeg.field_78795_f > f7) {
         this.bipedRightLeg.field_78795_f = f7;
      }

      if (this.bipedLeftLeg.field_78795_f > f7) {
         this.bipedLeftLeg.field_78795_f = f7;
      }

      if (this.bipedRightLeg.field_78795_f < -f7) {
         this.bipedRightLeg.field_78795_f = -f7;
      }

      if (this.bipedLeftLeg.field_78795_f < -f7) {
         this.bipedLeftLeg.field_78795_f = -f7;
      }

      if (this.heldItemLeft != 0) {
         this.bipedRightArm.field_78795_f = -0.5F;
         this.bipedLeftArm.field_78795_f = -0.5F;
         this.bipedRightArm.field_78808_h = 0.05F;
         this.bipedLeftArm.field_78808_h = -0.05F;
      }

      this.bipedRightArm.field_78798_e = 0.0F;
      this.bipedLeftArm.field_78798_e = 0.0F;
      this.bipedRightLeg.field_78798_e = 0.0F;
      this.bipedLeftLeg.field_78798_e = 0.0F;
      this.bipedRightLeg.field_78797_d = 9.0F + f6;
      this.bipedLeftLeg.field_78797_d = 9.0F + f6;
      this.bipedHead.field_78798_e = -0.0F;
      this.bipedHead.field_78797_d = f6 + 1.0F;
      this.bipedHeadwear.field_78800_c = this.bipedHead.field_78800_c;
      this.bipedHeadwear.field_78797_d = this.bipedHead.field_78797_d;
      this.bipedHeadwear.field_78798_e = this.bipedHead.field_78798_e;
      this.bipedHeadwear.field_78795_f = this.bipedHead.field_78795_f;
      this.bipedHeadwear.field_78796_g = this.bipedHead.field_78796_g;
      this.bipedHeadwear.field_78808_h = this.bipedHead.field_78808_h;
   }
}
