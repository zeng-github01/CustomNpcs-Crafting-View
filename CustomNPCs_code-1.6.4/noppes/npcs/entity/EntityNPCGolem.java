package noppes.npcs.entity;

import net.minecraft.world.World;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.constants.EnumAnimation;

public class EntityNPCGolem extends EntityNPCInterface {
   public EntityNPCGolem(World world) {
      super(world);
      this.labelOffset = 0.5F;
      this.display.texture = "customnpcs:textures/entity/golem/Iron Golem.png";
   }

   public void updateHitbox() {
      if (this.currentAnimation == EnumAnimation.LYING) {
         this.field_70130_N = this.field_70131_O = 0.5F;
      } else if (this.currentAnimation == EnumAnimation.SITTING) {
         this.field_70130_N = 1.4F;
         this.field_70131_O = 2.3F;
      } else {
         this.field_70130_N = 1.4F;
         this.field_70131_O = 2.9F;
      }

      this.field_70130_N = this.field_70130_N / 5.0F * (float)this.display.modelSize;
      this.field_70131_O = this.field_70131_O / 5.0F * (float)this.display.modelSize;
   }
}
