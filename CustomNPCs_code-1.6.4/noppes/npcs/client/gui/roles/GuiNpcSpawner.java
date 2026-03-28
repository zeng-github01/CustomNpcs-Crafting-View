package noppes.npcs.client.gui.roles;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.GuiNpcMobSpawnerSelector;
import noppes.npcs.client.gui.GuiNpcMusicSelection;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.roles.JobSpawner;

public class GuiNpcSpawner extends GuiNPCInterface2 implements ITextfieldListener {
   private JobSpawner job;
   private GuiNpcMusicSelection gui;
   private int slot = -1;

   public GuiNpcSpawner(EntityNPCInterface npc) {
      super(npc);
      this.job = (JobSpawner)npc.jobInterface;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      int y = this.guiTop + 6;
      this.addButton(new GuiNpcButton(20, this.guiLeft + 25, y, 20, 20, "X"));
      this.addLabel(new GuiNpcLabel(0, "1:", this.guiLeft + 4, y + 5, 4210752));
      this.addButton(new GuiNpcButton(0, this.guiLeft + 50, y, this.getTitle(this.job.compound1)));
      y += 23;
      this.addButton(new GuiNpcButton(21, this.guiLeft + 25, y, 20, 20, "X"));
      this.addLabel(new GuiNpcLabel(1, "2:", this.guiLeft + 4, y + 5, 4210752));
      this.addButton(new GuiNpcButton(1, this.guiLeft + 50, y, this.getTitle(this.job.compound2)));
      y += 23;
      this.addButton(new GuiNpcButton(22, this.guiLeft + 25, y, 20, 20, "X"));
      this.addLabel(new GuiNpcLabel(2, "3:", this.guiLeft + 4, y + 5, 4210752));
      this.addButton(new GuiNpcButton(2, this.guiLeft + 50, y, this.getTitle(this.job.compound3)));
      y += 23;
      this.addButton(new GuiNpcButton(23, this.guiLeft + 25, y, 20, 20, "X"));
      this.addLabel(new GuiNpcLabel(3, "4:", this.guiLeft + 4, y + 5, 4210752));
      this.addButton(new GuiNpcButton(3, this.guiLeft + 50, y, this.getTitle(this.job.compound4)));
      y += 23;
      this.addButton(new GuiNpcButton(24, this.guiLeft + 25, y, 20, 20, "X"));
      this.addLabel(new GuiNpcLabel(4, "5:", this.guiLeft + 4, y + 5, 4210752));
      this.addButton(new GuiNpcButton(4, this.guiLeft + 50, y, this.getTitle(this.job.compound5)));
      y += 23;
      this.addButton(new GuiNpcButton(25, this.guiLeft + 25, y, 20, 20, "X"));
      this.addLabel(new GuiNpcLabel(5, "6:", this.guiLeft + 4, y + 5, 4210752));
      this.addButton(new GuiNpcButton(5, this.guiLeft + 50, y, this.getTitle(this.job.compound6)));
      y += 23;
      this.addLabel(new GuiNpcLabel(6, "Dies after spawning", this.guiLeft + 4, y + 5, 4210752));
      this.addButton(new GuiNpcButton(26, this.guiLeft + 75, y, 40, 20, new String[]{"gui.yes", "gui.no"}, this.job.doesntDie ? 1 : 0));
      y += 23;
      this.addLabel(new GuiNpcLabel(7, "Position Offset X:", this.guiLeft + 4, y + 5, 4210752));
      this.addTextField(new GuiNpcTextField(7, this, this.field_73886_k, this.guiLeft + 99, y, 24, 20, this.job.xOffset + ""));
      this.getTextField(7).numbersOnly = true;
      this.getTextField(7).setMinMaxDefault(-9, 9, 0);
      this.addLabel(new GuiNpcLabel(8, "Y:", this.guiLeft + 125, y + 5, 4210752));
      this.addTextField(new GuiNpcTextField(8, this, this.field_73886_k, this.guiLeft + 135, y, 24, 20, this.job.yOffset + ""));
      this.getTextField(8).numbersOnly = true;
      this.getTextField(8).setMinMaxDefault(-9, 9, 0);
      this.addLabel(new GuiNpcLabel(9, "Z:", this.guiLeft + 161, y + 5, 4210752));
      this.addTextField(new GuiNpcTextField(9, this, this.field_73886_k, this.guiLeft + 171, y, 24, 20, this.job.zOffset + ""));
      this.getTextField(9).numbersOnly = true;
      this.getTextField(9).setMinMaxDefault(-9, 9, 0);
      y += 23;
      this.addLabel(new GuiNpcLabel(10, "SpawnType", this.guiLeft + 4, y + 5, 4210752));
      this.addButton(new GuiNpcButton(10, this.guiLeft + 80, y, 100, 20, new String[]{"One by One", "All", "Random"}, this.job.spawnType));
   }

   private String getTitle(NBTTagCompound compound) {
      return compound != null && compound.func_74764_b("ClonedName") ? compound.func_74779_i("ClonedName") : "gui.selectnpc";
   }

   public void elementClicked() {
      this.gui.getSelected();
   }

   protected void func_73875_a(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if (button.field_73741_f >= 0 && button.field_73741_f < 6) {
         this.slot = button.field_73741_f + 1;
         this.setSubGui(new GuiNpcMobSpawnerSelector());
      }

      if (button.field_73741_f >= 20 && button.field_73741_f < 26) {
         this.job.setJobCompound(button.field_73741_f - 19, (NBTTagCompound)null);
         this.func_73866_w_();
      }

      if (button.field_73741_f == 26) {
         this.job.doesntDie = button.getValue() == 1;
      }

      if (button.field_73741_f == 10) {
         this.job.spawnType = button.getValue();
      }

   }

   public void closeSubGui(SubGuiInterface gui) {
      super.closeSubGui(gui);
      NBTTagCompound compound = ((GuiNpcMobSpawnerSelector)gui).getCompound();
      if (compound != null) {
         this.job.setJobCompound(this.slot, compound);
      }

      this.func_73866_w_();
   }

   public void save() {
      NoppesUtil.sendData(EnumPacketType.MainmenuAdvancedSave, this.npc.advanced.writeToNBT(new NBTTagCompound()));
   }

   public void unFocused(GuiNpcTextField textfield) {
      if (textfield.id == 7) {
         this.job.xOffset = textfield.getInteger();
      }

      if (textfield.id == 8) {
         this.job.yOffset = textfield.getInteger();
      }

      if (textfield.id == 9) {
         this.job.zOffset = textfield.getInteger();
      }

   }
}
