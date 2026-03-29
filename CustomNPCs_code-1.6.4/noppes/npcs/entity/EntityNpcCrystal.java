package noppes.npcs.entity;

import net.minecraft.world.World;
import noppes.npcs.EntityNPCInterface;

public class EntityNpcCrystal extends EntityNPCInterface {
   public int innerRotation;

   public EntityNpcCrystal(World world) {
      super(world);
      this.innerRotation = this.field_70146_Z.nextInt(100000);
      this.scaleX = 0.7F;
      this.scaleY = 0.7F;
      this.scaleZ = 0.7F;
      this.labelOffset = 0.8F;
      this.display.texture = "customnpcs:textures/entity/crystal/EnderCrystal.png";
   }

   public void func_70071_h_() {
      ++this.innerRotation;
      super.func_70071_h_();
   }
}
