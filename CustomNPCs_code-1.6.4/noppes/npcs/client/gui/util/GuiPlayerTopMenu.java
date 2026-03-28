package noppes.npcs.client.gui.util;

import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.player.GuiFaction;
import noppes.npcs.client.gui.player.GuiQuestLog;

public class GuiPlayerTopMenu extends GuiScreen implements ITopButtonListener {
   private int x;
   private int y;
   private int active;
   private EntityPlayer player;
   private HashMap topButtons = new HashMap();

   public GuiPlayerTopMenu(int x, int y, int active, EntityPlayer player) {
      this.player = player;
      this.x = x;
      this.y = y;
      this.active = active;
      this.func_73866_w_();
      this.field_73882_e = Minecraft.func_71410_x();
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.topButtons.clear();
      GuiMenuTopButton inv = new GuiMenuTopButton(1, this.x, this.y, "menu.inventory");
      GuiMenuTopButton quests = new GuiMenuTopButton(2, inv, "quest.quests");
      GuiMenuTopButton factions = new GuiMenuTopButton(3, quests, "menu.factions");
      this.topButtons.put(1, inv);
      this.topButtons.put(2, quests);
      this.topButtons.put(3, factions);
      ((GuiMenuTopButton)this.topButtons.get(this.active)).active = true;
   }

   public void func_73863_a(int i, int j, float f) {
      for(GuiMenuTopButton button : this.topButtons.values()) {
         button.func_73737_a(this.field_73882_e, i, j);
      }

   }

   protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
   }

   public void func_73864_a(int i, int j, int k) {
      super.func_73864_a(i, j, k);
      if (k == 0) {
         for(GuiMenuTopButton button : this.topButtons.values()) {
            if (button.func_73736_c(this.field_73882_e, i, j)) {
               this.topButtonPressed(button);
            }
         }
      }

   }

   private void topButtonPressed(GuiMenuTopButton button) {
      if (!button.active) {
         this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
         if (button.field_73741_f == 1) {
            NoppesUtil.openGUI(this.player, new GuiInventory(this.player));
         }

         if (button.field_73741_f == 2) {
            NoppesUtil.openGUI(this.player, new GuiQuestLog(this.field_73882_e.field_71439_g));
         }

         if (button.field_73741_f == 3) {
            NoppesUtil.openGUI(this.player, new GuiFaction());
         }

      }
   }
}
