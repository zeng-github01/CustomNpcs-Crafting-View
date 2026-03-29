package noppes.npcs.items;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.client.NoppesUtil;

public class ItemMoneyBag extends Item {
   public ItemMoneyBag(int i) {
      super(i - 26700 + CustomNpcs.ItemStartId);
      this.field_77777_bU = 1;
      this.func_77637_a(CustomItems.tab);
   }

   public ItemStack func_77659_a(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      if (par2World.field_72995_K) {
         return par1ItemStack;
      } else {
         if (par1ItemStack.field_77990_d == null) {
            par1ItemStack.field_77990_d = new NBTTagCompound();
         }

         new MoneyBagContents(par3EntityPlayer);
         NoppesUtil.openGUI(par3EntityPlayer, new GuiScreen());
         return par1ItemStack;
      }
   }
}
