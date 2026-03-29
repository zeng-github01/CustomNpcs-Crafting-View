package noppes.npcs.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.entity.EntityMagicProjectile;
import noppes.npcs.entity.EntityProjectile;

public class ItemStaff extends ItemNpcInterface {
   private EnumNpcToolMaterial material;

   public ItemStaff(int par1, EnumNpcToolMaterial material) {
      super(par1);
      this.material = material;
      this.func_77637_a(CustomItems.tabWeapon);
   }

   public void renderSpecial() {
   }

   public void func_77615_a(ItemStack stack, World worldObj, EntityPlayer player, int par4) {
      if (!worldObj.field_72995_K) {
         if (stack.field_77990_d != null) {
            Entity entity = ((WorldServer)player.field_70170_p).func_73045_a(stack.field_77990_d.func_74762_e("MagicProjectile"));
            if (entity != null && entity instanceof EntityProjectile) {
               EntityProjectile item = (EntityProjectile)entity;
               item.field_70126_B = item.field_70177_z = player.field_70177_z;
               item.field_70127_C = item.field_70125_A = player.field_70125_A;
               item.shoot(2.0F);
               player.field_70170_p.func_72956_a(player, "customnpcs:magic.shot", 1.0F, 1.0F);
            }
         }
      }
   }

   public void onUsingItemTick(ItemStack stack, EntityPlayer player, int count) {
      int tick = this.func_77626_a(stack) - count;
      if (player.field_70170_p.field_72995_K) {
         this.spawnParticle(stack, player);
      } else {
         int chargeTime = 20 + this.material.getHarvestLevel() * 8;
         if (tick == chargeTime) {
            if (!player.field_71075_bZ.field_75098_d) {
               if (!player.field_71071_by.func_70450_e(CustomItems.mana.field_77779_bT)) {
                  return;
               }

               player.field_71071_by.func_70435_d(CustomItems.mana.field_77779_bT);
            }

            player.field_70170_p.func_72956_a(player, "customnpcs:magic.charge", 1.0F, 1.0F);
            if (stack.field_77990_d == null) {
               stack.field_77990_d = new NBTTagCompound();
            }

            int damage = 6 + this.material.getDamageVsEntity() + player.field_70170_p.field_73012_v.nextInt(4);
            EntityProjectile projectile = new EntityMagicProjectile(player.field_70170_p, player, this.getProjectile(stack), false);
            projectile.damage = (float)damage;
            projectile.setSpeed(25);
            double dx = (double)(-MathHelper.func_76126_a((float)((double)(player.field_70177_z / 180.0F) * Math.PI)) * MathHelper.func_76134_b((float)((double)(player.field_70125_A / 180.0F) * Math.PI)));
            double dz = (double)(MathHelper.func_76134_b((float)((double)(player.field_70177_z / 180.0F) * Math.PI)) * MathHelper.func_76134_b((float)((double)(player.field_70125_A / 180.0F) * Math.PI)));
            projectile.func_70107_b(player.field_70165_t + dx * 0.8, player.field_70163_u + (double)1.5F - (double)(player.field_70125_A / 40.0F), player.field_70161_v + dz * 0.8);
            player.field_70170_p.func_72838_d(projectile);
            stack.field_77990_d.func_74768_a("MagicProjectile", projectile.field_70157_k);
         }

         if (tick > chargeTime && stack.field_77990_d != null) {
            Entity entity = ((WorldServer)player.field_70170_p).func_73045_a(stack.field_77990_d.func_74762_e("MagicProjectile"));
            if (entity == null || !(entity instanceof EntityProjectile)) {
               return;
            }

            EntityProjectile item = (EntityProjectile)entity;
            item.ticksInAir = 0;
            double dx = (double)(-MathHelper.func_76126_a((float)((double)(player.field_70177_z / 180.0F) * Math.PI)) * MathHelper.func_76134_b((float)((double)(player.field_70125_A / 180.0F) * Math.PI)));
            double dz = (double)(MathHelper.func_76134_b((float)((double)(player.field_70177_z / 180.0F) * Math.PI)) * MathHelper.func_76134_b((float)((double)(player.field_70125_A / 180.0F) * Math.PI)));
            item.func_70107_b(player.field_70165_t + dx * 0.8, player.field_70163_u + (double)1.5F - (double)(player.field_70125_A / 40.0F), player.field_70161_v + dz * 0.8);
         }

      }
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

   public ItemStack getProjectile(ItemStack stack) {
      if (stack.func_77973_b() == CustomItems.staffWood) {
         return new ItemStack(CustomItems.spellNature);
      } else if (stack.func_77973_b() == CustomItems.staffStone) {
         return new ItemStack(CustomItems.spellDark);
      } else if (stack.func_77973_b() == CustomItems.staffIron) {
         return new ItemStack(CustomItems.spellHoly);
      } else if (stack.func_77973_b() == CustomItems.staffBronze) {
         return new ItemStack(CustomItems.spellLightning);
      } else if (stack.func_77973_b() == CustomItems.staffGold) {
         return new ItemStack(CustomItems.spellFire);
      } else if (stack.func_77973_b() == CustomItems.staffDiamond) {
         return new ItemStack(CustomItems.spellIce);
      } else {
         return stack.func_77973_b() == CustomItems.staffEmerald ? new ItemStack(CustomItems.spellArcane) : new ItemStack(CustomItems.orb, 1, stack.func_77960_j());
      }
   }

   public void spawnParticle(ItemStack stack, EntityPlayer player) {
      if (stack.func_77973_b() == CustomItems.staffWood) {
         CustomNpcs.proxy.spawnParticle(player, "Spell", 5, 2);
         CustomNpcs.proxy.spawnParticle(player, "Spell", 12, 2);
      }

      if (stack.func_77973_b() == CustomItems.staffStone) {
         CustomNpcs.proxy.spawnParticle(player, "Spell", 5649239, 2);
         CustomNpcs.proxy.spawnParticle(player, "Spell", 4400964, 2);
      }

      if (stack.func_77973_b() == CustomItems.staffBronze) {
         CustomNpcs.proxy.spawnParticle(player, "Spell", 8648694, 2);
         CustomNpcs.proxy.spawnParticle(player, "Spell", 6091007, 2);
      }

      if (stack.func_77973_b() == CustomItems.staffIron) {
         CustomNpcs.proxy.spawnParticle(player, "Spell", 16580553, 2);
         CustomNpcs.proxy.spawnParticle(player, "Spell", 15728535, 2);
      }

      if (stack.func_77973_b() == CustomItems.staffGold) {
         CustomNpcs.proxy.spawnParticle(player, "Spell", 1, 2);
         CustomNpcs.proxy.spawnParticle(player, "Spell", 14, 2);
      }

      if (stack.func_77973_b() == CustomItems.staffDiamond) {
         CustomNpcs.proxy.spawnParticle(player, "Spell", 9756653, 2);
         CustomNpcs.proxy.spawnParticle(player, "Spell", 4503295, 2);
      }

      if (stack.func_77973_b() == CustomItems.staffEmerald) {
         CustomNpcs.proxy.spawnParticle(player, "Spell", 16761831, 2);
         CustomNpcs.proxy.spawnParticle(player, "Spell", 16487167, 2);
      }

   }
}
