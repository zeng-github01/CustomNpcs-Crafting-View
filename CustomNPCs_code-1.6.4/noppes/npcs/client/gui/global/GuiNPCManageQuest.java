package noppes.npcs.client.gui.global;

import java.util.HashMap;
import java.util.Vector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.SubGuiMailmanSendSetup;
import noppes.npcs.client.gui.SubGuiNpcFactionOptions;
import noppes.npcs.client.gui.SubGuiNpcTextArea;
import noppes.npcs.client.gui.questtypes.GuiNpcQuestTypeDialog;
import noppes.npcs.client.gui.questtypes.GuiNpcQuestTypeKill;
import noppes.npcs.client.gui.questtypes.GuiNpcQuestTypeLocation;
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
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.constants.EnumQuestCompletion;
import noppes.npcs.constants.EnumQuestRepeat;
import noppes.npcs.constants.EnumQuestType;
import noppes.npcs.controllers.PlayerMail;
import noppes.npcs.controllers.Quest;
import noppes.npcs.controllers.QuestCategory;

public class GuiNPCManageQuest extends GuiNPCInterface2 implements IScrollData, ISubGuiListener, GuiSelectionListener, GuiCustomScrollActionListener, ITextfieldListener, IGuiData {
   private GuiCustomScroll scroll;
   private HashMap data = new HashMap();
   public static Quest quest = new Quest();
   private QuestCategory category = new QuestCategory();
   private boolean categorySelection = true;
   private boolean questlogTA = false;
   public static GuiScreen Instance;

   public GuiNPCManageQuest(EntityNPCInterface npc) {
      super(npc);
      Instance = this;
      NoppesUtil.sendData(EnumPacketType.QuestCategoriesGet);
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addButton(new GuiNpcButton(0, this.guiLeft + 358, this.guiTop + 8, 58, 20, this.categorySelection ? "quest.quests" : "gui.categories"));
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

      if (!this.categorySelection && quest.id >= 0) {
         this.dialogGuiInit();
      }

   }

   private void dialogGuiInit() {
      this.addLabel(new GuiNpcLabel(1, "gui.title", this.guiLeft + 4, this.guiTop + 8, 4210752));
      this.addTextField(new GuiNpcTextField(1, this, this.field_73886_k, this.guiLeft + 60, this.guiTop + 3, 140, 20, quest.title));
      this.addLabel(new GuiNpcLabel(3, "quest.completedtext", this.guiLeft + 4, this.guiTop + 30, 4210752));
      this.addButton(new GuiNpcButton(3, this.guiLeft + 120, this.guiTop + 25, 50, 20, "selectServer.edit"));
      this.addLabel(new GuiNpcLabel(4, "quest.questlogtext", this.guiLeft + 4, this.guiTop + 51, 4210752));
      this.addButton(new GuiNpcButton(4, this.guiLeft + 120, this.guiTop + 46, 50, 20, "selectServer.edit"));
      this.addLabel(new GuiNpcLabel(5, "quest.reward", this.guiLeft + 4, this.guiTop + 72, 4210752));
      this.addButton(new GuiNpcButton(5, this.guiLeft + 120, this.guiTop + 67, 50, 20, "selectServer.edit"));
      this.addLabel(new GuiNpcLabel(6, "gui.type", this.guiLeft + 4, this.guiTop + 93, 4210752));
      this.addButton(new GuiNpcButton(6, this.guiLeft + 90, this.guiTop + 88, 70, 20, new String[]{"quest.item", "quest.dialog", "quest.kill", "quest.location"}, quest.type.ordinal()));
      this.addButton(new GuiNpcButton(7, this.guiLeft + 162, this.guiTop + 88, 50, 20, "selectServer.edit"));
      this.addLabel(new GuiNpcLabel(8, "quest.repeatable", this.guiLeft + 4, this.guiTop + 114, 4210752));
      this.addButton(new GuiNpcButton(8, this.guiLeft + 110, this.guiTop + 109, 70, 20, new String[]{"gui.no", "gui.yes", "quest.daily", "quest.weekly"}, quest.repeat.ordinal()));
      this.addButton(new GuiNpcButton(9, this.guiLeft + 4, this.guiTop + 131, 90, 20, new String[]{"quest.npc", "quest.instant"}, quest.completion.ordinal()));
      if (quest.completerNpc.isEmpty()) {
         quest.completerNpc = this.npc.display.name;
      }

      this.addTextField(new GuiNpcTextField(2, this, this.field_73886_k, this.guiLeft + 96, this.guiTop + 131, 114, 20, quest.completerNpc));
      this.getTextField(2).enabled = quest.completion == EnumQuestCompletion.Npc;
      this.addLabel(new GuiNpcLabel(10, "faction.options", this.guiLeft + 4, this.guiTop + 157, 4210752));
      this.addButton(new GuiNpcButton(10, this.guiLeft + 120, this.guiTop + 152, 50, 20, "selectServer.edit"));
      this.addButton(new GuiNpcButton(13, this.guiLeft + 4, this.guiTop + 173, 164, 20, "mailbox.setup"));
      this.addButton(new GuiNpcButton(14, this.guiLeft + 170, this.guiTop + 173, 20, 20, "X"));
      if (!quest.mail.subject.isEmpty()) {
         this.getButton(13).field_73744_e = quest.mail.subject;
      }

      this.addButton(new GuiNpcButton(11, this.guiLeft + 4, this.guiTop + 194, 164, 20, "quest.next"));
      this.addButton(new GuiNpcButton(12, this.guiLeft + 170, this.guiTop + 194, 20, 20, "X"));
      if (!quest.nextQuestTitle.isEmpty()) {
         this.getButton(11).field_73744_e = quest.nextQuestTitle;
      }

   }

