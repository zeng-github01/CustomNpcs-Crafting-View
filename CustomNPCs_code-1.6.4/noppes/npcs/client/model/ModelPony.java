package noppes.npcs.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import noppes.npcs.client.model.util.ModelPlaneRenderer;
import org.lwjgl.opengl.GL11;

public class ModelPony extends ModelBase {
   private boolean rainboom;
   private float WingRotateAngleX;
   private float WingRotateAngleY;
   private float WingRotateAngleZ;
   private float TailRotateAngleY;
   public ModelRenderer Head;
   public ModelRenderer[] Headpiece;
   public ModelRenderer Helmet;
   public ModelRenderer Body;
   public ModelPlaneRenderer[] Bodypiece;
   public ModelRenderer RightArm;
   public ModelRenderer LeftArm;
   public ModelRenderer RightLeg;
   public ModelRenderer LeftLeg;
   public ModelRenderer unicornarm;
   public ModelPlaneRenderer[] Tail;
   public ModelRenderer[] LeftWing;
   public ModelRenderer[] RightWing;
   public ModelRenderer[] LeftWingExt;
   public ModelRenderer[] RightWingExt;
   public boolean isPegasus;
   public boolean isUnicorn;
   public boolean isFlying;
   public boolean isGlow;
   public boolean isSleeping;
   public boolean isSneak;
   public boolean aimedBow;
   public int heldItemRight;

   public ModelPony(float f) {
      this.init(f, 0.0F);
   }

