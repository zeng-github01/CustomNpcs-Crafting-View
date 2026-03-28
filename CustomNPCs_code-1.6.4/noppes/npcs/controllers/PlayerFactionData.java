package noppes.npcs.controllers;

import java.util.HashMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class PlayerFactionData implements IPlayerData {
   public HashMap factionData = new HashMap();

   public void readNBT(NBTTagCompound compound) {
      HashMap<Integer, Integer> factionData = new HashMap();
      if (compound != null) {
         NBTTagList list = compound.func_74761_m("FactionData");
         if (list != null && list.func_74745_c() != 0) {
            for(int i = 0; i < list.func_74745_c(); ++i) {
               NBTTagCompound nbttagcompound = (NBTTagCompound)list.func_74743_b(i);
               factionData.put(nbttagcompound.func_74762_e("Faction"), nbttagcompound.func_74762_e("Points"));
            }

            this.factionData = factionData;
         }
      }
   }

   public NBTTagCompound writeNBT(NBTTagCompound compound) {
      NBTTagList list = new NBTTagList();

      for(int faction : this.factionData.keySet()) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         nbttagcompound.func_74768_a("Faction", faction);
         nbttagcompound.func_74768_a("Points", (Integer)this.factionData.get(faction));
         list.func_74742_a(nbttagcompound);
      }

      compound.func_74782_a("FactionData", list);
      return compound;
   }

   public int getFactionPoints(int id) {
      if (!this.factionData.containsKey(id)) {
         Faction faction = FactionController.getInstance().getFaction(id);
         this.factionData.put(id, faction == null ? -1 : faction.defaultPoints);
      }

      return (Integer)this.factionData.get(id);
   }

   public void increasePoints(int factionId, int points) {
      if (!this.factionData.containsKey(factionId)) {
         Faction faction = FactionController.getInstance().getFaction(factionId);
         this.factionData.put(factionId, faction == null ? -1 : faction.defaultPoints);
      }

      this.factionData.put(factionId, (Integer)this.factionData.get(factionId) + points);
   }

   public NBTTagCompound getPlayerGuiData() {
      NBTTagCompound compound = new NBTTagCompound();
      this.writeNBT(compound);
      NBTTagList list = new NBTTagList();

      for(int id : this.factionData.keySet()) {
         Faction faction = FactionController.getInstance().getFaction(id);
         if (faction != null && !faction.hideFaction) {
            NBTTagCompound com = new NBTTagCompound();
            faction.writeNBT(com);
            list.func_74742_a(com);
         }
      }

      compound.func_74782_a("FactionList", list);
      return compound;
   }
}
