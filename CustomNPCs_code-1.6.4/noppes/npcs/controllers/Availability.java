package noppes.npcs.controllers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.ICompatibilty;
import noppes.npcs.VersionCompatibility;
import noppes.npcs.constants.EnumAvailabilityDialog;
import noppes.npcs.constants.EnumAvailabilityFaction;
import noppes.npcs.constants.EnumAvailabilityFactionType;
import noppes.npcs.constants.EnumAvailabilityQuest;
import noppes.npcs.constants.EnumDayTime;

public class Availability implements ICompatibilty {
   public int version;
   public EnumAvailabilityDialog dialogAvailable;
   public EnumAvailabilityQuest questAvailable;
   public int questId;
   public int dialogId;
   public EnumAvailabilityDialog dialog2Available;
   public EnumAvailabilityQuest quest2Available;
   public int quest2Id;
   public int dialog2Id;
   public EnumDayTime daytime;
   public int factionId;
   public int faction2Id;
   public EnumAvailabilityFactionType factionAvailable;
   public EnumAvailabilityFactionType faction2Available;
   public EnumAvailabilityFaction factionStance;
   public EnumAvailabilityFaction faction2Stance;
   public int minPlayerLevel;

   public Availability() {
      this.version = VersionCompatibility.ModRev;
      this.dialogAvailable = EnumAvailabilityDialog.Always;
      this.questAvailable = EnumAvailabilityQuest.Always;
      this.questId = -1;
      this.dialogId = -1;
      this.dialog2Available = EnumAvailabilityDialog.Always;
      this.quest2Available = EnumAvailabilityQuest.Always;
      this.quest2Id = -1;
      this.dialog2Id = -1;
      this.daytime = EnumDayTime.Always;
      this.factionId = -1;
      this.faction2Id = -1;
      this.factionAvailable = EnumAvailabilityFactionType.Always;
      this.faction2Available = EnumAvailabilityFactionType.Always;
      this.factionStance = EnumAvailabilityFaction.Friendly;
      this.faction2Stance = EnumAvailabilityFaction.Friendly;
      this.minPlayerLevel = 0;
   }

   public void readFromNBT(NBTTagCompound compound) {
      this.version = compound.func_74762_e("ModRev");
      VersionCompatibility.CheckAvailabilityCompatibility(this, compound);
      this.setDialogAvailability(compound.func_74762_e("AvailabilityDialog"));
      this.setQuestAvailability(compound.func_74762_e("AvailabilityQuest"));
      this.setFactionAvailability(compound.func_74762_e("AvailabilityFaction"));
      this.setFactionAvailabilityStance(compound.func_74762_e("AvailabilityFactionStance"));
      this.questId = compound.func_74762_e("AvailabilityQuestId");
      this.dialogId = compound.func_74762_e("AvailabilityDialogId");
      this.factionId = compound.func_74762_e("AvailabilityFactionId");
      this.setDialog2Availability(compound.func_74762_e("AvailabilityDialog2"));
      this.setQuest2Availability(compound.func_74762_e("AvailabilityQuest2"));
      this.setFaction2Availability(compound.func_74762_e("AvailabilityFaction2"));
      this.setFaction2AvailabilityStance(compound.func_74762_e("AvailabilityFaction2Stance"));
      this.quest2Id = compound.func_74762_e("AvailabilityQuest2Id");
      this.dialog2Id = compound.func_74762_e("AvailabilityDialog2Id");
      this.faction2Id = compound.func_74762_e("AvailabilityFaction2Id");
      this.daytime = EnumDayTime.values()[compound.func_74762_e("AvailabilityDayTime")];
      this.minPlayerLevel = compound.func_74762_e("AvailabilityMinPlayerLevel");
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      compound.func_74768_a("ModRev", this.version);
      compound.func_74768_a("AvailabilityDialog", this.dialogAvailable.ordinal());
      compound.func_74768_a("AvailabilityQuest", this.questAvailable.ordinal());
      compound.func_74768_a("AvailabilityFaction", this.factionAvailable.ordinal());
      compound.func_74768_a("AvailabilityFactionStance", this.factionStance.ordinal());
      compound.func_74768_a("AvailabilityQuestId", this.questId);
      compound.func_74768_a("AvailabilityDialogId", this.dialogId);
      compound.func_74768_a("AvailabilityFactionId", this.factionId);
      compound.func_74768_a("AvailabilityDialog2", this.dialog2Available.ordinal());
      compound.func_74768_a("AvailabilityQuest2", this.quest2Available.ordinal());
      compound.func_74768_a("AvailabilityFaction2", this.faction2Available.ordinal());
      compound.func_74768_a("AvailabilityFaction2Stance", this.faction2Stance.ordinal());
      compound.func_74768_a("AvailabilityQuest2Id", this.quest2Id);
      compound.func_74768_a("AvailabilityDialog2Id", this.dialog2Id);
      compound.func_74768_a("AvailabilityFaction2Id", this.faction2Id);
      compound.func_74768_a("AvailabilityDayTime", this.daytime.ordinal());
      compound.func_74768_a("AvailabilityMinPlayerLevel", this.minPlayerLevel);
      return compound;
   }

