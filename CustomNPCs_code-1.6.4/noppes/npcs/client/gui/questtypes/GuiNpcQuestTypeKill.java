package noppes.npcs.client.gui.questtypes;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiCustomScrollActionListener;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.controllers.Quest;
import noppes.npcs.quests.QuestKill;

public class GuiNpcQuestTypeKill extends GuiNPCInterface implements ITextfieldListener, GuiCustomScrollActionListener {
   private GuiScreen parent;
   private GuiCustomScroll scroll;
   private QuestKill quest;
   private GuiNpcTextField lastSelected;

   public GuiNpcQuestTypeKill(EntityNPCInterface npc, Quest q, GuiScreen parent) {
      super(npc);
      this.parent = parent;
      this.title = "Quest Kill Setup";
      this.quest = (QuestKill)q.questInterface;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      int i = 0;
      this.addLabel(new GuiNpcLabel(0, "You can fill in npc or player names too", this.guiLeft - 100, this.guiTop + 50, 16777215));

      for(String name : this.quest.targets.keySet()) {
         this.addTextField(new GuiNpcTextField(i, this, this.field_73886_k, this.guiLeft - 100, this.guiTop + 70 + i * 22, 180, 20, name));
         this.addTextField(new GuiNpcTextField(i + 3, this, this.field_73886_k, this.guiLeft + 84, this.guiTop + 70 + i * 22, 30, 20, this.quest.targets.get(name) + ""));
         this.getTextField(i + 3).numbersOnly = true;
         this.getTextField(i + 3).setMinMaxDefault(1, Integer.MAX_VALUE, 1);
         ++i;
      }

      while(i < 3) {
         this.addTextField(new GuiNpcTextField(i, this, this.field_73886_k, this.guiLeft - 100, this.guiTop + 70 + i * 22, 180, 20, ""));
         this.addTextField(new GuiNpcTextField(i + 3, this, this.field_73886_k, this.guiLeft + 84, this.guiTop + 70 + i * 22, 30, 20, "1"));
         this.getTextField(i + 3).numbersOnly = true;
         this.getTextField(i + 3).setMinMaxDefault(1, Integer.MAX_VALUE, 1);
         ++i;
      }

      Map<?, ?> data = EntityList.field_75625_b;
      ArrayList<String> list = new ArrayList();

      for(Object name : data.keySet()) {
         Class<?> c = (Class)data.get(name);

         try {
            if (EntityLivingBase.class.isAssignableFrom(c) && !EntityNPCInterface.class.isAssignableFrom(c) && c.getConstructor(World.class) != null && !Modifier.isAbstract(c.getModifiers())) {
               list.add(name.toString());
            }
         } catch (SecurityException e) {
            e.printStackTrace();
         } catch (NoSuchMethodException var9) {
         }
      }

      this.scroll = new GuiCustomScroll(this, 0);
      this.scroll.setList(list);
      this.scroll.func_73872_a(this.field_73882_e, 350, 250);
      this.scroll.setSize(140, 190);
      this.scroll.guiLeft = this.guiLeft + 120;
      this.scroll.guiTop = this.guiTop + 14;
      this.addButton(new GuiNpcButton(0, this.guiLeft - 100, this.guiTop + 140, 98, 20, "gui.back"));
   }

   public void func_73863_a(int i, int j, float f) {
      super.func_73863_a(i, j, f);
      this.scroll.func_73863_a(i, j, f);
   }

   protected void func_73875_a(GuiButton guibutton) {
      super.func_73875_a(guibutton);
      if (guibutton.field_73741_f == 0) {
         NoppesUtil.openGUI(this.player, this.parent);
      }

   }

   public void func_73864_a(int i, int j, int k) {
      super.func_73864_a(i, j, k);
      this.scroll.func_73864_a(i, j, k);
   }

   public void save() {
   }

   public void unFocused(GuiNpcTextField guiNpcTextField) {
      if (guiNpcTextField.id < 3) {
         this.lastSelected = guiNpcTextField;
      }

      this.saveTargets();
   }

   private void saveTargets() {
      HashMap<String, Integer> map = new HashMap();

      for(int i = 0; i < 3; ++i) {
         String name = this.getTextField(i).func_73781_b();
         if (!name.isEmpty()) {
            map.put(name, this.getTextField(i + 3).getInteger());
         }
      }

      this.quest.targets = map;
   }

   public void customScrollClicked(int i, int j, int k, GuiCustomScroll guiCustomScroll) {
      if (this.lastSelected != null) {
         this.lastSelected.func_73782_a(guiCustomScroll.getSelected());
         this.saveTargets();
      }
   }
}
