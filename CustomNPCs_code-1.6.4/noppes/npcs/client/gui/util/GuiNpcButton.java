package noppes.npcs.client.gui.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;

public class GuiNpcButton extends GuiButton {
   public boolean shown;
   private String[] display;
   private int displayValue;

   public GuiNpcButton(int i, int j, int k, String s) {
      super(i, j, k, StatCollector.func_74838_a(s));
      this.shown = true;
      this.displayValue = 0;
   }

   public GuiNpcButton(int i, int j, int k, String[] display, int val) {
      super(i, j, k, display[val]);
      this.shown = true;
      this.displayValue = 0;
      this.display = display;
      this.displayValue = val;
   }

   public GuiNpcButton(int i, int j, int k, int l, int m, String string) {
      super(i, j, k, l, m, StatCollector.func_74838_a(string));
      this.shown = true;
      this.displayValue = 0;
   }

   public GuiNpcButton(int i, int j, int k, int l, int m, String[] display, int val) {
      this(i, j, k, l, m, display[val % display.length]);
      this.display = display;
      this.displayValue = val % display.length;
   }

   public void setDisplayText(String text) {
      this.field_73744_e = StatCollector.func_74838_a(text);
   }

   public int getValue() {
      return this.displayValue;
   }

   public void func_73737_a(Minecraft minecraft, int i, int j) {
      if (this.shown) {
         super.func_73737_a(minecraft, i, j);
      }
   }

   public boolean func_73736_c(Minecraft minecraft, int i, int j) {
      boolean bo = super.func_73736_c(minecraft, i, j);
      if (bo && this.display != null) {
         this.displayValue = (this.displayValue + 1) % this.display.length;
         this.field_73744_e = StatCollector.func_74838_a(this.display[this.displayValue]);
      }

      return bo;
   }

   public void setDisplay(int value) {
      this.displayValue = value;
      this.field_73744_e = StatCollector.func_74838_a(this.display[value]);
   }
}
