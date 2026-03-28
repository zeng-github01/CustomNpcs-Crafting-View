package noppes.npcs.ai;

import net.minecraft.entity.ai.EntityAIBase;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.constants.EnumMovingType;

public class EntityAIReturn extends EntityAIBase {
   private final EntityNPCInterface npc;
   private int stuckTicks = 0;
   private int returnTicks = 0;
   private double posX;
   private double posY;
   private double posZ;
   private boolean wasAttacked = false;
   private double[] preAttackPos;

   public EntityAIReturn(EntityNPCInterface npc) {
      this.npc = npc;
      this.func_75248_a(1);
   }

   public boolean func_75250_a() {
      if (this.npc.isFollowerWithOwner()) {
         return false;
      } else if (!this.npc.ai.returnToStart) {
         return false;
      } else if (this.npc.ai.findShelter != 0 || this.npc.field_70170_p.func_72935_r() && !this.npc.field_70170_p.func_72896_J() || this.npc.field_70170_p.field_73011_w.field_76576_e || !this.npc.field_70170_p.func_72937_j((int)this.npc.getStartXPos(), (int)this.npc.getStartYPos(), (int)this.npc.getStartZPos()) && this.npc.field_70170_p.func_72883_k((int)this.npc.getStartXPos(), (int)this.npc.getStartYPos(), (int)this.npc.getStartZPos()) > 8) {
         if (this.npc.ai.findShelter == 1 && this.npc.field_70170_p.func_72935_r() && this.npc.field_70170_p.func_72937_j((int)this.npc.getStartXPos(), (int)this.npc.getStartYPos(), (int)this.npc.getStartZPos())) {
            return false;
         } else if (this.npc.isAttacking()) {
            if (!this.wasAttacked) {
               this.wasAttacked = true;
               this.preAttackPos = new double[]{this.npc.field_70165_t, this.npc.field_70163_u, this.npc.field_70161_v};
            }

            return false;
         } else if (!this.npc.isAttacking() && this.wasAttacked) {
            return true;
         } else if (this.npc.ai.movingType == EnumMovingType.Wandering) {
            return this.npc.func_70011_f((double)this.npc.getStartXPos(), this.npc.getStartYPos(), (double)this.npc.getStartZPos()) > (double)this.npc.ai.walkingRange;
         } else if (this.npc.ai.movingType == EnumMovingType.Standing) {
            return !this.npc.isVeryNearAssignedPlace();
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean func_75253_b() {
      if (!this.wasAttacked || this.returnTicks < 30 || !this.npc.func_70661_as().func_75500_f() && !this.isTooFar()) {
         return !this.npc.isAttacking() && this.stuckTicks > 0 && !this.npc.isVeryNearAssignedPlace();
      } else {
         return false;
      }
   }

   public void func_75246_d() {
      if (this.returnTicks < 30) {
         ++this.returnTicks;
      } else {
         if (this.returnTicks == 30) {
            ++this.returnTicks;
            if (this.isTooFar()) {
               this.npc.func_70107_b(this.posX, this.posY, this.posZ);
            }
         } else if (this.npc.func_70661_as().func_75500_f()) {
            ++this.stuckTicks;
            if (this.stuckTicks == 30) {
               this.stuckTicks = 0;
               this.npc.func_70107_b(this.posX, this.posY, this.posZ);
            }
         } else {
            this.stuckTicks = 1;
         }

      }
   }

   private boolean isTooFar() {
      int allowedDistance = this.npc.stats.aggroRange * 2;
      if (this.npc.ai.movingType == EnumMovingType.Wandering) {
         allowedDistance += this.npc.ai.walkingRange;
      }

      return this.npc.func_70011_f(this.posX, this.posY, this.posZ) > (double)allowedDistance;
   }

   public void func_75249_e() {
      if (this.wasAttacked) {
         this.posX = this.preAttackPos[0];
         this.posY = this.preAttackPos[1];
         this.posZ = this.preAttackPos[2];
      } else {
         this.posX = (double)this.npc.getStartXPos();
         this.posY = this.npc.getStartYPos();
         this.posZ = (double)this.npc.getStartZPos();
      }

      this.npc.func_70661_as().func_75492_a(this.posX, this.posY, this.posZ, (double)1.0F);
      this.stuckTicks = 1;
      this.returnTicks = 0;
   }

   public void func_75251_c() {
      this.wasAttacked = false;
   }
}
