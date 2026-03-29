package noppes.npcs.client.gui.global;

import java.util.HashMap;
import java.util.Vector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface2;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiCustomScrollActionListener;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.client.gui.util.IScrollData;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.containers.ContainerManageRecipes;
import noppes.npcs.controllers.RecipeCarpentry;
import org.lwjgl.opengl.GL11;

public class GuiNpcManageRecipes extends GuiContainerNPCInterface2 implements IScrollData, IGuiData, GuiCustomScrollActionListener, ITextfieldListener {
   private GuiCustomScroll scroll;
   private HashMap data = new HashMap();
   private ContainerManageRecipes container;
   private RecipeCarpentry recipe = new RecipeCarpentry();
   private String selected = null;
   private ResourceLocation slot;

   public GuiNpcManageRecipes(EntityNPCInterface npc, ContainerManageRecipes container) {
      super(npc, container);
      this.container = container;
      this.drawDefaultBackground = false;
      NoppesUtil.sendData(EnumPacketType.RecipesGet, container.width);
      this.setBackground("npctradersetup.png");
      this.slot = this.getResource("slot.png");
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.scroll = new GuiCustomScroll(this, 0);
      this.scroll.func_73872_a(this.field_73882_e, 350, 250);
      this.scroll.setSize(130, 180);
      this.scroll.guiLeft = this.field_74198_m + 172;
      this.scroll.guiTop = this.field_74197_n + 8;
      this.addButton(new GuiNpcButton(0, this.field_74198_m + 306, this.field_74197_n + 10, 84, 20, "menu.global"));
      this.addButton(new GuiNpcButton(1, this.field_74198_m + 306, this.field_74197_n + 32, 84, 20, "tile.npcCarpentyBench.name"));
      this.getButton(0).field_73742_g = this.container.width == 4;
      this.getButton(1).field_73742_g = this.container.width == 3;
      this.addButton(new GuiNpcButton(3, this.field_74198_m + 306, this.field_74197_n + 60, 84, 20, "gui.add"));
      this.addButton(new GuiNpcButton(4, this.field_74198_m + 306, this.field_74197_n + 82, 84, 20, "gui.remove"));
      this.addLabel(new GuiNpcLabel(0, "gui.ignoreDamage", this.field_74198_m + 86, this.field_74197_n + 32, 4210752));
      this.addButton(new GuiNpcButton(5, this.field_74198_m + 114, this.field_74197_n + 40, 30, 20, new String[]{"gui.no", "gui.yes"}, 0));
      this.addTextField(new GuiNpcTextField(0, this, this.field_73886_k, this.field_74198_m + 8, this.field_74197_n + 8, 160, 20, this.recipe.name));
      this.getTextField(0).enabled = false;
      this.getButton(5).field_73742_g = false;
   }

   public void func_73863_a(int x, int y, float f) {
      super.func_73863_a(x, y, f);
   }

   protected void func_73875_a(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if (guibutton.field_73741_f == 0) {
         this.save();
         NoppesUtil.requestOpenGUI(EnumGuiType.ManageRecipes, 3, 0, 0);
      }

      if (guibutton.field_73741_f == 1) {
         this.save();
         NoppesUtil.requestOpenGUI(EnumGuiType.ManageRecipes, 4, 0, 0);
      }

      if (guibutton.field_73741_f == 3) {
         this.save();
         this.scroll.clear();

         String name;
         for(name = "New"; this.data.containsKey(name); name = name + "_") {
         }

         RecipeCarpentry recipe = new RecipeCarpentry(-1, name);
         recipe.isGlobal = this.container.width == 3;
         NoppesUtil.sendData(EnumPacketType.RecipeSave, recipe.writeNBT());
      }

      if (guibutton.field_73741_f == 4 && this.data.containsKey(this.scroll.getSelected())) {
         NoppesUtil.sendData(EnumPacketType.RecipeRemove, this.data.get(this.scroll.getSelected()));
         this.scroll.clear();
      }

      if (guibutton.field_73741_f == 5) {
         this.recipe.ignoreDamage = button.getValue() == 1;
      }

   }

   public void doubleClicked() {
   }

   public void setGuiData(NBTTagCompound compound) {
      RecipeCarpentry recipe = new RecipeCarpentry();
      recipe.readNBT(compound);
      this.recipe = recipe;
      this.getTextField(0).func_73782_a(recipe.name);
      this.container.setRecipe(recipe);
      this.getTextField(0).enabled = true;
      this.getButton(5).field_73742_g = true;
      this.getButton(5).setDisplay(recipe.ignoreDamage ? 1 : 0);
      this.setSelected(recipe.name);
   }

   protected void func_74185_a(float f, int x, int y) {
      super.func_74185_a(f, x, y);
      this.scroll.func_73863_a(x, y, f);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_73882_e.field_71446_o.func_110577_a(this.slot);

      for(int i = 0; i < this.container.width; ++i) {
         for(int j = 0; j < this.container.width; ++j) {
            this.func_73729_b(this.field_74198_m + i * 18 + 7, this.field_74197_n + j * 18 + 34, 0, 0, 20, 20);
         }
      }

      this.func_73729_b(this.field_74198_m + 86, this.field_74197_n + 60, 0, 0, 20, 20);
   }

   public void setData(Vector list, HashMap data) {
      String name = this.scroll.getSelected();
      this.data = data;
      this.scroll.setList(list);
      this.getTextField(0).enabled = name != null;
      this.getButton(5).field_73742_g = name != null;
      if (name != null) {
         this.scroll.setSelected(name);
      }

   }

   public void func_73864_a(int i, int j, int k) {
      super.func_73864_a(i, j, k);
      if (k == 0 && this.scroll != null) {
         this.scroll.func_73864_a(i, j, k);
      }

   }

   public void setSelected(String selected) {
      this.selected = selected;
      this.scroll.setSelected(selected);
   }

   public void customScrollClicked(int i, int j, int k, GuiCustomScroll guiCustomScroll) {
      this.save();
      this.selected = this.scroll.getSelected();
      NoppesUtil.sendData(EnumPacketType.RecipeGet, this.data.get(this.selected));
   }

   public void save() {
      GuiNpcTextField.unfocus();
      if (this.selected != null && this.data.containsKey(this.selected)) {
         this.container.saveRecipe();
         NoppesUtil.sendData(EnumPacketType.RecipeSave, this.recipe.writeNBT());
      }

   }

   public void unFocused(GuiNpcTextField guiNpcTextField) {
      String name = guiNpcTextField.func_73781_b();
      if (!name.isEmpty() && !this.data.containsKey(name)) {
         String old = this.recipe.name;
         this.data.remove(this.recipe.name);
         this.recipe.name = name;
         this.data.put(this.recipe.name, this.recipe.id);
         this.selected = name;
         this.scroll.replace(old, this.recipe.name);
      }

   }
}
