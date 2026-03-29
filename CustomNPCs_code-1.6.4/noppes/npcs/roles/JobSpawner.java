package noppes.npcs.roles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.EntityNPCInterface;
import org.apache.commons.lang3.RandomStringUtils;

public class JobSpawner extends JobInterface {
   public NBTTagCompound compound6;
   public NBTTagCompound compound5;
   public NBTTagCompound compound4;
   public NBTTagCompound compound3;
   public NBTTagCompound compound2;
   public NBTTagCompound compound1;
   private int number = 0;
   private List spawned = new ArrayList();
   private String id = RandomStringUtils.random(8, true, true);
   public boolean doesntDie = false;
   public int spawnType = 0;
   public int xOffset = 0;
   public int yOffset = 0;
   public int zOffset = 0;
   private EntityLivingBase target;

   public JobSpawner(EntityNPCInterface npc) {
      super(npc);
   }

   public void writeEntityToNBT(NBTTagCompound compound) {
      this.saveCompound(this.compound1, "SpawnerNBT1", compound);
      this.saveCompound(this.compound2, "SpawnerNBT2", compound);
      this.saveCompound(this.compound3, "SpawnerNBT3", compound);
      this.saveCompound(this.compound4, "SpawnerNBT4", compound);
      this.saveCompound(this.compound5, "SpawnerNBT5", compound);
      this.saveCompound(this.compound6, "SpawnerNBT6", compound);
      compound.func_74778_a("SpawnerId", this.id);
      compound.func_74757_a("SpawnerDoesntDie", this.doesntDie);
      compound.func_74768_a("SpawnerType", this.spawnType);
      compound.func_74768_a("SpawnerXOffset", this.xOffset);
      compound.func_74768_a("SpawnerYOffset", this.yOffset);
      compound.func_74768_a("SpawnerZOffset", this.zOffset);
   }

   private void saveCompound(NBTTagCompound save, String name, NBTTagCompound compound) {
      if (save != null) {
         compound.func_74766_a(name, save);
      }

   }

   public void readEntityFromNBT(NBTTagCompound compound) {
      this.compound1 = compound.func_74775_l("SpawnerNBT1");
      this.compound2 = compound.func_74775_l("SpawnerNBT2");
      this.compound3 = compound.func_74775_l("SpawnerNBT3");
      this.compound4 = compound.func_74775_l("SpawnerNBT4");
      this.compound5 = compound.func_74775_l("SpawnerNBT5");
      this.compound6 = compound.func_74775_l("SpawnerNBT6");
      this.id = compound.func_74779_i("SpawnerId");
      this.doesntDie = compound.func_74767_n("SpawnerDoesntDie");
      this.spawnType = compound.func_74762_e("SpawnerType");
      this.xOffset = compound.func_74762_e("SpawnerXOffset");
      this.yOffset = compound.func_74762_e("SpawnerYOffset");
      this.zOffset = compound.func_74762_e("SpawnerZOffset");
   }

   public void setJobCompound(int i, NBTTagCompound compound) {
      if (i == 1) {
         this.compound1 = compound;
      }

      if (i == 2) {
         this.compound2 = compound;
      }

      if (i == 3) {
         this.compound3 = compound;
      }

      if (i == 4) {
         this.compound4 = compound;
      }

      if (i == 5) {
         this.compound5 = compound;
      }

      if (i == 6) {
         this.compound6 = compound;
      }

   }

