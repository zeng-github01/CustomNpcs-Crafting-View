package noppes.npcs.client.gui.mainmenu;

import java.util.HashMap;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.EnumOptions;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcSlider;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.client.gui.util.ISliderListener;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.containers.ContainerNPCInv;
import org.lwjgl.opengl.GL11;

public class GuiNPCInv extends GuiContainerNPCInterface2 implements ISliderListener, IGuiData {
   private HashMap chances = new HashMap();
   private ContainerNPCInv container;
   private ResourceLocation slot;

   public GuiNPCInv(EntityNPCInterface npc, ContainerNPCInv container) {
      super(npc, container, 3);
      this.setBackground("npcinv.png");
      this.container = container;
      this.field_74195_c = 200;
      this.slot = this.getResource("slot.png");
      NoppesUtil.sendData(EnumPacketType.MainmenuInvGet);
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addLabel(new GuiNpcLabel(0, "inv.minExp", this.field_74198_m + 118, this.field_74197_n + 18, 4210752));
      this.addTextField(new GuiNpcTextField(0, this, this.field_73886_k, this.field_74198_m + 108, this.field_74197_n + 29, 60, 20, this.npc.inventory.minExp + ""));
      this.getTextField(0).numbersOnly = true;
      this.getTextField(0).setMinMaxDefault(0, 32767, 0);
      this.addLabel(new GuiNpcLabel(1, "inv.maxExp", this.field_74198_m + 118, this.field_74197_n + 52, 4210752));
      this.addTextField(new GuiNpcTextField(1, this, this.field_73886_k, this.field_74198_m + 108, this.field_74197_n + 63, 60, 20, this.npc.inventory.maxExp + ""));
      this.getTextField(1).numbersOnly = true;
      this.getTextField(1).setMinMaxDefault(0, 32767, 0);
      this.addLabel(new GuiNpcLabel(2, "inv.npcInventory", this.field_74198_m + 191, this.field_74197_n + 5, 4210752));
      this.addLabel(new GuiNpcLabel(3, "inv.inventory", this.field_74198_m + 8, this.field_74197_n + 101, 4210752));

      for(int i = 0; i < 8; ++i) {
         int chance = 100;
         if (this.npc.inventory.dropchance.containsKey(i)) {
            chance = (Integer)this.npc.inventory.dropchance.get(i);
         }

         if (chance <= 0 || chance > 100) {
            chance = 100;
         }

         this.chances.put(i, chance);
         GuiNpcSlider slider = new GuiNpcSlider(this, i, this.field_74198_m + 211, this.field_74197_n + 15 + i * 22, (EnumOptions)null, StatCollector.func_74838_a("inv.dropChance") + ": " + chance + "%", (float)chance / 100.0F);
         this.addSlider(slider);
      }

   }

   protected void func_74185_a(float f, int i, int j) {
      super.func_74185_a(f, i, j);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_73882_e.field_71446_o.func_110577_a(this.slot);

      for(int id = 4; id <= 6; ++id) {
         Slot slot = this.container.func_75139_a(id);
         if (slot.func_75216_d()) {
            this.func_73729_b(this.field_74198_m + slot.field_75223_e - 1, this.field_74197_n + slot.field_75221_f - 1, 0, 0, 20, 20);
         }
      }

   }

   public void func_73863_a(int i, int j, float f) {
      int showname = this.npc.display.showName;
      this.npc.display.showName = 1;
      int l = this.field_74198_m + 20;
      int i1 = this.field_73881_g / 2 - 145;
      GL11.glEnable(32826);
      GL11.glEnable(2903);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)(l + 33), (float)(i1 + 131), 50.0F);
      float f1 = 150.0F / (float)this.npc.display.modelSize;
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
      this.npc.field_70761_aq = (float)Math.atan((double)(f5 / 40.0F)) * 20.0F;
      this.npc.field_70177_z = (float)Math.atan((double)(f5 / 40.0F)) * 40.0F;
      this.npc.field_70125_A = -((float)Math.atan((double)(f6 / 40.0F))) * 20.0F;
      this.npc.field_70759_as = this.npc.field_70177_z;
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
      this.npc.display.showName = showname;
      super.func_73863_a(i, j, f);
   }

   public void save() {
      this.npc.inventory.dropchance = this.chances;
      this.npc.inventory.minExp = this.getTextField(0).getInteger();
      this.npc.inventory.maxExp = this.getTextField(1).getInteger();
      NoppesUtil.sendData(EnumPacketType.MainmenuInvSave, this.npc.inventory.writeEntityToNBT(new NBTTagCompound()));
   }

   public void setGuiData(NBTTagCompound compound) {
      this.npc.inventory.readEntityFromNBT(compound);
      this.func_73866_w_();
   }

   public void mouseDragged(GuiNpcSlider guiNpcSlider) {
      guiNpcSlider.field_73744_e = StatCollector.func_74838_a("inv.dropChance") + ": " + (int)(guiNpcSlider.field_73751_j * 100.0F) + "%";
   }

   public void mousePressed(GuiNpcSlider guiNpcSlider) {
   }

   public void mouseReleased(GuiNpcSlider guiNpcSlider) {
      this.chances.put(guiNpcSlider.field_73741_f, (int)(guiNpcSlider.field_73751_j * 100.0F));
   }
}
