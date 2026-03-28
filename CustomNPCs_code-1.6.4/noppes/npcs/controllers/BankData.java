package noppes.npcs.controllers;

import java.util.HashMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.NBTTags;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.NpcMiscInventory;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.containers.ContainerNPCBankInterface;

public class BankData {
   public HashMap itemSlots = new HashMap();
   public HashMap upgradedSlots = new HashMap();
   public int unlockedSlots = 0;
   public int bankId = -1;

   public BankData() {
      for(int i = 0; i < 6; ++i) {
         this.itemSlots.put(i, new NpcMiscInventory(54));
         this.upgradedSlots.put(i, false);
      }

   }

   public void readNBT(NBTTagCompound nbttagcompound) {
      this.bankId = nbttagcompound.func_74762_e("DataBankId");
      this.unlockedSlots = nbttagcompound.func_74762_e("UnlockedSlots");
      this.itemSlots = this.getItemSlots(nbttagcompound.func_74761_m("BankInv"));
      this.upgradedSlots = NBTTags.getBooleanList(nbttagcompound.func_74761_m("UpdatedSlots"));
   }

   private HashMap getItemSlots(NBTTagList tagList) {
      HashMap<Integer, NpcMiscInventory> list = new HashMap();

      for(int i = 0; i < tagList.func_74745_c(); ++i) {
         NBTTagCompound nbttagcompound = (NBTTagCompound)tagList.func_74743_b(i);
         int slot = nbttagcompound.func_74762_e("Slot");
         NpcMiscInventory inv = new NpcMiscInventory(54);
         inv.setFromNBT(nbttagcompound.func_74775_l("BankItems"));
         list.put(slot, inv);
      }

      return list;
   }

   public void writeNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.func_74768_a("DataBankId", this.bankId);
      nbttagcompound.func_74768_a("UnlockedSlots", this.unlockedSlots);
      nbttagcompound.func_74782_a("UpdatedSlots", NBTTags.nbtBooleanList(this.upgradedSlots));
      nbttagcompound.func_74782_a("BankInv", this.nbtItemSlots(this.itemSlots));
   }

   private NBTTagList nbtItemSlots(HashMap items) {
      NBTTagList list = new NBTTagList();

      for(int slot : items.keySet()) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         nbttagcompound.func_74768_a("Slot", slot);
         nbttagcompound.func_74766_a("BankItems", ((NpcMiscInventory)items.get(slot)).getToNBT());
         list.func_74742_a(nbttagcompound);
      }

      return list;
   }

   public boolean isUpgraded(Bank bank, int slot) {
      if (bank.isUpgraded(slot)) {
         return true;
      } else {
         return bank.canBeUpgraded(slot) && (Boolean)this.upgradedSlots.get(slot);
      }
   }

   public void openBankGui(EntityPlayer player, EntityNPCInterface npc, int bankId, int slot) {
      Bank bank = (Bank)BankController.getInstance().banks.get(bankId);
      if (bank == null) {
         bank = (Bank)BankController.getInstance().banks.values().iterator().next();
      }

      if (bank.getMaxSlots() > slot) {
         if (bank.startSlots > this.unlockedSlots) {
            this.unlockedSlots = bank.startSlots;
         }

         ItemStack currency = null;
         if (this.unlockedSlots <= slot) {
            currency = bank.currencyInventory.func_70301_a(slot);
            NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerBankUnlock, npc, slot, bank.id, 0);
         } else if (this.isUpgraded(bank, slot)) {
            NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerBankLarge, npc, slot, bank.id, 0);
         } else if (bank.canBeUpgraded(slot)) {
            currency = bank.upgradeInventory.func_70301_a(slot);
            NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerBankUprade, npc, slot, bank.id, 0);
         } else {
            NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerBankSmall, npc, slot, bank.id, 0);
         }

         NBTTagCompound compound = new NBTTagCompound();
         compound.func_74768_a("MaxSlots", bank.getMaxSlots());
         compound.func_74768_a("UnlockedSlots", this.unlockedSlots);
         if (currency != null) {
            compound.func_74766_a("Currency", currency.func_77955_b(new NBTTagCompound()));
            ContainerNPCBankInterface container = this.getContainer(player);
            container.currency.item = currency;
         }

         NoppesUtilServer.sendData(player, EnumPacketType.GuiData, compound);
      }
   }

   private ContainerNPCBankInterface getContainer(EntityPlayer player) {
      Container con = player.field_71070_bA;
      return con != null && con instanceof ContainerNPCBankInterface ? (ContainerNPCBankInterface)con : null;
   }
}
