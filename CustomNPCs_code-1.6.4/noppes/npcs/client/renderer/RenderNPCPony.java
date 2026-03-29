package noppes.npcs.client.renderer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.Resource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.model.ModelPony;
import noppes.npcs.client.model.ModelPonyArmor;
import noppes.npcs.entity.EntityNPCPony;
import org.lwjgl.opengl.GL11;

public class RenderNPCPony extends RenderNPCInterface {
   private ModelPony modelBipedMain;
   private ModelPonyArmor modelArmorChestplate;
   private ModelPonyArmor modelArmor;

   public RenderNPCPony() {
      super(new ModelPony(0.0F), 0.5F);
      this.modelBipedMain = (ModelPony)this.field_77045_g;
      this.modelArmorChestplate = new ModelPonyArmor(1.0F);
      this.modelArmor = new ModelPonyArmor(0.5F);
   }

   protected int setArmorModel(EntityNPCInterface entityplayer, int i, float f) {
      ItemStack itemstack = entityplayer.inventory.armorItemInSlot(i);
      if (itemstack != null) {
         Item item = itemstack.func_77973_b();
         if (item instanceof ItemArmor) {
            ItemArmor itemarmor = (ItemArmor)item;
            this.func_110776_a(RenderBiped.getArmorResource(entityplayer, itemstack, i, (String)null));
            ModelPonyArmor modelbiped = i != 2 ? this.modelArmorChestplate : this.modelArmor;
            modelbiped.head.field_78806_j = i == 0;
            modelbiped.Body.field_78806_j = i == 1;
            modelbiped.BodyBack.field_78806_j = i == 1;
            modelbiped.rightarm.field_78806_j = i == 3;
            modelbiped.LeftArm.field_78806_j = i == 3;
            modelbiped.RightLeg.field_78806_j = i == 3;
            modelbiped.LeftLeg.field_78806_j = i == 3;
            modelbiped.rightarm2.field_78806_j = i == 2;
            modelbiped.LeftArm2.field_78806_j = i == 2;
            modelbiped.RightLeg2.field_78806_j = i == 2;
            modelbiped.LeftLeg2.field_78806_j = i == 2;
            this.func_77042_a(modelbiped);
            float var8 = 1.0F;
            if (itemarmor.func_82812_d() == EnumArmorMaterial.CLOTH) {
               int var9 = itemarmor.func_82814_b(itemstack);
               float var10 = (float)(var9 >> 16 & 255) / 255.0F;
               float var11 = (float)(var9 >> 8 & 255) / 255.0F;
               float var12 = (float)(var9 & 255) / 255.0F;
               GL11.glColor3f(var8 * var10, var8 * var11, var8 * var12);
               if (itemstack.func_77948_v()) {
                  return 31;
               }

               return 16;
            }

            GL11.glColor3f(var8, var8, var8);
            return !itemstack.func_77948_v() ? 1 : 15;
         }
      }

      return -1;
   }

   public ResourceLocation func_110775_a(Entity entity) {
      EntityNPCPony pony = (EntityNPCPony)entity;
      boolean check = pony.textureLocation == null;
      ResourceLocation loc = super.func_110775_a(pony);
      if (check) {
         try {
            Resource resource = Minecraft.func_71410_x().func_110442_L().func_110536_a(loc);
            BufferedImage bufferedimage = ImageIO.read(resource.func_110527_b());
            pony.isPegasus = false;
            pony.isUnicorn = false;
            Color color = new Color(bufferedimage.getRGB(0, 0), true);
            Color color1 = new Color(249, 177, 49, 255);
            Color color2 = new Color(136, 202, 240, 255);
            Color color3 = new Color(209, 159, 228, 255);
            Color color4 = new Color(254, 249, 252, 255);
            if (color.equals(color1)) {
            }

            if (color.equals(color2)) {
               pony.isPegasus = true;
            }

            if (color.equals(color3)) {
               pony.isUnicorn = true;
            }

            if (color.equals(color4)) {
               pony.isPegasus = true;
               pony.isUnicorn = true;
            }
         } catch (IOException var12) {
         }
      }

      return loc;
   }

