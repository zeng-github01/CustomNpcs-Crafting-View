package noppes.npcs.entity;

import net.minecraft.world.World;
import noppes.npcs.EntityNPCInterface;

public class EntityNPCElfMale extends EntityNPCInterface {
   public EntityNPCElfMale(World world) {
      super(world);
      this.scaleX = 0.85F;
      this.scaleY = 1.07F;
      this.scaleZ = 0.85F;
      this.display.texture = "customnpcs:textures/entity/elfmale/ElfMale.png";
   }
}
