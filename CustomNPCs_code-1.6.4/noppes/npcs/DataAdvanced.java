package noppes.npcs;

import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.constants.EnumJobType;
import noppes.npcs.constants.EnumRoleType;
import noppes.npcs.controllers.FactionOptions;
import noppes.npcs.controllers.Line;
import noppes.npcs.controllers.Lines;
import noppes.npcs.roles.JobBard;
import noppes.npcs.roles.JobBoss;
import noppes.npcs.roles.JobConversation;
import noppes.npcs.roles.JobGuard;
import noppes.npcs.roles.JobHealer;
import noppes.npcs.roles.JobItemGiver;
import noppes.npcs.roles.JobSpawner;
import noppes.npcs.roles.RoleBank;
import noppes.npcs.roles.RoleFollower;
import noppes.npcs.roles.RolePostman;
import noppes.npcs.roles.RoleTrader;
import noppes.npcs.roles.RoleTransporter;

public class DataAdvanced {
   public Lines interactLines = new Lines();
   public Lines worldLines = new Lines();
   public Lines attackLines = new Lines();
   public Lines killedLines = new Lines();
   public String idleSound = "";
   public String angrySound = "";
   public String hurtSound = "damage.hit";
   public String deathSound = "damage.hit";
   public String stepSound = "";
   private EntityNPCInterface npc;
   public FactionOptions factions = new FactionOptions();
   public EnumRoleType role;
   public EnumJobType job;
   public boolean attackOtherFactions;
   public boolean defendFaction;

   public DataAdvanced(EntityNPCInterface npc) {
      this.role = EnumRoleType.None;
      this.job = EnumJobType.None;
      this.attackOtherFactions = false;
      this.defendFaction = false;
      this.npc = npc;
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      compound.func_74766_a("NpcLines", this.worldLines.writeToNBT());
      compound.func_74766_a("NpcKilledLines", this.killedLines.writeToNBT());
      compound.func_74766_a("NpcInteractLines", this.interactLines.writeToNBT());
      compound.func_74766_a("NpcAttackLines", this.attackLines.writeToNBT());
      compound.func_74778_a("NpcIdleSound", this.idleSound);
      compound.func_74778_a("NpcAngrySound", this.angrySound);
      compound.func_74778_a("NpcHurtSound", this.hurtSound);
      compound.func_74778_a("NpcDeathSound", this.deathSound);
      compound.func_74778_a("NpcStepSound", this.stepSound);
      compound.func_74768_a("FactionID", this.npc.getFaction().id);
      compound.func_74757_a("AttackOtherFactions", this.attackOtherFactions);
      compound.func_74757_a("DefendFaction", this.defendFaction);
      compound.func_74768_a("Role", this.role.ordinal());
      compound.func_74768_a("NpcJob", this.job.ordinal());
      compound.func_74766_a("FactionPoints", this.factions.writeToNBT(new NBTTagCompound()));
      if (this.role != EnumRoleType.None && this.npc.roleInterface != null) {
         this.npc.roleInterface.writeEntityToNBT(compound);
      }

      if (this.job != EnumJobType.None && this.npc.jobInterface != null) {
         this.npc.jobInterface.writeEntityToNBT(compound);
      }

      return compound;
   }

   public void readToNBT(NBTTagCompound compound) {
      this.interactLines.readNBT(compound.func_74775_l("NpcInteractLines"));
      this.worldLines.readNBT(compound.func_74775_l("NpcLines"));
      this.attackLines.readNBT(compound.func_74775_l("NpcAttackLines"));
      this.killedLines.readNBT(compound.func_74775_l("NpcKilledLines"));
      this.idleSound = compound.func_74779_i("NpcIdleSound");
      this.angrySound = compound.func_74779_i("NpcAngrySound");
      this.hurtSound = compound.func_74779_i("NpcHurtSound");
      this.deathSound = compound.func_74779_i("NpcDeathSound");
      this.stepSound = compound.func_74779_i("NpcStepSound");
      this.npc.setFaction(compound.func_74762_e("FactionID"));
      this.attackOtherFactions = compound.func_74767_n("AttackOtherFactions");
      this.defendFaction = compound.func_74767_n("DefendFaction");
      this.setRole(compound.func_74762_e("Role"));
      this.setJob(compound.func_74762_e("NpcJob"));
      this.factions.readFromNBT(compound.func_74775_l("FactionPoints"));
      if (this.role != EnumRoleType.None && this.npc.roleInterface != null) {
         this.npc.roleInterface.readEntityFromNBT(compound);
      }

      if (this.job != EnumJobType.None && this.npc.jobInterface != null) {
         this.npc.jobInterface.readEntityFromNBT(compound);
      }

   }

   public Line getInteractLine() {
      return this.interactLines.getLine();
   }

   public Line getAttackLine() {
      return this.attackLines.getLine();
   }

   public Line getKilledLine() {
      return this.killedLines.getLine();
   }

   public Line getWorldLine() {
      return this.worldLines.getLine();
   }

   public void setRole(int i) {
      if (EnumRoleType.values().length <= i) {
         i -= 2;
      }

      this.role = EnumRoleType.values()[i];
      if (this.role == EnumRoleType.None) {
         this.npc.roleInterface = null;
      } else if (this.role == EnumRoleType.Trader && !(this.npc.roleInterface instanceof RoleTrader)) {
         this.npc.roleInterface = new RoleTrader(this.npc);
      } else if (this.role == EnumRoleType.Follower && !(this.npc.roleInterface instanceof RoleFollower)) {
         this.npc.roleInterface = new RoleFollower(this.npc);
      } else if (this.role == EnumRoleType.Bank && !(this.npc.roleInterface instanceof RoleBank)) {
         this.npc.roleInterface = new RoleBank(this.npc);
      } else if (this.role == EnumRoleType.Transporter && !(this.npc.roleInterface instanceof RoleTransporter)) {
         this.npc.roleInterface = new RoleTransporter(this.npc);
      } else if (this.role == EnumRoleType.Postman && !(this.npc.roleInterface instanceof RolePostman)) {
         this.npc.roleInterface = new RolePostman(this.npc);
      }

   }

   public void setJob(int i) {
      this.job = EnumJobType.values()[i % EnumJobType.values().length];
      if (this.job == EnumJobType.None) {
         this.npc.jobInterface = null;
      } else if (this.job == EnumJobType.Bard && !(this.npc.jobInterface instanceof JobBard)) {
         this.npc.jobInterface = new JobBard(this.npc);
      } else if (this.job == EnumJobType.Healer && !(this.npc.jobInterface instanceof JobHealer)) {
         this.npc.jobInterface = new JobHealer(this.npc);
      } else if (this.job == EnumJobType.Guard && !(this.npc.jobInterface instanceof JobGuard)) {
         this.npc.jobInterface = new JobGuard(this.npc);
      } else if (this.job == EnumJobType.ItemGiver && !(this.npc.jobInterface instanceof JobItemGiver)) {
         this.npc.jobInterface = new JobItemGiver(this.npc);
      } else if (this.job == EnumJobType.Boss && !(this.npc.jobInterface instanceof JobBoss)) {
         this.npc.jobInterface = new JobBoss(this.npc);
      } else if (this.job == EnumJobType.Spawner && !(this.npc.jobInterface instanceof JobSpawner)) {
         this.npc.jobInterface = new JobSpawner(this.npc);
      } else if (this.job == EnumJobType.Conversation && !(this.npc.jobInterface instanceof JobConversation)) {
         this.npc.jobInterface = new JobConversation(this.npc);
      }

   }

   public boolean hasWorldLines() {
      return !this.worldLines.isEmpty();
   }
}