   private void categoryGuiInit() {
      this.addTextField(new GuiNpcTextField(0, this, this.field_73886_k, this.guiLeft + 8, this.guiTop + 8, 160, 16, this.category.title));
      this.getTextField(0).func_73804_f(20);
   }

   public void buttonEvent(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if (guibutton.field_73741_f == 0) {
         this.save();
         if (this.categorySelection) {
            if (this.category.id < 0) {
               return;
            }

            GuiNPCManageQuest.quest = new Quest();
            NoppesUtil.sendData(EnumPacketType.QuestsGet, this.category.id);
         } else if (!this.categorySelection) {
            GuiNPCManageQuest.quest = new Quest();
            this.category = new QuestCategory();
            NoppesUtil.sendData(EnumPacketType.QuestCategoriesGet);
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
            QuestCategory category = new QuestCategory();
            category.title = name;
            NoppesUtil.sendData(EnumPacketType.QuestCategorySave, category.writeNBT(new NBTTagCompound()));
         } else {
            Quest quest = new Quest();
            quest.title = name;
            NoppesUtil.sendData(EnumPacketType.QuestSave, this.category.id, quest.writeToNBT(new NBTTagCompound()));
         }
      }

      if (guibutton.field_73741_f == 2 && this.data.containsKey(this.scroll.getSelected())) {
         if (this.categorySelection) {
            NoppesUtil.sendData(EnumPacketType.QuestCategoryRemove, this.category.id);
            this.category = new QuestCategory();
         } else {
            NoppesUtil.sendData(EnumPacketType.QuestRemove, GuiNPCManageQuest.quest.id);
            GuiNPCManageQuest.quest = new Quest();
         }

         this.scroll.clear();
      }

      if (guibutton.field_73741_f == 3 && GuiNPCManageQuest.quest.id >= 0) {
         this.questlogTA = false;
         this.setSubGui(new SubGuiNpcTextArea(GuiNPCManageQuest.quest.completeText));
      }

      if (guibutton.field_73741_f == 4 && GuiNPCManageQuest.quest.id >= 0) {
         this.questlogTA = true;
         this.setSubGui(new SubGuiNpcTextArea(GuiNPCManageQuest.quest.logText));
      }

      if (guibutton.field_73741_f == 5 && GuiNPCManageQuest.quest.id >= 0) {
         NoppesUtil.sendData(EnumPacketType.QuestOpenGui, EnumGuiType.QuestReward, GuiNPCManageQuest.quest.writeToNBT(new NBTTagCompound()));
      }

      if (guibutton.field_73741_f == 6 && GuiNPCManageQuest.quest.id >= 0) {
         GuiNPCManageQuest.quest.setType(EnumQuestType.values()[button.getValue()]);
      }

      if (guibutton.field_73741_f == 7) {
         if (GuiNPCManageQuest.quest.type == EnumQuestType.Item) {
            NoppesUtil.sendData(EnumPacketType.QuestOpenGui, EnumGuiType.QuestItem, GuiNPCManageQuest.quest.writeToNBT(new NBTTagCompound()));
         }

         if (GuiNPCManageQuest.quest.type == EnumQuestType.Dialog) {
            NoppesUtil.openGUI(this.player, new GuiNpcQuestTypeDialog(this.npc, GuiNPCManageQuest.quest, this));
         }

         if (GuiNPCManageQuest.quest.type == EnumQuestType.Kill) {
            NoppesUtil.openGUI(this.player, new GuiNpcQuestTypeKill(this.npc, GuiNPCManageQuest.quest, this));
         }

         if (GuiNPCManageQuest.quest.type == EnumQuestType.Location) {
            NoppesUtil.openGUI(this.player, new GuiNpcQuestTypeLocation(this.npc, GuiNPCManageQuest.quest, this));
         }
      }

      if (guibutton.field_73741_f == 8) {
         GuiNPCManageQuest.quest.repeat = EnumQuestRepeat.values()[button.getValue()];
      }

      if (guibutton.field_73741_f == 9) {
         GuiNPCManageQuest.quest.completion = EnumQuestCompletion.values()[button.getValue()];
         this.getTextField(2).enabled = GuiNPCManageQuest.quest.completion == EnumQuestCompletion.Npc;
      }

      if (guibutton.field_73741_f == 10) {
         this.setSubGui(new SubGuiNpcFactionOptions(GuiNPCManageQuest.quest.factionOptions));
      }

      if (guibutton.field_73741_f == 11 && GuiNPCManageQuest.quest.id >= 0) {
         NoppesUtil.openGUI(this.player, new GuiNPCQuestSelection(this.npc, this, GuiNPCManageQuest.quest.nextQuestid));
      }

      if (guibutton.field_73741_f == 12 && GuiNPCManageQuest.quest.id >= 0) {
         GuiNPCManageQuest.quest.nextQuestid = -1;
         this.func_73866_w_();
      }

      if (guibutton.field_73741_f == 13) {
         this.setSubGui(new SubGuiMailmanSendSetup(GuiNPCManageQuest.quest.mail, this));
      }

      if (guibutton.field_73741_f == 14) {
         GuiNPCManageQuest.quest.mail = new PlayerMail();
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
         if (quest.id < 0) {
            guiNpcTextField.func_73782_a("");
         } else {
            String name = guiNpcTextField.func_73781_b();
            if (!name.isEmpty() && !this.data.containsKey(name)) {
               if (!this.categorySelection && quest.id >= 0) {
                  String old = quest.title;
                  this.data.remove(quest.title);
                  quest.title = name;
                  this.data.put(quest.title, quest.id);
                  this.scroll.replace(old, quest.title);
               }
            } else {
               guiNpcTextField.func_73782_a(quest.title);
            }
         }
      }

      if (guiNpcTextField.id == 2) {
         quest.completerNpc = guiNpcTextField.func_73781_b();
      }

   }

