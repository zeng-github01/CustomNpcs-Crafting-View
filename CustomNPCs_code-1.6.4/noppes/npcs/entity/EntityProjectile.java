package noppes.npcs.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import noppes.npcs.DataStats;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.constants.EnumParticleType;
import noppes.npcs.constants.EnumPotionType;

public class EntityProjectile extends Entity implements IProjectile {
   private int xTile = -1;
   private int yTile = -1;
   private int zTile = -1;
   private int inTile = 0;
   protected boolean inGround = false;
   private int inData = 0;
   public int throwableShake = 0;
   public int arrowShake = 0;
   public boolean canBePickedUp = false;
   public boolean destroyedOnEntityHit = true;
   private EntityLivingBase thrower;
   private EntityNPCInterface npc;
   public EntityItem entityitem;
   private String throwerName = null;
   private int ticksInGround;
   public int ticksInAir = 0;
   private double accelerationX;
   private double accelerationY;
   private double accelerationZ;
   public float damage = 5.0F;
   public int punch = 0;
   public boolean accelerate = false;
   public boolean explosive = false;
   public int explosiveRadius = 0;
   public EnumPotionType effect;
   public int duration;
   public int amplify;

   public EntityProjectile(World par1World) {
      super(par1World);
      this.effect = EnumPotionType.None;
      this.duration = 5;
      this.amplify = 0;
      this.func_70105_a(0.25F, 0.25F);
   }

   protected void func_70088_a() {
      this.field_70180_af.func_82709_a(21, 5);
      this.field_70180_af.func_75682_a(22, String.valueOf(""));
      this.field_70180_af.func_75682_a(23, 5);
      this.field_70180_af.func_75682_a(24, (byte)0);
      this.field_70180_af.func_75682_a(25, 10);
      this.field_70180_af.func_75682_a(26, (byte)0);
      this.field_70180_af.func_75682_a(27, (byte)0);
      this.field_70180_af.func_75682_a(28, (byte)0);
      this.field_70180_af.func_75682_a(29, (byte)0);
      this.field_70180_af.func_75682_a(30, (byte)0);
   }

   @SideOnly(Side.CLIENT)
   public boolean func_70112_a(double par1) {
      double d1 = this.field_70121_D.func_72320_b() * (double)4.0F;
      d1 *= (double)64.0F;
      return par1 < d1 * d1;
   }

   public EntityProjectile(World par1World, EntityLivingBase par2EntityLiving, ItemStack item, boolean isNPC) {
      super(par1World);
      this.effect = EnumPotionType.None;
      this.duration = 5;
      this.amplify = 0;
      this.thrower = par2EntityLiving;
      this.setThrownItem(item);
      this.field_70180_af.func_75692_b(27, (byte)(this.getItemId() == Item.field_77704_l.field_77779_bT ? 1 : 0));
      this.func_70105_a((float)(this.field_70180_af.func_75679_c(23) / 10), (float)(this.field_70180_af.func_75679_c(23) / 10));
      this.func_70012_b(par2EntityLiving.field_70165_t, par2EntityLiving.field_70163_u + (double)par2EntityLiving.func_70047_e(), par2EntityLiving.field_70161_v, par2EntityLiving.field_70177_z, par2EntityLiving.field_70125_A);
      this.field_70165_t -= (double)(MathHelper.func_76134_b(this.field_70177_z / 180.0F * (float)Math.PI) * 0.16F);
      this.field_70163_u -= 0.3;
      this.field_70161_v -= (double)(MathHelper.func_76126_a(this.field_70177_z / 180.0F * (float)Math.PI) * 0.16F);
      this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      this.field_70129_M = 0.0F;
      if (isNPC) {
         this.npc = (EntityNPCInterface)this.thrower;
         this.getStatProperties(this.npc.stats);
      }

   }

   public void setThrownItem(ItemStack item) {
      this.field_70180_af.func_75692_b(21, item);
   }

