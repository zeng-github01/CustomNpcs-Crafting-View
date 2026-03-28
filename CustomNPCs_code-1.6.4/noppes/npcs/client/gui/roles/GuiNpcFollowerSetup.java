package noppes.npcs.client.gui.roles;

import java.util.HashMap;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.containers.ContainerNPCFollowerSetup;
import noppes.npcs.roles.RoleFollower;

public class GuiNpcFollowerSetup extends GuiContainerNPCInterface2 {
   private RoleFollower role;
   private static final ResourceLocation field_110422_t = new ResourceLocation("textures/gui/followersetup.png");

   public GuiNpcFollowerSetup(EntityNPCInterface npc, ContainerNPCFollowerSetup container) {
      super(npc, container);
      this.role = (RoleFollower)npc.roleInterface;
      this.field_74195_c = 180;
      this.setBackground("followersetup.png");
   }

   public void func_73866_w_() {
      super.func_73866_w_();

      for(int i = 0; i < 3; ++i) {
         int x = this.field_74198_m + 66;
         int y = this.field_74197_n + 37;
         y += i * 25;
         GuiNpcTextField tf = new GuiNpcTextField(i, this, this.field_73886_k, x, y, 24, 20, "1");
         tf.numbersOnly = true;
         tf.setMinMaxDefault(1, Integer.MAX_VALUE, 1);
         this.addTextField(tf);
      }

      int i = 0;

      for(int day : this.role.rates.values()) {
         this.getTextField(i).func_73782_a(day + "");
         ++i;
      }

      this.addTextField(new GuiNpcTextField(3, this, this.field_73886_k, this.field_74198_m + 100, this.field_74197_n + 6, 286, 20, this.role.dialogHire));
      this.addTextField(new GuiNpcTextField(4, this, this.field_73886_k, this.field_74198_m + 100, this.field_74197_n + 30, 286, 20, this.role.dialogFarewell));
   }

   protected void func_73875_a(GuiButton guibutton) {
   }

   public void func_73874_b() {
   }

   protected void func_74189_g(int par1, int par2) {
   }

   public void save() {
      HashMap<Integer, Integer> map = new HashMap();

      for(int i = 0; i < this.role.inventory.func_70302_i_(); ++i) {
         ItemStack item = this.role.inventory.func_70301_a(i);
         if (item != null) {
            int days = 1;
            if (!this.getTextField(i).isEmpty() && this.getTextField(i).isInteger()) {
               days = this.getTextField(i).getInteger();
            }

            if (days <= 0) {
               days = 1;
            }

            map.put(i, days);
         }
      }

      this.role.rates = map;
      this.role.dialogHire = this.getTextField(3).func_73781_b();
      this.role.dialogFarewell = this.getTextField(4).func_73781_b();
      NoppesUtil.sendData(EnumPacketType.MainmenuAdvancedSave, this.npc.advanced.writeToNBT(new NBTTagCompound()));
   }
}