   public void setGuiData(NBTTagCompound compound) {
      if (this.categorySelection) {
         this.category.readNBT(compound);
         this.setSelected(this.category.title);
         this.func_73866_w_();
      } else {
         quest.readNBT(compound);
         this.setSelected(quest.title);
         this.func_73866_w_();
      }

   }

   public void subGuiClosed(SubGuiInterface subgui) {
      if (subgui instanceof SubGuiNpcTextArea) {
         SubGuiNpcTextArea gui = (SubGuiNpcTextArea)subgui;
         if (this.questlogTA) {
            quest.logText = gui.text;
         } else {
            quest.completeText = gui.text;
         }
      } else {
         this.func_73866_w_();
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
      quest.nextQuestid = ob;
   }

   public void customScrollClicked(int i, int j, int k, GuiCustomScroll guiCustomScroll) {
      if (guiCustomScroll.id == 0) {
         this.save();
         String selected = this.scroll.getSelected();
         if (this.categorySelection) {
            this.category = new QuestCategory();
            NoppesUtil.sendData(EnumPacketType.QuestCategoryGet, this.data.get(selected));
         } else {
            quest = new Quest();
            NoppesUtil.sendData(EnumPacketType.QuestGet, this.data.get(selected));
         }
      }

   }

   public void close() {
      super.close();
      quest = new Quest();
   }

   public void save() {
      GuiNpcTextField.unfocus();
      if (!this.categorySelection && quest.id >= 0) {
         NoppesUtil.sendData(EnumPacketType.QuestSave, this.category.id, quest.writeToNBT(new NBTTagCompound()));
      } else if (this.categorySelection && this.category.id >= 0) {
         NoppesUtil.sendData(EnumPacketType.QuestCategorySave, this.category.writeNBT(new NBTTagCompound()));
      }

   }
}
