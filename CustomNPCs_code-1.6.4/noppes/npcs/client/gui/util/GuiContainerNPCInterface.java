package noppes.npcs.client.gui.util;

import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.EntityNPCInterface;
import org.lwjgl.input.Keyboard;

public abstract class GuiContainerNPCInterface extends GuiContainer {
   public EntityClientPlayerMP player;
   public EntityNPCInterface npc;
   private HashMap buttons = new HashMap();
   private HashMap textfields = new HashMap();
   private HashMap labels = new HashMap();
   public String title;
   public boolean closeOnEsc = false;

   public GuiContainerNPCInterface(EntityNPCInterface npc, Container cont) {
      super(cont);
      this.player = Minecraft.func_71410_x().field_71439_g;
      this.npc = npc;
      this.title = "Npc Mainmenu";
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.field_73887_h.clear();
      this.buttons.clear();
      Keyboard.enableRepeatEvents(true);
   }

   public ResourceLocation getResource(String texture) {
      return new ResourceLocation("customnpcs", "textures/gui/" + texture);
   }

   public void func_73876_c() {
      for(GuiNpcTextField tf : this.textfields.values()) {
         if (tf.enabled) {
            tf.func_73780_a();
         }
      }

      super.func_73876_c();
   }

   protected void func_73864_a(int i, int j, int k) {
      for(GuiNpcTextField tf : this.textfields.values()) {
         if (tf.enabled) {
            tf.func_73793_a(i, j, k);
         }
      }

      super.func_73864_a(i, j, k);
   }

   protected void func_73869_a(char c, int i) {
      for(GuiNpcTextField tf : this.textfields.values()) {
         tf.func_73802_a(c, i);
      }

      if (this.closeOnEsc && (i == 1 || i == this.field_73882_e.field_71474_y.field_74315_B.field_74512_d)) {
         this.close();
      }

   }

   public void close() {
      this.save();
      this.player.func_71053_j();
      this.field_73882_e.func_71373_a((GuiScreen)null);
      this.field_73882_e.func_71381_h();
   }

   public void addButton(GuiNpcButton button) {
      this.buttons.put(button.field_73741_f, button);
      this.field_73887_h.add(button);
   }

   public GuiNpcButton getButton(int i) {
      return (GuiNpcButton)this.buttons.get(i);
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

   protected void func_74189_g(int par1, int par2) {
   }

   protected void func_74185_a(float f, int i, int j) {
      this.func_73732_a(this.field_73886_k, this.title, this.field_73880_f / 2, 10, 16777215);

      for(GuiNpcLabel label : this.labels.values()) {
         label.drawLabel(this, this.field_73886_k);
      }

      for(GuiNpcTextField tf : this.textfields.values()) {
         tf.func_73795_f();
      }

   }

   public abstract void save();

   public void func_73863_a(int i, int j, float f) {
      super.func_73863_a(i, j, f);
   }

   public FontRenderer getFontRenderer() {
      return this.field_73886_k;
   }
}
