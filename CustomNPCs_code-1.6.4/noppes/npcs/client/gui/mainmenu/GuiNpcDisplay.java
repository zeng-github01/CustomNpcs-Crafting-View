package noppes.npcs.client.gui.mainmenu;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.DataDisplay;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.GuiNPCTextures;
import noppes.npcs.client.gui.GuiNpcModelSelection;
import noppes.npcs.client.gui.GuiNpcTextureCloaks;
import noppes.npcs.client.gui.GuiNpcTextureOverlays;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.constants.EnumPacketType;

public class GuiNpcDisplay extends GuiNPCInterface2 implements ITextfieldListener, IGuiData {
   private DataDisplay display;

   public GuiNpcDisplay(EntityNPCInterface npc) {
      super(npc, 1);
      this.display = npc.display;
      NoppesUtil.sendData(EnumPacketType.MainmenuDisplayGet);
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addLabel(new GuiNpcLabel(0, "gui.name", this.guiLeft + 5, this.guiTop + 15, 4210752));
      this.addTextField(new GuiNpcTextField(0, this, this.field_73886_k, this.guiLeft + 50, this.guiTop + 10, 200, 20, this.display.name));
      this.addButton(new GuiNpcButton(0, this.guiLeft + 253, this.guiTop + 10, 110, 20, new String[]{"display.show", "display.hide", "display.showAttacking"}, this.display.showName));
      this.addLabel(new GuiNpcLabel(1, "display.model", this.guiLeft + 5, this.guiTop + 38, 4210752));
      this.addButton(new GuiNpcButton(1, this.guiLeft + 50, this.guiTop + 33, 110, 20, this.display.modelType.name));
      this.addLabel(new GuiNpcLabel(2, "display.size", this.guiLeft + 175, this.guiTop + 38, 4210752));
      this.addTextField(new GuiNpcTextField(2, this, this.field_73886_k, this.guiLeft + 203, this.guiTop + 33, 40, 20, this.display.modelSize + ""));
      this.getTextField(2).numbersOnly = true;
      this.getTextField(2).setMinMaxDefault(1, 30, 5);
      this.addLabel(new GuiNpcLabel(3, "(1-30)", this.guiLeft + 246, this.guiTop + 38, 4210752));
      this.addLabel(new GuiNpcLabel(4, "display.texture", this.guiLeft + 5, this.guiTop + 61, 4210752));
      this.addTextField(new GuiNpcTextField(3, this, this.field_73886_k, this.guiLeft + 80, this.guiTop + 56, 200, 20, this.display.usingSkinUrl ? this.display.skinUsername : this.display.texture));
      this.addButton(new GuiNpcButton(3, this.guiLeft + 325, this.guiTop + 56, 38, 20, "mco.template.button.select"));
      this.addButton(new GuiNpcButton(2, this.guiLeft + 283, this.guiTop + 56, 40, 20, new String[]{"display.texture", "display.player"}, this.display.usingSkinUrl ? 1 : 0));
      this.getButton(3).field_73742_g = !this.display.usingSkinUrl;
      this.addLabel(new GuiNpcLabel(8, "display.textureCape", this.guiLeft + 5, this.guiTop + 84, 4210752));
      this.addTextField(new GuiNpcTextField(8, this, this.field_73886_k, this.guiLeft + 80, this.guiTop + 79, 200, 20, this.display.cloakTexture));
      this.addButton(new GuiNpcButton(8, this.guiLeft + 283, this.guiTop + 79, 80, 20, "display.selectTexture"));
      this.addLabel(new GuiNpcLabel(9, "display.textureOverlay", this.guiLeft + 5, this.guiTop + 108, 4210752));
      this.addTextField(new GuiNpcTextField(9, this, this.field_73886_k, this.guiLeft + 80, this.guiTop + 103, 200, 20, this.display.glowTexture));
      this.addButton(new GuiNpcButton(9, this.guiLeft + 283, this.guiTop + 103, 80, 20, "display.selectTexture"));
      this.addLabel(new GuiNpcLabel(5, "display.livingAnimation", this.guiLeft + 5, this.guiTop + 132, 4210752));
      this.addButton(new GuiNpcButton(5, this.guiLeft + 120, this.guiTop + 127, 50, 20, new String[]{"gui.yes", "gui.no"}, this.display.NoLivingAnimation ? 1 : 0));
      this.addLabel(new GuiNpcLabel(6, "display.tint", this.guiLeft + 5, this.guiTop + 156, 4210752));

      String color;
      for(color = Integer.toHexString(this.display.skinColor); color.length() < 6; color = "0" + color) {
      }

      this.addTextField(new GuiNpcTextField(6, this, this.field_73886_k, this.guiLeft + 120, this.guiTop + 151, 40, 20, color));
      this.getTextField(6).func_73794_g(this.display.skinColor);
      this.addLabel(new GuiNpcLabel(7, "display.visible", this.guiLeft + 5, this.guiTop + 180, 4210752));
      this.addButton(new GuiNpcButton(7, this.guiLeft + 120, this.guiTop + 175, 50, 20, new String[]{"gui.yes", "gui.no", "gui.partly"}, this.display.visible));
   }

