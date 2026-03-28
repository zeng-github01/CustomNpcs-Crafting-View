package noppes.npcs.entity;

import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.MathHelper;

public class EntityElementalStaffFX extends EntityPortalFX {
   double x;
   double y;
   double z;
   EntityLivingBase player;

   public EntityElementalStaffFX(EntityLivingBase player, double d, double d1, double d2, double f1, double f2, double f3, int color) {
      super(player.field_70170_p, player.field_70165_t + d, player.field_70163_u + d1, player.field_70161_v + d2, f1, f2, f3);
      this.player = player;
      this.x = d;
      this.y = d1;
      this.z = d2;
      float[] colors;
      if (color <= 15) {
         colors = EntitySheep.field_70898_d[color];
      } else {
         colors = new float[]{(float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F};
      }

      this.field_70552_h = colors[0];
      this.field_70553_i = colors[1];
      this.field_70551_j = colors[2];
      this.field_70547_e = (int)((double)16.0F / (Math.random() * 0.8 + 0.2));
      this.field_70145_X = false;
   }

   public void func_70071_h_() {
      if (this.player.field_70128_L) {
         this.func_70106_y();
      } else {
         this.field_70169_q = this.field_70165_t;
         this.field_70167_r = this.field_70163_u;
         this.field_70166_s = this.field_70161_v;
         float var1 = (float)this.field_70546_d / (float)this.field_70547_e;
         float var2 = var1;
         var1 = -var1 + var1 * var1 * 2.0F;
         var1 = 1.0F - var1;
         double dx = (double)(-MathHelper.func_76126_a((float)((double)(this.player.field_70177_z / 180.0F) * Math.PI)) * MathHelper.func_76134_b((float)((double)(this.player.field_70125_A / 180.0F) * Math.PI)));
         double dz = (double)(MathHelper.func_76134_b((float)((double)(this.player.field_70177_z / 180.0F) * Math.PI)) * MathHelper.func_76134_b((float)((double)(this.player.field_70125_A / 180.0F) * Math.PI)));
         this.field_70165_t = this.player.field_70165_t + this.x + dx + this.field_70159_w * (double)var1;
         this.field_70163_u = this.player.field_70163_u + this.y + this.field_70181_x * (double)var1 + (double)(1.0F - var2) - (double)(this.player.field_70125_A / 40.0F);
         this.field_70161_v = this.player.field_70161_v + this.z + dz + this.field_70179_y * (double)var1;
         if (this.field_70546_d++ >= this.field_70547_e) {
            this.func_70106_y();
         }

      }
   }

   public void func_70106_y() {
      super.func_70106_y();
   }
}
