package noppes.npcs.roles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.EntityNPCInterface;

public class JobGuard extends JobInterface {
   public boolean attacksAnimals = false;
   public boolean attackHostileMobs = true;
   public boolean attackCreepers = false;
   public boolean attackAll = false;

   public JobGuard(EntityNPCInterface npc) {
      super(npc);
   }

   public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.func_74757_a("GuardAttackAnimals", this.attacksAnimals);
      nbttagcompound.func_74757_a("GuardAttackMobs", this.attackHostileMobs);
      nbttagcompound.func_74757_a("GuardAttackCreepers", this.attackCreepers);
      nbttagcompound.func_74757_a("GuardAttackAll", this.attackAll);
   }

   public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
      this.attacksAnimals = nbttagcompound.func_74767_n("GuardAttackAnimals");
      this.attackHostileMobs = nbttagcompound.func_74767_n("GuardAttackMobs");
      this.attackCreepers = nbttagcompound.func_74767_n("GuardAttackCreepers");
      this.attackAll = nbttagcompound.func_74767_n("GuardAttackAll");
   }

   public boolean isEntityApplicable(EntityLivingBase entity) {
      if (!entity.field_70128_L && !(entity.func_110143_aJ() < 1.0F) && !(entity instanceof EntityPlayer)) {
         if (entity instanceof EntityNPCInterface) {
            return false;
         } else if (!(entity instanceof EntityAnimal)) {
            if (entity instanceof EntityCreeper) {
               return this.attackCreepers;
            } else if (!(entity instanceof IMob) && !(entity instanceof EntityDragon)) {
               return this.attackAll;
            } else {
               return this.attackHostileMobs;
            }
         } else {
            return this.attacksAnimals && (!(entity instanceof EntityTameable) || ((EntityTameable)entity).func_70905_p().isEmpty());
         }
      } else {
         return false;
      }
   }
}
