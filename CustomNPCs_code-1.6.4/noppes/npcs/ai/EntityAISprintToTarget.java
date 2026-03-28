package noppes.npcs.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import noppes.npcs.EntityNPCInterface;

public class EntityAISprintToTarget extends EntityAIBase {
   EntityNPCInterface runner;
   EntityLivingBase runTarget;

   public EntityAISprintToTarget(EntityNPCInterface par1EntityLiving) {
      this.runner = par1EntityLiving;
      this.func_75248_a(8);
   }

   public boolean func_75250_a() {
      this.runTarget = this.runner.func_70638_az();
      if (this.runTarget == null) {
         return false;
      } else if (this.runner.func_70661_as().func_75500_f()) {
         return false;
      } else {
         switch (this.runner.ai.onAttack) {
            case 0:
               return this.runner.func_70068_e(this.runTarget) >= (double)64.0F ? this.runner.field_70122_E : false;
            case 2:
               return this.runner.func_70068_e(this.runTarget) <= (double)49.0F ? this.runner.field_70122_E : false;
            default:
               return false;
         }
      }
   }

   public boolean func_75253_b() {
      return this.runner.func_70089_S() && this.runner.field_70122_E && this.runner.field_70737_aN <= 0 && this.runner.field_70159_w != (double)0.0F && this.runner.field_70179_y != (double)0.0F;
   }

   public void func_75249_e() {
      this.runner.func_70031_b(true);
   }

   public void func_75251_c() {
      this.runner.func_70031_b(false);
   }
}
