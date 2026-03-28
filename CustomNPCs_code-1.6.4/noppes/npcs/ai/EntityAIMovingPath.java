package noppes.npcs.ai;

import java.util.List;
import net.minecraft.entity.ai.EntityAIBase;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.constants.EnumMovingType;

public class EntityAIMovingPath extends EntityAIBase {
   private EntityNPCInterface npc;
   private int[] pos;

   public EntityAIMovingPath(EntityNPCInterface par1EntityNPCInterface) {
      this.npc = par1EntityNPCInterface;
      this.func_75248_a(1);
   }

   public boolean func_75250_a() {
      if (this.npc.ai.movingType == EnumMovingType.MovingPath && !this.npc.isAttacking() && (this.npc.func_70681_au().nextInt(40) == 0 || !this.npc.ai.movingPause)) {
         List<int[]> list = this.npc.ai.getMovingPath();
         if (list.size() < 2) {
            return false;
         } else {
            this.npc.ai.incrementMovingPath();
            this.pos = this.npc.ai.getCurrentMovingPath();
            return true;
         }
      } else {
         return false;
      }
   }

   public boolean func_75253_b() {
      if (this.npc.isAttacking()) {
         this.npc.ai.decreaseMovingPath();
         return false;
      } else {
         return !this.npc.func_70661_as().func_75500_f();
      }
   }

   public void func_75249_e() {
      this.npc.func_70661_as().func_75492_a((double)this.pos[0] + (double)0.5F, (double)this.pos[1], (double)this.pos[2] + (double)0.5F, (double)1.0F);
   }
}
