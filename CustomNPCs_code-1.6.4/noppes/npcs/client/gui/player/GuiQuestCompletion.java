package noppes.npcs.client.gui.player;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import noppes.npcs.NoppesStringUtils;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.ITopButtonListener;
import noppes.npcs.constants.EnumPlayerPacket;
import noppes.npcs.controllers.Quest;
import org.lwjgl.opengl.GL11;

public class GuiQuestCompletion extends GuiNPCInterface implements ITopButtonListener {
   private int xSize = 176;
   private int ySize = 222;
   private int guiLeft;
   private int guiTop;
   private Quest quest;
   private final ResourceLocation resource = new ResourceLocation("customnpcs", "textures/gui/smallbg.png");

   public GuiQuestCompletion(Quest quest) {
      this.quest = quest;
      this.drawDefaultBackground = false;
      this.title = "";
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
      this.addButton(new GuiNpcButton(0, this.guiLeft + 38, this.guiTop + this.ySize - 24, 100, 20, StatCollector.func_74838_a("quest.complete")));
   }

   public void func_73863_a(int i, int j, float f) {
      this.func_73873_v_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_73882_e.field_71446_o.func_110577_a(this.resource);
      this.func_73729_b(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
      String questTitle = this.quest.title;
      int left = (this.xSize - this.field_73886_k.func_78256_a(questTitle)) / 2;
      this.addLabel(new GuiNpcLabel(0, questTitle, this.guiLeft + left, this.guiTop + 5, 4210752));
      this.drawQuestText();
      super.func_73863_a(i, j, f);
   }

   private void drawQuestText() {
      int x = 0;
      int yoffset = this.guiTop + 20;
      int xoffset = this.guiLeft + 4;
      String text = NoppesStringUtils.formatText(this.quest.completeText, this.player.field_71092_bJ);
      String line = "";

      for(char c : text.toCharArray()) {
         if (c != '\r' && c != '\n') {
            if (this.field_73886_k.func_78256_a(line + c) > 176) {
               this.field_73886_k.func_78276_b(line, xoffset, yoffset + x * this.field_73886_k.field_78288_b, 4210752);
               line = "";
               ++x;
            }

            line = line + c;
         } else {
            this.field_73886_k.func_78276_b(line, xoffset, yoffset + x * this.field_73886_k.field_78288_b, 4210752);
            line = "";
            ++x;
         }
      }

      this.field_73886_k.func_78276_b(line, xoffset, yoffset + x * this.field_73886_k.field_78288_b, 4210752);
   }

   protected void func_73875_a(GuiButton guibutton) {
      if (guibutton.field_73741_f == 0) {
         NoppesUtilPlayer.sendData(EnumPlayerPacket.QuestCompletion, this.quest.id);
         this.close();
      }

   }

   protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
   }

   public void func_73864_a(int i, int j, int k) {
      super.func_73864_a(i, j, k);
   }

   public void func_73869_a(char c, int i) {
      if (i == 1 || i == this.field_73882_e.field_71474_y.field_74315_B.field_74512_d) {
         this.close();
      }

   }

   public void save() {
   }
}
