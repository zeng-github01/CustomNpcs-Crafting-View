package noppes.npcs.client.gui;

import java.util.HashMap;
import java.util.Vector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNPCStringSlot;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.constants.EnumModelType;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.entity.EntityNPCDwarfFemale;
import noppes.npcs.entity.EntityNPCDwarfMale;
import noppes.npcs.entity.EntityNPCElfFemale;
import noppes.npcs.entity.EntityNPCElfMale;
import noppes.npcs.entity.EntityNPCEnderman;
import noppes.npcs.entity.EntityNPCFurryFemale;
import noppes.npcs.entity.EntityNPCFurryMale;
import noppes.npcs.entity.EntityNPCGolem;
import noppes.npcs.entity.EntityNPCHumanFemale;
import noppes.npcs.entity.EntityNPCHumanMale;
import noppes.npcs.entity.EntityNPCOrcFemale;
import noppes.npcs.entity.EntityNPCOrcMale;
import noppes.npcs.entity.EntityNPCPony;
import noppes.npcs.entity.EntityNPCVillager;
import noppes.npcs.entity.EntityNpcCrystal;
import noppes.npcs.entity.EntityNpcDragon;
import noppes.npcs.entity.EntityNpcEnderchibi;
import noppes.npcs.entity.EntityNpcMonsterFemale;
import noppes.npcs.entity.EntityNpcMonsterMale;
import noppes.npcs.entity.EntityNpcNagaFemale;
import noppes.npcs.entity.EntityNpcNagaMale;
import noppes.npcs.entity.EntityNpcSkeleton;
import noppes.npcs.entity.EntityNpcSlime;

public class GuiNpcModelSelection extends GuiNPCInterface {
   private GuiNPCStringSlot slot;
   private GuiNPCInterface2 parent;
   private HashMap data = new HashMap();

   public GuiNpcModelSelection(EntityNPCInterface npc, GuiNPCInterface2 parent) {
      super(npc);
      this.drawDefaultBackground = false;
      this.title = "Select Npc Model";
      this.parent = parent;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      Vector<String> list = new Vector();

      for(EnumModelType type : EnumModelType.values()) {
         list.add(type.name);
         this.data.put(type.name, type);
      }

      this.slot = new GuiNPCStringSlot(list, this, this.npc, false, 18);
      this.slot.func_77220_a(4, 5);
      this.slot.selected = this.npc.display.modelType.name;
      this.addButton(2, new GuiNpcButton(2, this.field_73880_f / 2 - 100, this.field_73881_g - 41, 98, 20, "gui.back"));
      this.addButton(4, new GuiNpcButton(4, this.field_73880_f / 2 + 2, this.field_73881_g - 41, 98, 20, "mco.template.button.select"));
   }

   public void func_73863_a(int i, int j, float f) {
      this.slot.func_77211_a(i, j, f);
      super.func_73863_a(i, j, f);
   }

   protected void func_73875_a(GuiButton guibutton) {
      if (guibutton.field_73741_f == 2) {
         NoppesUtil.openGUI(this.player, this.parent);
      }

      if (guibutton.field_73741_f == 4) {
         this.close();
      }

   }

   public void doubleClicked() {
      this.close();
   }

   public void save() {
      if (this.npc.display.modelType != this.data.get(this.slot.selected)) {
         this.npc.display.modelType = (EnumModelType)this.data.get(this.slot.selected);
         EntityNPCInterface newnpc = null;
         if (this.npc.display.modelType == EnumModelType.HumanMale) {
            newnpc = new EntityNPCHumanMale(this.npc.field_70170_p);
         }

         if (this.npc.display.modelType == EnumModelType.Villager) {
            newnpc = new EntityNPCVillager(this.npc.field_70170_p);
         }

         if (this.npc.display.modelType == EnumModelType.Pony) {
            newnpc = new EntityNPCPony(this.npc.field_70170_p);
         }

         if (this.npc.display.modelType == EnumModelType.HumanFemale) {
            newnpc = new EntityNPCHumanFemale(this.npc.field_70170_p);
         }

         if (this.npc.display.modelType == EnumModelType.DwarfMale) {
            newnpc = new EntityNPCDwarfMale(this.npc.field_70170_p);
         }

         if (this.npc.display.modelType == EnumModelType.FurryMale) {
            newnpc = new EntityNPCFurryMale(this.npc.field_70170_p);
         }

         if (this.npc.display.modelType == EnumModelType.MonsterMale) {
            newnpc = new EntityNpcMonsterMale(this.npc.field_70170_p);
         }

         if (this.npc.display.modelType == EnumModelType.MonsterFemale) {
            newnpc = new EntityNpcMonsterFemale(this.npc.field_70170_p);
         }

         if (this.npc.display.modelType == EnumModelType.Skeleton) {
            newnpc = new EntityNpcSkeleton(this.npc.field_70170_p);
         }

         if (this.npc.display.modelType == EnumModelType.DwarfFemale) {
            newnpc = new EntityNPCDwarfFemale(this.npc.field_70170_p);
         }

         if (this.npc.display.modelType == EnumModelType.FurryFemale) {
            newnpc = new EntityNPCFurryFemale(this.npc.field_70170_p);
         }

         if (this.npc.display.modelType == EnumModelType.OrcMale) {
            newnpc = new EntityNPCOrcMale(this.npc.field_70170_p);
         }

         if (this.npc.display.modelType == EnumModelType.OrcFemale) {
            newnpc = new EntityNPCOrcFemale(this.npc.field_70170_p);
         }

         if (this.npc.display.modelType == EnumModelType.ElfMale) {
            newnpc = new EntityNPCElfMale(this.npc.field_70170_p);
         }

         if (this.npc.display.modelType == EnumModelType.ElfFemale) {
            newnpc = new EntityNPCElfFemale(this.npc.field_70170_p);
         }

         if (this.npc.display.modelType == EnumModelType.Crystal) {
            newnpc = new EntityNpcCrystal(this.npc.field_70170_p);
         }

         if (this.npc.display.modelType == EnumModelType.EnderChibi) {
            newnpc = new EntityNpcEnderchibi(this.npc.field_70170_p);
         }

         if (this.npc.display.modelType == EnumModelType.EnderMan) {
            newnpc = new EntityNPCEnderman(this.npc.field_70170_p);
         }

         if (this.npc.display.modelType == EnumModelType.NagaFemale) {
            newnpc = new EntityNpcNagaFemale(this.npc.field_70170_p);
         }

         if (this.npc.display.modelType == EnumModelType.NagaMale) {
            newnpc = new EntityNpcNagaMale(this.npc.field_70170_p);
         }

         if (this.npc.display.modelType == EnumModelType.Dragon) {
            newnpc = new EntityNpcDragon(this.npc.field_70170_p);
         }

         if (this.npc.display.modelType == EnumModelType.Slime) {
            newnpc = new EntityNpcSlime(this.npc.field_70170_p);
         }

         if (this.npc.display.modelType == EnumModelType.Golem) {
            newnpc = new EntityNPCGolem(this.npc.field_70170_p);
         }

         String texture = newnpc.display.texture;
         NBTTagCompound nbttagcompound = this.npc.copy();
         newnpc.func_70020_e(nbttagcompound);
         String faction = this.npc.func_70096_w().func_75681_e(13);
         newnpc.func_70096_w().func_75692_b(13, faction);
         newnpc.display.texture = texture;
         NoppesUtil.sendData(EnumPacketType.ChangeModel, this.npc.startPos[0], this.npc.startPos[1], this.npc.startPos[2], newnpc.copy());
      }
   }
}
