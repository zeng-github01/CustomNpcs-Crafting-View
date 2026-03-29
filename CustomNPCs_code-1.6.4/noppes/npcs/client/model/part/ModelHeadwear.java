package noppes.npcs.client.model.part;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import noppes.npcs.client.model.util.Model2DRenderer;
import noppes.npcs.client.model.util.ModelScaleRenderer;

public class ModelHeadwear extends ModelScaleRenderer {
   public ModelHeadwear(ModelBase base) {
      super(base);
      Model2DRenderer right = new Model2DRenderer(base, 32.0F, 8.0F, 8, 8, 64.0F, 32.0F);
      right.func_78793_a(-4.64F, 0.8F, 4.64F);
      right.setScale(0.58F);
      right.setThickness(0.65F);
      this.setRotation(right, 0.0F, ((float)Math.PI / 2F), 0.0F);
      this.func_78792_a(right);
      Model2DRenderer left = new Model2DRenderer(base, 48.0F, 8.0F, 8, 8, 64.0F, 32.0F);
      left.func_78793_a(4.64F, 0.8F, -4.64F);
      left.setScale(0.58F);
      left.setThickness(0.65F);
      this.setRotation(left, 0.0F, (-(float)Math.PI / 2F), 0.0F);
      this.func_78792_a(left);
      Model2DRenderer front = new Model2DRenderer(base, 40.0F, 8.0F, 8, 8, 64.0F, 32.0F);
      front.func_78793_a(-4.64F, 0.8F, -4.64F);
      front.setScale(0.58F);
      front.setThickness(0.65F);
      this.setRotation(front, 0.0F, 0.0F, 0.0F);
      this.func_78792_a(front);
      Model2DRenderer back = new Model2DRenderer(base, 56.0F, 8.0F, 8, 8, 64.0F, 32.0F);
      back.func_78793_a(4.64F, 0.8F, 4.64F);
      back.setScale(0.58F);
      back.setThickness(0.65F);
      this.setRotation(back, 0.0F, (float)Math.PI, 0.0F);
      this.func_78792_a(back);
      Model2DRenderer top = new Model2DRenderer(base, 40.0F, 0.0F, 8, 8, 64.0F, 32.0F);
      top.func_78793_a(-4.64F, -8.5F, -4.64F);
      top.setScale(0.58F);
      top.setThickness(0.65F);
      this.setRotation(top, (-(float)Math.PI / 2F), 0.0F, 0.0F);
      this.func_78792_a(top);
      Model2DRenderer bottom = new Model2DRenderer(base, 48.0F, 0.0F, 8, 8, 64.0F, 32.0F);
      bottom.func_78793_a(-4.64F, 0.0F, -4.64F);
      bottom.setScale(0.58F);
      bottom.setThickness(0.65F);
      this.setRotation(bottom, (-(float)Math.PI / 2F), 0.0F, 0.0F);
      this.func_78792_a(bottom);
   }

   public void setRotation(ModelRenderer model, float x, float y, float z) {
      model.field_78795_f = x;
      model.field_78796_g = y;
      model.field_78808_h = z;
   }
}
