package noppes.npcs.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class EntityAIZigZagTarget extends EntityAIBase {
   private EntityCreature theEntity;
   private EntityLivingBase targetEntity;
   private double movePosX;
   private double movePosY;
   private double movePosZ;
   private int entityPosX;
   private int entityPosY;
   private int entityPosZ;
   private double field_75425_f;
   private float field_75426_g;

   public EntityAIZigZagTarget(EntityCreature par1EntityCreature, double par2, float par4) {
      this.theEntity = par1EntityCreature;
      this.field_75425_f = par2;
      this.field_75426_g = par4;
      this.func_75248_a(3);
   }

   public boolean func_75250_a() {
      this.targetEntity = this.theEntity.func_70638_az();
      if (this.targetEntity == null) {
         return false;
      } else if (this.targetEntity.func_70068_e(this.theEntity) < (double)(this.field_75426_g * this.field_75426_g)) {
         return false;
      } else {
         PathEntity pathentity = this.theEntity.func_70661_as().func_75494_a(this.targetEntity);
         if (pathentity != null && (float)pathentity.func_75874_d() >= this.field_75426_g) {
            PathPoint pathpoint = pathentity.func_75877_a(MathHelper.func_76128_c((double)this.field_75426_g / (double)2.0F));
            this.entityPosX = pathpoint.field_75839_a;
            this.entityPosY = pathpoint.field_75837_b;
            this.entityPosZ = pathpoint.field_75838_c;
            Vec3 vec3 = RandomPositionGenerator.func_75464_a(this.theEntity, (int)this.field_75426_g, 3, this.theEntity.field_70170_p.func_82732_R().func_72345_a((double)this.entityPosX, (double)this.entityPosY, (double)this.entityPosZ));
            if (vec3 != null && this.targetEntity.func_70092_e(vec3.field_72450_a, vec3.field_72448_b, vec3.field_72449_c) < this.targetEntity.func_70092_e((double)this.entityPosX, (double)this.entityPosY, (double)this.entityPosZ)) {
               this.movePosX = vec3.field_72450_a;
               this.movePosY = vec3.field_72448_b;
               this.movePosZ = vec3.field_72449_c;
               return true;
            }
         }

         return false;
      }
   }

   public boolean func_75253_b() {
      return !this.theEntity.func_70661_as().func_75500_f() && this.targetEntity.func_70089_S() && this.targetEntity.func_70068_e(this.theEntity) > (double)(this.field_75426_g * this.field_75426_g) && this.theEntity.func_70635_at().func_75522_a(this.targetEntity);
   }

   public void func_75251_c() {
      this.targetEntity = null;
   }

   public void func_75249_e() {
      this.theEntity.func_70661_as().func_75492_a(this.movePosX, this.movePosY, this.movePosZ, this.field_75425_f);
   }

   public void func_75246_d() {
      this.theEntity.func_70671_ap().func_75651_a(this.targetEntity, 30.0F, 30.0F);
   }
}
