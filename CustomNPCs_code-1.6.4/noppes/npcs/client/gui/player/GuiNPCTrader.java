package noppes.npcs.client.gui.player;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface;
import noppes.npcs.containers.ContainerNPCTrader;
import noppes.npcs.roles.RoleTrader;
import org.lwjgl.opengl.GL11;

public class GuiNPCTrader extends GuiContainerNPCInterface {
   private final ResourceLocation resource = new ResourceLocation("customnpcs", "textures/gui/npctrader.png");
   private RoleTrader role;

   public GuiNPCTrader(EntityNPCInterface npc, ContainerNPCTrader container) {
      super(npc, container);
      this.role = (RoleTrader)npc.roleInterface;
      this.closeOnEsc = true;
      this.field_74195_c = 232;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
   }

   protected void func_74185_a(float f, int i, int j) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_73882_e.field_71446_o.func_110577_a(this.resource);
      int l = (this.field_73880_f - this.field_74194_b) / 2;
      int i1 = (this.field_73881_g - this.field_74195_c) / 2;
      this.func_73729_b(l, i1, 0, 0, this.field_74194_b, this.field_74195_c);

      for(int slot : this.role.inventoryCurrency.items.keySet()) {
         ItemStack item = (ItemStack)this.role.inventoryCurrency.items.get(slot);
         if (item != null && this.role.inventorySold.items.get(slot) != null) {
            int x = this.field_74198_m + slot % 3 * 45 + 40;
            int y = this.field_74197_n + slot / 3 * 22 + 8;
            GL11.glEnable(32826);
            RenderHelper.func_74520_c();
            field_74196_a.func_77015_a(this.field_73886_k, this.field_73882_e.field_71446_o, item, x, y);
            field_74196_a.func_77021_b(this.field_73886_k, this.field_73882_e.field_71446_o, item, x, y);
            RenderHelper.func_74518_a();
            GL11.glDisable(32826);
            this.field_73886_k.func_78276_b("=", x + 16, y + 4, 4210752);
         }
      }

   }

   public void func_73863_a(int par1, int par2, float par3) {
      super.func_73863_a(par1, par2, par3);

      for(int slot : this.role.inventoryCurrency.items.keySet()) {
         ItemStack item = (ItemStack)this.role.inventoryCurrency.items.get(slot);
         if (item != null && this.role.inventorySold.items.get(slot) != null) {
            int x = this.field_74198_m + slot % 3 * 45 + 40;
            int y = this.field_74197_n + slot / 3 * 22 + 8;
            if (this.func_74188_c(x - this.field_74198_m, y - this.field_74197_n, 16, 16, par1, par2)) {
               this.func_74184_a(item, par1, par2);
            }
         }
      }

   }

   public void save() {
   }
}
