package noppes.npcs.client.gui.questtypes;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.global.GuiNPCManageQuest;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.containers.ContainerNpcQuestTypeItem;
import noppes.npcs.controllers.Quest;
import org.lwjgl.opengl.GL11;

public class GuiNpcQuestTypeItem extends GuiContainerNPCInterface implements ITextfieldListener {
   private Quest quest;
   private static final ResourceLocation field_110422_t = new ResourceLocation("customnpcs", "textures/gui/followersetup.png");

   public GuiNpcQuestTypeItem(EntityNPCInterface npc, ContainerNpcQuestTypeItem container) {
      super(npc, container);
      this.quest = GuiNPCManageQuest.quest;
      this.title = "Quest Item Setup";
      this.field_74195_c = 202;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addButton(new GuiNpcButton(5, this.field_74198_m, this.field_74197_n + this.field_74195_c, 98, 20, "gui.back"));
   }

   public void func_73875_a(GuiButton guibutton) {
      if (guibutton.field_73741_f == 5) {
         NoppesUtil.openGUI(this.player, GuiNPCManageQuest.Instance);
      }

   }

   public void func_73874_b() {
   }

   protected void func_73869_a(char c, int i) {
   }

   protected void func_74185_a(float f, int i, int j) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_73882_e.field_71446_o.func_110577_a(field_110422_t);
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
