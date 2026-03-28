package noppes.npcs.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import org.lwjgl.opengl.GL11;

public class ItemNpcInterface extends Item implements ItemRenderInterface {
   public EnumNpcToolMaterial toolMaterial;

   public ItemNpcInterface(int par1) {
      super(par1 - 26700 + CustomNpcs.ItemStartId);
      this.func_77637_a(CustomItems.tab);
      CustomNpcs.proxy.registerItem(this.field_77779_bT);
   }

   public void renderSpecial() {
      GL11.glScalef(0.66F, 0.66F, 0.66F);
      GL11.glTranslatef(0.0F, 0.3F, 0.0F);
   }

   public int func_77619_b() {
      return super.func_77619_b();
   }

   public Item func_77655_b(String name) {
      GameRegistry.registerItem(this, name);
      return super.func_77655_b(name);
   }

   public boolean func_77644_a(ItemStack par1ItemStack, EntityLivingBase par2EntityLiving, EntityLivingBase par3EntityLiving) {
      if (par2EntityLiving.func_110143_aJ() <= 0.0F) {
         return false;
      } else {
         par1ItemStack.func_77972_a(1, par3EntityLiving);
         return true;
      }
   }
}
