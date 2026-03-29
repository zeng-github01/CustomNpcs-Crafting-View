package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.global.GuiNPCQuestSelection;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.GuiSelectionListener;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.constants.EnumAvailabilityDialog;
import noppes.npcs.constants.EnumAvailabilityFactionType;
import noppes.npcs.constants.EnumAvailabilityQuest;
import noppes.npcs.constants.EnumDayTime;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.controllers.Availability;
import noppes.npcs.controllers.Dialog;
import noppes.npcs.controllers.Faction;
import noppes.npcs.controllers.Quest;

public class SubGuiNpcAvailability extends SubGuiInterface implements ITextfieldListener, GuiSelectionListener, IGuiData {
   private Availability availabitily;
   private boolean selectDialog = false;
   private boolean selectFaction = false;
   private int slot = 0;
   public GuiScreen parent2;

   public SubGuiNpcAvailability(Availability availabitily) {
      this.availabitily = availabitily;
      this.setBackground("menubg.png");
      this.xSize = 256;
      this.ySize = 216;
      this.closeOnEsc = true;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addLabel(new GuiNpcLabel(1, "availability.available", this.guiLeft, this.guiTop + 4, 4210752));
      this.getLabel(1).center(this.xSize);
      this.addButton(new GuiNpcButton(0, this.guiLeft + 4, this.guiTop + 12, 50, 20, new String[]{"availability.always", "availability.after", "availability.before"}, this.availabitily.dialogAvailable.ordinal()));
      this.addButton(new GuiNpcButton(1, this.guiLeft + 56, this.guiTop + 12, 172, 20, "availability.selectdialog"));
      this.getButton(1).field_73742_g = this.availabitily.dialogAvailable != EnumAvailabilityDialog.Always;
      this.addButton(new GuiNpcButton(4, this.guiLeft + 230, this.guiTop + 12, 20, 20, "X"));
      this.addButton(new GuiNpcButton(10, this.guiLeft + 4, this.guiTop + 34, 50, 20, new String[]{"availability.always", "availability.after", "availability.before"}, this.availabitily.dialog2Available.ordinal()));
      this.addButton(new GuiNpcButton(11, this.guiLeft + 56, this.guiTop + 34, 172, 20, "availability.selectdialog"));
      this.getButton(11).field_73742_g = this.availabitily.dialog2Available != EnumAvailabilityDialog.Always;
      this.addButton(new GuiNpcButton(14, this.guiLeft + 230, this.guiTop + 34, 20, 20, "X"));
      this.addButton(new GuiNpcButton(2, this.guiLeft + 4, this.guiTop + 58, 50, 20, new String[]{"availability.always", "availability.after", "availability.before", "availability.whenactive", "availability.whennotactive"}, this.availabitily.questAvailable.ordinal()));
      this.addButton(new GuiNpcButton(3, this.guiLeft + 56, this.guiTop + 58, 172, 20, "availability.selectquest"));
      this.getButton(3).field_73742_g = this.availabitily.questAvailable != EnumAvailabilityQuest.Always;
      this.addButton(new GuiNpcButton(5, this.guiLeft + 230, this.guiTop + 58, 20, 20, "X"));
      this.addButton(new GuiNpcButton(12, this.guiLeft + 4, this.guiTop + 80, 50, 20, new String[]{"availability.always", "availability.after", "availability.before", "availability.whenactive", "availability.whennotactive"}, this.availabitily.quest2Available.ordinal()));
      this.addButton(new GuiNpcButton(13, this.guiLeft + 56, this.guiTop + 80, 172, 20, "availability.selectquest"));
      this.getButton(13).field_73742_g = this.availabitily.quest2Available != EnumAvailabilityQuest.Always;
      this.addButton(new GuiNpcButton(15, this.guiLeft + 230, this.guiTop + 80, 20, 20, "X"));
      this.addButton(new GuiNpcButton(20, this.guiLeft + 4, this.guiTop + 104, 50, 20, new String[]{"availability.always", "availability.is", "availability.isnot"}, this.availabitily.factionAvailable.ordinal()));
      this.addButton(new GuiNpcButton(22, this.guiLeft + 56, this.guiTop + 104, 60, 20, new String[]{"faction.friendly", "faction.neutral", "faction.unfriendly"}, this.availabitily.factionStance.ordinal()));
      this.addButton(new GuiNpcButton(21, this.guiLeft + 118, this.guiTop + 104, 110, 20, "availability.selectfaction"));
      this.getButton(21).field_73742_g = this.availabitily.factionAvailable != EnumAvailabilityFactionType.Always;
      this.getButton(22).field_73742_g = this.availabitily.factionAvailable != EnumAvailabilityFactionType.Always;
      this.addButton(new GuiNpcButton(23, this.guiLeft + 230, this.guiTop + 104, 20, 20, "X"));
      this.addButton(new GuiNpcButton(24, this.guiLeft + 4, this.guiTop + 126, 50, 20, new String[]{"availability.always", "availability.is", "availability.isnot"}, this.availabitily.faction2Available.ordinal()));
      this.addButton(new GuiNpcButton(27, this.guiLeft + 56, this.guiTop + 126, 60, 20, new String[]{"faction.friendly", "faction.neutral", "faction.unfriendly"}, this.availabitily.faction2Stance.ordinal()));
      this.addButton(new GuiNpcButton(25, this.guiLeft + 118, this.guiTop + 126, 110, 20, "availability.selectfaction"));
      this.getButton(25).field_73742_g = this.availabitily.faction2Available != EnumAvailabilityFactionType.Always;
      this.getButton(27).field_73742_g = this.availabitily.faction2Available != EnumAvailabilityFactionType.Always;
      this.addButton(new GuiNpcButton(26, this.guiLeft + 230, this.guiTop + 126, 20, 20, "X"));
      this.addLabel(new GuiNpcLabel(50, "availability.daytime", this.guiLeft + 4, this.guiTop + 153, 4210752));
      this.addButton(new GuiNpcButton(50, this.guiLeft + 50, this.guiTop + 148, 150, 20, new String[]{"availability.wholeday", "availability.night", "availability.day"}, this.availabitily.daytime.ordinal()));
      this.addLabel(new GuiNpcLabel(51, "availability.minlevel", this.guiLeft + 4, this.guiTop + 175, 4210752));
      this.addTextField(new GuiNpcTextField(51, this, this.field_73886_k, this.guiLeft + 50, this.guiTop + 170, 90, 20, this.availabitily.minPlayerLevel + ""));
      this.getTextField(51).numbersOnly = true;
      this.getTextField(51).setMinMaxDefault(0, 400, 0);
      this.addButton(new GuiNpcButton(66, this.guiLeft + 82, this.guiTop + 192, 98, 20, "gui.done"));
      this.updateGuiButtons();
   }

