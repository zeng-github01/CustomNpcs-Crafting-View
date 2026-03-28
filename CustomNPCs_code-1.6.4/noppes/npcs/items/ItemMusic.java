package noppes.npcs.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import noppes.npcs.CustomItems;

public class ItemMusic extends ItemNpcInterface {
   public ItemMusic(int par1) {
      super(par1);
      this.func_77637_a(CustomItems.tabMisc);
   }

   public ItemStack func_77659_a(ItemStack par1ItemStack, World par2World, EntityPlayer player) {
      if (par2World.field_72995_K) {
         return par1ItemStack;
      } else {
         int note = par2World.field_73012_v.nextInt(24);
         float var7 = (float)Math.pow((double)2.0F, (double)(note - 12) / (double)12.0F);
         String var8 = "harp";
         par2World.func_72908_a(player.field_70165_t, player.field_70163_u, player.field_70161_v, "note." + var8, 3.0F, var7);
         par2World.func_72869_a("note", player.field_70163_u, player.field_70163_u + 1.2, player.field_70163_u, (double)note / (double)24.0F, (double)0.0F, (double)0.0F);
         return par1ItemStack;
      }
   }
}