   public void unFocused(GuiNpcTextField textfield) {
      if (textfield.id == 0) {
         if (!textfield.isEmpty()) {
            this.display.name = textfield.func_73781_b();
         } else {
            textfield.func_73782_a(this.display.name);
         }
      } else if (textfield.id == 2) {
         this.display.modelSize = textfield.getInteger();
      } else if (textfield.id == 3) {
         if (this.display.usingSkinUrl) {
            this.display.skinUsername = textfield.func_73781_b();
         } else {
            this.display.texture = textfield.func_73781_b();
         }
      } else if (textfield.id == 6) {
         int color = 0;

         try {
            color = Integer.parseInt(textfield.func_73781_b(), 16);
         } catch (NumberFormatException var4) {
            color = 16777215;
         }

         this.display.skinColor = color;
         textfield.func_73794_g(this.display.skinColor);
      } else if (textfield.id == 8) {
         this.display.cloakTexture = textfield.func_73781_b();
      } else if (textfield.id == 9) {
         this.display.glowTexture = textfield.func_73781_b();
      }

   }

   protected void func_73875_a(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if (button.field_73741_f == 0) {
         this.display.showName = button.getValue();
      }

      if (button.field_73741_f == 1) {
         NoppesUtil.openGUI(this.player, new GuiNpcModelSelection(this.npc, this));
      }

      if (button.field_73741_f == 2) {
         this.display.usingSkinUrl = button.getValue() == 1;
         this.getButton(3).field_73742_g = !this.display.usingSkinUrl;
         if (this.display.usingSkinUrl) {
            this.getTextField(3).func_73782_a(this.display.skinUsername);
         } else {
            this.getTextField(3).func_73782_a(this.npc.display.texture);
         }
      } else if (button.field_73741_f == 3) {
         NoppesUtil.openGUI(this.player, new GuiNPCTextures(this.npc, this));
      } else if (button.field_73741_f == 5) {
         this.display.NoLivingAnimation = button.getValue() == 1;
      } else if (button.field_73741_f == 7) {
         this.display.visible = button.getValue();
      } else if (button.field_73741_f == 8) {
         NoppesUtil.openGUI(this.player, new GuiNpcTextureCloaks(this.npc, this));
      } else if (button.field_73741_f == 9) {
         NoppesUtil.openGUI(this.player, new GuiNpcTextureOverlays(this.npc, this));
      }

   }

   public void save() {
      this.field_73882_e.field_71438_f.func_72709_b(this.npc);
      this.field_73882_e.field_71438_f.func_72703_a(this.npc);
      NoppesUtil.sendData(EnumPacketType.MainmenuDisplaySave, this.display.writeToNBT(new NBTTagCompound()));
   }

   public void setGuiData(NBTTagCompound compound) {
      this.display.readToNBT(compound);
      this.func_73866_w_();
   }
}
