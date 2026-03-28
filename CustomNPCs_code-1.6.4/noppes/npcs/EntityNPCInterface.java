package noppes.npcs;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.block.Block;
import net.minecraft.block.StepSound;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAITaskEntry;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import noppes.npcs.ai.AttackSelectorNPC;
import noppes.npcs.ai.EntityAIAmbushTarget;
import noppes.npcs.ai.EntityAIAnimation;
import noppes.npcs.ai.EntityAIAttackTarget;
import noppes.npcs.ai.EntityAIAvoidTarget;
import noppes.npcs.ai.EntityAIBustDoor;
import noppes.npcs.ai.EntityAIClosestTarget;
import noppes.npcs.ai.EntityAIDodgeShoot;
import noppes.npcs.ai.EntityAIFindShade;
import noppes.npcs.ai.EntityAIJob;
import noppes.npcs.ai.EntityAILook;
import noppes.npcs.ai.EntityAIMoveIndoors;
import noppes.npcs.ai.EntityAIMovingPath;
import noppes.npcs.ai.EntityAIOccupyBed;
import noppes.npcs.ai.EntityAIOrbitTarget;
import noppes.npcs.ai.EntityAIPanic;
import noppes.npcs.ai.EntityAIRangedAttack;
import noppes.npcs.ai.EntityAIReturn;
import noppes.npcs.ai.EntityAIRole;
import noppes.npcs.ai.EntityAISprintToTarget;
import noppes.npcs.ai.EntityAIStalkTarget;
import noppes.npcs.ai.EntityAIWander;
import noppes.npcs.ai.EntityAIWatchClosest;
import noppes.npcs.ai.EntityAIWaterNav;
import noppes.npcs.ai.EntityAIWorldLines;
import noppes.npcs.ai.EntityAIZigZagTarget;
import noppes.npcs.constants.EnumAnimation;
import noppes.npcs.constants.EnumJobType;
import noppes.npcs.constants.EnumModelType;
import noppes.npcs.constants.EnumMovingType;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.constants.EnumPotionType;
import noppes.npcs.constants.EnumRoleType;
import noppes.npcs.constants.EnumStandingType;
import noppes.npcs.controllers.Dialog;
import noppes.npcs.controllers.DialogOption;
import noppes.npcs.controllers.Faction;
import noppes.npcs.controllers.FactionController;
import noppes.npcs.controllers.Line;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.PlayerQuestData;
import noppes.npcs.controllers.QuestData;
import noppes.npcs.entity.EntityProjectile;
import noppes.npcs.roles.JobBard;
import noppes.npcs.roles.JobBoss;
import noppes.npcs.roles.JobInterface;
import noppes.npcs.roles.RoleFollower;
import noppes.npcs.roles.RoleInterface;

public class EntityNPCInterface extends EntityCreature implements IEntityAdditionalSpawnData, ICommandSender, IRangedAttackMob, IBossDisplayData {
   public DataDisplay display;
   public DataStats stats;
   public DataAI ai;
   public DataAdvanced advanced;
   public DataInventory inventory;
   public int[] startPos;
   public float scaleX;
   public float scaleY;
   public float scaleZ;
   public float labelOffset;
   private boolean wasKilled = false;
   public RoleInterface roleInterface;
   public JobInterface jobInterface;
   public HashMap dialogs;
   public boolean hasDied = false;
   public long killedtime = 0L;
   private int taskCount = 1;
   private boolean isSleeping = false;
   private EntityAIRangedAttack aiRange;
   public Object textureLocation = null;
   public Object textureGlowLocation = null;
   public Object textureCloakLocation = null;
   public EnumAnimation currentAnimation;
   public int npcVersion;
   public double field_20066_r;
   public double field_20065_s;
   public double field_20064_t;
   public double field_20063_u;
   public double field_20062_v;
   public double field_20061_w;

   public EntityNPCInterface(World world) {
      super(world);
      this.currentAnimation = EnumAnimation.NONE;
      this.npcVersion = VersionCompatibility.ModRev;
      this.display = new DataDisplay(this);
      this.stats = new DataStats(this);
      this.ai = new DataAI(this);
      this.advanced = new DataAdvanced(this);
      this.inventory = new DataInventory(this);
      this.dialogs = new HashMap();
      this.advanced.interactLines.lines.put(0, new Line("Hello {player}"));
      this.field_70728_aV = 0;
      this.labelOffset = 0.0F;
      this.scaleX = this.scaleY = this.scaleZ = 0.9375F;
      this.updateHitbox();
      this.setFaction(this.getFaction().id);
      this.updateTasks();
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(13, String.valueOf(""));
      this.field_70180_af.func_75682_a(14, 0);
      this.field_70180_af.func_75682_a(15, 0);
      this.field_70180_af.func_75682_a(23, (byte)0);
      this.field_70180_af.func_75682_a(24, 0);
   }

   protected boolean func_70650_aV() {
      return true;
   }

   public boolean func_110167_bD() {
      return false;
   }

   public boolean func_70652_k(Entity par1Entity) {
      float f = (float)this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
      if (this.stats.attackSpeed < 10) {
         par1Entity.field_70172_ad = 0;
      }

      boolean var4 = par1Entity.func_70097_a(new NpcDamageSource("mob", this), f);
      if (var4 && this.stats.knockback > 0) {
         par1Entity.func_70024_g((double)(-MathHelper.func_76126_a(this.field_70177_z * (float)Math.PI / 180.0F) * (float)this.stats.knockback * 0.5F), 0.1, (double)(MathHelper.func_76134_b(this.field_70177_z * (float)Math.PI / 180.0F) * (float)this.stats.knockback * 0.5F));
         this.field_70159_w *= 0.6;
         this.field_70179_y *= 0.6;
      }

      if (this.stats.potionType != EnumPotionType.None) {
         if (this.stats.potionType != EnumPotionType.Fire) {
            ((EntityLivingBase)par1Entity).func_70690_d(new PotionEffect(this.getPotionEffect(this.stats.potionType), this.stats.potionDuration * 20, this.stats.potionAmp));
         } else {
            par1Entity.func_70015_d(this.stats.potionDuration);
         }
      }

      return var4;
   }