   public void renderPlayer(EntityNPCPony pony, double d, double d1, double d2, float f, float f1) {
      ItemStack itemstack = pony.func_70694_bm();
      this.func_77042_a(this.modelBipedMain);
      this.modelArmorChestplate.heldItemRight = this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = itemstack == null ? 0 : 1;
      this.modelArmorChestplate.isSneak = this.modelArmor.isSneak = this.modelBipedMain.isSneak = pony.func_70093_af();
      this.modelArmorChestplate.field_78093_q = this.modelArmor.field_78093_q = this.modelBipedMain.field_78093_q = false;
      this.modelArmorChestplate.isSleeping = this.modelArmor.isSleeping = this.modelBipedMain.isSleeping = pony.func_70608_bn();
      this.modelArmorChestplate.isUnicorn = this.modelArmor.isUnicorn = this.modelBipedMain.isUnicorn = pony.isUnicorn;
      this.modelArmorChestplate.isPegasus = this.modelArmor.isPegasus = this.modelBipedMain.isPegasus = pony.isPegasus;
      double d3 = d1 - (double)pony.field_70129_M;
      if (pony.func_70093_af()) {
         d3 -= (double)0.125F;
      }

      super.func_77031_a(pony, d, d3, d2, f, f1);
      this.modelArmorChestplate.aimedBow = this.modelArmor.aimedBow = this.modelBipedMain.aimedBow = false;
      this.modelArmorChestplate.field_78093_q = this.modelArmor.field_78093_q = this.modelBipedMain.field_78093_q = false;
      this.modelArmorChestplate.isSneak = this.modelArmor.isSneak = this.modelBipedMain.isSneak = false;
      this.modelArmorChestplate.heldItemRight = this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = 0;
   }

   protected void renderSpecials(EntityNPCPony entityplayer, float f) {
      super.func_77029_c(entityplayer, f);
      if (!entityplayer.func_70608_bn()) {
         if (entityplayer.isUnicorn) {
            this.renderDrop(this.field_76990_c, entityplayer, this.modelBipedMain.unicornarm, 1.0F, 0.35F, 0.5375F, -0.45F);
         } else {
            this.renderDrop(this.field_76990_c, entityplayer, this.modelBipedMain.RightArm, 1.0F, -0.0625F, 0.8375F, 0.0625F);
         }
      }

   }

   protected void renderDrop(RenderManager rendermanager, EntityNPCPony entityplayer, ModelRenderer modelrenderer, float f, float f1, float f2, float f3) {
      ItemStack itemstack = entityplayer.func_70694_bm();
      if (itemstack != null) {
         GL11.glPushMatrix();
         if (modelrenderer != null) {
            modelrenderer.func_78794_c(f * 0.0625F);
         }

         GL11.glTranslatef(f1, f2, f3);
         if (itemstack.field_77993_c < 256 && RenderBlocks.func_78597_b(Block.field_71973_m[itemstack.field_77993_c].func_71857_b())) {
            GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
            GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            float f4 = 0.375F * f;
            GL11.glScalef(f4, -f4, f4);
         } else if (itemstack.field_77993_c == Item.field_77707_k.field_77779_bT) {
            GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
            GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
            float f5 = 0.625F * f;
            GL11.glScalef(f5, -f5, f5);
            GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
         } else if (Item.field_77698_e[itemstack.field_77993_c].func_77662_d()) {
            if (Item.field_77698_e[itemstack.field_77993_c].func_77629_n_()) {
               GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
               GL11.glTranslatef(0.0F, -0.125F, 0.0F);
            }

            GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
            float f6 = 0.625F * f;
            GL11.glScalef(f6, -f6, f6);
            GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
         } else {
            GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
            float f7 = 0.375F * f;
            GL11.glScalef(f7, f7, f7);
            GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
         }

         if (itemstack.field_77993_c == Item.field_77726_bs.field_77779_bT) {
            for(int j = 0; j <= 1; ++j) {
               int k = itemstack.func_77973_b().func_82790_a(itemstack, j);
               float f9 = (float)(k >> 16 & 255) / 255.0F;
               float f10 = (float)(k >> 8 & 255) / 255.0F;
               float f12 = (float)(k & 255) / 255.0F;
               GL11.glColor4f(f9, f10, f12, 1.0F);
               this.field_76990_c.field_78721_f.func_78443_a(entityplayer, itemstack, j);
            }
         } else {
            rendermanager.field_78721_f.func_78443_a(entityplayer, itemstack, 0);
         }

         GL11.glPopMatrix();
      }
   }

   protected int func_77032_a(EntityLivingBase entityliving, int i, float f) {
      return this.setArmorModel((EntityNPCInterface)entityliving, i, f);
   }

   protected void func_77029_c(EntityLivingBase entityliving, float f) {
      this.renderSpecials((EntityNPCPony)entityliving, f);
   }

   public void func_77031_a(EntityLiving entityliving, double d, double d1, double d2, float f, float f1) {
      this.renderPlayer((EntityNPCPony)entityliving, d, d1, d2, f, f1);
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      this.renderPlayer((EntityNPCPony)entity, d, d1, d2, f, f1);
   }
}