   public void aiUpdateTask() {
      if (!this.spawned.isEmpty()) {
         Iterator<EntityLivingBase> iterator = this.spawned.iterator();

         while(iterator.hasNext()) {
            EntityLivingBase spawn = (EntityLivingBase)iterator.next();
            if (!(this.npc.func_70032_d(spawn) > 60.0F) && !spawn.field_70128_L && !(spawn.func_110143_aJ() <= 0.0F)) {
               if (spawn instanceof EntityLiving) {
                  ((EntityLiving)spawn).func_70624_b(this.target);
               }
            } else {
               spawn.field_70128_L = true;
               iterator.remove();
            }
         }

         if (this.spawnType == 0 && this.spawned.isEmpty() && !this.spawnEntity(this.number + 1) && !this.doesntDie) {
            this.npc.func_70106_y();
         }

         if (this.spawnType == 1 && this.spawned.isEmpty()) {
            if (this.number >= 6 && !this.doesntDie) {
               this.npc.func_70106_y();
            } else {
               this.spawnEntity(this.compound1);
               this.spawnEntity(this.compound2);
               this.spawnEntity(this.compound3);
               this.spawnEntity(this.compound4);
               this.spawnEntity(this.compound5);
               this.spawnEntity(this.compound6);
               this.number = 6;
            }
         }

         if (this.spawnType == 2 && this.spawned.isEmpty()) {
            ArrayList<NBTTagCompound> list = new ArrayList();
            if (this.compound1 != null && this.compound1.func_74764_b("id")) {
               list.add(this.compound1);
            }

            if (this.compound2 != null && this.compound2.func_74764_b("id")) {
               list.add(this.compound2);
            }

            if (this.compound3 != null && this.compound3.func_74764_b("id")) {
               list.add(this.compound3);
            }

            if (this.compound4 != null && this.compound4.func_74764_b("id")) {
               list.add(this.compound4);
            }

            if (this.compound5 != null && this.compound5.func_74764_b("id")) {
               list.add(this.compound5);
            }

            if (this.compound6 != null && this.compound6.func_74764_b("id")) {
               list.add(this.compound6);
            }

            if (!list.isEmpty()) {
               NBTTagCompound compound = (NBTTagCompound)list.get(this.npc.func_70681_au().nextInt(list.size()));
               this.spawnEntity(compound);
            }
         }

      }
   }

   private EntityLivingBase getTarget() {
      EntityLivingBase target = this.npc.func_70638_az();
      if (target == null || target.field_70128_L || target.func_110143_aJ() <= 0.0F) {
         target = this.npc.func_70643_av();
      }

      if (target != null && !target.field_70128_L && target.func_110143_aJ() > 0.0F) {
         return target;
      } else {
         for(EntityLivingBase entity : this.spawned) {
            if (entity instanceof EntityLiving) {
               target = ((EntityLiving)entity).func_70638_az();
               if (target != null && !target.field_70128_L && target.func_110143_aJ() > 0.0F) {
                  return target;
               }
            }

            target = entity.func_70643_av();
            if (target != null && !target.field_70128_L && target.func_110143_aJ() > 0.0F) {
               return target;
            }
         }

         return null;
      }
   }

   private boolean isEmpty() {
      if (this.compound1 != null && this.compound1.func_74764_b("id")) {
         return false;
      } else if (this.compound2 != null && this.compound2.func_74764_b("id")) {
         return false;
      } else if (this.compound3 != null && this.compound3.func_74764_b("id")) {
         return false;
      } else if (this.compound4 != null && this.compound4.func_74764_b("id")) {
         return false;
      } else if (this.compound5 != null && this.compound5.func_74764_b("id")) {
         return false;
      } else {
         return this.compound6 == null || !this.compound6.func_74764_b("id");
      }
   }

   private void setTarget(EntityLivingBase base, EntityLivingBase target) {
      if (!EntityList.func_75621_b(base).equals("Pixelmon") || !(target instanceof EntityPlayer)) {
         if (base instanceof EntityLiving) {
            ((EntityLiving)base).func_70624_b(target);
         } else {
            base.func_70604_c(target);
         }
      }

   }

   private void addEntityToList(ArrayList team, int i) {
      NBTTagCompound compound = this.getCompound(i);
      if (compound == null) {
         this.npc.func_70106_y();
      } else {
         Entity entity = EntityList.func_75615_a(compound, this.npc.field_70170_p);
         if (entity != null && EntityList.func_75621_b(entity).equals("Pixelmon")) {
            team.add(entity);
         }

      }
   }

   public boolean aiShouldExecute() {
      if (!this.isEmpty() && !this.npc.isKilled()) {
         this.target = this.getTarget();
         if (this.npc.func_70681_au().nextInt(30) == 1) {
            if (this.spawned.isEmpty()) {
               this.spawned = this.getNearbySpawned();
            }

            if (this.target == null) {
               this.reset();
            }
         }

         return this.target != null;
      } else {
         return false;
      }
   }

