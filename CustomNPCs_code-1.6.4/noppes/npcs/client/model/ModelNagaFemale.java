package noppes.npcs.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import noppes.npcs.client.model.util.ModelPlaneRenderer;
import org.lwjgl.opengl.GL11;

public class ModelNagaFemale extends ModelNPCFemale {
   ModelRenderer leg;
   ModelRenderer leg2;
   ModelRenderer leg3;
   ModelRenderer leg4;
   ModelRenderer leg5;

   public ModelNagaFemale(int width, int height, float f) {
      super(width, height, f);
   }

   public void init(float f, float f1) {
      super.init(f, f1);
      this.bipedRightLeg = new ModelRenderer(this, 0, 0);
      this.bipedLeftLeg = new ModelRenderer(this, 0, 0);
      this.leg = new ModelRenderer(this, 0, 0);
      ModelRenderer legPart = new ModelRenderer(this, 0, 16);
      legPart.func_78789_a(0.0F, -2.0F, -2.0F, 4, 4, 4);
      legPart.func_78793_a(-4.0F, 0.0F, 0.0F);
      this.leg.func_78792_a(legPart);
      legPart = new ModelRenderer(this, 0, 16);
      legPart.field_78809_i = true;
      legPart.func_78789_a(0.0F, -2.0F, -2.0F, 4, 4, 4);
      this.leg.func_78792_a(legPart);
      this.leg2 = new ModelRenderer(this, 0, 0);
      this.leg2.field_78805_m = this.leg.field_78805_m;
      this.leg3 = new ModelRenderer(this, 0, 0);
      ModelPlaneRenderer plane = new ModelPlaneRenderer(this, 4, 24);
      plane.func_78787_b(this.field_78090_t, this.field_78089_u);
      plane.addBackPlane(0.0F, -2.0F, 0.0F, 4, 4);
      plane.func_78793_a(-4.0F, 0.0F, 0.0F);
      this.leg3.func_78792_a(plane);
      plane = new ModelPlaneRenderer(this, 4, 24);
      plane.func_78787_b(this.field_78090_t, this.field_78089_u);
      plane.field_78809_i = true;
      plane.addBackPlane(0.0F, -2.0F, 0.0F, 4, 4);
      this.leg3.func_78792_a(plane);
      plane = new ModelPlaneRenderer(this, 8, 24);
      plane.func_78787_b(this.field_78090_t, this.field_78089_u);
      plane.addBackPlane(0.0F, -2.0F, 6.0F, 4, 4);
      plane.func_78793_a(-4.0F, 0.0F, 0.0F);
      this.leg3.func_78792_a(plane);
      plane = new ModelPlaneRenderer(this, 8, 24);
      plane.func_78787_b(this.field_78090_t, this.field_78089_u);
      plane.field_78809_i = true;
      plane.addBackPlane(0.0F, -2.0F, 6.0F, 4, 4);
      this.leg3.func_78792_a(plane);
      plane = new ModelPlaneRenderer(this, 4, 26);
      plane.func_78787_b(this.field_78090_t, this.field_78089_u);
      plane.addTopPlane(0.0F, -2.0F, -6.0F, 4, 6);
      plane.func_78793_a(-4.0F, 0.0F, 0.0F);
      plane.field_78795_f = (float)Math.PI;
      this.leg3.func_78792_a(plane);
      plane = new ModelPlaneRenderer(this, 4, 26);
      plane.func_78787_b(this.field_78090_t, this.field_78089_u);
      plane.field_78809_i = true;
      plane.addTopPlane(0.0F, -2.0F, -6.0F, 4, 6);
      plane.field_78795_f = (float)Math.PI;
      this.leg3.func_78792_a(plane);
      plane = new ModelPlaneRenderer(this, 8, 26);
      plane.func_78787_b(this.field_78090_t, this.field_78089_u);
      plane.addTopPlane(0.0F, -2.0F, 0.0F, 4, 6);
      plane.func_78793_a(-4.0F, 0.0F, 0.0F);
      this.leg3.func_78792_a(plane);
      plane = new ModelPlaneRenderer(this, 8, 26);
      plane.func_78787_b(this.field_78090_t, this.field_78089_u);
      plane.field_78809_i = true;
      plane.addTopPlane(0.0F, -2.0F, 0.0F, 4, 6);
      this.leg3.func_78792_a(plane);
      plane = new ModelPlaneRenderer(this, 0, 26);
      plane.func_78787_b(this.field_78090_t, this.field_78089_u);
      plane.field_78795_f = ((float)Math.PI / 2F);
      plane.addSidePlane(0.0F, 0.0F, -2.0F, 6, 4);
      plane.func_78793_a(-4.0F, 0.0F, 0.0F);
      this.leg3.func_78792_a(plane);
      plane = new ModelPlaneRenderer(this, 0, 26);
      plane.func_78787_b(this.field_78090_t, this.field_78089_u);
      plane.field_78795_f = ((float)Math.PI / 2F);
      plane.addSidePlane(4.0F, 0.0F, -2.0F, 6, 4);
      this.leg3.func_78792_a(plane);
      this.leg4 = new ModelRenderer(this, 0, 0);
      this.leg4.func_78787_b(this.field_78090_t, this.field_78089_u);
      this.leg4.field_78805_m = this.leg3.field_78805_m;
      this.leg5 = new ModelRenderer(this, 0, 0);
      legPart = new ModelRenderer(this, 56, 20);
      legPart.func_78789_a(0.0F, 0.0F, -2.0F, 2, 5, 2);
      legPart.func_78793_a(-2.0F, 0.0F, 0.0F);
      legPart.field_78795_f = ((float)Math.PI / 2F);
      this.leg5.func_78792_a(legPart);
      legPart = new ModelRenderer(this, 56, 20);
      legPart.field_78809_i = true;
      legPart.func_78789_a(0.0F, 0.0F, -2.0F, 2, 5, 2);
      legPart.field_78795_f = ((float)Math.PI / 2F);
      this.leg5.func_78792_a(legPart);
      this.defaultRotation();
      if (this.field_78089_u != 32) {
         ModelRenderer Snout = new ModelRenderer(this, 0, 32);
         Snout.func_78789_a(0.0F, 0.0F, 0.0F, 4, 3, 3);
         Snout.func_78793_a(-2.0F, -3.0F, -7.0F);
         this.bipedHead.func_78792_a(Snout);
         ModelRenderer Snout2 = new ModelRenderer(this, 0, 38);
         Snout2.func_78789_a(0.0F, 0.0F, 0.0F, 4, 3, 1);
         Snout2.func_78793_a(-2.0F, -3.0F, -5.0F);
         this.bipedHead.func_78792_a(Snout2);
         ModelPlaneRenderer headFin = new ModelPlaneRenderer(this, 14, 32);
         headFin.func_78787_b(64, 64);
         headFin.addSidePlane(0.0F, -12.0F, -1.0F, 9, 9);
         this.bipedHead.func_78792_a(headFin);
         ModelPlaneRenderer backFin = new ModelPlaneRenderer(this, 23, 32);
         backFin.func_78787_b(64, 64);
         backFin.addSidePlane(0.0F, 0.0F, 2.0F, 12, 7);
         this.bipedBody.func_78792_a(backFin);
      }
   }

