package noppes.npcs.entity;

import net.minecraft.world.World;
import noppes.npcs.EntityNPCInterface;

public class EntityNPCOrcFemale extends EntityNPCInterface {
   public EntityNPCOrcFemale(World world) {
      super(world);
      this.scaleX = this.scaleY = this.scaleZ = 0.9375F;
      this.display.texture = "customnpcs:textures/entity/orcfemale/StrandedFemaleOrc.png";
   }
}
