package noppes.npcs.client.gui.roles;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.roles.JobGuard;

public class GuiNpcGuard extends GuiNPCInterface2 {
   private JobGuard role;

   public GuiNpcGuard(EntityNPCInterface npc) {
      super(npc);
      this.role = (JobGuard)npc.jobInterface;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addButton(new GuiNpcButton(0, this.guiLeft + 85, this.guiTop + 20, new String[]{"Dont Attack Animals", "Attack Animals"}, this.role.attacksAnimals ? 1 : 0));
      this.addButton(new GuiNpcButton(1, this.guiLeft + 85, this.guiTop + 43, new String[]{"Dont Attack Monsters", "Attack Monsters"}, this.role.attackHostileMobs ? 1 : 0));
      this.addButton(new GuiNpcButton(4, this.guiLeft + 85, this.guiTop + 66, new String[]{"Dont Attack Creepers", "Attack Creepers"}, this.role.attackCreepers ? 1 : 0));
      this.addButton(new GuiNpcButton(2, this.guiLeft + 85, this.guiTop + 89, new String[]{"Dont Attack Other Mobs", "Attack Other Mobs"}, this.role.attackAll ? 1 : 0));
   }

   protected void func_73875_a(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if (guibutton.field_73741_f == 0) {
         this.role.attacksAnimals = button.getValue() == 1;
      }

      if (guibutton.field_73741_f == 1) {
         this.role.attackHostileMobs = button.getValue() == 1;
      }

      if (guibutton.field_73741_f == 2) {
         this.role.attackAll = button.getValue() == 1;
      }

      if (guibutton.field_73741_f == 4) {
         this.role.attackCreepers = button.getValue() == 1;
      }

   }

   public void save() {
      NoppesUtil.sendData(EnumPacketType.MainmenuAdvancedSave, this.npc.advanced.writeToNBT(new NBTTagCompound()));
   }
}
