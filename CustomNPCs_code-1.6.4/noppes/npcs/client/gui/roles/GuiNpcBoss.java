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
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.roles.JobBoss;

public class GuiNpcBoss extends GuiNPCInterface2 {
   private JobBoss job;
   private GuiNpcMusicSelection gui;
   private int slot = -1;

   public GuiNpcBoss(EntityNPCInterface npc) {
      super(npc);
      this.job = (JobBoss)npc.jobInterface;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addLabel(new GuiNpcLabel(40, "Show name", this.guiLeft + 300, this.guiTop + 25, 4210752));
      this.addButton(new GuiNpcButton(40, this.guiLeft + 355, this.guiTop + 20, 40, 20, new String[]{"gui.no", "gui.yes"}, this.job.hideName ? 0 : 1));
      int y = 6;
      this.addButton(new GuiNpcButton(20, this.guiLeft + 55, this.guiTop + y, 20, 20, "X"));
      this.addLabel(new GuiNpcLabel(0, "90%", this.guiLeft + 4, this.guiTop + y + 5, 4210752));
      this.addButton(new GuiNpcButton(0, this.guiLeft + 80, this.guiTop + y, this.getTitle(this.job.compound9)));
      y += 23;
      this.addButton(new GuiNpcButton(21, this.guiLeft + 55, this.guiTop + y, 20, 20, "X"));
      this.addLabel(new GuiNpcLabel(1, "80%", this.guiLeft + 4, this.guiTop + y + 5, 4210752));
      this.addButton(new GuiNpcButton(1, this.guiLeft + 80, this.guiTop + y, this.getTitle(this.job.compound8)));
      y += 23;
      this.addButton(new GuiNpcButton(22, this.guiLeft + 55, this.guiTop + y, 20, 20, "X"));
      this.addLabel(new GuiNpcLabel(2, "70%", this.guiLeft + 4, this.guiTop + y + 5, 4210752));
      this.addButton(new GuiNpcButton(2, this.guiLeft + 80, this.guiTop + y, this.getTitle(this.job.compound7)));
      y += 23;
      this.addButton(new GuiNpcButton(23, this.guiLeft + 55, this.guiTop + y, 20, 20, "X"));
      this.addLabel(new GuiNpcLabel(3, "60%", this.guiLeft + 4, this.guiTop + y + 5, 4210752));
      this.addButton(new GuiNpcButton(3, this.guiLeft + 80, this.guiTop + y, this.getTitle(this.job.compound6)));
      y += 23;
      this.addButton(new GuiNpcButton(24, this.guiLeft + 55, this.guiTop + y, 20, 20, "X"));
      this.addLabel(new GuiNpcLabel(4, "50%", this.guiLeft + 4, this.guiTop + y + 5, 4210752));
      this.addButton(new GuiNpcButton(4, this.guiLeft + 80, this.guiTop + y, this.getTitle(this.job.compound5)));
      y += 23;
      this.addButton(new GuiNpcButton(25, this.guiLeft + 55, this.guiTop + y, 20, 20, "X"));
      this.addLabel(new GuiNpcLabel(5, "40%", this.guiLeft + 4, this.guiTop + y + 5, 4210752));
      this.addButton(new GuiNpcButton(5, this.guiLeft + 80, this.guiTop + y, this.getTitle(this.job.compound4)));
      y += 23;
      this.addButton(new GuiNpcButton(26, this.guiLeft + 55, this.guiTop + y, 20, 20, "X"));
      this.addLabel(new GuiNpcLabel(6, "30%", this.guiLeft + 4, this.guiTop + y + 5, 4210752));
      this.addButton(new GuiNpcButton(6, this.guiLeft + 80, this.guiTop + y, this.getTitle(this.job.compound3)));
      y += 23;
      this.addButton(new GuiNpcButton(27, this.guiLeft + 55, this.guiTop + y, 20, 20, "X"));
      this.addLabel(new GuiNpcLabel(7, "20%", this.guiLeft + 4, this.guiTop + y + 5, 4210752));
      this.addButton(new GuiNpcButton(7, this.guiLeft + 80, this.guiTop + y, this.getTitle(this.job.compound2)));
      y += 23;
      this.addButton(new GuiNpcButton(28, this.guiLeft + 55, this.guiTop + y, 20, 20, "X"));
      this.addLabel(new GuiNpcLabel(8, "10%", this.guiLeft + 4, this.guiTop + y + 5, 4210752));
      this.addButton(new GuiNpcButton(8, this.guiLeft + 80, this.guiTop + y, this.getTitle(this.job.compound1)));
   }

   private String getTitle(NBTTagCompound compound) {
      return compound != null && compound.func_74764_b("ClonedName") ? compound.func_74779_i("ClonedName") : "gui.selectnpc";
   }

   public void elementClicked() {
      this.gui.getSelected();
   }

   protected void func_73875_a(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if (button.field_73741_f >= 0 && button.field_73741_f < 9) {
         this.slot = 9 - button.field_73741_f;
         this.setSubGui(new GuiNpcMobSpawnerSelector());
      }

      if (button.field_73741_f >= 20 && button.field_73741_f < 29) {
         this.job.setNBT(29 - button.field_73741_f, (NBTTagCompound)null);
         this.func_73866_w_();
      }

      if (button.field_73741_f == 40) {
         this.job.hideName = button.getValue() == 0;
      }

   }

   public void closeSubGui(SubGuiInterface gui) {
      super.closeSubGui(gui);
      NBTTagCompound compound = ((GuiNpcMobSpawnerSelector)gui).getCompound();
      if (compound != null) {
         this.job.setNBT(this.slot, compound);
      }

      this.func_73866_w_();
   }

   public void save() {
      NoppesUtil.sendData(EnumPacketType.MainmenuAdvancedSave, this.npc.advanced.writeToNBT(new NBTTagCompound()));
   }
}
