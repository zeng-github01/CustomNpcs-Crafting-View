package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.settings.EnumOptions;
import noppes.npcs.Resistances;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcSlider;
import noppes.npcs.client.gui.util.ISliderListener;
import noppes.npcs.client.gui.util.SubGuiInterface;

public class SubGuiNpcResistanceProperties extends SubGuiInterface implements ISliderListener {
   private Resistances resistances;

   public SubGuiNpcResistanceProperties(Resistances resistances) {
      this.resistances = resistances;
      this.setBackground("menubg.png");
      this.xSize = 256;
      this.ySize = 216;
      this.closeOnEsc = true;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addLabel(new GuiNpcLabel(0, "enchantment.knockback", this.guiLeft + 4, this.guiTop + 15, 4210752));
      this.addSlider(new GuiNpcSlider(this, 0, this.guiLeft + 94, this.guiTop + 10, (EnumOptions)null, (int)(this.resistances.knockback * 100.0F - 100.0F) + "%", this.resistances.knockback / 2.0F));
      this.addLabel(new GuiNpcLabel(1, "item.arrow.name", this.guiLeft + 4, this.guiTop + 37, 4210752));
      this.addSlider(new GuiNpcSlider(this, 1, this.guiLeft + 94, this.guiTop + 32, (EnumOptions)null, (int)(this.resistances.arrow * 100.0F - 100.0F) + "%", this.resistances.arrow / 2.0F));
      this.addLabel(new GuiNpcLabel(2, "stats.melee", this.guiLeft + 4, this.guiTop + 59, 4210752));
      this.addSlider(new GuiNpcSlider(this, 2, this.guiLeft + 94, this.guiTop + 54, (EnumOptions)null, (int)(this.resistances.playermelee * 100.0F - 100.0F) + "%", this.resistances.playermelee / 2.0F));
      this.addButton(new GuiNpcButton(66, this.guiLeft + 190, this.guiTop + 190, 60, 20, "gui.done"));
   }

   protected void func_73875_a(GuiButton guibutton) {
      if (guibutton.field_73741_f == 66) {
         this.close();
      }

   }

   public void mouseDragged(GuiNpcSlider slider) {
      slider.field_73744_e = (int)(slider.field_73751_j * 200.0F - 100.0F) + "%";
   }

   public void mousePressed(GuiNpcSlider slider) {
   }

   public void mouseReleased(GuiNpcSlider slider) {
      if (slider.field_73741_f == 0) {
         this.resistances.knockback = slider.field_73751_j * 2.0F;
      }

      if (slider.field_73741_f == 1) {
         this.resistances.arrow = slider.field_73751_j * 2.0F;
      }

      if (slider.field_73741_f == 2) {
         this.resistances.playermelee = slider.field_73751_j * 2.0F;
      }

   }
}
