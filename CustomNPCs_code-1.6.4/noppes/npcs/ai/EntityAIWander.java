package noppes.npcs.ai;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.Vec3;
import noppes.npcs.CustomNpcs;
import noppes.npcs.EntityNPCInterface;

public class EntityAIWander extends EntityAIBase {
   private EntityNPCInterface entity;
   private double xPosition;
   private double yPosition;
   private double zPosition;

   public EntityAIWander(EntityNPCInterface par1EntityNPCInterface) {
      this.entity = par1EntityNPCInterface;
      this.func_75248_a(1);
   }

   public boolean func_75250_a() {
      if (this.entity.func_70654_ax() >= 100) {
         return false;
      } else if (this.entity.func_70681_au().nextInt(80) != 0) {
         return false;
      } else {
         Vec3 vec = this.getVec();
         if (vec == null) {
            return false;
         } else {
            this.xPosition = vec.field_72450_a;
            this.yPosition = vec.field_72448_b;
            this.zPosition = vec.field_72449_c;
            return true;
         }
      }
   }

   private Vec3 getVec() {
      if (this.entity.ai.walkingRange > 0) {
         double distance = this.entity.func_70011_f((double)this.entity.getStartXPos(), this.entity.getStartYPos(), (double)this.entity.getStartZPos());
         int range = (int)((double)this.entity.ai.walkingRange - distance);
         if (range > CustomNpcs.NpcNavRange) {
            range = CustomNpcs.NpcNavRange;
         }

         if (range < 3) {
            range = this.entity.ai.walkingRange;
            if (range > CustomNpcs.NpcNavRange) {
               range = CustomNpcs.NpcNavRange;
            }

            Vec3 start = this.entity.field_70170_p.func_82732_R().func_72345_a((double)this.entity.getStartXPos(), this.entity.getStartYPos(), (double)this.entity.getStartZPos());
            return RandomPositionGeneratorAlt.findRandomTargetBlockTowards(this.entity, range / 2, 7, start);
         } else {
            return RandomPositionGeneratorAlt.findRandomTarget(this.entity, range, 7);
         }
      } else {
         return RandomPositionGeneratorAlt.findRandomTarget(this.entity, CustomNpcs.NpcNavRange, 7);
      }
   }

   public boolean func_75253_b() {
      return !this.entity.func_70661_as().func_75500_f();
   }

   public void func_75249_e() {
      this.entity.func_70661_as().func_75492_a(this.xPosition, this.yPosition, this.zPosition, 0.7);
   }
}
