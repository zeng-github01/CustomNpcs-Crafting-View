package noppes.npcs.client.renderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.client.ClientProxy;
import noppes.npcs.entity.EntityProjectile;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderProjectile extends Render {
   public boolean renderWithColor = true;
   private static final ResourceLocation field_110780_a = new ResourceLocation("textures/entity/arrow.png");
   private static final ResourceLocation field_110798_h = new ResourceLocation("textures/misc/enchanted_item_glint.png");
   private RenderBlocks itemRenderBlocks = new RenderBlocks();

   public void doRenderProjectile(EntityProjectile par1EntityProjectile, double par2, double par4, double par6, float par8, float par9) {
      GL11.glPushMatrix();
      GL11.glTranslatef((float)par2, (float)par4, (float)par6);
      GL11.glEnable(32826);
      float f = (float)par1EntityProjectile.func_70096_w().func_75679_c(23) / 10.0F;
      ItemStack item = par1EntityProjectile.getItemDisplay();
      GL11.glScalef(f, f, f);
      Tessellator tessellator = Tessellator.field_78398_a;
      if (par1EntityProjectile.isArrow()) {
         this.func_110777_b(par1EntityProjectile);
         GL11.glRotatef(par1EntityProjectile.field_70126_B + (par1EntityProjectile.field_70177_z - par1EntityProjectile.field_70126_B) * par9 - 90.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(par1EntityProjectile.field_70127_C + (par1EntityProjectile.field_70125_A - par1EntityProjectile.field_70127_C) * par9, 0.0F, 0.0F, 1.0F);
         byte b0 = 0;
         float f2 = 0.0F;
         float f3 = 0.5F;
         float f4 = (float)(0 + b0 * 10) / 32.0F;
         float f5 = (float)(5 + b0 * 10) / 32.0F;
         float f6 = 0.0F;
         float f7 = 0.15625F;
         float f8 = (float)(5 + b0 * 10) / 32.0F;
         float f9 = (float)(10 + b0 * 10) / 32.0F;
         float f10 = 0.05625F;
         GL11.glEnable(32826);
         float f11 = (float)par1EntityProjectile.arrowShake - par9;
         if (f11 > 0.0F) {
            float f12 = -MathHelper.func_76126_a(f11 * 3.0F) * f11;
            GL11.glRotatef(f12, 0.0F, 0.0F, 1.0F);
         }

         GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
         GL11.glScalef(f10, f10, f10);
         GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
         GL11.glNormal3f(f10, 0.0F, 0.0F);
         tessellator.func_78382_b();
         tessellator.func_78374_a((double)-7.0F, (double)-2.0F, (double)-2.0F, (double)f6, (double)f8);
         tessellator.func_78374_a((double)-7.0F, (double)-2.0F, (double)2.0F, (double)f7, (double)f8);
         tessellator.func_78374_a((double)-7.0F, (double)2.0F, (double)2.0F, (double)f7, (double)f9);
         tessellator.func_78374_a((double)-7.0F, (double)2.0F, (double)-2.0F, (double)f6, (double)f9);
         tessellator.func_78381_a();
         GL11.glNormal3f(-f10, 0.0F, 0.0F);
         tessellator.func_78382_b();
         tessellator.func_78374_a((double)-7.0F, (double)2.0F, (double)-2.0F, (double)f6, (double)f8);
         tessellator.func_78374_a((double)-7.0F, (double)2.0F, (double)2.0F, (double)f7, (double)f8);
         tessellator.func_78374_a((double)-7.0F, (double)-2.0F, (double)2.0F, (double)f7, (double)f9);
         tessellator.func_78374_a((double)-7.0F, (double)-2.0F, (double)-2.0F, (double)f6, (double)f9);
         tessellator.func_78381_a();

         for(int i = 0; i < 4; ++i) {
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, f10);
            tessellator.func_78382_b();
            tessellator.func_78374_a((double)-8.0F, (double)-2.0F, (double)0.0F, (double)f2, (double)f4);
            tessellator.func_78374_a((double)8.0F, (double)-2.0F, (double)0.0F, (double)f3, (double)f4);
            tessellator.func_78374_a((double)8.0F, (double)2.0F, (double)0.0F, (double)f3, (double)f5);
            tessellator.func_78374_a((double)-8.0F, (double)2.0F, (double)0.0F, (double)f2, (double)f5);
            tessellator.func_78381_a();
         }
      } else if (par1EntityProjectile.is3D()) {
         GL11.glRotatef(par1EntityProjectile.field_70126_B + (par1EntityProjectile.field_70177_z - par1EntityProjectile.field_70126_B) * par9 - 90.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(par1EntityProjectile.field_70127_C + (par1EntityProjectile.field_70125_A - par1EntityProjectile.field_70127_C) * par9 - 180.0F, 0.0F, 0.0F, 1.0F);
         Block block = null;
         if (item.field_77993_c < Block.field_71973_m.length) {
            block = Block.field_71973_m[item.field_77993_c];
         }

         if (item.func_94608_d() == 0 && block != null && RenderBlocks.func_78597_b(Block.field_71973_m[item.field_77993_c].func_71857_b())) {
            this.func_110776_a(TextureMap.field_110575_b);
            float f7 = 0.25F;
            int j = block.func_71857_b();
            if (j == 1 || j == 19 || j == 12 || j == 2) {
               f7 = 0.5F;
            }

            float f5 = 1.0F;
            this.field_76988_d.func_78600_a(block, item.func_77960_j(), f5);
         } else if (item.func_77973_b().func_77623_v()) {
            for(int k = 0; k < item.func_77973_b().getRenderPasses(item.func_77960_j()); ++k) {
               item.func_77973_b().getIcon(item, k);
               float f8 = 1.0F;
               if (this.renderWithColor) {
                  int i = Item.field_77698_e[item.field_77993_c].func_82790_a(item, k);
                  float f5 = (float)(i >> 16 & 255) / 255.0F;
                  float f4 = (float)(i >> 8 & 255) / 255.0F;
                  float f6 = (float)(i & 255) / 255.0F;
                  GL11.glColor4f(f5 * f8, f4 * f8, f6 * f8, 1.0F);
                  this.field_76990_c.field_78721_f.func_78443_a(Minecraft.func_71410_x().field_71439_g, item, 0);
               } else {
                  this.field_76990_c.field_78721_f.func_78443_a(Minecraft.func_71410_x().field_71439_g, item, 0);
               }
            }
         } else {
            Icon icon1 = item.func_77954_c();
            if (this.renderWithColor) {
               int l = Item.field_77698_e[item.field_77993_c].func_82790_a(item, 0);
               float f8 = (float)(l >> 16 & 255) / 255.0F;
               float f9 = (float)(l >> 8 & 255) / 255.0F;
               float f5 = (float)(l & 255) / 255.0F;
               float f4 = 1.0F;
               this.renderDroppedItem(item, icon1, par9, f8 * f4, f9 * f4, f5 * f4, f);
            } else {
               this.renderDroppedItem(item, icon1, par9, 1.0F, 1.0F, 1.0F, f);
            }
         }
      } else {
         Icon icon = item.func_77954_c();
         this.func_110776_a(TextureMap.field_110576_c);
         if (item.func_77973_b().func_77623_v()) {
            for(int k = 0; k < item.func_77973_b().getRenderPasses(item.func_77960_j()); ++k) {
               int i = Item.field_77698_e[item.field_77993_c].func_82790_a(item, k);
               float f5 = (float)(i >> 16 & 255) / 255.0F;
               float f4 = (float)(i >> 8 & 255) / 255.0F;
               float f6 = (float)(i & 255) / 255.0F;
               GL11.glColor4f(f5, f4, f6, 1.0F);
            }
         }

         if (icon == ItemPotion.func_94589_d("potion_splash")) {
            int i = PotionHelper.func_77915_a(item.func_77960_j(), false);
            float f2 = (float)(i >> 16 & 255) / 255.0F;
            float f3 = (float)(i >> 8 & 255) / 255.0F;
            float f4 = (float)(i & 255) / 255.0F;
            GL11.glColor3f(f2, f3, f4);
            GL11.glPushMatrix();
            this.renderSprite(tessellator, ItemPotion.func_94589_d("potion_contents"));
            GL11.glPopMatrix();
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
         }

         this.renderSprite(tessellator, icon);
      }

      if (par1EntityProjectile.is3D() && par1EntityProjectile.glows()) {
         GL11.glDisable(2896);
      }

      GL11.glDisable(32826);
      GL11.glPopMatrix();
      GL11.glEnable(2896);
   }

   private void renderSprite(Tessellator par1Tessellator, Icon par2Icon) {
      float f = par2Icon.func_94209_e();
      float f1 = par2Icon.func_94212_f();
      float f2 = par2Icon.func_94206_g();
      float f3 = par2Icon.func_94210_h();
      float f4 = 1.0F;
      float f5 = 0.5F;
      float f6 = 0.25F;
      GL11.glRotatef(180.0F - this.field_76990_c.field_78735_i, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-this.field_76990_c.field_78732_j, 1.0F, 0.0F, 0.0F);
      par1Tessellator.func_78382_b();
      par1Tessellator.func_78375_b(0.0F, 1.0F, 0.0F);
      par1Tessellator.func_78374_a((double)(0.0F - f5), (double)(0.0F - f6), (double)0.0F, (double)f, (double)f3);
      par1Tessellator.func_78374_a((double)(f4 - f5), (double)(0.0F - f6), (double)0.0F, (double)f1, (double)f3);
      par1Tessellator.func_78374_a((double)(f4 - f5), (double)(f4 - f6), (double)0.0F, (double)f1, (double)f2);
      par1Tessellator.func_78374_a((double)(0.0F - f5), (double)(f4 - f6), (double)0.0F, (double)f, (double)f2);
      par1Tessellator.func_78381_a();
   }

   private void renderDroppedItem(ItemStack item, Icon par2Icon, float par4, float par5, float par6, float par7, float par8) {
      Tessellator tessellator = Tessellator.field_78398_a;
      if (par2Icon == null) {
         TextureManager texturemanager = Minecraft.func_71410_x().func_110434_K();
         ResourceLocation resourcelocation = texturemanager.func_130087_a(item.func_94608_d());
         par2Icon = ((TextureMap)texturemanager.func_110581_b(resourcelocation)).func_94245_a("missingno");
      }

      float f4 = par2Icon.func_94209_e();
      float f5 = par2Icon.func_94212_f();
      float f6 = par2Icon.func_94206_g();
      float f7 = par2Icon.func_94210_h();
      float f8 = 1.0F;
      float f9 = 0.5F;
      float f10 = 0.25F;
      float f12 = 0.0625F;
      if (item.func_94608_d() == 0) {
         this.func_110776_a(TextureMap.field_110575_b);
      } else {
         this.func_110776_a(TextureMap.field_110576_c);
      }

      GL11.glColor4f(par5, par6, par7, 1.0F);
      ItemRenderer.func_78439_a(tessellator, f5, f6, f4, f7, par2Icon.func_94211_a(), par2Icon.func_94216_b(), f12);
      if (item != null && item.hasEffect(0)) {
         GL11.glDepthFunc(514);
         GL11.glDisable(2896);
         ClientProxy.bindTexture(field_110798_h);
         GL11.glEnable(3042);
         GL11.glBlendFunc(768, 1);
         float f13 = 0.76F;
         GL11.glColor4f(0.5F * f13, 0.25F * f13, 0.8F * f13, 1.0F);
         GL11.glMatrixMode(5890);
         GL11.glPushMatrix();
         GL11.glScalef(par8, par8, par8);
         float f15 = (float)(Minecraft.func_71386_F() % 3000L) / 3000.0F * 8.0F;
         GL11.glTranslatef(f15, 0.0F, 0.0F);
         GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
         ItemRenderer.func_78439_a(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, f12);
         GL11.glPopMatrix();
         GL11.glPushMatrix();
         GL11.glScalef(par8, par8, par8);
         f15 = (float)(Minecraft.func_71386_F() % 4873L) / 4873.0F * 8.0F;
         GL11.glTranslatef(-f15, 0.0F, 0.0F);
         GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
         ItemRenderer.func_78439_a(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, f12);
         GL11.glPopMatrix();
         GL11.glMatrixMode(5888);
         GL11.glDisable(3042);
         GL11.glEnable(2896);
         GL11.glDepthFunc(515);
      }

   }

   public void func_76986_a(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
      this.doRenderProjectile((EntityProjectile)par1Entity, par2, par4, par6, par8, par9);
   }

   protected ResourceLocation func_110779_a(EntityProjectile par1EntityProjectile) {
      return par1EntityProjectile.isArrow() ? field_110780_a : this.field_76990_c.field_78724_e.func_130087_a(par1EntityProjectile.getItemDisplay().func_94608_d());
   }

   protected ResourceLocation func_110775_a(Entity par1Entity) {
      return this.func_110779_a((EntityProjectile)par1Entity);
   }
}
