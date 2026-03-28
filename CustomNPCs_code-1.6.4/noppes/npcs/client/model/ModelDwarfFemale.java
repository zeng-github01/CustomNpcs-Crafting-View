package noppes.npcs.client.model;

import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelDwarfFemale extends ModelNPCFemale {
   public ModelDwarfFemale(float f) {
      super(f);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.setRotationAngles(f, f1, f2, f3, f4, f5);
      this.renderHead(entity, f5);
      GL11.glPushMatrix();
      GL11.glScalef(1.1F, 1.0F, 1.0F);
      this.renderArms(entity, f5);
      this.renderLegs(entity, f5);
      GL11.glPopMatrix();
      GL11.glPushMatrix();
      GL11.glScalef(1.1F, 1.0F, 1.25F);
      this.renderBody(entity, f5);
      GL11.glPopMatrix();
   }
}
