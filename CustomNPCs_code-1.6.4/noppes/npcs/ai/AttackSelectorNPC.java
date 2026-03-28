package noppes.npcs.ai;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.constants.EnumJobType;
import noppes.npcs.constants.EnumMovingType;
import noppes.npcs.roles.JobGuard;

public class AttackSelectorNPC implements IEntitySelector {
   private EntityNPCInterface npc;

   public AttackSelectorNPC(EntityNPCInterface npc) {
      this.npc = npc;
   }

   public boolean func_82704_a(Entity entity) {
      if (entity != this.npc && entity instanceof EntityLivingBase && !(this.npc.func_70032_d(entity) > (float)this.npc.stats.aggroRange)) {
         if (this.npc.ai.directLOS && !this.npc.func_70635_at().func_75522_a(entity)) {
            return false;
         } else {
            if (!this.npc.isFollowerWithOwner() && this.npc.ai.returnToStart) {
               int allowedDistance = this.npc.stats.aggroRange * 2;
               if (this.npc.ai.movingType == EnumMovingType.Wandering) {
                  allowedDistance += this.npc.ai.walkingRange;
               }

               if (entity.func_70011_f((double)this.npc.getStartXPos(), this.npc.getStartYPos(), (double)this.npc.getStartZPos()) > (double)allowedDistance) {
                  return false;
               }
            }

            if (this.npc.advanced.job == EnumJobType.Guard && ((JobGuard)this.npc.jobInterface).isEntityApplicable((EntityLivingBase)entity)) {
               return true;
            } else if (entity instanceof EntityPlayer) {
               return this.npc.getFaction().isAggressiveToPlayer((EntityPlayer)entity);
            } else {
               if (entity instanceof EntityNPCInterface) {
                  if (((EntityNPCInterface)entity).isKilled()) {
                     return false;
                  }

                  if (this.npc.advanced.attackOtherFactions) {
                     return this.npc.getFaction().isAggressiveToNpc((EntityNPCInterface)entity);
                  }
               }

               return false;
            }
         }
      } else {
         return false;
      }
   }
}
