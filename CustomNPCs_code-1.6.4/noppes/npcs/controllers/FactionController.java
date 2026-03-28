package noppes.npcs.controllers;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.CustomNpcs;

public class FactionController {
   public HashMap factions;
   private static FactionController instance;
   private int lastUsedID = 0;

   public FactionController() {
      instance = this;
      this.factions = new HashMap();
      this.loadFactions();
      if (this.factions.isEmpty()) {
         this.factions.put(0, new Faction(0, "Friendly", 65280, 2000));
         this.factions.put(1, new Faction(1, "Neutral", 15924992, 1000));
         this.factions.put(2, new Faction(2, "Aggressive", 16711680, 0));
      }

   }

   public static FactionController getInstance() {
      return instance;
   }

   private void loadFactions() {
      File saveDir = CustomNpcs.getWorldSaveDirectory();
      if (saveDir != null) {
         try {
            File file = new File(saveDir, "factions.dat");
            if (file.exists()) {
               this.loadFactionsFile(file);
            }
         } catch (Exception var5) {
            try {
               File file = new File(saveDir, "factions.dat_old");
               if (file.exists()) {
                  this.loadFactionsFile(file);
               }
            } catch (Exception var4) {
            }
         }

      }
   }

   private void loadFactionsFile(File file) throws IOException {
      DataInputStream var1 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(new FileInputStream(file))));
      this.loadFactions(var1);
      var1.close();
   }

   public void loadFactions(DataInputStream stream) throws IOException {
      HashMap<Integer, Faction> factions = new HashMap();
      NBTTagCompound nbttagcompound1 = CompressedStreamTools.func_74794_a(stream);
      this.lastUsedID = nbttagcompound1.func_74762_e("lastID");
      NBTTagList list = nbttagcompound1.func_74761_m("NPCFactions");
      if (list != null) {
         for(int i = 0; i < list.func_74745_c(); ++i) {
            NBTTagCompound nbttagcompound = (NBTTagCompound)list.func_74743_b(i);
            Faction faction = new Faction();
            faction.readNBT(nbttagcompound);
            factions.put(faction.id, faction);
         }
      }

      this.factions = factions;
   }

   public NBTTagCompound getNBT() {
      NBTTagList list = new NBTTagList();

      for(int slot : this.factions.keySet()) {
         Faction faction = (Faction)this.factions.get(slot);
         NBTTagCompound nbtfactions = new NBTTagCompound();
         faction.writeNBT(nbtfactions);
         list.func_74742_a(nbtfactions);
      }

      NBTTagCompound nbttagcompound = new NBTTagCompound();
      nbttagcompound.func_74768_a("lastID", this.lastUsedID);
      nbttagcompound.func_74782_a("NPCFactions", list);
      return nbttagcompound;
   }

   public void saveFactions() {
      try {
         File saveDir = CustomNpcs.getWorldSaveDirectory();
         File file = new File(saveDir, "factions.dat_new");
         File file1 = new File(saveDir, "factions.dat_old");
         File file2 = new File(saveDir, "factions.dat");
         CompressedStreamTools.func_74799_a(this.getNBT(), new FileOutputStream(file));
         if (file1.exists()) {
            file1.delete();
         }

         file2.renameTo(file1);
         if (file2.exists()) {
            file2.delete();
         }

         file.renameTo(file2);
         if (file.exists()) {
            file.delete();
         }
      } catch (Exception e) {
         System.err.println(e.getMessage());
      }

   }

   public Faction getFaction(int faction) {
      return (Faction)this.factions.get(faction);
   }

   public void saveFaction(Faction faction) {
      if (faction.id < 0) {
         for(faction.id = this.getUnusedId(); this.hasName(faction.name); faction.name = faction.name + "_") {
         }
      } else {
         Faction existing = (Faction)this.factions.get(faction.id);
         if (existing != null && !existing.name.equals(faction.name)) {
            while(this.hasName(faction.name)) {
               faction.name = faction.name + "_";
            }
         }
      }

      this.factions.remove(faction.id);
      this.factions.put(faction.id, faction);
      this.saveFactions();
   }

   public int getUnusedId() {
      if (this.lastUsedID == 0) {
         for(int catid : this.factions.keySet()) {
            if (catid > this.lastUsedID) {
               this.lastUsedID = catid;
            }
         }
      }

      ++this.lastUsedID;
      return this.lastUsedID;
   }

   public void removeFaction(int id) {
      if (id >= 0 && this.factions.size() > 1) {
         this.factions.remove(id);
         this.saveFactions();
      }
   }

   public int getFirstFactionId() {
      return (Integer)this.factions.keySet().iterator().next();
   }

   public Faction getFirstFaction() {
      return (Faction)this.factions.values().iterator().next();
   }

   public boolean hasName(String newName) {
      if (newName.trim().isEmpty()) {
         return true;
      } else {
         for(Faction faction : this.factions.values()) {
            if (faction.name.equals(newName)) {
               return true;
            }
         }

         return false;
      }
   }
}
