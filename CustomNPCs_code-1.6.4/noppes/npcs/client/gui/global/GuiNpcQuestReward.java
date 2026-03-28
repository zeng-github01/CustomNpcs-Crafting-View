package noppes.npcs.client.gui.global;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.containers.ContainerNpcQuestReward;
import noppes.npcs.controllers.Quest;
import org.lwjgl.opengl.GL11;

public class GuiNpcQuestReward extends GuiContainerNPCInterface implements ITextfieldListener {
   private Quest quest;
   private ResourceLocation resource;

   public GuiNpcQuestReward(EntityNPCInterface npc, ContainerNpcQuestReward container) {
      super(npc, container);
      this.quest = GuiNPCManageQuest.quest;
      this.resource = this.getResource("questreward.png");
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addButton(new GuiNpcButton(5, this.field_74198_m, this.field_74197_n + this.field_74195_c, 98, 20, "gui.back"));
      this.addLabel(new GuiNpcLabel(1, "Exp:", this.field_74198_m + 5, this.field_74197_n + 45, 4210752));
      this.addTextField(new GuiNpcTextField(0, this, this.field_73886_k, this.field_74198_m + 30, this.field_74197_n + 40, 60, 20, this.quest.rewardExp + ""));
      this.getTextField(0).numbersOnly = true;
      this.getTextField(0).setMinMaxDefault(0, 99999, 0);
   }

   public void func_73875_a(GuiButton guibutton) {
      if (guibutton.field_73741_f == 5) {
         NoppesUtil.openGUI(this.player, GuiNPCManageQuest.Instance);
      }

   }

   public void func_73874_b() {
   }

   protected void func_74185_a(float f, int i, int j) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_73882_e.field_71446_o.func_110577_a(this.resource);
      int l = (this.field_73880_f - this.field_74194_b) / 2;
      int i1 = (this.field_73881_g - this.field_74195_c) / 2;
      this.func_73729_b(l, i1, 0, 0, this.field_74194_b, this.field_74195_c);
      super.func_74185_a(f, i, j);
   }

   public void func_73863_a(int i, int j, float f) {
      super.func_73863_a(i, j, f);
   }

   public void save() {
   }

   public void unFocused(GuiNpcTextField textfield) {
      this.quest.rewardExp = textfield.getInteger();
   }
}
