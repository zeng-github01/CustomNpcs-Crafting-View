package noppes.npcs.client.gui.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiMenuSideButton extends GuiNpcButton {
   public static final ResourceLocation resource = new ResourceLocation("customnpcs", "textures/gui/menusidebutton.png");
   protected int field_73747_a;
   protected int field_73745_b;
   public int field_73746_c;
   public int field_73743_d;
   public String field_73744_e;
   public int field_73741_f;
   public boolean active;
   public boolean field_73748_h;
   public boolean hover;

   public GuiMenuSideButton(int i, int j, int k, String s) {
      this(i, j, k, 200, 20, s);
   }

   public GuiMenuSideButton(int i, int j, int k, int l, int i1, String s) {
      super(i, j, k, l, i1, s);
      this.hover = false;
      this.field_73747_a = 200;
      this.field_73745_b = 20;
      this.active = false;
      this.field_73748_h = true;
      this.field_73741_f = i;
      this.field_73746_c = j;
      this.field_73743_d = k;
      this.field_73747_a = l;
      this.field_73745_b = i1;
      this.field_73744_e = s;
   }

   protected int func_73738_a(boolean flag) {
      byte byte0 = 1;
      if (this.active) {
         byte0 = 0;
      } else if (flag) {
         byte0 = 1;
      }

      return byte0;
   }

   public void func_73737_a(Minecraft minecraft, int i, int j) {
      if (this.field_73748_h) {
         FontRenderer fontrenderer = minecraft.field_71466_p;
         minecraft.field_71446_o.func_110577_a(resource);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         int width = this.field_73747_a + (this.active ? 2 : 0);
         this.hover = i >= this.field_73746_c && j >= this.field_73743_d && i < this.field_73746_c + width && j < this.field_73743_d + this.field_73745_b;
         int k = this.func_73738_a(this.hover);
         this.func_73729_b(this.field_73746_c, this.field_73743_d, 0, k * 22, width, this.field_73745_b);
         this.func_73739_b(minecraft, i, j);
         if (this.active) {
            this.func_73732_a(fontrenderer, this.field_73744_e, this.field_73746_c + width / 2, this.field_73743_d + (this.field_73745_b - 8) / 2, 16777120);
         } else if (this.hover) {
            this.func_73732_a(fontrenderer, this.field_73744_e, this.field_73746_c + width / 2, this.field_73743_d + (this.field_73745_b - 8) / 2, 16777120);
         } else {
            this.func_73732_a(fontrenderer, this.field_73744_e, this.field_73746_c + width / 2, this.field_73743_d + (this.field_73745_b - 8) / 2, 14737632);
         }

      }
   }

   protected void func_73739_b(Minecraft minecraft, int i, int j) {
   }

   public void func_73740_a(int i, int j) {
   }

   public boolean func_73736_c(Minecraft minecraft, int i, int j) {
      return !this.active && this.field_73748_h && this.hover;
   }
}
