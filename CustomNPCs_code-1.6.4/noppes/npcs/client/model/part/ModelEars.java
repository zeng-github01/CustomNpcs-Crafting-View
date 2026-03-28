package noppes.npcs.client.model.part;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.EntityCustomNpc;
import noppes.npcs.ModelPartData;
import noppes.npcs.client.model.ModelMPM;
import noppes.npcs.client.model.util.Model2DRenderer;
import noppes.npcs.client.model.util.ModelPartInterface;

public class ModelEars extends ModelPartInterface {
   private ModelRenderer ears;
   private ModelRenderer bunny;

   public ModelEars(ModelMPM par1ModelBase) {
      super(par1ModelBase);
      this.ears = new ModelRenderer(this.base);
      this.func_78792_a(this.ears);
      Model2DRenderer right = new Model2DRenderer(this.base, 56.0F, 0.0F, 8, 4, 64.0F, 32.0F);
      right.func_78793_a(-7.44F, -7.3F, -0.0F);
      right.setScale(0.234F, 0.234F);
      right.setThickness(1.16F);
      this.ears.func_78792_a(right);
      Model2DRenderer left = new Model2DRenderer(this.base, 56.0F, 0.0F, 8, 4, 64.0F, 32.0F);
      left.func_78793_a(7.44F, -7.3F, 1.15F);
      left.setScale(0.234F, 0.234F);
      this.setRotation(left, 0.0F, (float)Math.PI, 0.0F);
      left.setThickness(1.16F);
      this.ears.func_78792_a(left);
      Model2DRenderer right2 = new Model2DRenderer(this.base, 56.0F, 4.0F, 8, 4, 64.0F, 32.0F);
      right2.func_78793_a(-7.44F, -7.3F, 1.14F);
      right2.setScale(0.234F, 0.234F);
      right2.setThickness(1.16F);
      this.ears.func_78792_a(right2);
      Model2DRenderer left2 = new Model2DRenderer(this.base, 56.0F, 4.0F, 8, 4, 64.0F, 32.0F);
      left2.func_78793_a(7.44F, -7.3F, 2.31F);
      left2.setScale(0.234F, 0.234F);
      this.setRotation(left2, 0.0F, (float)Math.PI, 0.0F);
      left2.setThickness(1.16F);
      this.ears.func_78792_a(left2);
      this.bunny = new ModelRenderer(this.base);
      this.func_78792_a(this.bunny);
      ModelRenderer earleft = new ModelRenderer(this.base, 56, 0);
      earleft.field_78809_i = true;
      earleft.func_78789_a(-1.466667F, -4.0F, 0.0F, 3, 7, 1);
      earleft.func_78793_a(2.533333F, -11.0F, 0.0F);
      this.bunny.func_78792_a(earleft);
      ModelRenderer earright = new ModelRenderer(this.base, 56, 0);
      earright.func_78789_a(-1.5F, -4.0F, 0.0F, 3, 7, 1);
      earright.func_78793_a(-2.466667F, -11.0F, 0.0F);
      this.bunny.func_78792_a(earright);
   }

   public void initData(EntityCustomNpc data) {
      ModelPartData config = data.getPartData("ears");
      if (config == null) {
         this.field_78807_k = true;
      } else {
         this.field_78807_k = false;
         this.color = config.color;
         this.ears.field_78807_k = config.type != 0;
         this.bunny.field_78807_k = config.type != 1;
         if (!config.playerTexture) {
            this.location = (ResourceLocation)config.getResource();
         } else {
            this.location = null;
         }

      }
   }
}
