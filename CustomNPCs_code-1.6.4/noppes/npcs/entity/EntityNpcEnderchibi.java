package noppes.npcs.entity;

import net.minecraft.world.World;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;

public class EntityNpcEnderchibi extends EntityNPCInterface {
   public EntityNpcEnderchibi(World world) {
      super(world);
      this.display.texture = "customnpcs:textures/entity/enderchibi/MrEnderchibi.png";
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (this.field_70170_p.field_72995_K) {
         NoppesUtil.spawnEnderchibi(this);
      }

   }
}
