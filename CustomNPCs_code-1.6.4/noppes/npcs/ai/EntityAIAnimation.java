package noppes.npcs.ai;

import net.minecraft.entity.ai.EntityAIBase;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.constants.EnumAnimation;
import noppes.npcs.constants.EnumMovingType;

public class EntityAIAnimation extends EntityAIBase {
   private EntityNPCInterface npc;
   private boolean isAttacking = false;
   private boolean isDead = false;
   private boolean isAtStartpoint = false;
   private boolean hasPath = false;

   public EntityAIAnimation(EntityNPCInterface npc) {
      this.npc = npc;
   }

   public boolean func_75250_a() {
      this.isDead = this.npc.isKilled();
      if (this.isDead) {
         return this.npc.currentAnimation != EnumAnimation.LYING;
      } else if (this.npc.ai.animationType == EnumAnimation.NONE) {
         return this.npc.currentAnimation != EnumAnimation.NONE;
      } else {
         this.isAttacking = this.npc.isAttacking();
         this.isAtStartpoint = this.npc.isVeryNearAssignedPlace();
         this.hasPath = !this.npc.func_70661_as().func_75500_f();
         boolean hasCorrectAnimation = this.npc.currentAnimation == this.npc.ai.animationType;
         if (this.npc.ai.movingType == EnumMovingType.Standing && this.hasNavigation()) {
            return hasCorrectAnimation;
         } else {
            return !hasCorrectAnimation;
         }
      }
   }

   public void func_75249_e() {
      EnumAnimation type = this.npc.ai.animationType;
      if (!this.isDead && !this.npc.isSleeping()) {
         if (this.npc.ai.movingType == EnumMovingType.Standing && this.hasNavigation() && (this.npc.ai.animationType == EnumAnimation.SITTING || this.npc.ai.animationType == EnumAnimation.LYING)) {
            type = EnumAnimation.NONE;
         }
      } else {
         type = EnumAnimation.LYING;
      }

      this.setAnimation(type);
   }

   private void setAnimation(EnumAnimation animation) {
      this.npc.currentAnimation = animation;
      this.npc.func_70096_w().func_75692_b(14, animation.ordinal());
      this.npc.updateHitbox();
      this.npc.func_70107_b(this.npc.field_70165_t, this.npc.field_70163_u, this.npc.field_70161_v);
   }

   private boolean hasNavigation() {
      return this.isAttacking || !this.isAtStartpoint || this.hasPath;
   }
}
