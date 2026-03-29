package noppes.npcs.blocks;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import noppes.npcs.CustomNpcs;
import noppes.npcs.controllers.Availability;

public class TileRedstoneBlock extends TileEntity {
   public int onRangeX = 6;
   public int onRangeY = 6;
   public int onRangeZ = 6;
   public int offRangeX = 10;
   public int offRangeY = 10;
   public int offRangeZ = 10;
   public Availability availability = new Availability();
   public boolean isActivated = false;
   private int ticks = 10;

   public void func_70316_g() {
      if (!this.field_70331_k.field_72995_K) {
         --this.ticks;
         if (this.ticks <= 0) {
            this.ticks = 20;
            Block block = Block.field_71973_m[this.field_70331_k.func_72798_a(this.field_70329_l, this.field_70330_m, this.field_70327_n)];
            if (block != null && block instanceof BlockNpcRedstone) {
               if (CustomNpcs.FreezeNPCs) {
                  if (this.isActivated) {
                     this.setActive(block, false);
                  }

               } else {
                  if (!this.isActivated) {
                     List<EntityPlayer> list = this.getPlayerList(this.onRangeX, this.onRangeY, this.onRangeZ);
                     if (list.isEmpty()) {
                        return;
                     }

                     for(EntityPlayer player : list) {
                        if (this.availability.isAvailable(player)) {
                           this.setActive(block, true);
                           return;
                        }
                     }
                  } else {
                     for(EntityPlayer player : this.getPlayerList(this.offRangeX, this.offRangeY, this.offRangeZ)) {
                        if (this.availability.isAvailable(player)) {
                           return;
                        }
                     }

                     this.setActive(block, false);
                  }

               }
            } else {
               this.field_70331_k.func_72932_q(this.field_70329_l, this.field_70330_m, this.field_70327_n);
            }
         }
      }
   }

   private void setActive(Block block, boolean bo) {
      this.isActivated = bo;
      this.field_70331_k.func_72921_c(this.field_70329_l, this.field_70330_m, this.field_70327_n, this.isActivated ? 1 : 0, 2);
      this.field_70331_k.func_72845_h(this.field_70329_l, this.field_70330_m, this.field_70327_n);
      if (this.func_70309_m()) {
         block.func_71861_g(this.field_70331_k, this.field_70329_l, this.field_70330_m, this.field_70327_n);
      }

   }

   private List getPlayerList(int x, int y, int z) {
      return this.field_70331_k.func_72872_a(EntityPlayer.class, AxisAlignedBB.func_72330_a((double)this.field_70329_l, (double)this.field_70330_m, (double)this.field_70327_n, (double)(this.field_70329_l + 1), (double)(this.field_70330_m + 1), (double)(this.field_70327_n + 1)).func_72314_b((double)x, (double)y, (double)z));
   }

   public void func_70307_a(NBTTagCompound par1NBTTagCompound) {
      super.func_70307_a(par1NBTTagCompound);
      this.onRangeX = par1NBTTagCompound.func_74762_e("BlockOnRangeX");
      this.onRangeY = par1NBTTagCompound.func_74762_e("BlockOnRangeY");
      this.onRangeZ = par1NBTTagCompound.func_74762_e("BlockOnRangeZ");
      this.offRangeX = par1NBTTagCompound.func_74762_e("BlockOffRangeX");
      this.offRangeY = par1NBTTagCompound.func_74762_e("BlockOffRangeY");
      this.offRangeZ = par1NBTTagCompound.func_74762_e("BlockOffRangeZ");
      this.isActivated = par1NBTTagCompound.func_74767_n("BlockActivated");
      this.availability.readFromNBT(par1NBTTagCompound);
      if (this.func_70309_m()) {
         this.setActive(this.func_70311_o(), this.isActivated);
      }

   }

   public void func_70310_b(NBTTagCompound par1NBTTagCompound) {
      super.func_70310_b(par1NBTTagCompound);
      par1NBTTagCompound.func_74768_a("BlockOnRangeX", this.onRangeX);
      par1NBTTagCompound.func_74768_a("BlockOnRangeY", this.onRangeY);
      par1NBTTagCompound.func_74768_a("BlockOnRangeZ", this.onRangeZ);
      par1NBTTagCompound.func_74768_a("BlockOffRangeX", this.offRangeX);
      par1NBTTagCompound.func_74768_a("BlockOffRangeY", this.offRangeY);
      par1NBTTagCompound.func_74768_a("BlockOffRangeZ", this.offRangeZ);
      par1NBTTagCompound.func_74757_a("BlockActivated", this.isActivated);
      this.availability.writeToNBT(par1NBTTagCompound);
   }
}
