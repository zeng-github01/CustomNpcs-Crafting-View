package noppes.npcs.client.model;

import net.minecraft.client.model.ModelRenderer;

public class ModelFurryMale extends ModelNPCMale {
   public ModelRenderer Snout;
   public ModelRenderer Snout2;
   public ModelRenderer Tail;
   public ModelRenderer LeftEar;
   public ModelRenderer RightEar;
   public ModelRenderer LeftWing;
   public ModelRenderer RightWing;
   public ModelRenderer LeftHorn;
   public ModelRenderer RightHorn;

   public ModelFurryMale(int width, int height, float f) {
      super(width, height, f);
   }

   public void init(float f, float f1) {
      super.init(f, f1);
      this.Snout = new ModelRenderer(this, 0, 32);
      this.Snout.func_78789_a(0.0F, 0.0F, 0.0F, 4, 3, 3);
      this.Snout.func_78793_a(-2.0F, -3.0F, -7.0F);
      this.bipedHead.func_78792_a(this.Snout);
      this.Snout2 = new ModelRenderer(this, 0, 38);
      this.Snout2.func_78789_a(0.0F, 0.0F, 0.0F, 4, 3, 1);
      this.Snout2.func_78793_a(-2.0F, -3.0F, -5.0F);
      this.bipedHead.func_78792_a(this.Snout2);
      this.LeftEar = new ModelRenderer(this, 14, 32);
      this.LeftEar.func_78789_a(-1.5F, -3.0F, -0.1F, 3, 3, 2);
      this.LeftEar.func_78793_a(3.0F, -7.5F, 2.0F);
      this.bipedHead.func_78792_a(this.LeftEar);
      this.RightEar = new ModelRenderer(this, 14, 32);
      this.RightEar.field_78809_i = true;
      this.RightEar.func_78789_a(-1.5F, -3.0F, -0.1F, 3, 3, 2);
      this.RightEar.func_78793_a(-3.0F, -7.5F, 2.0F);
      this.bipedHead.func_78792_a(this.RightEar);
      this.Tail = new ModelRenderer(this, 24, 32);
      this.Tail.func_78789_a(0.0F, 0.0F, 0.0F, 2, 9, 2);
      this.Tail.func_78793_a(-1.0F, 11.0F, 1.0F);
      this.setRotation(this.Tail, 0.8714253F, 0.0F, 0.0F);
      this.bipedBody.func_78792_a(this.Tail);
      this.LeftWing = new ModelRenderer(this, 32, 32);
      this.LeftWing.field_78809_i = true;
      this.LeftWing.func_78789_a(0.0F, 0.0F, 0.0F, 15, 20, 0);
      this.LeftWing.func_78793_a(1.0F, -2.0F, 0.0F);
      this.setRotation(this.LeftWing, 0.3141593F, (-(float)Math.PI / 6F), -0.3490659F);
      this.bipedBody.func_78792_a(this.LeftWing);
      this.RightWing = new ModelRenderer(this, 32, 32);
      this.RightWing.func_78789_a(-15.0F, 0.0F, 0.0F, 15, 20, 0);
      this.RightWing.func_78793_a(-1.0F, -2.0F, 0.0F);
      this.setRotation(this.RightWing, 0.3141593F, ((float)Math.PI / 6F), 0.3490659F);
      this.bipedBody.func_78792_a(this.RightWing);
      this.LeftHorn = new ModelRenderer(this, 0, 42);
      this.LeftHorn.field_78809_i = true;
      this.LeftHorn.func_78789_a(0.0F, 0.0F, 0.0F, 5, 2, 2);
      this.LeftHorn.func_78793_a(4.0F, -7.0F, 0.0F);
      this.bipedHead.func_78792_a(this.LeftHorn);
      this.RightHorn = new ModelRenderer(this, 0, 42);
      this.RightHorn.func_78789_a(0.0F, 0.0F, 0.0F, 5, 2, 2);
      this.RightHorn.func_78793_a(-9.0F, -7.0F, 0.0F);
      this.bipedHead.func_78792_a(this.RightHorn);
      ModelRenderer earleft = new ModelRenderer(this, 56, 23);
      earleft.field_78809_i = true;
      earleft.func_78789_a(-1.466667F, -4.0F, 0.0F, 3, 8, 1);
      earleft.func_78793_a(2.533333F, -12.0F, 0.0F);
      this.bipedHead.func_78792_a(earleft);
      ModelRenderer earright = new ModelRenderer(this, 56, 23);
      earright.func_78789_a(-1.5F, -4.0F, 0.0F, 3, 8, 1);
      earright.func_78793_a(-2.466667F, -12.0F, 0.0F);
      this.bipedHead.func_78792_a(earright);
      ModelRenderer tailbottom = new ModelRenderer(this, 56, 18);
      tailbottom.func_78789_a(-1.5F, -2.0F, 0.0F, 3, 4, 1);
      tailbottom.func_78793_a(0.0F, 8.5F, 2.0F);
      tailbottom.func_78787_b(64, 32);
      tailbottom.field_78809_i = true;
      this.setRotation(tailbottom, 0.0F, 0.0F, -1.570796F);
      this.bipedBody.func_78792_a(tailbottom);
      ModelRenderer tailtop = new ModelRenderer(this, 56, 16);
      tailtop.func_78789_a(0.0F, 0.0F, 0.0F, 2, 1, 1);
      tailtop.func_78793_a(-1.0F, 6.0F, 2.0F);
      tailtop.func_78787_b(64, 32);
      tailtop.field_78809_i = true;
      this.bipedBody.func_78792_a(tailtop);
      ModelRenderer tooth = new ModelRenderer(this, 14, 40);
      tooth.func_78789_a(-1.0F, -2.0F, -5.0F, 2, 1, 1);
      tooth.func_78793_a(0.0F, 0.0F, 0.0F);
      this.bipedHead.func_78792_a(tooth);
      ModelRenderer face = new ModelRenderer(this, 14, 37);
      face.func_78789_a(1.0F, 0.0F, 0.0F, 4, 2, 1);
      face.func_78793_a(-3.0F, -4.0F, -5.0F);
      this.bipedHead.func_78792_a(face);
   }
}
