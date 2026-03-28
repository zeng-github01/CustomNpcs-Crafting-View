package noppes.npcs.roles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.controllers.Bank;
import noppes.npcs.controllers.BankController;
import noppes.npcs.controllers.BankData;
import noppes.npcs.controllers.PlayerDataController;

public class RoleBank extends RoleInterface {
   public int bankId = -1;

   public RoleBank(EntityNPCInterface npc) {
      super(npc);
   }

   public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.func_74768_a("RoleBankID", this.bankId);
   }

   public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
      this.bankId = nbttagcompound.func_74762_e("RoleBankID");
   }

   public boolean interact(EntityPlayer player) {
      BankData data = PlayerDataController.instance.getBankData(player, this.bankId).getBankOrDefault(this.bankId);
      data.openBankGui(player, this.npc, this.bankId, 0);
      this.npc.say(player, this.npc.advanced.getInteractLine());
      return false;
   }

   public Bank getBank() {
      Bank bank = (Bank)BankController.getInstance().banks.get(this.bankId);
      return bank != null ? bank : (Bank)BankController.getInstance().banks.values().iterator().next();
   }
}
