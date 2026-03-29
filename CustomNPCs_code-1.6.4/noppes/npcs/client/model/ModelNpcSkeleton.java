package noppes.npcs.client.model;

import net.minecraft.client.model.ModelRenderer;

public class ModelNpcSkeleton extends ModelZombieMale {
   public ModelNpcSkeleton(float f) {
      super(f);
      this.bipedRightArm = new ModelRenderer(this, 40, 16);
      this.bipedRightArm.func_78790_a(-1.0F, -2.0F, -1.0F, 2, 12, 2, f);
      this.bipedRightArm.func_78793_a(-5.0F, 2.0F, 0.0F);
      this.bipedLeftArm = new ModelRenderer(this, 40, 16);
      this.bipedLeftArm.field_78809_i = true;
      this.bipedLeftArm.func_78790_a(-1.0F, -2.0F, -1.0F, 2, 12, 2, f);
      this.bipedLeftArm.func_78793_a(5.0F, 2.0F, 0.0F);
      this.bipedRightLeg = new ModelRenderer(this, 0, 16);
      this.bipedRightLeg.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 12, 2, f);
      this.bipedRightLeg.func_78793_a(-2.0F, 12.0F, 0.0F);
      this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
      this.bipedLeftLeg.field_78809_i = true;
      this.bipedLeftLeg.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 12, 2, f);
      this.bipedLeftLeg.func_78793_a(2.0F, 12.0F, 0.0F);
   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
      this.aimedBow = true;
      super.setRotationAngles(f, f1, f2, f3, f4, f5);
   }
}