   public void setDialogAvailability(int integer) {
      this.dialogAvailable = EnumAvailabilityDialog.values()[integer];
   }

   public void setQuestAvailability(int integer) {
      this.questAvailable = EnumAvailabilityQuest.values()[integer];
   }

   public void setDialog2Availability(int integer) {
      this.dialog2Available = EnumAvailabilityDialog.values()[integer];
   }

   public void setQuest2Availability(int integer) {
      this.quest2Available = EnumAvailabilityQuest.values()[integer];
   }

   public void setFactionAvailability(int value) {
      this.factionAvailable = EnumAvailabilityFactionType.values()[value];
   }

   public void setFaction2Availability(int value) {
      this.faction2Available = EnumAvailabilityFactionType.values()[value];
   }

   public void setFactionAvailabilityStance(int integer) {
      this.factionStance = EnumAvailabilityFaction.values()[integer];
   }

   public void setFaction2AvailabilityStance(int integer) {
      this.faction2Stance = EnumAvailabilityFaction.values()[integer];
   }

   public boolean isAvailable(EntityPlayer player) {
      if (this.daytime == EnumDayTime.Day) {
         long time = player.field_70170_p.func_72820_D() % 24000L;
         if (time > 12000L) {
            return false;
         }
      }

      if (this.daytime == EnumDayTime.Night) {
         long time = player.field_70170_p.func_72820_D() % 24000L;
         if (time < 12000L) {
            return false;
         }
      }

      if (!this.dialogAvailable(this.dialogId, this.dialogAvailable, player)) {
         return false;
      } else if (!this.dialogAvailable(this.dialog2Id, this.dialog2Available, player)) {
         return false;
      } else if (!this.questAvailable(this.questId, this.questAvailable, player)) {
         return false;
      } else if (!this.questAvailable(this.quest2Id, this.quest2Available, player)) {
         return false;
      } else if (!this.factionAvailable(this.factionId, this.factionStance, this.factionAvailable, player)) {
         return false;
      } else if (!this.factionAvailable(this.faction2Id, this.faction2Stance, this.faction2Available, player)) {
         return false;
      } else {
         return player.field_71068_ca >= this.minPlayerLevel;
      }
   }

   private boolean factionAvailable(int id, EnumAvailabilityFaction stance, EnumAvailabilityFactionType available, EntityPlayer player) {
      if (available == EnumAvailabilityFactionType.Always) {
         return true;
      } else {
         Faction faction = FactionController.getInstance().getFaction(id);
         if (faction == null) {
            return true;
         } else {
            PlayerFactionData data = PlayerDataController.instance.getPlayerData(player).factionData;
            int points = data.getFactionPoints(id);
            EnumAvailabilityFaction current = EnumAvailabilityFaction.Neutral;
            if (faction.neutralPoints >= points) {
               current = EnumAvailabilityFaction.Hostile;
            }

            if (faction.friendlyPoints < points) {
               current = EnumAvailabilityFaction.Friendly;
            }

            if (available == EnumAvailabilityFactionType.Is && stance == current) {
               return true;
            } else {
               return available == EnumAvailabilityFactionType.IsNot && stance != current;
            }
         }
      }
   }

   public boolean dialogAvailable(int id, EnumAvailabilityDialog en, EntityPlayer player) {
      if (en == EnumAvailabilityDialog.Always) {
         return true;
      } else {
         boolean hasRead = PlayerDataController.instance.getPlayerData(player).dialogData.dialogsRead.contains(id);
         if (hasRead && en == EnumAvailabilityDialog.After) {
            return true;
         } else {
            return !hasRead && en == EnumAvailabilityDialog.Before;
         }
      }
   }

   public boolean questAvailable(int id, EnumAvailabilityQuest en, EntityPlayer player) {
      if (en == EnumAvailabilityQuest.Always) {
         return true;
      } else if (en == EnumAvailabilityQuest.After && PlayerQuestController.isQuestFinished(player, id)) {
         return true;
      } else if (en == EnumAvailabilityQuest.Before && !PlayerQuestController.isQuestFinished(player, id)) {
         return true;
      } else if (en == EnumAvailabilityQuest.Active && PlayerQuestController.isQuestActive(player, id)) {
         return true;
      } else {
         return en == EnumAvailabilityQuest.NotActive && !PlayerQuestController.isQuestActive(player, id);
      }
   }

   public int getVersion() {
      return this.version;
   }

   public void setVersion(int version) {
      this.version = version;
   }
}
