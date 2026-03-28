package noppes.npcs.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;

public class ItemGunChainsaw extends ItemNpcWeaponInterface {
   public ItemGunChainsaw(int par1, EnumToolMaterial tool) {
      super(par1, tool);
   }

   public boolean func_77644_a(ItemStack par1ItemStack, EntityLivingBase par2EntityLiving, EntityLivingBase par3EntityLiving) {
      if (par2EntityLiving.func_110143_aJ() <= 0.0F) {
         return false;
      } else {
         double x = par2EntityLiving.field_70165_t;
         double y = par2EntityLiving.field_70163_u + (double)(par2EntityLiving.field_70131_O / 2.0F);
         double z = par2EntityLiving.field_70161_v;
         par3EntityLiving.field_70170_p.func_72908_a(x, y, z, "random.explode", 0.8F, (1.0F + (par3EntityLiving.field_70170_p.field_73012_v.nextFloat() - par3EntityLiving.field_70170_p.field_73012_v.nextFloat()) * 0.2F) * 0.7F);
         par3EntityLiving.field_70170_p.func_72869_a("largeexplode", x, y, z, (double)0.0F, (double)0.0F, (double)0.0F);
         return super.func_77644_a(par1ItemStack, par2EntityLiving, par3EntityLiving);
      }
   }
}
