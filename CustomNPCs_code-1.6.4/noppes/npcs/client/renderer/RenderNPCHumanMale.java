package noppes.npcs.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.model.ModelNPCMale;
import noppes.npcs.constants.EnumAnimation;
import noppes.npcs.constants.EnumModelType;
import noppes.npcs.items.ItemClaw;
import noppes.npcs.items.ItemShield;
import org.lwjgl.opengl.GL11;

public class RenderNPCHumanMale extends RenderNPCInterface {
   private ModelNPCMale modelBipedMain;
   protected ModelNPCMale modelArmorChestplate;
   protected ModelNPCMale modelArmor;

   public RenderNPCHumanMale(ModelNPCMale mainmodel, ModelNPCMale armorChest, ModelNPCMale armor) {
      super(mainmodel, 0.5F);
      this.modelBipedMain = mainmodel;
      this.modelArmorChestplate = armorChest;
      this.modelArmor = armor;
   }

   protected int func_130006_a(EntityLiving par1EntityLiving, int par2, float par3) {
      ItemStack itemstack = par1EntityLiving.func_130225_q(par2);
      if (itemstack != null) {
         Item item = itemstack.func_77973_b();
         if (item instanceof ItemArmor) {
            ItemArmor itemarmor = (ItemArmor)item;
            this.func_110776_a(RenderBiped.getArmorResource(par1EntityLiving, itemstack, par2, (String)null));
            ModelNPCMale modelbiped = par2 == 2 ? this.modelArmor : this.modelArmorChestplate;
            modelbiped.bipedHead.field_78806_j = par2 == 0;
            modelbiped.bipedHeadwear.field_78806_j = par2 == 0;
            modelbiped.bipedBody.field_78806_j = par2 == 1 || par2 == 2;
            modelbiped.bipedRightArm.field_78806_j = par2 == 1;
            modelbiped.bipedLeftArm.field_78806_j = par2 == 1;
            modelbiped.bipedRightLeg.field_78806_j = par2 == 2 || par2 == 3;
            modelbiped.bipedLeftLeg.field_78806_j = par2 == 2 || par2 == 3;
            this.func_77042_a(modelbiped);
            modelbiped.field_78095_p = this.field_77045_g.field_78095_p;
            modelbiped.field_78093_q = this.field_77045_g.field_78093_q;
            modelbiped.field_78091_s = this.field_77045_g.field_78091_s;
            float f1 = 1.0F;
            int j = itemarmor.func_82814_b(itemstack);
            if (j != -1) {
               float f2 = (float)(j >> 16 & 255) / 255.0F;
               float f3 = (float)(j >> 8 & 255) / 255.0F;
               float f4 = (float)(j & 255) / 255.0F;
               GL11.glColor3f(f1 * f2, f1 * f3, f1 * f4);
               if (itemstack.func_77948_v()) {
                  return 31;
               }

               return 16;
            }

            GL11.glColor3f(f1, f1, f1);
            if (itemstack.func_77948_v()) {
               return 15;
            }

            return 1;
         }
      }

      return -1;
   }

   protected int func_77032_a(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
      return this.func_130006_a((EntityLiving)par1EntityLivingBase, par2, par3);
   }

   public void renderPlayer(EntityNPCInterface npc, double d, double d1, double d2, float f, float f1) {
      ItemStack itemstack = npc.func_70694_bm();
      this.modelArmorChestplate.heldItemRight = this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = itemstack == null ? 0 : 1;
      this.modelArmorChestplate.isSneak = this.modelArmor.isSneak = this.modelBipedMain.isSneak = npc.func_70093_af();
      this.modelArmorChestplate.isSleeping = this.modelArmor.isSleeping = this.modelBipedMain.isSleeping = npc.func_70608_bn();
      this.modelArmorChestplate.isDancing = this.modelArmor.isDancing = this.modelBipedMain.isDancing = npc.currentAnimation == EnumAnimation.DANCING;
      this.modelArmorChestplate.aimedBow = this.modelArmor.aimedBow = this.modelBipedMain.aimedBow = npc.currentAnimation == EnumAnimation.Aiming;
      this.modelArmorChestplate.field_78093_q = this.modelArmor.field_78093_q = this.modelBipedMain.field_78093_q = npc.func_70115_ae();
      double d3 = d1 - (double)npc.field_70129_M;
      if (npc.func_70093_af()) {
         d3 -= (double)0.125F;
      }

      super.func_77031_a(npc, d, d3, d2, f, f1);
      this.modelArmorChestplate.aimedBow = this.modelArmor.aimedBow = this.modelBipedMain.aimedBow = false;
      this.modelArmorChestplate.isSneak = this.modelArmor.isSneak = this.modelBipedMain.isSneak = false;
      this.modelArmorChestplate.heldItemRight = this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = 0;
   }

