package noppes.npcs.client.model.part;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.EntityCustomNpc;
import noppes.npcs.ModelPartData;
import noppes.npcs.client.model.ModelMPM;
import noppes.npcs.client.model.part.legs.ModelDigitigradeLegs;
import noppes.npcs.client.model.part.legs.ModelMermaidLegs;
import noppes.npcs.client.model.part.legs.ModelNagaLegs;
import noppes.npcs.client.model.util.ModelScaleRenderer;
import noppes.npcs.constants.EnumAnimation;
import org.lwjgl.opengl.GL11;

public class ModelLegs extends ModelScaleRenderer {
   private EntityCustomNpc entity;
   private ModelScaleRenderer leg1;
   private ModelScaleRenderer leg2;
   private ModelRenderer spider;
   private ModelRenderer horse;
   private ModelNagaLegs naga;
   private ModelDigitigradeLegs digitigrade;
   private ModelMermaidLegs mermaid;
   private ModelRenderer spiderLeg1;
   private ModelRenderer spiderLeg2;
   private ModelRenderer spiderLeg3;
   private ModelRenderer spiderLeg4;
   private ModelRenderer spiderLeg5;
   private ModelRenderer spiderLeg6;
   private ModelRenderer spiderLeg7;
   private ModelRenderer spiderLeg8;
   private ModelRenderer spiderBody;
   private ModelRenderer spiderNeck;
   private ModelRenderer backLeftLeg;
   private ModelRenderer backLeftShin;
   private ModelRenderer backLeftHoof;
   private ModelRenderer backRightLeg;
   private ModelRenderer backRightShin;
   private ModelRenderer backRightHoof;
   private ModelRenderer frontLeftLeg;
   private ModelRenderer frontLeftShin;
   private ModelRenderer frontLeftHoof;
   private ModelRenderer frontRightLeg;
   private ModelRenderer frontRightShin;
   private ModelRenderer frontRightHoof;
   private ModelMPM base;

