package noppes.npcs.client.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.DataAI;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.constants.EnumPacketType;

public class GuiNpcPather extends GuiNPCInterface implements IGuiData {
   private GuiCustomScroll scroll;
   private HashMap data = new HashMap();
   private DataAI ai;

   public GuiNpcPather(EntityNPCInterface npc) {
      this.drawDefaultBackground = false;
      this.xSize = 176;
      this.title = "Npc Pather";
      this.setBackground("smallbg.png");
      this.ai = npc.ai;
      NoppesUtil.sendData(EnumPacketType.MainmenuAIGet);
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.scroll = new GuiCustomScroll(this, 0);
      this.scroll.setSize(160, 164);
      List<String> list = new ArrayList();

      for(int[] arr : this.ai.getMovingPath()) {
         list.add("x:" + arr[0] + " y:" + arr[1] + " z:" + arr[2]);
      }

      this.scroll.setUnsortedList(list);
      this.scroll.guiLeft = this.guiLeft + 7;
      this.scroll.guiTop = this.guiTop + 12;
      this.addScroll(this.scroll, this.field_73882_e);
      this.addButton(new GuiNpcButton(0, this.guiLeft + 6, this.guiTop + 178, 52, 20, "gui.down"));
      this.addButton(new GuiNpcButton(1, this.guiLeft + 62, this.guiTop + 178, 52, 20, "gui.up"));
      this.addButton(new GuiNpcButton(2, this.guiLeft + 118, this.guiTop + 178, 52, 20, "selectWorld.deleteButton"));
   }

   protected void func_73875_a(GuiButton guibutton) {
      if (this.scroll.selected >= 0) {
         if (guibutton.field_73741_f == 0) {
            List<int[]> list = this.ai.getMovingPath();
            int selected = this.scroll.selected;
            if (list.size() <= selected + 1) {
               return;
            }

            int[] a = (int[])list.get(selected);
            int[] b = (int[])list.get(selected + 1);
            list.set(selected, b);
            list.set(selected + 1, a);
            this.ai.setMovingPath(list);
            this.func_73866_w_();
            this.scroll.selected = selected + 1;
         }

         if (guibutton.field_73741_f == 1) {
            if (this.scroll.selected - 1 < 0) {
               return;
            }

            List<int[]> list = this.ai.getMovingPath();
            int selected = this.scroll.selected;
            int[] a = (int[])list.get(selected);
            int[] b = (int[])list.get(selected - 1);
            list.set(selected, b);
            list.set(selected - 1, a);
            this.ai.setMovingPath(list);
            this.func_73866_w_();
            this.scroll.selected = selected - 1;
         }

         if (guibutton.field_73741_f == 2) {
            List<int[]> list = this.ai.getMovingPath();
            if (list.size() <= 1) {
               return;
            }

            list.remove(this.scroll.selected);
            this.ai.setMovingPath(list);
            this.func_73866_w_();
         }

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
      NoppesUtil.sendData(EnumPacketType.MainmenuAISave, this.ai.writeToNBT(new NBTTagCompound()));
   }

   public void setGuiData(NBTTagCompound compound) {
      this.ai.readToNBT(compound);
      this.func_73866_w_();
   }
}
