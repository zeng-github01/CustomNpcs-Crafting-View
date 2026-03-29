package noppes.npcs;

import java.util.HashMap;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class DataInventory implements IInventory {
   public HashMap items = new HashMap();
   public HashMap dropchance = new HashMap();
   public HashMap weapons = new HashMap();
   public HashMap armor = new HashMap();
   public int minExp = 0;
   public int maxExp = 0;
   private EntityNPCInterface npc;

   public DataInventory(EntityNPCInterface npc) {
      this.npc = npc;
   }

   public NBTTagCompound writeEntityToNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.func_74768_a("MinExp", this.minExp);
      nbttagcompound.func_74768_a("MaxExp", this.maxExp);
      nbttagcompound.func_74782_a("NpcInv", NBTTags.nbtItemStackList(this.items));
      nbttagcompound.func_74782_a("Armor", NBTTags.nbtItemStackList(this.getArmor()));
      nbttagcompound.func_74782_a("Weapons", NBTTags.nbtItemStackList(this.getWeapons()));
      nbttagcompound.func_74782_a("DropChance", NBTTags.nbtIntegerIntegerMap(this.dropchance));
      return nbttagcompound;
   }

   public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
      this.minExp = nbttagcompound.func_74762_e("MinExp");
      this.maxExp = nbttagcompound.func_74762_e("MaxExp");
      this.items = NBTTags.getItemStackList(nbttagcompound.func_74761_m("NpcInv"));
      this.setArmor(NBTTags.getItemStackList(nbttagcompound.func_74761_m("Armor")));
      this.setWeapons(NBTTags.getItemStackList(nbttagcompound.func_74761_m("Weapons")));
      this.dropchance = NBTTags.getIntegerIntegerMap(nbttagcompound.func_74761_m("DropChance"));
      this.npc.updateTasks();
   }

   public HashMap getWeapons() {
      return this.weapons;
   }

   public void setWeapons(HashMap list) {
      this.weapons = list;
   }

   public HashMap getArmor() {
      return this.armor;
   }

   public void setArmor(HashMap list) {
      this.armor = list;
   }

   public ItemStack getWeapon() {
      return (ItemStack)this.getWeapons().get(0);
   }

   public ItemStack getProjectile() {
      return (ItemStack)this.getWeapons().get(1);
   }

   public ItemStack getOffHand() {
      return (ItemStack)this.getWeapons().get(2);
   }

   public void dropStuff(boolean dropXP) {
      if (!this.npc.field_70170_p.field_72995_K) {
         for(int i : this.items.keySet()) {
            ItemStack item = (ItemStack)this.items.get(i);
            if (item != null) {
               int dchance = 100;
               if (this.dropchance.containsKey(i)) {
                  dchance = (Integer)this.dropchance.get(i);
               }

               int chance = this.npc.field_70170_p.field_73012_v.nextInt(100) + dchance;
               if (chance >= 100) {
                  this.npc.dropPlayerItemWithRandomChoice(item.func_77946_l(), true);
               }
            }
         }

         if (dropXP) {
            int var1 = this.minExp;
            if (this.maxExp - this.minExp > 0) {
               var1 += this.npc.field_70170_p.field_73012_v.nextInt(this.maxExp - this.minExp);
            }

            while(var1 > 0) {
               int var2 = EntityXPOrb.func_70527_a(var1);
               var1 -= var2;
               this.npc.field_70170_p.func_72838_d(new EntityXPOrb(this.npc.field_70170_p, this.npc.field_70165_t, this.npc.field_70163_u, this.npc.field_70161_v, var2));
            }
         }

      }
   }

   public ItemStack armorItemInSlot(int i) {
      return (ItemStack)this.getArmor().get(i);
   }

   public int func_70302_i_() {
      return 15;
   }

   public ItemStack func_70301_a(int i) {
      if (i < 4) {
         return this.armorItemInSlot(i);
      } else {
         return i < 7 ? (ItemStack)this.getWeapons().get(i - 4) : (ItemStack)this.items.get(i - 7);
      }
   }

   public ItemStack func_70298_a(int par1, int par2) {
      int i = 0;
      HashMap<Integer, ItemStack> var3;
      if (par1 >= 7) {
         var3 = this.items;
         par1 -= 7;
      } else if (par1 >= 4) {
         var3 = this.getWeapons();
         par1 -= 4;
         i = 1;
      } else {
         var3 = this.getArmor();
         i = 2;
      }

      ItemStack var4 = null;
      if (var3.get(par1) != null) {
         if (((ItemStack)var3.get(par1)).field_77994_a <= par2) {
            var4 = (ItemStack)var3.get(par1);
            var3.put(par1, (Object)null);
         } else {
            var4 = ((ItemStack)var3.get(par1)).func_77979_a(par2);
            if (((ItemStack)var3.get(par1)).field_77994_a == 0) {
               var3.put(par1, (Object)null);
            }
         }
      }

      if (i == 1) {
         this.setWeapons(var3);
      }

      if (i == 2) {
         this.setArmor(var3);
      }

      return var4;
   }

   public ItemStack func_70304_b(int par1) {
      int i = 0;
      HashMap<Integer, ItemStack> var2;
      if (par1 >= 7) {
         var2 = this.items;
         par1 -= 7;
      } else if (par1 >= 4) {
         var2 = this.getWeapons();
         par1 -= 4;
         i = 1;
      } else {
         var2 = this.getArmor();
         i = 2;
      }

      if (var2.get(par1) != null) {
         ItemStack var3 = (ItemStack)var2.get(par1);
         var2.put(par1, (Object)null);
         if (i == 1) {
            this.setWeapons(var2);
         }

         if (i == 2) {
            this.setArmor(var2);
         }

         return var3;
      } else {
         return null;
      }
   }

   public void func_70299_a(int par1, ItemStack par2ItemStack) {
      int i = 0;
      HashMap<Integer, ItemStack> var3;
      if (par1 >= 7) {
         var3 = this.items;
         par1 -= 7;
      } else if (par1 >= 4) {
         var3 = this.getWeapons();
         par1 -= 4;
         i = 1;
      } else {
         var3 = this.getArmor();
         i = 2;
      }

      var3.put(par1, par2ItemStack);
      if (i == 1) {
         this.setWeapons(var3);
      }

      if (i == 2) {
         this.setArmor(var3);
      }

   }

   public String func_70303_b() {
      return "NPC Inventory";
   }

   public int func_70297_j_() {
      return 64;
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
