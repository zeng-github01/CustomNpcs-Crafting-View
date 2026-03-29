package noppes.npcs.controllers;

import java.util.HashMap;
import java.util.Random;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class Lines {
   private static final Random random = new Random();
   public HashMap lines = new HashMap();

   public NBTTagCompound writeToNBT() {
      NBTTagCompound compound = new NBTTagCompound();
      NBTTagList nbttaglist = new NBTTagList();

      for(int slot : this.lines.keySet()) {
         Line line = (Line)this.lines.get(slot);
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         nbttagcompound.func_74768_a("Slot", slot);
         nbttagcompound.func_74778_a("Line", line.text);
         nbttagcompound.func_74778_a("Song", line.sound);
         nbttaglist.func_74742_a(nbttagcompound);
      }

      compound.func_74782_a("Lines", nbttaglist);
      return compound;
   }

   public void readNBT(NBTTagCompound compound) {
      NBTTagList nbttaglist = compound.func_74761_m("Lines");
      HashMap<Integer, Line> map = new HashMap();

      for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
         NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.func_74743_b(i);
         Line line = new Line();
         line.text = nbttagcompound.func_74779_i("Line");
         line.sound = nbttagcompound.func_74779_i("Song");
         map.put(nbttagcompound.func_74762_e("Slot"), line);
      }

      this.lines = map;
   }

   public Line getLine() {
      return this.lines.isEmpty() ? null : (Line)this.lines.get(random.nextInt(this.lines.size()));
   }

   public boolean isEmpty() {
      return this.lines.isEmpty();
   }
}
