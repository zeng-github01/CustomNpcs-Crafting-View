package noppes.npcs.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelEnderChibi extends ModelNPCMale {
   public ModelEnderChibi(float f) {
      super(f);
   }

   public void init(float f, float f1) {
      super.init(f, f1);
      this.bipedRightArm = new ModelRenderer(this, 44, 18);
      this.bipedRightArm.func_78789_a(-1.0F, 4.0F, -1.0F, 2, 12, 2);
      this.bipedRightArm.func_78793_a(-5.0F, 2.0F, 0.0F);
      ModelRenderer extension = new ModelRenderer(this, 44, 18);
      extension.func_78789_a(-1.0F, -2.0F, -1.0F, 2, 6, 2);
      this.bipedRightArm.func_78792_a(extension);
      this.bipedLeftArm = new ModelRenderer(this, 44, 18);
      this.bipedLeftArm.field_78809_i = true;
      this.bipedLeftArm.func_78789_a(-1.0F, 4.0F, -1.0F, 2, 12, 2);
      this.bipedLeftArm.func_78793_a(5.0F, 2.0F, 0.0F);
      ModelRenderer extension2 = new ModelRenderer(this, 44, 18);
      extension2.field_78809_i = true;
      extension2.func_78789_a(-1.0F, -2.0F, -1.0F, 2, 6, 2);
      this.bipedLeftArm.func_78792_a(extension2);
      this.bipedRightLeg = new ModelRenderer(this, 4, 20);
      this.bipedRightLeg.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 10, 2);
      this.bipedRightLeg.func_78793_a(-2.0F, 12.0F, 0.0F);
      this.bipedLeftLeg = new ModelRenderer(this, 4, 20);
      this.bipedLeftLeg.field_78809_i = true;
      this.bipedLeftLeg.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 10, 2);
      this.bipedLeftLeg.func_78793_a(2.0F, 12.0F, 0.0F);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 0.14F, 0.0F);
      super.func_78088_a(entity, f, f1, f2, f3, f4, f5);
      GL11.glPopMatrix();
   }
}
