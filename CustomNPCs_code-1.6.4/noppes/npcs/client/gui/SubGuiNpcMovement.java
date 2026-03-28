package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import noppes.npcs.DataAI;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.client.gui.util.SubGuiInterface;
import noppes.npcs.constants.EnumAnimation;
import noppes.npcs.constants.EnumMovingType;
import noppes.npcs.constants.EnumStandingType;

public class SubGuiNpcMovement extends SubGuiInterface implements ITextfieldListener {
   private DataAI ai;

   public SubGuiNpcMovement(DataAI ai) {
      this.ai = ai;
      this.setBackground("menubg.png");
      this.xSize = 256;
      this.ySize = 216;
      this.closeOnEsc = true;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addLabel(new GuiNpcLabel(0, "Moving type", this.guiLeft + 4, this.guiTop + 9, 4210752));
      this.addButton(new GuiNpcButton(0, this.guiLeft + 80, this.guiTop + 4, 100, 20, EnumMovingType.names(), this.ai.movingType.ordinal()));
      if (this.ai.movingType == EnumMovingType.Wandering) {
         this.addLabel(new GuiNpcLabel(4, "Walking range", this.guiLeft + 4, this.guiTop + 31, 4210752));
         this.addTextField(new GuiNpcTextField(4, this, this.field_73886_k, this.guiLeft + 100, this.guiTop + 26, 40, 20, this.ai.walkingRange + ""));
         this.getTextField(4).numbersOnly = true;
         this.getTextField(4).setMinMaxDefault(0, 1000, 5);
      } else if (this.ai.movingType == EnumMovingType.Standing) {
         this.addLabel(new GuiNpcLabel(7, "Position Offset X:", this.guiLeft + 4, this.guiTop + 31, 4210752));
         this.addTextField(new GuiNpcTextField(7, this, this.field_73886_k, this.guiLeft + 99, this.guiTop + 26, 24, 20, (int)this.ai.bodyOffsetX + ""));
         this.getTextField(7).numbersOnly = true;
         this.getTextField(7).setMinMaxDefault(0, 10, 5);
         this.addLabel(new GuiNpcLabel(8, "Y:", this.guiLeft + 125, this.guiTop + 31, 4210752));
         this.addTextField(new GuiNpcTextField(8, this, this.field_73886_k, this.guiLeft + 135, this.guiTop + 26, 24, 20, (int)this.ai.bodyOffsetY + ""));
         this.getTextField(8).numbersOnly = true;
         this.getTextField(8).setMinMaxDefault(0, 10, 5);
         this.addLabel(new GuiNpcLabel(9, "Z:", this.guiLeft + 161, this.guiTop + 31, 4210752));
         this.addTextField(new GuiNpcTextField(9, this, this.field_73886_k, this.guiLeft + 171, this.guiTop + 26, 24, 20, (int)this.ai.bodyOffsetZ + ""));
         this.getTextField(9).numbersOnly = true;
         this.getTextField(9).setMinMaxDefault(0, 10, 5);
         this.addLabel(new GuiNpcLabel(3, "Animation", this.guiLeft + 4, this.guiTop + 53, 4210752));
         this.addButton(new GuiNpcButton(3, this.guiLeft + 80, this.guiTop + 48, 100, 20, new String[]{"Normal", "Sitting", "Lying", "Sneaking", "Dancing", "Aiming"}, this.ai.animationType.ordinal()));
         if (this.ai.standingType == EnumStandingType.NoRotation || this.ai.standingType == EnumStandingType.HeadRotation) {
            this.addTextField(new GuiNpcTextField(5, this, this.field_73886_k, this.guiLeft + 165, this.guiTop + 70, 40, 20, this.ai.orientation + ""));
            this.getTextField(5).numbersOnly = true;
            this.getTextField(5).setMinMaxDefault(0, 359, 0);
            this.addLabel(new GuiNpcLabel(5, "(0-359)", this.guiLeft + 207, this.guiTop + 75, 4210752));
         }

         if (this.ai.animationType != EnumAnimation.LYING) {
            this.addLabel(new GuiNpcLabel(1, "Rotation", this.guiLeft + 4, this.guiTop + 75, 4210752));
            this.addButton(new GuiNpcButton(4, this.guiLeft + 80, this.guiTop + 70, 80, 20, new String[]{"Body", "Manual", "Stalking", "Head"}, this.ai.standingType.ordinal()));
         } else {
            this.addLabel(new GuiNpcLabel(6, "Lying Rotation", this.guiLeft + 4, this.guiTop + 75, 4210752));
            this.addTextField(new GuiNpcTextField(5, this, this.field_73886_k, this.guiLeft + 99, this.guiTop + 70, 40, 20, this.ai.orientation + ""));
            this.getTextField(5).numbersOnly = true;
            this.getTextField(5).setMinMaxDefault(0, 359, 0);
            this.addLabel(new GuiNpcLabel(5, "(0-359)", this.guiLeft + 142, this.guiTop + 75, 4210752));
         }
      }

      if (this.ai.movingType != EnumMovingType.Standing) {
         this.addLabel(new GuiNpcLabel(12, "Animation", this.guiLeft + 4, this.guiTop + 53, 4210752));
         this.addButton(new GuiNpcButton(12, this.guiLeft + 80, this.guiTop + 48, 100, 20, new String[]{"Normal", "Sneaking", "Aiming", "Dancing"}, this.ai.animationType.getWalkingAnimation()));
      }

      if (this.ai.movingType == EnumMovingType.MovingPath) {
         this.addLabel(new GuiNpcLabel(8, "Movement", this.guiLeft + 4, this.guiTop + 31, 4210752));
         this.addButton(new GuiNpcButton(8, this.guiLeft + 80, this.guiTop + 26, 80, 20, new String[]{"ai.looping", "ai.backtracking"}, this.ai.movingPattern));
         this.addLabel(new GuiNpcLabel(9, "Pauses", this.guiLeft + 4, this.guiTop + 75, 4210752));
         this.addButton(new GuiNpcButton(9, this.guiLeft + 80, this.guiTop + 70, 80, 20, new String[]{"gui.no", "gui.yes"}, this.ai.movingPause ? 1 : 0));
      }

      this.addButton(new GuiNpcButton(66, this.guiLeft + 190, this.guiTop + 190, 60, 20, "gui.done"));
   }

