package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.constants.EnumPacketType;

public class GuiNPCSoundsMenu extends GuiNPCInterface2 implements ITextfieldListener {
   private GuiNpcSoundSelection gui;
   private GuiNpcTextField selectedField;

   public GuiNPCSoundsMenu(EntityNPCInterface npc) {
      super(npc);
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addLabel(new GuiNpcLabel(0, "advanced.idlesound", this.guiLeft + 5, this.guiTop + 20, 4210752));
      this.addTextField(new GuiNpcTextField(0, this, this.field_73886_k, this.guiLeft + 80, this.guiTop + 15, 200, 20, this.npc.advanced.idleSound));
      this.addButton(new GuiNpcButton(0, this.guiLeft + 290, this.guiTop + 15, 80, 20, "gui.selectSound"));
      this.addLabel(new GuiNpcLabel(2, "advanced.angersound", this.guiLeft + 5, this.guiTop + 45, 4210752));
      this.addTextField(new GuiNpcTextField(2, this, this.field_73886_k, this.guiLeft + 80, this.guiTop + 40, 200, 20, this.npc.advanced.angrySound));
      this.addButton(new GuiNpcButton(2, this.guiLeft + 290, this.guiTop + 40, 80, 20, "gui.selectSound"));
      this.addLabel(new GuiNpcLabel(3, "advanced.hurtsound", this.guiLeft + 5, this.guiTop + 70, 4210752));
      this.addTextField(new GuiNpcTextField(3, this, this.field_73886_k, this.guiLeft + 80, this.guiTop + 65, 200, 20, this.npc.advanced.hurtSound));
      this.addButton(new GuiNpcButton(3, this.guiLeft + 290, this.guiTop + 65, 80, 20, "gui.selectSound"));
      this.addLabel(new GuiNpcLabel(4, "advanced.deathsound", this.guiLeft + 5, this.guiTop + 95, 4210752));
      this.addTextField(new GuiNpcTextField(4, this, this.field_73886_k, this.guiLeft + 80, this.guiTop + 90, 200, 20, this.npc.advanced.deathSound));
      this.addButton(new GuiNpcButton(4, this.guiLeft + 290, this.guiTop + 90, 80, 20, "gui.selectSound"));
      this.addLabel(new GuiNpcLabel(5, "advanced.stepsound", this.guiLeft + 5, this.guiTop + 120, 4210752));
      this.addTextField(new GuiNpcTextField(5, this, this.field_73886_k, this.guiLeft + 80, this.guiTop + 115, 200, 20, this.npc.advanced.stepSound));
      this.addButton(new GuiNpcButton(5, this.guiLeft + 290, this.guiTop + 115, 80, 20, "gui.selectSound"));
   }

   public void buttonEvent(GuiButton button) {
      this.selectedField = this.getTextField(button.field_73741_f);
      NoppesUtil.openGUI(this.player, this.gui = new GuiNpcSoundSelection(this.npc, this, this.selectedField.func_73781_b()));
   }

   public void unFocused(GuiNpcTextField textfield) {
      if (textfield.id == 0) {
         this.npc.advanced.idleSound = textfield.func_73781_b();
      }

      if (textfield.id == 2) {
         this.npc.advanced.angrySound = textfield.func_73781_b();
      }

      if (textfield.id == 3) {
         this.npc.advanced.hurtSound = textfield.func_73781_b();
      }

      if (textfield.id == 4) {
         this.npc.advanced.deathSound = textfield.func_73781_b();
      }

      if (textfield.id == 5) {
         this.npc.advanced.stepSound = textfield.func_73781_b();
      }

   }

   public void elementClicked() {
      this.selectedField.func_73782_a(this.gui.getSelected());
      this.unFocused(this.selectedField);
   }

   public void save() {
      NoppesUtil.sendData(EnumPacketType.MainmenuAdvancedSave, this.npc.advanced.writeToNBT(new NBTTagCompound()));
   }
}
