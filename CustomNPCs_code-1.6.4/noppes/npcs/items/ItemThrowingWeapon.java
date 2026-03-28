package noppes.npcs.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import noppes.npcs.CustomItems;
import noppes.npcs.entity.EntityProjectile;

public class ItemThrowingWeapon extends ItemNpcInterface {
   private boolean rotating = false;
   private int damage = 2;
   private boolean dropItem = false;

   public ItemThrowingWeapon(int par1) {
      super(par1);
      this.func_77637_a(CustomItems.tabWeapon);
   }

   public void func_77615_a(ItemStack par1ItemStack, World worldObj, EntityPlayer player, int par4) {
      if (worldObj.field_72995_K) {
         player.func_71038_i();
      } else {
         EntityProjectile projectile = new EntityProjectile(worldObj, player, new ItemStack(par1ItemStack.func_77973_b(), 1, par1ItemStack.func_77960_j()), false);
         projectile.damage = (float)this.damage;
         projectile.canBePickedUp = !player.field_71075_bZ.field_75098_d && this.dropItem;
         projectile.setRotating(this.rotating);
         projectile.setIs3D(true);
         projectile.setStickInWall(true);
         projectile.setHasGravity(true);
         projectile.setSpeed(12);
         if (!player.field_71075_bZ.field_75098_d) {
            player.field_71071_by.func_70435_d(this.field_77779_bT);
         }

         projectile.shoot(1.0F);
         worldObj.func_72956_a(player, "customnpcs:misc.swosh", 1.0F, 1.0F);
         worldObj.func_72838_d(projectile);
      }
   }

   public ItemThrowingWeapon setRotating() {
      this.rotating = true;
      return this;
   }

   public ItemThrowingWeapon setDamage(int damage) {
      this.damage = damage;
      return this;
   }

   public ItemThrowingWeapon setDropItem() {
      this.dropItem = true;
      return this;
   }

   public ItemStack func_77659_a(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      par3EntityPlayer.func_71008_a(par1ItemStack, this.func_77626_a(par1ItemStack));
      return par1ItemStack;
   }

   public int func_77626_a(ItemStack par1ItemStack) {
      return 72000;
   }
}
