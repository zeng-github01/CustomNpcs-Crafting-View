package noppes.npcs.client.gui;

import java.util.Collections;
import java.util.HashSet;
import java.util.Vector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.AssetsBrowser;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNPCStringSlot;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcSkinPreviewInterface;
import org.lwjgl.opengl.GL11;

public class GuiNpcTextureCloaks extends GuiNPCInterface implements GuiNpcSkinPreviewInterface {
   private GuiNPCStringSlot slot;
   private GuiScreen parent;
   private String root = "/customnpcs/textures/cloak";
   private AssetsBrowser assets;
   private HashSet dataFolder = new HashSet();
   private HashSet dataTextures = new HashSet();

   public GuiNpcTextureCloaks(EntityNPCInterface npc, GuiScreen parent) {
      super(npc);
      if (!npc.display.cloakTexture.isEmpty()) {
         this.root = AssetsBrowser.getRoot(npc.display.cloakTexture);
      }

      this.assets = new AssetsBrowser(this.root, new String[]{"png"});
      this.drawDefaultBackground = false;
      this.title = "Select Texture";
      this.parent = parent;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.dataFolder.clear();
      String ss = "Current Folder: " + this.root;
      this.addLabel(new GuiNpcLabel(0, ss, this.field_73880_f / 2 - this.field_73886_k.func_78256_a(ss) / 2, 20, 16777215));
      Vector<String> list = new Vector();
      if (!this.assets.isRoot) {
         list.add("..<UP>..");
      }

      for(String folder : this.assets.folders) {
         list.add("/" + folder);
         this.dataFolder.add("/" + folder);
      }

      for(String texture : this.assets.files) {
         list.add(texture);
         this.dataTextures.add(texture);
      }

      Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
      this.slot = new GuiNPCStringSlot(list, this, this.npc, false, 18);
      int index = this.npc.display.cloakTexture.lastIndexOf("/");
      if (index > 0) {
         String asset = this.npc.display.cloakTexture.substring(index + 1);
         if (this.npc.display.cloakTexture.equals(this.assets.getAsset(asset))) {
            this.slot.selected = asset;
         }
      }

      this.slot.func_77220_a(4, 5);
      this.addButton(2, new GuiNpcButton(2, this.field_73880_f / 2 - 100, this.field_73881_g - 44, 98, 20, "gui.back"));
   }

   public void func_73863_a(int i, int j, float f) {
      int l = this.field_73880_f / 2 - 180;
      int i1 = this.field_73881_g / 2 - 90;
      GL11.glEnable(32826);
      GL11.glEnable(2903);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)(l + 33), (float)(i1 + 131), 50.0F);
      float f1 = 250.0F / (float)this.npc.display.modelSize;
      GL11.glScalef(-f1, f1, f1);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      float f2 = this.npc.field_70761_aq;
      float f3 = this.npc.field_70177_z;
      float f4 = this.npc.field_70125_A;
      float f5 = (float)(l + 33) - (float)i;
      float f6 = (float)(i1 + 131 - 50) - (float)j;
      GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
      RenderHelper.func_74519_b();
      GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-((float)Math.atan((double)(f6 / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
      this.npc.field_70761_aq = (float)Math.atan((double)(f5 / 40.0F)) * 20.0F + 180.0F;
      this.npc.field_70177_z = (float)Math.atan((double)(f5 / 40.0F)) * 40.0F + 180.0F;
      this.npc.field_70125_A = -((float)Math.atan((double)(f6 / 40.0F))) * 20.0F;
      this.npc.field_70759_as = this.npc.field_70177_z;
      this.npc.cloakUpdate();
      GL11.glTranslatef(0.0F, this.npc.field_70129_M, 0.0F);
      RenderManager.field_78727_a.field_78735_i = 180.0F;
      RenderManager.field_78727_a.func_78719_a(this.npc, (double)0.0F, (double)0.0F, (double)0.0F, 0.0F, 1.0F);
      this.npc.field_70761_aq = f2;
      this.npc.field_70177_z = f3;
      this.npc.field_70125_A = f4;
      GL11.glPopMatrix();
      RenderHelper.func_74518_a();
      GL11.glDisable(32826);
      OpenGlHelper.func_77473_a(OpenGlHelper.field_77476_b);
      GL11.glDisable(3553);
      OpenGlHelper.func_77473_a(OpenGlHelper.field_77478_a);
      this.slot.func_77211_a(i, j, f);
      super.func_73863_a(i, j, f);
   }

   public void elementClicked() {
      if (this.dataTextures.contains(this.slot.selected) && this.slot.selected != null) {
         this.npc.display.cloakTexture = this.assets.getAsset(this.slot.selected);
         this.npc.textureCloakLocation = null;
      }

   }

   public void doubleClicked() {
      String selected = this.slot.selected;
      if (selected.equals("..<UP>..")) {
         this.root = this.root.substring(0, this.root.lastIndexOf("/"));
         this.assets = new AssetsBrowser(this.root, new String[]{"png"});
         this.func_73866_w_();
      } else if (this.dataFolder.contains(selected)) {
         this.root = this.root + selected;
         this.assets = new AssetsBrowser(this.root, new String[]{"png"});
         this.func_73866_w_();
      } else {
         this.close();
         NoppesUtil.openGUI(this.player, this.parent);
      }

   }

   protected void func_73875_a(GuiButton guibutton) {
      if (guibutton.field_73741_f == 2) {
         this.close();
         NoppesUtil.openGUI(this.player, this.parent);
      }

   }

   public void save() {
   }
}
