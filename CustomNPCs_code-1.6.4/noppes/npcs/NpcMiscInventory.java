package noppes.npcs;

import java.util.HashMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NpcMiscInventory implements IInventory {
   public HashMap items = new HashMap();
   public int stackLimit = 64;
   private int size;

   public NBTTagCompound getToNBT() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      nbttagcompound.func_74782_a("NpcMiscInv", NBTTags.nbtItemStackList(this.items));
      return nbttagcompound;
   }

   public void setFromNBT(NBTTagCompound nbttagcompound) {
      this.items = NBTTags.getItemStackList(nbttagcompound.func_74761_m("NpcMiscInv"));
   }

   public NpcMiscInventory(int size) {
      this.size = size;
   }

   public int func_70302_i_() {
      return this.size;
   }

   public ItemStack func_70301_a(int var1) {
      return (ItemStack)this.items.get(var1);
   }

   public ItemStack func_70298_a(int par1, int par2) {
      ItemStack var4 = null;
      if (this.items.get(par1) != null) {
         if (((ItemStack)this.items.get(par1)).field_77994_a <= par2) {
            var4 = (ItemStack)this.items.get(par1);
            this.items.put(par1, (Object)null);
         } else {
            var4 = ((ItemStack)this.items.get(par1)).func_77979_a(par2);
            if (((ItemStack)this.items.get(par1)).field_77994_a == 0) {
               this.items.put(par1, (Object)null);
            }
         }
      }

      return var4;
   }

   public ItemStack func_70304_b(int var1) {
      if (this.items.get(var1) != null) {
         ItemStack var3 = (ItemStack)this.items.get(var1);
         this.items.put(var1, (Object)null);
         return var3;
      } else {
         return null;
      }
   }

   public void func_70299_a(int var1, ItemStack var2) {
      this.items.put(var1, var2);
   }

   public String func_70303_b() {
      return "Npc Misc Inventory";
   }

   public int func_70297_j_() {
      return this.stackLimit;
   }

   public void func_70296_d() {
   }

   public boolean func_70300_a(EntityPlayer var1) {
      return true;
   }

   public void func_70295_k_() {
   }

   public void func_70305_f() {
   }

   public boolean func_94042_c() {
      return true;
   }

   public boolean func_94041_b(int i, ItemStack itemstack) {
      return true;
   }
}
