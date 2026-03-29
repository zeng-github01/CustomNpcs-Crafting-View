package noppes.npcs.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonMerchant;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.IMerchant;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.containers.ContainerMerchantAdd;
import noppes.npcs.events.ItemInteractEvent;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiMerchantAdd extends GuiContainer {
   private static final ResourceLocation merchantGuiTextures = new ResourceLocation("textures/gui/container/villager.png");
   private IMerchant theIMerchant;
   private GuiButtonMerchant nextRecipeButtonIndex;
   private GuiButtonMerchant previousRecipeButtonIndex;
   private int currentRecipeIndex;
   private String field_94082_v;

   public GuiMerchantAdd() {
      super(new ContainerMerchantAdd(Minecraft.func_71410_x().field_71439_g.field_71071_by, ItemInteractEvent.Merchant, Minecraft.func_71410_x().field_71441_e));
      this.theIMerchant = ItemInteractEvent.Merchant;
      this.field_94082_v = I18n.func_135053_a("entity.Villager.name");
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      int i = (this.field_73880_f - this.field_74194_b) / 2;
      int j = (this.field_73881_g - this.field_74195_c) / 2;
      this.field_73887_h.add(this.nextRecipeButtonIndex = new GuiButtonMerchant(1, i + 120 + 27, j + 24 - 1, true));
      this.field_73887_h.add(this.previousRecipeButtonIndex = new GuiButtonMerchant(2, i + 36 - 19, j + 24 - 1, false));
      this.field_73887_h.add(new GuiNpcButton(4, i + this.field_74194_b, j + 20, 60, 20, "gui.remove"));
      this.field_73887_h.add(new GuiNpcButton(5, i + this.field_74194_b, j + 50, 60, 20, "gui.add"));
      this.nextRecipeButtonIndex.field_73742_g = false;
      this.previousRecipeButtonIndex.field_73742_g = false;
   }

   protected void func_74189_g(int par1, int par2) {
      this.field_73886_k.func_78276_b(this.field_94082_v, this.field_74194_b / 2 - this.field_73886_k.func_78256_a(this.field_94082_v) / 2, 6, 4210752);
      this.field_73886_k.func_78276_b(I18n.func_135053_a("container.inventory"), 8, this.field_74195_c - 96 + 2, 4210752);
   }

   public void func_73876_c() {
      super.func_73876_c();
      MerchantRecipeList merchantrecipelist = this.theIMerchant.func_70934_b(this.field_73882_e.field_71439_g);
      if (merchantrecipelist != null) {
         this.nextRecipeButtonIndex.field_73742_g = this.currentRecipeIndex < merchantrecipelist.size() - 1;
         this.previousRecipeButtonIndex.field_73742_g = this.currentRecipeIndex > 0;
      }

   }

   protected void func_73875_a(GuiButton par1GuiButton) {
      boolean flag = false;
      if (par1GuiButton == this.nextRecipeButtonIndex) {
         ++this.currentRecipeIndex;
         flag = true;
      } else if (par1GuiButton == this.previousRecipeButtonIndex) {
         --this.currentRecipeIndex;
         flag = true;
      }

      if (par1GuiButton.field_73741_f == 4) {
         MerchantRecipeList merchantrecipelist = this.theIMerchant.func_70934_b(this.field_73882_e.field_71439_g);
         if (this.currentRecipeIndex < merchantrecipelist.size()) {
            merchantrecipelist.remove(this.currentRecipeIndex);
            if (this.currentRecipeIndex > 0) {
               --this.currentRecipeIndex;
            }

            NoppesUtil.sendData(EnumPacketType.MerchantUpdate, ItemInteractEvent.Merchant.field_70157_k, merchantrecipelist);
         }
      }

      if (par1GuiButton.field_73741_f == 5) {
         ItemStack item1 = this.field_74193_d.func_75139_a(0).func_75211_c();
         ItemStack item2 = this.field_74193_d.func_75139_a(1).func_75211_c();
         ItemStack sold = this.field_74193_d.func_75139_a(2).func_75211_c();
         if (item1 == null && item2 != null) {
            item1 = item2;
            item2 = null;
         }

         if (item1 != null && sold != null) {
            item1 = item1.func_77946_l();
            sold = sold.func_77946_l();
            if (item2 != null) {
               item2 = item2.func_77946_l();
            }

            MerchantRecipe recipe = new MerchantRecipe(item1, item2, sold);
            recipe.func_82783_a(2147483639);
            MerchantRecipeList merchantrecipelist = this.theIMerchant.func_70934_b(this.field_73882_e.field_71439_g);
            merchantrecipelist.add(recipe);
            NoppesUtil.sendData(EnumPacketType.MerchantUpdate, ItemInteractEvent.Merchant.field_70157_k, merchantrecipelist);
         }
      }

      if (flag) {
         ((ContainerMerchantAdd)this.field_74193_d).setCurrentRecipeIndex(this.currentRecipeIndex);
         ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
         DataOutputStream dataoutputstream = new DataOutputStream(bytearrayoutputstream);

         try {
            dataoutputstream.writeInt(this.currentRecipeIndex);
            this.field_73882_e.func_71391_r().func_72552_c(new Packet250CustomPayload("MC|TrSel", bytearrayoutputstream.toByteArray()));
         } catch (Exception exception) {
            exception.printStackTrace();
         }
      }

   }

   protected void func_74185_a(float par1, int par2, int par3) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_73882_e.func_110434_K().func_110577_a(merchantGuiTextures);
      int k = (this.field_73880_f - this.field_74194_b) / 2;
      int l = (this.field_73881_g - this.field_74195_c) / 2;
      this.func_73729_b(k, l, 0, 0, this.field_74194_b, this.field_74195_c);
      MerchantRecipeList merchantrecipelist = this.theIMerchant.func_70934_b(this.field_73882_e.field_71439_g);
      if (merchantrecipelist != null && !merchantrecipelist.isEmpty()) {
         int i1 = this.currentRecipeIndex;
         MerchantRecipe merchantrecipe = (MerchantRecipe)merchantrecipelist.get(i1);
         if (merchantrecipe.func_82784_g()) {
            this.field_73882_e.func_110434_K().func_110577_a(merchantGuiTextures);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(2896);
            this.func_73729_b(this.field_74198_m + 83, this.field_74197_n + 21, 212, 0, 28, 21);
            this.func_73729_b(this.field_74198_m + 83, this.field_74197_n + 51, 212, 0, 28, 21);
         }
      }

   }

   public void func_73863_a(int par1, int par2, float par3) {
      super.func_73863_a(par1, par2, par3);
      MerchantRecipeList merchantrecipelist = this.theIMerchant.func_70934_b(this.field_73882_e.field_71439_g);
      if (merchantrecipelist != null && !merchantrecipelist.isEmpty()) {
         int k = (this.field_73880_f - this.field_74194_b) / 2;
         int l = (this.field_73881_g - this.field_74195_c) / 2;
         int i1 = this.currentRecipeIndex;
         MerchantRecipe merchantrecipe = (MerchantRecipe)merchantrecipelist.get(i1);
         GL11.glPushMatrix();
         ItemStack itemstack = merchantrecipe.func_77394_a();
         ItemStack itemstack1 = merchantrecipe.func_77396_b();
         ItemStack itemstack2 = merchantrecipe.func_77397_d();
         RenderHelper.func_74520_c();
         GL11.glDisable(2896);
         GL11.glEnable(32826);
         GL11.glEnable(2903);
         GL11.glEnable(2896);
         field_74196_a.field_77023_b = 100.0F;
         field_74196_a.func_82406_b(this.field_73886_k, this.field_73882_e.func_110434_K(), itemstack, k + 36, l + 24);
         field_74196_a.func_77021_b(this.field_73886_k, this.field_73882_e.func_110434_K(), itemstack, k + 36, l + 24);
         if (itemstack1 != null) {
            field_74196_a.func_82406_b(this.field_73886_k, this.field_73882_e.func_110434_K(), itemstack1, k + 62, l + 24);
            field_74196_a.func_77021_b(this.field_73886_k, this.field_73882_e.func_110434_K(), itemstack1, k + 62, l + 24);
         }

         field_74196_a.func_82406_b(this.field_73886_k, this.field_73882_e.func_110434_K(), itemstack2, k + 120, l + 24);
         field_74196_a.func_77021_b(this.field_73886_k, this.field_73882_e.func_110434_K(), itemstack2, k + 120, l + 24);
         field_74196_a.field_77023_b = 0.0F;
         GL11.glDisable(2896);
         if (this.func_74188_c(36, 24, 16, 16, par1, par2)) {
            this.func_74184_a(itemstack, par1, par2);
         } else if (itemstack1 != null && this.func_74188_c(62, 24, 16, 16, par1, par2)) {
            this.func_74184_a(itemstack1, par1, par2);
         } else if (this.func_74188_c(120, 24, 16, 16, par1, par2)) {
            this.func_74184_a(itemstack2, par1, par2);
         }

         GL11.glPopMatrix();
         GL11.glEnable(2896);
         GL11.glEnable(2929);
         RenderHelper.func_74519_b();
      }

   }

   public IMerchant getIMerchant() {
      return this.theIMerchant;
   }

   static ResourceLocation func_110417_h() {
      return merchantGuiTextures;
   }
}
