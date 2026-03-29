package noppes.npcs.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.constants.EnumAnimation;
import noppes.npcs.constants.EnumJobType;
import noppes.npcs.constants.EnumMovingType;
import noppes.npcs.constants.EnumStandingType;
import noppes.npcs.roles.JobBoss;
import org.lwjgl.opengl.GL11;

public class RenderNPCInterface extends RenderLiving {
   public RenderNPCInterface(ModelBase model, float f) {
      super(model, f);
   }

   protected void renderName(EntityNPCInterface npc, double d, double d1, double d2) {
      if (Minecraft.func_71382_s() && npc != this.field_76990_c.field_78734_h && npc.display.showName()) {
         float f2 = npc.func_70032_d(this.field_76990_c.field_78734_h);
         float f3 = npc.func_70093_af() ? 32.0F : 64.0F;
         if (f2 < f3) {
            String s = npc.func_70023_ak();
            float yoffset = 2.0F + npc.labelOffset;
            if (npc.ai.movingType == EnumMovingType.Standing) {
               if (!npc.func_70608_bn() && !npc.isKilled()) {
                  if (npc.func_70115_ae()) {
                     yoffset *= 0.75F;
                  }
               } else {
                  yoffset = 0.5F;
               }
            }

            yoffset = (float)((double)yoffset * Math.pow((double)((float)npc.display.modelSize / 5.0F), 1.1));
            yoffset = (float)((double)yoffset * ((double)npc.scaleY + 0.06));
            yoffset += npc.currentAnimation == EnumAnimation.NONE ? npc.ai.bodyOffsetY / 10.0F - 0.5F : 0.0F;
            this.renderLivingLabel(npc, s, d, d1 - (double)2.0F + (double)yoffset, d2, 64);
         }
      }

   }

