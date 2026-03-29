package noppes.npcs.client.gui.global;

import java.util.HashMap;
import java.util.Vector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.GuiNpcMusicSelection;
import noppes.npcs.client.gui.SubGuiMailmanSendSetup;
import noppes.npcs.client.gui.SubGuiNpcAvailability;
import noppes.npcs.client.gui.SubGuiNpcCommand;
import noppes.npcs.client.gui.SubGuiNpcDialogOption;
import noppes.npcs.client.gui.SubGuiNpcDialogOptions;
import noppes.npcs.client.gui.SubGuiNpcFactionOptions;
import noppes.npcs.client.gui.SubGuiNpcTextArea;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiCustomScrollActionListener;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.GuiSelectionListener;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.client.gui.util.IScrollData;
import noppes.npcs.client.gui.util.ISubGuiListener;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.controllers.Dialog;
import noppes.npcs.controllers.DialogCategory;
import noppes.npcs.controllers.PlayerMail;

public class GuiNPCManageDialogs extends GuiNPCInterface2 implements IScrollData, ISubGuiListener, GuiSelectionListener, GuiCustomScrollActionListener, ITextfieldListener, IGuiData {
   private GuiCustomScroll scroll;
   private HashMap data = new HashMap();
   private Dialog dialog = new Dialog();
   private DialogCategory category = new DialogCategory();
   private boolean categorySelection = true;
   private GuiNpcMusicSelection gui;

   public GuiNPCManageDialogs(EntityNPCInterface npc) {
      super(npc);
      NoppesUtil.sendData(EnumPacketType.DialogCategoriesGet);
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addButton(new GuiNpcButton(0, this.guiLeft + 358, this.guiTop + 8, 58, 20, this.categorySelection ? "dialog.dialogs" : "gui.categories"));
      this.addButton(new GuiNpcButton(1, this.guiLeft + 358, this.guiTop + 38, 58, 20, "gui.add"));
      this.addButton(new GuiNpcButton(2, this.guiLeft + 358, this.guiTop + 61, 58, 20, "gui.remove"));
      if (this.scroll == null) {
         this.scroll = new GuiCustomScroll(this, 0);
         this.scroll.setSize(143, 208);
         this.scroll.guiLeft = this.guiLeft + 214;
         this.scroll.guiTop = this.guiTop + 4;
      }

      this.addScroll(this.scroll);
      if (this.categorySelection && this.category.id >= 0) {
         this.categoryGuiInit();
      }

      if (!this.categorySelection && this.dialog.id >= 0) {
         this.dialogGuiInit();
      }

   }

   private void dialogGuiInit() {
      this.addLabel(new GuiNpcLabel(1, "gui.title", this.guiLeft + 4, this.guiTop + 8, 4210752));
      this.addTextField(new GuiNpcTextField(1, this, this.field_73886_k, this.guiLeft + 60, this.guiTop + 3, 140, 20, this.dialog.title));
      this.addLabel(new GuiNpcLabel(3, "dialog.dialogtext", this.guiLeft + 4, this.guiTop + 30, 4210752));
      this.addButton(new GuiNpcButton(3, this.guiLeft + 120, this.guiTop + 25, 50, 20, "selectServer.edit"));
      this.addLabel(new GuiNpcLabel(4, "availability.options", this.guiLeft + 4, this.guiTop + 51, 4210752));
      this.addButton(new GuiNpcButton(4, this.guiLeft + 120, this.guiTop + 46, 50, 20, "selectServer.edit"));
      this.addLabel(new GuiNpcLabel(5, "faction.options", this.guiLeft + 4, this.guiTop + 72, 4210752));
      this.addButton(new GuiNpcButton(5, this.guiLeft + 120, this.guiTop + 67, 50, 20, "selectServer.edit"));
      this.addLabel(new GuiNpcLabel(6, "dialog.options", this.guiLeft + 4, this.guiTop + 93, 4210752));
      this.addButton(new GuiNpcButton(6, this.guiLeft + 120, this.guiTop + 89, 50, 20, "selectServer.edit"));
      this.addButton(new GuiNpcButton(7, this.guiLeft + 4, this.guiTop + 114, 144, 20, "availability.selectquest"));
      this.addButton(new GuiNpcButton(8, this.guiLeft + 150, this.guiTop + 114, 20, 20, "X"));
      this.addLabel(new GuiNpcLabel(9, "gui.selectSound", this.guiLeft + 4, this.guiTop + 138, 4210752));
      this.addTextField(new GuiNpcTextField(2, this, this.field_73886_k, this.guiLeft + 4, this.guiTop + 148, 144, 20, this.dialog.sound));
      this.addButton(new GuiNpcButton(9, this.guiLeft + 150, this.guiTop + 148, 60, 20, "mco.template.button.select"));
      this.addButton(new GuiNpcButton(13, this.guiLeft + 4, this.guiTop + 169, 164, 20, "mailbox.setup"));
      this.addButton(new GuiNpcButton(14, this.guiLeft + 170, this.guiTop + 169, 20, 20, "X"));
      if (!this.dialog.mail.subject.isEmpty()) {
         this.getButton(13).field_73744_e = this.dialog.mail.subject;
      }

      this.addLabel(new GuiNpcLabel(10, "advMode.command", this.guiLeft + 4, this.guiTop + 195, 4210752));
      this.addButton(new GuiNpcButton(10, this.guiLeft + 120, this.guiTop + 190, 50, 20, "selectServer.edit"));
   }

