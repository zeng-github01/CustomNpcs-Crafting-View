package noppes.npcs.client.gui.util;

import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.util.StatCollector;
import noppes.npcs.CustomNpcs;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.constants.EnumPacketType;
import org.lwjgl.input.Keyboard;

public class GuiNpcMenu {
   private GuiScreen parent;
   private HashMap buttons = new HashMap();
   private HashMap sliders = new HashMap();
   private HashMap textfields = new HashMap();
   private HashMap labels = new HashMap();
   private HashMap scrolls = new HashMap();
   private GuiMenuTopButton[] topButtons;
   private int activeMenu;
   private EntityNPCInterface npc;

   public GuiNpcMenu(GuiScreen parent, int activeMenu, EntityNPCInterface npc) {
      this.parent = parent;
      this.activeMenu = activeMenu;
      this.npc = npc;
   }

   public void initGui(int guiLeft, int guiTop, int width) {
      this.labels.clear();
      this.textfields.clear();
      this.buttons.clear();
      this.sliders.clear();
      this.scrolls.clear();
      Keyboard.enableRepeatEvents(true);
      GuiMenuTopButton display = new GuiMenuTopButton(1, guiLeft + 4, guiTop - 17, "menu.display");
      GuiMenuTopButton stats = new GuiMenuTopButton(2, display.field_73746_c + display.getWidth(), guiTop - 17, "menu.stats");
      GuiMenuTopButton ai = new GuiMenuTopButton(6, stats.field_73746_c + stats.getWidth(), guiTop - 17, "menu.ai");
      GuiMenuTopButton inv = new GuiMenuTopButton(3, ai.field_73746_c + ai.getWidth(), guiTop - 17, "menu.inventory");
      GuiMenuTopButton advanced = new GuiMenuTopButton(4, inv.field_73746_c + inv.getWidth(), guiTop - 17, "menu.advanced");
      GuiMenuTopButton global = new GuiMenuTopButton(5, advanced.field_73746_c + advanced.getWidth(), guiTop - 17, "menu.global");
      GuiMenuTopButton close = new GuiMenuTopButton(0, guiLeft + width - 22, guiTop - 17, "X");
      GuiMenuTopButton delete = new GuiMenuTopButton(66, guiLeft + width - 72, guiTop - 17, "selectWorld.deleteButton");
      delete.field_73746_c = close.field_73746_c - delete.getWidth();
      this.topButtons = new GuiMenuTopButton[]{display, stats, ai, inv, advanced, global, close, delete};

      for(GuiMenuTopButton button : this.topButtons) {
         button.active = button.field_73741_f == this.activeMenu;
      }

   }

   private void topButtonPressed(GuiMenuTopButton button) {
      if (!button.field_73744_e.equals(this.activeMenu)) {
         Minecraft mc = Minecraft.func_71410_x();
         mc.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
         if (button.field_73741_f == 0) {
            this.close();
         } else if (button.field_73741_f == 66) {
            GuiYesNo guiyesno = new GuiYesNo(this.parent, "Confirm", StatCollector.func_74838_a("gui.delete"), 0);
            mc.func_71373_a(guiyesno);
         } else {
            this.save();
            if (button.field_73741_f == 1) {
               CustomNpcs.proxy.openGui(this.npc, EnumGuiType.MainMenuDisplay);
            } else if (button.field_73741_f == 2) {
               CustomNpcs.proxy.openGui(this.npc, EnumGuiType.MainMenuStats);
            } else if (button.field_73741_f == 3) {
               NoppesUtil.requestOpenGUI(EnumGuiType.MainMenuInv);
            } else if (button.field_73741_f == 4) {
               CustomNpcs.proxy.openGui(this.npc, EnumGuiType.MainMenuAdvanced);
            } else if (button.field_73741_f == 5) {
               CustomNpcs.proxy.openGui(this.npc, EnumGuiType.MainMenuGlobal);
            } else if (button.field_73741_f == 6) {
               CustomNpcs.proxy.openGui(this.npc, EnumGuiType.MainMenuAI);
            }

            this.activeMenu = button.field_73741_f;
         }
      }
   }

   private void save() {
      GuiNpcTextField.unfocus();
      if (this.parent instanceof GuiContainerNPCInterface2) {
         ((GuiContainerNPCInterface2)this.parent).save();
      }

      if (this.parent instanceof GuiNPCInterface2) {
         ((GuiNPCInterface2)this.parent).save();
      }

   }

   private void close() {
      if (this.parent instanceof GuiContainerNPCInterface2) {
         ((GuiContainerNPCInterface2)this.parent).close();
      }

      if (this.parent instanceof GuiNPCInterface2) {
         ((GuiNPCInterface2)this.parent).close();
      }

      if (this.npc != null) {
         this.npc.reset();
         NoppesUtil.sendData(EnumPacketType.RemoteReset, this.npc.field_70157_k);
      }

   }

   public void updateScreen() {
      for(GuiNpcTextField tf : this.textfields.values()) {
         if (tf.enabled) {
            tf.func_73780_a();
         }
      }

   }

   public void mouseClicked(int i, int j, int k) {
      for(GuiNpcTextField tf : this.textfields.values()) {
         if (tf.enabled) {
            tf.func_73793_a(i, j, k);
         }
      }

      if (k == 0) {
         Minecraft mc = Minecraft.func_71410_x();

         for(GuiMenuTopButton button : this.topButtons) {
            if (button.func_73736_c(mc, i, j)) {
               this.topButtonPressed(button);
            }
         }

         for(GuiCustomScroll scroll : this.scrolls.values()) {
            scroll.func_73864_a(i, j, k);
         }
      }

   }

   public void keyTyped(char c, int i) {
      for(GuiNpcTextField tf : this.textfields.values()) {
         tf.func_73802_a(c, i);
      }

      if (i == 1) {
         this.close();
      }

   }

   public void addButton(GuiNpcButton button) {
      this.buttons.put(button.field_73741_f, button);
   }

   public GuiNpcButton getButton(int i) {
      return (GuiNpcButton)this.buttons.get(i);
   }

   public void addSlider(GuiNpcSlider slider) {
      this.sliders.put(slider.field_73741_f, slider);
   }

   public GuiNpcSlider getSlider(int i) {
      return (GuiNpcSlider)this.sliders.get(i);
   }

   public void addTextField(GuiNpcTextField tf) {
      this.textfields.put(tf.id, tf);
   }

   public GuiNpcTextField getTextField(int i) {
      return (GuiNpcTextField)this.textfields.get(i);
   }

   public void addLabel(GuiNpcLabel label) {
      this.labels.put(label.id, label);
   }

   public GuiNpcLabel getLabel(int i) {
      return (GuiNpcLabel)this.labels.get(i);
   }

   public void addScroll(GuiCustomScroll scroll, Minecraft mc) {
      scroll.func_73872_a(mc, 350, 250);
      this.scrolls.put(scroll.id, scroll);
   }

   public void drawElements(FontRenderer fontRenderer, int i, int j, Minecraft mc, float f) {
      for(GuiNpcLabel label : this.labels.values()) {
         label.drawLabel(this.parent, fontRenderer);
      }

      for(GuiNpcTextField tf : this.textfields.values()) {
         tf.func_73795_f();
      }

      for(GuiMenuTopButton button : this.topButtons) {
         button.func_73737_a(mc, i, j);
      }

      for(GuiCustomScroll scroll : this.scrolls.values()) {
         scroll.func_73863_a(i, j, f);
      }

   }

   public void clearAllTextFields() {
      for(GuiNpcTextField textfield : this.textfields.values()) {
         textfield.func_73782_a("0");
      }

   }
}
