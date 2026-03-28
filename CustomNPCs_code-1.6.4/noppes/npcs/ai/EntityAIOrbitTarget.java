package noppes.npcs.ai;

import java.util.Random;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;
import noppes.npcs.EntityNPCInterface;

public class EntityAIOrbitTarget extends EntityAIBase {
   private EntityNPCInterface theEntity;
   private EntityLivingBase targetEntity;
   private double movePosX;
   private double movePosY;
   private double movePosZ;
   private double speed;
   private float distance;
   private int delay = 0;
   private float angle = 0.0F;
   private int direction = 1;
   private float targetDistance;
   private boolean decay;
   private boolean canNavigate = true;
   private float decayRate = 1.0F;
   private int tick = 0;

   public EntityAIOrbitTarget(EntityNPCInterface par1EntityCreature, double par2, float par4, boolean par5) {
      this.theEntity = par1EntityCreature;
      this.speed = par2;
      this.distance = par4;
      this.decay = par5;
      this.func_75248_a(3);
   }

   public boolean func_75250_a() {
      if (--this.delay > 0) {
         return false;
      } else {
         this.targetEntity = this.theEntity.func_70638_az();
         if (this.targetEntity == null) {
            return false;
         } else {
            double d0 = (double)this.theEntity.func_70032_d(this.targetEntity);
            return d0 >= (double)(this.distance / 2.0F) && (this.theEntity.inventory.getProjectile() != null || d0 <= (double)this.distance);
         }
      }
   }

   public boolean func_75253_b() {
      double d0 = (double)this.targetEntity.func_70032_d(this.theEntity);
      return this.targetEntity.func_70089_S() && d0 >= (double)(this.distance / 2.0F) && d0 <= (double)(this.distance * 1.5F) && !this.theEntity.func_70090_H() && this.canNavigate;
   }

   public void func_75251_c() {
      this.theEntity.func_70661_as().func_75499_g();
      this.delay = 60;
      if (this.theEntity.inventory.getProjectile() != null) {
         this.theEntity.getRangedTask().navOverride(false);
      }

   }

   public void func_75249_e() {
      this.canNavigate = true;
      Random random = this.theEntity.func_70681_au();
      this.direction = random.nextInt(10) > 5 ? 1 : -1;
      this.decayRate = random.nextFloat() + this.distance / 16.0F;
      this.targetDistance = this.theEntity.func_70032_d(this.targetEntity);
      double d0 = this.theEntity.field_70165_t - this.targetEntity.field_70165_t;
      double d1 = this.theEntity.field_70161_v - this.targetEntity.field_70161_v;
      this.angle = (float)(Math.atan2(d1, d0) * (double)180.0F / Math.PI);
      if (this.theEntity.inventory.getProjectile() != null) {
         this.theEntity.getRangedTask().navOverride(true);
      }

   }

   public void func_75246_d() {
      this.theEntity.func_70671_ap().func_75651_a(this.targetEntity, 30.0F, 30.0F);
      if (this.theEntity.func_70661_as().func_75500_f() && this.tick >= 0 && this.theEntity.field_70122_E && !this.theEntity.func_70090_H()) {
         double d0 = (double)this.targetDistance * (double)MathHelper.func_76134_b(this.angle / 180.0F * (float)Math.PI);
         double d1 = (double)this.targetDistance * (double)MathHelper.func_76126_a(this.angle / 180.0F * (float)Math.PI);
         this.movePosX = this.targetEntity.field_70165_t + d0;
         this.movePosY = this.targetEntity.field_70121_D.field_72337_e;
         this.movePosZ = this.targetEntity.field_70161_v + d1;
         this.theEntity.func_70661_as().func_75492_a(this.movePosX, this.movePosY, this.movePosZ, this.speed);
         this.angle += 15.0F * (float)this.direction;
         this.tick = MathHelper.func_76143_f(this.theEntity.func_70011_f(this.movePosX, this.movePosY, this.movePosZ) / (double)(this.theEntity.getSpeed() / 20.0F));
         if (this.decay) {
            this.targetDistance -= this.decayRate;
         }
      }

      if (this.tick >= 0) {
         --this.tick;
      }

   }
}
