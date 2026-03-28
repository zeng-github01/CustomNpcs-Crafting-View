package noppes.npcs.client.gui;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.controllers.CloneController;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiMenuSideButton;
import noppes.npcs.client.gui.util.GuiMenuTopButton;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.constants.EnumPacketType;

public class GuiNpcMobSpawner extends GuiNPCInterface {
   private GuiCustomScroll scroll;
   private int posX;
   private int posY;
   private int posZ;
   private HashMap cloneData = new HashMap();
   private List data = new ArrayList();
   private ArrayList list;
   private static boolean showingClones = true;
   private static String search = "";
   private int activeTab = 1;

   public GuiNpcMobSpawner(int i, int j, int k) {
      this.xSize = 256;
      this.posX = i;
      this.posY = j;
      this.posZ = k;
      this.closeOnEsc = true;
      this.setBackground("menubg.png");
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.guiTop += 10;
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
      GuiMenuTopButton button;
      this.addTopButton(button = new GuiMenuTopButton(3, this.guiLeft + 4, this.guiTop - 17, "spawner.clones"));
      button.active = showingClones;
      GuiMenuTopButton var2;
      this.addTopButton(var2 = new GuiMenuTopButton(4, button, "spawner.entities"));
      var2.active = !showingClones;
      this.addButton(new GuiNpcButton(1, this.guiLeft + 170, this.guiTop + 6, 82, 20, "item.monsterPlacer.name"));
      this.addButton(new GuiNpcButton(2, this.guiLeft + 170, this.guiTop + 100, 82, 20, "spawner.mobspawner"));
      if (showingClones) {
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
      } else {
         this.showEntities();
      }

   }

   private void showEntities() {
      Map<?, ?> data = EntityList.field_75625_b;
      ArrayList<String> list = new ArrayList();

      for(Object name : data.keySet()) {
         Class<?> c = (Class)data.get(name);

         try {
            if (EntityLiving.class.isAssignableFrom(c) && c.getConstructor(World.class) != null && !Modifier.isAbstract(c.getModifiers())) {
               list.add(name.toString());
            }
         } catch (SecurityException e) {
            e.printStackTrace();
         } catch (NoSuchMethodException var8) {
         }
      }

      this.list = list;
      this.scroll.setList(this.getSearchList());
   }

   private void showClones() {
      this.addButton(new GuiNpcButton(5, this.guiLeft + 170, this.guiTop + 30, 82, 20, "gui.remove"));
      this.cloneData.clear();
      ArrayList<String> list = new ArrayList();
      this.data = CloneController.getClones();

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

   private NBTTagCompound getCompound() {
      String sel = this.scroll.getSelected();
      if (sel == null) {
         return null;
      } else if (showingClones) {
         NBTTagCompound compound = (NBTTagCompound)this.cloneData.get(sel);
         compound.func_82580_o("StartPos");
         compound.func_82580_o("Pos");
         if (!compound.func_74764_b("ModRev")) {
            compound.func_74768_a("ModRev", 1);
         }

         return compound;
      } else {
         Entity entity = EntityList.func_75620_a(sel, Minecraft.func_71410_x().field_71441_e);
         NBTTagCompound compound = new NBTTagCompound();
         entity.func_70039_c(compound);
         return compound;
      }
   }

   protected void func_73875_a(GuiButton guibutton) {
      if (guibutton.field_73741_f == 0) {
         this.close();
      }

      if (guibutton.field_73741_f == 1) {
         NBTTagCompound compound = this.getCompound();
         if (compound != null) {
            compound.func_74782_a("Pos", this.newDoubleNBTList((double)this.posX + (double)0.5F, (double)(this.posY + 1), (double)this.posZ + (double)0.5F));
            compound.func_74768_a("ItemGiverId", 0);
            compound.func_74768_a("TransporterId", -1);
            NoppesUtil.sendData(EnumPacketType.SpawnMob, compound);
            this.close();
         }
      }

      if (guibutton.field_73741_f == 2) {
         NBTTagCompound compound = this.getCompound();
         if (compound != null) {
            NoppesUtil.sendData(EnumPacketType.MobSpawner, this.posX, this.posY, this.posZ, compound);
            this.close();
         }
      }

      if (guibutton.field_73741_f == 3) {
         showingClones = true;
         this.func_73866_w_();
      }

      if (guibutton.field_73741_f == 4) {
         showingClones = false;
         this.func_73866_w_();
      }

      if (guibutton.field_73741_f == 5 && this.scroll.getSelected() != null) {
         NBTTagCompound toCheck = (NBTTagCompound)this.cloneData.get(this.scroll.getSelected());
         this.data.remove(toCheck);
         CloneController.saveClones(this.data);
         this.scroll.selected = -1;
         this.func_73866_w_();
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