   public ModelLegs(ModelMPM base, ModelScaleRenderer leg1, ModelScaleRenderer leg2) {
      super(base);
      this.base = base;
      this.leg1 = leg1;
      this.leg2 = leg2;
      if (!base.isArmor) {
         this.spider = new ModelRenderer(base);
         this.func_78792_a(this.spider);
         float var1 = 0.0F;
         byte var2 = 15;
         this.spiderNeck = new ModelRenderer(base, 0, 0);
         this.spiderNeck.func_78790_a(-3.0F, -3.0F, -3.0F, 6, 6, 6, var1);
         this.spiderNeck.func_78793_a(0.0F, (float)var2, 2.0F);
         this.spider.func_78792_a(this.spiderNeck);
         this.spiderBody = new ModelRenderer(base, 0, 12);
         this.spiderBody.func_78790_a(-5.0F, -4.0F, -6.0F, 10, 8, 12, var1);
         this.spiderBody.func_78793_a(0.0F, (float)var2, 11.0F);
         this.spider.func_78792_a(this.spiderBody);
         this.spiderLeg1 = new ModelRenderer(base, 18, 0);
         this.spiderLeg1.func_78790_a(-15.0F, -1.0F, -1.0F, 16, 2, 2, var1);
         this.spiderLeg1.func_78793_a(-4.0F, (float)var2, 4.0F);
         this.spider.func_78792_a(this.spiderLeg1);
         this.spiderLeg2 = new ModelRenderer(base, 18, 0);
         this.spiderLeg2.func_78790_a(-1.0F, -1.0F, -1.0F, 16, 2, 2, var1);
         this.spiderLeg2.func_78793_a(4.0F, (float)var2, 4.0F);
         this.spider.func_78792_a(this.spiderLeg2);
         this.spiderLeg3 = new ModelRenderer(base, 18, 0);
         this.spiderLeg3.func_78790_a(-15.0F, -1.0F, -1.0F, 16, 2, 2, var1);
         this.spiderLeg3.func_78793_a(-4.0F, (float)var2, 3.0F);
         this.spider.func_78792_a(this.spiderLeg3);
         this.spiderLeg4 = new ModelRenderer(base, 18, 0);
         this.spiderLeg4.func_78790_a(-1.0F, -1.0F, -1.0F, 16, 2, 2, var1);
         this.spiderLeg4.func_78793_a(4.0F, (float)var2, 3.0F);
         this.spider.func_78792_a(this.spiderLeg4);
         this.spiderLeg5 = new ModelRenderer(base, 18, 0);
         this.spiderLeg5.func_78790_a(-15.0F, -1.0F, -1.0F, 16, 2, 2, var1);
         this.spiderLeg5.func_78793_a(-4.0F, (float)var2, 2.0F);
         this.spider.func_78792_a(this.spiderLeg5);
         this.spiderLeg6 = new ModelRenderer(base, 18, 0);
         this.spiderLeg6.func_78790_a(-1.0F, -1.0F, -1.0F, 16, 2, 2, var1);
         this.spiderLeg6.func_78793_a(4.0F, (float)var2, 2.0F);
         this.spider.func_78792_a(this.spiderLeg6);
         this.spiderLeg7 = new ModelRenderer(base, 18, 0);
         this.spiderLeg7.func_78790_a(-15.0F, -1.0F, -1.0F, 16, 2, 2, var1);
         this.spiderLeg7.func_78793_a(-4.0F, (float)var2, 1.0F);
         this.spider.func_78792_a(this.spiderLeg7);
         this.spiderLeg8 = new ModelRenderer(base, 18, 0);
         this.spiderLeg8.func_78790_a(-1.0F, -1.0F, -1.0F, 16, 2, 2, var1);
         this.spiderLeg8.func_78793_a(4.0F, (float)var2, 1.0F);
         this.spider.func_78792_a(this.spiderLeg8);
         int zOffset = 10;
         float yOffset = 7.0F;
         this.horse = new ModelRenderer(base);
         this.func_78792_a(this.horse);
         ModelRenderer body = new ModelRenderer(base, 0, 34);
         body.func_78787_b(128, 128);
         body.func_78789_a(-5.0F, -8.0F, -19.0F, 10, 10, 24);
         body.func_78793_a(0.0F, 11.0F + yOffset, 9.0F + (float)zOffset);
         this.horse.func_78792_a(body);
         this.backLeftLeg = new ModelRenderer(base, 78, 29);
         this.backLeftLeg.func_78787_b(128, 128);
         this.backLeftLeg.func_78789_a(-2.5F, -2.0F, -2.5F, 4, 9, 5);
         this.backLeftLeg.func_78793_a(4.0F, 9.0F + yOffset, 11.0F + (float)zOffset);
         this.horse.func_78792_a(this.backLeftLeg);
         this.backLeftShin = new ModelRenderer(base, 78, 43);
         this.backLeftShin.func_78787_b(128, 128);
         this.backLeftShin.func_78789_a(-2.0F, 0.0F, -1.5F, 3, 5, 3);
         this.backLeftShin.func_78793_a(0.0F, 7.0F, 0.0F);
         this.backLeftLeg.func_78792_a(this.backLeftShin);
         this.backLeftHoof = new ModelRenderer(base, 78, 51);
         this.backLeftHoof.func_78787_b(128, 128);
         this.backLeftHoof.func_78789_a(-2.5F, 5.1F, -2.0F, 4, 3, 4);
         this.backLeftHoof.func_78793_a(0.0F, 7.0F, 0.0F);
         this.backLeftLeg.func_78792_a(this.backLeftHoof);
         this.backRightLeg = new ModelRenderer(base, 96, 29);
         this.backRightLeg.func_78787_b(128, 128);
         this.backRightLeg.func_78789_a(-1.5F, -2.0F, -2.5F, 4, 9, 5);
         this.backRightLeg.func_78793_a(-4.0F, 9.0F + yOffset, 11.0F + (float)zOffset);
         this.horse.func_78792_a(this.backRightLeg);
         this.backRightShin = new ModelRenderer(base, 96, 43);
         this.backRightShin.func_78787_b(128, 128);
         this.backRightShin.func_78789_a(-1.0F, 0.0F, -1.5F, 3, 5, 3);
         this.backRightShin.func_78793_a(0.0F, 7.0F, 0.0F);
         this.backRightLeg.func_78792_a(this.backRightShin);
         this.backRightHoof = new ModelRenderer(base, 96, 51);
         this.backRightHoof.func_78787_b(128, 128);
         this.backRightHoof.func_78789_a(-1.5F, 5.1F, -2.0F, 4, 3, 4);
         this.backRightHoof.func_78793_a(0.0F, 7.0F, 0.0F);
         this.backRightLeg.func_78792_a(this.backRightHoof);
         this.frontLeftLeg = new ModelRenderer(base, 44, 29);
         this.frontLeftLeg.func_78787_b(128, 128);
         this.frontLeftLeg.func_78789_a(-1.9F, -1.0F, -2.1F, 3, 8, 4);
         this.frontLeftLeg.func_78793_a(4.0F, 9.0F + yOffset, -8.0F + (float)zOffset);
         this.horse.func_78792_a(this.frontLeftLeg);
         this.frontLeftShin = new ModelRenderer(base, 44, 41);
         this.frontLeftShin.func_78787_b(128, 128);
         this.frontLeftShin.func_78789_a(-1.9F, 0.0F, -1.6F, 3, 5, 3);
         this.frontLeftShin.func_78793_a(0.0F, 7.0F, 0.0F);
         this.frontLeftLeg.func_78792_a(this.frontLeftShin);
         this.frontLeftHoof = new ModelRenderer(base, 44, 51);
         this.frontLeftHoof.func_78787_b(128, 128);
         this.frontLeftHoof.func_78789_a(-2.4F, 5.1F, -2.1F, 4, 3, 4);
         this.frontLeftHoof.func_78793_a(0.0F, 7.0F, 0.0F);
         this.frontLeftLeg.func_78792_a(this.frontLeftHoof);
         this.frontRightLeg = new ModelRenderer(base, 60, 29);
         this.frontRightLeg.func_78787_b(128, 128);
         this.frontRightLeg.func_78789_a(-1.1F, -1.0F, -2.1F, 3, 8, 4);
         this.frontRightLeg.func_78793_a(-4.0F, 9.0F + yOffset, -8.0F + (float)zOffset);
         this.horse.func_78792_a(this.frontRightLeg);
         this.frontRightShin = new ModelRenderer(base, 60, 41);
         this.frontRightShin.func_78787_b(128, 128);
         this.frontRightShin.func_78789_a(-1.1F, 0.0F, -1.6F, 3, 5, 3);
         this.frontRightShin.func_78793_a(0.0F, 7.0F, 0.0F);
         this.frontRightLeg.func_78792_a(this.frontRightShin);
         this.frontRightHoof = new ModelRenderer(base, 60, 51);
         this.frontRightHoof.func_78787_b(128, 128);
         this.frontRightHoof.func_78789_a(-1.6F, 5.1F, -2.1F, 4, 3, 4);
         this.frontRightHoof.func_78793_a(0.0F, 7.0F, 0.0F);
         this.frontRightLeg.func_78792_a(this.frontRightHoof);
         this.naga = new ModelNagaLegs(base);
         this.func_78792_a(this.naga);
         this.mermaid = new ModelMermaidLegs(base);
         this.func_78792_a(this.mermaid);
         this.digitigrade = new ModelDigitigradeLegs(base);
         this.func_78792_a(this.digitigrade);
      }
   }

