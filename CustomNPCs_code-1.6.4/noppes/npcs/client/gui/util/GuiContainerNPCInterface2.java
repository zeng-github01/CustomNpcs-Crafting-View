package noppes.npcs.client.gui.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.constants.EnumPacketType;
import org.lwjgl.opengl.GL11;

public abstract class GuiContainerNPCInterface2 extends GuiContainer {
   public int field_74198_m;
   public int field_74197_n;
   public EntityPlayer player;
   public boolean drawDefaultBackground;
   public EntityNPCInterface npc;
   private ResourceLocation background;
   private final ResourceLocation defaultBackground;
   private GuiNpcMenu menu;
   private SubGuiInterface subgui;

   public GuiContainerNPCInterface2(EntityNPCInterface npc, Container cont) {
      this(npc, cont, -1);
   }

   public GuiContainerNPCInterface2(EntityNPCInterface npc, Container cont, int activeMenu) {
      super(cont);
      this.drawDefaultBackground = false;
      this.background = new ResourceLocation("customnpcs", "textures/gui/menubg.png");
      this.defaultBackground = new ResourceLocation("customnpcs", "textures/gui/menubg.png");
      this.player = Minecraft.func_71410_x().field_71439_g;
      this.npc = npc;
      this.field_74194_b = 420;
      this.menu = new GuiNpcMenu(this, activeMenu, npc);
   }

   public void setBackground(String texture) {
      this.background = new ResourceLocation("customnpcs", "textures/gui/" + texture);
   }

   public ResourceLocation getResource(String texture) {
      return new ResourceLocation("customnpcs", "textures/gui/" + texture);
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      if (this.subgui != null) {
         this.subgui.func_73872_a(this.field_73882_e, this.field_73880_f, this.field_73881_g);
         this.subgui.func_73866_w_();
      }

      this.field_73887_h.clear();
      this.field_74198_m = (this.field_73880_f - this.field_74194_b) / 2;
      this.field_74197_n = (this.field_73881_g - 200) / 2;
      this.menu.initGui(this.field_74198_m, this.field_74197_n, this.field_74194_b);
   }

   public void setSubGui(SubGuiInterface gui) {
      this.subgui = gui;
      this.subgui.func_73872_a(this.field_73882_e, this.field_73880_f, this.field_73881_g);
      this.subgui.parent = this;
      this.func_73866_w_();
   }

   public void func_73876_c() {
      this.menu.updateScreen();
      super.func_73876_c();
   }

   protected void func_73864_a(int i, int j, int k) {
      if (this.subgui != null) {
         this.subgui.func_73864_a(i, j, k);
      } else {
         this.menu.mouseClicked(i, j, k);
         this.mouseEvent(i, j, k);
         super.func_73864_a(i, j, k);
      }

   }

   public void mouseEvent(int i, int j, int k) {
   }

   protected void func_73875_a(GuiButton guibutton) {
      if (this.subgui != null) {
         this.subgui.buttonEvent(guibutton);
      } else {
         this.buttonEvent(guibutton);
      }

   }

   public void buttonEvent(GuiButton guibutton) {
   }

   public void func_73878_a(boolean flag, int i) {
      if (flag) {
         NoppesUtil.sendData(EnumPacketType.Delete);
         this.field_73882_e.func_71373_a((GuiScreen)null);
         this.field_73882_e.func_71381_h();
      } else {
         NoppesUtil.openGUI(this.player, this);
      }

   }

   public void func_73869_a(char c, int i) {
      if (this.subgui != null) {
         this.subgui.func_73869_a(c, i);
      } else {
         this.menu.keyTyped(c, i);
      }

   }

   public void close() {
      GuiNpcTextField.unfocus();
      this.save();
      this.field_73882_e.func_71373_a((GuiScreen)null);
      this.field_73882_e.func_71381_h();
   }

   public void addButton(GuiNpcButton button) {
      this.menu.addButton(button);
      this.field_73887_h.add(button);
   }

   public GuiNpcButton getButton(int i) {
      return this.menu.getButton(i);
   }

   public void addSlider(GuiNpcSlider slider) {
      this.menu.addSlider(slider);
      this.field_73887_h.add(slider);
   }

   public GuiNpcSlider getSlider(int i) {
      return this.menu.getSlider(i);
   }

   public void addTextField(GuiNpcTextField tf) {
      this.menu.addTextField(tf);
   }

   public GuiNpcTextField getTextField(int i) {
      return this.menu.getTextField(i);
   }

   public void addLabel(GuiNpcLabel label) {
      this.menu.addLabel(label);
   }

   public GuiNpcLabel getLabel(int i) {
      return this.menu.getLabel(i);
   }

   public void delete() {
      this.npc.delete();
      this.field_73882_e.func_71373_a((GuiScreen)null);
      this.field_73882_e.func_71381_h();
   }

   public void func_73873_v_() {
      if (this.drawDefaultBackground && this.subgui == null) {
         this.func_73859_b(0);
      }

   }

   protected void func_74185_a(float f, int i, int j) {
      this.func_73873_v_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_73882_e.field_71446_o.func_110577_a(this.background);
      this.func_73729_b(this.field_74198_m, this.field_74197_n, 0, 0, 256, 256);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_73882_e.field_71446_o.func_110577_a(this.defaultBackground);
      this.func_73729_b(this.field_74198_m + this.field_74194_b - 200, this.field_74197_n, 26, 0, 200, 220);
      this.menu.drawElements(this.field_73886_k, i, j, this.field_73882_e, f);
   }

   public abstract void save();

   public void func_73863_a(int i, int j, float f) {
      super.func_73863_a(i, j, f);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      if (this.subgui != null) {
         RenderHelper.func_74518_a();
         this.subgui.func_73863_a(i, j, f);
      }

   }

   protected void func_74192_a(Slot par1Slot) {
      if (this.subgui == null) {
         super.func_74192_a(par1Slot);
      }

   }

   public FontRenderer getFontRenderer() {
      return this.field_73886_k;
   }

   public void closeSubGui(SubGuiInterface gui) {
      this.subgui = null;
   }

   public boolean hasSubGui() {
      return this.subgui != null;
   }

   public void setSubGuiData(NBTTagCompound compound) {
      ((IGuiData)this.subgui).setGuiData(compound);
   }

   public SubGuiInterface getSubGui() {
      return this.subgui;
   }
}
