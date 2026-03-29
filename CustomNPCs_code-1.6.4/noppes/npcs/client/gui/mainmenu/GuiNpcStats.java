package noppes.npcs.client.gui.mainmenu;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.DataStats;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.SubGuiNpcMeleeProperties;
import noppes.npcs.client.gui.SubGuiNpcProjectiles;
import noppes.npcs.client.gui.SubGuiNpcRangeProperties;
import noppes.npcs.client.gui.SubGuiNpcResistanceProperties;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.constants.EnumPacketType;

public class GuiNpcStats extends GuiNPCInterface2 implements ITextfieldListener, IGuiData {
   DataStats stats;

   public GuiNpcStats(EntityNPCInterface npc) {
      super(npc, 2);
      this.stats = npc.stats;
      NoppesUtil.sendData(EnumPacketType.MainmenuStatsGet);
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addLabel(new GuiNpcLabel(0, "stats.health", this.guiLeft + 5, this.guiTop + 15, 4210752));
      this.addTextField(new GuiNpcTextField(0, this, this.field_73886_k, this.guiLeft + 85, this.guiTop + 10, 50, 18, this.stats.maxHealth + ""));
      this.getTextField(0).numbersOnly = true;
      this.getTextField(0).setMinMaxDefault(1, 32767, 20);
      this.addLabel(new GuiNpcLabel(1, "stats.aggro", this.guiLeft + 140, this.guiTop + 15, 4210752));
      this.addTextField(new GuiNpcTextField(1, this, this.field_73886_k, this.guiLeft + 220, this.guiTop + 10, 50, 18, this.stats.aggroRange + ""));
      this.getTextField(1).numbersOnly = true;
      this.getTextField(1).setMinMaxDefault(1, 64, 2);
      this.addLabel(new GuiNpcLabel(34, "stats.creaturetype", this.guiLeft + 275, this.guiTop + 15, 4210752));
      this.addButton(new GuiNpcButton(8, this.guiLeft + 355, this.guiTop + 10, 56, 20, new String[]{"stats.normal", "stats.undead", "stats.arthropod"}, this.stats.creatureType.ordinal()));
      this.addLabel(new GuiNpcLabel(2, "stats.respawn", this.guiLeft + 5, this.guiTop + 35, 4210752));
      this.addButton(new GuiNpcButton(0, this.guiLeft + 82, this.guiTop + 30, 56, 20, new String[]{"gui.yes", "gui.day", "gui.night", "gui.no"}, this.stats.spawnCycle));
      if (this.stats.respawnTime > 0) {
         this.addLabel(new GuiNpcLabel(3, "gui.time", this.guiLeft + 140, this.guiTop + 35, 4210752));
         this.addTextField(new GuiNpcTextField(2, this, this.field_73886_k, this.guiLeft + 220, this.guiTop + 30, 50, 18, this.stats.respawnTime + ""));
         this.getTextField(2).numbersOnly = true;
         this.getTextField(2).setMinMaxDefault(1, 99999, 20);
         this.addLabel(new GuiNpcLabel(4, "stats.deadbody", this.guiLeft + 275, this.guiTop + 35, 4210752));
         this.addButton(new GuiNpcButton(1, this.guiLeft + 355, this.guiTop + 30, 51, 20, new String[]{"gui.no", "gui.yes"}, this.stats.hideKilledBody ? 1 : 0));
      }

      this.addLabel(new GuiNpcLabel(5, "stats.meleeproperties", this.guiLeft + 5, this.guiTop + 65, 4210752));
      this.addButton(new GuiNpcButton(2, this.guiLeft + 82, this.guiTop + 60, 56, 20, "selectServer.edit"));
      this.addLabel(new GuiNpcLabel(6, "stats.rangedproperties", this.guiLeft + 5, this.guiTop + 89, 4210752));
      this.addButton(new GuiNpcButton(3, this.guiLeft + 82, this.guiTop + 84, 56, 20, "selectServer.edit"));
      this.addLabel(new GuiNpcLabel(7, "stats.projectileproperties", this.guiLeft + 140, this.guiTop + 89, 4210752));
      this.addButton(new GuiNpcButton(9, this.guiLeft + 217, this.guiTop + 84, 56, 20, "selectServer.edit"));
      this.addLabel(new GuiNpcLabel(15, "potion.resistance", this.guiLeft + 5, this.guiTop + 113, 4210752));
      this.addButton(new GuiNpcButton(15, this.guiLeft + 82, this.guiTop + 108, 56, 20, "selectServer.edit"));
      this.addLabel(new GuiNpcLabel(8, "stats.walkspeed", this.guiLeft + 5, this.guiTop + 141, 4210752));
      this.addTextField(new GuiNpcTextField(3, this, this.field_73886_k, this.guiLeft + 85, this.guiTop + 136, 50, 18, this.stats.moveSpeed + ""));
      this.getTextField(3).numbersOnly = true;
      this.getTextField(3).setMinMaxDefault(0, 10, 4);
      this.addLabel(new GuiNpcLabel(10, "stats.fireimmune", this.guiLeft + 5, this.guiTop + 163, 4210752));
      this.addButton(new GuiNpcButton(4, this.guiLeft + 82, this.guiTop + 158, 56, 20, new String[]{"gui.no", "gui.yes"}, this.npc.func_70045_F() ? 1 : 0));
      this.addLabel(new GuiNpcLabel(11, "stats.candrown", this.guiLeft + 140, this.guiTop + 163, 4210752));
      this.addButton(new GuiNpcButton(5, this.guiLeft + 217, this.guiTop + 158, 56, 20, new String[]{"gui.no", "gui.yes"}, this.stats.canDrown ? 1 : 0));
      this.addLabel(new GuiNpcLabel(12, "stats.burninsun", this.guiLeft + 5, this.guiTop + 185, 4210752));
      this.addButton(new GuiNpcButton(14, this.guiLeft + 355, this.guiTop + 158, 56, 20, new String[]{"gui.no", "gui.yes"}, this.stats.healthRegen ? 1 : 0));
      this.addLabel(new GuiNpcLabel(14, "stats.regenhealth", this.guiLeft + 275, this.guiTop + 163, 4210752));
      this.addButton(new GuiNpcButton(6, this.guiLeft + 82, this.guiTop + 180, 56, 20, new String[]{"gui.no", "gui.yes"}, this.stats.burnInSun ? 1 : 0));
      this.addLabel(new GuiNpcLabel(13, "stats.nofalldamage", this.guiLeft + 140, this.guiTop + 185, 4210752));
      this.addButton(new GuiNpcButton(7, this.guiLeft + 217, this.guiTop + 180, 56, 20, new String[]{"gui.no", "gui.yes"}, this.stats.noFallDamage ? 1 : 0));
   }

