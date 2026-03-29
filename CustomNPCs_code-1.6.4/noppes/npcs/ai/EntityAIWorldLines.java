package noppes.npcs.ai;

import net.minecraft.entity.ai.EntityAIBase;
import noppes.npcs.EntityNPCInterface;

public class EntityAIWorldLines extends EntityAIBase {
   private EntityNPCInterface npc;

   public EntityAIWorldLines(EntityNPCInterface npc) {
      this.npc = npc;
   }

   public boolean func_75250_a() {
      return !this.npc.isAttacking() && !this.npc.isKilled() && this.npc.advanced.hasWorldLines() && this.npc.func_70681_au().nextInt(900) == 1;
   }

   public void func_75249_e() {
      this.npc.saySurrounding(this.npc.advanced.getWorldLine());
   }
}
