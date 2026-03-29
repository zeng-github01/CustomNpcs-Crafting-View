package noppes.npcs.entity;

import net.minecraft.world.World;
import noppes.npcs.EntityNPCInterface;

public class EntityNPCDwarfFemale extends EntityNPCInterface {
   public EntityNPCDwarfFemale(World world) {
      super(world);
      this.scaleX = this.scaleZ = 0.75F;
      this.scaleY = 0.6275F;
      this.display.texture = "customnpcs:textures/entity/dwarffemale/Simone.png";
   }
}
