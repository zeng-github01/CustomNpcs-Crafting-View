package noppes.npcs.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import noppes.npcs.CustomItems;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.constants.EnumPacketType;

public class BlockMailbox extends BlockContainer {
   public int renderId = -1;

   public BlockMailbox(int par1) {
      super(par1, Material.field_76246_e);
      this.func_71849_a(CustomItems.tab);
   }

   public void func_71879_a(int par1, CreativeTabs par2CreativeTabs, List par3List) {
      par3List.add(new ItemStack(par1, 1, 0));
      par3List.add(new ItemStack(par1, 1, 1));
   }

   public boolean func_71903_a(World par1World, int i, int j, int k, EntityPlayer player, int par6, float par7, float par8, float par9) {
      if (!par1World.field_72995_K) {
         NoppesUtilServer.sendData(player, EnumPacketType.Gui, EnumGuiType.PlayerMailbox);
      }

      return true;
   }

   public TileEntity func_72274_a(World world) {
      return new TileMailbox();
   }

   public ArrayList getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
      ArrayList<ItemStack> ret = new ArrayList();
      ret.add(new ItemStack(this, 1, this.func_71873_h(world, x, y, z)));
      return ret;
   }

   public int func_71899_b(int par1) {
      return par1 >> 2;
   }

   public void func_71860_a(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
      int l = MathHelper.func_76128_c((double)(par5EntityLivingBase.field_70177_z * 4.0F / 360.0F) + (double)0.5F) & 3;
      l %= 4;
      par1World.func_72921_c(par2, par3, par4, l | par6ItemStack.func_77960_j() << 2, 2);
   }

   public boolean func_71926_d() {
      return false;
   }

   public boolean func_71886_c() {
      return false;
   }

   public int func_71857_b() {
      return this.renderId;
   }

   @SideOnly(Side.CLIENT)
   public Icon func_71858_a(int par1, int par2) {
      return Block.field_72013_bc.func_71851_a(par1);
   }
}
