package noppes.npcs.client.gui.mainmenu;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.DataAI;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.SubGuiNpcMovement;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.constants.EnumNavType;
import noppes.npcs.constants.EnumPacketType;

public class GuiNpcAI extends GuiNPCInterface2 implements ITextfieldListener, IGuiData {
   private DataAI ai;

   public GuiNpcAI(EntityNPCInterface npc) {
      super(npc, 6);
      this.ai = npc.ai;
      NoppesUtil.sendData(EnumPacketType.MainmenuAIGet);
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addLabel(new GuiNpcLabel(0, "ai.enemyresponse", this.guiLeft + 5, this.guiTop + 17, 4210752));
      this.addButton(new GuiNpcButton(0, this.guiLeft + 86, this.guiTop + 10, 60, 20, new String[]{"gui.retaliate", "gui.panic", "gui.retreat", "gui.nothing"}, this.npc.ai.onAttack));
      this.addLabel(new GuiNpcLabel(1, "ai.door", this.guiLeft + 5, this.guiTop + 40, 4210752));
      this.addButton(new GuiNpcButton(1, this.guiLeft + 86, this.guiTop + 35, 60, 20, new String[]{"gui.break", "gui.open", "gui.disabled"}, this.npc.ai.doorInteract));
      this.addLabel(new GuiNpcLabel(12, "ai.swim", this.guiLeft + 5, this.guiTop + 65, 4210752));
      this.addButton(new GuiNpcButton(7, this.guiLeft + 86, this.guiTop + 60, 60, 20, new String[]{"gui.no", "gui.yes"}, this.npc.ai.canSwim ? 1 : 0));
      this.addLabel(new GuiNpcLabel(13, "ai.shelter", this.guiLeft + 5, this.guiTop + 90, 4210752));
      this.addButton(new GuiNpcButton(9, this.guiLeft + 86, this.guiTop + 85, 60, 20, new String[]{"gui.darkness", "gui.sunlight", "gui.disabled"}, this.npc.ai.findShelter));
      this.addLabel(new GuiNpcLabel(14, "ai.clearlos", this.guiLeft + 5, this.guiTop + 115, 4210752));
      this.addButton(new GuiNpcButton(10, this.guiLeft + 86, this.guiTop + 110, 60, 20, new String[]{"gui.no", "gui.yes"}, this.npc.ai.directLOS ? 1 : 0));
      this.addLabel(new GuiNpcLabel(18, "ai.sprint", this.guiLeft + 5, this.guiTop + 140, 4210752));
      this.addButton(new GuiNpcButton(16, this.guiLeft + 86, this.guiTop + 135, 60, 20, new String[]{"gui.no", "gui.yes"}, this.npc.ai.canSprint ? 1 : 0));
      this.addLabel(new GuiNpcLabel(10, "ai.avoidwater", this.guiLeft + 150, this.guiTop + 17, 4210752));
      this.addButton(new GuiNpcButton(5, this.guiLeft + 230, this.guiTop + 10, 60, 20, new String[]{"gui.no", "gui.yes"}, this.npc.func_70661_as().func_75486_a() ? 1 : 0));
      this.addLabel(new GuiNpcLabel(11, "ai.return", this.guiLeft + 150, this.guiTop + 40, 4210752));
      this.addButton(new GuiNpcButton(6, this.guiLeft + 230, this.guiTop + 35, 60, 20, new String[]{"gui.no", "gui.yes"}, this.npc.ai.returnToStart ? 1 : 0));
      this.addLabel(new GuiNpcLabel(17, "ai.leapattarget", this.guiLeft + 150, this.guiTop + 65, 4210752));
      this.addButton(new GuiNpcButton(15, this.guiLeft + 230, this.guiTop + 60, 60, 20, new String[]{"gui.no", "gui.yes"}, this.npc.ai.canLeap ? 1 : 0));
      this.addLabel(new GuiNpcLabel(15, "ai.indirect", this.guiLeft + 150, this.guiTop + 90, 4210752));
      this.addButton(new GuiNpcButton(13, this.guiLeft + 230, this.guiTop + 85, 60, 20, new String[]{"gui.no", "gui.yes"}, this.ai.canFireIndirect ? 1 : 0));
      this.addLabel(new GuiNpcLabel(16, "ai.rangemelee", this.guiLeft + 150, this.guiTop + 115, 4210752));
      this.addButton(new GuiNpcButton(14, this.guiLeft + 230, this.guiTop + 110, 60, 20, new String[]{"gui.always", "gui.untilclose", "gui.whenavailable"}, this.ai.useRangeMelee));
      if (this.ai.useRangeMelee == 1) {
         this.addLabel(new GuiNpcLabel(20, "gui.engagedistance", this.guiLeft + 300, this.guiTop + 115, 4210752));
         this.addTextField(new GuiNpcTextField(6, this, this.field_73886_k, this.guiLeft + 380, this.guiTop + 110, 30, 20, this.ai.distanceToMelee + ""));
         this.getTextField(6).numbersOnly = true;
         this.getTextField(6).setMinMaxDefault(1, this.npc.stats.aggroRange, 5);
      }

      this.addLabel(new GuiNpcLabel(19, "ai.tacticalvariant", this.guiLeft + 150, this.guiTop + 140, 4210752));
      this.addButton(new GuiNpcButton(17, this.guiLeft + 230, this.guiTop + 135, 60, 20, EnumNavType.names(), this.ai.tacticalVariant.ordinal()));
      if (this.ai.tacticalVariant != EnumNavType.Default) {
         this.addLabel(new GuiNpcLabel(21, this.ai.tacticalVariant == EnumNavType.Surround ? "gui.orbitdistance" : "gui.engagedistance", this.guiLeft + 300, this.guiTop + 140, 4210752));
         this.addTextField(new GuiNpcTextField(3, this, this.field_73886_k, this.guiLeft + 380, this.guiTop + 135, 30, 20, this.ai.tacticalRadius + ""));
         this.getTextField(3).numbersOnly = true;
         this.getTextField(3).setMinMaxDefault(1, this.npc.stats.aggroRange, 5);
      }

      this.getButton(17).field_73742_g = this.ai.onAttack == 0;
      this.getButton(15).field_73742_g = this.ai.onAttack == 0;
      this.getButton(13).field_73742_g = this.npc.inventory.getProjectile() != null;
      this.getButton(14).field_73742_g = this.npc.inventory.getProjectile() != null;
      this.getButton(10).field_73742_g = this.npc.ai.tacticalVariant != EnumNavType.Stalk;
      this.addLabel(new GuiNpcLabel(2, "Movement", this.guiLeft + 4, this.guiTop + 165, 4210752));
      this.addButton(new GuiNpcButton(2, this.guiLeft + 86, this.guiTop + 160, 60, 20, "selectServer.edit"));
   }

