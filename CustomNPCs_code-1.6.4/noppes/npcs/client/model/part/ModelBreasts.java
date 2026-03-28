package noppes.npcs.client.model.part;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import noppes.npcs.EntityCustomNpc;
import noppes.npcs.client.model.ModelMPM;
import noppes.npcs.client.model.util.Model2DRenderer;
import noppes.npcs.client.model.util.ModelPartInterface;

public class ModelBreasts extends ModelPartInterface {
   private Model2DRenderer breasts;
   private ModelRenderer breasts2;
   private ModelRenderer breasts3;

   public ModelBreasts(ModelMPM base) {
      super(base);
      this.breasts = new Model2DRenderer(base, 20.0F, 22.0F, 8, 3, 64.0F, 32.0F);
      this.breasts.func_78793_a(-3.6F, 5.2F, -3.0F);
      this.breasts.setScale(0.17F, 0.19F);
      this.breasts.setThickness(1.0F);
      this.func_78792_a(this.breasts);
      this.breasts2 = new ModelRenderer(base);
      this.func_78792_a(this.breasts2);
      Model2DRenderer bottom = new Model2DRenderer(base, 20.0F, 22.0F, 8, 4, 64.0F, 32.0F);
      bottom.func_78793_a(-3.6F, 5.0F, -3.1F);
      bottom.setScale(0.225F, 0.2F);
      bottom.setThickness(2.0F);
      bottom.field_78795_f = (-(float)Math.PI / 10F);
      this.breasts2.func_78792_a(bottom);
      this.breasts3 = new ModelRenderer(base);
      this.func_78792_a(this.breasts3);
      Model2DRenderer right = new Model2DRenderer(base, 20.0F, 22.0F, 3, 2, 64.0F, 32.0F);
      right.func_78793_a(-3.8F, 5.3F, -3.6F);
      right.setScale(0.12F, 0.14F);
      right.setThickness(1.75F);
      this.breasts3.func_78792_a(right);
      Model2DRenderer right2 = new Model2DRenderer(base, 20.0F, 22.0F, 3, 1, 64.0F, 32.0F);
      right2.func_78793_a(-3.8F, 4.1F, -3.14F);
      right2.setScale(0.06F, 0.07F);
      right2.setThickness(1.75F);
      right2.field_78795_f = 0.34906584F;
      this.breasts3.func_78792_a(right2);
      Model2DRenderer right3 = new Model2DRenderer(base, 20.0F, 24.0F, 3, 1, 64.0F, 32.0F);
      right3.func_78793_a(-3.8F, 5.3F, -3.6F);
      right3.setScale(0.06F, 0.07F);
      right3.setThickness(1.75F);
      right3.field_78795_f = -0.34906584F;
      this.breasts3.func_78792_a(right3);
      Model2DRenderer right4 = new Model2DRenderer(base, 23.0F, 22.0F, 1, 2, 64.0F, 32.0F);
      right4.func_78793_a(-1.8F, 5.3F, -3.14F);
      right4.setScale(0.12F, 0.14F);
      right4.setThickness(1.75F);
      right4.field_78796_g = 0.34906584F;
      this.breasts3.func_78792_a(right4);
      Model2DRenderer left = new Model2DRenderer(base, 25.0F, 22.0F, 3, 2, 64.0F, 32.0F);
      left.func_78793_a(0.8F, 5.3F, -3.6F);
      left.setScale(0.12F, 0.14F);
      left.setThickness(1.75F);
      this.breasts3.func_78792_a(left);
      Model2DRenderer left2 = new Model2DRenderer(base, 25.0F, 22.0F, 3, 1, 64.0F, 32.0F);
      left2.func_78793_a(0.8F, 4.1F, -3.18F);
      left2.setScale(0.06F, 0.07F);
      left2.setThickness(1.75F);
      left2.field_78795_f = 0.34906584F;
      this.breasts3.func_78792_a(left2);
      Model2DRenderer left3 = new Model2DRenderer(base, 25.0F, 24.0F, 3, 1, 64.0F, 32.0F);
      left3.func_78793_a(0.8F, 5.3F, -3.6F);
      left3.setScale(0.06F, 0.07F);
      left3.setThickness(1.75F);
      left3.field_78795_f = -0.34906584F;
      this.breasts3.func_78792_a(left3);
      Model2DRenderer left4 = new Model2DRenderer(base, 24.0F, 22.0F, 1, 2, 64.0F, 32.0F);
      left4.func_78793_a(0.8F, 5.3F, -3.6F);
      left4.setScale(0.12F, 0.14F);
      left4.setThickness(1.75F);
      left4.field_78796_g = -0.34906584F;
      this.breasts3.func_78792_a(left4);
   }

   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity) {
   }

   public void initData(EntityCustomNpc data) {
      this.field_78807_k = data.breasts == 0;
      this.breasts.field_78807_k = data.breasts != 1;
      this.breasts2.field_78807_k = data.breasts != 2;
      this.breasts3.field_78807_k = data.breasts != 3;
   }
}