   public void func_70636_d() {
      if (!CustomNpcs.FreezeNPCs) {
         this.func_82168_bl();
         if (!this.field_70170_p.field_72995_K) {
            if (!this.isKilled() && !this.isAttacking() && this.field_70173_aa % 20 == 0) {
               if (this.stats.healthRegen && this.func_110143_aJ() < this.func_110138_aP()) {
                  float heal = this.func_110138_aP() / 20.0F;
                  this.func_70691_i(heal > 0.0F ? heal : 1.0F);
               }

               if (this.getFaction().getsAttacked) {
                  for(EntityMob mob : this.field_70170_p.func_72872_a(EntityMob.class, this.field_70121_D.func_72314_b((double)16.0F, (double)16.0F, (double)16.0F))) {
                     if (mob.func_70638_az() == null && this.func_70685_l(mob)) {
                        if (mob instanceof EntityZombie && !mob.getEntityData().func_74764_b("AttackNpcs")) {
                           mob.field_70714_bg.func_75776_a(2, new EntityAIAttackOnCollide(mob, EntityLivingBase.class, (double)1.0F, false));
                           mob.getEntityData().func_74757_a("AttackNpcs", true);
                        }

                        mob.func_70624_b(this);
                        break;
                     }
                  }
               }
            }

            if (this.func_110143_aJ() <= 0.0F) {
               this.func_70674_bp();
               this.field_70180_af.func_75692_b(24, 1);
            }

            this.field_70180_af.func_75692_b(23, (byte)(this.func_70638_az() != null ? 1 : 0));
            this.field_70180_af.func_75692_b(15, this.func_70661_as().func_75500_f() ? 0 : 1);
         }

         if (this.wasKilled != this.isKilled() && this.wasKilled) {
            this.reset();
         }

         this.wasKilled = this.isKilled();
         if (this.field_70170_p.func_72935_r() && !this.field_70170_p.field_72995_K && this.stats.burnInSun) {
            float f = this.func_70013_c(1.0F);
            if (f > 0.5F && this.field_70146_Z.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.field_70170_p.func_72937_j(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70163_u), MathHelper.func_76128_c(this.field_70161_v))) {
               this.func_70015_d(8);
            }
         }

         super.func_70636_d();
         if (this.field_70170_p.field_72995_K) {
            if (!this.display.cloakTexture.isEmpty()) {
               this.cloakUpdate();
            }

            if (this.currentAnimation.ordinal() != this.field_70180_af.func_75679_c(14)) {
               this.currentAnimation = EnumAnimation.values()[this.field_70180_af.func_75679_c(14)];
               this.updateHitbox();
            }

            if (this.advanced.job == EnumJobType.Bard) {
               ((JobBard)this.jobInterface).onLivingUpdate();
            }
         }

      }
   }

   public boolean func_70085_c(EntityPlayer player) {
      if (this.field_70170_p.field_72995_K) {
         return false;
      } else {
         ItemStack currentItem = player.field_71071_by.func_70448_g();
         if (currentItem != null) {
            if (currentItem.field_77993_c == CustomItems.cloner.field_77779_bT || currentItem.field_77993_c == CustomItems.wand.field_77779_bT) {
               this.func_70624_b((EntityLivingBase)null);
               this.func_70604_c((EntityLivingBase)null);
               return true;
            }

            if (currentItem.field_77993_c == CustomItems.moving.field_77779_bT) {
               this.func_70624_b((EntityLivingBase)null);
               if (currentItem.field_77990_d == null) {
                  currentItem.field_77990_d = new NBTTagCompound();
               }

               currentItem.field_77990_d.func_74768_a("NPCID", this.field_70157_k);
               player.func_70006_a(ChatMessageComponent.func_111066_d("Registered " + this.func_70023_ak() + " to your NPC Pather"));
               return true;
            }
         }

         if (!this.isAttacking() && !this.isKilled() && !this.getFaction().isAggressiveToPlayer(player)) {
            Dialog dialog = this.getDialog(player);
            PlayerQuestData playerdata = PlayerDataController.instance.getPlayerData(player).questData;
            QuestData data = playerdata.getQuestCompletion(player, this);
            if (data != null) {
               NoppesUtilServer.sendData(player, EnumPacketType.QuestCompletion, data.quest.writeToNBT(new NBTTagCompound()));
            } else if (dialog != null) {
               NoppesUtilServer.openDialog(player, this, dialog);
            } else if (this.roleInterface != null) {
               this.roleInterface.interact(player);
            } else {
               this.say(player, this.advanced.getInteractLine());
            }

            return true;
         } else {
            return false;
         }
      }
   }

   private Dialog getDialog(EntityPlayer player) {
      for(DialogOption option : this.dialogs.values()) {
         if (option != null && option.hasDialog()) {
            Dialog dialog = option.getDialog();
            if (dialog.availability.isAvailable(player)) {
               return dialog;
            }
         }
      }

      return null;
   }

   public boolean func_70097_a(DamageSource damagesource, float i) {
      if (!this.field_70170_p.field_72995_K && !this.isKilled() && !CustomNpcs.FreezeNPCs && !damagesource.field_76373_n.equals("inWall")) {
         i = this.stats.resistances.applyResistance(damagesource, i);
         if (this.advanced.job == EnumJobType.Boss && ((JobBoss)this.jobInterface).applyDamage(i)) {
            return false;
         } else {
            Entity entity = damagesource.func_76346_g();
            EntityLivingBase attackingEntity = null;
            if (entity instanceof EntityLivingBase) {
               attackingEntity = (EntityLivingBase)entity;
            }

            if (attackingEntity instanceof EntityPlayer && this.getFaction().isFriendlyToPlayer((EntityPlayer)attackingEntity)) {
               return false;
            } else {
               if (entity instanceof EntityArrow && ((EntityArrow)entity).field_70250_c != null) {
                  attackingEntity = (EntityLivingBase)((EntityArrow)entity).field_70250_c;
               }

               int faction = this.getFaction().id;
               if (attackingEntity instanceof EntityNPCInterface && ((EntityNPCInterface)attackingEntity).getFaction().id == faction) {
                  return false;
               } else if (attackingEntity == null) {
                  return super.func_70097_a(damagesource, i);
               } else if (this.isAttacking()) {
                  if (this.func_70638_az() != null && attackingEntity != null && this.func_70068_e(this.func_70638_az()) > this.func_70068_e(attackingEntity)) {
                     this.func_70624_b(attackingEntity);
                  }

                  return super.func_70097_a(damagesource, i);
               } else {
                  if (i > 0.0F) {
                     for(EntityNPCInterface npc : this.field_70170_p.func_72872_a(EntityNPCInterface.class, this.field_70121_D.func_72314_b((double)32.0F, (double)16.0F, (double)32.0F))) {
                        if (!npc.isKilled() && npc.advanced.defendFaction && npc.getFaction().id == faction && (npc.func_70685_l(this) || npc.func_70685_l(attackingEntity))) {
                           npc.onAttack(attackingEntity);
                        }
                     }

                     this.func_70624_b(attackingEntity);
                  }

                  return super.func_70097_a(damagesource, i);
               }
            }
         }
      } else {
         return false;
      }
   }

   public void onAttack(EntityLivingBase entity) {
      if (entity != null && entity != this && !this.isAttacking() && this.ai.onAttack != 3) {
         super.func_70624_b(entity);
      }
   }

   public void func_70624_b(EntityLivingBase entity) {
      if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).field_71075_bZ.field_75102_a) {
         if (entity != null && entity != this && this.ai.onAttack != 3 && !this.isAttacking() && !this.isRemote()) {
            this.saySurrounding(this.advanced.getAttackLine());
         }

         super.func_70624_b(entity);
      }
   }

   public void func_82196_d(EntityLivingBase entitylivingbase, float f) {
      ItemStack proj = this.inventory.getProjectile();
      if (proj == null) {
         this.updateTasks();
      } else {
         EntityProjectile projectile = new EntityProjectile(this.field_70170_p, this, proj.func_77946_l(), true);
         if (this.display.modelType == EnumModelType.Golem) {
            projectile.func_70012_b(projectile.field_70165_t, this.field_70163_u + (double)1.45F, projectile.field_70161_v, projectile.field_70177_z, projectile.field_70125_A);
         }

         double varX = entitylivingbase.field_70165_t - this.field_70165_t;
         double varY = entitylivingbase.field_70121_D.field_72338_b + (double)(entitylivingbase.field_70131_O / 2.0F) - (this.field_70163_u + (double)this.func_70047_e());
         double varZ = entitylivingbase.field_70161_v - this.field_70161_v;
         float varF = projectile.hasGravity() ? MathHelper.func_76133_a(varX * varX + varZ * varZ) : 0.0F;
         float angle = projectile.getAngleForXYZ(varX, varY, varZ, (double)varF, f == 1.0F && this.ai.canFireIndirect);
         float accuracy = 20.0F - (float)MathHelper.func_76141_d((float)this.stats.accuracy / 5.0F);
         projectile.func_70186_c(varX, varY, varZ, angle, accuracy);
         this.func_85030_a(this.stats.fireSound, 2.0F, 1.0F);
         this.field_70170_p.func_72838_d(projectile);
      }
   }

   private void clearTasks(EntityAITasks tasks) {
      for(EntityAITaskEntry entityaitaskentry : tasks.field_75782_a) {
         EntityAIBase entityaibase1 = entityaitaskentry.field_75733_a;
         if (tasks.field_75780_b.contains(entityaitaskentry)) {
            entityaibase1.func_75251_c();
         }
      }

      tasks.field_75782_a = new ArrayList();
      tasks.field_75780_b = new ArrayList();
   }

   public void updateTasks() {
      if (this.field_70170_p != null && !this.field_70170_p.field_72995_K) {
         this.clearTasks(this.field_70714_bg);
         this.clearTasks(this.field_70715_bh);
         IEntitySelector attackEntitySelector = new AttackSelectorNPC(this);
         this.field_70715_bh.func_75776_a(1, new EntityAIHurtByTarget(this, false));
         this.field_70715_bh.func_75776_a(2, new EntityAIClosestTarget(this, EntityLivingBase.class, 0, this.ai.directLOS, false, attackEntitySelector));
         this.field_70714_bg.func_75776_a(0, new EntityAIWaterNav(this));
         this.taskCount = 1;
         this.doorInteractType();
         this.seekShelter();
         this.setResponse();
         this.setCanSleep();
         this.setMoveType();
         this.addRegularEntries();
      }
   }

   public void setResponse() {
      this.aiRange = null;
      if (this.ai.onAttack == 1) {
         this.field_70714_bg.func_75776_a(this.taskCount++, new EntityAIPanic(this, 1.4F));
      } else if (this.ai.onAttack == 2) {
         this.field_70714_bg.func_75776_a(this.taskCount++, new EntityAIAvoidTarget(this));
         this.setCanSprint();
      } else if (this.ai.onAttack == 0) {
         this.setCanLeap();
         this.setCanSprint();
         if (this.inventory.getProjectile() != null && this.ai.useRangeMelee != 2) {
            switch (this.ai.tacticalVariant) {
               case Dodge:
                  this.field_70714_bg.func_75776_a(this.taskCount++, new EntityAIDodgeShoot(this));
                  break;
               case Surround:
                  this.field_70714_bg.func_75776_a(this.taskCount++, new EntityAIOrbitTarget(this, (double)1.0F, (float)this.stats.rangedRange, false));
                  break;
               case Ambush:
                  this.field_70714_bg.func_75776_a(this.taskCount++, new EntityAIAmbushTarget(this, 1.2, (double)this.ai.tacticalRadius, false));
                  break;
               case Stalk:
                  this.field_70714_bg.func_75776_a(this.taskCount++, new EntityAIStalkTarget(this, (double)this.ai.tacticalRadius));
            }
         } else {
            switch (this.ai.tacticalVariant) {
               case Dodge:
                  this.field_70714_bg.func_75776_a(this.taskCount++, new EntityAIZigZagTarget(this, (double)1.0F, (float)this.ai.tacticalRadius));
                  break;
               case Surround:
                  this.field_70714_bg.func_75776_a(this.taskCount++, new EntityAIOrbitTarget(this, (double)1.0F, (float)this.ai.tacticalRadius, true));
                  break;
               case Ambush:
                  this.field_70714_bg.func_75776_a(this.taskCount++, new EntityAIAmbushTarget(this, 1.2, (double)this.ai.tacticalRadius, false));
                  break;
               case Stalk:
                  this.field_70714_bg.func_75776_a(this.taskCount++, new EntityAIStalkTarget(this, (double)this.ai.tacticalRadius));
            }
         }

         this.field_70714_bg.func_75776_a(this.taskCount, new EntityAIAttackTarget(this, true));
         if (this.inventory.getProjectile() != null) {
            this.field_70714_bg.func_75776_a(this.taskCount++, this.aiRange = new EntityAIRangedAttack(this));
         }
      }

   }

   public void setMoveType() {
      if (this.ai.movingType == EnumMovingType.Wandering) {
         this.field_70714_bg.func_75776_a(this.taskCount++, new EntityAIWander(this));
      }

      if (this.ai.movingType == EnumMovingType.MovingPath) {
         this.field_70714_bg.func_75776_a(this.taskCount++, new EntityAIMovingPath(this));
      }

   }

   public void doorInteractType() {
      EntityAIBase aiDoor = null;
      if (this.ai.doorInteract == 1) {
         this.field_70714_bg.func_75776_a(this.taskCount++, aiDoor = new EntityAIOpenDoor(this, true));
      } else if (this.ai.doorInteract == 0) {
         this.field_70714_bg.func_75776_a(this.taskCount++, aiDoor = new EntityAIBustDoor(this));
      }

      this.func_70661_as().func_75498_b(aiDoor != null);
   }

   public void seekShelter() {
      if (this.ai.findShelter == 0) {
         this.field_70714_bg.func_75776_a(this.taskCount++, new EntityAIMoveIndoors(this));
      } else if (this.ai.findShelter == 1) {
         this.field_70714_bg.func_75776_a(this.taskCount++, new EntityAIRestrictSun(this));
         this.field_70714_bg.func_75776_a(this.taskCount++, new EntityAIFindShade(this));
      }

   }

   public void setCanSleep() {
      if (this.ai.canSleep) {
         this.field_70714_bg.func_75776_a(this.taskCount++, new EntityAIOccupyBed(this));
      }

   }

   public void setCanLeap() {
      if (this.ai.canLeap) {
         this.field_70714_bg.func_75776_a(this.taskCount++, new EntityAILeapAtTarget(this, 0.4F));
      }

   }

   public void setCanSprint() {
      if (this.ai.canSprint) {
         this.field_70714_bg.func_75776_a(this.taskCount++, new EntityAISprintToTarget(this));
      }

   }

   public void addRegularEntries() {
      this.field_70714_bg.func_75776_a(this.taskCount++, new EntityAIReturn(this));
      this.field_70714_bg.func_75776_a(this.taskCount++, new EntityAILook(this));
      if (this.ai.standingType != EnumStandingType.NoRotation && this.ai.standingType != EnumStandingType.HeadRotation) {
         this.field_70714_bg.func_75776_a(this.taskCount++, new EntityAIWatchClosest(this, EntityLiving.class, 5.0F));
      }

      this.field_70714_bg.func_75776_a(this.taskCount++, new EntityAIWorldLines(this));
      this.field_70714_bg.func_75776_a(this.taskCount++, new EntityAIJob(this));
      this.field_70714_bg.func_75776_a(this.taskCount++, new EntityAIRole(this));
      this.field_70714_bg.func_75776_a(this.taskCount++, new EntityAIAnimation(this));
   }

   public float getSpeed() {
      return (float)this.stats.moveSpeed / 20.0F;
   }

   public float func_70783_a(int par1, int par2, int par3) {
      return this.field_70170_p.func_72801_o(par1, par2, par3) - 0.5F;
   }

   private int getPotionEffect(EnumPotionType p) {
      switch (p) {
         case Poison:
            return Potion.field_76436_u.field_76415_H;
         case Hunger:
            return Potion.field_76438_s.field_76415_H;
         case Weakness:
            return Potion.field_76437_t.field_76415_H;
         case Slowness:
            return Potion.field_76421_d.field_76415_H;
         case Nausea:
            return Potion.field_76431_k.field_76415_H;
         case Blindness:
            return Potion.field_76440_q.field_76415_H;
         case Wither:
            return Potion.field_82731_v.field_76415_H;
         default:
            return 0;
      }
   }

   protected int func_70682_h(int par1) {
      return !this.stats.canDrown ? par1 : super.func_70682_h(par1);
   }

   public EnumCreatureAttribute func_70668_bt() {
      return this.stats.creatureType;
   }

   public String func_70023_ak() {
      return this.display.name;
   }

   protected String func_70639_aQ() {
      return this.func_70089_S() ? (this.func_70638_az() != null && !this.advanced.angrySound.equals("") ? this.advanced.angrySound : this.advanced.idleSound) : null;
   }

   protected String func_70621_aR() {
      return this.advanced.hurtSound;
   }

   protected String func_70673_aS() {
      return this.advanced.deathSound;
   }

   protected void func_70036_a(int par1, int par2, int par3, int par4) {
      if (!this.advanced.stepSound.equals("")) {
         this.func_85030_a(this.advanced.stepSound, 0.15F, 1.0F);
      } else {
         StepSound stepsound = Block.field_71973_m[par4].field_72020_cn;
         if (this.field_70170_p.func_72798_a(par1, par2 + 1, par3) == Block.field_72037_aS.field_71990_ca) {
            stepsound = Block.field_72037_aS.field_72020_cn;
            this.func_85030_a(stepsound.func_72675_d(), stepsound.func_72677_b() * 0.15F, stepsound.func_72678_c());
         } else if (!Block.field_71973_m[par4].field_72018_cp.func_76224_d()) {
            this.func_85030_a(stepsound.func_72675_d(), stepsound.func_72677_b() * 0.15F, stepsound.func_72678_c());
         }
      }

   }

   public void saySurrounding(Line line) {
      if (line != null) {
         for(EntityPlayer player : this.field_70170_p.func_72872_a(EntityPlayer.class, this.field_70121_D.func_72314_b((double)20.0F, (double)20.0F, (double)20.0F))) {
            this.say(player, line);
         }

      }
   }

   public void say(EntityPlayer player, Line line) {
      if (line != null && this.func_70685_l(player)) {
         if (!line.sound.isEmpty()) {
            NoppesUtilServer.sendData(player, EnumPacketType.PlaySound, line.sound, (float)this.field_70165_t, (float)this.field_70163_u, (float)this.field_70161_v);
         }

         String text = NoppesStringUtils.formatText(line.text, player.field_71092_bJ);
         player.func_71035_c(this.func_70023_ak() + ": " + text);
      }
   }

   public void func_70024_g(double d, double d1, double d2) {
      if (this.isWalking() && !this.isKilled()) {
         super.func_70024_g(d, d1, d2);
      }

   }

   public void func_70037_a(NBTTagCompound compound) {
      super.func_70037_a(compound);
      this.npcVersion = compound.func_74762_e("ModRev");
      VersionCompatibility.CheckNpcCompatibility(this, compound);
      this.display.readToNBT(compound);
      this.stats.readToNBT(compound);
      this.ai.readToNBT(compound);
      this.advanced.readToNBT(compound);
      this.inventory.readEntityFromNBT(compound);
      this.killedtime = compound.func_74763_f("KilledTime");
      this.startPos = NBTTags.getIntArray(compound.func_74761_m("StartPos"));
      if (this.startPos == null || this.startPos.length != 3) {
         this.startPos = new int[]{MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70163_u), MathHelper.func_76128_c(this.field_70161_v)};
      }

      this.dialogs = this.getDialogs(compound.func_74761_m("NPCDialogOptions"));
      this.textureLocation = null;
      this.textureGlowLocation = null;
      this.textureCloakLocation = null;
      this.updateTasks();
   }

   private HashMap getDialogs(NBTTagList tagList) {
      HashMap<Integer, DialogOption> map = new HashMap();

      for(int i = 0; i < tagList.func_74745_c(); ++i) {
         NBTTagCompound nbttagcompound = (NBTTagCompound)tagList.func_74743_b(i);
         int slot = nbttagcompound.func_74762_e("DialogSlot");
         DialogOption option = new DialogOption();
         option.readNBT(nbttagcompound.func_74775_l("NPCDialog"));
         map.put(slot, option);
      }

      return map;
   }

   public void func_70014_b(NBTTagCompound compound) {
      super.func_70014_b(compound);
      this.display.writeToNBT(compound);
      this.stats.writeToNBT(compound);
      this.ai.writeToNBT(compound);
      this.advanced.writeToNBT(compound);
      this.inventory.writeEntityToNBT(compound);
      compound.func_74772_a("KilledTime", this.killedtime);
      if (this.startPos == null) {
         this.startPos = new int[]{MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70163_u), MathHelper.func_76128_c(this.field_70161_v)};
      }

      compound.func_74782_a("StartPos", NBTTags.nbtIntArray(this.startPos));
      compound.func_74782_a("NPCDialogOptions", this.nbtDialogs(this.dialogs));
      compound.func_74768_a("ModRev", this.npcVersion);
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a((double)this.stats.maxHealth);
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a((double)CustomNpcs.NpcNavRange);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a((double)this.getSpeed());
      this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a((double)this.stats.attackStrength);
   }

   private NBTTagList nbtDialogs(HashMap dialogs2) {
      NBTTagList nbttaglist = new NBTTagList();

      for(int slot : dialogs2.keySet()) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         nbttagcompound.func_74768_a("DialogSlot", slot);
         nbttagcompound.func_74766_a("NPCDialog", ((DialogOption)dialogs2.get(slot)).writeNBT());
         nbttaglist.func_74742_a(nbttagcompound);
      }

      return nbttaglist;
   }

   public void updateHitbox() {
      if (this.currentAnimation == EnumAnimation.LYING) {
         this.field_70130_N = this.field_70131_O = 0.2F;
      } else if (this.currentAnimation == EnumAnimation.SITTING) {
         this.field_70130_N = 0.6F;
         this.field_70131_O = 1.3F;
      } else {
         this.field_70130_N = 0.6F;
         this.field_70131_O = 1.8F;
      }

      this.field_70130_N = this.field_70130_N / 5.0F * (float)this.display.modelSize;
      this.field_70131_O = this.field_70131_O / 5.0F * (float)this.display.modelSize;
   }

   public void dropPlayerItemWithRandomChoice(ItemStack itemstack, boolean flag) {
      if (itemstack != null) {
         EntityItem entityitem = new EntityItem(this.field_70170_p, this.field_70165_t, this.field_70163_u - (double)0.3F + (double)this.func_70047_e(), this.field_70161_v, itemstack);
         entityitem.field_70293_c = 40;
         if (flag) {
            float f2 = this.field_70146_Z.nextFloat() * 0.5F;
            float f4 = this.field_70146_Z.nextFloat() * 3.141593F * 2.0F;
            entityitem.field_70159_w = (double)(-MathHelper.func_76126_a(f4) * f2);
            entityitem.field_70179_y = (double)(MathHelper.func_76134_b(f4) * f2);
            entityitem.field_70181_x = (double)0.2F;
         } else {
            float f1 = 0.3F;
            entityitem.field_70159_w = (double)(-MathHelper.func_76126_a(this.field_70177_z / 180.0F * 3.141593F) * MathHelper.func_76134_b(this.field_70125_A / 180.0F * 3.141593F) * f1);
            entityitem.field_70179_y = (double)(MathHelper.func_76134_b(this.field_70177_z / 180.0F * 3.141593F) * MathHelper.func_76134_b(this.field_70125_A / 180.0F * 3.141593F) * f1);
            entityitem.field_70181_x = (double)(-MathHelper.func_76126_a(this.field_70125_A / 180.0F * 3.141593F) * f1 + 0.1F);
            f1 = 0.02F;
            float f3 = this.field_70146_Z.nextFloat() * 3.141593F * 2.0F;
            f1 *= this.field_70146_Z.nextFloat();
            entityitem.field_70159_w += Math.cos((double)f3) * (double)f1;
            entityitem.field_70181_x += (double)((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.1F);
            entityitem.field_70179_y += Math.sin((double)f3) * (double)f1;
         }

         this.field_70170_p.func_72838_d(entityitem);
      }
   }

   public void func_70609_aI() {
      if (this.stats.spawnCycle == 3) {
         super.func_70609_aI();
      } else {
         ++this.field_70725_aQ;
         if (!this.field_70170_p.field_72995_K) {
            if (!this.hasDied) {
               this.func_70106_y();
            }

            --this.killedtime;
            if (this.killedtime <= 0L && (this.stats.spawnCycle == 0 || this.field_70170_p.func_72935_r() && this.stats.spawnCycle == 1 || !this.field_70170_p.func_72935_r() && this.stats.spawnCycle == 2)) {
               this.reset();
            }

         }
      }
   }

   public void reset() {
      this.hasDied = false;
      this.func_70606_j(this.func_110138_aP());
      this.field_70180_af.func_75692_b(24, 0);
      this.field_70180_af.func_75692_b(14, 0);
      this.field_70180_af.func_75692_b(15, 0);
      this.func_70624_b((EntityLivingBase)null);
      this.func_70604_c((EntityLivingBase)null);
      this.field_70725_aQ = 0;
      if (this.startPos != null) {
         this.func_70012_b((double)this.getStartXPos(), this.getStartYPos() + (double)1.0F, (double)this.getStartZPos(), this.field_70177_z, this.field_70125_A);
      }

      this.killedtime = 0L;
      this.func_70066_B();
      this.func_70674_bp();
      this.func_70612_e(0.0F, 0.0F);
      this.field_70140_Q = 0.0F;
      this.func_70661_as().func_75499_g();
      this.currentAnimation = EnumAnimation.NONE;
      this.updateHitbox();
      this.updateTasks();
      this.ai.movingPos = 0;
      if (this.jobInterface != null) {
         this.jobInterface.reset();
      }

   }

   public void cloakUpdate() {
      this.field_20066_r = this.field_20063_u;
      this.field_20065_s = this.field_20062_v;
      this.field_20064_t = this.field_20061_w;
      double d = this.field_70165_t - this.field_20063_u;
      double d1 = this.field_70163_u - this.field_20062_v;
      double d2 = this.field_70161_v - this.field_20061_w;
      double d3 = (double)10.0F;
      if (d > d3) {
         this.field_20066_r = this.field_20063_u = this.field_70165_t;
      }

      if (d2 > d3) {
         this.field_20064_t = this.field_20061_w = this.field_70161_v;
      }

      if (d1 > d3) {
         this.field_20065_s = this.field_20062_v = this.field_70163_u;
      }

      if (d < -d3) {
         this.field_20066_r = this.field_20063_u = this.field_70165_t;
      }

      if (d2 < -d3) {
         this.field_20064_t = this.field_20061_w = this.field_70161_v;
      }

      if (d1 < -d3) {
         this.field_20065_s = this.field_20062_v = this.field_70163_u;
      }

      this.field_20063_u += d * (double)0.25F;
      this.field_20061_w += d2 * (double)0.25F;
      this.field_20062_v += d1 * (double)0.25F;
   }

   protected boolean func_70692_ba() {
      return false;
   }

   public ItemStack func_70694_bm() {
      if (this.isAttacking()) {
         return this.inventory.getWeapon();
      } else {
         return this.jobInterface != null && this.jobInterface.overrideMainHand ? this.jobInterface.mainhand : this.inventory.getWeapon();
      }
   }

   public ItemStack getOffHand() {
      if (this.isAttacking()) {
         return this.inventory.getOffHand();
      } else {
         return this.jobInterface != null && this.jobInterface.overrideOffHand ? this.jobInterface.offhand : this.inventory.getOffHand();
      }
   }

   public void func_70645_a(DamageSource par1DamageSource) {
      this.inventory.dropStuff(this.field_70718_bc > 0);
      if (!this.isRemote()) {
         this.saySurrounding(this.advanced.getKilledLine());
      }

      super.func_70645_a(par1DamageSource);
   }

   public void func_70106_y() {
      this.hasDied = true;
      if (!this.field_70170_p.field_72995_K && this.stats.spawnCycle != 3) {
         this.func_70606_j(-1.0F);
         this.killedtime = (long)(this.stats.respawnTime * 10);
         if (this.advanced.role != EnumRoleType.None && this.roleInterface != null) {
            this.roleInterface.killed();
         }

         if (this.advanced.job != EnumJobType.None && this.jobInterface != null) {
            this.jobInterface.killed();
         }
      } else {
         this.func_70656_aK();
         this.delete();
      }

   }

   public void delete() {
      if (this.advanced.role != EnumRoleType.None && this.roleInterface != null) {
         this.roleInterface.delete();
      }

      if (this.advanced.job != EnumJobType.None && this.jobInterface != null) {
         this.jobInterface.delete();
      }

      super.func_70106_y();
   }

   public float getStartXPos() {
      return (float)this.startPos[0] + this.ai.bodyOffsetX / 10.0F;
   }

   public float getStartZPos() {
      return (float)this.startPos[2] + this.ai.bodyOffsetZ / 10.0F;
   }

   public boolean isVeryNearAssignedPlace() {
      double xx = this.field_70165_t - (double)this.getStartXPos();
      double zz = this.field_70161_v - (double)this.getStartZPos();
      if (!(xx < -0.2) && !(xx > 0.2)) {
         return !(zz < -0.2) && !(zz > 0.2);
      } else {
         return false;
      }
   }

   public Icon func_70620_b(ItemStack par1ItemStack, int par2) {
      EntityPlayer player = CustomNpcs.proxy.getPlayer();
      return player == null ? super.func_70620_b(par1ItemStack, par2) : player.func_70620_b(par1ItemStack, par2);
   }

   public double getStartYPos() {
      int i = this.startPos[0];
      int j = this.startPos[1];
      int k = this.startPos[2];
      double yy = (double)0.0F;

      for(int ii = j; ii >= 0; --ii) {
         int id = this.field_70170_p.func_72798_a(i, ii, k);
         if (id != 0) {
            Block block = Block.field_71973_m[id];
            AxisAlignedBB bb = block.func_71872_e(this.field_70170_p, i, ii, k);
            if (bb != null) {
               yy = bb.field_72337_e;
               break;
            }
         }
      }

      if (yy == (double)0.0F) {
         this.func_70106_y();
      }

      yy += (double)0.5F;
      return yy;
   }

   public void givePlayerItem(EntityPlayer player, ItemStack item) {
      if (!this.field_70170_p.field_72995_K) {
         item = item.func_77946_l();
         float f = 0.7F;
         double d = (double)(this.field_70170_p.field_73012_v.nextFloat() * f) + (double)(1.0F - f);
         double d1 = (double)(this.field_70170_p.field_73012_v.nextFloat() * f) + (double)(1.0F - f);
         double d2 = (double)(this.field_70170_p.field_73012_v.nextFloat() * f) + (double)(1.0F - f);
         EntityItem entityitem = new EntityItem(this.field_70170_p, this.field_70165_t + d, this.field_70163_u + d1, this.field_70161_v + d2, item);
         entityitem.field_70293_c = 2;
         this.field_70170_p.func_72838_d(entityitem);
         int i = item.field_77994_a;
         if (player.field_71071_by.func_70441_a(item)) {
            this.field_70170_p.func_72956_a(entityitem, "random.pop", 0.2F, ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            player.func_71001_a(entityitem, i);
            if (item.field_77994_a <= 0) {
               entityitem.func_70106_y();
            }
         }

      }
   }

   public boolean func_70608_bn() {
      return this.currentAnimation == EnumAnimation.LYING;
   }

   public boolean func_70115_ae() {
      return this.currentAnimation == EnumAnimation.SITTING;
   }

   public boolean isWalking() {
      return this.ai.movingType != EnumMovingType.Standing || this.isAttacking() || this.isFollowerWithOwner() || this.field_70180_af.func_75679_c(15) == 1;
   }

   public boolean func_70093_af() {
      return this.currentAnimation == EnumAnimation.SNEAKING;
   }

   public void func_70653_a(Entity par1Entity, float par2, double par3, double par5) {
      this.field_70160_al = true;
      float f1 = MathHelper.func_76133_a(par3 * par3 + par5 * par5);
      float f2 = 0.5F * (2.0F - this.stats.resistances.knockback);
      this.field_70159_w /= (double)2.0F;
      this.field_70181_x /= (double)2.0F;
      this.field_70179_y /= (double)2.0F;
      this.field_70159_w -= par3 / (double)f1 * (double)f2;
      this.field_70181_x += 0.2 + (double)(f2 / 2.0F);
      this.field_70179_y -= par5 / (double)f1 * (double)f2;
      if (this.field_70181_x > (double)0.4F) {
         this.field_70181_x = (double)0.4F;
      }

   }

   public Faction getFaction() {
      String[] split = this.field_70180_af.func_75681_e(13).split(":");
      int faction = 0;
      if (this.field_70170_p != null && (split.length > 1 || !this.field_70170_p.field_72995_K)) {
         if (split.length > 1) {
            faction = Integer.parseInt(split[0]);
         }

         if (this.field_70170_p.field_72995_K) {
            Faction fac = new Faction();
            fac.id = faction;
            fac.color = Integer.parseInt(split[1]);
            fac.name = split[2];
            return fac;
         } else {
            Faction fac = FactionController.getInstance().getFaction(faction);
            if (fac == null) {
               faction = FactionController.getInstance().getFirstFactionId();
               fac = FactionController.getInstance().getFaction(faction);
            }

            return fac;
         }
      } else {
         return new Faction();
      }
   }

   public boolean isRemote() {
      return this.field_70170_p == null || this.field_70170_p.field_72995_K;
   }

   public void setFaction(int integer) {
      if (integer >= 0 && !this.isRemote()) {
         Faction faction = FactionController.getInstance().getFaction(integer);
         if (faction != null) {
            String str = faction.id + ":" + faction.color + ":" + faction.name;
            if (str.length() > 64) {
               str = str.substring(0, 64);
            }

            this.field_70180_af.func_75692_b(13, str);
         }
      }
   }

   public boolean isFollowerWithOwner() {
      return this.advanced.role == EnumRoleType.Follower && ((RoleFollower)this.roleInterface).getDaysLeft() > 0;
   }

   public boolean func_70687_e(PotionEffect par1PotionEffect) {
      return this.func_70668_bt() == EnumCreatureAttribute.ARTHROPOD && par1PotionEffect.func_76456_a() == Potion.field_76436_u.field_76415_H ? false : super.func_70687_e(par1PotionEffect);
   }

   public NBTTagCompound copy() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      this.func_70039_c(nbttagcompound);
      nbttagcompound.func_74768_a("EntityId", this.field_70157_k);
      return nbttagcompound;
   }

   public boolean inNormalState() {
      return !this.isAttacking() && !this.isFollowerWithOwner();
   }

   public boolean isAttacking() {
      return this.field_70180_af.func_75683_a(23) == 1;
   }

   public boolean isKilled() {
      return this.field_70180_af.func_75679_c(24) == 1;
   }

   public void writeSpawnData(ByteArrayDataOutput data) {
      try {
         CompressedStreamTools.func_74800_a(this.writeSpawnData(), data);
      } catch (IOException e) {
         e.printStackTrace();
      }

   }

   public NBTTagCompound writeSpawnData() {
      NBTTagCompound compound = new NBTTagCompound();
      this.display.writeToNBT(compound);
      compound.func_74768_a("MaxHealth", this.stats.maxHealth);
      compound.func_74782_a("Armor", NBTTags.nbtItemStackList(this.inventory.getArmor()));
      compound.func_74782_a("Weapons", NBTTags.nbtItemStackList(this.inventory.getWeapons()));
      compound.func_74768_a("Speed", this.stats.moveSpeed);
      compound.func_74757_a("DeadBody", this.stats.hideKilledBody);
      compound.func_74768_a("StandingState", this.ai.standingType.ordinal());
      compound.func_74768_a("MovingState", this.ai.movingType.ordinal());
      compound.func_74768_a("Orientation", this.ai.orientation);
      compound.func_74768_a("Role", this.advanced.role.ordinal());
      compound.func_74768_a("Job", this.advanced.job.ordinal());
      if (this.advanced.job == EnumJobType.Bard) {
         NBTTagCompound bard = new NBTTagCompound();
         this.jobInterface.writeEntityToNBT(bard);
         compound.func_74782_a("Bard", bard);
      }

      if (this.advanced.job == EnumJobType.Boss) {
         compound.func_74757_a("Boss", ((JobBoss)this.jobInterface).hideName);
      }

      return compound;
   }

   public void readSpawnData(ByteArrayDataInput data) {
      try {
         NBTTagCompound compound = CompressedStreamTools.func_74794_a(data);
         this.readSpawnData(compound);
      } catch (IOException var3) {
      }

   }

   public void readSpawnData(NBTTagCompound compound) {
      this.stats.maxHealth = compound.func_74762_e("MaxHealth");
      this.stats.moveSpeed = compound.func_74762_e("Speed");
      this.stats.hideKilledBody = compound.func_74767_n("DeadBody");
      this.ai.standingType = EnumStandingType.values()[compound.func_74762_e("StandingState") % EnumStandingType.values().length];
      this.ai.movingType = EnumMovingType.values()[compound.func_74762_e("MovingState") % EnumMovingType.values().length];
      this.ai.orientation = compound.func_74762_e("Orientation");
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a((double)this.stats.maxHealth);
      this.inventory.setArmor(NBTTags.getItemStackList(compound.func_74761_m("Armor")));
      this.inventory.setWeapons(NBTTags.getItemStackList(compound.func_74761_m("Weapons")));
      this.advanced.setRole(compound.func_74762_e("Role"));
      this.advanced.setJob(compound.func_74762_e("Job"));
      if (this.advanced.job == EnumJobType.Bard) {
         NBTTagCompound bard = compound.func_74775_l("Bard");
         this.jobInterface.readEntityFromNBT(bard);
      }

      if (this.advanced.job == EnumJobType.Boss) {
         ((JobBoss)this.jobInterface).hideName = compound.func_74767_n("Boss");
      }

      this.display.readToNBT(compound);
   }

   public String func_70005_c_() {
      return this.display.name;
   }

   public boolean func_70003_b(int var1, String var2) {
      if (CustomNpcs.NpcUseOpCommands) {
         return true;
      } else {
         return var1 <= 2;
      }
   }

   public ChunkCoordinates func_82114_b() {
      return new ChunkCoordinates(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70163_u), MathHelper.func_76128_c(this.field_70161_v));
   }

   public boolean func_70686_a(Class par1Class) {
      return EntityBat.class != par1Class;
   }

   public void setImmuneToFire(boolean immuneToFire) {
      this.field_70178_ae = immuneToFire;
      this.stats.immuneToFire = immuneToFire;
   }

   public void setAvoidWater(boolean avoidWater) {
      this.func_70661_as().func_75491_a(avoidWater);
      this.ai.avoidsWater = avoidWater;
   }

   public void setSleeping(boolean b) {
      this.isSleeping = b;
   }

   public boolean isSleeping() {
      return this.isSleeping;
   }

   protected void func_70069_a(float par1) {
      if (!this.stats.noFallDamage) {
         super.func_70069_a(par1);
      }

   }

   public boolean func_70067_L() {
      return !this.isKilled();
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111264_e);
   }

   public EntityAIRangedAttack getRangedTask() {
      return this.aiRange;
   }

   public void func_70006_a(ChatMessageComponent chatmessagecomponent) {
   }

   public ItemStack func_130225_q(int i) {
      return this.inventory.armorItemInSlot(i);
   }

   public void func_70063_aa() {
   }

   public World func_130014_f_() {
      return this.field_70170_p;
   }

   public boolean func_98034_c(EntityPlayer player) {
      return this.display.visible == 1 && (player.func_70694_bm() == null || player.func_70694_bm().func_77973_b() != CustomItems.wand);
   }

   public boolean func_82150_aj() {
      return this.display.visible != 0;
   }
}
