package noppes.npcs.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAIWatchClosest extends EntityAIBase {
   private EntityLiving theWatcher;
   protected Entity closestEntity;
   private float field_75333_c;
   private int lookTime;
   private float field_75331_e;
   private Class watchedClass;

   public EntityAIWatchClosest(EntityLiving par1EntityLiving, Class par2Class, float par3) {
      this.theWatcher = par1EntityLiving;
      this.watchedClass = par2Class;
      this.field_75333_c = par3;
      this.field_75331_e = 0.02F;
      this.func_75248_a(2);
   }

   public EntityAIWatchClosest(EntityLiving par1EntityLiving, Class par2Class, float par3, float par4) {
      this.theWatcher = par1EntityLiving;
      this.watchedClass = par2Class;
      this.field_75333_c = par3;
      this.field_75331_e = par4;
      this.func_75248_a(2);
   }

   public boolean func_75250_a() {
      if (this.theWatcher.func_70681_au().nextFloat() >= this.field_75331_e) {
         return false;
      } else {
         if (this.theWatcher.func_70638_az() != null) {
            this.closestEntity = this.theWatcher.func_70638_az();
         }

         if (this.watchedClass == EntityPlayer.class) {
            this.closestEntity = this.theWatcher.field_70170_p.func_72890_a(this.theWatcher, (double)this.field_75333_c);
         } else {
            this.closestEntity = this.theWatcher.field_70170_p.func_72857_a(this.watchedClass, this.theWatcher.field_70121_D.func_72314_b((double)this.field_75333_c, (double)3.0F, (double)this.field_75333_c), this.theWatcher);
            if (this.closestEntity != null) {
               return this.theWatcher.func_70685_l(this.closestEntity);
            }
         }

         return this.closestEntity != null;
      }
   }

   public boolean func_75253_b() {
      return !this.closestEntity.func_70089_S() ? false : (this.theWatcher.func_70068_e(this.closestEntity) > (double)(this.field_75333_c * this.field_75333_c) ? false : this.lookTime > 0);
   }

   public void func_75249_e() {
      this.lookTime = 40 + this.theWatcher.func_70681_au().nextInt(40);
   }

   public void func_75251_c() {
      this.closestEntity = null;
   }

   public void func_75246_d() {
      this.theWatcher.func_70671_ap().func_75650_a(this.closestEntity.field_70165_t, this.closestEntity.field_70163_u + (double)this.closestEntity.func_70047_e(), this.closestEntity.field_70161_v, 10.0F, (float)this.theWatcher.func_70646_bf());
      --this.lookTime;
   }
}
