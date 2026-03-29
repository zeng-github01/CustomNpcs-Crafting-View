package noppes.npcs.entity;

import net.minecraft.world.World;
import noppes.npcs.EntityNPCInterface;

public class EntityNPCElfFemale extends EntityNPCInterface {
   public EntityNPCElfFemale(World world) {
      super(world);
      this.display.texture = "customnpcs:textures/entity/elffemale/ElfFemale.png";
      this.scaleX = 0.8F;
      this.scaleY = 1.0F;
      this.scaleZ = 0.8F;
   }
}
