package noppes.npcs.client.gui.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiCustomScroll extends GuiScreen {
   private final ResourceLocation resource;
   private List list;
   public int id;
   public int guiLeft;
   public int guiTop;
   private int xSize;
   private int ySize;
   public int selected;
   private HashSet selectedList;
   private int hover;
   private int listHeight;
   private int scrollY;
   private int maxScrollY;
   private int scrollHeight;
   private boolean isScrolling;
   private boolean multipleSelection;
   private GuiCustomScrollActionListener listener;
   private boolean isSorted;

   public GuiCustomScroll(GuiScreen parent, int id) {
      this.resource = new ResourceLocation("customnpcs", "textures/gui/misc.png");
      this.guiLeft = 0;
      this.guiTop = 0;
      this.multipleSelection = false;
      this.isSorted = true;
      this.field_73880_f = 176;
      this.field_73881_g = 166;
      this.xSize = 176;
      this.ySize = 159;
      this.selected = -1;
      this.hover = -1;
      this.selectedList = new HashSet();
      this.listHeight = 0;
      this.scrollY = 0;
      this.scrollHeight = 0;
      this.isScrolling = false;
      if (parent instanceof GuiCustomScrollActionListener) {
         this.listener = (GuiCustomScrollActionListener)parent;
      }

      this.list = new ArrayList();
      this.id = id;
   }

   public GuiCustomScroll(GuiScreen parent, int id, boolean multipleSelection) {
      this(parent, id);
      this.multipleSelection = multipleSelection;
   }

   public void setSize(int x, int y) {
      this.ySize = y;
      this.xSize = x;
      this.listHeight = 14 * this.list.size();
      this.scrollHeight = (int)((double)(this.ySize - 8) / (double)this.listHeight * (double)(this.ySize - 8));
      this.maxScrollY = this.listHeight - (this.ySize - 8) - 1;
   }

   public void func_73863_a(int i, int j, float f) {
      this.func_73733_a(this.guiLeft, this.guiTop, this.xSize + this.guiLeft, this.ySize + this.guiTop, -1072689136, -804253680);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_73882_e.field_71446_o.func_110577_a(this.resource);
      if (this.scrollHeight < this.ySize - 8) {
         this.drawScrollBar();
      }

      GL11.glPushMatrix();
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      GL11.glPopMatrix();
      GL11.glPushMatrix();
      GL11.glTranslatef((float)this.guiLeft, (float)this.guiTop, 0.0F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.hover = this.getMouseOver(i, j);
      this.drawItems();
      GL11.glPopMatrix();
      if (this.scrollHeight < this.ySize - 8) {
         i -= this.guiLeft;
         j -= this.guiTop;
         if (Mouse.isButtonDown(0)) {
            if (i >= this.xSize - 11 && i < this.xSize - 6 && j >= 4 && j < this.ySize) {
               this.isScrolling = true;
            }
         } else {
            this.isScrolling = false;
         }

         if (this.isScrolling) {
            this.scrollY = (j - 8) * this.listHeight / (this.ySize - 8) - this.scrollHeight;
            if (this.scrollY < 0) {
               this.scrollY = 0;
            }

            if (this.scrollY > this.maxScrollY) {
               this.scrollY = this.maxScrollY;
            }
         }

         int k2 = Mouse.getDWheel();
         if (k2 < 0) {
            this.scrollY += 14;
            if (this.scrollY > this.maxScrollY) {
               this.scrollY = this.maxScrollY;
            }
         } else if (k2 > 0) {
            this.scrollY -= 14;
            if (this.scrollY < 0) {
               this.scrollY = 0;
            }
         }
      }

   }

   public boolean mouseInOption(int i, int j, int k) {
      int l = 4;
      int i1 = 14 * k + 4 - this.scrollY;
      return i >= l - 1 && i < l + this.xSize - 11 && j >= i1 - 1 && j < i1 + 8;
   }

   protected void drawItems() {
      for(int i = 0; i < this.list.size(); ++i) {
         int j = 4;
         int k = 14 * i + 4 - this.scrollY;
         if (k >= 4 && k + 12 < this.ySize) {
            String text = (String)this.list.get(i);
            if ((!this.multipleSelection || !this.selectedList.contains(text)) && (this.multipleSelection || this.selected != i)) {
               if (i == this.hover) {
                  this.field_73886_k.func_78276_b(text, j, k, 65280);
               } else {
                  this.field_73886_k.func_78276_b(text, j, k, 16777215);
               }
            } else {
               this.func_73728_b(j - 2, k - 4, k + 10, -1);
               this.func_73728_b(j + this.xSize - 20, k - 4, k + 10, -1);
               this.func_73730_a(j - 2, j + this.xSize - 20, k - 3, -1);
               this.func_73730_a(j - 2, j + this.xSize - 20, k + 10, -1);
               this.field_73886_k.func_78276_b(text, j, k, 16777215);
            }
         }
      }

   }

   public String getSelected() {
      return this.selected != -1 && this.selected < this.list.size() ? (String)this.list.get(this.selected) : null;
   }

   private int getMouseOver(int i, int j) {
      i -= this.guiLeft;
      j -= this.guiTop;
      if (i >= 4 && i < this.xSize - 4 && j >= 4 && j < this.ySize) {
         for(int j1 = 0; j1 < this.list.size(); ++j1) {
            if (this.mouseInOption(i, j, j1)) {
               return j1;
            }
         }
      }

      return -1;
   }

   public void func_73864_a(int i, int j, int k) {
      if (k == 0 && this.hover >= 0) {
         if (this.multipleSelection) {
            if (this.selectedList.contains(this.list.get(this.hover))) {
               this.selectedList.remove(this.list.get(this.hover));
            } else {
               this.selectedList.add(this.list.get(this.hover));
            }
         } else {
            if (this.hover >= 0) {
               this.selected = this.hover;
            }

            this.hover = -1;
         }

         if (this.listener != null) {
            this.listener.customScrollClicked(i, j, k, this);
         }

      }
   }

   private void drawScrollBar() {
      int i = this.guiLeft + this.xSize - 10;
      int j = this.guiTop + (int)((double)this.scrollY / (double)this.listHeight * (double)(this.ySize - 8)) + 4;
      this.func_73729_b(i, j, this.xSize, 9, 5, 1);

      int k;
      for(k = j + 1; k < j + this.scrollHeight - 1; ++k) {
         this.func_73729_b(i, k, this.xSize, 10, 5, 1);
      }

      this.func_73729_b(i, k, this.xSize, 11, 5, 1);
   }

   public boolean hasSelected() {
      return this.selected >= 0;
   }

   public void setList(List list) {
      this.isSorted = true;
      Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
      this.list = list;
      this.setSize(this.xSize, this.ySize);
   }

   public void setUnsortedList(List list) {
      this.isSorted = false;
      this.list = list;
      this.setSize(this.xSize, this.ySize);
   }

   public void replace(String old, String name) {
      String select = this.getSelected();
      this.list.remove(old);
      this.list.add(name);
      if (this.isSorted) {
         Collections.sort(this.list, String.CASE_INSENSITIVE_ORDER);
      }

      if (old.equals(select)) {
         select = name;
      }

      this.selected = this.list.indexOf(select);
      this.setSize(this.xSize, this.ySize);
   }

   public void setSelected(String name) {
      this.selected = this.list.indexOf(name);
   }

   public void clear() {
      this.list = new ArrayList();
      this.selected = -1;
      this.scrollY = 0;
      this.setSize(this.xSize, this.ySize);
   }

   public List getList() {
      return this.list;
   }

   public HashSet getSelectedList() {
      return this.selectedList;
   }

   public void setSelectedList(HashSet selectedList) {
      this.selectedList = selectedList;
   }
}