   public void init(float strech, float f) {
      float f2 = 0.0F;
      float f3 = 0.0F;
      float f4 = 0.0F;
      this.Head = new ModelRenderer(this, 0, 0);
      this.Head.func_78790_a(-4.0F, -4.0F, -6.0F, 8, 8, 8, strech);
      this.Head.func_78793_a(f2, f3 + f, f4);
      this.Headpiece = new ModelRenderer[3];
      this.Headpiece[0] = new ModelRenderer(this, 12, 16);
      this.Headpiece[0].func_78790_a(-4.0F, -6.0F, -1.0F, 2, 2, 2, strech);
      this.Headpiece[0].func_78793_a(f2, f3 + f, f4);
      this.Headpiece[1] = new ModelRenderer(this, 12, 16);
      this.Headpiece[1].func_78790_a(2.0F, -6.0F, -1.0F, 2, 2, 2, strech);
      this.Headpiece[1].func_78793_a(f2, f3 + f, f4);
      this.Headpiece[2] = new ModelRenderer(this, 56, 0);
      this.Headpiece[2].func_78790_a(-0.5F, -10.0F, -4.0F, 1, 4, 1, strech);
      this.Headpiece[2].func_78793_a(f2, f3 + f, f4);
      this.Helmet = new ModelRenderer(this, 32, 0);
      this.Helmet.func_78790_a(-4.0F, -4.0F, -6.0F, 8, 8, 8, strech + 0.5F);
      this.Helmet.func_78793_a(f2, f3, f4);
      float f5 = 0.0F;
      float f6 = 0.0F;
      float f7 = 0.0F;
      this.Body = new ModelRenderer(this, 16, 16);
      this.Body.func_78790_a(-4.0F, 4.0F, -2.0F, 8, 8, 4, strech);
      this.Body.func_78793_a(f5, f6 + f, f7);
      this.Bodypiece = new ModelPlaneRenderer[13];
      this.Bodypiece[0] = new ModelPlaneRenderer(this, 24, 0);
      this.Bodypiece[0].addSidePlane(-4.0F, 4.0F, 2.0F, 8, 8, strech);
      this.Bodypiece[0].func_78793_a(f5, f6 + f, f7);
      this.Bodypiece[1] = new ModelPlaneRenderer(this, 24, 0);
      this.Bodypiece[1].addSidePlane(4.0F, 4.0F, 2.0F, 8, 8, strech);
      this.Bodypiece[1].func_78793_a(f5, f6 + f, f7);
      this.Bodypiece[2] = new ModelPlaneRenderer(this, 24, 0);
      this.Bodypiece[2].addTopPlane(-4.0F, 4.0F, 2.0F, 8, 8, strech);
      this.Bodypiece[2].func_78793_a(f2, f3 + f, f4);
      this.Bodypiece[3] = new ModelPlaneRenderer(this, 24, 0);
      this.Bodypiece[3].addTopPlane(-4.0F, 12.0F, 2.0F, 8, 8, strech);
      this.Bodypiece[3].func_78793_a(f2, f3 + f, f4);
      this.Bodypiece[4] = new ModelPlaneRenderer(this, 0, 20);
      this.Bodypiece[4].addSidePlane(-4.0F, 4.0F, 10.0F, 8, 4, strech);
      this.Bodypiece[4].func_78793_a(f5, f6 + f, f7);
      this.Bodypiece[5] = new ModelPlaneRenderer(this, 0, 20);
      this.Bodypiece[5].addSidePlane(4.0F, 4.0F, 10.0F, 8, 4, strech);
      this.Bodypiece[5].func_78793_a(f5, f6 + f, f7);
      this.Bodypiece[6] = new ModelPlaneRenderer(this, 24, 0);
      this.Bodypiece[6].addTopPlane(-4.0F, 4.0F, 10.0F, 8, 4, strech);
      this.Bodypiece[6].func_78793_a(f2, f3 + f, f4);
      this.Bodypiece[7] = new ModelPlaneRenderer(this, 24, 0);
      this.Bodypiece[7].addTopPlane(-4.0F, 12.0F, 10.0F, 8, 4, strech);
      this.Bodypiece[7].func_78793_a(f2, f3 + f, f4);
      this.Bodypiece[8] = new ModelPlaneRenderer(this, 24, 0);
      this.Bodypiece[8].addBackPlane(-4.0F, 4.0F, 14.0F, 8, 8, strech);
      this.Bodypiece[8].func_78793_a(f2, f3 + f, f4);
      this.Bodypiece[9] = new ModelPlaneRenderer(this, 32, 0);
      this.Bodypiece[9].addTopPlane(-1.0F, 10.0F, 8.0F, 2, 6, strech);
      this.Bodypiece[9].func_78793_a(f2, f3 + f, f4);
      this.Bodypiece[10] = new ModelPlaneRenderer(this, 32, 0);
      this.Bodypiece[10].addTopPlane(-1.0F, 12.0F, 8.0F, 2, 6, strech);
      this.Bodypiece[10].func_78793_a(f2, f3 + f, f4);
      this.Bodypiece[11] = new ModelPlaneRenderer(this, 32, 0);
      this.Bodypiece[11].field_78809_i = true;
      this.Bodypiece[11].addSidePlane(-1.0F, 10.0F, 8.0F, 2, 6, strech);
      this.Bodypiece[11].func_78793_a(f2, f3 + f, f4);
      this.Bodypiece[12] = new ModelPlaneRenderer(this, 32, 0);
      this.Bodypiece[12].addSidePlane(1.0F, 10.0F, 8.0F, 2, 6, strech);
      this.Bodypiece[12].func_78793_a(f2, f3 + f, f4);
      this.RightArm = new ModelRenderer(this, 40, 16);
      this.RightArm.func_78790_a(-2.0F, 4.0F, -2.0F, 4, 12, 4, strech);
      this.RightArm.func_78793_a(-3.0F, 8.0F + f, 0.0F);
      this.LeftArm = new ModelRenderer(this, 40, 16);
      this.LeftArm.field_78809_i = true;
      this.LeftArm.func_78790_a(-2.0F, 4.0F, -2.0F, 4, 12, 4, strech);
      this.LeftArm.func_78793_a(3.0F, 8.0F + f, 0.0F);
      this.RightLeg = new ModelRenderer(this, 40, 16);
      this.RightLeg.func_78790_a(-2.0F, 4.0F, -2.0F, 4, 12, 4, strech);
      this.RightLeg.func_78793_a(-3.0F, 0.0F + f, 0.0F);
      this.LeftLeg = new ModelRenderer(this, 40, 16);
      this.LeftLeg.field_78809_i = true;
      this.LeftLeg.func_78790_a(-2.0F, 4.0F, -2.0F, 4, 12, 4, strech);
      this.LeftLeg.func_78793_a(3.0F, 0.0F + f, 0.0F);
      this.unicornarm = new ModelRenderer(this, 40, 16);
      this.unicornarm.func_78790_a(-3.0F, -2.0F, -2.0F, 4, 12, 4, strech);
      this.unicornarm.func_78793_a(-5.0F, 2.0F + f, 0.0F);
      float f8 = 0.0F;
      float f9 = 8.0F;
      float f10 = -14.0F;
      float f11 = 0.0F - f8;
      float f12 = 10.0F - f9;
      float f13 = 0.0F;
      this.Tail = new ModelPlaneRenderer[10];
      this.Tail[0] = new ModelPlaneRenderer(this, 32, 0);
      this.Tail[0].addTopPlane(-2.0F + f8, -7.0F + f9, 16.0F + f10, 4, 4, strech);
      this.Tail[0].func_78793_a(f11, f12 + f, f13);
      this.Tail[1] = new ModelPlaneRenderer(this, 32, 0);
      this.Tail[1].addTopPlane(-2.0F + f8, 9.0F + f9, 16.0F + f10, 4, 4, strech);
      this.Tail[1].func_78793_a(f11, f12 + f, f13);
      this.Tail[2] = new ModelPlaneRenderer(this, 32, 0);
      this.Tail[2].addBackPlane(-2.0F + f8, -7.0F + f9, 16.0F + f10, 4, 8, strech);
      this.Tail[2].func_78793_a(f11, f12 + f, f13);
      this.Tail[3] = new ModelPlaneRenderer(this, 32, 0);
      this.Tail[3].addBackPlane(-2.0F + f8, -7.0F + f9, 20.0F + f10, 4, 8, strech);
      this.Tail[3].func_78793_a(f11, f12 + f, f13);
      this.Tail[4] = new ModelPlaneRenderer(this, 32, 0);
      this.Tail[4].addBackPlane(-2.0F + f8, 1.0F + f9, 16.0F + f10, 4, 8, strech);
      this.Tail[4].func_78793_a(f11, f12 + f, f13);
      this.Tail[5] = new ModelPlaneRenderer(this, 32, 0);
      this.Tail[5].addBackPlane(-2.0F + f8, 1.0F + f9, 20.0F + f10, 4, 8, strech);
      this.Tail[5].func_78793_a(f11, f12 + f, f13);
      this.Tail[6] = new ModelPlaneRenderer(this, 36, 0);
      this.Tail[6].field_78809_i = true;
      this.Tail[6].addSidePlane(2.0F + f8, -7.0F + f9, 16.0F + f10, 8, 4, strech);
      this.Tail[6].func_78793_a(f11, f12 + f, f13);
      this.Tail[7] = new ModelPlaneRenderer(this, 36, 0);
      this.Tail[7].addSidePlane(-2.0F + f8, -7.0F + f9, 16.0F + f10, 8, 4, strech);
      this.Tail[7].func_78793_a(f11, f12 + f, f13);
      this.Tail[8] = new ModelPlaneRenderer(this, 36, 0);
      this.Tail[8].field_78809_i = true;
      this.Tail[8].addSidePlane(2.0F + f8, 1.0F + f9, 16.0F + f10, 8, 4, strech);
      this.Tail[8].func_78793_a(f11, f12 + f, f13);
      this.Tail[9] = new ModelPlaneRenderer(this, 36, 0);
      this.Tail[9].addSidePlane(-2.0F + f8, 1.0F + f9, 16.0F + f10, 8, 4, strech);
      this.Tail[9].func_78793_a(f11, f12 + f, f13);
      this.TailRotateAngleY = this.Tail[0].field_78796_g;
      this.TailRotateAngleY = this.Tail[0].field_78796_g;
      float f14 = 0.0F;
      float f15 = 0.0F;
      float f16 = 0.0F;
      this.LeftWing = new ModelRenderer[3];
      this.LeftWing[0] = new ModelRenderer(this, 56, 16);
      this.LeftWing[0].field_78809_i = true;
      this.LeftWing[0].func_78790_a(4.0F, 5.0F, 2.0F, 2, 6, 2, strech);
      this.LeftWing[0].func_78793_a(f14, f15 + f, f16);
      this.LeftWing[1] = new ModelRenderer(this, 56, 16);
      this.LeftWing[1].field_78809_i = true;
      this.LeftWing[1].func_78790_a(4.0F, 5.0F, 4.0F, 2, 8, 2, strech);
      this.LeftWing[1].func_78793_a(f14, f15 + f, f16);
      this.LeftWing[2] = new ModelRenderer(this, 56, 16);
      this.LeftWing[2].field_78809_i = true;
      this.LeftWing[2].func_78790_a(4.0F, 5.0F, 6.0F, 2, 6, 2, strech);
      this.LeftWing[2].func_78793_a(f14, f15 + f, f16);
      this.RightWing = new ModelRenderer[3];
      this.RightWing[0] = new ModelRenderer(this, 56, 16);
      this.RightWing[0].func_78790_a(-6.0F, 5.0F, 2.0F, 2, 6, 2, strech);
      this.RightWing[0].func_78793_a(f14, f15 + f, f16);
      this.RightWing[1] = new ModelRenderer(this, 56, 16);
      this.RightWing[1].func_78790_a(-6.0F, 5.0F, 4.0F, 2, 8, 2, strech);
      this.RightWing[1].func_78793_a(f14, f15 + f, f16);
      this.RightWing[2] = new ModelRenderer(this, 56, 16);
      this.RightWing[2].func_78790_a(-6.0F, 5.0F, 6.0F, 2, 6, 2, strech);
      this.RightWing[2].func_78793_a(f14, f15 + f, f16);
      float f17 = f2 + 4.5F;
      float f18 = f3 + 5.0F;
      float f19 = f4 + 6.0F;
      this.LeftWingExt = new ModelRenderer[7];
      this.LeftWingExt[0] = new ModelRenderer(this, 56, 19);
      this.LeftWingExt[0].field_78809_i = true;
      this.LeftWingExt[0].func_78790_a(0.0F, 0.0F, 0.0F, 1, 8, 2, strech + 0.1F);
      this.LeftWingExt[0].func_78793_a(f17, f18 + f, f19);
      this.LeftWingExt[1] = new ModelRenderer(this, 56, 19);
      this.LeftWingExt[1].field_78809_i = true;
      this.LeftWingExt[1].func_78790_a(0.0F, 8.0F, 0.0F, 1, 6, 2, strech + 0.1F);
      this.LeftWingExt[1].func_78793_a(f17, f18 + f, f19);
      this.LeftWingExt[2] = new ModelRenderer(this, 56, 19);
      this.LeftWingExt[2].field_78809_i = true;
      this.LeftWingExt[2].func_78790_a(0.0F, -1.2F, -0.2F, 1, 8, 2, strech - 0.2F);
      this.LeftWingExt[2].func_78793_a(f17, f18 + f, f19);
      this.LeftWingExt[3] = new ModelRenderer(this, 56, 19);
      this.LeftWingExt[3].field_78809_i = true;
      this.LeftWingExt[3].func_78790_a(0.0F, 1.8F, 1.3F, 1, 8, 2, strech - 0.1F);
      this.LeftWingExt[3].func_78793_a(f17, f18 + f, f19);
      this.LeftWingExt[4] = new ModelRenderer(this, 56, 19);
      this.LeftWingExt[4].field_78809_i = true;
      this.LeftWingExt[4].func_78790_a(0.0F, 5.0F, 2.0F, 1, 8, 2, strech);
      this.LeftWingExt[4].func_78793_a(f17, f18 + f, f19);
      this.LeftWingExt[5] = new ModelRenderer(this, 56, 19);
      this.LeftWingExt[5].field_78809_i = true;
      this.LeftWingExt[5].func_78790_a(0.0F, 0.0F, -0.2F, 1, 6, 2, strech + 0.3F);
      this.LeftWingExt[5].func_78793_a(f17, f18 + f, f19);
      this.LeftWingExt[6] = new ModelRenderer(this, 56, 19);
      this.LeftWingExt[6].field_78809_i = true;
      this.LeftWingExt[6].func_78790_a(0.0F, 0.0F, 0.2F, 1, 3, 2, strech + 0.2F);
      this.LeftWingExt[6].func_78793_a(f17, f18 + f, f19);
      float f20 = f2 - 4.5F;
      float f21 = f3 + 5.0F;
      float f22 = f4 + 6.0F;
      this.RightWingExt = new ModelRenderer[7];
      this.RightWingExt[0] = new ModelRenderer(this, 56, 19);
      this.RightWingExt[0].field_78809_i = true;
      this.RightWingExt[0].func_78790_a(0.0F, 0.0F, 0.0F, 1, 8, 2, strech + 0.1F);
      this.RightWingExt[0].func_78793_a(f20, f21 + f, f22);
      this.RightWingExt[1] = new ModelRenderer(this, 56, 19);
      this.RightWingExt[1].field_78809_i = true;
      this.RightWingExt[1].func_78790_a(0.0F, 8.0F, 0.0F, 1, 6, 2, strech + 0.1F);
      this.RightWingExt[1].func_78793_a(f20, f21 + f, f22);
      this.RightWingExt[2] = new ModelRenderer(this, 56, 19);
      this.RightWingExt[2].field_78809_i = true;
      this.RightWingExt[2].func_78790_a(0.0F, -1.2F, -0.2F, 1, 8, 2, strech - 0.2F);
      this.RightWingExt[2].func_78793_a(f20, f21 + f, f22);
      this.RightWingExt[3] = new ModelRenderer(this, 56, 19);
      this.RightWingExt[3].field_78809_i = true;
      this.RightWingExt[3].func_78790_a(0.0F, 1.8F, 1.3F, 1, 8, 2, strech - 0.1F);
      this.RightWingExt[3].func_78793_a(f20, f21 + f, f22);
      this.RightWingExt[4] = new ModelRenderer(this, 56, 19);
      this.RightWingExt[4].field_78809_i = true;
      this.RightWingExt[4].func_78790_a(0.0F, 5.0F, 2.0F, 1, 8, 2, strech);
      this.RightWingExt[4].func_78793_a(f20, f21 + f, f22);
      this.RightWingExt[5] = new ModelRenderer(this, 56, 19);
      this.RightWingExt[5].field_78809_i = true;
      this.RightWingExt[5].func_78790_a(0.0F, 0.0F, -0.2F, 1, 6, 2, strech + 0.3F);
      this.RightWingExt[5].func_78793_a(f20, f21 + f, f22);
      this.RightWingExt[6] = new ModelRenderer(this, 56, 19);
      this.RightWingExt[6].field_78809_i = true;
      this.RightWingExt[6].func_78790_a(0.0F, 0.0F, 0.2F, 1, 3, 2, strech + 0.2F);
      this.RightWingExt[6].func_78793_a(f20, f21 + f, f22);
      this.WingRotateAngleX = this.LeftWingExt[0].field_78795_f;
      this.WingRotateAngleY = this.LeftWingExt[0].field_78796_g;
      this.WingRotateAngleZ = this.LeftWingExt[0].field_78808_h;
   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
      this.rainboom = false;
      float f6;
      float f7;
      if (this.isSleeping) {
         f6 = 1.4F;
         f7 = 0.1F;
      } else {
         f6 = f3 / 57.29578F;
         f7 = f4 / 57.29578F;
      }

      this.Head.field_78796_g = f6;
      this.Head.field_78795_f = f7;
      this.Headpiece[0].field_78796_g = f6;
      this.Headpiece[0].field_78795_f = f7;
      this.Headpiece[1].field_78796_g = f6;
      this.Headpiece[1].field_78795_f = f7;
      this.Headpiece[2].field_78796_g = f6;
      this.Headpiece[2].field_78795_f = f7;
      this.Helmet.field_78796_g = f6;
      this.Helmet.field_78795_f = f7;
      this.Headpiece[2].field_78795_f = f7 + 0.5F;
      float f8;
      float f9;
      float f10;
      float f11;
      if (this.isFlying && this.isPegasus) {
         if (f1 < 0.9999F) {
            this.rainboom = false;
            f8 = MathHelper.func_76126_a(0.0F - f1 * 0.5F);
            f9 = MathHelper.func_76126_a(0.0F - f1 * 0.5F);
            f10 = MathHelper.func_76126_a(f1 * 0.5F);
            f11 = MathHelper.func_76126_a(f1 * 0.5F);
         } else {
            this.rainboom = true;
            f8 = 4.712F;
            f9 = 4.712F;
            f10 = 1.571F;
            f11 = 1.571F;
         }

         this.RightArm.field_78796_g = 0.2F;
         this.LeftArm.field_78796_g = -0.2F;
         this.RightLeg.field_78796_g = -0.2F;
         this.LeftLeg.field_78796_g = 0.2F;
      } else {
         f8 = MathHelper.func_76134_b(f * 0.6662F + 3.141593F) * 0.6F * f1;
         f9 = MathHelper.func_76134_b(f * 0.6662F) * 0.6F * f1;
         f10 = MathHelper.func_76134_b(f * 0.6662F) * 0.3F * f1;
         f11 = MathHelper.func_76134_b(f * 0.6662F + 3.141593F) * 0.3F * f1;
         this.RightArm.field_78796_g = 0.0F;
         this.unicornarm.field_78796_g = 0.0F;
         this.LeftArm.field_78796_g = 0.0F;
         this.RightLeg.field_78796_g = 0.0F;
         this.LeftLeg.field_78796_g = 0.0F;
      }

      if (this.isSleeping) {
         f8 = 4.712F;
         f9 = 4.712F;
         f10 = 1.571F;
         f11 = 1.571F;
      }

      this.RightArm.field_78795_f = f8;
      this.unicornarm.field_78795_f = 0.0F;
      this.LeftArm.field_78795_f = f9;
      this.RightLeg.field_78795_f = f10;
      this.LeftLeg.field_78795_f = f11;
      this.RightArm.field_78808_h = 0.0F;
      this.unicornarm.field_78808_h = 0.0F;
      this.LeftArm.field_78808_h = 0.0F;

      for(int i = 0; i < this.Tail.length; ++i) {
         if (this.rainboom) {
            this.Tail[i].field_78808_h = 0.0F;
         } else {
            this.Tail[i].field_78808_h = MathHelper.func_76134_b(f * 0.8F) * 0.2F * f1;
         }
      }

      if (this.heldItemRight != 0 && !this.rainboom && !this.isUnicorn) {
         this.RightArm.field_78795_f = this.RightArm.field_78795_f * 0.5F - 0.3141593F;
      }

      float f12 = 0.0F;
      if (f5 > -9990.0F && !this.isUnicorn) {
         f12 = MathHelper.func_76126_a(MathHelper.func_76129_c(f5) * 3.141593F * 2.0F) * 0.2F;
      }

      this.Body.field_78796_g = (float)((double)f12 * 0.2);

      for(int j = 0; j < this.Bodypiece.length; ++j) {
         this.Bodypiece[j].field_78796_g = (float)((double)f12 * 0.2);
      }

      for(int k = 0; k < this.LeftWing.length; ++k) {
         this.LeftWing[k].field_78796_g = (float)((double)f12 * 0.2);
      }

      for(int l = 0; l < this.RightWing.length; ++l) {
         this.RightWing[l].field_78796_g = (float)((double)f12 * 0.2);
      }

      for(int i1 = 0; i1 < this.Tail.length; ++i1) {
         this.Tail[i1].field_78796_g = f12;
      }

      float f13 = MathHelper.func_76126_a(this.Body.field_78796_g) * 5.0F;
      float f14 = MathHelper.func_76134_b(this.Body.field_78796_g) * 5.0F;
      float f15 = 4.0F;
      if (this.isSneak && !this.isFlying) {
         f15 = 0.0F;
      }

      if (this.isSleeping) {
         f15 = 2.6F;
      }

      if (this.rainboom) {
         this.RightArm.field_78798_e = f13 + 2.0F;
         this.LeftArm.field_78798_e = 0.0F - f13 + 2.0F;
      } else {
         this.RightArm.field_78798_e = f13 + 1.0F;
         this.LeftArm.field_78798_e = 0.0F - f13 + 1.0F;
      }

      this.RightArm.field_78800_c = 0.0F - f14 - 1.0F + f15;
      this.LeftArm.field_78800_c = f14 + 1.0F - f15;
      this.RightLeg.field_78800_c = 0.0F - f14 - 1.0F + f15;
      this.LeftLeg.field_78800_c = f14 + 1.0F - f15;
      ModelRenderer var10000 = this.RightArm;
      var10000.field_78796_g += this.Body.field_78796_g;
      var10000 = this.LeftArm;
      var10000.field_78796_g += this.Body.field_78796_g;
      var10000 = this.LeftArm;
      var10000.field_78795_f += this.Body.field_78796_g;
      this.RightArm.field_78797_d = 8.0F;
      this.LeftArm.field_78797_d = 8.0F;
      this.RightLeg.field_78797_d = 4.0F;
      this.LeftLeg.field_78797_d = 4.0F;
      if (f5 > -9990.0F) {
         float f16 = 1.0F - f5;
         f16 *= f16 * f16;
         f16 = 1.0F - f16;
         float f22 = MathHelper.func_76126_a(f16 * 3.141593F);
         float f28 = MathHelper.func_76126_a(f5 * 3.141593F);
         float f33 = f28 * -(this.Head.field_78795_f - 0.7F) * 0.75F;
         if (this.isUnicorn) {
            var10000 = this.unicornarm;
            var10000.field_78795_f = (float)((double)var10000.field_78795_f - ((double)f22 * 1.2 + (double)f33));
            var10000 = this.unicornarm;
            var10000.field_78796_g += this.Body.field_78796_g * 2.0F;
            this.unicornarm.field_78808_h = f28 * -0.4F;
         } else {
            var10000 = this.unicornarm;
            var10000.field_78795_f = (float)((double)var10000.field_78795_f - ((double)f22 * 1.2 + (double)f33));
            var10000 = this.unicornarm;
            var10000.field_78796_g += this.Body.field_78796_g * 2.0F;
            this.unicornarm.field_78808_h = f28 * -0.4F;
         }
      }

      if (this.isSneak && !this.isFlying) {
         float f17 = 0.4F;
         float f23 = 7.0F;
         float f29 = -4.0F;
         this.Body.field_78795_f = f17;
         this.Body.field_78797_d = f23;
         this.Body.field_78798_e = f29;

         for(int i3 = 0; i3 < this.Bodypiece.length; ++i3) {
            this.Bodypiece[i3].field_78795_f = f17;
            this.Bodypiece[i3].field_78797_d = f23;
            this.Bodypiece[i3].field_78798_e = f29;
         }

         float f34 = 3.5F;
         float f37 = 6.0F;

         for(int i4 = 0; i4 < this.LeftWingExt.length; ++i4) {
            this.LeftWingExt[i4].field_78795_f = (float)((double)f17 + (double)2.3561947F);
            this.LeftWingExt[i4].field_78797_d = f23 + f34;
            this.LeftWingExt[i4].field_78798_e = f29 + f37;
            this.LeftWingExt[i4].field_78795_f = 2.5F;
            this.LeftWingExt[i4].field_78808_h = -6.0F;
         }

         float f40 = 4.5F;
         float f43 = 6.0F;

         for(int i5 = 0; i5 < this.LeftWingExt.length; ++i5) {
            this.RightWingExt[i5].field_78795_f = (float)((double)f17 + (double)2.3561947F);
            this.RightWingExt[i5].field_78797_d = f23 + f40;
            this.RightWingExt[i5].field_78798_e = f29 + f43;
            this.RightWingExt[i5].field_78795_f = 2.5F;
            this.RightWingExt[i5].field_78808_h = 6.0F;
         }

         var10000 = this.RightLeg;
         var10000.field_78795_f -= 0.0F;
         var10000 = this.LeftLeg;
         var10000.field_78795_f -= 0.0F;
         var10000 = this.RightArm;
         var10000.field_78795_f -= 0.4F;
         var10000 = this.unicornarm;
         var10000.field_78795_f += 0.4F;
         var10000 = this.LeftArm;
         var10000.field_78795_f -= 0.4F;
         this.RightLeg.field_78798_e = 10.0F;
         this.LeftLeg.field_78798_e = 10.0F;
         this.RightLeg.field_78797_d = 7.0F;
         this.LeftLeg.field_78797_d = 7.0F;
         float f46;
         float f48;
         float f50;
         if (this.isSleeping) {
            f46 = 2.0F;
            f48 = -1.0F;
            f50 = 1.0F;
         } else {
            f46 = 6.0F;
            f48 = -2.0F;
            f50 = 0.0F;
         }

         this.Head.field_78797_d = f46;
         this.Head.field_78798_e = f48;
         this.Head.field_78800_c = f50;
         this.Helmet.field_78797_d = f46;
         this.Helmet.field_78798_e = f48;
         this.Helmet.field_78800_c = f50;
         this.Headpiece[0].field_78797_d = f46;
         this.Headpiece[0].field_78798_e = f48;
         this.Headpiece[0].field_78800_c = f50;
         this.Headpiece[1].field_78797_d = f46;
         this.Headpiece[1].field_78798_e = f48;
         this.Headpiece[1].field_78800_c = f50;
         this.Headpiece[2].field_78797_d = f46;
         this.Headpiece[2].field_78798_e = f48;
         this.Headpiece[2].field_78800_c = f50;
         float f52 = 0.0F;
         float f54 = 8.0F;
         float f56 = -14.0F;
         float f58 = 0.0F - f52;
         float f60 = 9.0F - f54;
         float f62 = -4.0F - f56;
         float f63 = 0.0F;

         for(int i6 = 0; i6 < this.Tail.length; ++i6) {
            this.Tail[i6].field_78800_c = f58;
            this.Tail[i6].field_78797_d = f60;
            this.Tail[i6].field_78798_e = f62;
            this.Tail[i6].field_78795_f = f63;
         }
      } else {
         float f18 = 0.0F;
         float f24 = 0.0F;
         float f30 = 0.0F;
         this.Body.field_78795_f = f18;
         this.Body.field_78797_d = f24;
         this.Body.field_78798_e = f30;

         for(int j3 = 0; j3 < this.Bodypiece.length; ++j3) {
            this.Bodypiece[j3].field_78795_f = f18;
            this.Bodypiece[j3].field_78797_d = f24;
            this.Bodypiece[j3].field_78798_e = f30;
         }

         if (this.isPegasus) {
            if (!this.isFlying) {
               for(int k3 = 0; k3 < this.LeftWing.length; ++k3) {
                  this.LeftWing[k3].field_78795_f = (float)((double)f18 + (double)1.5707965F);
                  this.LeftWing[k3].field_78797_d = f24 + 13.0F;
                  this.LeftWing[k3].field_78798_e = f30 - 3.0F;
               }

               for(int l3 = 0; l3 < this.RightWing.length; ++l3) {
                  this.RightWing[l3].field_78795_f = (float)((double)f18 + (double)1.5707965F);
                  this.RightWing[l3].field_78797_d = f24 + 13.0F;
                  this.RightWing[l3].field_78798_e = f30 - 3.0F;
               }
            } else {
               float f35 = 5.5F;
               float f38 = 3.0F;

               for(int j4 = 0; j4 < this.LeftWingExt.length; ++j4) {
                  this.LeftWingExt[j4].field_78795_f = (float)((double)f18 + (double)1.5707965F);
                  this.LeftWingExt[j4].field_78797_d = f24 + f35;
                  this.LeftWingExt[j4].field_78798_e = f30 + f38;
               }

               float f41 = 6.5F;
               float f44 = 3.0F;

               for(int j5 = 0; j5 < this.RightWingExt.length; ++j5) {
                  this.RightWingExt[j5].field_78795_f = (float)((double)f18 + (double)1.5707965F);
                  this.RightWingExt[j5].field_78797_d = f24 + f41;
                  this.RightWingExt[j5].field_78798_e = f30 + f44;
               }
            }
         }

         this.RightLeg.field_78798_e = 10.0F;
         this.LeftLeg.field_78798_e = 10.0F;
         this.RightLeg.field_78797_d = 8.0F;
         this.LeftLeg.field_78797_d = 8.0F;
         float f36 = MathHelper.func_76134_b(f2 * 0.09F) * 0.05F + 0.05F;
         float f39 = MathHelper.func_76126_a(f2 * 0.067F) * 0.05F;
         var10000 = this.unicornarm;
         var10000.field_78808_h += f36;
         var10000 = this.unicornarm;
         var10000.field_78795_f += f39;
         if (this.isPegasus && this.isFlying) {
            this.WingRotateAngleY = MathHelper.func_76126_a(f2 * 0.067F * 8.0F) * 1.0F;
            this.WingRotateAngleZ = MathHelper.func_76126_a(f2 * 0.067F * 8.0F) * 1.0F;

            for(int k4 = 0; k4 < this.LeftWingExt.length; ++k4) {
               this.LeftWingExt[k4].field_78795_f = 2.5F;
               this.LeftWingExt[k4].field_78808_h = -this.WingRotateAngleZ - 4.712F - 0.4F;
            }

            for(int l4 = 0; l4 < this.RightWingExt.length; ++l4) {
               this.RightWingExt[l4].field_78795_f = 2.5F;
               this.RightWingExt[l4].field_78808_h = this.WingRotateAngleZ + 4.712F + 0.4F;
            }
         }

         float f42;
         float f45;
         float f47;
         if (this.isSleeping) {
            f42 = 2.0F;
            f45 = 1.0F;
            f47 = 1.0F;
         } else {
            f42 = 0.0F;
            f45 = 0.0F;
            f47 = 0.0F;
         }

         this.Head.field_78797_d = f42;
         this.Head.field_78798_e = f45;
         this.Head.field_78800_c = f47;
         this.Helmet.field_78797_d = f42;
         this.Helmet.field_78798_e = f45;
         this.Helmet.field_78800_c = f47;
         this.Headpiece[0].field_78797_d = f42;
         this.Headpiece[0].field_78798_e = f45;
         this.Headpiece[0].field_78800_c = f47;
         this.Headpiece[1].field_78797_d = f42;
         this.Headpiece[1].field_78798_e = f45;
         this.Headpiece[1].field_78800_c = f47;
         this.Headpiece[2].field_78797_d = f42;
         this.Headpiece[2].field_78798_e = f45;
         this.Headpiece[2].field_78800_c = f47;
         float f49 = 0.0F;
         float f51 = 8.0F;
         float f53 = -14.0F;
         float f55 = 0.0F - f49;
         float f57 = 9.0F - f51;
         float f59 = 0.0F - f53;
         float f61 = 0.5F * f1;

         for(int k5 = 0; k5 < this.Tail.length; ++k5) {
            this.Tail[k5].field_78800_c = f55;
            this.Tail[k5].field_78797_d = f57;
            this.Tail[k5].field_78798_e = f59;
            if (this.rainboom) {
               this.Tail[k5].field_78795_f = 1.571F + 0.1F * MathHelper.func_76126_a(f);
            } else {
               this.Tail[k5].field_78795_f = f61;
            }
         }

         for(int l5 = 0; l5 < this.Tail.length; ++l5) {
            if (!this.rainboom) {
               var10000 = this.Tail[l5];
               var10000.field_78795_f += f39;
            }
         }
      }

      this.LeftWingExt[2].field_78795_f -= 0.85F;
      this.LeftWingExt[3].field_78795_f -= 0.75F;
      this.LeftWingExt[4].field_78795_f -= 0.5F;
      this.LeftWingExt[6].field_78795_f -= 0.85F;
      this.RightWingExt[2].field_78795_f -= 0.85F;
      this.RightWingExt[3].field_78795_f -= 0.75F;
      this.RightWingExt[4].field_78795_f -= 0.5F;
      this.RightWingExt[6].field_78795_f -= 0.85F;
      this.Bodypiece[9].field_78795_f += 0.5F;
      this.Bodypiece[10].field_78795_f += 0.5F;
      this.Bodypiece[11].field_78795_f += 0.5F;
      this.Bodypiece[12].field_78795_f += 0.5F;
      if (this.rainboom) {
         for(int j1 = 0; j1 < this.Tail.length; ++j1) {
            this.Tail[j1].field_78797_d += 6.0F;
            ++this.Tail[j1].field_78798_e;
         }
      }

      if (this.isSleeping) {
         this.RightArm.field_78798_e += 6.0F;
         this.LeftArm.field_78798_e += 6.0F;
         this.RightLeg.field_78798_e -= 8.0F;
         this.LeftLeg.field_78798_e -= 8.0F;
         this.RightArm.field_78797_d += 2.0F;
         this.LeftArm.field_78797_d += 2.0F;
         this.RightLeg.field_78797_d += 2.0F;
         this.LeftLeg.field_78797_d += 2.0F;
      }

      if (this.aimedBow) {
         if (this.isUnicorn) {
            float f20 = 0.0F;
            float f26 = 0.0F;
            this.unicornarm.field_78808_h = 0.0F;
            this.unicornarm.field_78796_g = -(0.1F - f20 * 0.6F) + this.Head.field_78796_g;
            this.unicornarm.field_78795_f = 4.712F + this.Head.field_78795_f;
            var10000 = this.unicornarm;
            var10000.field_78795_f -= f20 * 1.2F - f26 * 0.4F;
            var10000 = this.unicornarm;
            var10000.field_78808_h += MathHelper.func_76134_b(f2 * 0.09F) * 0.05F + 0.05F;
            var10000 = this.unicornarm;
            var10000.field_78795_f += MathHelper.func_76126_a(f2 * 0.067F) * 0.05F;
         } else {
            float f21 = 0.0F;
            float f27 = 0.0F;
            this.RightArm.field_78808_h = 0.0F;
            this.RightArm.field_78796_g = -(0.1F - f21 * 0.6F) + this.Head.field_78796_g;
            this.RightArm.field_78795_f = 4.712F + this.Head.field_78795_f;
            var10000 = this.RightArm;
            var10000.field_78795_f -= f21 * 1.2F - f27 * 0.4F;
            var10000 = this.RightArm;
            var10000.field_78808_h += MathHelper.func_76134_b(f2 * 0.09F) * 0.05F + 0.05F;
            var10000 = this.RightArm;
            var10000.field_78795_f += MathHelper.func_76126_a(f2 * 0.067F) * 0.05F;
            ++this.RightArm.field_78798_e;
         }
      }

   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.setRotationAngles(f, f1, f2, f3, f4, f5);
      GL11.glPushMatrix();
      if (this.isSleeping) {
         GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
         GL11.glTranslatef(0.0F, -0.5F, -0.9F);
      }

      float scale = f5;
      this.Head.func_78785_a(f5);
      this.Headpiece[0].func_78785_a(f5);
      this.Headpiece[1].func_78785_a(f5);
      if (this.isUnicorn) {
         this.Headpiece[2].func_78785_a(f5);
      }

      this.Helmet.func_78785_a(f5);
      this.Body.func_78785_a(f5);

      for(int i = 0; i < this.Bodypiece.length; ++i) {
         this.Bodypiece[i].func_78785_a(scale);
      }

      this.LeftArm.func_78785_a(scale);
      this.RightArm.func_78785_a(scale);
      this.LeftLeg.func_78785_a(scale);
      this.RightLeg.func_78785_a(scale);

      for(int j = 0; j < this.Tail.length; ++j) {
         this.Tail[j].func_78785_a(scale);
      }

      if (this.isPegasus) {
         if (!this.isFlying && !this.isSneak) {
            for(int i1 = 0; i1 < this.LeftWing.length; ++i1) {
               this.LeftWing[i1].func_78785_a(scale);
            }

            for(int j1 = 0; j1 < this.RightWing.length; ++j1) {
               this.RightWing[j1].func_78785_a(scale);
            }
         } else {
            for(int k = 0; k < this.LeftWingExt.length; ++k) {
               this.LeftWingExt[k].func_78785_a(scale);
            }

            for(int l = 0; l < this.RightWingExt.length; ++l) {
               this.RightWingExt[l].func_78785_a(scale);
            }
         }
      }

      GL11.glPopMatrix();
   }

