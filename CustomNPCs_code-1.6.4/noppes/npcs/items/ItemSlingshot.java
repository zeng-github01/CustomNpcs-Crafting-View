package noppes.npcs.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import noppes.npcs.CustomItems;
import noppes.npcs.constants.EnumParticleType;
import noppes.npcs.entity.EntityProjectile;
import org.lwjgl.opengl.GL11;

public class ItemSlingshot extends ItemNpcInterface {
   public ItemSlingshot(int par1) {
      super(par1);
      this.field_77777_bU = 1;
      this.func_77656_e(384);
      this.func_77637_a(CustomItems.tabWeapon);
   }

   public void func_77615_a(ItemStack par1ItemStack, World worldObj, EntityPlayer player, int par4) {
      if (!worldObj.field_72995_K) {
         int ticks = this.func_77626_a(par1ItemStack) - par4;
         if (ticks >= 6) {
            if (player.field_71075_bZ.field_75098_d || player.field_71071_by.func_70435_d(Block.field_71978_w.field_71990_ca)) {
               par1ItemStack.func_77972_a(1, player);
               EntityProjectile projectile = new EntityProjectile(worldObj, player, new ItemStack(Block.field_71978_w), false);
               projectile.damage = 3.0F;
               projectile.punch = 1;
               projectile.setRotating(true);
               if (ticks > 24) {
                  projectile.setParticleEffect(EnumParticleType.Crit);
                  projectile.punch = 2;
               }

               projectile.setHasGravity(true);
               projectile.setSpeed(14);
               projectile.shoot(1.0F);
               worldObj.func_72956_a(player, "random.bow", 1.0F, field_77697_d.nextFloat() * 0.3F + 0.8F);
               worldObj.func_72838_d(projectile);
            }
         }
      }
   }

   public void renderSpecial() {
      GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      GL11.glTranslatef(0.0F, 0.3F, 0.0F);
   }

   public ItemStack func_77659_a(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      par3EntityPlayer.func_71008_a(par1ItemStack, this.func_77626_a(par1ItemStack));
      return par1ItemStack;
   }

   public int func_77626_a(ItemStack par1ItemStack) {
      return 72000;
   }

   public EnumAction func_77661_b(ItemStack par1ItemStack) {
      return EnumAction.bow;
   }
}
