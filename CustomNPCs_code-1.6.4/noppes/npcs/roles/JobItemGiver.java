package noppes.npcs.roles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.NBTTags;
import noppes.npcs.NpcMiscInventory;
import noppes.npcs.controllers.GlobalDataController;
import noppes.npcs.controllers.Line;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.PlayerItemGiverData;

public class JobItemGiver extends JobInterface {
   public int cooldownType = 0;
   public int givingMethod = 0;
   public int cooldown = 10;
   public NpcMiscInventory inventory = new NpcMiscInventory(9);
   public int itemGiverId = 0;
   public List lines = new ArrayList();
   private int ticks = 10;
   private List recentlyChecked = new ArrayList();
   private List toCheck;

   public JobItemGiver(EntityNPCInterface npc) {
      super(npc);
      this.lines.add("Have these items {player}");
   }

   public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.func_74768_a("igCooldownType", this.cooldownType);
      nbttagcompound.func_74768_a("igGivingMethod", this.givingMethod);
      nbttagcompound.func_74768_a("igCooldown", this.cooldown);
      nbttagcompound.func_74768_a("ItemGiverId", this.itemGiverId);
      nbttagcompound.func_74782_a("igLines", NBTTags.nbtStringList(this.lines));
      nbttagcompound.func_74766_a("igJobInventory", this.inventory.getToNBT());
   }

   public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
      this.itemGiverId = nbttagcompound.func_74762_e("ItemGiverId");
      this.cooldownType = nbttagcompound.func_74762_e("igCooldownType");
      this.givingMethod = nbttagcompound.func_74762_e("igGivingMethod");
      this.cooldown = nbttagcompound.func_74762_e("igCooldown");
      this.lines = NBTTags.getStringList(nbttagcompound.func_74761_m("igLines"));
      this.inventory.setFromNBT(nbttagcompound.func_74775_l("igJobInventory"));
      if (this.itemGiverId == 0 && GlobalDataController.instance != null) {
         this.itemGiverId = GlobalDataController.instance.incrementItemGiverId();
      }

   }

   public NBTTagList newHashMapNBTList(HashMap lines) {
      NBTTagList nbttaglist = new NBTTagList();

      for(String s : lines.keySet()) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         nbttagcompound.func_74778_a("Line", s);
         nbttagcompound.func_74772_a("Time", (Long)lines.get(s));
         nbttaglist.func_74742_a(nbttagcompound);
      }

      return nbttaglist;
   }

   public HashMap getNBTLines(NBTTagList tagList) {
      HashMap<String, Long> map = new HashMap();

      for(int i = 0; i < tagList.func_74745_c(); ++i) {
         NBTTagCompound nbttagcompound = (NBTTagCompound)tagList.func_74743_b(i);
         String line = nbttagcompound.func_74779_i("Line");
         long time = nbttagcompound.func_74763_f("Time");
         map.put(line, time);
      }

      return map;
   }

   private boolean giveItems(EntityPlayer player) {
      PlayerItemGiverData data = PlayerDataController.instance.getPlayerData(player).itemgiverData;
      if (!this.canPlayerInteract(data)) {
         return false;
      } else {
         System.out.println("test hmm");
         Vector<ItemStack> items = new Vector();
         Vector<ItemStack> toGive = new Vector();

         for(ItemStack is : this.inventory.items.values()) {
            if (is != null) {
               items.add(is.func_77946_l());
            }
         }

         if (items.isEmpty()) {
            return false;
         } else {
            if (this.isAllGiver()) {
               toGive = items;
            } else if (this.isRemainingGiver()) {
               for(ItemStack is : items) {
                  if (!this.playerHasItem(player, is.field_77993_c)) {
                     toGive.add(is);
                  }
               }
            } else if (this.isRandomGiver()) {
               toGive.add(((ItemStack)items.get(this.npc.field_70170_p.field_73012_v.nextInt(items.size()))).func_77946_l());
            } else if (this.isGiverWhenNotOwnedAny()) {
               boolean ownsItems = false;

               for(ItemStack is : items) {
                  if (this.playerHasItem(player, is.field_77993_c)) {
                     ownsItems = true;
                     break;
                  }
               }

               if (ownsItems) {
                  return false;
               }

               toGive = items;
            } else if (this.isChainedGiver()) {
               int itemIndex = data.getItemIndex(this);
               int i = 0;

               for(ItemStack item : this.inventory.items.values()) {
                  if (i == itemIndex) {
                     toGive.add(item.func_77946_l());
                     break;
                  }

                  ++i;
               }
            }

            if (this.givePlayerItems(player, toGive)) {
               if (!this.lines.isEmpty()) {
                  this.npc.say(player, new Line((String)this.lines.get(this.npc.func_70681_au().nextInt(this.lines.size()))));
               }

               if (this.isDaily()) {
                  data.setTime(this, (long)this.getDay());
               } else {
                  data.setTime(this, System.currentTimeMillis());
               }

               if (this.isChainedGiver()) {
                  data.setItemIndex(this, (data.getItemIndex(this) + 1) % this.inventory.items.size());
               }

               return true;
            } else {
               return false;
            }
         }
      }
   }

   private int getDay() {
      return (int)(this.npc.field_70170_p.func_72820_D() / 24000L);
   }

   private boolean canPlayerInteract(PlayerItemGiverData data) {
      if (this.inventory.items.isEmpty()) {
         return false;
      } else if (this.isOnTimer()) {
         if (!data.hasInteractedBefore(this)) {
            return true;
         } else {
            return data.getTime(this) + (long)(this.cooldown * 1000) < System.currentTimeMillis();
         }
      } else if (this.isGiveOnce()) {
         return !data.hasInteractedBefore(this);
      } else if (this.isDaily()) {
         if (!data.hasInteractedBefore(this)) {
            return true;
         } else {
            return (long)this.getDay() > data.getTime(this);
         }
      } else {
         return false;
      }
   }

   private boolean givePlayerItems(EntityPlayer player, Vector toGive) {
      if (toGive.isEmpty()) {
         return false;
      } else if (this.freeInventorySlots(player) < toGive.size()) {
         return false;
      } else {
         for(ItemStack is : toGive) {
            this.npc.givePlayerItem(player, is);
         }

         return true;
      }
   }

   private boolean playerHasItem(EntityPlayer player, int id) {
      for(ItemStack is : player.field_71071_by.field_70462_a) {
         if (is != null && is.field_77993_c == id) {
            return true;
         }
      }

      for(ItemStack is : player.field_71071_by.field_70460_b) {
         if (is != null && is.field_77993_c == id) {
            return true;
         }
      }

      return false;
   }

   private int freeInventorySlots(EntityPlayer player) {
      int i = 0;

      for(ItemStack is : player.field_71071_by.field_70462_a) {
         if (is == null) {
            ++i;
         }
      }

      return i;
   }

   private boolean isRandomGiver() {
      return this.givingMethod == 0;
   }

   private boolean isAllGiver() {
      return this.givingMethod == 1;
   }

   private boolean isRemainingGiver() {
      return this.givingMethod == 2;
   }

   private boolean isGiverWhenNotOwnedAny() {
      return this.givingMethod == 3;
   }

   private boolean isChainedGiver() {
      return this.givingMethod == 4;
   }

   public boolean isOnTimer() {
      return this.cooldownType == 0;
   }

   private boolean isGiveOnce() {
      return this.cooldownType == 1;
   }

   private boolean isDaily() {
      return this.cooldownType == 2;
   }

   public boolean aiShouldExecute() {
      --this.ticks;
      if (this.ticks <= 0 && !this.inventory.items.isEmpty()) {
         this.ticks = 20;
         this.toCheck = this.npc.field_70170_p.func_72872_a(EntityPlayer.class, this.npc.field_70121_D.func_72314_b((double)3.0F, (double)3.0F, (double)3.0F));
         this.toCheck.removeAll(this.recentlyChecked);
         List<EntityPlayer> listMax = this.npc.field_70170_p.func_72872_a(EntityPlayer.class, this.npc.field_70121_D.func_72314_b((double)10.0F, (double)10.0F, (double)10.0F));
         this.recentlyChecked.retainAll(listMax);
         this.recentlyChecked.addAll(this.toCheck);
         return this.toCheck.size() > 0;
      } else {
         return false;
      }
   }

   public void aiStartExecuting() {
      for(EntityPlayer player : this.toCheck) {
         if (this.npc.func_70685_l(player)) {
            this.recentlyChecked.add(player);
            this.interact(player);
         }
      }

   }

   public void killed() {
   }

   private boolean interact(EntityPlayer player) {
      if (!this.giveItems(player)) {
         this.npc.say(player, this.npc.advanced.getInteractLine());
      }

      return true;
   }

   public void delete() {
   }
}
