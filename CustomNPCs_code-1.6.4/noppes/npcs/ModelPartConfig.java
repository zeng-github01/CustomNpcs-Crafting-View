package noppes.npcs;

import net.minecraft.nbt.NBTTagCompound;

public class ModelPartConfig {
   public float scaleX = 1.0F;
   public float scaleY = 1.0F;
   public float scaleZ = 1.0F;
   public float transX = 0.0F;
   public float transY = 0.0F;
   public float transZ = 0.0F;

   public NBTTagCompound writeToNBT() {
      NBTTagCompound compound = new NBTTagCompound();
      compound.func_74776_a("ScaleX", this.scaleX);
      compound.func_74776_a("ScaleY", this.scaleY);
      compound.func_74776_a("ScaleZ", this.scaleZ);
      compound.func_74776_a("TransX", this.transX);
      compound.func_74776_a("TransY", this.transY);
      compound.func_74776_a("TransZ", this.transZ);
      return compound;
   }

   public void readFromNBT(NBTTagCompound compound) {
      this.scaleX = compound.func_74760_g("ScaleX");
      this.scaleY = compound.func_74760_g("ScaleY");
      this.scaleZ = compound.func_74760_g("ScaleZ");
      this.transX = compound.func_74760_g("TransX");
      this.transY = compound.func_74760_g("TransY");
      this.transZ = compound.func_74760_g("TransZ");
   }

   public String toString() {
      return "ScaleX: " + this.scaleX + " - ScaleY: " + this.scaleY + " - ScaleZ: " + this.scaleZ;
   }

   public void setScale(float x, float y, float z) {
      this.scaleX = x;
      this.scaleY = y;
      this.scaleZ = z;
   }

   public void setScale(float x, float y) {
      this.scaleZ = this.scaleX = x;
      this.scaleY = y;
   }
}
