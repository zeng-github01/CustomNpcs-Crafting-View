package noppes.npcs.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import noppes.npcs.CustomItems;
import noppes.npcs.entity.EntityProjectile;
import org.lwjgl.opengl.GL11;

public class ItemMachineGun extends ItemNpcInterface {
   public ItemMachineGun(int par1) {
      super(par1);
      this.func_77656_e(80);
      this.func_77637_a(CustomItems.tabWeapon);
   }

   public void func_77615_a(ItemStack stack, World par2World, EntityPlayer player, int count) {
      if (!player.field_71075_bZ.field_75098_d) {
         int ticks = this.func_77626_a(stack) - count;
         int shotsleft = stack.field_77990_d.func_74762_e("ShotsLeft") - ticks / 6;
         if (stack.field_77990_d.func_74762_e("Reloading") == 1) {
            shotsleft = ticks / 3;
            if (ticks > 24) {
               shotsleft = 8;
            }

            if (shotsleft > 1) {
               stack.field_77990_d.func_74768_a("ShotsLeft", shotsleft);
               stack.field_77990_d.func_74768_a("Reloading", 0);
            }
         } else if (shotsleft <= 0) {
            stack.field_77990_d.func_74768_a("Reloading", 1);
            stack.func_77972_a(1, player);
         } else {
            stack.field_77990_d.func_74768_a("ShotsLeft", shotsleft);
         }

      }
   }

   public void onUsingItemTick(ItemStack stack, EntityPlayer player, int count) {
      if (!player.field_70170_p.field_72995_K) {
         int ticks = this.func_77626_a(stack) - count;
         if (ticks % 6 == 0) {
            int shotsleft = stack.field_77990_d.func_74762_e("ShotsLeft") - ticks / 6;
            if (!player.field_71075_bZ.field_75098_d) {
               if (stack.field_77990_d.func_74762_e("Reloading") == 1 && player.field_71071_by.func_70450_e(CustomItems.bulletBlack.field_77779_bT)) {
                  if (ticks > 0 && ticks <= 24) {
                     player.field_70170_p.func_72956_a(player, "customnpcs:gun.ak47chamberround", 1.0F, 1.0F);
                  }

                  return;
               }

               if (shotsleft <= 0 || !player.field_71071_by.func_70450_e(CustomItems.bulletBlack.field_77779_bT)) {
                  player.field_70170_p.func_72956_a(player, "customnpcs:gun.empty", 1.0F, 1.0F);
                  return;
               }
            }

            EntityProjectile projectile = new EntityProjectile(player.field_70170_p, player, new ItemStack(CustomItems.bulletBlack, 1, 0), false);
            projectile.damage = 4.0F;
            projectile.setSpeed(40);
            projectile.shoot(2.0F);
            if (!player.field_71075_bZ.field_75098_d) {
               player.field_71071_by.func_70435_d(CustomItems.bulletBlack.field_77779_bT);
            }

            player.field_70170_p.func_72956_a(player, "customnpcs:gun.pistolshot", 0.9F, field_77697_d.nextFloat() * 0.3F + 0.8F);
            player.field_70170_p.func_72838_d(projectile);
         }
      }
   }

   public void renderSpecial() {
      GL11.glScalef(0.7F, 0.7F, 0.7F);
      GL11.glTranslatef(0.0F, 0.2F, 0.0F);
   }

   public ItemStack func_77659_a(ItemStack stack, World world, EntityPlayer player) {
      if (stack.field_77990_d == null) {
         stack.field_77990_d = new NBTTagCompound();
      }

      if (!player.field_71075_bZ.field_75098_d && !player.field_71071_by.func_70450_e(CustomItems.bulletBlack.field_77779_bT)) {
         stack.field_77990_d.func_74768_a("Reloading", 1);
      }

      player.func_71008_a(stack, this.func_77626_a(stack));
      return stack;
   }

   public int func_77626_a(ItemStack par1ItemStack) {
      return 72000;
   }

   public EnumAction func_77661_b(ItemStack stack) {
      return stack.field_77990_d != null && stack.field_77990_d.func_74762_e("Reloading") != 0 ? EnumAction.block : EnumAction.bow;
   }
}
