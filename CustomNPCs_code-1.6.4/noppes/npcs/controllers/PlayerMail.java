package noppes.npcs.controllers;

import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.NpcMiscInventory;

public class PlayerMail {
   public NpcMiscInventory items = new NpcMiscInventory(9);
   public String subject = "";
   public String sender = "";
   public NBTTagCompound message = new NBTTagCompound();
   public long time = 0L;
   public boolean beenRead = false;
   public int questId = -1;
   public String questTitle = "";
   public long timePast;

   public void readNBT(NBTTagCompound compound) {
      this.items.setFromNBT(compound.func_74775_l("Items"));
      this.subject = compound.func_74779_i("Subject");
      this.sender = compound.func_74779_i("Sender");
      this.time = compound.func_74763_f("Time");
      this.beenRead = compound.func_74767_n("BeenRead");
      this.message = compound.func_74775_l("Message");
      this.timePast = compound.func_74763_f("TimePast");
      if (compound.func_74764_b("MailQuest")) {
         this.questId = compound.func_74762_e("MailQuest");
      }

      this.questTitle = compound.func_74779_i("MailQuestTitle");
   }

   public NBTTagCompound writeNBT() {
      NBTTagCompound compound = new NBTTagCompound();
      compound.func_74766_a("Items", this.items.getToNBT());
      compound.func_74778_a("Subject", this.subject);
      compound.func_74778_a("Sender", this.sender);
      compound.func_74772_a("Time", this.time);
      compound.func_74757_a("BeenRead", this.beenRead);
      compound.func_74766_a("Message", this.message);
      compound.func_74772_a("TimePast", System.currentTimeMillis() - this.time);
      compound.func_74768_a("MailQuest", this.questId);
      if (this.hasQuest()) {
         compound.func_74778_a("MailQuestTitle", this.getQuest().title);
      }

      return compound;
   }

   public boolean isValid() {
      return !this.subject.isEmpty() && !this.message.func_82582_d() && !this.sender.isEmpty();
   }

   public boolean hasQuest() {
      return this.getQuest() != null;
   }

   public Quest getQuest() {
      return QuestController.instance != null ? (Quest)QuestController.instance.quests.get(this.questId) : null;
   }
}
