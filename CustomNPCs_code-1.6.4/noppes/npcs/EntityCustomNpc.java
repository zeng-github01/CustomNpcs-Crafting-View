package noppes.npcs;

import java.util.HashMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class EntityCustomNpc extends EntityNPCInterface {
   public Entity renderEntity;
   public String renderEntityName = "";
   public ModelPartConfig arms = new ModelPartConfig();
   public ModelPartConfig body = new ModelPartConfig();
   public ModelPartConfig legs = new ModelPartConfig();
   public ModelPartConfig head = new ModelPartConfig();
   public ModelPartData legParts = new ModelPartData();
   private HashMap parts = new HashMap();
   public byte breasts = 0;
   public int animationStart;

   public EntityCustomNpc(World world) {
      super(world);
   }

   public void func_70037_a(NBTTagCompound compound) {
      super.func_70037_a(compound);
      this.setRenderEntityName(compound.func_74779_i("RenderEntityName"));
      this.arms.readFromNBT(compound.func_74775_l("ArmsConfig"));
      this.body.readFromNBT(compound.func_74775_l("BodyConfig"));
      this.legs.readFromNBT(compound.func_74775_l("LegsConfig"));
      this.head.readFromNBT(compound.func_74775_l("HeadConfig"));
      this.legParts.readFromNBT(compound.func_74775_l("LegParts"));
      HashMap<String, ModelPartData> parts = new HashMap();
      NBTTagList list = compound.func_74761_m("Parts");

      for(int i = 0; i < list.func_74745_c(); ++i) {
         NBTTagCompound item = (NBTTagCompound)list.func_74743_b(i);
         ModelPartData part = new ModelPartData();
         part.readFromNBT(item);
         parts.put(item.func_74779_i("PartName"), part);
      }

      this.parts = parts;
      this.breasts = compound.func_74771_c("Breasts");
   }

   public void func_70014_b(NBTTagCompound compound) {
      super.func_70014_b(compound);
      compound.func_74778_a("RenderEntityName", this.renderEntityName);
      compound.func_74766_a("ArmsConfig", this.arms.writeToNBT());
      compound.func_74766_a("BodyConfig", this.body.writeToNBT());
      compound.func_74766_a("LegsConfig", this.legs.writeToNBT());
      compound.func_74766_a("HeadConfig", this.head.writeToNBT());
      compound.func_74766_a("LegParts", this.legParts.writeToNBT());
      NBTTagList list = new NBTTagList();

      for(String name : this.parts.keySet()) {
         NBTTagCompound item = ((ModelPartData)this.parts.get(name)).writeToNBT();
         item.func_74778_a("PartName", name);
         list.func_74742_a(item);
      }

      compound.func_74782_a("Parts", list);
      compound.func_74774_a("Breasts", this.breasts);
   }

   private void setRenderEntityName(String name) {
      this.renderEntity = null;

      try {
         Class<?> cls = Class.forName(name);
         if (EntityLivingBase.class.isAssignableFrom(cls)) {
            this.renderEntity = (Entity)cls.getConstructor(World.class).newInstance(this.field_70170_p);
         }
      } catch (Exception var3) {
      }

   }

   public ModelPartData getPartData(String part) {
      return (ModelPartData)this.parts.get(part);
   }

   public float getBodyY() {
      if (this.legParts.type == 3) {
         return (0.9F - this.body.scaleY) * 0.75F + this.getLegsY();
      } else {
         return this.legParts.type == 3 ? (0.5F - this.body.scaleY) * 0.75F + this.getLegsY() : (1.0F - this.body.scaleY) * 0.75F + this.getLegsY();
      }
   }

   public float getLegsY() {
      return this.legParts.type == 3 ? (0.87F - this.legs.scaleY) * 1.0F : (1.0F - this.legs.scaleY) * 0.75F;
   }
}
