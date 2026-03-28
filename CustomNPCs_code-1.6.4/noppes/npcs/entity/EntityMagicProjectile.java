package noppes.npcs.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMagicProjectile extends EntityProjectile {
   private EntityPlayer player;
   private ItemStack equiped;

   public EntityMagicProjectile(World par1World, EntityPlayer player, ItemStack item, boolean isNPC) {
      super(par1World, player, item, isNPC);
      this.player = player;
      this.equiped = player.field_71071_by.func_70448_g();
   }

   public void func_70071_h_() {
      if (this.player.field_71071_by.func_70448_g() != this.equiped) {
         this.func_70106_y();
      }

      super.func_70071_h_();
   }
}
