package noppes.npcs.controllers;

import java.util.HashMap;
import java.util.Vector;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TransportCategory {
   public int id = -1;
   public String title = "";
   public HashMap locations = new HashMap();

   public Vector getDefaultLocations() {
      Vector<TransportLocation> list = new Vector();

      for(TransportLocation loc : this.locations.values()) {
         if (loc.isDefault()) {
            list.add(loc);
         }
      }

      return list;
   }

   public void readNBT(NBTTagCompound compound) {
      this.id = compound.func_74762_e("CategoryId");
      this.title = compound.func_74779_i("CategoryTitle");
      NBTTagList locs = compound.func_74761_m("CategoryLocations");
      if (locs != null && locs.func_74745_c() != 0) {
         for(int ii = 0; ii < locs.func_74745_c(); ++ii) {
            TransportLocation location = new TransportLocation();
            location.readNBT((NBTTagCompound)locs.func_74743_b(ii));
            location.category = this;
            this.locations.put(location.id, location);
         }

      }
   }

   public void writeNBT(NBTTagCompound compound) {
      compound.func_74768_a("CategoryId", this.id);
      compound.func_74778_a("CategoryTitle", this.title);
      NBTTagList locs = new NBTTagList();

      for(TransportLocation location : this.locations.values()) {
         locs.func_74742_a(location.writeNBT());
      }

      compound.func_74782_a("CategoryLocations", locs);
   }
}
