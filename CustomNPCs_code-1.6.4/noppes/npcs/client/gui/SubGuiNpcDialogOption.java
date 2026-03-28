package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.GuiSelectionListener;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.constants.EnumOptionType;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.controllers.Dialog;
import noppes.npcs.controllers.DialogOption;

public class SubGuiNpcDialogOption extends SubGuiInterface implements IGuiData, ITextfieldListener, GuiSelectionListener {
   private DialogOption option;

   public SubGuiNpcDialogOption(DialogOption option) {
      this.option = option;
      this.setBackground("menubg.png");
      this.xSize = 256;
      this.ySize = 216;
      this.closeOnEsc = true;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addLabel(new GuiNpcLabel(66, "dialog.editoption", this.guiLeft, this.guiTop + 4, 4210752));
      this.getLabel(66).center(this.xSize);
      this.addLabel(new GuiNpcLabel(0, "gui.title", this.guiLeft + 4, this.guiTop + 20, 4210752));
      this.addTextField(new GuiNpcTextField(0, this, this.field_73886_k, this.guiLeft + 40, this.guiTop + 15, 196, 20, this.option.title));

      String color;
      for(color = Integer.toHexString(this.option.optionColor); color.length() < 6; color = 0 + color) {
      }

      this.addLabel(new GuiNpcLabel(2, "gui.color", this.guiLeft + 4, this.guiTop + 45, 4210752));
      this.addTextField(new GuiNpcTextField(2, this, this.field_73886_k, this.guiLeft + 62, this.guiTop + 40, 92, 20, color));
      this.getTextField(2).func_73794_g(this.option.optionColor);
      this.addLabel(new GuiNpcLabel(1, "dialog.optiontype", this.guiLeft + 4, this.guiTop + 67, 4210752));
      this.addButton(new GuiNpcButton(1, this.guiLeft + 62, this.guiTop + 62, 92, 20, new String[]{"gui.close", "dialog.dialog", "gui.disabled", "menu.role", "tile.commandBlock.name"}, this.option.optionType.ordinal()));
      if (this.option.optionType == EnumOptionType.DialogOption) {
         this.addButton(new GuiNpcButton(3, this.guiLeft + 4, this.guiTop + 84, "availability.selectdialog"));
         if (this.option.dialogId >= 0) {
            NoppesUtil.sendData(EnumPacketType.DialogGet, this.option.dialogId);
         }
      }

      if (this.option.optionType == EnumOptionType.CommandBlock) {
         this.addTextField(new GuiNpcTextField(4, this, this.field_73886_k, this.guiLeft + 4, this.guiTop + 84, 248, 20, this.option.command));
         this.getTextField(4).func_73804_f(32767);
         this.addLabel(new GuiNpcLabel(4, "advMode.command", this.guiLeft + 4, this.guiTop + 110, 4210752));
         this.addLabel(new GuiNpcLabel(5, "advMode.nearestPlayer", this.guiLeft + 4, this.guiTop + 125, 4210752));
         this.addLabel(new GuiNpcLabel(6, "advMode.randomPlayer", this.guiLeft + 4, this.guiTop + 140, 4210752));
         this.addLabel(new GuiNpcLabel(7, "advMode.allPlayers", this.guiLeft + 4, this.guiTop + 155, 4210752));
         this.addLabel(new GuiNpcLabel(8, "dialogcommandoptionplayer", this.guiLeft + 4, this.guiTop + 170, 4210752));
      }

      this.addButton(new GuiNpcButton(66, this.guiLeft + 82, this.guiTop + 190, 98, 20, "gui.done"));
   }

   protected void func_73875_a(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if (button.field_73741_f == 1) {
         this.option.optionType = EnumOptionType.values()[button.getValue()];
         this.func_73866_w_();
      }

      if (button.field_73741_f == 3) {
         GuiNPCDialogSelection gui = new GuiNPCDialogSelection(this.npc, this.parent, this.option.dialogId);
         gui.listener = this;
         NoppesUtil.openGUI(this.player, gui);
      }

      if (guibutton.field_73741_f == 66) {
         this.close();
      }

   }

   public void unFocused(GuiNpcTextField textfield) {
      if (textfield.id == 0) {
         if (textfield.isEmpty()) {
            textfield.func_73782_a(this.option.title);
         } else {
            this.option.title = textfield.func_73781_b();
         }
      }

      if (textfield.id == 2) {
         int color = 14737632;

         try {
            color = Integer.parseInt(textfield.func_73781_b(), 16);
         } catch (NumberFormatException var4) {
            color = 14737632;
         }

         this.option.optionColor = color;

         String colors;
         for(colors = Integer.toHexString(this.option.optionColor); colors.length() < 6; colors = 0 + colors) {
         }

         textfield.func_73782_a(colors);
         textfield.func_73794_g(color);
      }

      if (textfield.id == 4) {
         this.option.command = textfield.func_73781_b();
      }

   }

   public void setGuiData(NBTTagCompound compound) {
      if (compound.func_74764_b("DialogId")) {
         Dialog dialog = new Dialog();
         dialog.readNBT(compound);
         this.option.dialogId = dialog.id;
         if (this.getButton(3) != null) {
            this.getButton(3).field_73744_e = dialog.title;
         }
      }

   }

   public void selected(int ob) {
      this.option.dialogId = ob;
   }
}
