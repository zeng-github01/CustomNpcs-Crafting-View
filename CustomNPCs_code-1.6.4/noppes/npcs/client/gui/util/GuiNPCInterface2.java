package noppes.npcs.client.gui.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.constants.EnumPacketType;
import org.lwjgl.opengl.GL11;

public abstract class GuiNPCInterface2 extends GuiScreen {
   public int guiLeft;
   public int guiTop;
   public int guiWidth;
   public EntityPlayer player;
   public boolean drawDefaultBackground;
   public EntityNPCInterface npc;
   private ResourceLocation background;
   private GuiNpcMenu menu;
   private SubGuiInterface subgui;

   public GuiNPCInterface2(EntityNPCInterface npc) {
      this(npc, -1);
   }

   public GuiNPCInterface2(EntityNPCInterface npc, int activeMenu) {
      this.drawDefaultBackground = true;
      this.background = new ResourceLocation("customnpcs:textures/gui/menubg.png");
      this.player = Minecraft.func_71410_x().field_71439_g;
      this.npc = npc;
      this.guiWidth = 420;
      this.menu = new GuiNpcMenu(this, activeMenu, npc);
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      if (this.subgui != null) {
         this.subgui.func_73872_a(this.field_73882_e, this.field_73880_f, this.field_73881_g);
         this.subgui.func_73866_w_();
      }

      this.field_73887_h.clear();
      this.guiLeft = (this.field_73880_f - this.guiWidth) / 2;
      this.guiTop = (this.field_73881_g - 200) / 2;
      this.menu.initGui(this.guiLeft, this.guiTop, this.guiWidth);
   }

   public void func_73876_c() {
      this.menu.updateScreen();
      super.func_73876_c();
   }

   public void setSubGui(SubGuiInterface gui) {
      this.subgui = gui;
      this.subgui.func_73872_a(this.field_73882_e, this.field_73880_f, this.field_73881_g);
      this.subgui.parent = this;
      this.func_73866_w_();
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

   public void clearAllTextFields() {
      this.menu.clearAllTextFields();
   }

   public void func_73878_a(boolean flag, int i) {
      if (flag) {
         NoppesUtil.sendData(EnumPacketType.Delete);
         this.field_73882_e.func_71373_a((GuiScreen)null);
         this.field_73882_e.func_71381_h();
      } else {
         this.field_73882_e.func_71373_a(this);
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

   public void addScroll(GuiCustomScroll scroll) {
      this.menu.addScroll(scroll, this.field_73882_e);
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

   public abstract void save();

   public void func_73863_a(int i, int j, float f) {
      if (this.drawDefaultBackground && this.subgui == null) {
         this.func_73873_v_();
      }

      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_73882_e.field_71446_o.func_110577_a(this.background);
      this.func_73729_b(this.guiLeft, this.guiTop, 0, 0, 200, 220);
      this.func_73729_b(this.guiLeft + this.guiWidth - 230, this.guiTop, 26, 0, 230, 220);
      this.menu.drawElements(this.field_73886_k, i, j, this.field_73882_e, f);
      super.func_73863_a(i, j, f);
      if (this.subgui != null) {
         this.subgui.func_73863_a(i, j, f);
      }

   }

   public FontRenderer getFontRenderer() {
      return this.field_73886_k;
   }

   public boolean drawSlot(int i, int j, int k, int l, Tessellator tessellator, String drawString) {
      return false;
   }

   public void elementClicked() {
      if (this.subgui != null) {
         this.subgui.elementClicked();
      }

   }

   public void doubleClicked() {
   }

   public boolean func_73868_f() {
      return false;
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
