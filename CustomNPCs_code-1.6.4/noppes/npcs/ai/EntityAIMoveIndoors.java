package noppes.npcs.ai;

import java.util.Random;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityAIMoveIndoors extends EntityAIBase {
   private EntityCreature theCreature;
   private double shelterX;
   private double shelterY;
   private double shelterZ;
   private World theWorld;

   public EntityAIMoveIndoors(EntityCreature par1EntityCreature) {
      this.theCreature = par1EntityCreature;
      this.theWorld = par1EntityCreature.field_70170_p;
      this.func_75248_a(1);
   }

   public boolean func_75250_a() {
      int x = MathHelper.func_76128_c(this.theCreature.field_70165_t);
      int y = (int)this.theCreature.field_70121_D.field_72338_b;
      int z = MathHelper.func_76128_c(this.theCreature.field_70161_v);
      if ((!this.theCreature.field_70170_p.func_72935_r() || this.theCreature.field_70170_p.func_72896_J()) && !this.theCreature.field_70170_p.field_73011_w.field_76576_e) {
         if (!this.theWorld.func_72937_j(x, y, z) && this.theWorld.func_72883_k(x, y, z) > 8) {
            return false;
         } else {
            Vec3 var1 = this.findPossibleShelter();
            if (var1 == null) {
               return false;
            } else {
               this.shelterX = var1.field_72450_a;
               this.shelterY = var1.field_72448_b;
               this.shelterZ = var1.field_72449_c;
               return true;
            }
         }
      } else {
         return false;
      }
   }

   public boolean func_75253_b() {
      return !this.theCreature.func_70661_as().func_75500_f();
   }

   public void func_75249_e() {
      this.theCreature.func_70661_as().func_75492_a(this.shelterX, this.shelterY, this.shelterZ, (double)1.0F);
   }

   private Vec3 findPossibleShelter() {
      Random var1 = this.theCreature.func_70681_au();

      for(int var2 = 0; var2 < 10; ++var2) {
         int var3 = MathHelper.func_76128_c(this.theCreature.field_70165_t + (double)var1.nextInt(20) - (double)10.0F);
         int var4 = MathHelper.func_76128_c(this.theCreature.field_70121_D.field_72338_b + (double)var1.nextInt(6) - (double)3.0F);
         int var5 = MathHelper.func_76128_c(this.theCreature.field_70161_v + (double)var1.nextInt(20) - (double)10.0F);
         if (!this.theWorld.func_72937_j(var3, var4, var5) && this.theWorld.func_72883_k(var3, var4, var5) > 8) {
            return this.theWorld.func_82732_R().func_72345_a((double)var3, (double)var4, (double)var5);
         }
      }

      return null;
   }
}