   private void categoryGuiInit() {
      this.addTextField(new GuiNpcTextField(0, this, this.field_73886_k, this.guiLeft + 8, this.guiTop + 8, 160, 16, this.category.title));
      this.getTextField(0).func_73804_f(20);
   }

   public void elementClicked() {
      this.getTextField(2).func_73782_a(this.gui.getSelected());
      this.unFocused(this.getTextField(2));
   }

   public void buttonEvent(GuiButton guibutton) {
      if (guibutton.field_73741_f == 0) {
         this.save();
         if (this.categorySelection) {
            if (this.category.id < 0) {
               return;
            }

            NoppesUtil.sendData(EnumPacketType.DialogsGet, this.category.id);
         } else if (!this.categorySelection) {
            NoppesUtil.sendData(EnumPacketType.DialogCategoriesGet);
            this.dialog = new Dialog();
            this.category = new DialogCategory();
         }

         this.categorySelection = !this.categorySelection;
         this.getButton(0).field_73742_g = false;
         this.scroll.clear();
         this.data.clear();
      }

      if (guibutton.field_73741_f == 1) {
         this.save();

         String name;
         for(name = "New"; this.data.containsKey(name); name = name + "_") {
         }

         if (this.categorySelection) {
            DialogCategory category = new DialogCategory();
            category.title = name;
            NoppesUtil.sendData(EnumPacketType.DialogCategorySave, category.writeNBT(new NBTTagCompound()));
         } else {
            Dialog dialog = new Dialog();
            dialog.title = name;
            NoppesUtil.sendData(EnumPacketType.DialogSave, this.category.id, dialog.writeToNBT(new NBTTagCompound()));
         }
      }

      if (guibutton.field_73741_f == 2 && this.data.containsKey(this.scroll.getSelected())) {
         if (this.categorySelection) {
            NoppesUtil.sendData(EnumPacketType.DialogCategoryRemove, this.category.id);
            this.category = new DialogCategory();
         } else {
            NoppesUtil.sendData(EnumPacketType.DialogRemove, this.dialog.id);
            this.dialog = new Dialog();
         }

         this.scroll.clear();
      }

      if (guibutton.field_73741_f == 3 && this.dialog.id >= 0) {
         this.setSubGui(new SubGuiNpcTextArea(this.dialog.text));
      }

      if (guibutton.field_73741_f == 4 && this.dialog.id >= 0) {
         this.setSubGui(new SubGuiNpcAvailability(this.dialog.availability));
      }

      if (guibutton.field_73741_f == 5 && this.dialog.id >= 0) {
         this.setSubGui(new SubGuiNpcFactionOptions(this.dialog.factionOptions));
      }

      if (guibutton.field_73741_f == 6 && this.dialog.id >= 0) {
         this.setSubGui(new SubGuiNpcDialogOptions(this.dialog));
      }

      if (guibutton.field_73741_f == 7 && this.dialog.id >= 0) {
         NoppesUtil.openGUI(this.player, new GuiNPCQuestSelection(this.npc, this, this.dialog.quest));
      }

      if (guibutton.field_73741_f == 8 && this.dialog.id >= 0) {
         this.dialog.quest = -1;
         this.func_73866_w_();
      }

      if (guibutton.field_73741_f == 9 && this.dialog.id >= 0) {
         NoppesUtil.openGUI(this.player, this.gui = new GuiNpcMusicSelection(this.npc, this, this.getTextField(2).func_73781_b()));
      }

      if (guibutton.field_73741_f == 10) {
         this.setSubGui(new SubGuiNpcCommand(this.dialog.command));
      }

      if (guibutton.field_73741_f == 13) {
         this.setSubGui(new SubGuiMailmanSendSetup(this.dialog.mail, this));
      }

      if (guibutton.field_73741_f == 14) {
         this.dialog.mail = new PlayerMail();
         this.func_73866_w_();
      }

   }

