package noppes.npcs.controllers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.constants.EnumOptionType;

public class DialogOption {
   public int dialogId = -1;
   public String title = "Talk";
   public EnumOptionType optionType;
   public int optionColor;
   public String command;

   public DialogOption() {
      this.optionType = EnumOptionType.Disabled;
      this.optionColor = 14737632;
      this.command = "";
   }

   public void readNBT(NBTTagCompound compound) {
      if (compound != null) {
         this.title = compound.func_74779_i("Title");
         this.dialogId = compound.func_74762_e("Dialog");
         this.optionColor = compound.func_74762_e("DialogColor");
         this.optionType = EnumOptionType.values()[compound.func_74762_e("OptionType")];
         this.command = compound.func_74779_i("DialogCommand");
         if (this.optionColor == 0) {
            this.optionColor = 14737632;
         }

      }
   }

   public NBTTagCompound writeNBT() {
      NBTTagCompound compound = new NBTTagCompound();
      compound.func_74778_a("Title", this.title);
      compound.func_74768_a("OptionType", this.optionType.ordinal());
      compound.func_74768_a("Dialog", this.dialogId);
      compound.func_74768_a("DialogColor", this.optionColor);
      compound.func_74778_a("DialogCommand", this.command);
      return compound;
   }

   public boolean hasDialog() {
      if (this.dialogId <= 0) {
         return false;
      } else if (!DialogController.instance.hasDialog(this.dialogId)) {
         this.dialogId = -1;
         return false;
      } else {
         return true;
      }
   }

   public Dialog getDialog() {
      return !this.hasDialog() ? null : (Dialog)DialogController.instance.dialogs.get(this.dialogId);
   }

   public boolean isAvailable(EntityPlayer player) {
      Dialog dialog = this.getDialog();
      return dialog == null ? false : dialog.availability.isAvailable(player);
   }
}
