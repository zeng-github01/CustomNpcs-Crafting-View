package noppes.npcs.client.model;

import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelElfFemale extends ModelNPCFemale {
   public ModelElfFemale(float f) {
      super(f);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.setRotationAngles(f, f1, f2, f3, f4, f5);
      float f6 = 0.85F;
      GL11.glPushMatrix();
      GL11.glScalef(f6, f6 - 0.1F, f6);
      GL11.glTranslatef(0.0F, -0.015F, 0.0F);
      this.renderHead(entity, f5);
      GL11.glPopMatrix();
      f6 = 0.74F;
      GL11.glPushMatrix();
      GL11.glScalef(f6, 0.9F, f6);
      GL11.glTranslatef(0.09F, 0.0F, 0.0F);
      this.renderLeftArm(entity, f5);
      GL11.glPopMatrix();
      GL11.glPushMatrix();
      GL11.glScalef(f6, 0.9F, f6);
      GL11.glTranslatef(-0.09F, 0.0F, 0.0F);
      this.renderRightArm(entity, f5);
      GL11.glPopMatrix();
      this.renderBody(entity, f5);
      this.renderLegs(entity, f5);
   }
}
