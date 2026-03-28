package noppes.npcs.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;
import noppes.npcs.EntityNPCInterface;

public class EntityAIOccupyBed extends EntityAIBase {
   private final EntityNPCInterface npc;
   private int maxSleepingTicks = 0;
   private int bedX = 0;
   private int bedY = 0;
   private int bedZ = 0;

   public EntityAIOccupyBed(EntityNPCInterface par1EntityNPCInterface) {
      this.npc = par1EntityNPCInterface;
      this.func_75248_a(5);
   }

   public boolean func_75250_a() {
      return !this.npc.isSleeping() && this.getNearbyBedDistance() && !this.npc.field_70170_p.func_72935_r();
   }

   public boolean func_75253_b() {
      return !this.npc.field_70170_p.func_72935_r() && this.isSittableBlock(this.npc.field_70170_p, this.bedX, this.bedY, this.bedZ);
   }

   public void func_75249_e() {
      this.npc.func_70661_as().func_75492_a((double)((float)this.bedX) + (double)0.5F, (double)(this.bedY + 1), (double)((float)this.bedZ) + (double)0.5F, (double)1.0F);
      this.npc.setSleeping(false);
   }

   public void func_75251_c() {
      this.npc.setSleeping(false);
      this.occupyBed(this.npc.field_70170_p, this.bedX, this.bedY, this.bedZ, false);
   }

   public void func_75246_d() {
      if (this.npc.func_70092_e((double)this.bedX, (double)(this.bedY + 1), (double)this.bedZ) > (double)1.5F) {
         this.npc.setSleeping(false);
         this.npc.func_70661_as().func_75492_a((double)((float)this.bedX), (double)(this.bedY + 1), (double)((float)this.bedZ), (double)1.0F);
      } else if (!this.npc.isSleeping()) {
         this.npc.field_70126_B = this.npc.field_70177_z = this.npc.field_70760_ar = this.npc.field_70761_aq = (float)this.getDirection(this.npc.field_70170_p, this.bedX, this.bedY, this.bedZ);
         this.npc.setSleeping(true);
         this.occupyBed(this.npc.field_70170_p, this.bedX, this.bedY, this.bedZ, true);
      }

   }

   protected boolean getNearbyBedDistance() {
      int var1 = (int)this.npc.field_70163_u;
      double var2 = (double)Integer.MAX_VALUE;

      for(int var4 = (int)this.npc.field_70165_t - 8; (double)var4 < this.npc.field_70165_t + (double)8.0F; ++var4) {
         for(int var5 = (int)this.npc.field_70161_v - 8; (double)var5 < this.npc.field_70161_v + (double)8.0F; ++var5) {
            if (this.isSittableBlock(this.npc.field_70170_p, var4, var1, var5) && this.npc.field_70170_p.func_72799_c(var4, var1 + 1, var5)) {
               double var6 = this.npc.func_70092_e((double)var4, (double)var1, (double)var5);
               if (var6 < var2) {
                  this.bedX = var4;
                  this.bedY = var1;
                  this.bedZ = var5;
                  var2 = var6;
               }
            }
         }
      }

      return var2 < (double)Integer.MAX_VALUE;
   }

   protected boolean isSittableBlock(World par1World, int par2, int par3, int par4) {
      int var5 = par1World.func_72798_a(par2, par3, par4);
      int var6 = par1World.func_72805_g(par2, par3, par4);
      return var5 == Block.field_71959_S.field_71990_ca && !BlockBed.func_72229_a_(var6);
   }

   protected int getDirection(World par1World, int par2, int par3, int par4) {
      int orientation = -1;
      int var6 = par1World.func_72805_g(par2, par3, par4);
      int var7 = BlockBed.func_72217_d(var6);
      switch (var7) {
         case 0:
            orientation = 180;
            break;
         case 1:
            orientation = 270;
            break;
         case 2:
            orientation = 0;
            break;
         case 3:
            orientation = 90;
      }

      return orientation;
   }

   protected void occupyBed(World par1World, int par2, int par3, int par4, boolean par5) {
      int var5 = par1World.func_72798_a(par2, par3, par4);
      if (var5 == Block.field_71959_S.field_71990_ca) {
         BlockBed.func_72228_a(par1World, par2, par3, par4, par5);
      }

   }
}
