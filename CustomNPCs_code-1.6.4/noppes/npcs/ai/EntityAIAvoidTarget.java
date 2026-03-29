package noppes.npcs.ai;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.Vec3;
import noppes.npcs.EntityNPCInterface;

public class EntityAIAvoidTarget extends EntityAIBase {
   private EntityNPCInterface theEntity;
   private Entity closestLivingEntity;
   private float distanceFromEntity;
   private PathEntity entityPathEntity;
   private PathNavigate entityPathNavigate;
   private Class targetEntityClass;

   public EntityAIAvoidTarget(EntityNPCInterface par1EntityNPC) {
      this.theEntity = par1EntityNPC;
      this.distanceFromEntity = (float)this.theEntity.stats.aggroRange;
      this.entityPathNavigate = par1EntityNPC.func_70661_as();
      this.func_75248_a(1);
   }

   public boolean func_75250_a() {
      EntityLivingBase target = this.theEntity.func_70638_az();
      if (target == null) {
         return false;
      } else {
         this.targetEntityClass = target.getClass();
         if (this.targetEntityClass == EntityPlayer.class) {
            this.closestLivingEntity = this.theEntity.field_70170_p.func_72890_a(this.theEntity, (double)this.distanceFromEntity);
            if (this.closestLivingEntity == null) {
               return false;
            }
         } else {
            List var1 = this.theEntity.field_70170_p.func_72872_a(this.targetEntityClass, this.theEntity.field_70121_D.func_72314_b((double)this.distanceFromEntity, (double)3.0F, (double)this.distanceFromEntity));
            if (var1.isEmpty()) {
               return false;
            }

            this.closestLivingEntity = (Entity)var1.get(0);
         }

         if (!this.theEntity.func_70635_at().func_75522_a(this.closestLivingEntity)) {
            return false;
         } else {
            Vec3 var2 = RandomPositionGenerator.func_75461_b(this.theEntity, 16, 7, this.theEntity.field_70170_p.func_82732_R().func_72345_a(this.closestLivingEntity.field_70165_t, this.closestLivingEntity.field_70163_u, this.closestLivingEntity.field_70161_v));
            if (var2 == null) {
               return false;
            } else if (this.closestLivingEntity.func_70092_e(var2.field_72450_a, var2.field_72448_b, var2.field_72449_c) < this.closestLivingEntity.func_70068_e(this.theEntity)) {
               return false;
            } else {
               this.entityPathEntity = this.entityPathNavigate.func_75488_a(var2.field_72450_a, var2.field_72448_b, var2.field_72449_c);
               return this.entityPathEntity == null ? false : this.entityPathEntity.func_75880_b(var2);
            }
         }
      }
   }

   public boolean func_75253_b() {
      return !this.entityPathNavigate.func_75500_f();
   }

   public void func_75249_e() {
      this.entityPathNavigate.func_75484_a(this.entityPathEntity, (double)1.0F);
   }

   public void func_75251_c() {
      this.closestLivingEntity = null;
      this.theEntity.func_70624_b((EntityLivingBase)null);
   }

   public void func_75246_d() {
      if (this.theEntity.func_70068_e(this.closestLivingEntity) < (double)49.0F) {
         this.theEntity.func_70661_as().func_75489_a(1.2);
      } else {
         this.theEntity.func_70661_as().func_75489_a((double)1.0F);
      }

   }
}