   private void updateGuiButtons() {
      this.getButton(1).setDisplayText("availability.selectdialog");
      this.getButton(11).setDisplayText("availability.selectdialog");
      this.getButton(3).setDisplayText("availability.selectquest");
      this.getButton(13).setDisplayText("availability.selectquest");
      if (this.availabitily.dialogId >= 0) {
         NoppesUtil.sendData(EnumPacketType.DialogGet, this.availabitily.dialogId);
      }

      if (this.availabitily.dialog2Id >= 0) {
         NoppesUtil.sendData(EnumPacketType.DialogGet, this.availabitily.dialog2Id);
      }

      if (this.availabitily.questId >= 0) {
         NoppesUtil.sendData(EnumPacketType.QuestGet, this.availabitily.questId);
      }

      if (this.availabitily.quest2Id >= 0) {
         NoppesUtil.sendData(EnumPacketType.QuestGet, this.availabitily.quest2Id);
      }

      if (this.availabitily.factionId >= 0) {
         NoppesUtil.sendData(EnumPacketType.FactionGet, this.availabitily.factionId);
      }

      if (this.availabitily.faction2Id >= 0) {
         NoppesUtil.sendData(EnumPacketType.FactionGet, this.availabitily.faction2Id);
      }

   }

   protected void func_73875_a(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if (guibutton.field_73741_f == 0) {
         this.availabitily.setDialogAvailability(button.getValue());
         if (this.availabitily.dialogAvailable == EnumAvailabilityDialog.Always) {
            this.availabitily.dialogId = -1;
         }

         this.func_73866_w_();
      }

      if (guibutton.field_73741_f == 1) {
         this.slot = 1;
         this.selectDialog = true;
         GuiNPCDialogSelection gui = new GuiNPCDialogSelection(this.npc, this.parent, this.availabitily.dialogId);
         gui.listener = this;
         NoppesUtil.openGUI(this.player, gui);
      }

      if (guibutton.field_73741_f == 2) {
         this.availabitily.setQuestAvailability(button.getValue());
         if (this.availabitily.questAvailable == EnumAvailabilityQuest.Always) {
            this.availabitily.questId = -1;
         }

         this.func_73866_w_();
      }

      if (guibutton.field_73741_f == 3) {
         this.slot = 1;
         GuiNPCQuestSelection gui = new GuiNPCQuestSelection(this.npc, this.parent, this.availabitily.questId);
         gui.listener = this;
         NoppesUtil.openGUI(this.player, gui);
      }

      if (guibutton.field_73741_f == 4) {
         this.availabitily.dialogId = -1;
         this.getButton(1).setDisplayText("availability.selectdialog");
      }

      if (guibutton.field_73741_f == 5) {
         this.availabitily.questId = -1;
         this.getButton(3).setDisplayText("availability.selectquest");
      }

      if (guibutton.field_73741_f == 10) {
         this.availabitily.setDialog2Availability(button.getValue());
         if (this.availabitily.dialog2Available == EnumAvailabilityDialog.Always) {
            this.availabitily.dialog2Id = -1;
         }

         this.func_73866_w_();
      }

      if (guibutton.field_73741_f == 11) {
         this.slot = 2;
         this.selectDialog = true;
         GuiNPCDialogSelection gui = new GuiNPCDialogSelection(this.npc, this.parent, this.availabitily.dialog2Id);
         gui.listener = this;
         NoppesUtil.openGUI(this.player, gui);
      }

      if (guibutton.field_73741_f == 12) {
         this.availabitily.setQuest2Availability(button.getValue());
         if (this.availabitily.quest2Available == EnumAvailabilityQuest.Always) {
            this.availabitily.quest2Id = -1;
         }

         this.func_73866_w_();
      }

      if (guibutton.field_73741_f == 13) {
         this.slot = 2;
         GuiNPCQuestSelection gui = new GuiNPCQuestSelection(this.npc, this.parent, this.availabitily.quest2Id);
         gui.listener = this;
         NoppesUtil.openGUI(this.player, gui);
      }

      if (guibutton.field_73741_f == 14) {
         this.availabitily.dialog2Id = -1;
         this.getButton(11).setDisplayText("availability.selectdialog");
      }

      if (guibutton.field_73741_f == 15) {
         this.availabitily.quest2Id = -1;
         this.getButton(13).setDisplayText("availability.selectquest");
      }

      if (guibutton.field_73741_f == 20) {
         this.availabitily.setFactionAvailability(button.getValue());
         if (this.availabitily.factionAvailable == EnumAvailabilityFactionType.Always) {
            this.availabitily.factionId = -1;
         }

         this.func_73866_w_();
      }

      if (guibutton.field_73741_f == 24) {
         this.availabitily.setFaction2Availability(button.getValue());
         if (this.availabitily.faction2Available == EnumAvailabilityFactionType.Always) {
            this.availabitily.faction2Id = -1;
         }

         this.func_73866_w_();
      }

      if (guibutton.field_73741_f == 21) {
         this.selectFaction = true;
         this.slot = 1;
         GuiNPCFactionSelection gui = new GuiNPCFactionSelection(this.npc, this.parent, this.availabitily.factionId);
         gui.listener = this;
         NoppesUtil.openGUI(this.player, gui);
      }

      if (guibutton.field_73741_f == 25) {
         this.selectFaction = true;
         this.slot = 2;
         GuiNPCFactionSelection gui = new GuiNPCFactionSelection(this.npc, this.parent, this.availabitily.faction2Id);
         gui.listener = this;
         NoppesUtil.openGUI(this.player, gui);
      }

      if (guibutton.field_73741_f == 22) {
         this.availabitily.setFactionAvailabilityStance(button.getValue());
      }

      if (guibutton.field_73741_f == 27) {
         this.availabitily.setFaction2AvailabilityStance(button.getValue());
      }

      if (guibutton.field_73741_f == 23) {
         this.availabitily.factionId = -1;
         this.getButton(21).setDisplayText("availability.selectfaction");
      }

      if (guibutton.field_73741_f == 26) {
         this.availabitily.faction2Id = -1;
         this.getButton(25).setDisplayText("availability.selectfaction");
      }

      if (guibutton.field_73741_f == 50) {
         this.availabitily.daytime = EnumDayTime.values()[button.getValue()];
      }

      if (guibutton.field_73741_f == 66) {
         this.close();
         if (this.parent2 != null) {
            NoppesUtil.openGUI(this.player, this.parent2);
         }
      }

   }

