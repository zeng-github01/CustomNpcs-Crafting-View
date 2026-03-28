package noppes.npcs.client.gui;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.AssetsBrowser;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNPCStringSlot;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;

public class GuiNpcSoundSelection extends GuiNPCInterface {
   private GuiNPCStringSlot slot;
   private Object parent;
   int depth = 0;
   private String location = "";
   private String selectedLocation = "";
   private String selectedFile = "";
   private List folders = new ArrayList();
   private List files = new ArrayList();
   private String up = "..<" + StatCollector.func_74838_a("gui.up") + ">..";

   public GuiNpcSoundSelection(EntityNPCInterface npc, Object parent, String sound) {
      super(npc);
      sound = sound.replaceAll("\\.", "/");
      int index = sound.lastIndexOf("/");
      if (index > 0) {
         this.selectedFile = sound.substring(index + 1);
         sound = sound.substring(0, index);
         if (sound.startsWith("customnpcs:")) {
            this.location = "customnpcs/" + sound.substring(11);
         } else {
            this.location = "minecraft/" + sound;
         }

         this.depth = this.location.split("/").length;
         this.selectedLocation = this.location;
      }

      this.drawDefaultBackground = false;
      this.title = "";
      this.parent = parent;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      String ss = StatCollector.func_74838_a("gui.currentFolder") + ": " + this.location;
      this.addLabel(new GuiNpcLabel(0, ss, this.field_73880_f / 2 - this.field_73886_k.func_78256_a(ss) / 2, 20, 16777215));
      this.slot = new GuiNPCStringSlot(this.getList(), this, this.npc, false, 18);
      this.slot.func_77220_a(4, 5);
      if (this.depth > 0 && this.selectedLocation.equals(this.location)) {
         this.slot.selected = this.selectedFile;
      }

      this.addButton(new GuiNpcButton(2, this.field_73880_f / 2 - 100, this.field_73881_g - 44, 98, 20, "gui.back"));
      this.addButton(new GuiNpcButton(1, this.field_73880_f / 2 + 2, this.field_73881_g - 44, 98, 20, "gui.play"));
   }

   private List getList() {
      this.folders.clear();
      this.files.clear();
      List<String> list = new ArrayList();
      if (this.depth == 0) {
         this.folders.add("minecraft");
         this.folders.add("customnpcs");
      } else {
         list.add(this.up);
         String loc;
         if (this.location.startsWith("customnpcs")) {
            loc = "/customnpcs/sound" + this.location.substring(10);
         } else {
            loc = "/sound" + this.location.substring(9);
         }

         AssetsBrowser browser = new AssetsBrowser(loc, new String[]{"ogg", "wav"});

         for(String folder : browser.folders) {
            this.folders.add("/" + folder);
         }

         for(String file : browser.files) {
            for(file = file.substring(0, file.lastIndexOf(".")); Character.isDigit(file.charAt(file.length() - 1)); file = file.substring(0, file.length() - 1)) {
            }

            if (!this.files.contains(file)) {
               this.files.add(file);
            }
         }
      }

      list.addAll(this.folders);
      list.addAll(this.files);
      return list;
   }

   public void func_73863_a(int i, int j, float f) {
      this.slot.func_77211_a(i, j, f);
      super.func_73863_a(i, j, f);
   }

   public void elementClicked() {
      if (this.slot.selected != null && this.files.contains(this.slot.selected)) {
         this.selectedLocation = this.location;
         this.selectedFile = this.slot.selected;
         if (this.parent instanceof GuiNPCInterface) {
            ((GuiNPCInterface)this.parent).elementClicked();
         } else if (this.parent instanceof GuiNPCInterface2) {
            ((GuiNPCInterface2)this.parent).elementClicked();
         }
      }

   }

   public String getSelected() {
      String file;
      if (this.location.startsWith("customnpcs/")) {
         file = "customnpcs:" + this.location.substring(11);
      } else {
         file = this.location.substring(10);
      }

      file = file.replaceAll("/", ".");
      return file + "." + this.slot.selected;
   }

   public void doubleClicked() {
      if (this.slot.selected.equals(this.up)) {
         --this.depth;
         if (this.depth == 0) {
            this.location = "";
         } else {
            this.location = this.location.substring(0, this.location.lastIndexOf("/"));
         }

         this.func_73866_w_();
      } else if (this.folders.contains(this.slot.selected)) {
         ++this.depth;
         if (this.location.endsWith("/") && this.slot.selected.startsWith("/")) {
            this.location = this.location + this.slot.selected.substring(1);
         } else {
            this.location = this.location + this.slot.selected;
         }

         this.func_73866_w_();
      } else {
         this.close();
         NoppesUtil.openGUI(this.player, this.parent);
      }

   }

   protected void func_73875_a(GuiButton guibutton) {
      if (guibutton.field_73741_f == 1 && this.files.contains(this.slot.selected)) {
         this.field_73882_e.field_71416_A.func_77366_a(this.getSelected(), 1.0F, 1.0F);
      }

      if (guibutton.field_73741_f == 2) {
         this.close();
         NoppesUtil.openGUI(this.player, this.parent);
      }

   }

   public void save() {
   }
}
