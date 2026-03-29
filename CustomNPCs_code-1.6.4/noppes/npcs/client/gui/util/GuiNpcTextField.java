package noppes.npcs.client.gui.util;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class GuiNpcTextField extends GuiTextField {
   public boolean enabled = true;
   public boolean inMenu = true;
   public boolean numbersOnly = false;
   private ITextfieldListener listener;
   public int id;
   public int min = 0;
   public int max = Integer.MAX_VALUE;
   public int def = 0;
   private static GuiNpcTextField activeTextfield = null;
   private final int[] allowedSpecialChars = new int[]{14, 211, 203, 205};

   public GuiNpcTextField(int id, GuiScreen parent, FontRenderer fontRenderer, int i, int j, int k, int l, String s) {
      super(fontRenderer, i, j, k, l);
      this.func_73804_f(500);
      this.func_73782_a(s);
      this.id = id;
      if (parent instanceof ITextfieldListener) {
         this.listener = (ITextfieldListener)parent;
      }

   }

   private boolean charAllowed(char c, int i) {
      if (this.numbersOnly && !Character.isDigit(c) && (!this.isEmpty() || c != '-')) {
         for(int j : this.allowedSpecialChars) {
            if (j == i) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   public boolean func_73802_a(char c, int i) {
      return !this.charAllowed(c, i) ? false : super.func_73802_a(c, i);
   }

   public boolean isEmpty() {
      return this.func_73781_b().trim().length() == 0;
   }

   public int getInteger() {
      return Integer.parseInt(this.func_73781_b());
   }

   public boolean isInteger() {
      try {
         Integer.parseInt(this.func_73781_b());
         return true;
      } catch (NumberFormatException var2) {
         return false;
      }
   }

   public void func_73793_a(int i, int j, int k) {
      boolean wasFocused = this.func_73806_l();
      super.func_73793_a(i, j, k);
      if (wasFocused != this.func_73806_l() && wasFocused) {
         this.unFocused();
      }

      if (this.func_73806_l()) {
         activeTextfield = this;
      }

   }

   public void unFocused() {
      if (this.numbersOnly) {
         if (!this.isEmpty() && this.isInteger()) {
            if (this.getInteger() < this.min) {
               this.func_73782_a(this.min + "");
            } else if (this.getInteger() > this.max) {
               this.func_73782_a(this.max + "");
            }
         } else {
            this.func_73782_a(this.def + "");
         }
      }

      if (this.listener != null) {
         this.listener.unFocused(this);
      }

      if (this == activeTextfield) {
         activeTextfield = null;
      }

   }

   public void func_73795_f() {
      if (this.enabled) {
         super.func_73795_f();
      }

   }

   public void setMinMaxDefault(int i, int j, int k) {
      this.min = i;
      this.max = j;
      this.def = k;
   }

   public static void unfocus() {
      if (activeTextfield != null) {
         activeTextfield.unFocused();
      }

      activeTextfield = null;
   }
}