   public void selected(int id) {
      if (this.selectDialog) {
         if (this.slot == 1) {
            this.availabitily.dialogId = id;
         }

         if (this.slot == 2) {
            this.availabitily.dialog2Id = id;
         }
      } else if (this.selectFaction) {
         if (this.slot == 1) {
            this.availabitily.factionId = id;
         }

         if (this.slot == 2) {
            this.availabitily.faction2Id = id;
         }
      } else {
         if (this.slot == 1) {
            this.availabitily.questId = id;
         }

         if (this.slot == 2) {
            this.availabitily.quest2Id = id;
         }
      }

      this.selectDialog = false;
      this.selectFaction = false;
   }

   public void setGuiData(NBTTagCompound compound) {
      if (compound.func_74764_b("DialogId")) {
         Dialog dialog = new Dialog();
         dialog.readNBT(compound);
         if (this.availabitily.dialogId == dialog.id) {
            this.getButton(1).field_73744_e = dialog.title;
         }

         if (this.availabitily.dialog2Id == dialog.id) {
            this.getButton(11).field_73744_e = dialog.title;
         }
      } else if (compound.func_74764_b("Slot")) {
         Faction faction = new Faction();
         faction.readNBT(compound);
         if (this.availabitily.factionId == faction.id) {
            this.getButton(21).field_73744_e = faction.name;
         }

         if (this.availabitily.faction2Id == faction.id) {
            this.getButton(25).field_73744_e = faction.name;
         }
      } else {
         Quest quest = new Quest();
         quest.readNBT(compound);
         if (this.availabitily.questId == quest.id) {
            this.getButton(3).field_73744_e = quest.title;
         }

         if (this.availabitily.quest2Id == quest.id) {
            this.getButton(13).field_73744_e = quest.title;
         }
      }

   }

   public void unFocused(GuiNpcTextField textfield) {
      if (textfield.id == 51) {
         this.availabitily.minPlayerLevel = textfield.getInteger();
      }

   }
}
