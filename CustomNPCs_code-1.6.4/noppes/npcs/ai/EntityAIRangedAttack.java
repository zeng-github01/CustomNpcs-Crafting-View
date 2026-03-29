package noppes.npcs.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.constants.EnumAnimation;
import noppes.npcs.constants.EnumNavType;

public class EntityAIRangedAttack extends EntityAIBase {
   private final EntityNPCInterface entityHost;
   private final IRangedAttackMob rangedAttackEntityHost;
   private EntityLivingBase attackTarget;
   private int rangedAttackTime = 0;
   private int field_75318_f = 0;
   private int field_70846_g = 0;
   private int attackTick = 0;
   private boolean hasFired = false;
   private boolean navOverride = false;

   public EntityAIRangedAttack(IRangedAttackMob par1IRangedAttackMob) {
      if (!(par1IRangedAttackMob instanceof EntityLivingBase)) {
         throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
      } else {
         this.rangedAttackEntityHost = par1IRangedAttackMob;
         this.entityHost = (EntityNPCInterface)par1IRangedAttackMob;
         this.rangedAttackTime = this.entityHost.stats.fireDelay / 2;
         this.func_75248_a(this.entityHost.ai.useRangeMelee != 2 && this.entityHost.ai.tacticalVariant != EnumNavType.Surround && this.entityHost.ai.tacticalVariant != EnumNavType.Stalk ? 3 : 4);
      }
   }

   public boolean func_75250_a() {
      EntityLivingBase var1 = this.entityHost.func_70638_az();
      if (var1 != null && var1.func_70089_S()) {
         if (this.entityHost.inventory.getProjectile() == null) {
            return false;
         } else {
            double var2 = this.entityHost.func_70092_e(var1.field_70165_t, var1.field_70121_D.field_72338_b, var1.field_70161_v);
            double var3 = (double)(this.entityHost.ai.distanceToMelee * this.entityHost.ai.distanceToMelee);
            if (this.entityHost.ai.useRangeMelee == 1 && var2 <= var3) {
               return false;
            } else if (this.entityHost.ai.useRangeMelee == 2 && var2 <= (double)16.0F) {
               return false;
            } else {
               this.attackTarget = var1;
               return true;
            }
         }
      } else {
         return false;
      }
   }

   public boolean func_75253_b() {
      return this.func_75250_a() || !this.entityHost.func_70661_as().func_75500_f();
   }

   public void func_75251_c() {
      this.attackTarget = null;
      this.entityHost.func_70624_b((EntityLivingBase)null);
      this.entityHost.func_70661_as().func_75499_g();
      this.field_75318_f = 0;
      this.hasFired = false;
      if (this.entityHost.ai.useRangeMelee != 2) {
         this.rangedAttackTime = this.entityHost.stats.fireDelay / 2;
      }

   }

   public void func_75246_d() {
      double var1 = this.entityHost.func_70092_e(this.attackTarget.field_70165_t, this.attackTarget.field_70121_D.field_72338_b, this.attackTarget.field_70161_v);
      float field_82642_h = (float)(this.entityHost.stats.rangedRange * this.entityHost.stats.rangedRange);
      if (this.entityHost.ai.directLOS && !this.entityHost.func_70635_at().func_75522_a(this.attackTarget)) {
         this.field_75318_f = 0;
      } else {
         ++this.field_75318_f;
      }

      if (this.entityHost.ai.useRangeMelee != 2 && !this.navOverride) {
         int i = this.entityHost.ai.tacticalVariant != EnumNavType.Default ? 5 : 20;
         if (var1 <= (double)field_82642_h && this.field_75318_f >= i) {
            this.entityHost.func_70661_as().func_75499_g();
         } else {
            this.entityHost.func_70661_as().func_75497_a(this.attackTarget, (double)1.0F);
         }

         this.entityHost.func_70671_ap().func_75651_a(this.attackTarget, 30.0F, 30.0F);
      }

      this.rangedAttackTime = Math.max(this.rangedAttackTime - 1, 0);
      if (this.rangedAttackTime <= 0 && var1 <= (double)field_82642_h && (this.entityHost.func_70635_at().func_75522_a(this.attackTarget) || !this.entityHost.ai.directLOS && this.entityHost.ai.canFireIndirect)) {
         if (this.field_70846_g++ <= this.entityHost.stats.burstCount) {
            this.rangedAttackTime = this.entityHost.stats.fireRate;
         } else {
            this.field_70846_g = 0;
            this.hasFired = true;
            this.rangedAttackTime = this.entityHost.stats.fireDelay + MathHelper.func_76141_d(this.entityHost.func_70681_au().nextFloat() * (float)this.entityHost.stats.delayVariance);
         }

         if (this.field_70846_g > 1) {
            this.rangedAttackEntityHost.func_82196_d(this.attackTarget, !this.entityHost.func_70635_at().func_75522_a(this.attackTarget) ? 1.0F : 0.0F);
            if (this.entityHost.currentAnimation != EnumAnimation.Aiming) {
               this.entityHost.func_71038_i();
            }
         }
      }

   }

   public boolean hasFired() {
      return this.hasFired;
   }

   public void navOverride(boolean nav) {
      this.navOverride = nav;
   }
}
