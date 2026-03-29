package noppes.npcs.entity;

import net.minecraft.world.World;
import noppes.npcs.EntityNPCInterface;

public class EntityNpcSlime extends EntityNPCInterface {
   public EntityNpcSlime(World world) {
      super(world);
      this.scaleX = 2.0F;
      this.scaleY = 2.0F;
      this.scaleZ = 2.0F;
      this.labelOffset = -1.4F;
      this.display.texture = "customnpcs:textures/entity/slime/Slime.png";
   }

   public void updateHitbox() {
      this.field_70130_N = 0.8F;
      this.field_70131_O = 0.8F;
      this.field_70130_N = this.field_70130_N / 5.0F * (float)this.display.modelSize;
      this.field_70131_O = this.field_70131_O / 5.0F * (float)this.display.modelSize;
   }
}
