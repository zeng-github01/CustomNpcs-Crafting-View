package noppes.npcs;

import net.minecraft.nbt.NBTTagCompound;

public class ModelPartData {
   public int color = 16777215;
   public String texture;
   public byte type = 0;
   public boolean playerTexture;
   private Object location;

   public ModelPartData() {
      this.playerTexture = true;
   }

   public ModelPartData(String texture) {
      this.texture = texture;
      this.playerTexture = false;
   }

   public NBTTagCompound writeToNBT() {
      NBTTagCompound compound = new NBTTagCompound();
      compound.func_74774_a("Type", this.type);
      compound.func_74768_a("Color", this.color);
      if (this.texture != null && !this.texture.isEmpty()) {
         compound.func_74778_a("Texture", this.texture);
      }

      compound.func_74757_a("PlayerTexture", this.playerTexture);
      return compound;
   }

   public void readFromNBT(NBTTagCompound compound) {
      this.type = compound.func_74771_c("Type");
      this.color = compound.func_74762_e("Color");
      this.texture = compound.func_74779_i("Texture");
      this.playerTexture = compound.func_74767_n("PlayerTexture");
      this.location = null;
   }

   public Object getResource() {
      if (this.location != null) {
         return this.location;
      } else {
         this.location = CustomNpcs.proxy.loadResource(this.texture);
         return this.location;
      }
   }

   public void setTexture(String texture, int type) {
      this.type = (byte)type;
      this.location = null;
      if (texture.isEmpty()) {
         this.playerTexture = true;
         this.texture = texture;
      } else {
         this.texture = "moreplayermodels:textures/" + texture + ".png";
         this.playerTexture = false;
      }

   }

   public String toString() {
      return "Color: " + this.color + " Type: " + this.type;
   }

   public String getColor() {
      String str;
      for(str = Integer.toHexString(this.color); str.length() < 6; str = "0" + str) {
      }

      return str;
   }
}
