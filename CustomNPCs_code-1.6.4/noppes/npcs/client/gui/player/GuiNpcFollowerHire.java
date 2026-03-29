package noppes.npcs.client.gui.player;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.constants.EnumPlayerPacket;
import noppes.npcs.containers.ContainerNPCFollowerHire;
import noppes.npcs.roles.RoleFollower;
import org.lwjgl.opengl.GL11;

public class GuiNpcFollowerHire extends GuiContainerNPCInterface {
   private final ResourceLocation resource = new ResourceLocation("customnpcs", "textures/gui/followerhire.png");
   private EntityNPCInterface npc;
   private ContainerNPCFollowerHire container;
   private RoleFollower role;

   public GuiNpcFollowerHire(EntityNPCInterface npc, ContainerNPCFollowerHire container) {
      super(npc, container);
      this.container = container;
      this.npc = npc;
      this.role = (RoleFollower)npc.roleInterface;
      this.closeOnEsc = true;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addButton(new GuiNpcButton(5, this.field_74198_m + 26, this.field_74197_n + 60, 50, 20, StatCollector.func_74838_a("follower.hire")));
   }

   public void func_73875_a(GuiButton guibutton) {
      super.func_73875_a(guibutton);
      if (guibutton.field_73741_f == 5) {
         NoppesUtilPlayer.sendData(EnumPlayerPacket.FollowerHire);
         this.close();
      }

   }

   protected void func_74189_g(int par1, int par2) {
   }

   protected void func_74185_a(float f, int i, int j) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_73882_e.field_71446_o.func_110577_a(this.resource);
      int l = (this.field_73880_f - this.field_74194_b) / 2;
      int i1 = (this.field_73881_g - this.field_74195_c) / 2;
      this.func_73729_b(l, i1, 0, 0, this.field_74194_b, this.field_74195_c);
      int index = 0;

      for(int id : this.role.inventory.items.keySet()) {
         ItemStack itemstack = (ItemStack)this.role.inventory.items.get(id);
         if (itemstack != null) {
            int days = 1;
            if (this.role.rates.containsKey(id)) {
               days = (Integer)this.role.rates.get(id);
            }

            int yOffset = index * 26;
            int x = this.field_74198_m + 78;
            int y = this.field_74197_n + yOffset + 10;
            GL11.glEnable(32826);
            RenderHelper.func_74520_c();
            field_74196_a.func_77015_a(this.field_73886_k, this.field_73882_e.field_71446_o, itemstack, x + 11, y);
            field_74196_a.func_77021_b(this.field_73886_k, this.field_73882_e.field_71446_o, itemstack, x + 11, y);
            RenderHelper.func_74518_a();
            GL11.glDisable(32826);
            String daysS = days + " " + (days == 1 ? StatCollector.func_74838_a("follower.day") : StatCollector.func_74838_a("follower.days"));
            this.field_73886_k.func_78276_b(" = " + daysS, x + 27, y + 4, 4210752);
            ++index;
         }
      }

   }

   public void save() {
   }
}