   private void defaultRotation() {
      this.leg.func_78793_a(0.0F, 14.0F, 0.0F);
      this.leg2.func_78793_a(0.0F, 18.0F, 0.6F);
      this.leg3.func_78793_a(0.0F, 22.0F, -0.3F);
      this.leg4.func_78793_a(0.0F, 22.0F, 5.0F);
      this.leg5.func_78793_a(0.0F, 22.0F, 10.0F);
      this.leg.field_78795_f = 0.0F;
      this.leg2.field_78795_f = 0.0F;
      this.leg3.field_78795_f = 0.0F;
      this.leg4.field_78795_f = 0.0F;
      this.leg5.field_78795_f = 0.0F;
   }

   public void func_78088_a(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
      super.func_78088_a(par1Entity, par2, par3, par4, par5, par6, par7);
      this.leg.func_78785_a(par7);
      this.leg3.func_78785_a(par7);
      if (!this.field_78093_q) {
         this.leg2.func_78785_a(par7);
      }

      GL11.glPushMatrix();
      GL11.glScalef(0.64F, 0.7F, 0.85F);
      GL11.glTranslatef(this.leg3.field_78796_g, 0.66F, 0.06F);
      this.leg4.func_78785_a(par7);
      GL11.glPopMatrix();
      GL11.glPushMatrix();
      GL11.glTranslatef(this.leg3.field_78796_g + this.leg4.field_78796_g, 0.0F, 0.0F);
      this.leg5.func_78785_a(par7);
      GL11.glPopMatrix();
   }

