package noppes.npcs.controllers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.ICompatibilty;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.NpcMiscInventory;
import noppes.npcs.VersionCompatibility;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.constants.EnumQuestCompletion;
import noppes.npcs.constants.EnumQuestRepeat;
import noppes.npcs.constants.EnumQuestType;
import noppes.npcs.quests.QuestDialog;
import noppes.npcs.quests.QuestInterface;
import noppes.npcs.quests.QuestItem;
import noppes.npcs.quests.QuestKill;
import noppes.npcs.quests.QuestLocation;

public class Quest implements ICompatibilty {
   public int version;
   public int id;
   public EnumQuestType type;
   public EnumQuestRepeat repeat;
   public EnumQuestCompletion completion;
   public String title;
   public QuestCategory category;
   public String logText;
   public String completeText;
   public String completerNpc;
   public int nextQuestid;
   public String nextQuestTitle;
   public PlayerMail mail;
   public QuestInterface questInterface;
   public int rewardExp;
   public NpcMiscInventory rewardItems;
   public FactionOptions factionOptions;

   public Quest() {
      this.version = VersionCompatibility.ModRev;
      this.id = -1;
      this.type = EnumQuestType.Item;
      this.repeat = EnumQuestRepeat.None;
      this.completion = EnumQuestCompletion.Npc;
      this.title = "default";
      this.logText = "";
      this.completeText = "";
      this.completerNpc = "";
      this.nextQuestid = -1;
      this.nextQuestTitle = "";
      this.mail = new PlayerMail();
      this.questInterface = new QuestItem();
      this.rewardExp = 0;
      this.rewardItems = new NpcMiscInventory(9);
      this.factionOptions = new FactionOptions();
   }

   public void readNBT(NBTTagCompound compound) {
      this.version = compound.func_74762_e("ModRev");
      VersionCompatibility.CheckAvailabilityCompatibility(this, compound);
      this.id = compound.func_74762_e("Id");
      this.setType(EnumQuestType.values()[compound.func_74762_e("Type")]);
      this.title = compound.func_74779_i("Title");
      this.logText = compound.func_74779_i("Text");
      this.completeText = compound.func_74779_i("CompleteText");
      this.completerNpc = compound.func_74779_i("CompleterNpc");
      this.nextQuestid = compound.func_74762_e("NextQuestId");
      this.nextQuestTitle = compound.func_74779_i("NextQuestTitle");
      if (this.hasNewQuest()) {
         this.nextQuestTitle = this.getNextQuest().title;
      } else {
         this.nextQuestTitle = "";
      }

      this.rewardExp = compound.func_74762_e("RewardExp");
      this.rewardItems.setFromNBT(compound.func_74775_l("Rewards"));
      this.completion = EnumQuestCompletion.values()[compound.func_74762_e("QuestCompletion")];
      this.repeat = EnumQuestRepeat.values()[compound.func_74762_e("QuestRepeat")];
      this.questInterface.readEntityFromNBT(compound);
      this.factionOptions.readFromNBT(compound.func_74775_l("QuestFactionPoints"));
      this.mail.readNBT(compound.func_74775_l("QuestMail"));
   }

   public void setType(EnumQuestType questType) {
      this.type = questType;
      if (this.type == EnumQuestType.Item) {
         this.questInterface = new QuestItem();
      } else if (this.type == EnumQuestType.Dialog) {
         this.questInterface = new QuestDialog();
      } else if (this.type == EnumQuestType.Kill) {
         this.questInterface = new QuestKill();
      } else if (this.type == EnumQuestType.Location) {
         this.questInterface = new QuestLocation();
      }

      if (this.questInterface != null) {
         this.questInterface.questId = this.id;
      }

   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      compound.func_74768_a("ModRev", this.version);
      compound.func_74768_a("Id", this.id);
      compound.func_74768_a("Type", this.type.ordinal());
      compound.func_74778_a("Title", this.title);
      compound.func_74778_a("Text", this.logText);
      compound.func_74778_a("CompleteText", this.completeText);
      compound.func_74778_a("CompleterNpc", this.completerNpc);
      compound.func_74768_a("NextQuestId", this.nextQuestid);
      compound.func_74778_a("NextQuestTitle", this.nextQuestTitle);
      compound.func_74768_a("RewardExp", this.rewardExp);
      compound.func_74766_a("Rewards", this.rewardItems.getToNBT());
      compound.func_74768_a("QuestCompletion", this.completion.ordinal());
      compound.func_74768_a("QuestRepeat", this.repeat.ordinal());
      this.questInterface.writeEntityToNBT(compound);
      compound.func_74766_a("QuestFactionPoints", this.factionOptions.writeToNBT(new NBTTagCompound()));
      compound.func_74766_a("QuestMail", this.mail.writeNBT());
      return compound;
   }

   public boolean hasNewQuest() {
      return this.getNextQuest() != null;
   }

   public Quest getNextQuest() {
      return QuestController.instance == null ? null : (Quest)QuestController.instance.quests.get(this.nextQuestid);
   }

   public boolean complete(EntityPlayer player, QuestData data) {
      if (this.completion == EnumQuestCompletion.Instant) {
         NoppesUtilServer.sendData(player, EnumPacketType.QuestCompletion, data.quest.writeToNBT(new NBTTagCompound()));
         return true;
      } else {
         return false;
      }
   }

   public Quest copy() {
      Quest quest = new Quest();
      quest.readNBT(this.writeToNBT(new NBTTagCompound()));
      return quest;
   }

   public int getVersion() {
      return this.version;
   }

   public void setVersion(int version) {
      this.version = version;
   }
}
