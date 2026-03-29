package noppes.npcs.controllers;

import java.util.HashMap;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.NBTTags;
import noppes.npcs.roles.JobItemGiver;

public class PlayerItemGiverData implements IPlayerData {
   private HashMap itemgivers = new HashMap();
   private HashMap chained = new HashMap();

   public void readNBT(NBTTagCompound compound) {
      this.chained = NBTTags.getIntegerIntegerMap(compound.func_74761_m("ItemGiverChained"));
      this.itemgivers = NBTTags.getIntegerLongMap(compound.func_74761_m("ItemGiversList"));
   }

   public NBTTagCompound writeNBT(NBTTagCompound compound) {
      compound.func_74782_a("ItemGiverChained", NBTTags.nbtIntegerIntegerMap(this.chained));
      compound.func_74782_a("ItemGiversList", NBTTags.nbtIntegerLongMap(this.itemgivers));
      return compound;
   }

   public boolean hasInteractedBefore(JobItemGiver jobItemGiver) {
      return this.itemgivers.containsKey(jobItemGiver.itemGiverId);
   }

   public long getTime(JobItemGiver jobItemGiver) {
      return (Long)this.itemgivers.get(jobItemGiver.itemGiverId);
   }

   public void setTime(JobItemGiver jobItemGiver, long day) {
      this.itemgivers.put(jobItemGiver.itemGiverId, day);
   }

   public int getItemIndex(JobItemGiver jobItemGiver) {
      return this.chained.containsKey(jobItemGiver.itemGiverId) ? (Integer)this.chained.get(jobItemGiver.itemGiverId) : 0;
   }

   public void setItemIndex(JobItemGiver jobItemGiver, int i) {
      this.chained.put(jobItemGiver.itemGiverId, i);
   }
}
