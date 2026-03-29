package noppes.npcs.client.gui.util;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;

public class GuiNpcTextArea extends GuiNpcTextField {
   public boolean inMenu = true;
   public boolean numbersOnly = false;
   private int posX;
   private int posY;
   private int field_73811_d;
   private int field_73812_e;
   private int field_73822_h;
   private FontRenderer fontrenderer;
   private int field_73817_o = 0;

   public GuiNpcTextArea(int id, GuiScreen guiscreen, FontRenderer fontrenderer, int i, int j, int k, int l, String s) {
      super(id, guiscreen, fontrenderer, i, j, k, l, s);
      this.posX = i;
      this.posY = j;
      this.field_73811_d = k;
      this.field_73812_e = l;
      this.fontrenderer = fontrenderer;
      this.func_73804_f(1500);
      this.func_73782_a(s);
   }

   public void func_73780_a() {
      ++this.field_73822_h;
   }

   public boolean func_73802_a(char c, int i) {
      if (!this.func_73806_l()) {
         return false;
      } else {
         String originalText = this.func_73781_b();
         this.func_73782_a(originalText);
         if (c == '\r' || c == '\n') {
            this.func_73782_a(originalText + c);
         }

         boolean bo = super.func_73802_a(c, i);
         String newText = this.func_73781_b();
         if (originalText.length() > newText.length()) {
            --this.field_73817_o;
         }

         if (originalText.length() < newText.length()) {
            ++this.field_73817_o;
         }

         return bo;
      }
   }

   public void func_73793_a(int i, int j, int k) {
      boolean wasFocused = this.func_73806_l();
      super.func_73793_a(i, j, k);
      if (!wasFocused && this.func_73806_l()) {
         this.field_73817_o = this.func_73781_b().length();
      }

   }

   public void func_73795_f() {
      func_73734_a(this.posX - 1, this.posY - 1, this.posX + this.field_73811_d + 1, this.posY + this.field_73812_e + 1, -6250336);
      func_73734_a(this.posX, this.posY, this.posX + this.field_73811_d, this.posY + this.field_73812_e, -16777216);
      int x = 0;
      String line = "";
      int color = 14737632;

      for(char c : this.func_73781_b().toCharArray()) {
         if (c != '\r' && c != '\n') {
            if (this.fontrenderer.func_78256_a(line + c) > this.field_73811_d - 8) {
               this.func_73731_b(this.fontrenderer, line, this.posX + 4, this.posY + 4 + x * this.fontrenderer.field_78288_b, color);
               line = "";
               ++x;
            }

            line = line + c;
         } else {
            this.func_73731_b(this.fontrenderer, line, this.posX + 4, this.posY + 4 + x * this.fontrenderer.field_78288_b, color);
            line = "";
            ++x;
         }
      }

      this.func_73731_b(this.fontrenderer, line, this.posX + 4, this.posY + 4 + x * this.fontrenderer.field_78288_b, color);
      boolean flag = this.func_73806_l() && this.field_73822_h / 6 % 2 == 0;
      int i = 0;
      x = 0;
      line = "";
      if (flag && 0 == this.field_73817_o) {
         this.fontrenderer.func_78276_b("_", this.posX + 3 + this.fontrenderer.func_78256_a(line), this.posY + 4 + x * this.fontrenderer.field_78288_b, color);
      }

      for(char c : this.func_73781_b().toCharArray()) {
         ++i;
         if (c != '\r' && c != '\n') {
            if (this.fontrenderer.func_78256_a(line + c) > this.field_73811_d - 8) {
               line = "";
               ++x;
               line = line + c;
            } else {
               line = line + c;
            }
         } else {
            line = "";
            ++x;
         }

         if (flag && i == this.field_73817_o) {
            this.fontrenderer.func_78276_b("_", this.posX + 3 + this.fontrenderer.func_78256_a(line), this.posY + 4 + x * this.fontrenderer.field_78288_b, color);
         }
      }

   }
}
