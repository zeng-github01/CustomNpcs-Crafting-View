package noppes.npcs.ai;

import java.util.Collections;
import java.util.List;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTargetSorter;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.MathHelper;

public class EntityAIClosestTarget extends EntityAITarget {
   private final Class targetClass;
   private final int targetChance;
   private final EntityAINearestAttackableTargetSorter theNearestAttackableTargetSorter;
   private final IEntitySelector field_82643_g;
   private EntityLivingBase targetEntity;

   public EntityAIClosestTarget(EntityCreature par1EntityCreature, Class par2Class, int par3, boolean par4) {
      this(par1EntityCreature, par2Class, par3, par4, false);
   }

   public EntityAIClosestTarget(EntityCreature par1EntityCreature, Class par2Class, int par3, boolean par4, boolean par5) {
      this(par1EntityCreature, par2Class, par3, par4, par5, (IEntitySelector)null);
   }

   public EntityAIClosestTarget(EntityCreature par1EntityCreature, Class par2Class, int par3, boolean par4, boolean par5, IEntitySelector par6IEntitySelector) {
      super(par1EntityCreature, par4, par5);
      this.targetClass = par2Class;
      this.targetChance = par3;
      this.theNearestAttackableTargetSorter = new EntityAINearestAttackableTargetSorter(par1EntityCreature);
      this.func_75248_a(1);
      this.field_82643_g = par6IEntitySelector;
   }

   public boolean func_75250_a() {
      if (this.targetChance > 0 && this.field_75299_d.func_70681_au().nextInt(this.targetChance) != 0) {
         return false;
      } else {
         double d0 = this.func_111175_f();
         List list = this.field_75299_d.field_70170_p.func_82733_a(this.targetClass, this.field_75299_d.field_70121_D.func_72314_b(d0, (double)MathHelper.func_76143_f(d0 / (double)2.0F), d0), this.field_82643_g);
         Collections.sort(list, this.theNearestAttackableTargetSorter);
         if (list.isEmpty()) {
            return false;
         } else {
            this.targetEntity = (EntityLivingBase)list.get(0);
            return true;
         }
      }
   }

   public void func_75249_e() {
      this.field_75299_d.func_70624_b(this.targetEntity);
      if (this.targetEntity instanceof EntityMob && ((EntityMob)this.targetEntity).func_70638_az() == null) {
         ((EntityMob)this.targetEntity).func_70624_b(this.field_75299_d);
         ((EntityMob)this.targetEntity).func_70784_b(this.field_75299_d);
      }

      super.func_75249_e();
   }
}
