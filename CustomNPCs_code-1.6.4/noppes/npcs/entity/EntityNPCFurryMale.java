package noppes.npcs.entity;

import net.minecraft.world.World;
import noppes.npcs.EntityNPCInterface;

public class EntityNPCFurryMale extends EntityNPCInterface {
   public EntityNPCFurryMale(World world) {
      super(world);
      this.display.texture = "customnpcs:textures/entity/furrymale/WolfGrey.png";
   }
}
