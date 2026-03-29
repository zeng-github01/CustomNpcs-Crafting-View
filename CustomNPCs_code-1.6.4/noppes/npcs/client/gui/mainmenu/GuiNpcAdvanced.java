package noppes.npcs.client.gui.mainmenu;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.GuiNPCDialogNpcOptions;
import noppes.npcs.client.gui.GuiNPCFactionSetup;
import noppes.npcs.client.gui.GuiNPCLinesMenu;
import noppes.npcs.client.gui.GuiNPCSoundsMenu;
import noppes.npcs.client.gui.roles.GuiNpcBard;
import noppes.npcs.client.gui.roles.GuiNpcBoss;
import noppes.npcs.client.gui.roles.GuiNpcConversation;
import noppes.npcs.client.gui.roles.GuiNpcGuard;
import noppes.npcs.client.gui.roles.GuiNpcHealer;
import noppes.npcs.client.gui.roles.GuiNpcSpawner;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.constants.EnumJobType;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.constants.EnumRoleType;

public class GuiNpcAdvanced extends GuiNPCInterface2 implements IGuiData {
   private boolean hasChanges = false;

   public GuiNpcAdvanced(EntityNPCInterface npc) {
      super(npc, 4);
      NoppesUtil.sendData(EnumPacketType.MainmenuAdvancedGet);
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addButton(new GuiNpcButton(3, this.guiLeft + 85 + 160, this.guiTop + 20, 52, 20, "selectServer.edit"));
      this.addButton(new GuiNpcButton(8, this.guiLeft + 85, this.guiTop + 20, 155, 20, new String[]{"role.none", "role.trader", "role.follower", "role.bank", "role.transporter", "role.mailman"}, this.npc.advanced.role.ordinal()));
      this.getButton(3).field_73742_g = this.npc.advanced.role != EnumRoleType.None && this.npc.advanced.role != EnumRoleType.Postman;
      this.addButton(new GuiNpcButton(4, this.guiLeft + 85 + 160, this.guiTop + 43, 52, 20, "selectServer.edit"));
      this.addButton(new GuiNpcButton(5, this.guiLeft + 85, this.guiTop + 43, 155, 20, new String[]{"job.none", "job.bard", "job.healer", "job.guard", "job.itemgiver", "Boss(WIP)", "job.spawner", "job.conversation"}, this.npc.advanced.job.ordinal()));
      this.getButton(4).field_73742_g = this.npc.advanced.job != EnumJobType.None;
      this.addButton(new GuiNpcButton(7, this.guiLeft + 85, this.guiTop + 66, 214, 20, "advanced.lines"));
      this.addButton(new GuiNpcButton(9, this.guiLeft + 85, this.guiTop + 89, 214, 20, "menu.factions"));
      this.addButton(new GuiNpcButton(10, this.guiLeft + 85, this.guiTop + 112, 214, 20, "dialog.dialogs"));
      this.addButton(new GuiNpcButton(11, this.guiLeft + 85, this.guiTop + 135, 214, 20, "advanced.sounds"));
   }

   protected void func_73875_a(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if (guibutton.field_73741_f == 3) {
         switch (this.npc.advanced.role) {
            case Trader:
               this.save();
               NoppesUtil.requestOpenGUI(EnumGuiType.SetupTrader);
               break;
            case Follower:
               this.save();
               NoppesUtil.requestOpenGUI(EnumGuiType.SetupFollower);
               break;
            case Bank:
               this.save();
               NoppesUtil.requestOpenGUI(EnumGuiType.SetupBank);
               break;
            case Transporter:
               this.save();
               NoppesUtil.requestOpenGUI(EnumGuiType.SetupTransporter);
         }
      }

      if (guibutton.field_73741_f == 8) {
         this.hasChanges = true;
         this.npc.advanced.setRole(button.getValue());
         this.getButton(3).field_73742_g = this.npc.advanced.role != EnumRoleType.None && this.npc.advanced.role != EnumRoleType.Postman;
      }

      if (guibutton.field_73741_f == 4) {
         switch (this.npc.advanced.job) {
            case Bard:
               NoppesUtil.openGUI(this.player, new GuiNpcBard(this.npc));
               break;
            case Healer:
               this.save();
               NoppesUtil.openGUI(this.player, new GuiNpcHealer(this.npc));
               break;
            case Guard:
               this.save();
               NoppesUtil.openGUI(this.player, new GuiNpcGuard(this.npc));
               break;
            case ItemGiver:
               this.close();
               NoppesUtil.requestOpenGUI(EnumGuiType.SetupItemGiver);
               break;
            case Boss:
               this.close();
               NoppesUtil.openGUI(this.player, new GuiNpcBoss(this.npc));
               break;
            case Spawner:
               this.close();
               NoppesUtil.openGUI(this.player, new GuiNpcSpawner(this.npc));
               break;
            case Conversation:
               this.close();
               NoppesUtil.openGUI(this.player, new GuiNpcConversation(this.npc));
         }
      }

      if (guibutton.field_73741_f == 5) {
         this.hasChanges = true;
         this.npc.advanced.setJob(button.getValue());
         this.getButton(4).field_73742_g = this.npc.advanced.job != EnumJobType.None;
      }

      if (guibutton.field_73741_f == 9) {
         NoppesUtil.openGUI(this.player, new GuiNPCFactionSetup(this.npc));
      }

      if (guibutton.field_73741_f == 10) {
         NoppesUtil.openGUI(this.player, new GuiNPCDialogNpcOptions(this.npc, this));
      }

      if (guibutton.field_73741_f == 11) {
         NoppesUtil.openGUI(this.player, new GuiNPCSoundsMenu(this.npc));
      }

      if (guibutton.field_73741_f == 7) {
         NoppesUtil.openGUI(this.player, new GuiNPCLinesMenu(this.npc));
      }

   }

   public void setGuiData(NBTTagCompound compound) {
      this.npc.advanced.readToNBT(compound);
      this.func_73866_w_();
   }

   public void save() {
      if (this.hasChanges) {
         NoppesUtil.sendData(EnumPacketType.MainmenuAdvancedSave, this.npc.advanced.writeToNBT(new NBTTagCompound()));
         this.hasChanges = false;
      }

   }
}