   public boolean aiContinueExecute() {
      return this.aiShouldExecute();
   }

   public void resetTask() {
      this.reset();
   }

   public void aiStartExecuting() {
      if (this.spawned.isEmpty()) {
         this.spawned = this.getNearbySpawned();
         if (this.spawned.isEmpty() && !this.spawnEntity(1) && !this.doesntDie) {
            this.npc.func_70106_y();
         }
      }

      this.number = 0;

      for(EntityLivingBase entity : this.spawned) {
         int i = entity.getEntityData().func_74762_e("NpcSpawnerNr");
         if (i > this.number) {
            this.number = i;
         }

         this.setTarget(entity, this.npc.func_70638_az());
      }

   }

   public void reset() {
      this.number = 0;
      if (!this.spawned.isEmpty()) {
         for(EntityLivingBase entity : this.spawned) {
            entity.field_70128_L = true;
         }
      } else {
         for(Entity entity : this.npc.field_70170_p.func_72872_a(EntityLivingBase.class, this.npc.field_70121_D.func_72314_b((double)40.0F, (double)40.0F, (double)40.0F))) {
            if (entity.getEntityData().func_74779_i("NpcSpawnerId").equals(this.id)) {
               entity.field_70128_L = true;
            }
         }
      }

      this.spawned.clear();
   }

   public void killed() {
      this.reset();
   }

   private boolean spawnEntity(int i) {
      NBTTagCompound compound = this.getCompound(i);
      if (compound == null) {
         return false;
      } else {
         this.spawnEntity(compound);
         return true;
      }
   }

   private void spawnEntity(NBTTagCompound compound) {
      if (compound != null && compound.func_74764_b("id")) {
         EntityLivingBase entity = (EntityLivingBase)EntityList.func_75615_a(compound, this.npc.field_70170_p);
         if (entity != null) {
            entity.getEntityData().func_74778_a("NpcSpawnerId", this.id);
            entity.getEntityData().func_74768_a("NpcSpawnerNr", this.number);
            this.setTarget(entity, this.npc.func_70638_az());
            entity.func_70107_b(this.npc.field_70165_t + (double)this.xOffset, this.npc.field_70163_u + (double)this.yOffset, this.npc.field_70161_v + (double)this.zOffset);
            if (entity instanceof EntityNPCInterface) {
               EntityNPCInterface snpc = (EntityNPCInterface)entity;
               snpc.stats.spawnCycle = 3;
               snpc.ai.returnToStart = false;
            }

            this.npc.field_70170_p.func_72838_d(entity);
            this.spawned.add(entity);
         }
      }
   }

   private NBTTagCompound getCompound(int i) {
      if (i <= 1 && this.compound1 != null && this.compound1.func_74764_b("id")) {
         this.number = 1;
         return this.compound1;
      } else if (i <= 2 && this.compound2 != null && this.compound2.func_74764_b("id")) {
         this.number = 2;
         return this.compound2;
      } else if (i <= 3 && this.compound3 != null && this.compound3.func_74764_b("id")) {
         this.number = 3;
         return this.compound3;
      } else if (i <= 4 && this.compound4 != null && this.compound4.func_74764_b("id")) {
         this.number = 4;
         return this.compound4;
      } else if (i <= 5 && this.compound5 != null && this.compound5.func_74764_b("id")) {
         this.number = 5;
         return this.compound5;
      } else if (i <= 6 && this.compound6 != null && this.compound6.func_74764_b("id")) {
         this.number = 6;
         return this.compound6;
      } else {
         return null;
      }
   }

   private List getNearbySpawned() {
      List<EntityLivingBase> spawnList = new ArrayList();

      for(EntityLivingBase entity : this.npc.field_70170_p.func_72872_a(EntityLivingBase.class, this.npc.field_70121_D.func_72314_b((double)40.0F, (double)40.0F, (double)40.0F))) {
         if (entity.getEntityData().func_74779_i("NpcSpawnerId").equals(this.id) && !entity.field_70128_L) {
            spawnList.add(entity);
         }
      }

      return spawnList;
   }
}
