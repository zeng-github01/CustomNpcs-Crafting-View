package noppes.npcs.controllers;

import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.EntityNPCInterface;

public class TransportLocation {
   public int id = -1;
   public String name = "default name";
   public double posX;
   public double posY;
   public double posZ;
   public int npcX;
   public int npcY;
   public int npcZ;
   public int type = 0;
   public int dimension = 0;
   public TransportCategory category;

   public void readNBT(NBTTagCompound compound) {
      if (compound != null) {
         this.id = compound.func_74762_e("Id");
         this.posX = compound.func_74769_h("PosX");
         this.posY = compound.func_74769_h("PosY");
         this.posZ = compound.func_74769_h("PosZ");
         this.npcX = compound.func_74762_e("PosNpcX");
         this.npcY = compound.func_74762_e("PosNpcY");
         this.npcZ = compound.func_74762_e("PosNpcZ");
         this.type = compound.func_74762_e("Type");
         this.dimension = compound.func_74762_e("Dimension");
         this.name = compound.func_74779_i("Name");
      }
   }

   public NBTTagCompound writeNBT() {
      NBTTagCompound compound = new NBTTagCompound();
      compound.func_74768_a("Id", this.id);
      compound.func_74780_a("PosX", this.posX);
      compound.func_74780_a("PosY", this.posY);
      compound.func_74780_a("PosZ", this.posZ);
      compound.func_74768_a("PosNpcX", this.npcX);
      compound.func_74768_a("PosNpcY", this.npcY);
      compound.func_74768_a("PosNpcZ", this.npcZ);
      compound.func_74768_a("Type", this.type);
      compound.func_74768_a("Dimension", this.dimension);
      compound.func_74778_a("Name", this.name);
      return compound;
   }

   public boolean isNpc(EntityNPCInterface npc) {
      return npc.startPos[0] == this.npcX && npc.startPos[1] == this.npcY && npc.startPos[2] == this.npcZ;
   }

   public boolean isDefault() {
      return this.type == 1;
   }
}
