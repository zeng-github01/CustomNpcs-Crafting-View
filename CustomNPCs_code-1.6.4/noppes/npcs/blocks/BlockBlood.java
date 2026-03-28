package noppes.npcs.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import noppes.npcs.CustomItems;

public class BlockBlood extends Block {
   @SideOnly(Side.CLIENT)
   private Icon field_94458_cO;
   @SideOnly(Side.CLIENT)
   private Icon field_94459_cP;

   public BlockBlood(int par1) {
      super(par1, Material.field_76246_e);
      this.func_71875_q();
      this.func_71849_a(CustomItems.tabMisc);
      this.func_71905_a(0.01F, 0.01F, 0.01F, 0.99F, 0.99F, 0.99F);
      this.func_71900_a(0.08F);
   }

   @SideOnly(Side.CLIENT)
   public Icon func_71858_a(int par1, int par2) {
      if (par2 % 3 == 1) {
         return this.field_94459_cP;
      } else {
         return par2 % 3 == 2 ? this.field_94458_cO : this.field_94336_cN;
      }
   }

   public AxisAlignedBB func_71872_e(World world, int i, int j, int k) {
      return null;
   }

   public AxisAlignedBB func_71911_a_(World par1World, int par2, int par3, int par4) {
      return AxisAlignedBB.func_72332_a().func_72299_a((double)par2, (double)par3, (double)par4, (double)par2, (double)par3, (double)par4);
   }

   public boolean func_71886_c() {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public void func_94332_a(IconRegister par1IconRegister) {
      this.field_94336_cN = par1IconRegister.func_94245_a(this.func_111023_E());
      this.field_94459_cP = par1IconRegister.func_94245_a(this.func_111023_E() + "2");
      this.field_94458_cO = par1IconRegister.func_94245_a(this.func_111023_E() + "3");
   }

   public boolean func_71877_c(IBlockAccess world, int par2, int par3, int par4, int par5) {
      Block block = Block.field_71973_m[world.func_72798_a(par2, par3, par4)];
      return block != null && block.func_71886_c();
   }

   public boolean func_71926_d() {
      return false;
   }

   public int func_71856_s_() {
      return 1;
   }

   public void func_71860_a(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLiving, ItemStack item) {
      int var6 = MathHelper.func_76128_c((double)(par5EntityLiving.field_70177_z / 90.0F) + (double)0.5F) & 3;
      par1World.func_72921_c(par2, par3, par4, var6, 2);
   }
}
