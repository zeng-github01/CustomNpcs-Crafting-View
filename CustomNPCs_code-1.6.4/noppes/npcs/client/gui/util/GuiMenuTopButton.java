package noppes.npcs.client.gui.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class GuiMenuTopButton extends GuiButton {
   public static final ResourceLocation resource = new ResourceLocation("customnpcs", "textures/gui/menutopbutton.png");
   protected int field_73745_b;
   public boolean active;
   public boolean hover;
   public boolean rotated;
   public IButtonListener listener;

   public GuiMenuTopButton(int i, int j, int k, String s) {
      super(i, j, k, StatCollector.func_74838_a(s));
      this.hover = false;
      this.rotated = false;
      this.active = false;
      this.field_73747_a = Minecraft.func_71410_x().field_71466_p.func_78256_a(this.field_73744_e) + 12;
      this.field_73745_b = 20;
   }

   public GuiMenuTopButton(int i, GuiMenuTopButton parent, String s) {
      this(i, parent.field_73746_c + parent.field_73747_a, parent.field_73743_d, s);
   }

   public GuiMenuTopButton(int i, GuiMenuTopButton parent, String s, IButtonListener listener) {
      this(i, parent, s);
      this.listener = listener;
   }

   protected int func_73738_a(boolean flag) {
      byte byte0 = 1;
      if (this.active) {
         byte0 = 0;
      } else if (flag) {
         byte0 = 2;
      }

      return byte0;
   }

   public void func_73737_a(Minecraft minecraft, int i, int j) {
      if (this.field_73748_h) {
         GL11.glPushMatrix();
         minecraft.field_71446_o.func_110577_a(resource);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         int height = this.field_73745_b - (this.active ? 0 : 2);
         this.hover = i >= this.field_73746_c && j >= this.field_73743_d && i < this.field_73746_c + this.field_73747_a && j < this.field_73743_d + height;
         int k = this.func_73738_a(this.hover);
         this.func_73729_b(this.field_73746_c, this.field_73743_d, 0, k * 20, this.field_73747_a / 2, height);
         this.func_73729_b(this.field_73746_c + this.field_73747_a / 2, this.field_73743_d, 200 - this.field_73747_a / 2, k * 20, this.field_73747_a / 2, height);
         this.func_73739_b(minecraft, i, j);
         FontRenderer fontrenderer = minecraft.field_71466_p;
         if (this.rotated) {
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
         }

         if (this.active) {
            this.func_73732_a(fontrenderer, this.field_73744_e, this.field_73746_c + this.field_73747_a / 2, this.field_73743_d + (height - 8) / 2, 16777120);
         } else if (this.hover) {
            this.func_73732_a(fontrenderer, this.field_73744_e, this.field_73746_c + this.field_73747_a / 2, this.field_73743_d + (height - 8) / 2, 16777120);
         } else {
            this.func_73732_a(fontrenderer, this.field_73744_e, this.field_73746_c + this.field_73747_a / 2, this.field_73743_d + (height - 8) / 2, 14737632);
         }

         GL11.glPopMatrix();
      }
   }

   protected void func_73739_b(Minecraft minecraft, int i, int j) {
   }

   public void func_73740_a(int i, int j) {
   }

   public boolean func_73736_c(Minecraft minecraft, int i, int j) {
      boolean bo = !this.active && this.field_73748_h && this.hover;
      if (bo && this.listener != null) {
         this.listener.actionPerformed(this);
         return false;
      } else {
         return bo;
      }
   }

   public int getWidth() {
      return this.field_73747_a;
   }
}
