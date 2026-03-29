package noppes.npcs.roles;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.NoppesUtilServer;

public class JobHealer extends JobInterface {
   private long healTicks = 0L;
   public int range = 5;
   public int speed = 5;
   private List toHeal = new ArrayList();

   public JobHealer(EntityNPCInterface npc) {
      super(npc);
   }

   public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.func_74768_a("HealerRange", this.range);
      nbttagcompound.func_74768_a("HealerSpeed", this.speed);
   }

   public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
      this.range = nbttagcompound.func_74762_e("HealerRange");
      this.speed = nbttagcompound.func_74762_e("HealerSpeed");
   }

   public boolean aiShouldExecute() {
      ++this.healTicks;
      if (this.healTicks < (long)(this.speed * 10)) {
         return false;
      } else {
         for(Object plObj : this.npc.field_70170_p.func_72872_a(EntityLivingBase.class, this.npc.field_70121_D.func_72314_b((double)this.range, (double)(this.range / 2), (double)this.range))) {
            EntityLivingBase entity = (EntityLivingBase)plObj;
            if (entity instanceof EntityPlayer) {
               EntityPlayer player = (EntityPlayer)entity;
               if (player.func_110143_aJ() < player.func_110138_aP() && !this.npc.getFaction().isAggressiveToPlayer(player)) {
                  this.toHeal.add(player);
               }
            }

            if (entity instanceof EntityNPCInterface) {
               EntityNPCInterface npc = (EntityNPCInterface)entity;
               if (npc.func_110143_aJ() < npc.func_110138_aP() && !this.npc.getFaction().isAggressiveToNpc(npc)) {
                  this.toHeal.add(npc);
               }
            }
         }

         this.healTicks = 0L;
         return !this.toHeal.isEmpty();
      }
   }

   public void aiStartExecuting() {
      for(EntityLivingBase entity : this.toHeal) {
         float heal = entity.func_110138_aP() / 20.0F;
         entity.func_70691_i(heal > 0.0F ? heal : 1.0F);
         NoppesUtilServer.spawnParticle(entity, "heal", entity.field_71093_bK);
      }

      this.toHeal.clear();
   }
}