   protected void func_73875_a(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if (button.field_73741_f == 0) {
         this.ai.movingType = EnumMovingType.values()[button.getValue()];
         if (this.ai.movingType != EnumMovingType.Standing) {
            this.ai.animationType = EnumAnimation.NONE;
            this.ai.standingType = EnumStandingType.RotateBody;
            this.ai.bodyOffsetX = this.ai.bodyOffsetY = this.ai.bodyOffsetZ = 5.0F;
         }

         this.func_73866_w_();
      } else if (button.field_73741_f == 3) {
         this.ai.animationType = EnumAnimation.values()[button.getValue()];
         this.func_73866_w_();
      } else if (button.field_73741_f == 4) {
         this.ai.standingType = EnumStandingType.values()[button.getValue()];
         this.func_73866_w_();
      } else if (button.field_73741_f == 8) {
         this.ai.movingPattern = button.getValue();
      } else if (button.field_73741_f == 9) {
         this.ai.movingPause = button.getValue() == 1;
      } else if (button.field_73741_f == 12) {
         if (button.getValue() == 0) {
            this.ai.animationType = EnumAnimation.NONE;
         }

         if (button.getValue() == 1) {
            this.ai.animationType = EnumAnimation.SNEAKING;
         }

         if (button.getValue() == 2) {
            this.ai.animationType = EnumAnimation.Aiming;
         }

         if (button.getValue() == 3) {
            this.ai.animationType = EnumAnimation.DANCING;
         }
      } else if (guibutton.field_73741_f == 66) {
         this.close();
      }

   }

   public void unFocused(GuiNpcTextField textfield) {
      if (textfield.id == 7) {
         this.ai.bodyOffsetX = (float)textfield.getInteger();
      } else if (textfield.id == 8) {
         this.ai.bodyOffsetY = (float)textfield.getInteger();
      } else if (textfield.id == 9) {
         this.ai.bodyOffsetZ = (float)textfield.getInteger();
      } else if (textfield.id == 5) {
         this.ai.orientation = textfield.getInteger();
      } else if (textfield.id == 4) {
         this.ai.walkingRange = textfield.getInteger();
      } else if (textfield.id == 6) {
         this.ai.distanceToMelee = textfield.getInteger();
      }

   }
}
