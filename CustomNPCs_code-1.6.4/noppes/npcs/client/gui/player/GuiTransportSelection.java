package noppes.npcs.client.gui.player;

import java.util.HashMap;
import java.util.Vector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.IScrollData;
import noppes.npcs.client.gui.util.ITopButtonListener;
import noppes.npcs.constants.EnumPlayerPacket;
import org.lwjgl.opengl.GL11;

public class GuiTransportSelection extends GuiNPCInterface implements ITopButtonListener, IScrollData {
   private final ResourceLocation resource = new ResourceLocation("customnpcs", "textures/gui/smallbg.png");
   protected int xSize = 176;
   protected int guiLeft;
   protected int guiTop;
   private GuiCustomScroll scroll;

   public GuiTransportSelection(EntityNPCInterface npc) {
      super(npc);
      this.drawDefaultBackground = false;
      this.title = "";
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - 222) / 2;
      String name = "";
      this.addLabel(new GuiNpcLabel(0, name, this.guiLeft + (this.xSize - this.field_73886_k.func_78256_a(name)) / 2, this.guiTop + 10, 4210752));
      this.addButton(new GuiNpcButton(0, this.guiLeft + 10, this.guiTop + 192, 156, 20, StatCollector.func_74838_a("transporter.travel")));
      this.scroll = new GuiCustomScroll(this, 0);
      this.scroll.func_73872_a(this.field_73882_e, 350, 250);
      this.scroll.setSize(156, 165);
      this.scroll.guiLeft = this.guiLeft + 10;
      this.scroll.guiTop = this.guiTop + 20;
   }

   public void func_73863_a(int i, int j, float f) {
      this.func_73873_v_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_73882_e.field_71446_o.func_110577_a(this.resource);
      this.func_73729_b(this.guiLeft, this.guiTop, 0, 0, 176, 222);
      super.func_73863_a(i, j, f);
      this.scroll.func_73863_a(i, j, f);
   }

   protected void func_73875_a(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      String sel = this.scroll.getSelected();
      if (button.field_73741_f == 0 && sel != null) {
         this.close();
         NoppesUtilPlayer.sendData(EnumPlayerPacket.Transport, sel);
      }

   }

   protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
   }

   public void func_73864_a(int i, int j, int k) {
      super.func_73864_a(i, j, k);
      this.scroll.func_73864_a(i, j, k);
   }

   public void func_73869_a(char c, int i) {
      if (i == 1 || i == this.field_73882_e.field_71474_y.field_74315_B.field_74512_d) {
         this.close();
      }

   }

   public void save() {
   }

   public void setData(Vector list, HashMap data) {
      this.scroll.setList(list);
   }

   public void setSelected(String selected) {
   }
}
