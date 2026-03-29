package noppes.npcs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;

public class NBTTags {
   public static HashMap getItemStackList(NBTTagList tagList) {
      HashMap<Integer, ItemStack> list = new HashMap();

      for(int i = 0; i < tagList.func_74745_c(); ++i) {
         NBTTagCompound nbttagcompound = (NBTTagCompound)tagList.func_74743_b(i);

         try {
            list.put(nbttagcompound.func_74771_c("Slot") & 255, ItemStack.func_77949_a(nbttagcompound));
         } catch (ClassCastException var5) {
            list.put(nbttagcompound.func_74762_e("Slot"), ItemStack.func_77949_a(nbttagcompound));
         }
      }

      return list;
   }

   public static ItemStack[] getItemStackArray(NBTTagList tagList) {
      ItemStack[] list = new ItemStack[tagList.func_74745_c()];

      for(int i = 0; i < tagList.func_74745_c(); ++i) {
         NBTTagCompound nbttagcompound = (NBTTagCompound)tagList.func_74743_b(i);
         list[nbttagcompound.func_74771_c("Slot") & 255] = ItemStack.func_77949_a(nbttagcompound);
      }

      return list;
   }

   public static ArrayList getIntegerArraySet(NBTTagList tagList) {
      ArrayList<int[]> set = new ArrayList();

      for(int i = 0; i < tagList.func_74745_c(); ++i) {
         NBTTagList list = (NBTTagList)tagList.func_74743_b(i);
         int[] arr = new int[list.func_74745_c()];

         for(int j = 0; j < list.func_74745_c(); ++j) {
            NBTTagCompound nbttagcompound = (NBTTagCompound)list.func_74743_b(j);
            arr[j] = nbttagcompound.func_74762_e("Slot");
         }

         set.add(arr);
      }

      return set;
   }

   public static HashMap getBooleanList(NBTTagList tagList) {
      HashMap<Integer, Boolean> list = new HashMap();

      for(int i = 0; i < tagList.func_74745_c(); ++i) {
         NBTTagCompound nbttagcompound = (NBTTagCompound)tagList.func_74743_b(i);
         list.put(nbttagcompound.func_74762_e("Slot"), nbttagcompound.func_74767_n("Boolean"));
      }

      return list;
   }

   public static HashMap getIntegerIntegerMap(NBTTagList tagList) {
      HashMap<Integer, Integer> list = new HashMap();

      for(int i = 0; i < tagList.func_74745_c(); ++i) {
         NBTTagCompound nbttagcompound = (NBTTagCompound)tagList.func_74743_b(i);
         list.put(nbttagcompound.func_74762_e("Slot"), nbttagcompound.func_74762_e("Integer"));
      }

      return list;
   }

   public static HashMap getIntegerLongMap(NBTTagList tagList) {
      HashMap<Integer, Long> list = new HashMap();

      for(int i = 0; i < tagList.func_74745_c(); ++i) {
         NBTTagCompound nbttagcompound = (NBTTagCompound)tagList.func_74743_b(i);
         list.put(nbttagcompound.func_74762_e("Slot"), nbttagcompound.func_74763_f("Long"));
      }

      return list;
   }

   public static HashSet getIntegerSet(NBTTagList tagList) {
      HashSet<Integer> list = new HashSet();

      for(int i = 0; i < tagList.func_74745_c(); ++i) {
         NBTBase base = tagList.func_74743_b(i);
         if (base instanceof NBTTagInt) {
            list.add(((NBTTagInt)base).field_74748_a);
         } else {
            NBTTagCompound nbttagcompound = (NBTTagCompound)tagList.func_74743_b(i);
            list.add(nbttagcompound.func_74762_e("Integer"));
         }
      }

      return list;
   }

   public static HashMap getStringStringMap(NBTTagList tagList) {
      HashMap<String, String> list = new HashMap();

      for(int i = 0; i < tagList.func_74745_c(); ++i) {
         NBTTagCompound nbttagcompound = (NBTTagCompound)tagList.func_74743_b(i);
         list.put(nbttagcompound.func_74779_i("Slot"), nbttagcompound.func_74779_i("Value"));
      }

      return list;
   }

   public static HashMap getIntegerStringMap(NBTTagList tagList) {
      HashMap<Integer, String> list = new HashMap();

      for(int i = 0; i < tagList.func_74745_c(); ++i) {
         NBTTagCompound nbttagcompound = (NBTTagCompound)tagList.func_74743_b(i);
         list.put(nbttagcompound.func_74762_e("Slot"), nbttagcompound.func_74779_i("Value"));
      }

      return list;
   }