   protected void renderLivingLabel(EntityNPCInterface npc, String s, double d, double d1, double d2, int i) {
      float f = npc.func_70032_d(this.field_76990_c.field_78734_h);
      if (!(f > (float)i)) {
         FontRenderer fontrenderer = this.func_76983_a();
         float f1 = 1.6F * (float)npc.display.modelSize / 5.0F;
         float f2 = 0.01666667F * f1;
         GL11.glPushMatrix();
         GL11.glTranslatef((float)d + 0.0F, (float)d1 + 2.3F, (float)d2);
         GL11.glNormal3f(0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-this.field_76990_c.field_78735_i, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(this.field_76990_c.field_78732_j, 1.0F, 0.0F, 0.0F);
         GL11.glScalef(-f2, -f2, f2);
         GL11.glDisable(2896);
         GL11.glDepthMask(false);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         Tessellator tessellator = Tessellator.field_78398_a;
         GL11.glDisable(3553);
         tessellator.func_78382_b();
         int j = fontrenderer.func_78256_a(s) / 2;
         tessellator.func_78369_a(0.0F, 0.0F, 0.0F, 0.25F);
         tessellator.func_78377_a((double)(-j - 1), (double)-1.0F, (double)0.0F);
         tessellator.func_78377_a((double)(-j - 1), (double)8.0F, (double)0.0F);
         tessellator.func_78377_a((double)(j + 1), (double)8.0F, (double)0.0F);
         tessellator.func_78377_a((double)(j + 1), (double)-1.0F, (double)0.0F);
         tessellator.func_78381_a();
         GL11.glEnable(3553);
         GL11.glDepthMask(true);
         int color = npc.getFaction().color;
         fontrenderer.func_78276_b(s, -fontrenderer.func_78256_a(s) / 2, 0, color);
         GL11.glEnable(2896);
         GL11.glDisable(3042);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glPopMatrix();
      }
   }

   protected void renderPlayerScale(EntityNPCInterface npc, float f) {
      float red = (float)(npc.display.skinColor >> 16 & 255) / 255.0F;
      float green = (float)(npc.display.skinColor >> 8 & 255) / 255.0F;
      float blue = (float)(npc.display.skinColor & 255) / 255.0F;
      GL11.glColor3f(red, green, blue);
      GL11.glScalef(npc.scaleX / 5.0F * (float)npc.display.modelSize, npc.scaleY / 5.0F * (float)npc.display.modelSize, npc.scaleZ / 5.0F * (float)npc.display.modelSize);
   }

   protected void renderPlayerSleep(EntityNPCInterface npc, double d, double d1, double d2) {
      this.field_76989_e = (float)npc.display.modelSize / 10.0F;
      float xOffset = 0.0F;
      float yOffset = npc.currentAnimation == EnumAnimation.NONE ? npc.ai.bodyOffsetY / 10.0F - 0.5F : 0.0F;
      float zOffset = 0.0F;
      if (!npc.isKilled() && !npc.isWalking()) {
         if (npc.func_70608_bn()) {
            xOffset = (float)(-Math.cos(Math.toRadians((double)(180 - npc.ai.orientation))));
            zOffset = (float)(-Math.sin(Math.toRadians((double)npc.ai.orientation)));
            yOffset += 0.14F;
         } else if (npc.func_70115_ae()) {
            yOffset -= 0.5F;
         }
      }

      this.renderLiving(npc, d, d1, d2, xOffset, yOffset, zOffset);
   }

   private void renderLiving(EntityNPCInterface npc, double d, double d1, double d2, float xoffset, float yoffset, float zoffset) {
      xoffset = xoffset / 5.0F * (float)npc.display.modelSize;
      yoffset = yoffset / 5.0F * (float)npc.display.modelSize;
      zoffset = zoffset / 5.0F * (float)npc.display.modelSize;
      super.func_77039_a(npc, d + (double)xoffset, d1 + (double)yoffset, d2 + (double)zoffset);
   }

   protected void func_77043_a(EntityLivingBase entity, float f, float f1, float f2) {
      EntityNPCInterface npc = (EntityNPCInterface)entity;
      if (npc.func_70089_S() && npc.func_70608_bn()) {
         GL11.glRotatef((float)npc.ai.orientation, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(this.func_77037_a(npc), 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
      } else {
         super.func_77043_a(npc, f, f1, f2);
      }

   }

   protected void func_77033_b(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6) {
      if (!MinecraftForge.EVENT_BUS.post(new RenderLivingEvent.Specials.Pre(par1EntityLivingBase, this))) {
         this.renderName((EntityNPCInterface)par1EntityLivingBase, par2, par4, par6);
      }
   }

   protected void func_77041_b(EntityLivingBase entityliving, float f) {
      this.renderPlayerScale((EntityNPCInterface)entityliving, f);
   }

   public void func_77031_a(EntityLiving entityliving, double d, double d1, double d2, float f, float f1) {
      EntityNPCInterface npc = (EntityNPCInterface)entityliving;
      if (!npc.isKilled() || !npc.stats.hideKilledBody || npc.field_70725_aQ <= 10) {
         if (npc.advanced.job == EnumJobType.Boss && !npc.isKilled() && npc.field_70725_aQ <= 10 && !((JobBoss)npc.jobInterface).hideName) {
            BossStatus.func_82824_a(npc, true);
         }

         if (npc.ai.standingType == EnumStandingType.HeadRotation && !npc.isWalking()) {
            npc.field_70760_ar = npc.field_70761_aq = (float)npc.ai.orientation;
         }

         super.func_77031_a(entityliving, d, d1, d2, f, f1);
      }
   }

   protected void func_77036_a(EntityLivingBase entityliving, float par2, float par3, float par4, float par5, float par6, float par7) {
      super.func_77036_a(entityliving, par2, par3, par4, par5, par6, par7);
      EntityNPCInterface npc = (EntityNPCInterface)entityliving;
      if (!npc.display.glowTexture.isEmpty()) {
         GL11.glDepthFunc(515);
         if (npc.textureGlowLocation == null) {
            npc.textureGlowLocation = new ResourceLocation(npc.display.glowTexture);
         }

         this.func_110776_a((ResourceLocation)npc.textureGlowLocation);
         float f1 = 1.0F;
         GL11.glEnable(3042);
         GL11.glBlendFunc(1, 1);
         GL11.glDisable(2896);
         if (npc.func_82150_aj()) {
            GL11.glDepthMask(false);
         } else {
            GL11.glDepthMask(true);
         }

         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glPushMatrix();
         GL11.glScalef(1.001F, 1.001F, 1.001F);
         this.field_77045_g.func_78088_a(entityliving, par2, par3, par4, par5, par6, par7);
         GL11.glPopMatrix();
         GL11.glEnable(2896);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, f1);
         GL11.glDepthFunc(515);
         GL11.glDisable(3042);
      }

   }

   protected float func_77044_a(EntityLivingBase par1EntityLiving, float par2) {
      EntityNPCInterface npc = (EntityNPCInterface)par1EntityLiving;
      return !npc.isKilled() && !npc.display.NoLivingAnimation ? super.func_77044_a(par1EntityLiving, par2) : 0.0F;
   }

   protected void func_77039_a(EntityLivingBase entityliving, double d, double d1, double d2) {
      this.renderPlayerSleep((EntityNPCInterface)entityliving, d, d1, d2);
   }

   public ResourceLocation func_110775_a(Entity entity) {
      EntityNPCInterface npc = (EntityNPCInterface)entity;
      if (npc.textureLocation == null) {
         if (npc.display.usingSkinUrl) {
            ResourceLocation location = AbstractClientPlayer.func_110311_f(npc.display.skinUsername);
            AbstractClientPlayer.func_110304_a(location, npc.display.skinUsername);
            npc.textureLocation = location;
         } else {
            npc.textureLocation = new ResourceLocation(npc.display.texture);
         }
      }

      return (ResourceLocation)npc.textureLocation;
   }
}
