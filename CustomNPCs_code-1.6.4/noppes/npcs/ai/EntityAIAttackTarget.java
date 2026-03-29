package noppes.npcs.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import noppes.npcs.EntityNPCInterface;

public class EntityAIAttackTarget extends EntityAIBase {
   World worldObj;
   EntityNPCInterface attacker;
   EntityLivingBase entityTarget;
   int attackTick = 0;
   boolean field_75437_f;
   PathEntity entityPathEntity;
   private int field_75445_i;

   public EntityAIAttackTarget(EntityNPCInterface par1EntityLiving, boolean par3) {
      this.attacker = par1EntityLiving;
      this.worldObj = par1EntityLiving.field_70170_p;
      this.field_75437_f = par3;
      this.func_75248_a(3);
   }

   public boolean func_75250_a() {
      EntityLivingBase entitylivingbase = this.attacker.func_70638_az();
      if (entitylivingbase == null) {
         return false;
      } else if (!entitylivingbase.func_70089_S()) {
         return false;
      } else if (this.attacker.inventory.getProjectile() != null && this.attacker.ai.useRangeMelee == 0) {
         return false;
      } else {
         double var2 = this.attacker.func_70092_e(entitylivingbase.field_70165_t, entitylivingbase.field_70121_D.field_72338_b, entitylivingbase.field_70161_v);
         double var3 = (double)(this.attacker.ai.distanceToMelee * this.attacker.ai.distanceToMelee);
         if (this.attacker.ai.useRangeMelee == 1 && var2 > var3) {
            return false;
         } else {
            this.entityTarget = entitylivingbase;
            this.entityPathEntity = this.attacker.func_70661_as().func_75494_a(entitylivingbase);
            return this.entityPathEntity != null;
         }
      }
   }

   public boolean func_75253_b() {
      this.entityTarget = this.attacker.func_70638_az();
      if (this.entityTarget != null && !(this.attacker.func_70068_e(this.entityTarget) > this.attacker.func_70068_e(this.entityTarget))) {
         if (this.attacker.ai.useRangeMelee == 1 && this.attacker.func_70068_e(this.entityTarget) > (double)(this.attacker.ai.distanceToMelee * this.attacker.ai.distanceToMelee)) {
            return false;
         } else {
            return !this.entityTarget.func_70089_S() ? false : (!this.field_75437_f ? !this.attacker.func_70661_as().func_75500_f() : this.attacker.func_110176_b(MathHelper.func_76128_c(this.entityTarget.field_70165_t), MathHelper.func_76128_c(this.entityTarget.field_70163_u), MathHelper.func_76128_c(this.entityTarget.field_70161_v)));
         }
      } else {
         return false;
      }
   }

   public void func_75249_e() {
      this.attacker.func_70661_as().func_75484_a(this.entityPathEntity, (double)1.0F);
      this.field_75445_i = 0;
   }

   public void func_75251_c() {
      this.entityPathEntity = null;
      this.entityTarget = null;
      this.attacker.func_70624_b((EntityLivingBase)null);
      this.attacker.func_70661_as().func_75499_g();
   }

   public void func_75246_d() {
      this.attacker.func_70671_ap().func_75651_a(this.entityTarget, 30.0F, 30.0F);
      if ((this.field_75437_f || this.attacker.func_70635_at().func_75522_a(this.entityTarget)) && --this.field_75445_i <= 0) {
         this.field_75445_i = 4 + this.attacker.func_70681_au().nextInt(7);
         this.attacker.func_70661_as().func_75497_a(this.entityTarget, (double)1.0F);
      }

      this.attackTick = Math.max(this.attackTick - 1, 0);
      if (this.attacker.func_70011_f(this.entityTarget.field_70165_t, this.entityTarget.field_70121_D.field_72338_b, this.entityTarget.field_70161_v) <= (double)this.attacker.stats.attackRange && this.attacker.func_70685_l(this.entityTarget) && this.attackTick <= 0) {
         this.attackTick = this.attacker.stats.attackSpeed;
         this.attacker.func_71038_i();
         this.attacker.func_70652_k(this.entityTarget);
      }

   }
}
