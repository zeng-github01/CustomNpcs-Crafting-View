package noppes.npcs.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MoneyBagContents {
   private EntityPlayer player;
   private int[] coinData = new int[]{0, 0, 0, 0, 0, 0, 0};

   public MoneyBagContents(EntityPlayer player) {
      this.player = player;
   }

   public void readNBT(NBTTagCompound compound) {
      this.coinData = compound.func_74759_k("coins");
   }

   public NBTTagCompound writeNBT() {
      NBTTagCompound compound = new NBTTagCompound();
      compound.func_74783_a("coins", this.coinData);
      return compound;
   }

   public void AddCurrency(CoinType coinType, byte stackSize, ItemStack theBag) {
      this.coinData[coinType.ordinal()] += stackSize;
      theBag.field_77990_d.func_74766_a("contents", this.writeNBT());
   }

   public void WithdrawCurrencyByVal(CoinType coinType, short amount, ItemStack theBag) {
      int amtAdded = 0;
      this.coinData[coinType.ordinal()] -= amtAdded;
      theBag.field_77990_d.func_74766_a("contents", this.writeNBT());
   }

   public void WithdrawCurrencyByStack(CoinType coinType, byte numStacks, ItemStack theBag) {
      int amtAdded = 0;
      this.coinData[coinType.ordinal()] -= amtAdded;
      theBag.field_77990_d.func_74766_a("contents", this.writeNBT());
   }

   public static enum CoinType {
      WOOD,
      STONE,
      IRON,
      GOLD,
      DIAMOND,
      BRONZE,
      EMERALD;
   }
}