   public void setRotation(ModelRenderer model, float x, float y, float z) {
      model.field_78795_f = x;
      model.field_78796_g = y;
      model.field_78808_h = z;
   }

   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity) {
      ModelPartData part = this.entity.legParts;
      this.field_78798_e = 0.0F;
      this.field_78797_d = 0.0F;
      if (!this.base.isArmor) {
         if (part.type == 2) {
            this.field_78795_f = 0.0F;
            this.spiderBody.field_78797_d = 15.0F;
            this.spiderBody.field_78798_e = 11.0F;
            this.spiderNeck.field_78795_f = 0.0F;
            float var8 = ((float)Math.PI / 4F);
            this.spiderLeg1.field_78808_h = -var8;
            this.spiderLeg2.field_78808_h = var8;
            this.spiderLeg3.field_78808_h = -var8 * 0.74F;
            this.spiderLeg4.field_78808_h = var8 * 0.74F;
            this.spiderLeg5.field_78808_h = -var8 * 0.74F;
            this.spiderLeg6.field_78808_h = var8 * 0.74F;
            this.spiderLeg7.field_78808_h = -var8;
            this.spiderLeg8.field_78808_h = var8;
            float var9 = -0.0F;
            float var10 = ((float)Math.PI / 8F);
            this.spiderLeg1.field_78796_g = var10 * 2.0F + var9;
            this.spiderLeg2.field_78796_g = -var10 * 2.0F - var9;
            this.spiderLeg3.field_78796_g = var10 * 1.0F + var9;
            this.spiderLeg4.field_78796_g = -var10 * 1.0F - var9;
            this.spiderLeg5.field_78796_g = -var10 * 1.0F + var9;
            this.spiderLeg6.field_78796_g = var10 * 1.0F - var9;
            this.spiderLeg7.field_78796_g = -var10 * 2.0F + var9;
            this.spiderLeg8.field_78796_g = var10 * 2.0F - var9;
            float var11 = -(MathHelper.func_76134_b(par1 * 0.6662F * 2.0F + 0.0F) * 0.4F) * par2;
            float var12 = -(MathHelper.func_76134_b(par1 * 0.6662F * 2.0F + (float)Math.PI) * 0.4F) * par2;
            float var13 = -(MathHelper.func_76134_b(par1 * 0.6662F * 2.0F + ((float)Math.PI / 2F)) * 0.4F) * par2;
            float var14 = -(MathHelper.func_76134_b(par1 * 0.6662F * 2.0F + ((float)Math.PI * 1.5F)) * 0.4F) * par2;
            float var15 = Math.abs(MathHelper.func_76126_a(par1 * 0.6662F + 0.0F) * 0.4F) * par2;
            float var16 = Math.abs(MathHelper.func_76126_a(par1 * 0.6662F + (float)Math.PI) * 0.4F) * par2;
            float var17 = Math.abs(MathHelper.func_76126_a(par1 * 0.6662F + ((float)Math.PI / 2F)) * 0.4F) * par2;
            float var18 = Math.abs(MathHelper.func_76126_a(par1 * 0.6662F + ((float)Math.PI * 1.5F)) * 0.4F) * par2;
            ModelRenderer var10000 = this.spiderLeg1;
            var10000.field_78796_g += var11;
            var10000 = this.spiderLeg2;
            var10000.field_78796_g += -var11;
            var10000 = this.spiderLeg3;
            var10000.field_78796_g += var12;
            var10000 = this.spiderLeg4;
            var10000.field_78796_g += -var12;
            var10000 = this.spiderLeg5;
            var10000.field_78796_g += var13;
            var10000 = this.spiderLeg6;
            var10000.field_78796_g += -var13;
            var10000 = this.spiderLeg7;
            var10000.field_78796_g += var14;
            var10000 = this.spiderLeg8;
            var10000.field_78796_g += -var14;
            var10000 = this.spiderLeg1;
            var10000.field_78808_h += var15;
            var10000 = this.spiderLeg2;
            var10000.field_78808_h += -var15;
            var10000 = this.spiderLeg3;
            var10000.field_78808_h += var16;
            var10000 = this.spiderLeg4;
            var10000.field_78808_h += -var16;
            var10000 = this.spiderLeg5;
            var10000.field_78808_h += var17;
            var10000 = this.spiderLeg6;
            var10000.field_78808_h += -var17;
            var10000 = this.spiderLeg7;
            var10000.field_78808_h += var18;
            var10000 = this.spiderLeg8;
            var10000.field_78808_h += -var18;
            if (this.base.isSneak) {
               this.field_78798_e = 5.0F;
               this.field_78797_d = -1.0F;
               this.spiderBody.field_78797_d = 16.0F;
               this.spiderBody.field_78798_e = 10.0F;
               this.spiderNeck.field_78795_f = (-(float)Math.PI / 8F);
            }

            if (this.base.isSleeping(entity) || this.entity.currentAnimation == EnumAnimation.CRAWLING) {
               this.field_78797_d = 12.0F * this.entity.legs.scaleY;
               this.field_78798_e = 15.0F * this.entity.legs.scaleY;
               this.field_78795_f = (-(float)Math.PI / 2F);
            }
         } else if (part.type == 3) {
            this.frontLeftLeg.field_78795_f = MathHelper.func_76134_b(par1 * 0.6662F) * 0.4F * par2;
            this.frontRightLeg.field_78795_f = MathHelper.func_76134_b(par1 * 0.6662F + (float)Math.PI) * 0.4F * par2;
            this.backLeftLeg.field_78795_f = MathHelper.func_76134_b(par1 * 0.6662F + (float)Math.PI) * 0.4F * par2;
            this.backRightLeg.field_78795_f = MathHelper.func_76134_b(par1 * 0.6662F) * 0.4F * par2;
         } else if (part.type == 1) {
            this.naga.isRiding = this.base.field_78093_q;
            this.naga.isSleeping = this.base.isSleeping(entity);
            this.naga.isCrawling = this.entity.currentAnimation == EnumAnimation.CRAWLING;
            this.naga.isSneaking = this.base.isSneak;
            this.naga.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
         } else if (part.type == 4) {
            this.mermaid.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
         } else if (part.type == 5) {
            this.digitigrade.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
         }

      }
   }

   public void func_78785_a(float par1) {
      if (this.field_78806_j && !this.field_78807_k) {
         ModelPartData part = this.entity.legParts;
         if (part.type >= 0) {
            GL11.glPushMatrix();
            if (part.type == 4) {
               part.playerTexture = !this.entity.func_70090_H();
            }

            if (!this.base.isArmor) {
               if (!part.playerTexture) {
                  ResourceLocation location = (ResourceLocation)part.getResource();
                  TextureManager texturemanager = Minecraft.func_71410_x().func_110434_K();
                  texturemanager.func_110577_a(location);
                  this.base.currentlyPlayerTexture = false;
               } else if (!this.base.currentlyPlayerTexture) {
                  TextureManager texturemanager = Minecraft.func_71410_x().func_110434_K();
                  texturemanager.func_110577_a((ResourceLocation)this.entity.textureLocation);
                  this.base.currentlyPlayerTexture = true;
               }
            }

            if (part.type == 0 || part.type == 4 && !this.entity.func_70090_H()) {
               this.leg1.setConfig(this.config, this.x, this.y, this.z);
               this.leg1.func_78785_a(par1);
               this.leg2.setConfig(this.config, -this.x, this.y, this.z);
               this.leg2.func_78785_a(par1);
            }

            if (!this.base.isArmor) {
               this.naga.field_78807_k = part.type != 1;
               this.spider.field_78807_k = part.type != 2;
               this.horse.field_78807_k = part.type != 3;
               this.mermaid.field_78807_k = part.type != 4 || !this.entity.func_70090_H();
               this.digitigrade.field_78807_k = part.type != 5;
               if (!this.horse.field_78807_k) {
                  this.x = 0.0F;
                  this.y *= 1.8F;
                  GL11.glScalef(0.9F, 0.9F, 0.9F);
               } else if (!this.spider.field_78807_k) {
                  this.x = 0.0F;
                  this.y *= 2.0F;
               } else if (!this.naga.field_78807_k) {
                  this.x = 0.0F;
                  this.y *= 2.0F;
               } else if (!this.mermaid.field_78807_k || !this.digitigrade.field_78807_k) {
                  this.x = 0.0F;
                  this.y *= 2.0F;
               }
            }

            boolean bo = this.entity.field_70737_aN <= 0 && this.entity.field_70725_aQ <= 0 && !this.base.isArmor;
            if (bo) {
               float red = (float)(this.entity.legParts.color >> 16 & 255) / 255.0F;
               float green = (float)(this.entity.legParts.color >> 8 & 255) / 255.0F;
               float blue = (float)(this.entity.legParts.color & 255) / 255.0F;
               GL11.glColor3f(red, green, blue);
            }

            super.func_78785_a(par1);
            if (bo) {
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            }

            GL11.glPopMatrix();
         }
      }
   }

   public void setData(EntityCustomNpc entity) {
      this.entity = entity;
   }
}
