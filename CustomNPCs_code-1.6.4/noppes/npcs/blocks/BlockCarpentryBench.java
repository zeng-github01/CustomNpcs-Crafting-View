package noppes.npcs.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.constants.EnumGuiType;

public class BlockCarpentryBench extends BlockContainer {
   public BlockCarpentryBench(int par1) {
      super(par1, Material.field_76243_f);
      this.func_71849_a(CustomItems.tab);
   }

   public boolean func_71903_a(World par1World, int i, int j, int k, EntityPlayer player, int par6, float par7, float par8, float par9) {
      if (!par1World.field_72995_K) {
         player.openGui(CustomNpcs.instance, EnumGuiType.PlayerAnvil.ordinal(), par1World, i, j, k);
      }

      return true;
   }

   public TileEntity func_72274_a(World var1) {
      return new TileBlockAnvil();
   }

   public boolean func_71926_d() {
      return false;
   }

   public int func_71857_b() {
      return -1;
   }

   public boolean func_71886_c() {
      return false;
   }

   public void func_71860_a(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLiving, ItemStack item) {
      int var6 = MathHelper.func_76128_c((double)(par5EntityLiving.field_70177_z / 90.0F) + (double)0.5F) & 3;
      par1World.func_72921_c(par2, par3, par4, var6, 2);
   }
}