   public void func_70186_c(double par1, double par3, double par5, float par7, float par8) {
      float f2 = MathHelper.func_76133_a(par1 * par1 + par3 * par3 + par5 * par5);
      float f3 = MathHelper.func_76133_a(par1 * par1 + par5 * par5);
      float yaw = (float)(Math.atan2(par1, par5) * (double)180.0F / Math.PI);
      float pitch = this.hasGravity() ? par7 : (float)(Math.atan2(par3, (double)f3) * (double)180.0F / Math.PI);
      this.field_70126_B = this.field_70177_z = yaw;
      this.field_70127_C = this.field_70125_A = pitch;
      this.field_70159_w = (double)(MathHelper.func_76126_a(yaw / 180.0F * (float)Math.PI) * MathHelper.func_76134_b(pitch / 180.0F * (float)Math.PI));
      this.field_70179_y = (double)(MathHelper.func_76134_b(yaw / 180.0F * (float)Math.PI) * MathHelper.func_76134_b(pitch / 180.0F * (float)Math.PI));
      this.field_70181_x = (double)MathHelper.func_76126_a((pitch + 1.0F) / 180.0F * (float)Math.PI);
      this.field_70159_w += this.field_70146_Z.nextGaussian() * (double)0.0075F * (double)par8;
      this.field_70179_y += this.field_70146_Z.nextGaussian() * (double)0.0075F * (double)par8;
      this.field_70181_x += this.field_70146_Z.nextGaussian() * (double)0.0075F * (double)par8;
      this.field_70159_w *= (double)this.getSpeed();
      this.field_70179_y *= (double)this.getSpeed();
      this.field_70181_x *= (double)this.getSpeed();
      this.accelerationX = par1 / (double)f2 * 0.1;
      this.accelerationY = par3 / (double)f2 * 0.1;
      this.accelerationZ = par5 / (double)f2 * 0.1;
      this.ticksInGround = 0;
   }

   public float getAngleForXYZ(double varX, double varY, double varZ, double horiDist, boolean arc) {
      float g = this.getGravityVelocity();
      float var1 = this.getSpeed() * this.getSpeed();
      double var2 = (double)g * horiDist;
      double var3 = (double)g * horiDist * horiDist + (double)2.0F * varY * (double)var1;
      double var4 = (double)(var1 * var1) - (double)g * var3;
      if (var4 < (double)0.0F) {
         return 30.0F;
      } else {
         float var6 = arc ? var1 + MathHelper.func_76133_a(var4) : var1 - MathHelper.func_76133_a(var4);
         float var7 = (float)(Math.atan2((double)var6, var2) * (double)180.0F / Math.PI);
         return var7;
      }
   }

   public void shoot(float speed) {
      double varX = (double)(-MathHelper.func_76126_a(this.field_70177_z / 180.0F * (float)Math.PI) * MathHelper.func_76134_b(this.field_70125_A / 180.0F * (float)Math.PI));
      double varZ = (double)(MathHelper.func_76134_b(this.field_70177_z / 180.0F * (float)Math.PI) * MathHelper.func_76134_b(this.field_70125_A / 180.0F * (float)Math.PI));
      double varY = (double)(-MathHelper.func_76126_a(this.field_70125_A / 180.0F * (float)Math.PI));
      this.func_70186_c(varX, varY, varZ, -this.field_70125_A, speed);
   }