   public void unFocused(GuiNpcTextField guiNpcTextField) {
      if (guiNpcTextField.id == 0) {
         if (this.category.id < 0) {
            guiNpcTextField.func_73782_a("");
         } else {
            String name = guiNpcTextField.func_73781_b();
            if (!name.isEmpty() && !this.data.containsKey(name)) {
               if (this.categorySelection && this.category.id >= 0) {
                  String old = this.category.title;
                  this.data.remove(this.category.title);
                  this.category.title = name;
                  this.data.put(this.category.title, this.category.id);
                  this.scroll.replace(old, this.category.title);
               }
            } else {
               guiNpcTextField.func_73782_a(this.category.title);
            }
         }
      }

      if (guiNpcTextField.id == 1) {
         if (this.dialog.id < 0) {
            guiNpcTextField.func_73782_a("");
         } else {
            String name = guiNpcTextField.func_73781_b();
            if (!name.isEmpty() && !this.data.containsKey(name)) {
               if (!this.categorySelection && this.dialog.id >= 0) {
                  String old = this.dialog.title;
                  this.data.remove(this.dialog.title);
                  this.dialog.title = name;
                  this.data.put(this.dialog.title, this.dialog.id);
                  this.scroll.replace(old, this.dialog.title);
               }
            } else {
               guiNpcTextField.func_73782_a(this.dialog.title);
            }
         }
      }

      if (guiNpcTextField.id == 2) {
         this.dialog.sound = guiNpcTextField.func_73781_b();
      }

   }

   public void setGuiData(NBTTagCompound compound) {
      if (this.categorySelection) {
         this.category.readNBT(compound);
         this.setSelected(this.category.title);
         this.func_73866_w_();
      } else {
         this.dialog.readNBT(compound);
         this.setSelected(this.dialog.title);
         this.func_73866_w_();
         if (compound.func_74764_b("DialogQuestName")) {
            this.getButton(7).field_73744_e = compound.func_74779_i("DialogQuestName");
         }
      }

   }

   public void subGuiClosed(SubGuiInterface subgui) {
      if (subgui instanceof SubGuiNpcTextArea) {
         SubGuiNpcTextArea gui = (SubGuiNpcTextArea)subgui;
         this.dialog.text = gui.text;
      }

      if (subgui instanceof SubGuiNpcDialogOption) {
         this.setSubGui(new SubGuiNpcDialogOptions(this.dialog));
      }

      if (subgui instanceof SubGuiNpcCommand) {
         this.dialog.command = ((SubGuiNpcCommand)subgui).command;
      }

   }

   public void setData(Vector list, HashMap data) {
      this.getButton(0).field_73742_g = true;
      String name = this.scroll.getSelected();
      this.data = data;
      this.scroll.setList(list);
      if (name != null) {
         this.scroll.setSelected(name);
      }

      this.func_73866_w_();
   }

   public void setSelected(String selected) {
   }

   public void selected(int ob) {
      this.dialog.quest = ob;
      NoppesUtil.sendData(EnumPacketType.DialogSave, this.category.id, this.dialog.writeToNBT(new NBTTagCompound()));
      NoppesUtil.sendData(EnumPacketType.DialogGet, this.dialog.id);
   }

   public void customScrollClicked(int i, int j, int k, GuiCustomScroll guiCustomScroll) {
      if (guiCustomScroll.id == 0) {
         this.save();
         String selected = this.scroll.getSelected();
         if (this.categorySelection) {
            this.category = new DialogCategory();
            NoppesUtil.sendData(EnumPacketType.DialogCategoryGet, this.data.get(selected));
         } else {
            this.dialog = new Dialog();
            NoppesUtil.sendData(EnumPacketType.DialogGet, this.data.get(selected));
         }
      }

   }

   public void save() {
      GuiNpcTextField.unfocus();
      if (!this.categorySelection && this.dialog.id >= 0) {
         NoppesUtil.sendData(EnumPacketType.DialogSave, this.category.id, this.dialog.writeToNBT(new NBTTagCompound()));
      } else if (this.categorySelection && this.category.id >= 0) {
         NoppesUtil.sendData(EnumPacketType.DialogCategorySave, this.category.writeNBT(new NBTTagCompound()));
      }

   }
}
