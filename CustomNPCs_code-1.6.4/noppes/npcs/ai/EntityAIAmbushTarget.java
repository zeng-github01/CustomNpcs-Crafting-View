package noppes.npcs.ai;

import java.util.Random;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityAIAmbushTarget extends EntityAIBase {
   private EntityCreature theEntity;
   private EntityLivingBase targetEntity;
   private double shelterX;
   private double shelterY;
   private double shelterZ;
   private double movementSpeed;
   private double distance;
   private int delay = 0;
   private World theWorld;
   private int tick;
   private boolean attackFromBehind;

   public EntityAIAmbushTarget(EntityCreature par1EntityCreature, double par2, double par3, boolean par4) {
      this.theEntity = par1EntityCreature;
      this.movementSpeed = par2;
      this.theWorld = par1EntityCreature.field_70170_p;
      this.distance = par3 * par3;
      this.attackFromBehind = par4;
      this.func_75248_a(3);
   }

   public boolean func_75250_a() {
      this.targetEntity = this.theEntity.func_70638_az();
      if (this.targetEntity == null) {
         return false;
      } else if (this.delay > 0) {
         --this.delay;
         return false;
      } else if (this.targetEntity.func_70068_e(this.theEntity) > this.distance && this.targetEntity.func_70685_l(this.theEntity)) {
         Vec3 vec3 = this.findHidingSpot();
         if (vec3 == null) {
            return false;
         } else {
            this.shelterX = vec3.field_72450_a;
            this.shelterY = vec3.field_72448_b;
            this.shelterZ = vec3.field_72449_c;
            return true;
         }
      } else {
         return false;
      }
   }

   public boolean func_75253_b() {
      if (this.targetEntity.func_70089_S()) {
         if (this.attackFromBehind) {
            this.theEntity.func_70661_as().func_75500_f();
         } else {
            boolean var10000 = true;
         }

         if (this.attackFromBehind) {
            if (this.isLookingAway() && !this.targetEntity.func_70685_l(this.theEntity)) {
               boolean var4 = true;
            } else {
               boolean var3 = false;
            }
         } else {
            boolean var5 = false;
         }

         return this.targetEntity.func_70068_e(this.theEntity) > this.distance && (!this.theEntity.func_70661_as().func_75500_f() || !this.targetEntity.func_70685_l(this.theEntity));
      } else {
         return false;
      }
   }

   public void func_75249_e() {
      this.theEntity.func_70661_as().func_75492_a(this.shelterX, this.shelterY, this.shelterZ, this.movementSpeed);
   }

   public void func_75251_c() {
      this.theEntity.func_70661_as().func_75499_g();
      if (this.theEntity.func_70638_az() == null && this.targetEntity != null) {
         this.theEntity.func_70624_b(this.targetEntity);
      }

   }

   private Vec3 findHidingSpot() {
      Random random = this.theEntity.func_70681_au();
      Vec3 idealPos = null;

      for(int i = 1; i <= 8; ++i) {
         for(int y = -2; y <= 2; ++y) {
            for(int x = -i; x <= i; ++x) {
               for(int z = -i; z <= i; ++z) {
                  double j = (double)MathHelper.func_76128_c(this.theEntity.field_70165_t + (double)x) + (double)0.5F;
                  double k = (double)MathHelper.func_76128_c(this.theEntity.field_70121_D.field_72338_b + (double)y) + (double)0.5F;
                  double l = (double)MathHelper.func_76128_c(this.theEntity.field_70161_v + (double)z) + (double)0.5F;
                  if (!this.theWorld.func_72799_c((int)j, (int)k, (int)l) && this.theWorld.func_72799_c((int)j, (int)k + 1, (int)l)) {
                     Vec3 vec1 = this.theWorld.func_82732_R().func_72345_a(this.targetEntity.field_70165_t, this.targetEntity.field_70163_u + (double)this.targetEntity.func_70047_e(), this.targetEntity.field_70161_v);
                     Vec3 vec2 = this.theWorld.func_82732_R().func_72345_a(j, k + (double)this.theEntity.func_70047_e(), l);
                     MovingObjectPosition movingobjectposition = this.theWorld.func_72933_a(vec1, vec2);
                     if (movingobjectposition != null && this.shelterX != j && this.shelterY != k && this.shelterZ != l) {
                        idealPos = this.theWorld.func_82732_R().func_72345_a(j, k, l);
                     }
                  }
               }
            }
         }

         if (idealPos != null) {
            return idealPos;
         }
      }

      this.delay = 60;
      return null;
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