   public void func_78086_a(EntityLivingBase entityliving, float f, float f1, float f2) {
      super.func_78086_a(entityliving, f, f1, f2);
   }

   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6) {
      super.setRotationAngles(par1, par2, par3, par4, par5, par6);
      this.leg.field_78796_g = MathHelper.func_76134_b(par1 * 0.6662F) * 0.26F * par2;
      this.leg2.field_78796_g = MathHelper.func_76134_b(par1 * 0.6662F) * 0.5F * par2;
      this.leg3.field_78796_g = MathHelper.func_76134_b(par1 * 0.6662F) * 0.26F * par2;
      this.leg4.field_78796_g = -MathHelper.func_76134_b(par1 * 0.6662F) * 0.16F * par2;
      this.leg5.field_78796_g = -MathHelper.func_76134_b(par1 * 0.6662F) * 0.3F * par2;
      this.defaultRotation();
      if (this.isSleeping) {
         this.leg3.field_78795_f = (-(float)Math.PI / 2F);
         this.leg4.field_78795_f = (-(float)Math.PI / 2F);
         this.leg5.field_78795_f = (-(float)Math.PI / 2F);
         ModelRenderer var10000 = this.leg3;
         var10000.field_78797_d -= 2.0F;
         this.leg3.field_78798_e = 0.9F;
         var10000 = this.leg4;
         var10000.field_78797_d += 4.0F;
         this.leg4.field_78798_e = 0.9F;
         var10000 = this.leg5;
         var10000.field_78797_d += 7.0F;
         this.leg5.field_78798_e = 2.9F;
      }

      if (this.field_78093_q) {
         --this.leg.field_78797_d;
         this.leg.field_78795_f = -0.19634955F;
         this.leg.field_78798_e = -1.0F;
         ModelRenderer var9 = this.leg2;
         var9.field_78797_d -= 4.0F;
         this.leg2.field_78798_e = -1.0F;
         var9 = this.leg3;
         var9.field_78797_d -= 9.0F;
         --this.leg3.field_78798_e;
         var9 = this.leg4;
         var9.field_78797_d -= 13.0F;
         --this.leg4.field_78798_e;
         var9 = this.leg5;
         var9.field_78797_d -= 9.0F;
         --this.leg5.field_78798_e;
         if (this.isSneak) {
            var9 = this.leg;
            var9.field_78798_e += 5.0F;
            var9 = this.leg3;
            var9.field_78798_e += 5.0F;
            var9 = this.leg4;
            var9.field_78798_e += 5.0F;
            var9 = this.leg5;
            var9.field_78798_e += 4.0F;
            --this.leg.field_78797_d;
            --this.leg2.field_78797_d;
            --this.leg3.field_78797_d;
            --this.leg4.field_78797_d;
            --this.leg5.field_78797_d;
         }
      } else if (this.isSneak) {
         --this.leg.field_78797_d;
         --this.leg2.field_78797_d;
         --this.leg3.field_78797_d;
         --this.leg4.field_78797_d;
         --this.leg5.field_78797_d;
         this.leg.field_78798_e = 5.0F;
         this.leg2.field_78798_e = 3.0F;
      }

   }
}
