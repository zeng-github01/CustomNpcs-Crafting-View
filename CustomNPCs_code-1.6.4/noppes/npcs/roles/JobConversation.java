package noppes.npcs.roles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.controllers.Availability;
import noppes.npcs.controllers.Line;
import noppes.npcs.controllers.PlayerQuestController;
import noppes.npcs.controllers.Quest;
import noppes.npcs.controllers.QuestController;

public class JobConversation extends JobInterface {
   public Availability availability = new Availability();
   private ArrayList names = new ArrayList();
   private HashMap npcs = new HashMap();
   public HashMap lines = new HashMap();
   public int quest = -1;
   public String questTitle = "";
   public int generalDelay = 400;
   public int ticks = 100;
   public int range = 20;
   private ConversationLine nextLine;

   public JobConversation(EntityNPCInterface npc) {
      super(npc);
   }

   public void writeEntityToNBT(NBTTagCompound compound) {
      compound.func_74766_a("ConversationAvailability", this.availability.writeToNBT(new NBTTagCompound()));
      compound.func_74768_a("ConversationQuest", this.quest);
      compound.func_74768_a("ConversationDelay", this.generalDelay);
      compound.func_74768_a("ConversationRange", this.range);
      NBTTagList nbttaglist = new NBTTagList();

      for(int slot : this.lines.keySet()) {
         ConversationLine line = (ConversationLine)this.lines.get(slot);
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         nbttagcompound.func_74768_a("Slot", slot);
         line.writeEntityToNBT(nbttagcompound);
         nbttaglist.func_74742_a(nbttagcompound);
      }

      compound.func_74782_a("ConversationLines", nbttaglist);
      if (this.hasQuest()) {
         compound.func_74778_a("ConversationQuestTitle", this.getQuest().title);
      }

   }

   public void readEntityFromNBT(NBTTagCompound compound) {
      this.names.clear();
      this.availability.readFromNBT(compound.func_74775_l("ConversationAvailability"));
      this.quest = compound.func_74762_e("ConversationQuest");
      this.generalDelay = compound.func_74762_e("ConversationDelay");
      this.questTitle = compound.func_74779_i("ConversationQuestTitle");
      this.range = compound.func_74762_e("ConversationRange");
      NBTTagList nbttaglist = compound.func_74761_m("ConversationLines");
      HashMap<Integer, ConversationLine> map = new HashMap();

      for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
         NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.func_74743_b(i);
         ConversationLine line = new ConversationLine();
         line.readEntityFromNBT(nbttagcompound);
         if (!line.npc.isEmpty() && !this.names.contains(line.npc.toLowerCase())) {
            this.names.add(line.npc.toLowerCase());
         }

         map.put(nbttagcompound.func_74762_e("Slot"), line);
      }

      this.lines = map;
      this.ticks = this.generalDelay;
   }

   public boolean hasQuest() {
      return this.getQuest() != null;
   }

   public Quest getQuest() {
      return this.npc.field_70170_p.field_72995_K ? null : (Quest)QuestController.instance.quests.get(this.quest);
   }

   public void aiUpdateTask() {
      --this.ticks;
      if (this.ticks <= 0 && this.nextLine != null) {
         this.say(this.nextLine);
         boolean seenNext = false;
         ConversationLine compare = this.nextLine;
         this.nextLine = null;

         for(ConversationLine line : this.lines.values()) {
            if (!line.isEmpty()) {
               if (seenNext) {
                  this.nextLine = line;
                  break;
               }

               if (line == compare) {
                  seenNext = true;
               }
            }
         }

         if (this.nextLine != null) {
            this.ticks = this.nextLine.delay;
         } else if (this.hasQuest()) {
            for(EntityPlayer player : this.npc.field_70170_p.func_72872_a(EntityPlayer.class, this.npc.field_70121_D.func_72314_b((double)this.range, (double)this.range, (double)this.range))) {
               if (this.availability.isAvailable(player)) {
                  PlayerQuestController.addActiveQuest(this.getQuest(), player);
               }
            }
         }

      }
   }

   public boolean aiShouldExecute() {
      if (!this.lines.isEmpty() && !this.npc.isKilled() && !this.npc.isAttacking() && this.shouldRun()) {
         for(ConversationLine line : this.lines.values()) {
            if (line != null && !line.isEmpty()) {
               this.nextLine = line;
               break;
            }
         }

         return this.nextLine != null;
      } else {
         return false;
      }
   }

   private boolean shouldRun() {
      --this.ticks;
      if (this.ticks > 0) {
         return false;
      } else {
         this.npcs.clear();

         for(EntityNPCInterface npc : this.npc.field_70170_p.func_72872_a(EntityNPCInterface.class, this.npc.field_70121_D.func_72314_b((double)10.0F, (double)20.0F, (double)10.0F))) {
            if (!npc.isKilled() && !npc.isAttacking() && this.names.contains(npc.func_70023_ak().toLowerCase())) {
               this.npcs.put(npc.func_70023_ak().toLowerCase(), npc);
            }
         }

         return this.names.size() == this.npcs.size();
      }
   }

   public boolean aiContinueExecute() {
      for(EntityNPCInterface npc : this.npcs.values()) {
         if (npc.isKilled() || npc.isAttacking()) {
            return false;
         }
      }

      return this.nextLine != null;
   }

   public void resetTask() {
      this.nextLine = null;
      this.ticks = this.generalDelay;
   }

   public void aiStartExecuting() {
   }

   private void say(ConversationLine line) {
      List<EntityPlayer> inRange = this.npc.field_70170_p.func_72872_a(EntityPlayer.class, this.npc.field_70121_D.func_72314_b((double)20.0F, (double)20.0F, (double)20.0F));
      EntityNPCInterface npc = (EntityNPCInterface)this.npcs.get(line.npc.toLowerCase());
      if (npc != null) {
         for(EntityPlayer player : inRange) {
            if (this.availability.isAvailable(player)) {
               npc.say(player, line);
            }
         }

      }
   }

   public void reset() {
      this.resetTask();
   }

   public void killed() {
      this.reset();
   }

   public ConversationLine getLine(int slot) {
      if (this.lines.containsKey(slot)) {
         return (ConversationLine)this.lines.get(slot);
      } else {
         ConversationLine line = new ConversationLine();
         this.lines.put(slot, line);
         return line;
      }
   }

   public class ConversationLine extends Line {
      public String npc = "";
      public int delay = 40;

      public void writeEntityToNBT(NBTTagCompound compound) {
         compound.func_74778_a("Line", this.text);
         compound.func_74778_a("Npc", this.npc);
         compound.func_74778_a("Sound", this.sound);
         compound.func_74768_a("Delay", this.delay);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         this.text = compound.func_74779_i("Line");
         this.npc = compound.func_74779_i("Npc");
         this.sound = compound.func_74779_i("Sound");
         this.delay = compound.func_74762_e("Delay");
      }

      public boolean isEmpty() {
         return this.npc.isEmpty() || this.text.isEmpty();
      }
   }
}
