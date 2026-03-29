package noppes.npcs.client.gui.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;
import noppes.npcs.EntityNPCInterface;

public class GuiNPCStringSlot extends GuiSlot {
   private List list;
   public String selected;
   public HashSet selectedList = new HashSet();
   private boolean multiSelect;
   private GuiNPCInterface parent;
   public int size;
   private long prevTime = 0L;

   public GuiNPCStringSlot(List list, GuiNPCInterface parent, EntityNPCInterface npc, boolean multiSelect, int size) {
      super(Minecraft.func_71410_x(), parent.field_73880_f, parent.field_73881_g, 32, parent.field_73881_g - 64, size);
      this.parent = parent;
      Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
      this.list = list;
      this.multiSelect = multiSelect;
      this.size = size;
   }

   public void setList(List list) {
      Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
      this.list = list;
      this.selected = "";
   }

   protected int func_77217_a() {
      return this.list.size();
   }

   protected void func_77213_a(int i, boolean flag) {
      long time = System.currentTimeMillis();
      if (this.selected != null && this.selected.equals(this.list.get(i)) && time - this.prevTime < 400L) {
         this.parent.doubleClicked();
      }

      this.selected = (String)this.list.get(i);
      if (this.selectedList.contains(this.selected)) {
         this.selectedList.remove(this.selected);
      } else {
         this.selectedList.add(this.selected);
      }

      this.parent.elementClicked();
      this.prevTime = time;
   }

   protected boolean func_77218_a(int i) {
      if (!this.multiSelect) {
         return this.selected == null ? false : this.selected.equals(this.list.get(i));
      } else {
         return this.selectedList.contains(this.list.get(i));
      }
   }

   protected int func_77212_b() {
      return this.list.size() * this.size;
   }

   protected void func_77221_c() {
      this.parent.func_73873_v_();
   }

   protected void func_77214_a(int i, int j, int k, int l, Tessellator tessellator) {
      String s = (String)this.list.get(i);
      if (!this.parent.drawSlot(i, j, k, l, tessellator, s)) {
         this.parent.func_73731_b(this.parent.getFontRenderer(), s, j + 50, k + 3, 16777215);
      }

   }

   public void clear() {
      this.list.clear();
   }
}
