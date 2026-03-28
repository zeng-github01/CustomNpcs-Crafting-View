package noppes.npcs.ai;

import net.minecraft.entity.ai.EntityAIBase;
import noppes.npcs.EntityNPCInterface;

public class EntityAIWaterNav extends EntityAIBase {
   private EntityNPCInterface theEntity;

   public EntityAIWaterNav(EntityNPCInterface par1EntityNPCInterface) {
      this.theEntity = par1EntityNPCInterface;
      this.func_75248_a(4);
      par1EntityNPCInterface.func_70661_as().func_75495_e(true);
   }

   public boolean func_75250_a() {
      if (this.theEntity.func_70090_H() || this.theEntity.func_70058_J()) {
         if (this.theEntity.ai.canSwim) {
            return true;
         }

         if (this.theEntity.field_70123_F) {
            return true;
         }
      }

      return false;
   }

   public void func_75246_d() {
      if (this.theEntity.func_70681_au().nextFloat() < 0.8F) {
         this.theEntity.func_70683_ar().func_75660_a();
      }

   }
}
