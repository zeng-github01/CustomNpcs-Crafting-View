package noppes.npcs.client.model.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.EntityCustomNpc;
import noppes.npcs.ModelPartData;
import noppes.npcs.client.model.ModelMPM;
import org.lwjgl.opengl.GL11;

public abstract class ModelPartInterface extends ModelRenderer {
   private EntityCustomNpc entity;
   public float scale = 1.0F;
   protected ResourceLocation location;
   public int color = 16777215;
   public ModelMPM base;

   public ModelPartInterface(ModelMPM par1ModelBase) {
      super(par1ModelBase);
      this.base = par1ModelBase;
      this.func_78787_b(0, 0);
   }

   public void setRotation(ModelRenderer model, float x, float y, float z) {
      model.field_78795_f = x;
      model.field_78796_g = y;
      model.field_78808_h = z;
   }

   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity) {
   }

   public void setLivingAnimations(ModelPartData data, EntityLivingBase entityliving, float f, float f1, float f2) {
   }

   public void setData(EntityCustomNpc entity) {
      this.entity = entity;
      this.initData(entity);
   }

   public void func_78785_a(float par1) {
      if (!this.base.isArmor) {
         if (this.location != null) {
            TextureManager texturemanager = Minecraft.func_71410_x().func_110434_K();
            texturemanager.func_110577_a(this.location);
            this.base.currentlyPlayerTexture = false;
         } else if (!this.base.currentlyPlayerTexture) {
            TextureManager texturemanager = Minecraft.func_71410_x().func_110434_K();
            texturemanager.func_110577_a((ResourceLocation)this.entity.textureLocation);
            this.base.currentlyPlayerTexture = true;
         }
      }

      boolean bo = this.entity.field_70737_aN <= 0 && this.entity.field_70725_aQ <= 0 && !this.base.isArmor;
      if (bo) {
         float red = (float)(this.color >> 16 & 255) / 255.0F;
         float green = (float)(this.color >> 8 & 255) / 255.0F;
         float blue = (float)(this.color & 255) / 255.0F;
         GL11.glColor4f(red, green, blue, 1.0F);
      }

      super.func_78785_a(par1);
      if (bo) {
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

   }

   public abstract void initData(EntityCustomNpc var1);
}
