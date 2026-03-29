package noppes.npcs.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.MathHelper;

public class ModelZombieMale extends ModelNPCMale {
   public ModelZombieMale(float f) {
      super(f);
   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
      super.setRotationAngles(f, f1, f2, f3, f4, f5);
      float f6 = MathHelper.func_76126_a(this.field_78095_p * 3.141593F);
      float f7 = MathHelper.func_76126_a((1.0F - (1.0F - this.field_78095_p) * (1.0F - this.field_78095_p)) * 3.141593F);
      this.bipedRightArm.field_78808_h = 0.0F;
      this.bipedLeftArm.field_78808_h = 0.0F;
      this.bipedRightArm.field_78796_g = -(0.1F - f6 * 0.6F);
      this.bipedLeftArm.field_78796_g = 0.1F - f6 * 0.6F;
      this.bipedRightArm.field_78795_f = -1.570796F;
      this.bipedLeftArm.field_78795_f = -1.570796F;
      ModelRenderer var10000 = this.bipedRightArm;
      var10000.field_78795_f -= f6 * 1.2F - f7 * 0.4F;
      var10000 = this.bipedLeftArm;
      var10000.field_78795_f -= f6 * 1.2F - f7 * 0.4F;
      var10000 = this.bipedRightArm;
      var10000.field_78808_h += MathHelper.func_76134_b(f2 * 0.09F) * 0.05F + 0.05F;
      var10000 = this.bipedLeftArm;
      var10000.field_78808_h -= MathHelper.func_76134_b(f2 * 0.09F) * 0.05F + 0.05F;
      var10000 = this.bipedRightArm;
      var10000.field_78795_f += MathHelper.func_76126_a(f2 * 0.067F) * 0.05F;
      var10000 = this.bipedLeftArm;
      var10000.field_78795_f -= MathHelper.func_76126_a(f2 * 0.067F) * 0.05F;
   }
}