   @SideOnly(Side.CLIENT)
   public void func_70016_h(double par1, double par3, double par5) {
      this.field_70159_w = par1;
      this.field_70181_x = par3;
      this.field_70179_y = par5;
      if (this.field_70127_C == 0.0F && this.field_70126_B == 0.0F) {
         float f = MathHelper.func_76133_a(par1 * par1 + par5 * par5);
         this.field_70126_B = this.field_70177_z = (float)(Math.atan2(par1, par5) * (double)180.0F / Math.PI);
         this.field_70127_C = this.field_70125_A = (float)(Math.atan2(par3, (double)f) * (double)180.0F / Math.PI);
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_70056_a(double par1, double par3, double par5, float par7, float par8, int par9) {
      if (!this.field_70170_p.field_72995_K || !this.inGround) {
         this.func_70107_b(par1, par3, par5);
         this.func_70101_b(par7, par8);
      }
   }

   protected float getGravityVelocity() {
      return 0.03F;
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (this.field_70127_C == 0.0F && this.field_70126_B == 0.0F) {
         float f = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
         this.field_70126_B = this.field_70177_z = (float)(Math.atan2(this.field_70159_w, this.field_70179_y) * (double)180.0F / Math.PI);
         this.field_70127_C = this.field_70125_A = (float)(Math.atan2(this.field_70181_x, (double)f) * (double)180.0F / Math.PI);
         if (this.isRotating()) {
            this.field_70125_A -= 20.0F;
         }
      }

      if (this.effect == EnumPotionType.Fire && !this.inGround) {
         this.func_70015_d(1);
      }

      int i = this.field_70170_p.func_72798_a(this.xTile, this.yTile, this.zTile);
      int j = this.field_70170_p.func_72805_g(this.xTile, this.yTile, this.zTile);
      if ((this.isArrow() || this.sticksToWalls()) && i > 0) {
         Block.field_71973_m[i].func_71902_a(this.field_70170_p, this.xTile, this.yTile, this.zTile);
         AxisAlignedBB axisalignedbb = Block.field_71973_m[i].func_71872_e(this.field_70170_p, this.xTile, this.yTile, this.zTile);
         if (axisalignedbb != null && axisalignedbb.func_72318_a(this.field_70170_p.func_82732_R().func_72345_a(this.field_70165_t, this.field_70163_u, this.field_70161_v))) {
            this.inGround = true;
         }
      }

      if (this.arrowShake > 0) {
         --this.arrowShake;
      }

      if (this.inGround) {
         if (i == this.inTile && j == this.inData) {
            ++this.ticksInGround;
            if (this.ticksInGround == 1200) {
               this.func_70106_y();
            }
         } else {
            this.inGround = false;
            this.field_70159_w *= (double)(this.field_70146_Z.nextFloat() * 0.2F);
            this.field_70181_x *= (double)(this.field_70146_Z.nextFloat() * 0.2F);
            this.field_70179_y *= (double)(this.field_70146_Z.nextFloat() * 0.2F);
            this.ticksInGround = 0;
            this.ticksInAir = 0;
         }
      } else {
         ++this.ticksInAir;
         if (this.ticksInAir == 1200) {
            this.func_70106_y();
         }

         Vec3 vec3 = this.field_70170_p.func_82732_R().func_72345_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
         Vec3 vec31 = this.field_70170_p.func_82732_R().func_72345_a(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
         MovingObjectPosition movingobjectposition = this.field_70170_p.func_72831_a(vec3, vec31, false, true);
         vec3 = this.field_70170_p.func_82732_R().func_72345_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
         vec31 = this.field_70170_p.func_82732_R().func_72345_a(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
         if (movingobjectposition != null) {
            vec31 = this.field_70170_p.func_82732_R().func_72345_a(movingobjectposition.field_72307_f.field_72450_a, movingobjectposition.field_72307_f.field_72448_b, movingobjectposition.field_72307_f.field_72449_c);
         }

         if (!this.field_70170_p.field_72995_K) {
            Entity entity = null;
            List list = this.field_70170_p.func_72839_b(this, this.field_70121_D.func_72321_a(this.field_70159_w, this.field_70181_x, this.field_70179_y).func_72314_b((double)1.0F, (double)1.0F, (double)1.0F));
            double d0 = (double)0.0F;
            EntityLivingBase entityliving = this.getThrower();

            for(int k = 0; k < list.size(); ++k) {
               Entity entity1 = (Entity)list.get(k);
               if (entity1.func_70067_L() && (entity1 != this.thrower || this.ticksInAir >= 5)) {
                  float f = 0.3F;
                  AxisAlignedBB axisalignedbb = entity1.field_70121_D.func_72314_b((double)f, (double)f, (double)f);
                  MovingObjectPosition movingobjectposition1 = axisalignedbb.func_72327_a(vec3, vec31);
                  if (movingobjectposition1 != null) {
                     double d1 = vec3.func_72438_d(movingobjectposition1.field_72307_f);
                     if (d1 < d0 || d0 == (double)0.0F) {
                        entity = entity1;
                        d0 = d1;
                     }
                  }
               }
            }

            if (entity != null) {
               movingobjectposition = new MovingObjectPosition(entity);
            }

            if (movingobjectposition != null && movingobjectposition.field_72308_g != null && movingobjectposition.field_72308_g instanceof EntityPlayer) {
               EntityPlayer entityplayer = (EntityPlayer)movingobjectposition.field_72308_g;
               if (this.thrower instanceof EntityNPCInterface && this.npc.getFaction().isFriendlyToPlayer(entityplayer)) {
                  movingobjectposition = null;
               }
            }
         }

         if (movingobjectposition != null) {
            if (movingobjectposition.field_72313_a == EnumMovingObjectType.TILE && this.field_70170_p.func_72798_a(movingobjectposition.field_72311_b, movingobjectposition.field_72312_c, movingobjectposition.field_72309_d) == Block.field_72015_be.field_71990_ca) {
               this.func_70063_aa();
            } else {
               this.field_70180_af.func_75692_b(29, (byte)0);
               this.onImpact(movingobjectposition);
            }
         }

         this.field_70165_t += this.field_70159_w;
         this.field_70163_u += this.field_70181_x;
         this.field_70161_v += this.field_70179_y;
         float f1 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
         this.field_70177_z = (float)(Math.atan2(this.field_70159_w, this.field_70179_y) * (double)180.0F / Math.PI);

         for(this.field_70125_A = (float)(Math.atan2(this.field_70181_x, (double)f1) * (double)180.0F / Math.PI); this.field_70125_A - this.field_70127_C < -180.0F; this.field_70127_C -= 360.0F) {
         }

         while(this.field_70125_A - this.field_70127_C >= 180.0F) {
            this.field_70127_C += 360.0F;
         }

         while(this.field_70177_z - this.field_70126_B < -180.0F) {
            this.field_70126_B -= 360.0F;
         }

         while(this.field_70177_z - this.field_70126_B >= 180.0F) {
            this.field_70126_B += 360.0F;
         }

         float f = this.isArrow() ? 0.0F : 225.0F;
         this.field_70125_A = this.field_70127_C + (this.field_70125_A - this.field_70127_C) + f * 0.2F;
         this.field_70177_z = this.field_70126_B + (this.field_70177_z - this.field_70126_B) * 0.2F;
         if (this.isRotating()) {
            int spin = this.isBlock() ? 10 : 20;
            this.field_70125_A -= (float)(this.ticksInAir * spin) * this.getSpeed();
         }

         float f2 = this.getMotionFactor();
         float f3 = this.getGravityVelocity();
         if (this.func_70090_H()) {
            for(int k = 0; k < 4; ++k) {
               float f4 = 0.25F;
               this.field_70170_p.func_72869_a("bubble", this.field_70165_t - this.field_70159_w * (double)f4, this.field_70163_u - this.field_70181_x * (double)f4, this.field_70161_v - this.field_70179_y * (double)f4, this.field_70159_w, this.field_70181_x, this.field_70179_y);
            }

            f2 = 0.8F;
         }

         this.field_70159_w *= (double)f2;
         this.field_70181_x *= (double)f2;
         this.field_70179_y *= (double)f2;
         if (this.hasGravity()) {
            this.field_70181_x -= (double)f3;
         }

         if (this.accelerate) {
            this.field_70159_w += this.accelerationX;
            this.field_70181_x += this.accelerationY;
            this.field_70179_y += this.accelerationZ;
         }

         if (!this.field_70180_af.func_75681_e(22).equals("")) {
            this.field_70170_p.func_72869_a(this.field_70180_af.func_75681_e(22), this.field_70165_t, this.field_70163_u, this.field_70161_v, (double)0.0F, (double)0.0F, (double)0.0F);
         }

         this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
         this.func_70017_D();
      }

   }

   public boolean isBlock() {
      ItemStack item = this.getItemDisplay();
      return item == null ? false : item.func_77973_b() instanceof ItemBlock;
   }

   private int getItemId() {
      ItemStack item = this.getItemDisplay();
      return item == null ? 0 : item.field_77993_c;
   }

   protected float getMotionFactor() {
      return this.accelerate ? 0.95F : 1.0F;
   }

   protected void onImpact(MovingObjectPosition movingobjectposition) {
      if (movingobjectposition.field_72308_g != null) {
         float damage = this.damage;
         if (damage == 0.0F) {
            damage = 0.001F;
         }

         if (movingobjectposition.field_72308_g.func_70097_a(DamageSource.func_76356_a(this, this.getThrower()), damage)) {
            if (movingobjectposition.field_72308_g instanceof EntityLivingBase && (this.isArrow() || this.sticksToWalls())) {
               EntityLivingBase entityliving = (EntityLivingBase)movingobjectposition.field_72308_g;
               if (!this.field_70170_p.field_72995_K) {
                  entityliving.func_85034_r(entityliving.func_85035_bI() + 1);
               }

               if (this.destroyedOnEntityHit && !(movingobjectposition.field_72308_g instanceof EntityEnderman)) {
                  this.func_70106_y();
               }
            }

            if (this.isBlock()) {
               this.field_70170_p.func_72926_e(2001, (int)movingobjectposition.field_72308_g.field_70165_t, (int)movingobjectposition.field_72308_g.field_70163_u, (int)movingobjectposition.field_72308_g.field_70161_v, this.getItemId());
            } else if (!this.isArrow() && !this.sticksToWalls()) {
               for(int i = 0; i < 8; ++i) {
                  this.field_70170_p.func_72869_a("iconcrack_" + this.getItemId(), this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70146_Z.nextGaussian() * 0.15, this.field_70146_Z.nextGaussian() * 0.2, this.field_70146_Z.nextGaussian() * 0.15);
               }
            }

            if (this.punch > 0) {
               float f3 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
               if (f3 > 0.0F) {
                  movingobjectposition.field_72308_g.func_70024_g(this.field_70159_w * (double)this.punch * (double)0.6F / (double)f3, 0.1, this.field_70179_y * (double)this.punch * (double)0.6F / (double)f3);
               }
            }

            if (this.effect != EnumPotionType.None && movingobjectposition.field_72308_g instanceof EntityLivingBase) {
               if (this.effect != EnumPotionType.Fire) {
                  int p = this.getPotionEffect(this.effect);
                  ((EntityLivingBase)movingobjectposition.field_72308_g).func_70690_d(new PotionEffect(p, this.duration * 20, this.amplify));
               } else {
                  movingobjectposition.field_72308_g.func_70015_d(this.duration);
               }
            }
         } else if (this.hasGravity() && (this.isArrow() || this.sticksToWalls())) {
            this.field_70159_w *= (double)-0.1F;
            this.field_70181_x *= (double)-0.1F;
            this.field_70179_y *= (double)-0.1F;
            this.field_70177_z += 180.0F;
            this.field_70126_B += 180.0F;
            this.ticksInAir = 0;
         }
      } else if (!this.isArrow() && !this.sticksToWalls()) {
         if (this.isBlock()) {
            this.field_70170_p.func_72926_e(2001, movingobjectposition.field_72311_b, movingobjectposition.field_72312_c, movingobjectposition.field_72309_d, this.getItemId());
         } else {
            for(int i = 0; i < 8; ++i) {
               this.field_70170_p.func_72869_a("iconcrack_" + this.getItemId(), this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70146_Z.nextGaussian() * 0.15, this.field_70146_Z.nextGaussian() * 0.2, this.field_70146_Z.nextGaussian() * 0.15);
            }
         }
      } else {
         this.xTile = movingobjectposition.field_72311_b;
         this.yTile = movingobjectposition.field_72312_c;
         this.zTile = movingobjectposition.field_72309_d;
         this.inTile = this.field_70170_p.func_72798_a(this.xTile, this.yTile, this.zTile);
         this.inData = this.field_70170_p.func_72805_g(this.xTile, this.yTile, this.zTile);
         this.field_70159_w = (double)((float)(movingobjectposition.field_72307_f.field_72450_a - this.field_70165_t));
         this.field_70181_x = (double)((float)(movingobjectposition.field_72307_f.field_72448_b - this.field_70163_u));
         this.field_70179_y = (double)((float)(movingobjectposition.field_72307_f.field_72449_c - this.field_70161_v));
         float f2 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y);
         this.field_70165_t -= this.field_70159_w / (double)f2 * (double)0.05F;
         this.field_70163_u -= this.field_70181_x / (double)f2 * (double)0.05F;
         this.field_70161_v -= this.field_70179_y / (double)f2 * (double)0.05F;
         this.inGround = true;
         if (this.isArrow()) {
            this.func_85030_a("random.bowhit", 1.0F, 1.2F / (this.field_70146_Z.nextFloat() * 0.2F + 0.9F));
         } else {
            this.func_85030_a("random.break", 1.0F, 1.2F / (this.field_70146_Z.nextFloat() * 0.2F + 0.9F));
         }

         this.arrowShake = 7;
         if (!this.hasGravity()) {
            this.field_70180_af.func_75692_b(26, (byte)1);
         }

         if (this.inTile != 0) {
            Block.field_71973_m[this.inTile].func_71869_a(this.field_70170_p, this.xTile, this.yTile, this.zTile, this);
         }
      }

      if (this.explosive) {
         if (this.explosiveRadius == 0 && this.effect != EnumPotionType.None) {
            if (this.effect == EnumPotionType.Fire) {
               int i = movingobjectposition.field_72311_b;
               int j = movingobjectposition.field_72312_c;
               int k = movingobjectposition.field_72309_d;
               switch (movingobjectposition.field_72310_e) {
                  case 0:
                     --j;
                     break;
                  case 1:
                     ++j;
                     break;
                  case 2:
                     --k;
                     break;
                  case 3:
                     ++k;
                     break;
                  case 4:
                     --i;
                     break;
                  case 5:
                     ++i;
               }

               if (this.field_70170_p.func_72799_c(i, j, k)) {
                  this.field_70170_p.func_94575_c(i, j, k, Block.field_72067_ar.field_71990_ca);
               }
            } else {
               AxisAlignedBB axisalignedbb = this.field_70121_D.func_72314_b((double)4.0F, (double)2.0F, (double)4.0F);
               List list1 = this.field_70170_p.func_72872_a(EntityLivingBase.class, axisalignedbb);
               if (list1 != null && !list1.isEmpty()) {
                  for(EntityLivingBase entitylivingbase : list1) {
                     double d0 = this.func_70068_e(entitylivingbase);
                     if (d0 < (double)16.0F) {
                        double d1 = (double)1.0F - Math.sqrt(d0) / (double)4.0F;
                        if (entitylivingbase == movingobjectposition.field_72308_g) {
                           d1 = (double)1.0F;
                        }

                        int i = this.getPotionEffect(this.effect);
                        if (Potion.field_76425_a[i].func_76403_b()) {
                           Potion.field_76425_a[i].func_76402_a(this.getThrower(), entitylivingbase, this.amplify, d1);
                        } else {
                           int j = (int)(d1 * (double)this.duration + (double)0.5F);
                           if (j > 20) {
                              entitylivingbase.func_70690_d(new PotionEffect(i, j, this.amplify));
                           }
                        }
                     }
                  }
               }

               this.field_70170_p.func_72926_e(2002, (int)Math.round(this.field_70165_t), (int)Math.round(this.field_70163_u), (int)Math.round(this.field_70161_v), this.getPotionColor(this.effect));
            }
         } else {
            this.field_70170_p.func_72885_a((Entity)null, this.field_70165_t, this.field_70163_u, this.field_70161_v, (float)this.explosiveRadius, this.effect == EnumPotionType.Fire, this.field_70170_p.func_82736_K().func_82766_b("mobGriefing"));
            if (this.explosiveRadius != 0 && (this.isArrow() || this.sticksToWalls())) {
               this.func_70106_y();
            }
         }
      }

      if (!this.field_70170_p.field_72995_K && !this.isArrow() && !this.sticksToWalls()) {
         this.func_70106_y();
      }

   }

   public void func_70014_b(NBTTagCompound par1NBTTagCompound) {
      par1NBTTagCompound.func_74777_a("xTile", (short)this.xTile);
      par1NBTTagCompound.func_74777_a("yTile", (short)this.yTile);
      par1NBTTagCompound.func_74777_a("zTile", (short)this.zTile);
      par1NBTTagCompound.func_74774_a("inTile", (byte)this.inTile);
      par1NBTTagCompound.func_74774_a("inData", (byte)this.inData);
      par1NBTTagCompound.func_74774_a("shake", (byte)this.throwableShake);
      par1NBTTagCompound.func_74774_a("inGround", (byte)(this.inGround ? 1 : 0));
      par1NBTTagCompound.func_74774_a("isArrow", (byte)(this.isArrow() ? 1 : 0));
      par1NBTTagCompound.func_74782_a("direction", this.func_70087_a(new double[]{this.field_70159_w, this.field_70181_x, this.field_70179_y}));
      par1NBTTagCompound.func_74757_a("canBePickedUp", this.canBePickedUp);
      if ((this.throwerName == null || this.throwerName.length() == 0) && this.thrower != null && this.thrower instanceof EntityPlayer) {
         this.throwerName = this.thrower.func_70023_ak();
      }

      par1NBTTagCompound.func_74778_a("ownerName", this.throwerName == null ? "" : this.throwerName);
      if (this.getItemDisplay() != null) {
         par1NBTTagCompound.func_74766_a("Item", this.getItemDisplay().func_77955_b(new NBTTagCompound()));
      }

      par1NBTTagCompound.func_74776_a("damagev2", this.damage);
      par1NBTTagCompound.func_74768_a("punch", this.punch);
      par1NBTTagCompound.func_74768_a("size", this.field_70180_af.func_75679_c(23));
      par1NBTTagCompound.func_74768_a("velocity", this.field_70180_af.func_75679_c(25));
      par1NBTTagCompound.func_74768_a("explosiveRadius", this.explosiveRadius);
      par1NBTTagCompound.func_74768_a("effectDuration", this.duration);
      par1NBTTagCompound.func_74757_a("gravity", this.hasGravity());
      par1NBTTagCompound.func_74757_a("accelerate", this.accelerate);
      par1NBTTagCompound.func_74774_a("glows", this.field_70180_af.func_75683_a(24));
      par1NBTTagCompound.func_74757_a("explosive", this.explosive);
      par1NBTTagCompound.func_74768_a("PotionEffect", this.effect.ordinal());
      par1NBTTagCompound.func_74778_a("trail", this.field_70180_af.func_75681_e(22));
      par1NBTTagCompound.func_74774_a("Render3D", this.field_70180_af.func_75683_a(28));
      par1NBTTagCompound.func_74774_a("Spins", this.field_70180_af.func_75683_a(29));
      par1NBTTagCompound.func_74774_a("Sticks", this.field_70180_af.func_75683_a(30));
   }

   public void func_70037_a(NBTTagCompound par1NBTTagCompound) {
      this.xTile = par1NBTTagCompound.func_74765_d("xTile");
      this.yTile = par1NBTTagCompound.func_74765_d("yTile");
      this.zTile = par1NBTTagCompound.func_74765_d("zTile");
      this.inTile = par1NBTTagCompound.func_74771_c("inTile") & 255;
      this.inData = par1NBTTagCompound.func_74771_c("inData") & 255;
      this.throwableShake = par1NBTTagCompound.func_74771_c("shake") & 255;
      this.inGround = par1NBTTagCompound.func_74771_c("inGround") == 1;
      this.field_70180_af.func_75692_b(27, par1NBTTagCompound.func_74771_c("isArrow"));
      this.throwerName = par1NBTTagCompound.func_74779_i("ownerName");
      this.canBePickedUp = par1NBTTagCompound.func_74767_n("canBePickedUp");
      this.damage = par1NBTTagCompound.func_74760_g("damagev2");
      this.punch = par1NBTTagCompound.func_74762_e("punch");
      this.explosiveRadius = par1NBTTagCompound.func_74762_e("explosiveRadius");
      this.duration = par1NBTTagCompound.func_74762_e("effectDuration");
      this.accelerate = par1NBTTagCompound.func_74767_n("accelerate");
      this.explosive = par1NBTTagCompound.func_74767_n("explosive");
      this.effect = EnumPotionType.values()[par1NBTTagCompound.func_74762_e("PotionEffect") % EnumPotionType.values().length];
      this.field_70180_af.func_75692_b(22, par1NBTTagCompound.func_74779_i("trail"));
      this.field_70180_af.func_75692_b(23, par1NBTTagCompound.func_74762_e("size"));
      this.field_70180_af.func_75692_b(24, (byte)(par1NBTTagCompound.func_74767_n("glows") ? 1 : 0));
      this.field_70180_af.func_75692_b(25, par1NBTTagCompound.func_74762_e("velocity"));
      this.field_70180_af.func_75692_b(26, (byte)(par1NBTTagCompound.func_74767_n("gravity") ? 1 : 0));
      this.field_70180_af.func_75692_b(28, (byte)(par1NBTTagCompound.func_74767_n("Render3D") ? 1 : 0));
      this.field_70180_af.func_75692_b(29, (byte)(par1NBTTagCompound.func_74767_n("Spins") ? 1 : 0));
      this.field_70180_af.func_75692_b(30, (byte)(par1NBTTagCompound.func_74767_n("Sticks") ? 1 : 0));
      if (this.throwerName != null && this.throwerName.length() == 0) {
         this.throwerName = null;
      }

      if (par1NBTTagCompound.func_74764_b("direction")) {
         NBTTagList nbttaglist = par1NBTTagCompound.func_74761_m("direction");
         this.field_70159_w = ((NBTTagDouble)nbttaglist.func_74743_b(0)).field_74755_a;
         this.field_70181_x = ((NBTTagDouble)nbttaglist.func_74743_b(1)).field_74755_a;
         this.field_70179_y = ((NBTTagDouble)nbttaglist.func_74743_b(2)).field_74755_a;
      }

      NBTTagCompound var2 = par1NBTTagCompound.func_74775_l("Item");
      ItemStack item = ItemStack.func_77949_a(var2);
      if (item == null) {
         this.func_70106_y();
      } else {
         this.field_70180_af.func_75692_b(21, item);
      }

   }

   public EntityLivingBase getThrower() {
      if (this.thrower == null && this.throwerName != null && this.throwerName.length() > 0) {
         this.thrower = this.field_70170_p.func_72924_a(this.throwerName);
      }

      return this.thrower;
   }

   private int getPotionEffect(EnumPotionType p) {
      switch (p) {
         case Poison:
            return Potion.field_76436_u.field_76415_H;
         case Hunger:
            return Potion.field_76438_s.field_76415_H;
         case Weakness:
            return Potion.field_76437_t.field_76415_H;
         case Slowness:
            return Potion.field_76421_d.field_76415_H;
         case Nausea:
            return Potion.field_76431_k.field_76415_H;
         case Blindness:
            return Potion.field_76440_q.field_76415_H;
         case Wither:
            return Potion.field_82731_v.field_76415_H;
         default:
            return 0;
      }
   }

   private int getPotionColor(EnumPotionType p) {
      switch (p) {
         case Poison:
            return 32660;
         case Hunger:
            return 32660;
         case Weakness:
            return 32696;
         case Slowness:
            return 32698;
         case Nausea:
            return 32732;
         case Blindness:
            return Potion.field_76440_q.field_76415_H;
         case Wither:
            return 32732;
         default:
            return 0;
      }
   }

   public void getStatProperties(DataStats stats) {
      this.damage = (float)stats.pDamage;
      this.punch = stats.pImpact;
      this.accelerate = stats.pXlr8;
      this.explosive = stats.pExplode;
      this.explosiveRadius = stats.pArea;
      this.effect = stats.pEffect;
      this.duration = stats.pDur;
      this.amplify = stats.pEffAmp;
      this.setParticleEffect(stats.pTrail);
      this.field_70180_af.func_75692_b(23, stats.pSize);
      this.field_70180_af.func_75692_b(24, (byte)(stats.pGlows ? 1 : 0));
      this.setSpeed(stats.pSpeed);
      this.setHasGravity(stats.pPhysics);
      this.setIs3D(stats.pRender3D);
      this.setRotating(stats.pSpin);
      this.setStickInWall(stats.pStick);
   }

   public void setParticleEffect(EnumParticleType type) {
      this.field_70180_af.func_75692_b(22, type.particleName);
   }

   public void setHasGravity(boolean bo) {
      this.field_70180_af.func_75692_b(26, (byte)(bo ? 1 : 0));
   }

   public void setIs3D(boolean bo) {
      this.field_70180_af.func_75692_b(28, (byte)(bo ? 1 : 0));
   }

   public void setStickInWall(boolean bo) {
      this.field_70180_af.func_75692_b(30, (byte)(bo ? 1 : 0));
   }

   public ItemStack getItemDisplay() {
      return this.field_70180_af.func_82710_f(21);
   }

   public float func_70013_c(float par1) {
      return this.field_70180_af.func_75683_a(24) == 1 ? 1.0F : super.func_70013_c(par1);
   }

   @SideOnly(Side.CLIENT)
   public int func_70070_b(float par1) {
      return this.field_70180_af.func_75683_a(24) == 1 ? 15728880 : super.func_70070_b(par1);
   }

   public boolean hasGravity() {
      return this.field_70180_af.func_75683_a(26) == 1;
   }

   public void setSpeed(int speed) {
      this.field_70180_af.func_75692_b(25, speed);
   }

   public float getSpeed() {
      return (float)this.field_70180_af.func_75679_c(25) / 10.0F;
   }

   public boolean isArrow() {
      return this.field_70180_af.func_75683_a(27) == 1;
   }

   public void setRotating(boolean bo) {
      this.field_70180_af.func_75692_b(29, (byte)(bo ? 1 : 0));
   }

   public boolean isRotating() {
      return this.field_70180_af.func_75683_a(29) == 1;
   }

   public boolean glows() {
      return this.field_70180_af.func_75683_a(24) == 1;
   }

   public boolean is3D() {
      return this.field_70180_af.func_75683_a(28) == 1 || this.isBlock();
   }

   public boolean sticksToWalls() {
      return this.is3D() && this.field_70180_af.func_75683_a(30) == 1;
   }

   public void func_70100_b_(EntityPlayer par1EntityPlayer) {
      if (!this.field_70170_p.field_72995_K && this.canBePickedUp && this.inGround && this.arrowShake <= 0) {
         if (par1EntityPlayer.field_71071_by.func_70441_a(this.getItemDisplay())) {
            this.inGround = false;
            this.func_85030_a("random.pop", 0.2F, ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            par1EntityPlayer.func_71001_a(this, 1);
            this.func_70106_y();
         }

      }
   }

   protected boolean func_70041_e_() {
      return false;
   }
}