   public static HashMap getStringIntegerMap(NBTTagList tagList) {
      HashMap<String, Integer> list = new HashMap();

      for(int i = 0; i < tagList.func_74745_c(); ++i) {
         NBTTagCompound nbttagcompound = (NBTTagCompound)tagList.func_74743_b(i);
         list.put(nbttagcompound.func_74779_i("Slot"), nbttagcompound.func_74762_e("Value"));
      }

      return list;
   }

   public static HashMap getVectorMap(NBTTagList tagList) {
      HashMap<String, Vector<String>> map = new HashMap();

      for(int i = 0; i < tagList.func_74745_c(); ++i) {
         Vector<String> values = new Vector();
         NBTTagCompound nbttagcompound = (NBTTagCompound)tagList.func_74743_b(i);
         NBTTagList list = nbttagcompound.func_74761_m("Values");

         for(int j = 0; j < list.func_74745_c(); ++j) {
            NBTTagCompound value = (NBTTagCompound)list.func_74743_b(j);
            values.add(value.func_74779_i("Value"));
         }

         map.put(nbttagcompound.func_74779_i("Key"), values);
      }

      return map;
   }

   public static int[] getIntArray(NBTTagList list) {
      int[] inta = new int[list.func_74745_c()];

      for(int i = 0; i < list.func_74745_c(); ++i) {
         inta[i] = ((NBTTagInt)list.func_74743_b(i)).field_74748_a;
      }

      return inta;
   }

   public static List getStringList(NBTTagList tagList) {
      List<String> list = new ArrayList();

      for(int i = 0; i < tagList.func_74745_c(); ++i) {
         NBTTagCompound nbttagcompound = (NBTTagCompound)tagList.func_74743_b(i);
         String line = nbttagcompound.func_74779_i("Line");
         list.add(line);
      }

      return list;
   }

   public static String[] getStringArray(NBTTagList tagList, int size) {
      String[] arr = new String[size];

      for(int i = 0; i < tagList.func_74745_c(); ++i) {
         NBTTagCompound nbttagcompound = (NBTTagCompound)tagList.func_74743_b(i);
         String line = nbttagcompound.func_74779_i("Value");
         int slot = nbttagcompound.func_74762_e("Slot");
         arr[slot] = line;
      }

      return arr;
   }

   public static NBTTagList nbtIntegerArraySet(List set) {
      NBTTagList nbttaglist = new NBTTagList();
      if (set == null) {
         return nbttaglist;
      } else {
         for(int[] arr : set) {
            NBTTagList list = new NBTTagList();

            for(int i : arr) {
               NBTTagCompound nbttagcompound = new NBTTagCompound();
               nbttagcompound.func_74768_a("Slot", i);
               list.func_74742_a(nbttagcompound);
            }

            nbttaglist.func_74742_a(list);
         }

         return nbttaglist;
      }
   }

   public static NBTTagList nbtItemStackList(HashMap inventory) {
      NBTTagList nbttaglist = new NBTTagList();
      if (inventory == null) {
         return nbttaglist;
      } else {
         for(int slot : inventory.keySet()) {
            ItemStack item = (ItemStack)inventory.get(slot);
            if (item != null) {
               NBTTagCompound nbttagcompound = new NBTTagCompound();
               nbttagcompound.func_74774_a("Slot", (byte)slot);
               item.func_77955_b(nbttagcompound);
               nbttaglist.func_74742_a(nbttagcompound);
            }
         }

         return nbttaglist;
      }
   }

   public static NBTTagList nbtItemStackArray(ItemStack[] inventory) {
      NBTTagList nbttaglist = new NBTTagList();
      if (inventory == null) {
         return nbttaglist;
      } else {
         for(int slot = 0; slot < inventory.length; ++slot) {
            ItemStack item = inventory[slot];
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.func_74774_a("Slot", (byte)slot);
            if (item != null) {
               item.func_77955_b(nbttagcompound);
            }

            nbttaglist.func_74742_a(nbttagcompound);
         }

         return nbttaglist;
      }
   }

