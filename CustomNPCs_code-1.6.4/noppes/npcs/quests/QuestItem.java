package noppes.npcs.quests;

import java.util.HashMap;
import java.util.Vector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.NpcMiscInventory;

public class QuestItem extends QuestInterface {
   public NpcMiscInventory items = new NpcMiscInventory(3);

   public void readEntityFromNBT(NBTTagCompound compound) {
      this.items.setFromNBT(compound.func_74775_l("Items"));
   }

   public void writeEntityToNBT(NBTTagCompound compound) {
      compound.func_74766_a("Items", this.items.getToNBT());
   }

   public boolean isCompleted(EntityPlayer player) {
      HashMap<Integer, ItemStack> map = this.getProcessSet(player);

      for(ItemStack reqItem : this.items.items.values()) {
         boolean done = false;

         for(ItemStack item : map.values()) {
            if (NoppesUtilPlayer.compareItems(reqItem, item, false) && item.field_77994_a >= reqItem.field_77994_a) {
               done = true;
               break;
            }
         }

         if (!done) {
            return false;
         }
      }

      return true;
   }

   public HashMap getProcessSet(EntityPlayer player) {
      HashMap<Integer, ItemStack> map = new HashMap();

      for(int slot : this.items.items.keySet()) {
         ItemStack item = (ItemStack)this.items.items.get(slot);
         if (item != null) {
            ItemStack is = item.func_77946_l();
            is.field_77994_a = 0;
            map.put(slot, is);
         }
      }

      for(ItemStack item : player.field_71071_by.field_70462_a) {
         if (item != null) {
            for(ItemStack questItem : map.values()) {
               if (NoppesUtilPlayer.compareItems(questItem, item, false)) {
                  questItem.field_77994_a += item.field_77994_a;
               }
            }
         }
      }

      return map;
   }

   public void handleComplete(EntityPlayer player) {
      for(ItemStack questitem : this.items.items.values()) {
         int stacksize = questitem.field_77994_a;

         for(int i = 0; i < player.field_71071_by.field_70462_a.length; ++i) {
            ItemStack item = player.field_71071_by.field_70462_a[i];
            if (item != null && NoppesUtilPlayer.compareItems(item, questitem, false)) {
               int size = item.field_77994_a;
               if (stacksize - size >= 0) {
                  player.field_71071_by.func_70299_a(i, (ItemStack)null);
                  item.func_77979_a(size);
               } else {
                  item.func_77979_a(stacksize);
               }

               stacksize -= size;
               if (stacksize <= 0) {
                  break;
               }
            }
         }
      }

   }

   public Vector getQuestLogStatus(EntityPlayer player) {
      Vector<String> vec = new Vector();
      HashMap<Integer, ItemStack> map = this.getProcessSet(player);

      for(int slot : map.keySet()) {
         ItemStack item = (ItemStack)map.get(slot);
         ItemStack quest = (ItemStack)this.items.items.get(slot);
         if (item != null) {
            String process = item.field_77994_a + "";
            if (item.field_77994_a > quest.field_77994_a) {
               process = quest.field_77994_a + "";
            }

            process = process + "/" + quest.field_77994_a + "";
            vec.add(item.func_82833_r() + ": " + process);
         }
      }

      return vec;
   }
}
