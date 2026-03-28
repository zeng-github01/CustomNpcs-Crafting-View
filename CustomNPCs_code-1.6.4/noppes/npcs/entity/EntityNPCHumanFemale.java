package noppes.npcs.entity;

import net.minecraft.world.World;
import noppes.npcs.EntityNPCInterface;

public class EntityNPCHumanFemale extends EntityNPCInterface {
   public EntityNPCHumanFemale(World world) {
      super(world);
      this.scaleX = this.scaleY = this.scaleZ = 0.9075F;
      this.display.texture = "customnpcs:textures/entity/humanfemale/Stephanie.png";
   }
}