   public static NBTTagList nbtBooleanList(HashMap updatedSlots) {
      NBTTagList nbttaglist = new NBTTagList();
      if (updatedSlots == null) {
         return nbttaglist;
      } else {
         HashMap<Integer, Boolean> inventory2 = updatedSlots;

         for(Integer slot : updatedSlots.keySet()) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.func_74768_a("Slot", slot);
            nbttagcompound.func_74757_a("Boolean", (Boolean)inventory2.get(slot));
            nbttaglist.func_74742_a(nbttagcompound);
         }

         return nbttaglist;
      }
   }

   public static NBTTagList nbtIntegerIntegerMap(HashMap lines) {
      NBTTagList nbttaglist = new NBTTagList();
      if (lines == null) {
         return nbttaglist;
      } else {
         for(int slot : lines.keySet()) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.func_74768_a("Slot", slot);
            nbttagcompound.func_74768_a("Integer", (Integer)lines.get(slot));
            nbttaglist.func_74742_a(nbttagcompound);
         }

         return nbttaglist;
      }
   }

   public static NBTTagList nbtIntegerLongMap(HashMap lines) {
      NBTTagList nbttaglist = new NBTTagList();
      if (lines == null) {
         return nbttaglist;
      } else {
         for(int slot : lines.keySet()) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.func_74768_a("Slot", slot);
            nbttagcompound.func_74772_a("Long", (Long)lines.get(slot));
            nbttaglist.func_74742_a(nbttagcompound);
         }

         return nbttaglist;
      }
   }

   public static NBTTagList nbtIntegerSet(HashSet set) {
      NBTTagList nbttaglist = new NBTTagList();
      if (set == null) {
         return nbttaglist;
      } else {
         for(int slot : set) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.func_74768_a("Integer", slot);
            nbttaglist.func_74742_a(nbttagcompound);
         }

         return nbttaglist;
      }
   }

   public static NBTTagList nbtVectorMap(HashMap map) {
      NBTTagList list = new NBTTagList();
      if (map == null) {
         return list;
      } else {
         for(String key : map.keySet()) {
            NBTTagCompound compound = new NBTTagCompound();
            compound.func_74778_a("Key", key);
            NBTTagList values = new NBTTagList();

            for(String value : (Vector)map.get(key)) {
               NBTTagCompound comp = new NBTTagCompound();
               comp.func_74778_a("Value", value);
               values.func_74742_a(comp);
            }

            compound.func_74782_a("Values", values);
            list.func_74742_a(compound);
         }

         return list;
      }
   }

   public static NBTTagList nbtStringStringMap(HashMap map) {
      NBTTagList nbttaglist = new NBTTagList();
      if (map == null) {
         return nbttaglist;
      } else {
         for(String slot : map.keySet()) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.func_74778_a("Slot", slot);
            nbttagcompound.func_74778_a("Value", (String)map.get(slot));
            nbttaglist.func_74742_a(nbttagcompound);
         }

         return nbttaglist;
      }
   }

   public static NBTTagList nbtStringIntegerMap(HashMap map) {
      NBTTagList nbttaglist = new NBTTagList();
      if (map == null) {
         return nbttaglist;
      } else {
         for(String slot : map.keySet()) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.func_74778_a("Slot", slot);
            nbttagcompound.func_74768_a("Value", (Integer)map.get(slot));
            nbttaglist.func_74742_a(nbttagcompound);
         }

         return nbttaglist;
      }
   }

   public static NBTBase nbtIntegerStringMap(HashMap map) {
      NBTTagList nbttaglist = new NBTTagList();
      if (map == null) {
         return nbttaglist;
      } else {
         for(int slot : map.keySet()) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.func_74768_a("Slot", slot);
            nbttagcompound.func_74778_a("Value", (String)map.get(slot));
            nbttaglist.func_74742_a(nbttagcompound);
         }

         return nbttaglist;
      }
   }

   public static NBTTagList nbtStringArray(String[] list) {
      NBTTagList nbttaglist = new NBTTagList();
      if (list == null) {
         return nbttaglist;
      } else {
         for(int i = 0; i < list.length; ++i) {
            if (list[i] != null) {
               NBTTagCompound nbttagcompound = new NBTTagCompound();
               nbttagcompound.func_74778_a("Value", list[i]);
               nbttagcompound.func_74768_a("Slot", i);
               nbttaglist.func_74742_a(nbttagcompound);
            }
         }

         return nbttaglist;
      }
   }

   public static NBTTagList nbtIntArray(int[] ad) {
      NBTTagList nbttaglist = new NBTTagList();

      for(int i : ad) {
         nbttaglist.func_74742_a(new NBTTagInt((String)null, i));
      }

      return nbttaglist;
   }

   public static NBTTagList nbtStringList(List list) {
      NBTTagList nbttaglist = new NBTTagList();

      for(String s : list) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         nbttagcompound.func_74778_a("Line", s);
         nbttaglist.func_74742_a(nbttagcompound);
      }

      return nbttaglist;
   }
}
