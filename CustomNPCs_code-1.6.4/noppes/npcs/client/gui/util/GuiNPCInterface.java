package noppes.npcs.client.gui.util;

import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.EntityNPCInterface;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public abstract class GuiNPCInterface extends GuiScreen {
   public EntityPlayer player;
   public boolean drawDefaultBackground;
   public EntityNPCInterface npc;
   private HashMap buttons;
   private HashMap topbuttons;
   private HashMap sidebuttons;
   private HashMap textfields;
   private HashMap labels;
   private HashMap scrolls;
   private HashMap sliders;
   public String title;
   private ResourceLocation background;
   public boolean closeOnEsc;
   public int guiLeft;
   public int guiTop;
   public int xSize;
   public int ySize;

   public GuiNPCInterface(EntityNPCInterface npc) {
      this.drawDefaultBackground = true;
      this.buttons = new HashMap();
      this.topbuttons = new HashMap();
      this.sidebuttons = new HashMap();
      this.textfields = new HashMap();
      this.labels = new HashMap();
      this.scrolls = new HashMap();
      this.sliders = new HashMap();
      this.background = null;
      this.closeOnEsc = false;
      this.player = Minecraft.func_71410_x().field_71439_g;
      this.npc = npc;
      this.title = "";
      this.xSize = 200;
      this.ySize = 222;
   }

   public GuiNPCInterface() {
      this((EntityNPCInterface)null);
   }

   public void setBackground(String texture) {
      this.background = new ResourceLocation("customnpcs", "textures/gui/" + texture);
   }

   public ResourceLocation getResource(String texture) {
      return new ResourceLocation("customnpcs", "textures/gui/" + texture);
   }

   public void func_73866_w_() {
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
      this.field_73887_h.clear();
      this.labels.clear();
      this.textfields.clear();
      this.buttons.clear();
      this.sidebuttons.clear();
      this.topbuttons.clear();
      this.scrolls.clear();
      this.sliders.clear();
      Keyboard.enableRepeatEvents(true);
   }

   public void func_73876_c() {
      for(GuiNpcTextField tf : this.textfields.values()) {
         if (tf.enabled) {
            tf.func_73780_a();
         }
      }

      super.func_73876_c();
   }

   public void func_73864_a(int i, int j, int k) {
      for(GuiNpcTextField tf : (GuiNpcTextField[])this.textfields.values().toArray(new GuiNpcTextField[this.textfields.size()])) {
         if (tf.enabled) {
            tf.func_73793_a(i, j, k);
         }
      }

      if (k == 0) {
         for(GuiCustomScroll scroll : this.scrolls.values()) {
            scroll.func_73864_a(i, j, k);
         }
      }

      super.func_73864_a(i, j, k);
   }

   protected void func_73875_a(GuiButton par1GuiButton) {
      this.buttonEvent(par1GuiButton);
   }

   public void buttonEvent(GuiButton guibutton) {
   }

   public void func_73869_a(char c, int i) {
      for(GuiNpcTextField tf : this.textfields.values()) {
         tf.func_73802_a(c, i);
      }

      if (this.closeOnEsc && i == 1) {
         this.close();
      }

   }

   public void close() {
      this.field_73882_e.func_71373_a((GuiScreen)null);
      this.field_73882_e.func_71381_h();
      this.save();
   }

   public void addButton(int i, GuiNpcButton button) {
      this.buttons.put(i, button);
      this.field_73887_h.add(button);
   }

   public void addButton(GuiNpcButton button) {
      this.buttons.put(button.field_73741_f, button);
      this.field_73887_h.add(button);
   }

   public void addTopButton(GuiMenuTopButton button) {
      this.topbuttons.put(button.field_73741_f, button);
      this.field_73887_h.add(button);
   }

   public void addSideButton(GuiMenuSideButton button) {
      this.sidebuttons.put(button.field_73741_f, button);
      this.field_73887_h.add(button);
   }

   public GuiNpcButton getButton(int i) {
      return (GuiNpcButton)this.buttons.get(i);
   }

   public GuiMenuSideButton getSideButton(int i) {
      return (GuiMenuSideButton)this.sidebuttons.get(i);
   }

   public void addTextField(int i, GuiNpcTextField tf) {
      this.textfields.put(i, tf);
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

   public void addSlider(GuiNpcSlider slider) {
      this.sliders.put(slider.field_73741_f, slider);
      this.field_73887_h.add(slider);
   }

   public GuiNpcSlider getSlider(int i) {
      return (GuiNpcSlider)this.sliders.get(i);
   }

   public GuiCustomScroll getScroll(int id) {
      return (GuiCustomScroll)this.scrolls.get(id);
   }

   public abstract void save();

   public void func_73863_a(int i, int j, float f) {
      if (this.drawDefaultBackground) {
         this.func_73873_v_();
      }

      if (this.background != null && this.field_73882_e.field_71446_o != null) {
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_73882_e.field_71446_o.func_110577_a(this.background);
         this.func_73729_b(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
      }

      this.func_73732_a(this.field_73886_k, this.title, this.field_73880_f / 2, 10, 16777215);

      for(GuiNpcLabel label : this.labels.values()) {
         label.drawLabel(this, this.field_73886_k);
      }

      for(GuiNpcTextField tf : this.textfields.values()) {
         tf.func_73795_f();
      }

      for(GuiCustomScroll scroll : this.scrolls.values()) {
         scroll.func_73863_a(i, j, f);
      }

      super.func_73863_a(i, j, f);
   }

   public FontRenderer getFontRenderer() {
      return this.field_73886_k;
   }

   public boolean drawSlot(int i, int j, int k, int l, Tessellator tessellator, String drawString) {
      return false;
   }

   public void elementClicked() {
   }

   public boolean func_73868_f() {
      return false;
   }

   public void doubleClicked() {
   }
}
