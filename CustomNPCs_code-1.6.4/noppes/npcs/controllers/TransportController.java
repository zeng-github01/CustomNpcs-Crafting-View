package noppes.npcs.controllers;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.CustomNpcs;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.constants.EnumRoleType;
import noppes.npcs.roles.RoleTransporter;

public class TransportController {
   private HashMap locations = new HashMap();
   public HashMap categories = new HashMap();
   private int lastUsedID = 0;
   private static TransportController instance;

   public TransportController() {
      instance = this;
      this.loadCategories();
      if (this.categories.isEmpty()) {
         TransportCategory cat = new TransportCategory();
         cat.id = 1;
         cat.title = "Default";
         this.categories.put(cat.id, cat);
      }

   }

   private void loadCategories() {
      File saveDir = CustomNpcs.getWorldSaveDirectory();
      if (saveDir != null) {
         try {
            File file = new File(saveDir, "transport.dat");
            if (!file.exists()) {
               return;
            }

            this.loadCategoriesFile(file);
         } catch (IOException var5) {
            try {
               File file = new File(saveDir, "transport.dat_old");
               if (!file.exists()) {
                  return;
               }

               this.loadCategoriesFile(file);
            } catch (IOException var4) {
            }
         }

      }
   }

   private void loadCategoriesFile(File file) throws IOException {
      DataInputStream var1 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(new FileInputStream(file))));
      this.loadCategoriesStream(var1);
      var1.close();
   }

   public void loadCategoriesStream(DataInputStream stream) throws IOException {
      HashMap<Integer, TransportLocation> locations = new HashMap();
      HashMap<Integer, TransportCategory> categories = new HashMap();
      NBTTagCompound nbttagcompound1 = CompressedStreamTools.func_74794_a(stream);
      this.lastUsedID = nbttagcompound1.func_74762_e("lastID");
      NBTTagList list = nbttagcompound1.func_74761_m("NPCTransportCategories");
      if (list != null) {
         for(int i = 0; i < list.func_74745_c(); ++i) {
            TransportCategory category = new TransportCategory();
            NBTTagCompound compound = (NBTTagCompound)list.func_74743_b(i);
            category.readNBT(compound);

            for(TransportLocation location : category.locations.values()) {
               locations.put(location.id, location);
            }

            categories.put(category.id, category);
         }

         this.locations = locations;
         this.categories = categories;
      }
   }

   public NBTTagCompound getNBT() {
      NBTTagList list = new NBTTagList();

      for(TransportCategory category : this.categories.values()) {
         NBTTagCompound compound = new NBTTagCompound();
         category.writeNBT(compound);
         list.func_74742_a(compound);
      }

      NBTTagCompound nbttagcompound = new NBTTagCompound();
      nbttagcompound.func_74768_a("lastID", this.lastUsedID);
      nbttagcompound.func_74782_a("NPCTransportCategories", list);
      return nbttagcompound;
   }

   public void saveCategories() {
      try {
         File saveDir = CustomNpcs.getWorldSaveDirectory();
         File file = new File(saveDir, "transport.dat_new");
         File file1 = new File(saveDir, "transport.dat_old");
         File file2 = new File(saveDir, "transport.dat");
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

   public TransportLocation getTransport(int transportId) {
      return (TransportLocation)this.locations.get(transportId);
   }

   public TransportLocation getTransport(String name) {
      for(TransportLocation loc : this.locations.values()) {
         if (loc.name.equals(name)) {
            return loc;
         }
      }

      return null;
   }

   private int getUniqueIdLocation() {
      if (this.lastUsedID == 0) {
         for(int catid : this.locations.keySet()) {
            if (catid > this.lastUsedID) {
               this.lastUsedID = catid;
            }
         }
      }

      ++this.lastUsedID;
      return this.lastUsedID;
   }

   private int getUniqueIdCategory() {
      int id = 0;

      for(int catid : this.categories.keySet()) {
         if (catid > id) {
            id = catid;
         }
      }

      ++id;
      return id;
   }

   public void setLocation(TransportLocation location) {
      if (this.locations.containsKey(location.id)) {
         for(TransportCategory cat : this.categories.values()) {
            cat.locations.remove(location.id);
         }
      }

      this.locations.put(location.id, location);
      location.category.locations.put(location.id, location);
   }

   public void removeLocation(int location) {
      TransportLocation loc = (TransportLocation)this.locations.get(location);
      if (loc != null) {
         loc.category.locations.remove(location);
         this.locations.remove(location);
         this.saveCategories();
      }
   }

   private boolean containsCategoryName(String name) {
      name = name.toLowerCase();

      for(TransportCategory cat : this.categories.values()) {
         if (cat.title.toLowerCase().equals(name)) {
            return true;
         }
      }

      return false;
   }

   public void saveCategory(DataInputStream dis) throws IOException {
      String name = dis.readUTF();
      int id = dis.readInt();
      if (id < 0) {
         id = this.getUniqueIdCategory();
      }

      if (this.categories.containsKey(id)) {
         TransportCategory category = (TransportCategory)this.categories.get(id);
         if (!category.title.equals(name)) {
            while(this.containsCategoryName(name)) {
               name = name + "_";
            }

            ((TransportCategory)this.categories.get(id)).title = name;
         }
      } else {
         while(this.containsCategoryName(name)) {
            name = name + "_";
         }

         TransportCategory category = new TransportCategory();
         category.id = id;
         category.title = name;
         this.categories.put(id, category);
      }

      this.saveCategories();
   }

   public void removeCategory(int id) {
      if (this.categories.size() != 1) {
         TransportCategory cat = (TransportCategory)this.categories.get(id);
         if (cat != null) {
            for(int i : cat.locations.keySet()) {
               this.locations.remove(i);
            }

            this.categories.remove(id);
            this.saveCategories();
         }
      }
   }

   public boolean containsLocationName(String name) {
      name = name.toLowerCase();

      for(TransportLocation loc : this.locations.values()) {
         if (loc.name.toLowerCase().equals(name)) {
            return true;
         }
      }

      return false;
   }

   public static TransportController getInstance() {
      return instance;
   }

   public TransportLocation saveLocation(int categoryId, DataInputStream dis, EntityPlayerMP player, EntityNPCInterface npc) throws IOException {
      TransportCategory category = (TransportCategory)this.categories.get(categoryId);
      if (category != null && npc.advanced.role == EnumRoleType.Transporter) {
         RoleTransporter role = (RoleTransporter)npc.roleInterface;
         TransportLocation location = new TransportLocation();
         location.readNBT(CompressedStreamTools.func_74794_a(dis));
         location.category = category;
         if (role.hasTransport()) {
            location.id = role.transportId;
         }

         if (location.id < 0 || !((TransportLocation)this.locations.get(location.id)).name.equals(location.name)) {
            while(this.containsLocationName(location.name)) {
               location.name = location.name + "_";
            }
         }

         if (location.id < 0) {
            location.id = this.getUniqueIdLocation();
         }

         category.locations.put(location.id, location);
         this.locations.put(location.id, location);
         this.saveCategories();
         return location;
      } else {
         return null;
      }
   }
}
