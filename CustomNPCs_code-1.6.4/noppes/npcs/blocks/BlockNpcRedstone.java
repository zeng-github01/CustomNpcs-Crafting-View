package noppes.npcs.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.constants.EnumPacketType;

public class BlockNpcRedstone extends BlockContainer {
   public BlockNpcRedstone(int par1) {
      super(par1, Material.field_76265_p);
      this.func_71849_a(CustomItems.tab);
   }

   public boolean func_71903_a(World par1World, int i, int j, int k, EntityPlayer player, int par6, float par7, float par8, float par9) {
      if (par1World.field_72995_K) {
         return false;
      } else {
         ItemStack currentItem = player.field_71071_by.func_70448_g();
         if (currentItem != null && currentItem.field_77993_c == CustomItems.wand.field_77779_bT) {
            TileEntity tile = par1World.func_72796_p(i, j, k);
            NBTTagCompound compound = new NBTTagCompound();
            tile.func_70310_b(compound);
            NoppesUtilServer.sendData(player, EnumPacketType.RedstoneBlockSave, compound);
            return true;
         } else {
            return false;
         }
      }
   }

   public void func_71861_g(World par1World, int par2, int par3, int par4) {
      par1World.func_72898_h(par2, par3, par4, this.field_71990_ca);
      par1World.func_72898_h(par2, par3 - 1, par4, this.field_71990_ca);
      par1World.func_72898_h(par2, par3 + 1, par4, this.field_71990_ca);
      par1World.func_72898_h(par2 - 1, par3, par4, this.field_71990_ca);
      par1World.func_72898_h(par2 + 1, par3, par4, this.field_71990_ca);
      par1World.func_72898_h(par2, par3, par4 - 1, this.field_71990_ca);
      par1World.func_72898_h(par2, par3, par4 + 1, this.field_71990_ca);
   }

   public void func_71860_a(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack item) {
      if (entityliving instanceof EntityPlayer && world.field_72995_K) {
         CustomNpcs.proxy.openGui(i, j, k, EnumGuiType.RedstoneBlock, (EntityPlayer)entityliving);
      }

   }

   public void func_71898_d(World par1World, int par2, int par3, int par4, int par5) {
      this.func_71861_g(par1World, par2, par3, par4);
   }

   public int func_71920_b(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
      return this.isActivated(par1IBlockAccess, par2, par3, par4) > 0 ? 16739176 : super.func_71920_b(par1IBlockAccess, par2, par3, par4);
   }

   public int func_71855_c(IBlockAccess par1World, int par2, int par3, int par4, int par5) {
      return this.isActivated(par1World, par2, par3, par4);
   }

   public int func_71865_a(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
      return this.isActivated(par1IBlockAccess, par2, par3, par4);
   }

   public boolean func_71853_i() {
      return true;
   }

   public TileEntity func_72274_a(World var1) {
      return new TileRedstoneBlock();
   }

   public int isActivated(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
      return par1IBlockAccess.func_72805_g(par2, par3, par4) == 1 ? 15 : 0;
   }
}
