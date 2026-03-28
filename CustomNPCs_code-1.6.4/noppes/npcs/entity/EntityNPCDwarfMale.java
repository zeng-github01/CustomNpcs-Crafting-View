package noppes.npcs.entity;

import net.minecraft.world.World;
import noppes.npcs.EntityNPCInterface;

public class EntityNPCDwarfMale extends EntityNPCInterface {
   public EntityNPCDwarfMale(World world) {
      super(world);
      this.scaleX = this.scaleZ = 0.85F;
      this.scaleY = 0.6875F;
      this.display.texture = "customnpcs:textures/entity/dwarfmale/Simon.png";
   }
}
