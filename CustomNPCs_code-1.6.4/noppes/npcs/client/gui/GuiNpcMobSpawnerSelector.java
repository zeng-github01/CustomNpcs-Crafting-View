package noppes.npcs.client.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.client.controllers.CloneController;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiMenuSideButton;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.SubGuiInterface;

public class GuiNpcMobSpawnerSelector extends SubGuiInterface {
   private GuiCustomScroll scroll;
   private HashMap cloneData = new HashMap();
   private ArrayList list;
   private static String search = "";
   private int activeTab = 1;

   public GuiNpcMobSpawnerSelector() {
      this.xSize = 256;
      this.closeOnEsc = true;
      this.setBackground("menubg.png");
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      if (this.scroll == null) {
         this.scroll = new GuiCustomScroll(this, 0);
         this.scroll.setSize(165, 188);
         this.scroll.guiLeft = this.guiLeft + 4;
         this.scroll.guiTop = this.guiTop + 26;
      } else {
         this.scroll.clear();
      }

      this.addScroll(this.scroll, this.field_73882_e);
      this.addTextField(new GuiNpcTextField(1, this, this.field_73886_k, this.guiLeft + 4, this.guiTop + 4, 165, 20, search));
      this.addButton(new GuiNpcButton(0, this.guiLeft + 171, this.guiTop + 80, 80, 20, "gui.done"));
      this.addButton(new GuiNpcButton(1, this.guiLeft + 171, this.guiTop + 103, 80, 20, "gui.cancel"));
      this.addSideButton(new GuiMenuSideButton(21, this.guiLeft - 69, this.guiTop + 2, 70, 22, "Tab 1"));
      this.addSideButton(new GuiMenuSideButton(22, this.guiLeft - 69, this.guiTop + 23, 70, 22, "Tab 2"));
      this.addSideButton(new GuiMenuSideButton(23, this.guiLeft - 69, this.guiTop + 44, 70, 22, "Tab 3"));
      this.addSideButton(new GuiMenuSideButton(24, this.guiLeft - 69, this.guiTop + 65, 70, 22, "Tab 4"));
      this.addSideButton(new GuiMenuSideButton(25, this.guiLeft - 69, this.guiTop + 86, 70, 22, "Tab 5"));
      this.addSideButton(new GuiMenuSideButton(26, this.guiLeft - 69, this.guiTop + 107, 70, 22, "Tab 6"));
      this.addSideButton(new GuiMenuSideButton(27, this.guiLeft - 69, this.guiTop + 128, 70, 22, "Tab 7"));
      this.addSideButton(new GuiMenuSideButton(28, this.guiLeft - 69, this.guiTop + 149, 70, 22, "Tab 8"));
      this.addSideButton(new GuiMenuSideButton(29, this.guiLeft - 69, this.guiTop + 170, 70, 22, "Tab 9"));
      this.getSideButton(20 + this.activeTab).active = true;
      this.showClones();
   }

   private void showClones() {
      this.cloneData.clear();
      ArrayList<String> list = new ArrayList();

      for(NBTTagCompound comp : CloneController.getClones()) {
         String name = comp.func_74779_i("ClonedName");

         for(int i = 1; list.contains(name); name = String.format("%s%s", comp.func_74779_i("ClonedName"), i)) {
            ++i;
         }

         int tab = 1;
         if (comp.func_74764_b("ClonedTab")) {
            tab = comp.func_74762_e("ClonedTab");
         }

         if (this.activeTab == tab) {
            list.add(name);
            this.cloneData.put(name, comp);
         }
      }

      this.list = list;
      this.scroll.setList(this.getSearchList());
   }

   public void func_73869_a(char c, int i) {
      super.func_73869_a(c, i);
      if (!search.equals(this.getTextField(1).func_73781_b())) {
         search = this.getTextField(1).func_73781_b().toLowerCase();
         this.scroll.setList(this.getSearchList());
      }
   }

   private List getSearchList() {
      if (search.isEmpty()) {
         return new ArrayList(this.list);
      } else {
         List<String> list = new ArrayList();

         for(String name : this.list) {
            if (name.toLowerCase().contains(search)) {
               list.add(name);
            }
         }

         return list;
      }
   }

   public NBTTagCompound getCompound() {
      String sel = this.scroll.getSelected();
      if (sel == null) {
         return null;
      } else {
         NBTTagCompound compound = (NBTTagCompound)this.cloneData.get(sel);
         compound.func_82580_o("StartPos");
         return compound;
      }
   }

   protected void func_73875_a(GuiButton guibutton) {
      if (guibutton.field_73741_f == 0) {
         this.close();
      }

      if (guibutton.field_73741_f == 1) {
         this.scroll.clear();
         this.close();
      }

      if (guibutton.field_73741_f > 20) {
         this.activeTab = guibutton.field_73741_f - 20;
         this.func_73866_w_();
      }

   }

   protected NBTTagList newDoubleNBTList(double... par1ArrayOfDouble) {
      NBTTagList nbttaglist = new NBTTagList();

      for(double d1 : par1ArrayOfDouble) {
         nbttaglist.func_74742_a(new NBTTagDouble((String)null, d1));
      }

      return nbttaglist;
   }

   public void save() {
   }
}
