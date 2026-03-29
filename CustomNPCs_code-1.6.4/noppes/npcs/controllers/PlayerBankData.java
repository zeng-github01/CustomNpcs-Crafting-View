package noppes.npcs.controllers;

import java.util.HashMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class PlayerBankData implements IPlayerData {
   public HashMap banks = new HashMap();

   public void readNBT(NBTTagCompound loadPlayerData) {
      HashMap<Integer, BankData> banks = new HashMap();
      NBTTagList list = loadPlayerData.func_74761_m("BankData");
      if (list != null && list.func_74745_c() != 0) {
         for(int i = 0; i < list.func_74745_c(); ++i) {
            NBTTagCompound nbttagcompound = (NBTTagCompound)list.func_74743_b(i);
            BankData data = new BankData();
            data.readNBT(nbttagcompound);
            banks.put(data.bankId, data);
         }

         this.banks = banks;
      }
   }

   public NBTTagCompound writeNBT(NBTTagCompound playerData) {
      NBTTagList list = new NBTTagList();

      for(BankData data : this.banks.values()) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         data.writeNBT(nbttagcompound);
         list.func_74742_a(nbttagcompound);
      }

      playerData.func_74782_a("BankData", list);
      return playerData;
   }

   public BankData getBank(int bankId) {
      return (BankData)this.banks.get(bankId);
   }

   public BankData getBankOrDefault(int bankId) {
      BankData data = (BankData)this.banks.get(bankId);
      if (data != null) {
         return data;
      } else {
         Bank bank = BankController.getInstance().getBank(bankId);
         return (BankData)this.banks.get(bank.id);
      }
   }

   public boolean hasBank(int bank) {
      return this.banks.containsKey(bank);
   }

   public void loadNew(int bank) {
      BankData data = new BankData();
      data.bankId = bank;
      this.banks.put(bank, data);
   }
}
