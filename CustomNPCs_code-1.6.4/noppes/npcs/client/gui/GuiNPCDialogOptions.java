package noppes.npcs.client.gui;

import java.util.HashMap;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.GuiSelectionListener;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.constants.EnumOptionType;
import noppes.npcs.controllers.Dialog;
import noppes.npcs.controllers.DialogOption;

public class GuiNPCDialogOptions extends GuiNPCInterface implements ITextfieldListener, GuiSelectionListener {
   private GuiScreen parent;
   private Dialog dialog;
   public HashMap options = new HashMap();
   private int selectedSlot;

   public GuiNPCDialogOptions(EntityNPCInterface npc, GuiScreen parent, Dialog dialog) {
      super(npc);
      this.parent = parent;
      this.dialog = dialog;
      this.options = dialog.options;
      this.title = "";
      this.drawDefaultBackground = false;
   }

   public void func_73866_w_() {
      super.func_73866_w_();

      for(int i = 0; i < 6; ++i) {
         if (!this.options.containsKey(i)) {
            this.options.put(i, new DialogOption());
         }
      }

      for(int i = 0; i < 6; ++i) {
         DialogOption option = (DialogOption)this.options.get(i);
         int y = 6 + i % 3 * 70;
         int x = (this.field_73880_f - 420) / 4 + this.field_73880_f / 2 + 1;
         if (i > 2) {
            x -= this.field_73880_f / 2;
         }

         this.addButton(new GuiNpcButton(i, x, y, 92, 20, new String[]{"dialog.quitoption", "dialog.option", " gui.disabled", "dialog.roleoption", "dialogcommandoption"}, option.optionType.ordinal()));
         this.addTextField(new GuiNpcTextField(i, this, this.field_73886_k, x + 2, y + 22, 196, 20, option.title));
         this.addButton(new GuiNpcButton(i + 6, x, y + 44, 196, 20, "dialog.selectoption"));

         String color;
         for(color = Integer.toHexString(option.optionColor); color.length() < 6; color = 0 + color) {
         }

         this.addLabel(new GuiNpcLabel(i, "gui.color", x + 98, y + 4, 16777215));
         this.addTextField(new GuiNpcTextField(i + 6, this, this.field_73886_k, x + 130, y, 45, 20, color));
         this.addTextField(new GuiNpcTextField(i + 12, this, this.field_73886_k, x + 2, y + 44, 196, 20, option.command));
         this.getTextField(i + 12).enabled = option.optionType == EnumOptionType.CommandBlock;
         this.getButton(i + 6).field_73742_g = this.getButton(i + 6).field_73748_h = option.optionType == EnumOptionType.DialogOption;
         this.getTextField(i).enabled = option.optionType != EnumOptionType.Disabled;
         this.getTextField(i + 6).enabled = option.optionType != EnumOptionType.Disabled;
         this.getLabel(i).enabled = option.optionType != EnumOptionType.Disabled;
      }

      this.addButton(new GuiNpcButton(66, this.field_73880_f / 2 - 49, 215, 98, 20, "gui.back"));
   }

   public void func_73863_a(int i, int j, float f) {
      this.func_73873_v_();
      this.func_73728_b(this.field_73880_f / 2, 0, this.field_73881_g, -1);
      this.func_73730_a(0, this.field_73880_f, 74, -1);
      this.func_73730_a(0, this.field_73880_f, 144, -1);
      super.func_73863_a(i, j, f);
   }

   protected void func_73875_a(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if (guibutton.field_73741_f >= 0 && guibutton.field_73741_f < 6) {
         DialogOption option = (DialogOption)this.options.get(guibutton.field_73741_f);
         option.optionType = EnumOptionType.values()[button.getValue()];
         this.func_73866_w_();
      }

      if (guibutton.field_73741_f >= 6 && guibutton.field_73741_f < 12) {
         this.save();
         this.selectedSlot = guibutton.field_73741_f - 6;
         DialogOption option = (DialogOption)this.options.get(this.selectedSlot);
         int id = -1;
         if (option != null) {
            id = option.dialogId;
         }

         NoppesUtil.openGUI(this.player, new GuiNPCDialogSelection(this.npc, this, id));
      }

      if (guibutton.field_73741_f == 66) {
         this.save();
         NoppesUtil.openGUI(this.player, this.parent);
      }

   }

   public void unFocused(GuiNpcTextField textfield) {
      if (textfield.id >= 0 && textfield.id < 6) {
         DialogOption option = (DialogOption)this.options.get(textfield.id);
         if (textfield.isEmpty()) {
            option.optionType = EnumOptionType.Disabled;
            this.func_73866_w_();
         } else {
            option.title = textfield.func_73781_b();
         }
      }

      if (textfield.id >= 6 && textfield.id < 12) {
         DialogOption option = (DialogOption)this.options.get(textfield.id - 6);
         int color = 14737632;

         try {
            color = Integer.parseInt(textfield.func_73781_b(), 16);
         } catch (NumberFormatException var5) {
            color = 14737632;
         }

         option.optionColor = color;

         String colors;
         for(colors = Integer.toHexString(option.optionColor); colors.length() < 6; colors = 0 + colors) {
         }

         textfield.func_73782_a(colors);
      }

      if (textfield.id >= 12 && textfield.id < 18) {
         DialogOption option = (DialogOption)this.options.get(textfield.id - 12);
         option.command = textfield.func_73781_b();
      }

   }

   public void save() {
      this.dialog.options = this.options;
   }

   public void selected(int id) {
      if (!this.options.containsKey(this.selectedSlot)) {
         this.options.put(this.selectedSlot, new DialogOption());
      }

      DialogOption option = (DialogOption)this.options.get(this.selectedSlot);
      option.dialogId = id;
   }
}
