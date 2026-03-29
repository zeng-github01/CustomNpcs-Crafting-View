package noppes.npcs.client.model.part;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.EntityCustomNpc;
import noppes.npcs.ModelPartData;
import noppes.npcs.client.model.ModelMPM;
import noppes.npcs.client.model.part.tails.ModelDragonTail;
import noppes.npcs.client.model.part.tails.ModelSquirrelTail;
import noppes.npcs.client.model.util.ModelScaleRenderer;
import noppes.npcs.constants.EnumAnimation;
import org.lwjgl.opengl.GL11;

public class ModelTail extends ModelScaleRenderer {
   private EntityCustomNpc entity;
   private ModelMPM base;
   private ModelRenderer tail;
   private ModelRenderer dragon;
   private ModelRenderer squirrel;
   private ModelRenderer horse;
   private int color = 16777215;
   private ResourceLocation location = null;

   public ModelTail(ModelMPM base) {
      super(base);
      this.base = base;
      this.field_78797_d = 11.0F;
      this.tail = new ModelRenderer(base, 56, 21);
      this.tail.func_78789_a(-1.0F, 0.0F, 0.0F, 2, 9, 2);
      this.tail.func_78793_a(0.0F, 0.0F, 1.0F);
      this.setRotation(this.tail, 0.8714253F, 0.0F, 0.0F);
      this.func_78792_a(this.tail);
      this.horse = new ModelRenderer(base);
      this.horse.func_78787_b(32, 32);
      this.horse.func_78793_a(0.0F, -1.0F, 1.0F);
      this.func_78792_a(this.horse);
      ModelRenderer tailBase = new ModelRenderer(base, 0, 26);
      tailBase.func_78787_b(32, 32);
      tailBase.func_78789_a(-1.0F, -1.0F, 0.0F, 2, 2, 3);
      this.setRotation(tailBase, -1.134464F, 0.0F, 0.0F);
      this.horse.func_78792_a(tailBase);
      ModelRenderer tailMiddle = new ModelRenderer(base, 0, 13);
      tailMiddle.func_78787_b(32, 32);
      tailMiddle.func_78789_a(-1.5F, -2.0F, 3.0F, 3, 4, 7);
      this.setRotation(tailMiddle, -1.134464F, 0.0F, 0.0F);
      this.horse.func_78792_a(tailMiddle);
      ModelRenderer tailTip = new ModelRenderer(base, 0, 0);
      tailTip.func_78787_b(32, 32);
      tailTip.func_78789_a(-1.5F, -4.5F, 9.0F, 3, 4, 7);
      this.setRotation(tailTip, -1.40215F, 0.0F, 0.0F);
      this.horse.func_78792_a(tailTip);
      this.horse.field_78795_f = 0.5F;
      this.func_78792_a(this.dragon = new ModelDragonTail(base));
      this.func_78792_a(this.squirrel = new ModelSquirrelTail(base));
   }

   public void setData(EntityCustomNpc entity) {
      this.entity = entity;
      this.initData(entity);
   }

   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity) {
      this.field_78796_g = MathHelper.func_76134_b(par1 * 0.6662F) * 0.3F * par2;
      this.field_78795_f = MathHelper.func_76126_a(par3 * 0.067F) * 0.05F;
      if (this.entity.legParts.type == 2) {
         this.field_78797_d = 13.0F;
         this.field_78798_e = 14.0F * this.entity.legs.scaleZ;
         if (this.base.isSleeping(entity) || this.entity.currentAnimation == EnumAnimation.CRAWLING) {
            this.field_78797_d = 12.0F + 16.0F * this.entity.legs.scaleZ;
            this.field_78798_e = 1.0F * this.entity.legs.scaleY;
            this.field_78795_f = (-(float)Math.PI / 4F);
         }
      } else if (this.entity.legParts.type == 3) {
         this.field_78797_d = 8.6F;
         this.field_78798_e = 19.0F * this.entity.legs.scaleZ;
      } else {
         this.field_78797_d = 11.0F;
         this.field_78798_e = -1.0F;
      }

      this.field_78798_e += this.base.bipedRightLeg.field_78798_e + 0.5F;
   }

   public void setLivingAnimations(ModelPartData data, EntityLivingBase entity, float par2, float par3, float par4) {
   }

   public void initData(EntityCustomNpc data) {
      ModelPartData config = data.getPartData("tail");
      if (config == null) {
         this.field_78807_k = true;
      } else {
         this.color = config.color;
         this.field_78807_k = false;
         this.tail.field_78807_k = config.type != 0;
         this.dragon.field_78807_k = config.type != 1;
         this.horse.field_78807_k = config.type != 2;
         this.squirrel.field_78807_k = config.type != 3;
         if (!config.playerTexture) {
            this.location = (ResourceLocation)config.getResource();
         } else {
            this.location = null;
         }

      }
   }

   public void func_78785_a(float par1) {
      if (!this.field_78807_k) {
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

         boolean bo = this.entity.field_70737_aN <= 0 && this.entity.field_70725_aQ <= 0;
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
   }
}