   public void unFocused(GuiNpcTextField textfield) {
      if (textfield.id == 0) {
         this.stats.maxHealth = textfield.getInteger();
         this.npc.func_70691_i((float)this.stats.maxHealth);
      } else if (textfield.id == 1) {
         this.stats.aggroRange = textfield.getInteger();
      } else if (textfield.id == 2) {
         this.stats.respawnTime = textfield.getInteger();
      } else if (textfield.id == 3) {
         this.stats.moveSpeed = textfield.getInteger();
      }

   }

   protected void func_73875_a(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if (button.field_73741_f == 0) {
         this.stats.spawnCycle = button.getValue();
         if (this.stats.spawnCycle == 3) {
            this.stats.respawnTime = 0;
         } else {
            this.stats.respawnTime = 20;
         }

         this.func_73866_w_();
      } else if (button.field_73741_f == 1) {
         this.stats.hideKilledBody = button.getValue() == 1;
      } else if (button.field_73741_f == 2) {
         this.setSubGui(new SubGuiNpcMeleeProperties(this.stats));
      } else if (button.field_73741_f == 3) {
         this.setSubGui(new SubGuiNpcRangeProperties(this.stats));
      } else if (button.field_73741_f == 4) {
         this.npc.setImmuneToFire(button.getValue() == 1);
      } else if (button.field_73741_f == 5) {
         this.stats.canDrown = button.getValue() == 1;
      } else if (button.field_73741_f == 6) {
         this.stats.burnInSun = button.getValue() == 1;
      } else if (button.field_73741_f == 7) {
         this.stats.noFallDamage = button.getValue() == 1;
      } else if (button.field_73741_f == 8) {
         this.stats.creatureType = EnumCreatureAttribute.values()[button.getValue()];
      } else if (button.field_73741_f == 9) {
         this.setSubGui(new SubGuiNpcProjectiles(this.stats));
      } else if (button.field_73741_f == 14) {
         this.stats.healthRegen = button.getValue() == 1;
      } else if (button.field_73741_f == 15) {
         this.setSubGui(new SubGuiNpcResistanceProperties(this.stats.resistances));
      }

   }

   public void save() {
      NoppesUtil.sendData(EnumPacketType.MainmenuStatsSave, this.stats.writeToNBT(new NBTTagCompound()));
   }

   public void setGuiData(NBTTagCompound compound) {
      this.stats.readToNBT(compound);
      this.func_73866_w_();
   }
}
