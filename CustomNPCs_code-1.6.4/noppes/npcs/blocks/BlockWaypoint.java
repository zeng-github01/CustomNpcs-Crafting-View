package noppes.npcs.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.constants.EnumPacketType;

public class BlockWaypoint extends BlockContainer {
   public BlockWaypoint(int par1) {
      super(par1, Material.field_76246_e);
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
            NoppesUtilServer.sendData(player, EnumPacketType.WaypointSave, compound);
            return true;
         } else {
            return false;
         }
      }
   }

   public void func_71860_a(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack item) {
      if (entityliving instanceof EntityPlayer && world.field_72995_K) {
         CustomNpcs.proxy.openGui(i, j, k, EnumGuiType.Waypoint, (EntityPlayer)entityliving);
      }

   }

   public TileEntity func_72274_a(World world) {
      return new TileWaypoint();
   }
}
