package noppes.npcs.client.gui;

import java.util.HashMap;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.controllers.Line;
import noppes.npcs.controllers.Lines;

public class GuiNPCLinesEdit extends GuiNPCInterface2 implements IGuiData {
   private Lines lines;
   private GuiNpcTextField field;
   private GuiNpcMusicSelection gui;

   public GuiNPCLinesEdit(EntityNPCInterface npc, Lines lines) {
      super(npc);
      this.lines = lines;
      NoppesUtil.sendData(EnumPacketType.MainmenuAdvancedGet);
   }

   public void func_73866_w_() {
      super.func_73866_w_();

      for(int i = 0; i < 8; ++i) {
         String text = "";
         String sound = "";
         if (this.lines.lines.containsKey(i)) {
            Line line = (Line)this.lines.lines.get(i);
            text = line.text;
            sound = line.sound;
         }

         this.addTextField(new GuiNpcTextField(i, this, this.field_73886_k, this.guiLeft + 4, this.guiTop + 4 + i * 24, 200, 20, text));
         this.addTextField(new GuiNpcTextField(i + 8, this, this.field_73886_k, this.guiLeft + 208, this.guiTop + 4 + i * 24, 146, 20, sound));
         this.addButton(new GuiNpcButton(i, this.guiLeft + 358, this.guiTop + 4 + i * 24, 60, 20, "mco.template.button.select"));
      }

   }

   protected void func_73875_a(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      this.field = this.getTextField(button.field_73741_f + 8);
      NoppesUtil.openGUI(this.player, this.gui = new GuiNpcMusicSelection(this.npc, this, this.field.func_73781_b()));
   }

   public void elementClicked() {
      this.field.func_73782_a(this.gui.getSelected());
      this.saveLines();
   }

   public void setGuiData(NBTTagCompound compound) {
      this.npc.advanced.readToNBT(compound);
      this.func_73866_w_();
   }

   private void saveLines() {
      HashMap<Integer, Line> lines = new HashMap();

      for(int i = 0; i < 8; ++i) {
         GuiNpcTextField tf = this.getTextField(i);
         GuiNpcTextField tf2 = this.getTextField(i + 8);
         if (!tf.isEmpty()) {
            Line line = new Line();
            line.text = tf.func_73781_b();
            line.sound = tf2.func_73781_b();
            lines.put(i, line);
         }
      }

      this.lines.lines = lines;
   }

   public void save() {
      this.saveLines();
      NoppesUtil.sendData(EnumPacketType.MainmenuAdvancedSave, this.npc.advanced.writeToNBT(new NBTTagCompound()));
   }
}
