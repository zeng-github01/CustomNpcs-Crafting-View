package noppes.npcs.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;
import noppes.npcs.EntityNPCInterface;

public class EntityAIDodgeShoot extends EntityAIBase {
   private EntityNPCInterface entity;
   private double xPosition;
   private double yPosition;
   private double zPosition;

   public EntityAIDodgeShoot(EntityNPCInterface par1EntityNPCInterface) {
      this.entity = par1EntityNPCInterface;
      this.func_75248_a(1);
   }

   public boolean func_75250_a() {
      EntityLivingBase var1 = this.entity.func_70638_az();
      if (var1 != null && var1.func_70089_S()) {
         if (this.entity.inventory.getProjectile() == null) {
            return false;
         } else if (this.entity.getRangedTask() == null) {
            return false;
         } else {
            Vec3 vec = this.entity.getRangedTask().hasFired() ? RandomPositionGenerator.func_75463_a(this.entity, 4, 1) : null;
            if (vec == null) {
               return false;
            } else {
               this.xPosition = vec.field_72450_a;
               this.yPosition = vec.field_72448_b;
               this.zPosition = vec.field_72449_c;
               return true;
            }
         }
      } else {
         return false;
      }
   }

   public boolean func_75253_b() {
      return !this.entity.func_70661_as().func_75500_f();
   }

   public void func_75249_e() {
      this.entity.func_70661_as().func_75492_a(this.xPosition, this.yPosition, this.zPosition, 1.2);
   }

   public void func_75246_d() {
      if (this.entity.func_70638_az() != null) {
         this.entity.func_70671_ap().func_75651_a(this.entity.func_70638_az(), 30.0F, 30.0F);
      }

   }
}
