package noppes.npcs.roles;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.EntityNPCInterface;

public class JobBoss extends JobInterface {
   public boolean hideName = false;
   public int resetTime = 300;
   private NBTTagCompound original;
   public NBTTagCompound compound9;
   public NBTTagCompound compound8;
   public NBTTagCompound compound7;
   public NBTTagCompound compound6;
   public NBTTagCompound compound5;
   public NBTTagCompound compound4;
   public NBTTagCompound compound3;
   public NBTTagCompound compound2;
   public NBTTagCompound compound1;
   private int type = 10;
   private long timeStart;

   public JobBoss(EntityNPCInterface npc) {
      super(npc);
   }

   public void writeEntityToNBT(NBTTagCompound compound) {
      compound.func_74757_a("BossHideName", this.hideName);
      this.saveCompound(this.original, "BossOriginal", compound);
      this.saveCompound(this.compound1, "BossNBT1", compound);
      this.saveCompound(this.compound2, "BossNBT2", compound);
      this.saveCompound(this.compound3, "BossNBT3", compound);
      this.saveCompound(this.compound4, "BossNBT4", compound);
      this.saveCompound(this.compound5, "BossNBT5", compound);
      this.saveCompound(this.compound6, "BossNBT6", compound);
      this.saveCompound(this.compound7, "BossNBT7", compound);
      this.saveCompound(this.compound8, "BossNBT8", compound);
      this.saveCompound(this.compound9, "BossNBT9", compound);
   }

   private void saveCompound(NBTTagCompound save, String name, NBTTagCompound compound) {
      if (save != null) {
         compound.func_74766_a(name, save);
      }

   }

   public void readEntityFromNBT(NBTTagCompound compound) {
      this.hideName = compound.func_74767_n("BossHideName");
      this.original = compound.func_74775_l("BossOriginal");
      this.compound1 = compound.func_74775_l("BossNBT1");
      this.compound2 = compound.func_74775_l("BossNBT2");
      this.compound3 = compound.func_74775_l("BossNBT3");
      this.compound4 = compound.func_74775_l("BossNBT4");
      this.compound5 = compound.func_74775_l("BossNBT5");
      this.compound6 = compound.func_74775_l("BossNBT6");
      this.compound7 = compound.func_74775_l("BossNBT7");
      this.compound8 = compound.func_74775_l("BossNBT8");
      this.compound9 = compound.func_74775_l("BossNBT9");
   }

   public boolean aiShouldExecute() {
      return this.type != 10 && this.npc.func_70638_az() == null ? false : false;
   }

   public void aiStartExecuting() {
      this.timeStart = System.currentTimeMillis();
   }

   public void aiUpdateTask() {
      if (this.timeStart - System.currentTimeMillis() >= (long)(this.resetTime * 1000)) {
         this.npc.field_70128_L = true;
         this.type = 10;
         this.spawnEntity(this.original);
      }
   }

   public boolean applyDamage(float damage) {
      return false;
   }

   private NBTTagCompound getNBT(int i) {
      if (i == 9) {
         return this.compound9;
      } else if (i == 8) {
         return this.compound8;
      } else if (i == 7) {
         return this.compound7;
      } else if (i == 6) {
         return this.compound6;
      } else if (i == 5) {
         return this.compound5;
      } else if (i == 4) {
         return this.compound4;
      } else if (i == 3) {
         return this.compound3;
      } else if (i == 2) {
         return this.compound2;
      } else {
         return i == 1 ? this.compound1 : null;
      }
   }

   public void setNBT(int i, NBTTagCompound compound) {
      if (i == 9) {
         this.compound9 = compound;
      }

      if (i == 8) {
         this.compound8 = compound;
      }

      if (i == 7) {
         this.compound7 = compound;
      }

      if (i == 6) {
         this.compound6 = compound;
      }

      if (i == 5) {
         this.compound5 = compound;
      }

      if (i == 4) {
         this.compound4 = compound;
      }

      if (i == 3) {
         this.compound3 = compound;
      }

      if (i == 2) {
         this.compound2 = compound;
      }

      if (i == 1) {
         this.compound1 = compound;
      }

   }

   public void reset() {
      if (this.type != 10) {
         this.type = 10;
         this.spawnEntity(this.original);
      }
   }

   private boolean spawnEntity(NBTTagCompound compound) {
      Entity entity = EntityList.func_75615_a(compound, this.npc.field_70170_p);
      return entity != null && entity instanceof EntityNPCInterface;
   }
}
