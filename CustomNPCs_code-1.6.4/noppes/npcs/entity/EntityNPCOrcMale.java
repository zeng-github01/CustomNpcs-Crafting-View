package noppes.npcs.entity;

import net.minecraft.world.World;
import noppes.npcs.EntityNPCInterface;

public class EntityNPCOrcMale extends EntityNPCInterface {
   public EntityNPCOrcMale(World world) {
      super(world);
      this.scaleY = 1.0F;
      this.scaleX = this.scaleZ = 1.2F;
      this.display.texture = "customnpcs:textures/entity/orcmale/StrandedOrc.png";
   }
}
