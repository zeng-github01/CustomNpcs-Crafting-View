package noppes.npcs.entity;

import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import noppes.npcs.EntityNPCInterface;

public class EntityNpcDragon extends EntityNPCInterface {
   public double[][] field_40162_d = new double[64][3];
   public int field_40164_e = -1;
   public float field_40173_aw = 0.0F;
   public float field_40172_ax = 0.0F;
   public int field_40178_aA = 0;
   public boolean isFlying = false;
   private boolean exploded = false;

   public EntityNpcDragon(World world) {
      super(world);
      this.scaleX = 0.4F;
      this.scaleY = 0.4F;
      this.scaleZ = 0.4F;
      this.labelOffset = 1.0F;
      this.display.texture = "customnpcs:textures/entity/dragon/BlackDragon.png";
   }

   public double func_70042_X() {
      return 1.1;
   }

   public double[] func_40160_a(int i, float f) {
      f = 1.0F - f;
      int j = this.field_40164_e - i * 1 & 63;
      int k = this.field_40164_e - i * 1 - 1 & 63;
      double[] ad = new double[3];
      double d = this.field_40162_d[j][0];

      double d1;
      for(d1 = this.field_40162_d[k][0] - d; d1 < (double)-180.0F; d1 += (double)360.0F) {
      }

      while(d1 >= (double)180.0F) {
         d1 -= (double)360.0F;
      }

      ad[0] = d + d1 * (double)f;
      d = this.field_40162_d[j][1];
      d1 = this.field_40162_d[k][1] - d;
      ad[1] = d + d1 * (double)f;
      ad[2] = this.field_40162_d[j][2] + (this.field_40162_d[k][2] - this.field_40162_d[j][2]) * (double)f;
      return ad;
   }

   public void func_70636_d() {
      this.field_40173_aw = this.field_40172_ax;
      if (this.field_70170_p.field_72995_K && this.func_110143_aJ() <= 0.0F) {
         if (!this.exploded) {
            this.exploded = true;
            float f = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
            float f2 = (this.field_70146_Z.nextFloat() - 0.5F) * 4.0F;
            float f4 = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
            this.field_70170_p.func_72869_a("largeexplode", this.field_70165_t + (double)f, this.field_70163_u + (double)2.0F + (double)f2, this.field_70161_v + (double)f4, (double)0.0F, (double)0.0F, (double)0.0F);
         }
      } else {
         this.exploded = false;
         float f1 = 0.2F / (MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y) * 10.0F + 1.0F);
         f1 = 0.045F;
         f1 *= (float)Math.pow((double)2.0F, this.field_70181_x);
         this.field_40172_ax += f1 * 0.5F;
      }

      super.func_70636_d();
   }

   public void updateHitbox() {
      this.field_70130_N = 1.8F;
      this.field_70131_O = 1.4F;
      this.field_70130_N = this.field_70130_N / 5.0F * (float)this.display.modelSize;
      this.field_70131_O = this.field_70131_O / 5.0F * (float)this.display.modelSize;
   }
}
