package noppes.npcs.client.gui.player;

import net.minecraft.util.ResourceLocation;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.NoppesStringUtils;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.client.controllers.MusicController;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.constants.EnumOptionType;
import noppes.npcs.constants.EnumPlayerPacket;
import noppes.npcs.controllers.Dialog;
import noppes.npcs.controllers.DialogOption;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiDialogInteract extends GuiNPCInterface {
   private Dialog dialog;
   private ResourceLocation wheel;
   private ResourceLocation[] wheelparts;
   private ResourceLocation indicator;
   private int selectedX = 0;
   private int selectedY = 0;
   private int selected = 0;

   public GuiDialogInteract(EntityNPCInterface npc, Dialog dialog) {
      super(npc);
      this.dialog = dialog;
      this.title = "";
      this.wheel = this.getResource("wheel.png");
      this.indicator = this.getResource("indicator.png");
      this.wheelparts = new ResourceLocation[]{this.getResource("wheel1.png"), this.getResource("wheel2.png"), this.getResource("wheel3.png"), this.getResource("wheel4.png"), this.getResource("wheel5.png"), this.getResource("wheel6.png")};
      if (dialog.sound != null && !dialog.sound.isEmpty()) {
         MusicController.Instance.playStreaming(dialog.sound, (float)npc.field_70165_t, (float)npc.field_70163_u, (float)npc.field_70161_v);
      }

   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.field_73882_e.field_71417_B.func_74372_a();
   }

   public void func_73863_a(int i, int j, float f) {
      super.func_73863_a(i, j, f);
      int yoffset = (this.field_73881_g - 240) / 2;
      this.func_73731_b(this.field_73886_k, this.npc.func_70023_ak(), this.field_73880_f / 2 - this.field_73886_k.func_78256_a(this.npc.func_70023_ak()) / 2, yoffset + 1, 14737632);
      int x = 0;
      String text = NoppesStringUtils.formatText(this.dialog.text, this.player.field_71092_bJ);
      String line = "";

      for(char c : text.toCharArray()) {
         if (c != '\r' && c != '\n') {
            if (this.field_73886_k.func_78256_a(line + c) > 182) {
               this.func_73731_b(this.field_73886_k, line, this.field_73880_f / 2 + 4 - 95, yoffset + 11 + x * this.field_73886_k.field_78288_b, 14737632);
               line = "";
               ++x;
            }

            line = line + c;
         } else {
            this.func_73731_b(this.field_73886_k, line, this.field_73880_f / 2 + 4 - 95, yoffset + 11 + x * this.field_73886_k.field_78288_b, 14737632);
            line = "";
            ++x;
         }
      }

      this.func_73731_b(this.field_73886_k, line, this.field_73880_f / 2 + 4 - 95, yoffset + 11 + x * this.field_73886_k.field_78288_b, 14737632);
      if (this.dialog != null && this.dialog.hasOtherOptions()) {
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_73882_e.field_71446_o.func_110577_a(this.wheel);
         this.func_73729_b(this.field_73880_f / 2 - 31, yoffset + 198, 0, 0, 63, 40);
         this.selectedX += Mouse.getDX();
         this.selectedY += Mouse.getDY();
         int limit = 80;
         if (this.selectedX > limit) {
            this.selectedX = limit;
         }

         if (this.selectedX < -limit) {
            this.selectedX = -limit;
         }

         if (this.selectedY > limit) {
            this.selectedY = limit;
         }

         if (this.selectedY < -limit) {
            this.selectedY = -limit;
         }

         this.selected = 2;
         if (this.selectedY < -20) {
            ++this.selected;
         }

         if (this.selectedY > 54) {
            --this.selected;
         }

         if (this.selectedX < 0) {
            this.selected += 3;
         }

         this.field_73882_e.field_71446_o.func_110577_a(this.wheelparts[this.selected - 1]);
         this.func_73729_b(this.field_73880_f / 2 - 31, yoffset + 198, 0, 0, 85, 55);

         for(int slot : this.dialog.options.keySet()) {
            DialogOption option = (DialogOption)this.dialog.options.get(slot);
            if (option != null && option.optionType != EnumOptionType.Disabled) {
               int color = option.optionColor;
               if (slot == this.selected - 1) {
                  color = 8622040;
               }

               if (slot == 0) {
                  this.func_73731_b(this.field_73886_k, option.title, this.field_73880_f / 2 + 13, yoffset + 192, color);
               }

               if (slot == 1) {
                  this.func_73731_b(this.field_73886_k, option.title, this.field_73880_f / 2 + 33, yoffset + 210, color);
               }

               if (slot == 2) {
                  this.func_73731_b(this.field_73886_k, option.title, this.field_73880_f / 2 + 27, yoffset + 230, color);
               }

               if (slot == 3) {
                  this.func_73731_b(this.field_73886_k, option.title, this.field_73880_f / 2 - 13 - this.field_73886_k.func_78256_a(option.title), yoffset + 192, color);
               }

               if (slot == 4) {
                  this.func_73731_b(this.field_73886_k, option.title, this.field_73880_f / 2 - 33 - this.field_73886_k.func_78256_a(option.title), yoffset + 210, color);
               }

               if (slot == 5) {
                  this.func_73731_b(this.field_73886_k, option.title, this.field_73880_f / 2 - 27 - this.field_73886_k.func_78256_a(option.title), yoffset + 230, color);
               }
            }
         }

         this.field_73882_e.field_71446_o.func_110577_a(this.indicator);
         this.func_73729_b(this.field_73880_f / 2 + this.selectedX / 4 - 2, yoffset + 214 - this.selectedY / 6, 0, 0, 8, 8);
      }
   }

   private void setSelectedXY() {
      if (this.selected == 1) {
         this.selectedX = 33;
         this.selectedY = 72;
      } else if (this.selected == 2) {
         this.selectedX = 80;
         this.selectedY = 31;
      } else if (this.selected == 3) {
         this.selectedX = 54;
         this.selectedY = -45;
      } else if (this.selected == 4) {
         this.selectedX = -33;
         this.selectedY = 72;
      } else if (this.selected == 5) {
         this.selectedX = -80;
         this.selectedY = 31;
      } else if (this.selected == 6) {
         this.selectedX = -54;
         this.selectedY = -45;
      }

   }

   public void func_73869_a(char c, int i) {
      if ((i == this.field_73882_e.field_71474_y.field_74351_w.field_74512_d || i == 200) && this.selected - 1 != 0 && this.selected - 1 != 3) {
         --this.selected;
         this.setSelectedXY();
      }

      if ((i == this.field_73882_e.field_71474_y.field_74368_y.field_74512_d || i == 208) && this.selected + 1 != 4 && this.selected + 1 != 7) {
         ++this.selected;
         this.setSelectedXY();
      }

      if ((i == this.field_73882_e.field_71474_y.field_74366_z.field_74512_d || i == 205) && this.selected - 3 > 0) {
         this.selected -= 3;
         this.setSelectedXY();
      }

      if ((i == this.field_73882_e.field_71474_y.field_74370_x.field_74512_d || i == 203) && this.selected + 3 < 7) {
         this.selected += 3;
         this.setSelectedXY();
      }

      if (i == 28) {
         this.handleDialogSelection();
      }

      if (i == 1 || i == this.field_73882_e.field_71474_y.field_74315_B.field_74512_d) {
         this.closed();
         this.close();
      }

      super.func_73869_a(c, i);
   }

   public void func_73864_a(int i, int j, int k) {
      this.handleDialogSelection();
   }

   private void handleDialogSelection() {
      this.close();
      if (this.dialog != null && this.dialog.hasOtherOptions()) {
         DialogOption option = (DialogOption)this.dialog.options.get(this.selected - 1);
         if (option != null && option.optionType != EnumOptionType.QuitOption && option.optionType != EnumOptionType.Disabled) {
            NoppesUtilPlayer.sendData(EnumPlayerPacket.Dialog, this.dialog.id, this.selected - 1);
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
         } else {
            this.closed();
         }
      } else {
         this.closed();
      }
   }

   private void closed() {
      NoppesUtilPlayer.sendData(EnumPlayerPacket.CheckQuestCompletion);
   }

   public void save() {
   }
}
