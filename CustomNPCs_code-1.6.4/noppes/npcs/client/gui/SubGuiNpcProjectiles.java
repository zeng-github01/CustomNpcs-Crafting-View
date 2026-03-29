package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import noppes.npcs.DataStats;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.constants.EnumParticleType;
import noppes.npcs.constants.EnumPotionType;

public class SubGuiNpcProjectiles extends SubGuiInterface implements ITextfieldListener {
   private DataStats stats;
   private String[] potionNames = new String[]{"gui.none", "tile.fire.name", "potion.poison", "potion.hunger", "potion.weakness", "potion.moveSlowdown", "potion.confusion", "potion.blindness", "potion.wither"};
   private String[] trailNames = new String[]{"gui.none", "Smoke", "Portal", "Redstone", "Lightning", "LargeSmoke", "Magic", "Enchant"};

   public SubGuiNpcProjectiles(DataStats stats) {
      this.stats = stats;
      this.setBackground("menubg.png");
      this.xSize = 256;
      this.ySize = 216;
      this.closeOnEsc = true;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addLabel(new GuiNpcLabel(1, "enchantment.arrowDamage", this.guiLeft + 5, this.guiTop + 15, 4210752));
      this.addTextField(new GuiNpcTextField(1, this, this.field_73886_k, this.guiLeft + 45, this.guiTop + 10, 50, 18, this.stats.pDamage + ""));
      this.getTextField(1).numbersOnly = true;
      this.getTextField(1).setMinMaxDefault(0, 9999, 5);
      this.addLabel(new GuiNpcLabel(2, "enchantment.arrowKnockback", this.guiLeft + 110, this.guiTop + 15, 4210752));
      this.addTextField(new GuiNpcTextField(2, this, this.field_73886_k, this.guiLeft + 150, this.guiTop + 10, 50, 18, this.stats.pImpact + ""));
      this.getTextField(2).numbersOnly = true;
      this.getTextField(2).setMinMaxDefault(0, 3, 0);
      this.addLabel(new GuiNpcLabel(3, "stats.size", this.guiLeft + 5, this.guiTop + 45, 4210752));
      this.addTextField(new GuiNpcTextField(3, this, this.field_73886_k, this.guiLeft + 45, this.guiTop + 40, 50, 18, this.stats.pSize + ""));
      this.getTextField(3).numbersOnly = true;
      this.getTextField(3).setMinMaxDefault(1, 10, 10);
      this.addLabel(new GuiNpcLabel(4, "stats.speed", this.guiLeft + 5, this.guiTop + 75, 4210752));
      this.addTextField(new GuiNpcTextField(4, this, this.field_73886_k, this.guiLeft + 45, this.guiTop + 70, 50, 18, this.stats.pSpeed + ""));
      this.getTextField(4).numbersOnly = true;
      this.getTextField(4).setMinMaxDefault(1, 50, 10);
      this.addLabel(new GuiNpcLabel(5, "stats.hasgravity", this.guiLeft + 5, this.guiTop + 105, 4210752));
      this.addButton(new GuiNpcButton(0, this.guiLeft + 60, this.guiTop + 100, 60, 20, new String[]{"gui.no", "gui.yes"}, this.stats.pPhysics ? 1 : 0));
      if (!this.stats.pPhysics) {
         this.addButton(new GuiNpcButton(1, this.guiLeft + 140, this.guiTop + 100, 60, 20, new String[]{"gui.constant", "gui.accelerate"}, this.stats.pXlr8 ? 1 : 0));
      }

      this.addLabel(new GuiNpcLabel(6, "stats.explosive", this.guiLeft + 5, this.guiTop + 135, 4210752));
      this.addButton(new GuiNpcButton(2, this.guiLeft + 60, this.guiTop + 130, 60, 20, new String[]{"gui.no", "gui.yes"}, this.stats.pExplode ? 1 : 0));
      if (this.stats.pExplode) {
         this.addButton(new GuiNpcButton(3, this.guiLeft + 140, this.guiTop + 130, 60, 20, new String[]{"gui.none", "gui.small", "gui.medium", "gui.large"}, this.stats.pArea));
      }

      this.addLabel(new GuiNpcLabel(7, "stats.rangedeffect", this.guiLeft + 5, this.guiTop + 165, 4210752));
      this.addButton(new GuiNpcButton(4, this.guiLeft + 60, this.guiTop + 160, 60, 20, this.potionNames, this.stats.pEffect.ordinal()));
      if (this.stats.pEffect != EnumPotionType.None) {
         this.addTextField(new GuiNpcTextField(5, this, this.field_73886_k, this.guiLeft + 140, this.guiTop + 160, 60, 18, this.stats.pDur + ""));
         this.getTextField(5).numbersOnly = true;
         this.getTextField(5).setMinMaxDefault(1, 99999, 5);
         if (this.stats.pEffect != EnumPotionType.Fire) {
            this.addButton(new GuiNpcButton(10, this.guiLeft + 210, this.guiTop + 160, 40, 20, new String[]{"stats.regular", "stats.amplified"}, this.stats.pEffAmp));
         }
      }

      this.addLabel(new GuiNpcLabel(8, "stats.trail", this.guiLeft + 5, this.guiTop + 195, 4210752));
      this.addButton(new GuiNpcButton(5, this.guiLeft + 60, this.guiTop + 190, 60, 20, this.trailNames, this.stats.pTrail.ordinal()));
      this.addButton(new GuiNpcButton(7, this.guiLeft + 220, this.guiTop + 10, 30, 20, new String[]{"2D", "3D"}, this.stats.pRender3D ? 1 : 0));
      if (this.stats.pRender3D) {
         this.addLabel(new GuiNpcLabel(10, "stats.spin", this.guiLeft + 160, this.guiTop + 45, 4210752));
         this.addButton(new GuiNpcButton(8, this.guiLeft + 220, this.guiTop + 40, 30, 20, new String[]{"gui.no", "gui.yes"}, this.stats.pSpin ? 1 : 0));
         this.addLabel(new GuiNpcLabel(11, "stats.stick", this.guiLeft + 160, this.guiTop + 75, 4210752));
         this.addButton(new GuiNpcButton(9, this.guiLeft + 220, this.guiTop + 70, 30, 20, new String[]{"gui.no", "gui.yes"}, this.stats.pStick ? 1 : 0));
      }

      this.addButton(new GuiNpcButton(6, this.guiLeft + 140, this.guiTop + 190, 60, 20, new String[]{"Glow", "No glow"}, this.stats.pGlows ? 1 : 0));
      this.addButton(new GuiNpcButton(66, this.guiLeft + 210, this.guiTop + 190, 40, 20, "gui.done"));
   }

