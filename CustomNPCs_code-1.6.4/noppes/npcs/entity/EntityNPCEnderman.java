package noppes.npcs.entity;

import net.minecraft.world.World;
import noppes.npcs.constants.EnumAnimation;

public class EntityNPCEnderman extends EntityNpcEnderchibi {
   public EntityNPCEnderman(World world) {
      super(world);
      this.labelOffset = 1.0F;
      this.display.texture = "customnpcs:textures/entity/enderman/enderman.png";
      this.display.glowTexture = "customnpcs:textures/overlays/ender_eyes.png";
      this.field_70130_N = 0.6F;
      this.field_70131_O = 2.9F;
   }

   public void updateHitbox() {
      if (this.currentAnimation == EnumAnimation.LYING) {
         this.field_70130_N = this.field_70131_O = 0.2F;
      } else if (this.currentAnimation == EnumAnimation.SITTING) {
         this.field_70130_N = 0.6F;
         this.field_70131_O = 2.3F;
      } else {
         this.field_70130_N = 0.6F;
         this.field_70131_O = 2.9F;
      }

      this.field_70130_N = this.field_70130_N / 5.0F * (float)this.display.modelSize;
      this.field_70131_O = this.field_70131_O / 5.0F * (float)this.display.modelSize;
   }
}
