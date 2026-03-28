package noppes.npcs.client.model;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.EntityCustomNpc;
import noppes.npcs.ModelPartConfig;
import noppes.npcs.ModelPartData;
import noppes.npcs.client.model.animation.AniCrawling;
import noppes.npcs.client.model.animation.AniHug;
import noppes.npcs.client.model.part.ModelBeard;
import noppes.npcs.client.model.part.ModelBreasts;
import noppes.npcs.client.model.part.ModelClaws;
import noppes.npcs.client.model.part.ModelEars;
import noppes.npcs.client.model.part.ModelFin;
import noppes.npcs.client.model.part.ModelHair;
import noppes.npcs.client.model.part.ModelHeadwear;
import noppes.npcs.client.model.part.ModelLegs;
import noppes.npcs.client.model.part.ModelMohawk;
import noppes.npcs.client.model.part.ModelSnout;
import noppes.npcs.client.model.part.ModelTail;
import noppes.npcs.client.model.part.ModelWings;
import noppes.npcs.client.model.util.ModelPartInterface;
import noppes.npcs.client.model.util.ModelScaleRenderer;
import noppes.npcs.constants.EnumAnimation;
import org.lwjgl.opengl.GL11;

public class ModelMPM extends ModelNPCMale {
   private ModelPartInterface wings;
   private ModelPartInterface mohawk;
   private ModelPartInterface hair;
   private ModelPartInterface beard;
   private ModelPartInterface breasts;
   private ModelPartInterface snout;
   private ModelPartInterface ears;
   private ModelPartInterface fin;
   private ModelPartInterface clawsR;
   private ModelPartInterface clawsL;
   private ModelLegs legs;
   private ModelScaleRenderer headwear;
   private ModelTail tail;
   public boolean currentlyPlayerTexture;
   public boolean isArmor;