   public void unFocused(GuiNpcTextField textfield) {
      if (textfield.id == 1) {
         this.stats.pDamage = textfield.getInteger();
      } else if (textfield.id == 2) {
         this.stats.pImpact = textfield.getInteger();
      } else if (textfield.id == 3) {
         this.stats.pSize = textfield.getInteger();
      } else if (textfield.id == 4) {
         this.stats.pSpeed = textfield.getInteger();
      } else if (textfield.id == 5) {
         this.stats.pDur = textfield.getInteger();
      }

   }

   protected void func_73875_a(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if (button.field_73741_f == 0) {
         this.stats.pPhysics = button.getValue() == 1;
         this.func_73866_w_();
      }

      if (button.field_73741_f == 1) {
         this.stats.pXlr8 = button.getValue() == 1;
      }

      if (button.field_73741_f == 2) {
         this.stats.pExplode = button.getValue() == 1;
         this.func_73866_w_();
      }

      if (button.field_73741_f == 3) {
         this.stats.pArea = button.getValue();
      }

      if (button.field_73741_f == 4) {
         this.stats.pEffect = EnumPotionType.values()[button.getValue()];
         this.func_73866_w_();
      }

      if (button.field_73741_f == 5) {
         this.stats.pTrail = EnumParticleType.values()[button.getValue()];
      }

      if (button.field_73741_f == 6) {
         this.stats.pGlows = button.getValue() == 1;
      }

      if (button.field_73741_f == 7) {
         this.stats.pRender3D = button.getValue() == 1;
         this.func_73866_w_();
      }

      if (button.field_73741_f == 8) {
         this.stats.pSpin = button.getValue() == 1;
      }

      if (button.field_73741_f == 9) {
         this.stats.pStick = button.getValue() == 1;
      }

      if (button.field_73741_f == 10) {
         this.stats.pEffAmp = button.getValue();
      }

      if (guibutton.field_73741_f == 66) {
         this.close();
      }

   }
}
