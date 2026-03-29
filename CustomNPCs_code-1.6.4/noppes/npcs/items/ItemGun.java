package noppes.npcs.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import noppes.npcs.CustomItems;
import noppes.npcs.entity.EntityProjectile;
import org.lwjgl.opengl.GL11;

public class ItemGun extends ItemNpcInterface {
   private EnumNpcToolMaterial material;

   public ItemGun(int par1, EnumNpcToolMaterial material) {
      super(par1);
      this.field_77777_bU = 1;
      this.material = material;
      this.func_77656_e(material.getMaxUses());
      this.func_77637_a(CustomItems.tabWeapon);
   }

   public void func_77615_a(ItemStack par1ItemStack, World worldObj, EntityPlayer player, int par4) {
      if (!worldObj.field_72995_K) {
         if (!this.hasBullet(player)) {
            worldObj.func_72956_a(player, "customnpcs:gun.empty", 1.0F, 1.0F);
         } else {
            int ticks = this.func_77626_a(par1ItemStack) - par4;
            if (ticks >= 10) {
               par1ItemStack.func_77972_a(1, player);
               ItemBullet bullet = (ItemBullet)this.getBullet(player);
               int damage = (bullet.getBulletDamage() + this.material.getDamageVsEntity() + 1) / 2 + 5;
               EntityProjectile projectile = new EntityProjectile(worldObj, player, new ItemStack(this.getBullet(player)), false);
               projectile.damage = (float)damage;
               projectile.setSpeed(40);
               projectile.shoot((float)(this.material.getDamageVsEntity() + 1));
               if (!player.field_71075_bZ.field_75098_d) {
                  player.field_71071_by.func_70435_d(this.getBullet(player).field_77779_bT);
               }

               worldObj.func_72956_a(player, "customnpcs:gun.pistolshot", 1.0F, field_77697_d.nextFloat() * 0.3F + 0.8F);
               worldObj.func_72838_d(projectile);
            }
         }
      }
   }

   public void onUsingItemTick(ItemStack stack, EntityPlayer player, int count) {
      int ticks = this.func_77626_a(stack) - count;
      if (ticks == 8 && !player.field_70170_p.field_72995_K) {
         player.field_70170_p.func_72956_a(player, "customnpcs:gun.pistoltrigger", 1.0F, 1.0F / (player.field_70170_p.field_73012_v.nextFloat() * 0.4F + 0.8F));
      }

   }

   public void renderSpecial() {
      GL11.glScalef(0.7F, 0.7F, 0.7F);
      GL11.glTranslatef(0.0F, 0.3F, 0.0F);
   }

   public ItemStack func_77659_a(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      par3EntityPlayer.func_71008_a(par1ItemStack, this.func_77626_a(par1ItemStack));
      return par1ItemStack;
   }

   public int func_77626_a(ItemStack par1ItemStack) {
      return 72000;
   }

   private boolean hasBullet(EntityPlayer par3EntityPlayer) {
      Item bullet = this.getBullet(par3EntityPlayer);
      return bullet != null && bullet.field_77779_bT >= 0;
   }

   private Item getBullet(EntityPlayer par3EntityPlayer) {
      switch (this.material) {
         case EMERALD:
            if (par3EntityPlayer.field_71071_by.func_70450_e(CustomItems.bulletEmerald.field_77779_bT)) {
               return CustomItems.bulletEmerald;
            }
         case DIA:
            if (par3EntityPlayer.field_71071_by.func_70450_e(CustomItems.bulletDiamond.field_77779_bT)) {
               return CustomItems.bulletDiamond;
            }
         case IRON:
            if (par3EntityPlayer.field_71071_by.func_70450_e(CustomItems.bulletIron.field_77779_bT)) {
               return CustomItems.bulletIron;
            }
         case BRONZE:
            if (par3EntityPlayer.field_71071_by.func_70450_e(CustomItems.bulletBronze.field_77779_bT)) {
               return CustomItems.bulletBronze;
            }
         case GOLD:
            if (par3EntityPlayer.field_71071_by.func_70450_e(CustomItems.bulletGold.field_77779_bT)) {
               return CustomItems.bulletGold;
            }
         case STONE:
            if (par3EntityPlayer.field_71071_by.func_70450_e(CustomItems.bulletStone.field_77779_bT)) {
               return CustomItems.bulletStone;
            }
         case WOOD:
            if (par3EntityPlayer.field_71071_by.func_70450_e(CustomItems.bulletWood.field_77779_bT)) {
               return CustomItems.bulletWood;
            }
         default:
            return !par3EntityPlayer.field_71071_by.func_70450_e(CustomItems.bulletBlack.field_77779_bT) && !par3EntityPlayer.field_71075_bZ.field_75098_d ? null : CustomItems.bulletBlack;
      }
   }

   public EnumAction func_77661_b(ItemStack par1ItemStack) {
      return EnumAction.bow;
   }
}