   public void unFocused(GuiNpcTextField textfield) {
      if (textfield.id == 3) {
         this.ai.tacticalRadius = textfield.getInteger();
      }

   }

   protected void func_73875_a(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if (button.field_73741_f == 0) {
         this.npc.ai.onAttack = button.getValue();
         this.func_73866_w_();
      } else if (button.field_73741_f == 1) {
         this.npc.ai.doorInteract = button.getValue();
      } else if (button.field_73741_f == 2) {
         this.setSubGui(new SubGuiNpcMovement(this.ai));
      } else if (button.field_73741_f == 5) {
         this.npc.setAvoidWater(button.getValue() == 1);
      } else if (button.field_73741_f == 6) {
         this.npc.ai.returnToStart = button.getValue() == 1;
      } else if (button.field_73741_f == 7) {
         this.npc.ai.canSwim = button.getValue() == 1;
      } else if (button.field_73741_f == 9) {
         this.npc.ai.findShelter = button.getValue();
      } else if (button.field_73741_f == 10) {
         this.npc.ai.directLOS = button.getValue() == 1;
      } else if (button.field_73741_f == 11) {
         this.npc.ai.canSleep = button.getValue() == 1;
      } else if (button.field_73741_f == 13) {
         this.npc.ai.canFireIndirect = button.getValue() == 1;
      } else if (button.field_73741_f == 14) {
         this.npc.ai.useRangeMelee = button.getValue();
         this.func_73866_w_();
      } else if (button.field_73741_f == 15) {
         this.npc.ai.canLeap = button.getValue() == 1;
      } else if (button.field_73741_f == 16) {
         this.npc.ai.canSprint = button.getValue() == 1;
      } else if (button.field_73741_f == 17) {
         this.npc.ai.tacticalVariant = EnumNavType.values()[button.getValue()];
         this.ai.directLOS = EnumNavType.values()[button.getValue()] != EnumNavType.Stalk;
         this.func_73866_w_();
      }

   }

   public void save() {
      NoppesUtil.sendData(EnumPacketType.MainmenuAISave, this.ai.writeToNBT(new NBTTagCompound()));
   }

   public void setGuiData(NBTTagCompound compound) {
      this.ai.readToNBT(compound);
      this.func_73866_w_();
   }
}
