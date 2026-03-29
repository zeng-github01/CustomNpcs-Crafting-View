package noppes.npcs.client.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.CustomNpcs;

public class CloneController {
   public static Entity toClone = null;

   private static ArrayList loadClones() {
      try {
         File file = new File(CustomNpcs.Dir, "clonednpcs.dat");
         return !file.exists() ? new ArrayList() : loadClones(file);
      } catch (Exception e) {
         System.err.println(e.getMessage());

         try {
            File file = new File(CustomNpcs.Dir, "clonednpcs.dat_old");
            return loadClones(file);
         } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ArrayList();
         }
      }
   }

   private static ArrayList loadClones(File file) throws Exception {
      ArrayList<NBTTagCompound> clones = new ArrayList();
      NBTTagCompound nbttagcompound1 = CompressedStreamTools.func_74796_a(new FileInputStream(file));
      NBTTagList list = nbttagcompound1.func_74761_m("Data");
      if (list == null) {
         return clones;
      } else {
         for(int i = 0; i < list.func_74745_c(); ++i) {
            NBTTagCompound nbttagcompound = (NBTTagCompound)list.func_74743_b(i);
            if (!nbttagcompound.func_74764_b("ClonedDate")) {
               nbttagcompound.func_74772_a("ClonedDate", System.currentTimeMillis());
               nbttagcompound.func_74778_a("ClonedName", nbttagcompound.func_74779_i("Name"));
            }

            clones.add(nbttagcompound);
         }

         return clones;
      }
   }

   public static void saveClones(Collection clones) {
      try {
         NBTTagList list = new NBTTagList();

         for(NBTTagCompound nbt : clones) {
            list.func_74742_a(nbt);
         }

         NBTTagCompound nbttagcompound = new NBTTagCompound();
         nbttagcompound.func_74782_a("Data", list);
         File file = new File(CustomNpcs.Dir, "clonednpcs.dat_new");
         File file1 = new File(CustomNpcs.Dir, "clonednpcs.dat_old");
         File file2 = new File(CustomNpcs.Dir, "clonednpcs.dat");
         CompressedStreamTools.func_74799_a(nbttagcompound, new FileOutputStream(file));
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

   public static ArrayList getClones() {
      return loadClones();
   }

   public static void addClone(Entity entity, String name, int tab) {
      ArrayList<NBTTagCompound> clones = getClones();
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      entity.func_70039_c(nbttagcompound);
      nbttagcompound.func_74772_a("ClonedDate", System.currentTimeMillis());
      nbttagcompound.func_74778_a("ClonedName", name);
      nbttagcompound.func_74768_a("ClonedTab", tab);
      clones.add(nbttagcompound);
      saveClones(clones);
   }
}
