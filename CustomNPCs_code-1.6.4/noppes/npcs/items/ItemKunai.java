package noppes.npcs.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import noppes.npcs.entity.EntityProjectile;
import org.lwjgl.opengl.GL11;

public class ItemKunai extends ItemNpcWeaponInterface {
   public ItemKunai(int par1, EnumToolMaterial tool) {
      super(par1, tool);
   }

   public void func_77615_a(ItemStack par1ItemStack, World worldObj, EntityPlayer player, int par4) {
      if (worldObj.field_72995_K) {
         player.func_71038_i();
      } else {
         EntityProjectile projectile = new EntityProjectile(worldObj, player, par1ItemStack, false);
         projectile.damage = this.func_82803_g();
         projectile.destroyedOnEntityHit = false;
         projectile.canBePickedUp = !player.field_71075_bZ.field_75098_d;
         projectile.setIs3D(true);
         projectile.setStickInWall(true);
         projectile.setHasGravity(true);
         projectile.setSpeed(12);
         projectile.shoot(1.0F);
         if (!player.field_71075_bZ.field_75098_d) {
            par1ItemStack.func_77972_a(1, player);
            if (par1ItemStack.field_77994_a == 0) {
               return;
            }

            player.field_71071_by.field_70462_a[player.field_71071_by.field_70461_c] = null;
         }

         worldObj.func_72956_a(player, "customnpcs:misc.swosh", 1.0F, 1.0F);
         worldObj.func_72838_d(projectile);
      }
   }

   public ItemStack func_77659_a(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      par3EntityPlayer.func_71008_a(par1ItemStack, this.func_77626_a(par1ItemStack));
      return par1ItemStack;
   }

   public int func_77626_a(ItemStack par1ItemStack) {
      return 72000;
   }

   public void renderSpecial() {
      GL11.glScalef(0.4F, 0.4F, 0.4F);
      GL11.glTranslatef(-0.2F, 0.3F, 0.2F);
   }

   public boolean func_77629_n_() {
      return true;
   }
}
