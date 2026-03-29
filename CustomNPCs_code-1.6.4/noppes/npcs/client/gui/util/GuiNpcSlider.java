package noppes.npcs.client.gui.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.settings.EnumOptions;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiNpcSlider extends GuiSlider {
   private ISliderListener listener;

   public GuiNpcSlider(GuiScreen parent, int id, int xPos, int yPos, EnumOptions idFloat, String displayString, float sliderValue) {
      super(id, xPos, yPos, idFloat, displayString, sliderValue);
      if (parent instanceof ISliderListener) {
         this.listener = (ISliderListener)parent;
      }

   }

   protected void func_73739_b(Minecraft par1Minecraft, int par2, int par3) {
      if (this.field_73748_h) {
         if (this.field_73752_k) {
            this.field_73751_j = (float)(par2 - (this.field_73746_c + 4)) / (float)(this.field_73747_a - 8);
            if (this.field_73751_j < 0.0F) {
               this.field_73751_j = 0.0F;
            }

            if (this.field_73751_j > 1.0F) {
               this.field_73751_j = 1.0F;
            }

            if (this.listener != null) {
               this.listener.mouseDragged(this);
            }

            if (!Mouse.isButtonDown(0)) {
               this.func_73740_a(0, 0);
            }
         }

         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.func_73729_b(this.field_73746_c + (int)(this.field_73751_j * (float)(this.field_73747_a - 8)), this.field_73743_d, 0, 66, 4, 20);
         this.func_73729_b(this.field_73746_c + (int)(this.field_73751_j * (float)(this.field_73747_a - 8)) + 4, this.field_73743_d, 196, 66, 4, 20);
      }

   }

   public boolean func_73736_c(Minecraft par1Minecraft, int par2, int par3) {
      if (this.field_73742_g && this.field_73748_h && par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b) {
         this.field_73751_j = (float)(par2 - (this.field_73746_c + 4)) / (float)(this.field_73747_a - 8);
         if (this.field_73751_j < 0.0F) {
            this.field_73751_j = 0.0F;
         }

         if (this.field_73751_j > 1.0F) {
            this.field_73751_j = 1.0F;
         }

         if (this.listener != null) {
            this.listener.mousePressed(this);
         }

         this.field_73752_k = true;
         return true;
      } else {
         return false;
      }
   }

   public void func_73740_a(int par1, int par2) {
      this.field_73752_k = false;
      if (this.listener != null) {
         this.listener.mouseReleased(this);
      }

   }
}
