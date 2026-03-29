package noppes.npcs.controllers;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.CustomNpcs;

public class QuestController {
   public HashMap categories = new HashMap();
   public HashMap quests = new HashMap();
   public static QuestController instance;
   private int lastUsedID = 0;

   public QuestController() {
      instance = this;
      this.loadCategories();
   }

   private void loadCategories() {
      File saveDir = CustomNpcs.getWorldSaveDirectory();

      try {
         File file = new File(saveDir, "quests.dat");
         if (file.exists()) {
            this.loadCategories(file);
         }
      } catch (Exception var5) {
         try {
            File file = new File(saveDir, "quests.dat_old");
            if (file.exists()) {
               this.loadCategories(file);
            }
         } catch (Exception ee) {
            ee.printStackTrace();
         }
      }

   }

   private void loadCategories(File file) throws Exception {
      HashMap<Integer, QuestCategory> categories = new HashMap();
      HashMap<Integer, Quest> quests = new HashMap();
      NBTTagCompound nbttagcompound1 = CompressedStreamTools.func_74796_a(new FileInputStream(file));
      this.lastUsedID = nbttagcompound1.func_74762_e("lastID");
      NBTTagList list = nbttagcompound1.func_74761_m("Data");
      if (list != null) {
         for(int i = 0; i < list.func_74745_c(); ++i) {
            QuestCategory category = new QuestCategory();
            category.readNBT((NBTTagCompound)list.func_74743_b(i));
            categories.put(category.id, category);

            for(Quest quest : category.quests.values()) {
               quests.put(quest.id, quest);
            }
         }
      }

      this.quests = quests;
      this.categories = categories;
   }

   public void saveCategories() {
      try {
         File saveDir = CustomNpcs.getWorldSaveDirectory();
         NBTTagList list = new NBTTagList();

         for(QuestCategory category : this.categories.values()) {
            NBTTagCompound nbtfactions = new NBTTagCompound();
            category.writeNBT(nbtfactions);
            list.func_74742_a(nbtfactions);
         }

         NBTTagCompound nbttagcompound = new NBTTagCompound();
         nbttagcompound.func_74768_a("lastID", this.lastUsedID);
         nbttagcompound.func_74782_a("Data", list);
         File file = new File(saveDir, "quests.dat_new");
         File file1 = new File(saveDir, "quests.dat_old");
         File file2 = new File(saveDir, "quests.dat");
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
         e.printStackTrace();
      }

   }

   public void removeCategory(DataInputStream dis) throws IOException {
      int category = dis.readInt();
      QuestCategory cat = (QuestCategory)this.categories.get(category);
      if (cat != null) {
         for(int dia : cat.quests.keySet()) {
            this.quests.remove(dia);
         }

         this.categories.remove(category);
         this.saveCategories();
      }
   }

   public void removeQuest(Quest quest) {
      for(QuestCategory category : this.categories.values()) {
         category.quests.remove(quest.id);
      }

      this.quests.remove(quest.id);
      quest.category.quests.remove(quest.id);
      this.saveCategories();
   }

   public void saveCategory(QuestCategory category) {
      if (category.id < 0) {
         if (this.lastUsedID == 0) {
            for(int catid : this.categories.keySet()) {
               if (catid > this.lastUsedID) {
                  this.lastUsedID = catid;
               }
            }
         }

         ++this.lastUsedID;
         category.id = this.lastUsedID;
      }

      if (this.categories.containsKey(category.id)) {
         QuestCategory currentcategory = (QuestCategory)this.categories.get(category.id);
         if (!currentcategory.title.equals(category.title)) {
            while(this.containsCategoryName(category.title)) {
               category.title = category.title + "_";
            }
         }
      } else {
         while(this.containsCategoryName(category.title)) {
            category.title = category.title + "_";
         }
      }

      this.categories.put(category.id, category);
      this.saveCategories();
   }

   private boolean containsCategoryName(String name) {
      name = name.toLowerCase();

      for(QuestCategory cat : this.categories.values()) {
         if (cat.title.toLowerCase().equals(name)) {
            return true;
         }
      }

      return false;
   }

   private boolean containsQuestName(QuestCategory category, String name) {
      name = name.toLowerCase();

      for(Quest quest : category.quests.values()) {
         if (quest.title.toLowerCase().equals(name)) {
            return true;
         }
      }

      return false;
   }

   public Quest saveQuest(DataInputStream dis) throws IOException {
      QuestCategory category = (QuestCategory)this.categories.get(dis.readInt());
      if (category == null) {
         return null;
      } else {
         Quest quest = new Quest();
         quest.readNBT(CompressedStreamTools.func_74794_a(dis));
         quest.category = category;
         if (quest.id < 0) {
            int id = 0;

            for(int key : this.quests.keySet()) {
               if (key > id) {
                  id = key;
               }
            }

            ++id;

            for(quest.id = id; this.containsQuestName(quest.category, quest.title); quest.title = quest.title + "_") {
            }
         }

         this.quests.put(quest.id, quest);
         quest.category.quests.put(quest.id, quest);
         this.saveCategories();
         return quest;
      }
   }
}
