package noppes.npcs.client.gui.player;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcSkinPreviewInterface;
import noppes.npcs.constants.EnumPlayerPacket;
import noppes.npcs.containers.ContainerNPCFollower;
import noppes.npcs.roles.RoleFollower;
import org.lwjgl.opengl.GL11;

public class GuiNpcFollower extends GuiContainerNPCInterface implements GuiNpcSkinPreviewInterface {
   private final ResourceLocation resource = new ResourceLocation("customnpcs", "textures/gui/follower.png");
   private EntityNPCInterface npc;
   private RoleFollower role;
   private float xSize_lo;
   private float ySize_lo;

   public GuiNpcFollower(EntityNPCInterface npc, ContainerNPCFollower container) {
      super(npc, container);
      this.npc = npc;
      this.role = (RoleFollower)npc.roleInterface;
      this.closeOnEsc = true;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.field_73887_h.clear();
      this.addButton(new GuiNpcButton(4, this.field_74198_m + 100, this.field_74197_n + 110, 50, 20, new String[]{StatCollector.func_74838_a("follower.waiting"), StatCollector.func_74838_a("follower.following")}, this.role.isFollowing ? 1 : 0));
      this.addButton(new GuiNpcButton(5, this.field_74198_m + 8, this.field_74197_n + 30, 50, 20, StatCollector.func_74838_a("follower.hire")));
   }

   public void func_73875_a(GuiButton guibutton) {
      super.func_73875_a(guibutton);
      if (guibutton.field_73741_f == 4) {
         NoppesUtilPlayer.sendData(EnumPlayerPacket.FollowerState);
         this.close();
      }

      if (guibutton.field_73741_f == 5) {
         NoppesUtilPlayer.sendData(EnumPlayerPacket.FollowerExtend);
         this.close();
      }

   }

   protected void func_74189_g(int par1, int par2) {
      this.field_73886_k.func_78276_b(StatCollector.func_74838_a("follower.health") + ": " + this.npc.func_110143_aJ() + "/" + this.npc.func_110138_aP(), 62, 70, 4210752);
      if (this.role.getDaysLeft() <= 1) {
         this.field_73886_k.func_78276_b(StatCollector.func_74838_a("follower.daysleft") + ": " + StatCollector.func_74838_a("follower.lastday"), 62, 94, 4210752);
      } else {
         this.field_73886_k.func_78276_b(StatCollector.func_74838_a("follower.daysleft") + ": " + (this.role.getDaysLeft() - 1), 62, 94, 4210752);
      }

   }

   protected void func_74185_a(float f, int i, int j) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_73882_e.field_71446_o.func_110577_a(this.resource);
      int l = this.field_74198_m;
      int i1 = this.field_74197_n;
      this.func_73729_b(l, i1, 0, 0, this.field_74194_b, this.field_74195_c);
      int index = 0;

      for(int id : this.role.inventory.items.keySet()) {
         ItemStack itemstack = (ItemStack)this.role.inventory.items.get(id);
         if (itemstack != null) {
            int days = 1;
            if (this.role.rates.containsKey(id)) {
               days = (Integer)this.role.rates.get(id);
            }

            int yOffset = index * 20;
            int x = this.field_74198_m + 68;
            int y = this.field_74197_n + yOffset + 4;
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

      GL11.glEnable(2903);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)(l + 33), (float)(i1 + 131), 50.0F);
      float f1 = 30.0F;
      GL11.glScalef(-f1, f1, f1);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      float f2 = this.npc.field_70761_aq;
      float f3 = this.npc.field_70177_z;
      float f4 = this.npc.field_70125_A;
      float f5 = (float)(l + 33) - this.xSize_lo;
      float f6 = (float)(i1 + 131 - 50) - this.ySize_lo;
      GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
      RenderHelper.func_74519_b();
      GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-((float)Math.atan((double)(f6 / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
      this.npc.field_70761_aq = (float)Math.atan((double)(f5 / 40.0F)) * 20.0F;
      this.npc.field_70177_z = (float)Math.atan((double)(f5 / 40.0F)) * 40.0F;
      this.npc.field_70125_A = -((float)Math.atan((double)(f6 / 40.0F))) * 20.0F;
      this.npc.field_70759_as = this.npc.field_70177_z;
      GL11.glTranslatef(0.0F, this.npc.field_70129_M, 0.0F);
      RenderManager.field_78727_a.field_78735_i = 180.0F;
      RenderManager.field_78727_a.func_78719_a(this.npc, (double)0.0F, (double)0.0F, (double)0.0F, 0.0F, 1.0F);
      this.npc.field_70761_aq = f2;
      this.npc.field_70177_z = f3;
      this.npc.field_70125_A = f4;
      GL11.glPopMatrix();
      RenderHelper.func_74518_a();
      GL11.glDisable(32826);
      OpenGlHelper.func_77473_a(OpenGlHelper.field_77476_b);
      GL11.glDisable(3553);
      OpenGlHelper.func_77473_a(OpenGlHelper.field_77478_a);
   }

   public void func_73863_a(int i, int j, float f) {
      super.func_73863_a(i, j, f);
      this.xSize_lo = (float)i;
      this.ySize_lo = (float)j;
   }

   public void save() {
   }
}
