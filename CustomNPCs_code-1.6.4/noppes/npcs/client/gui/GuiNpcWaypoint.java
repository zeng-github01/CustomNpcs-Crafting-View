package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.blocks.TileWaypoint;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.constants.EnumPacketType;

public class GuiNpcWaypoint extends GuiNPCInterface {
   private TileWaypoint tile;

   public GuiNpcWaypoint(int x, int y, int z) {
      this.tile = (TileWaypoint)this.player.field_70170_p.func_72796_p(x, y, z);
      this.xSize = 265;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addLabel(new GuiNpcLabel(0, "gui.name", this.guiLeft + 1, this.guiTop + 76, 16777215));
      this.addTextField(new GuiNpcTextField(0, this, this.field_73886_k, this.guiLeft + 60, this.guiTop + 71, 200, 20, this.tile.name));
      this.addLabel(new GuiNpcLabel(1, "gui.range", this.guiLeft + 1, this.guiTop + 97, 16777215));
      this.addTextField(new GuiNpcTextField(1, this, this.field_73886_k, this.guiLeft + 60, this.guiTop + 92, 200, 20, this.tile.range + ""));
      this.getTextField(1).numbersOnly = true;
      this.getTextField(1).setMinMaxDefault(2, 60, 10);
      this.addButton(new GuiNpcButton(0, this.guiLeft + 40, this.guiTop + 190, 120, 20, "Done"));
   }

   protected void func_73875_a(GuiButton guibutton) {
      if (guibutton.field_73741_f == 0) {
         this.close();
      }

   }

   public void save() {
      if (this.tile != null) {
         this.tile.name = this.getTextField(0).func_73781_b();
         this.tile.range = this.getTextField(1).getInteger();
         NBTTagCompound compound = new NBTTagCompound();
         this.tile.func_70310_b(compound);
         NoppesUtil.sendData(EnumPacketType.SaveTileEntity, compound);
      }
   }
}