   protected void renderSpecials(EntityNPCInterface npc, float f) {
      super.func_77029_c(npc, f);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      int i = npc.func_70070_b(f);
      int j = i % 65536;
      int k = i / 65536;
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)j / 1.0F, (float)k / 1.0F);
      if (!npc.display.cloakTexture.isEmpty()) {
         if (npc.textureCloakLocation == null) {
            npc.textureCloakLocation = new ResourceLocation(npc.display.cloakTexture);
         }

         this.func_110776_a((ResourceLocation)npc.textureCloakLocation);
         GL11.glPushMatrix();
         GL11.glTranslatef(0.0F, 0.0F, 0.125F);
         double d = npc.field_20066_r + (npc.field_20063_u - npc.field_20066_r) * (double)f - (npc.field_70169_q + (npc.field_70165_t - npc.field_70169_q) * (double)f);
         double d1 = npc.field_20065_s + (npc.field_20062_v - npc.field_20065_s) * (double)f - (npc.field_70167_r + (npc.field_70163_u - npc.field_70167_r) * (double)f);
         double d2 = npc.field_20064_t + (npc.field_20061_w - npc.field_20064_t) * (double)f - (npc.field_70166_s + (npc.field_70161_v - npc.field_70166_s) * (double)f);
         float f11 = npc.field_70760_ar + (npc.field_70761_aq - npc.field_70760_ar) * f;
         double d3 = (double)MathHelper.func_76126_a(f11 * 3.141593F / 180.0F);
         double d4 = (double)(-MathHelper.func_76134_b(f11 * 3.141593F / 180.0F));
         float f14 = (float)(d * d3 + d2 * d4) * 100.0F;
         float f15 = (float)(d * d4 - d2 * d3) * 100.0F;
         if (f14 < 0.0F) {
            f14 = 0.0F;
         }

         float var10000 = npc.field_70126_B + (npc.field_70177_z - npc.field_70126_B) * f;
         float f13 = 5.0F;
         if (npc.func_70093_af()) {
            f13 += 25.0F;
         }

         GL11.glRotatef(6.0F + f14 / 2.0F + f13, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef(f15 / 2.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(-f15 / 2.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
         this.modelBipedMain.renderCloak(0.0625F);
         GL11.glPopMatrix();
      }

      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      ItemStack itemstack = npc.inventory.armorItemInSlot(0);
      if (itemstack != null && itemstack.func_77973_b().field_77779_bT < 256) {
         GL11.glPushMatrix();
         this.modelBipedMain.bipedHead.func_78794_c(0.0625F);
         IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack, ItemRenderType.EQUIPPED);
         boolean is3D = customRenderer != null && customRenderer.shouldUseRenderHelper(ItemRenderType.EQUIPPED, itemstack, ItemRendererHelper.BLOCK_3D);
         if (itemstack.func_77973_b() instanceof ItemBlock) {
            if (is3D || RenderBlocks.func_78597_b(Block.field_71973_m[itemstack.field_77993_c].func_71857_b())) {
               float var6 = 0.625F;
               GL11.glTranslatef(0.0F, -0.25F, 0.0F);
               GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glScalef(var6, -var6, -var6);
            }

            this.field_76990_c.field_78721_f.func_78443_a(npc, itemstack, 0);
         }

         GL11.glPopMatrix();
      }

      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      ItemStack itemstack2 = npc.func_70694_bm();
      if (itemstack2 != null) {
         GL11.glPushMatrix();
         this.modelBipedMain.bipedRightArm.func_78794_c(0.0625F);
         float var7 = npc.display.modelType == EnumModelType.EnderMan ? 1.0F : 0.0F;
         GL11.glTranslatef(-0.0625F, 0.4375F + var7, 0.0625F);
         IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack2, ItemRenderType.EQUIPPED);
         boolean is3D = customRenderer != null && customRenderer.shouldUseRenderHelper(ItemRenderType.EQUIPPED, itemstack2, ItemRendererHelper.BLOCK_3D);
         if (!(itemstack2.func_77973_b() instanceof ItemBlock) || !is3D && !RenderBlocks.func_78597_b(Block.field_71973_m[itemstack2.field_77993_c].func_71857_b())) {
            if (itemstack2.field_77993_c == Item.field_77707_k.field_77779_bT) {
               float var6 = 0.625F;
               GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
               GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
               GL11.glScalef(var6, -var6, var6);
               GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
               GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            } else if (Item.field_77698_e[itemstack2.field_77993_c].func_77662_d()) {
               float var6 = 0.625F;
               if (Item.field_77698_e[itemstack2.field_77993_c].func_77629_n_()) {
                  GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                  GL11.glTranslatef(0.0F, -0.125F, 0.0F);
               }

               GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
               GL11.glScalef(var6, -var6, var6);
               GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
               GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            } else {
               float var6 = 0.375F;
               GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
               GL11.glScalef(var6, var6, var6);
               GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
               GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
               GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
            }
         } else {
            float var6 = 0.5F;
            GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
            var6 *= 0.75F;
            GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(-var6, -var6, var6);
         }

         this.field_76990_c.field_78721_f.func_78443_a(npc, itemstack2, 0);
         if (itemstack2.func_77973_b().func_77623_v()) {
            for(int x = 1; x < itemstack2.func_77973_b().getRenderPasses(itemstack2.func_77960_j()); ++x) {
               this.field_76990_c.field_78721_f.func_78443_a(npc, itemstack2, x);
            }
         }

         GL11.glPopMatrix();
      }

      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      itemstack2 = npc.getOffHand();
      if (itemstack2 != null) {
         GL11.glPushMatrix();
         this.modelBipedMain.bipedLeftArm.func_78794_c(0.0625F);
         float var7 = npc.display.modelType == EnumModelType.EnderMan ? 1.0F : 0.0F;
         GL11.glTranslatef(0.0625F, 0.4375F + var7, 0.0625F);
         IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack2, ItemRenderType.EQUIPPED);
         boolean is3D = customRenderer != null && customRenderer.shouldUseRenderHelper(ItemRenderType.EQUIPPED, itemstack2, ItemRendererHelper.BLOCK_3D);
         if (itemstack2.func_77973_b() instanceof ItemShield || itemstack2.func_77973_b() instanceof ItemClaw) {
            GL11.glTranslatef(0.3F, 0.0F, 0.0F);
         }

         if (!(itemstack2.func_77973_b() instanceof ItemBlock) || !is3D && !RenderBlocks.func_78597_b(Block.field_71973_m[itemstack2.field_77993_c].func_71857_b())) {
            if (itemstack2.field_77993_c == Item.field_77707_k.field_77779_bT) {
               float var6 = 0.625F;
               GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
               GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
               GL11.glScalef(var6, -var6, var6);
               GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
               GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            } else if (Item.field_77698_e[itemstack2.field_77993_c].func_77662_d()) {
               float var6 = 0.625F;
               if (Item.field_77698_e[itemstack2.field_77993_c].func_77629_n_()) {
                  GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                  GL11.glTranslatef(0.0F, -0.125F, 0.0F);
               }

               GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
               GL11.glScalef(var6, -var6, var6);
               GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
               GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            } else {
               float var6 = 0.375F;
               GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
               GL11.glScalef(var6, var6, var6);
               GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
               GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
               GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
            }
         } else {
            float var6 = 0.5F;
            GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
            var6 *= 0.75F;
            GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(var6, -var6, var6);
         }

         if (itemstack2.func_77973_b().func_77623_v()) {
            for(int var25 = 0; var25 < itemstack2.func_77973_b().getRenderPasses(itemstack2.func_77960_j()); ++var25) {
               int var24 = itemstack2.func_77973_b().func_82790_a(itemstack2, var25);
               float var26 = (float)(var24 >> 16 & 255) / 255.0F;
               float var9 = (float)(var24 >> 8 & 255) / 255.0F;
               float var10 = (float)(var24 & 255) / 255.0F;
               GL11.glColor4f(var26, var9, var10, 1.0F);
               this.field_76990_c.field_78721_f.func_78443_a(npc, itemstack2, var25);
            }
         } else {
            this.field_76990_c.field_78721_f.func_78443_a(npc, itemstack2, 0);
         }

         GL11.glPopMatrix();
      }

   }

   protected void func_77029_c(EntityLivingBase entityliving, float f) {
      this.renderSpecials((EntityNPCInterface)entityliving, f);
   }

   public void func_77031_a(EntityLiving entityliving, double d, double d1, double d2, float f, float f1) {
      this.renderPlayer((EntityNPCInterface)entityliving, d, d1, d2, f, f1);
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      this.renderPlayer((EntityNPCInterface)entity, d, d1, d2, f, f1);
   }
}