   public ModelMPM(float par1) {
      super(par1);
      this.isArmor = par1 > 0.0F;
      float par2 = 0.0F;
      this.bipedCloak = new ModelRenderer(this, 0, 0);
      this.bipedCloak.func_78790_a(-5.0F, 0.0F, -1.0F, 10, 16, 1, par1);
      this.bipedEars = new ModelRenderer(this, 24, 0);
      this.bipedEars.func_78790_a(-3.0F, -6.0F, -1.0F, 6, 6, 1, par1);
      this.bipedHead = new ModelScaleRenderer(this, 0, 0);
      this.bipedHead.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, par1);
      this.bipedHead.func_78793_a(0.0F, 0.0F + par2, 0.0F);
      this.bipedHeadwear = new ModelScaleRenderer(this, 32, 0);
      this.bipedHeadwear.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, par1 + 0.5F);
      this.bipedHeadwear.func_78793_a(0.0F, 0.0F + par2, 0.0F);
      this.bipedBody = new ModelScaleRenderer(this, 16, 16);
      this.bipedBody.func_78790_a(-4.0F, 0.0F, -2.0F, 8, 12, 4, par1);
      this.bipedBody.func_78793_a(0.0F, 0.0F + par2, 0.0F);
      this.bipedRightArm = new ModelScaleRenderer(this, 40, 16);
      this.bipedRightArm.func_78790_a(-3.0F, -2.0F, -2.0F, 4, 12, 4, par1);
      this.bipedRightArm.func_78793_a(-5.0F, 2.0F + par2, 0.0F);
      this.bipedLeftArm = new ModelScaleRenderer(this, 40, 16);
      this.bipedLeftArm.field_78809_i = true;
      this.bipedLeftArm.func_78790_a(-1.0F, -2.0F, -2.0F, 4, 12, 4, par1);
      this.bipedLeftArm.func_78793_a(5.0F, 2.0F + par2, 0.0F);
      this.bipedRightLeg = new ModelScaleRenderer(this, 0, 16);
      this.bipedRightLeg.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, par1);
      this.bipedRightLeg.func_78793_a(-1.9F, 12.0F + par2, 0.0F);
      this.bipedLeftLeg = new ModelScaleRenderer(this, 0, 16);
      this.bipedLeftLeg.field_78809_i = true;
      this.bipedLeftLeg.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, par1);
      this.bipedLeftLeg.func_78793_a(1.9F, 12.0F + par2, 0.0F);
      this.headwear = new ModelHeadwear(this);
      this.legs = new ModelLegs(this, (ModelScaleRenderer)this.bipedRightLeg, (ModelScaleRenderer)this.bipedLeftLeg);
      this.breasts = new ModelBreasts(this);
      this.bipedBody.func_78792_a(this.breasts);
      if (!this.isArmor) {
         this.ears = new ModelEars(this);
         this.bipedHead.func_78792_a(this.ears);
         this.mohawk = new ModelMohawk(this);
         this.bipedHead.func_78792_a(this.mohawk);
         this.hair = new ModelHair(this);
         this.bipedHead.func_78792_a(this.hair);
         this.beard = new ModelBeard(this);
         this.bipedHead.func_78792_a(this.beard);
         this.snout = new ModelSnout(this);
         this.bipedHead.func_78792_a(this.snout);
         this.tail = new ModelTail(this);
         this.wings = new ModelWings(this);
         this.bipedBody.func_78792_a(this.wings);
         this.fin = new ModelFin(this);
         this.bipedBody.func_78792_a(this.fin);
         this.clawsL = new ModelClaws(this, false);
         this.bipedLeftArm.func_78792_a(this.clawsL);
         this.clawsR = new ModelClaws(this, true);
         this.bipedRightArm.func_78792_a(this.clawsR);
      }

   }

   private void setPlayerData(EntityCustomNpc entity) {
      if (!this.isArmor) {
         this.mohawk.setData(entity);
         this.beard.setData(entity);
         this.hair.setData(entity);
         this.snout.setData(entity);
         this.tail.setData(entity);
         this.fin.setData(entity);
         this.wings.setData(entity);
         this.ears.setData(entity);
         this.clawsL.setData(entity);
         this.clawsR.setData(entity);
      }

      this.breasts.setData(entity);
      this.legs.setData(entity);
   }

   public void func_78088_a(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
      EntityCustomNpc npc = (EntityCustomNpc)par1Entity;
      this.setPlayerData(npc);
      this.currentlyPlayerTexture = true;
      this.func_78087_a(par2, par3, par4, par5, par6, par7, par1Entity);
      if (npc.currentAnimation == EnumAnimation.Aiming) {
         GL11.glPushMatrix();
         float ticks = (float)(par1Entity.field_70173_aa - npc.animationStart) / 10.0F;
         if (ticks > 1.0F) {
            ticks = 1.0F;
         }

         float scale = 2.0F - npc.body.scaleY;
         GL11.glTranslatef(0.0F, 12.0F * scale * par7, 0.0F);
         GL11.glRotatef(60.0F * ticks, 1.0F, 0.0F, 0.0F);
         GL11.glTranslatef(0.0F, -12.0F * scale * par7, 0.0F);
      }

      this.renderHead(npc, par7);
      this.renderArms(npc, par7, false);
      this.renderBody(npc, par7);
      if (npc.currentAnimation == EnumAnimation.Aiming) {
         GL11.glPopMatrix();
      }

      this.renderLegs(npc, par7);
   }

   public void func_78087_a(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity) {
      EntityCustomNpc npc = (EntityCustomNpc)entity;
      if (!this.field_78093_q) {
         this.field_78093_q = npc.currentAnimation == EnumAnimation.SITTING;
      }

      if (this.isSneak && (npc.currentAnimation == EnumAnimation.CRAWLING || npc.isSleeping())) {
         this.isSneak = false;
      }

      this.bipedBody.field_78798_e = 0.0F;
      this.bipedBody.field_78797_d = 0.0F;
      this.bipedHead.field_78808_h = 0.0F;
      this.bipedHeadwear.field_78808_h = 0.0F;
      this.bipedLeftLeg.field_78795_f = 0.0F;
      this.bipedLeftLeg.field_78796_g = 0.0F;
      this.bipedLeftLeg.field_78808_h = 0.0F;
      this.bipedRightLeg.field_78795_f = 0.0F;
      this.bipedRightLeg.field_78796_g = 0.0F;
      this.bipedRightLeg.field_78808_h = 0.0F;
      this.bipedLeftArm.field_78797_d = 2.0F;
      this.bipedLeftArm.field_78798_e = 0.0F;
      this.bipedRightArm.field_78797_d = 2.0F;
      this.bipedRightArm.field_78798_e = 0.0F;
      super.func_78087_a(par1, par2, par3, par4, par5, par6, entity);
      if (!this.isArmor) {
         this.hair.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
         this.beard.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
         this.wings.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
         this.tail.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
      }

      this.legs.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
      if (this.isSleeping(entity)) {
         if (this.bipedHead.field_78795_f < 0.0F) {
            this.bipedHead.field_78795_f = 0.0F;
            this.bipedHeadwear.field_78795_f = 0.0F;
         }
      } else if (npc.currentAnimation == EnumAnimation.CRY) {
         this.bipedHeadwear.field_78795_f = this.bipedHead.field_78795_f = 0.7F;
      } else if (npc.currentAnimation == EnumAnimation.HUG) {
         AniHug.setRotationAngles(par1, par2, par3, par4, par5, par6, entity, this);
      } else if (npc.currentAnimation == EnumAnimation.CRAWLING) {
         AniCrawling.setRotationAngles(par1, par2, par3, par4, par5, par6, entity, this);
      } else if (npc.currentAnimation == EnumAnimation.WAVING) {
         this.bipedRightArm.field_78795_f = -0.1F;
         this.bipedRightArm.field_78796_g = 0.0F;
         this.bipedRightArm.field_78808_h = (float)(2.141592653589793 - Math.sin((double)((float)entity.field_70173_aa * 0.27F)) * (double)0.5F);
      } else if (this.isSneak) {
         this.bipedBody.field_78795_f = 0.5F / npc.body.scaleY;
      }

   }

   public void func_78086_a(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4) {
      EntityCustomNpc npc = (EntityCustomNpc)par1EntityLivingBase;
      if (!this.isArmor) {
         ModelPartData partData = npc.getPartData("tail");
         if (partData != null) {
            this.tail.setLivingAnimations(partData, par1EntityLivingBase, par2, par3, par4);
         }
      }

   }

   public void loadPlayerTexture(EntityCustomNpc npc) {
      if (!this.isArmor && !this.currentlyPlayerTexture) {
         TextureManager texturemanager = Minecraft.func_71410_x().func_110434_K();
         texturemanager.func_110577_a((ResourceLocation)npc.textureLocation);
         this.currentlyPlayerTexture = true;
      }

   }

   private void renderHead(EntityCustomNpc entity, float f) {
      this.loadPlayerTexture(entity);
      float x = 0.0F;
      float y = entity.getBodyY();
      float z = 0.0F;
      GL11.glPushMatrix();
      if (entity.currentAnimation == EnumAnimation.DANCING) {
         float dancing = (float)entity.field_70173_aa / 4.0F;
         GL11.glTranslatef((float)Math.sin((double)dancing) * 0.075F, (float)Math.abs(Math.cos((double)dancing)) * 0.125F - 0.02F, (float)(-Math.abs(Math.cos((double)dancing))) * 0.075F);
      }

      ModelPartConfig head = entity.head;
      ((ModelScaleRenderer)this.bipedHeadwear).setConfig(head, x, y, z);
      ((ModelScaleRenderer)this.bipedHeadwear).func_78785_a(f);
      ((ModelScaleRenderer)this.bipedHead).setConfig(head, x, y, z);
      ((ModelScaleRenderer)this.bipedHead).func_78785_a(f);
      GL11.glPopMatrix();
   }

   private void renderBody(EntityCustomNpc entity, float f) {
      this.loadPlayerTexture(entity);
      float x = 0.0F;
      float y = entity.getBodyY();
      float z = 0.0F;
      GL11.glPushMatrix();
      if (entity.currentAnimation == EnumAnimation.DANCING) {
         float dancing = (float)entity.field_70173_aa / 4.0F;
         GL11.glTranslatef((float)Math.sin((double)dancing) * 0.015F, 0.0F, 0.0F);
      }

      ModelPartConfig body = entity.body;
      ((ModelScaleRenderer)this.bipedBody).setConfig(body, x, y, z);
      ((ModelScaleRenderer)this.bipedBody).func_78785_a(f);
      GL11.glPopMatrix();
   }

   public void renderArms(EntityCustomNpc entity, float f, boolean bo) {
      this.loadPlayerTexture(entity);
      ModelPartConfig arms = entity.arms;
      float x = (1.0F - entity.body.scaleX) * 0.25F + (1.0F - arms.scaleX) * 0.075F;
      float y = entity.getBodyY() + (1.0F - arms.scaleY) * -0.1F;
      float z = 0.0F;
      GL11.glPushMatrix();
      if (entity.currentAnimation == EnumAnimation.DANCING) {
         float dancing = (float)entity.field_70173_aa / 4.0F;
         GL11.glTranslatef((float)Math.sin((double)dancing) * 0.025F, (float)Math.abs(Math.cos((double)dancing)) * 0.125F - 0.02F, 0.0F);
      }

      if (!bo) {
         ((ModelScaleRenderer)this.bipedLeftArm).setConfig(arms, -x, y, z);
         ((ModelScaleRenderer)this.bipedLeftArm).func_78785_a(f);
         ((ModelScaleRenderer)this.bipedRightArm).setConfig(arms, x, y, z);
         ((ModelScaleRenderer)this.bipedRightArm).func_78785_a(f);
      } else {
         ((ModelScaleRenderer)this.bipedRightArm).setConfig(arms, 0.0F, 0.0F, 0.0F);
         ((ModelScaleRenderer)this.bipedRightArm).func_78785_a(f);
      }

      GL11.glPopMatrix();
   }

   private void renderLegs(EntityCustomNpc entity, float f) {
      this.loadPlayerTexture(entity);
      ModelPartConfig legs = entity.legs;
      float x = (1.0F - legs.scaleX) * 0.125F;
      float y = entity.getLegsY();
      float z = 0.0F;
      GL11.glPushMatrix();
      this.legs.setConfig(legs, x, y, z);
      this.legs.func_78785_a(f);
      if (!this.isArmor) {
         this.tail.setConfig(legs, 0.0F, y, z);
         this.tail.func_78785_a(f);
      }

      GL11.glPopMatrix();
   }

   public ModelRenderer func_85181_a(Random par1Random) {
      int random = par1Random.nextInt(5);
      switch (random) {
         case 0:
            return this.bipedRightLeg;
         case 1:
            return this.bipedHead;
         case 2:
            return this.bipedLeftArm;
         case 3:
            return this.bipedRightArm;
         case 4:
            return this.bipedLeftLeg;
         default:
            return this.bipedBody;
      }
   }

   public boolean isSleeping(Entity entity) {
      return entity instanceof EntityPlayer && ((EntityPlayer)entity).func_70608_bn() ? true : ((EntityCustomNpc)entity).isSleeping();
   }
}