   protected void renderGlow(RenderManager rendermanager, EntityPlayer entityplayer) {
      ItemStack itemstack = entityplayer.field_71071_by.func_70448_g();
      if (itemstack != null) {
         GL11.glPushMatrix();
         double d = entityplayer.field_70165_t;
         double d1 = entityplayer.field_70163_u;
         double d2 = entityplayer.field_70161_v;
         GL11.glEnable(32826);
         GL11.glTranslatef((float)d + 0.0F, (float)d1 + 2.3F, (float)d2);
         GL11.glScalef(5.0F, 5.0F, 5.0F);
         GL11.glRotatef(-rendermanager.field_78735_i, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(rendermanager.field_78732_j, 1.0F, 0.0F, 0.0F);
         Tessellator tessellator = Tessellator.field_78398_a;
         float f = 0.0F;
         float f1 = 0.25F;
         float f2 = 0.0F;
         float f3 = 0.25F;
         float f4 = 1.0F;
         float f5 = 0.5F;
         float f6 = 0.25F;
         tessellator.func_78382_b();
         tessellator.func_78375_b(0.0F, 1.0F, 0.0F);
         tessellator.func_78374_a((double)-1.0F, (double)-1.0F, (double)0.0F, (double)0.0F, (double)1.0F);
         tessellator.func_78374_a((double)-1.0F, (double)1.0F, (double)0.0F, (double)1.0F, (double)1.0F);
         tessellator.func_78374_a((double)1.0F, (double)1.0F, (double)0.0F, (double)1.0F, (double)0.0F);
         tessellator.func_78374_a((double)1.0F, (double)-1.0F, (double)0.0F, (double)0.0F, (double)0.0F);
         tessellator.func_78381_a();
         GL11.glDisable(32826);
         GL11.glPopMatrix();
      }
   }
}
