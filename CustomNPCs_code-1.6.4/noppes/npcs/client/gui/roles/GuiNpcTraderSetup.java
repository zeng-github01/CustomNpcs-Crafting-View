package noppes.npcs.client.gui.roles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface2;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.containers.ContainerNPCTraderSetup;
import org.lwjgl.opengl.GL11;

public class GuiNpcTraderSetup extends GuiContainerNPCInterface2 {
   private static final ResourceLocation field_110422_t = new ResourceLocation("customnpcs", "textures/gui/npctradersetup2.png");

   public GuiNpcTraderSetup(EntityNPCInterface npc, ContainerNPCTraderSetup container) {
      super(npc, container);
      this.field_74195_c = 180;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.field_73887_h.clear();
      this.setBackground("npctradersetup.png");
   }

   protected void func_74189_g(int par1, int par2) {
      super.func_74189_g(par1, par2);

      for(int ii = 0; ii < 18; ++ii) {
         int x = 212;
         x += ii % 3 * 59;
         int y = 16;
         y += ii / 3 * 22;
         this.field_73886_k.func_78276_b("=", x, y + 4, 4210752);
      }

   }

   public void func_73876_c() {
      super.func_73876_c();
   }

   protected void func_73864_a(int i, int j, int k) {
      super.func_73864_a(i, j, k);
   }

   public void func_73869_a(char c, int i) {
   }

   protected void func_74185_a(float f, int i, int j) {
      super.func_74185_a(f, i, j);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_73882_e.field_71446_o.func_110577_a(field_110422_t);
      this.func_73729_b(this.field_74198_m + 190, this.field_74197_n + 20, 0, 0, 167, 134);
   }

   public void func_73863_a(int i, int j, float f) {
      super.func_73863_a(i, j, f);
   }

   public void save() {
      NoppesUtil.sendData(EnumPacketType.MainmenuAdvancedSave, this.npc.advanced.writeToNBT(new NBTTagCompound()));
   }
}
