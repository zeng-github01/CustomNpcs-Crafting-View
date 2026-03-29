package noppes.npcs.ai;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.constants.EnumStandingType;

public class EntityAILook extends EntityAIBase {
   private final EntityNPCInterface npc;
   private int idle = 0;
   private double lookX;
   private double lookZ;
   boolean rotatebody;

   public EntityAILook(EntityNPCInterface npc) {
      this.npc = npc;
      this.func_75248_a(1);
   }

   public boolean func_75250_a() {
      return !this.npc.isAttacking() && this.npc.func_70661_as().func_75500_f() && !this.npc.func_70608_bn() && !this.npc.isKilled();
   }

   public void func_75249_e() {
      this.rotatebody = this.npc.ai.standingType == EnumStandingType.RotateBody || this.npc.ai.standingType == EnumStandingType.HeadRotation;
   }

   public void func_75251_c() {
      this.rotatebody = false;
   }

   public void func_75246_d() {
      if (this.npc.ai.standingType == EnumStandingType.Stalking) {
         EntityPlayer player = this.npc.field_70170_p.func_72890_a(this.npc, (double)16.0F);
         if (player == null) {
            this.rotatebody = true;
         } else {
            this.npc.func_70671_ap().func_75651_a(player, 10.0F, (float)this.npc.func_70646_bf());
         }
      }

      if (this.rotatebody) {
         if (this.idle == 0 && this.npc.func_70681_au().nextFloat() < 0.02F) {
            double var1 = (Math.PI * 2D) * this.npc.func_70681_au().nextDouble();
            if (this.npc.ai.standingType == EnumStandingType.HeadRotation) {
               var1 = (Math.PI / 180D) * (double)this.npc.ai.orientation + (Math.PI / 5D) + 1.8849555921538759 * this.npc.func_70681_au().nextDouble();
            }

            this.lookX = Math.cos(var1);
            this.lookZ = Math.sin(var1);
            this.idle = 20 + this.npc.func_70681_au().nextInt(20);
         }

         if (this.idle > 0) {
            --this.idle;
            this.npc.func_70671_ap().func_75650_a(this.npc.field_70165_t + this.lookX, this.npc.field_70163_u + (double)this.npc.func_70047_e(), this.npc.field_70161_v + this.lookZ, 10.0F, (float)this.npc.func_70646_bf());
         }
      }

      if (this.npc.ai.standingType == EnumStandingType.NoRotation) {
         this.npc.field_70759_as = this.npc.field_70177_z = this.npc.field_70761_aq = (float)this.npc.ai.orientation;
      }

   }
}
