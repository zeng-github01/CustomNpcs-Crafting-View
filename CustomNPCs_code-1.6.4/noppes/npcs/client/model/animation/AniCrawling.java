package noppes.npcs.client.model.animation;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import noppes.npcs.client.model.ModelMPM;

public class AniCrawling {
   public static void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity, ModelMPM model) {
      model.bipedHead.field_78808_h = -par4 / (180F / (float)Math.PI);
      model.bipedHead.field_78796_g = 0.0F;
      model.bipedHead.field_78795_f = -0.95993114F;
      model.bipedHeadwear.field_78795_f = model.bipedHead.field_78795_f;
      model.bipedHeadwear.field_78796_g = model.bipedHead.field_78796_g;
      model.bipedHeadwear.field_78808_h = model.bipedHead.field_78808_h;
      if ((double)par2 > (double)0.25F) {
         par2 = 0.25F;
      }

      float movement = MathHelper.func_76134_b(par1 * 0.8F + (float)Math.PI) * par2;
      model.bipedLeftArm.field_78795_f = (float)Math.PI - movement * 0.25F;
      model.bipedLeftArm.field_78796_g = movement * -0.46F;
      model.bipedLeftArm.field_78808_h = movement * -0.2F;
      model.bipedLeftArm.field_78797_d = 2.0F - movement * 9.0F;
      model.bipedRightArm.field_78795_f = (float)Math.PI + movement * 0.25F;
      model.bipedRightArm.field_78796_g = movement * -0.4F;
      model.bipedRightArm.field_78808_h = movement * -0.2F;
      model.bipedRightArm.field_78797_d = 2.0F + movement * 9.0F;
      model.bipedBody.field_78796_g = movement * 0.1F;
      model.bipedBody.field_78795_f = 0.0F;
      model.bipedBody.field_78808_h = movement * 0.1F;
      model.bipedLeftLeg.field_78795_f = movement * 0.1F;
      model.bipedLeftLeg.field_78796_g = movement * 0.1F;
      model.bipedLeftLeg.field_78808_h = -0.122173056F - movement * 0.25F;
      model.bipedLeftLeg.field_78797_d = 10.4F + movement * 9.0F;
      model.bipedLeftLeg.field_78798_e = movement * 0.6F;
      model.bipedRightLeg.field_78795_f = movement * -0.1F;
      model.bipedRightLeg.field_78796_g = movement * 0.1F;
      model.bipedRightLeg.field_78808_h = 0.122173056F - movement * 0.25F;
      model.bipedRightLeg.field_78797_d = 10.4F - movement * 9.0F;
      model.bipedRightLeg.field_78798_e = movement * -0.6F;
   }
}
