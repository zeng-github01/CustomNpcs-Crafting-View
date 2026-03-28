package noppes.npcs.client.gui.roles;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.containers.ContainerNpcItemGiver;
import noppes.npcs.roles.JobItemGiver;

public class GuiNpcItemGiver extends GuiContainerNPCInterface2 {
   private JobItemGiver role;

   public GuiNpcItemGiver(EntityNPCInterface npc, ContainerNpcItemGiver container) {
      super(npc, container);
      this.role = (JobItemGiver)npc.jobInterface;
      this.field_74195_c = 182;
      this.setBackground("npcitemgiver.png");
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addButton(new GuiNpcButton(0, this.field_74198_m + 6, this.field_74197_n + 6, 140, 20, new String[]{"Random Item", "All Items", "Give Not Owned Items", "Give When Doesnt Own Any", "Chained"}, this.role.givingMethod));
      this.addButton(new GuiNpcButton(1, this.field_74198_m + 6, this.field_74197_n + 29, 140, 20, new String[]{"Timer", "Give Only Once", "Daily"}, this.role.cooldownType));
      this.addTextField(new GuiNpcTextField(0, this, this.field_73886_k, this.field_74198_m + 55, this.field_74197_n + 54, 90, 20, this.role.cooldown + ""));
      this.getTextField(0).numbersOnly = true;
      this.addLabel(new GuiNpcLabel(0, "Cooldown:", this.field_74198_m + 6, this.field_74197_n + 59, 4210752));
      this.addLabel(new GuiNpcLabel(1, "Items to give", this.field_74198_m + 46, this.field_74197_n + 79, 4210752));
      this.getTextField(0).numbersOnly = true;
      int i = 0;

      for(String line : this.role.lines) {
         this.addTextField(new GuiNpcTextField(i + 1, this, this.field_73886_k, this.field_74198_m + 150, this.field_74197_n + 6 + i * 24, 236, 20, line));
         ++i;
      }

      while(i < 3) {
         this.addTextField(new GuiNpcTextField(i + 1, this, this.field_73886_k, this.field_74198_m + 150, this.field_74197_n + 6 + i * 24, 236, 20, ""));
         ++i;
      }

      this.getTextField(0).enabled = this.role.isOnTimer();
      this.getLabel(0).enabled = this.role.isOnTimer();
   }

   public void func_73875_a(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if (guibutton.field_73741_f == 0) {
         this.role.givingMethod = button.getValue();
      }

      if (guibutton.field_73741_f == 1) {
         this.role.cooldownType = button.getValue();
         this.getTextField(0).enabled = this.role.isOnTimer();
         this.getLabel(0).enabled = this.role.isOnTimer();
      }

   }

   public void save() {
      List<String> lines = new ArrayList();

      for(int i = 1; i < 4; ++i) {
         GuiNpcTextField tf = this.getTextField(i);
         if (!tf.isEmpty()) {
            lines.add(tf.func_73781_b());
         }
      }

      this.role.lines = lines;
      int cc = 10;
      if (!this.getTextField(0).isEmpty() && this.getTextField(0).isInteger()) {
         cc = this.getTextField(0).getInteger();
      }

      this.role.cooldown = cc;
      NoppesUtil.sendData(EnumPacketType.MainmenuAdvancedSave, this.npc.advanced.writeToNBT(new NBTTagCompound()));
   }
}
