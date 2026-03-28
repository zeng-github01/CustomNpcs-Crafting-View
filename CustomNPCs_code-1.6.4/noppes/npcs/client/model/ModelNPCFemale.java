package noppes.npcs.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import noppes.npcs.client.model.util.ModelPlaneRenderer;
import org.lwjgl.opengl.GL11;

public class ModelNPCFemale extends ModelNPCMale {
   public ModelRenderer Breasts;

   public ModelNPCFemale(float f) {
      super(f);
   }

   public ModelNPCFemale(int width, int height, float f) {
      super(width, height, f);
   }

   public void init(float f, float f1) {
      super.init(f, f1);
      this.Breasts = new ModelRenderer(this, 24, 0);
      this.Breasts.func_78790_a(0.0F, 0.0F, 0.0F, 7, 3, 1, 0.0F);
      this.Breasts.func_78793_a(-3.5F, 1.8F, -2.85F);
      ModelPlaneRenderer BreastsTop = new ModelPlaneRenderer(this, 56, 0);
      BreastsTop.addTopPlane(0.0F, 0.0F, 0.0F, 7, 1, f);
      this.Breasts.func_78792_a(BreastsTop);
      ModelPlaneRenderer BreastsFront = new ModelPlaneRenderer(this, 56, 1);
      BreastsFront.addBackPlane(0.0F, 0.0F, 0.0F, 7, 3, f);
      this.Breasts.func_78792_a(BreastsFront);
      ModelPlaneRenderer BreastsBottom = new ModelPlaneRenderer(this, 56, 4);
      BreastsBottom.addTopPlane(0.0F, -3.0F, -1.0F, 7, 1, f);
      BreastsBottom.field_78795_f = (float)Math.PI;
      this.Breasts.func_78792_a(BreastsBottom);
      ModelPlaneRenderer BreastsLeft = new ModelPlaneRenderer(this, 63, 0);
      BreastsLeft.addSidePlane(0.0F, 0.0F, 0.0F, 3, 1, f);
      this.Breasts.func_78792_a(BreastsLeft);
      ModelPlaneRenderer BreastsRight = new ModelPlaneRenderer(this, 63, 3);
      BreastsRight.addSidePlane(-7.0F, 0.0F, -1.0F, 3, 1, f);
      BreastsRight.field_78796_g = (float)Math.PI;
      this.Breasts.func_78792_a(BreastsRight);
      this.bipedBody.func_78792_a(this.Breasts);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.setRotationAngles(f, f1, f2, f3, f4, f5);
      float f6 = 0.85F;
      GL11.glPushMatrix();
      GL11.glScalef(f6, f6, f6);
      GL11.glTranslatef(0.0F, -0.015F, 0.0F);
      this.renderHead(entity, f5);
      GL11.glPopMatrix();
      f6 = 0.8F;
      GL11.glPushMatrix();
      GL11.glScalef(f6, 0.96F, f6);
      GL11.glTranslatef(0.07F, 0.0F, 0.0F);
      this.renderLeftArm(entity, f5);
      GL11.glPopMatrix();
      GL11.glPushMatrix();
      GL11.glScalef(f6, 0.96F, f6);
      GL11.glTranslatef(-0.07F, 0.0F, 0.0F);
      this.renderRightArm(entity, f5);
      GL11.glPopMatrix();
      this.renderLegs(entity, f5);
      this.renderBody(entity, f5);
   }
}
