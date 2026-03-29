package noppes.npcs.controllers;

import java.util.HashMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.ICompatibilty;
import noppes.npcs.VersionCompatibility;
import noppes.npcs.constants.EnumOptionType;

public class Dialog implements ICompatibilty {
   public int version;
   public int id;
   public String title;
   public String text;
   public int quest;
   public DialogCategory category;
   public HashMap options;
   public Availability availability;
   public FactionOptions factionOptions;
   public String sound;
   public String command;
   public PlayerMail mail;

   public Dialog() {
      this.version = VersionCompatibility.ModRev;
      this.id = -1;
      this.title = "";
      this.text = "";
      this.quest = -1;
      this.options = new HashMap();
      this.availability = new Availability();
      this.factionOptions = new FactionOptions();
      this.command = "";
      this.mail = new PlayerMail();
   }

   public boolean hasDialogs(EntityPlayer player) {
      for(DialogOption option : this.options.values()) {
         if (option != null && option.optionType == EnumOptionType.DialogOption && option.hasDialog() && option.isAvailable(player)) {
            return true;
         }
      }

      return false;
   }

   public void readNBT(NBTTagCompound compound) {
      this.version = compound.func_74762_e("ModRev");
      VersionCompatibility.CheckAvailabilityCompatibility(this, compound);
      this.id = compound.func_74762_e("DialogId");
      this.title = compound.func_74779_i("DialogTitle");
      this.text = compound.func_74779_i("DialogText");
      this.quest = compound.func_74762_e("DialogQuest");
      this.sound = compound.func_74779_i("DialogSound");
      this.command = compound.func_74779_i("DialogCommand");
      this.mail.readNBT(compound.func_74775_l("DialogMail"));
      NBTTagList options = compound.func_74761_m("Options");
      HashMap<Integer, DialogOption> newoptions = new HashMap();

      for(int iii = 0; iii < options.func_74745_c(); ++iii) {
         NBTTagCompound option = (NBTTagCompound)options.func_74743_b(iii);
         int opslot = option.func_74762_e("OptionSlot");
         DialogOption dia = new DialogOption();
         dia.readNBT(option.func_74775_l("Option"));
         newoptions.put(opslot, dia);
      }

      this.options = newoptions;
      this.availability.readFromNBT(compound);
      this.factionOptions.readFromNBT(compound);
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      compound.func_74768_a("ModRev", this.version);
      compound.func_74768_a("DialogId", this.id);
      compound.func_74778_a("DialogTitle", this.title);
      compound.func_74778_a("DialogText", this.text);
      compound.func_74768_a("DialogQuest", this.quest);
      compound.func_74778_a("DialogCommand", this.command);
      compound.func_74766_a("DialogMail", this.mail.writeNBT());
      if (this.sound != null && !this.sound.isEmpty()) {
         compound.func_74778_a("DialogSound", this.sound);
      }

      NBTTagList options = new NBTTagList();

      for(int opslot : this.options.keySet()) {
         NBTTagCompound listcompound = new NBTTagCompound();
         listcompound.func_74768_a("OptionSlot", opslot);
         listcompound.func_74782_a("Option", ((DialogOption)this.options.get(opslot)).writeNBT());
         options.func_74742_a(listcompound);
      }

      compound.func_74782_a("Options", options);
      this.availability.writeToNBT(compound);
      this.factionOptions.writeToNBT(compound);
      return compound;
   }

   public boolean hasQuest() {
      return this.getQuest() != null;
   }

   public Quest getQuest() {
      return (Quest)QuestController.instance.quests.get(this.quest);
   }

   public boolean hasOtherOptions() {
      for(DialogOption option : this.options.values()) {
         if (option != null && option.optionType != EnumOptionType.Disabled) {
            return true;
         }
      }

      return false;
   }

   public Dialog copy(EntityPlayer player) {
      Dialog dialog = new Dialog();
      dialog.id = this.id;
      dialog.text = this.text;
      dialog.title = this.title;
      dialog.category = this.category;
      dialog.quest = this.quest;
      dialog.sound = this.sound;
      dialog.mail = this.mail;
      dialog.command = this.command;

      for(int slot : this.options.keySet()) {
         DialogOption option = (DialogOption)this.options.get(slot);
         if (option.optionType != EnumOptionType.DialogOption || option.hasDialog() && option.isAvailable(player)) {
            dialog.options.put(slot, option);
         }
      }

      return dialog;
   }

   public int getVersion() {
      return this.version;
   }

   public void setVersion(int version) {
      this.version = version;
   }
}
