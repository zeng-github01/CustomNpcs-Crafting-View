package noppes.npcs.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import noppes.npcs.EntityNPCInterface;

public class EntityAIStalkTarget extends EntityAIBase {
   private EntityNPCInterface theEntity;
   private EntityLivingBase targetEntity;
   private Vec3 movePosition;
   private double distance;
   private boolean overRide;
   private World theWorld;
   private int delay;
   private int tick = 0;

   public EntityAIStalkTarget(EntityNPCInterface par1EntityCreature, double par2) {
      this.theEntity = par1EntityCreature;
      this.theWorld = par1EntityCreature.field_70170_p;
      this.distance = par2 * par2;
      this.overRide = false;
      this.delay = 0;
      this.func_75248_a(3);
   }

   public boolean func_75250_a() {
      this.targetEntity = this.theEntity.func_70638_az();
      if (this.targetEntity == null) {
         return false;
      } else if (this.tick > 0) {
         --this.tick;
         return false;
      } else {
         return this.targetEntity.func_70068_e(this.theEntity) > this.distance;
      }
   }

   public void func_75251_c() {
      this.theEntity.func_70661_as().func_75499_g();
      if (this.theEntity.func_70638_az() == null && this.targetEntity != null) {
         this.theEntity.func_70624_b(this.targetEntity);
      }

      if (this.theEntity.inventory.getProjectile() != null) {
         this.theEntity.getRangedTask().navOverride(false);
      }

   }

   public void func_75249_e() {
      if (this.theEntity.inventory.getProjectile() != null) {
         this.theEntity.getRangedTask().navOverride(true);
      }

   }

   public void func_75246_d() {
      this.theEntity.func_70671_ap().func_75651_a(this.targetEntity, 30.0F, 30.0F);
      if (this.theEntity.func_70661_as().func_75500_f() || this.overRide) {
         if (this.isLookingAway()) {
            this.movePosition = this.stalkTarget();
            if (this.movePosition != null) {
               this.theEntity.func_70661_as().func_75492_a(this.movePosition.field_72450_a, this.movePosition.field_72448_b, this.movePosition.field_72449_c, (double)1.0F);
               this.overRide = false;
            } else {
               this.tick = 100;
            }
         } else if (this.targetEntity.func_70685_l(this.theEntity)) {
            this.movePosition = this.hideFromTarget();
            if (this.movePosition != null) {
               this.theEntity.func_70661_as().func_75492_a(this.movePosition.field_72450_a, this.movePosition.field_72448_b, this.movePosition.field_72449_c, 1.33);
               this.overRide = false;
            } else {
               this.tick = 100;
            }
         }
      }

      if (this.delay > 0) {
         --this.delay;
      }

      if (!this.isLookingAway() && this.targetEntity.func_70685_l(this.theEntity) && this.delay == 0) {
         this.overRide = true;
         this.delay = 60;
      }

   }

   private Vec3 hideFromTarget() {
      for(int i = 1; i <= 8; ++i) {
         Vec3 vec = this.findSecludedXYZ(i, false);
         if (vec != null) {
            return vec;
         }
      }

      return null;
   }

   private Vec3 stalkTarget() {
      for(int i = 8; i >= 1; --i) {
         Vec3 vec = this.findSecludedXYZ(i, true);
         if (vec != null) {
            return vec;
         }
      }

      return null;
   }

   private Vec3 findSecludedXYZ(int radius, boolean nearest) {
      Vec3 idealPos = null;
      double dist = this.targetEntity.func_70068_e(this.theEntity);
      double u = (double)0.0F;
      double v = (double)0.0F;
      double w = (double)0.0F;
      if (this.movePosition != null) {
         u = this.movePosition.field_72450_a;
         v = this.movePosition.field_72448_b;
         w = this.movePosition.field_72449_c;
      }

      for(int y = -2; y <= 2; ++y) {
         for(int x = -radius; x <= radius; ++x) {
            for(int z = -radius; z <= radius; ++z) {
               double j = (double)MathHelper.func_76128_c(this.theEntity.field_70165_t + (double)x) + (double)0.5F;
               double k = (double)MathHelper.func_76128_c(this.theEntity.field_70121_D.field_72338_b + (double)y) + (double)0.5F;
               double l = (double)MathHelper.func_76128_c(this.theEntity.field_70161_v + (double)z) + (double)0.5F;
               if (!this.theWorld.func_72799_c((int)j, (int)k, (int)l) && this.theWorld.func_72799_c((int)j, (int)k + 1, (int)l) && this.theWorld.func_72799_c((int)j, (int)k + 2, (int)l)) {
                  Vec3 vec1 = this.theWorld.func_82732_R().func_72345_a(this.targetEntity.field_70165_t, this.targetEntity.field_70163_u + (double)this.targetEntity.func_70047_e(), this.targetEntity.field_70161_v);
                  Vec3 vec2 = this.theWorld.func_82732_R().func_72345_a(j, k + (double)this.theEntity.func_70047_e(), l);
                  MovingObjectPosition movingobjectposition = this.theWorld.func_72933_a(vec1, vec2);
                  if (movingobjectposition != null) {
                     boolean weight = nearest ? this.targetEntity.func_70092_e(j, k, l) <= dist : true;
                     if (weight && (j != u || k != v || l != w)) {
                        idealPos = this.theWorld.func_82732_R().func_72345_a(j, k, l);
                        if (nearest) {
                           dist = this.targetEntity.func_70092_e(j, k, l);
                        }
                     }
                  }
               }
            }
         }
      }

      return idealPos;
   }

   private boolean isLookingAway() {
      Vec3 vec3 = this.targetEntity.func_70676_i(1.0F).func_72432_b();
      Vec3 vec31 = this.theWorld.func_82732_R().func_72345_a(this.theEntity.field_70165_t - this.targetEntity.field_70165_t, this.theEntity.field_70121_D.field_72338_b + (double)(this.theEntity.field_70131_O / 2.0F) - (this.targetEntity.field_70163_u + (double)this.targetEntity.func_70047_e()), this.theEntity.field_70161_v - this.targetEntity.field_70161_v);
      double d0 = vec31.func_72433_c();
      vec31 = vec31.func_72432_b();
      double d1 = vec3.func_72430_b(vec31);
      return d1 < 0.6;
   }
}
