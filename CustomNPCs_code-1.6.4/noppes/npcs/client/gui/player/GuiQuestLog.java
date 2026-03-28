package noppes.npcs.client.gui.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.NoppesStringUtils;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.QuestLogData;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiCustomScrollActionListener;
import noppes.npcs.client.gui.util.GuiMenuSideButton;
import noppes.npcs.client.gui.util.GuiPlayerTopMenu;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.client.gui.util.ITopButtonListener;
import noppes.npcs.constants.EnumPlayerPacket;
import org.lwjgl.opengl.GL11;

public class GuiQuestLog extends GuiScreen implements ITopButtonListener, GuiCustomScrollActionListener, IGuiData {
   private final ResourceLocation resource = new ResourceLocation("customnpcs", "textures/gui/standardbg.png");
   protected int xSize;
   protected int guiLeft;
   protected int guiTop;
   private EntityPlayer player;
   private GuiCustomScroll scroll;
   private HashMap sideButtons = new HashMap();
   private QuestLogData data = new QuestLogData();
   private boolean noQuests = false;
   private GuiPlayerTopMenu topMenu;
   private int yoffset;

   public GuiQuestLog(EntityPlayer player) {
      this.player = player;
      this.xSize = 240;
      NoppesUtilPlayer.sendData(EnumPlayerPacket.QuestLog);
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.sideButtons.clear();
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - 176) / 2;
      this.topMenu = new GuiPlayerTopMenu(this.guiLeft + 35, this.guiTop - 17, 2, this.player);
      this.noQuests = false;
      if (this.data.categories.isEmpty()) {
         this.noQuests = true;
      } else {
         List<String> categories = new ArrayList();
         categories.addAll(this.data.categories.keySet());
         Collections.sort(categories, String.CASE_INSENSITIVE_ORDER);
         int i = 0;

         for(String category : categories) {
            if (this.data.selectedCategory.isEmpty()) {
               this.data.selectedCategory = category;
            }

            this.sideButtons.put(i, new GuiMenuSideButton(i, this.guiLeft - 89, this.guiTop + 2 + i * 21, 90, 22, category));
            ++i;
         }

         ((GuiMenuSideButton)this.sideButtons.get(categories.indexOf(this.data.selectedCategory))).active = true;
         this.scroll = new GuiCustomScroll(this, 0);
         this.scroll.setList((List)this.data.categories.get(this.data.selectedCategory));
         this.scroll.func_73872_a(this.field_73882_e, 350, 250);
         this.scroll.setSize(144, 183);
         this.scroll.guiLeft = this.guiLeft + 6;
         this.scroll.guiTop = this.guiTop + 6;
      }
   }

   public void func_73863_a(int i, int j, float f) {
      this.func_73873_v_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_73882_e.field_71446_o.func_110577_a(this.resource);
      this.func_73729_b(this.guiLeft, this.guiTop, 0, 0, 252, 195);
      this.func_73729_b(this.guiLeft + 252, this.guiTop, 188, 0, 67, 195);
      super.func_73863_a(i, j, f);
      this.topMenu.func_73863_a(i, j, f);
      if (this.noQuests) {
         this.field_73886_k.func_78276_b("You have no active quests", this.guiLeft + 84, this.guiTop + 80, 4210752);
      } else {
         for(GuiMenuSideButton button : (GuiMenuSideButton[])this.sideButtons.values().toArray(new GuiMenuSideButton[this.sideButtons.size()])) {
            button.func_73737_a(this.field_73882_e, i, j);
         }

         this.scroll.func_73863_a(i, j, f);
         if (this.data.hasSelectedQuest()) {
            this.drawQuestText();
         }
      }
   }

   private void drawQuestText() {
      this.yoffset = this.guiTop + 5;
      int x = 0;
      this.drawProgress();
      this.yoffset += 4;
      int xoffset = this.guiLeft + 150;
      String text = NoppesStringUtils.formatText(this.data.getQuestText(), this.player.field_71092_bJ);
      String line = "";

      for(char c : text.toCharArray()) {
         if (c != '\r' && c != '\n') {
            if (this.field_73886_k.func_78256_a(line + c) > 176) {
               this.field_73886_k.func_78276_b(line, xoffset, this.yoffset + x * this.field_73886_k.field_78288_b, 4210752);
               line = "";
               ++x;
            }

            line = line + c;
         } else {
            this.field_73886_k.func_78276_b(line, xoffset, this.yoffset + x * this.field_73886_k.field_78288_b, 4210752);
            line = "";
            ++x;
         }
      }

      this.field_73886_k.func_78276_b(line, xoffset, this.yoffset + x * this.field_73886_k.field_78288_b, 4210752);
   }

   private void drawProgress() {
      int xoffset = this.guiLeft + 152;

      for(String process : this.data.getQuestStatus()) {
         this.field_73886_k.func_78276_b(process, xoffset, this.yoffset, 4210752);
         this.yoffset += 10;
      }

   }

   protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
   }

   public void func_73864_a(int i, int j, int k) {
      super.func_73864_a(i, j, k);
      this.topMenu.func_73864_a(i, j, k);
      if (k == 0) {
         if (this.scroll != null) {
            this.scroll.func_73864_a(i, j, k);
         }

         for(GuiMenuSideButton button : (GuiMenuSideButton[])this.sideButtons.values().toArray(new GuiMenuSideButton[this.sideButtons.size()])) {
            if (button.func_73736_c(this.field_73882_e, i, j)) {
               this.sideButtonPressed(button);
            }
         }
      }

   }

   private void sideButtonPressed(GuiMenuSideButton button) {
      if (!button.active) {
         this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
         this.data.selectedCategory = button.field_73744_e;
         this.data.selectedQuest = "";
         this.func_73866_w_();
      }
   }

   public void customScrollClicked(int i, int j, int k, GuiCustomScroll scroll) {
      if (scroll.hasSelected()) {
         this.data.selectedQuest = scroll.getSelected();
      }
   }

   protected void func_73869_a(char c, int i) {
      if (i == 1 || i == this.field_73882_e.field_71474_y.field_74315_B.field_74512_d) {
         this.field_73882_e.func_71373_a((GuiScreen)null);
         this.field_73882_e.func_71381_h();
      }

   }

   public boolean func_73868_f() {
      return false;
   }

   public void setGuiData(NBTTagCompound compound) {
      QuestLogData data = new QuestLogData();
      data.readNBT(compound);
      this.data = data;
      this.func_73866_w_();
   }
}
