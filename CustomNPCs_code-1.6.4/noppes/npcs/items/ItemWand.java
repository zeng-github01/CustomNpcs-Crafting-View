package noppes.npcs.items;

import net.minecraft.item.ItemStack;
import noppes.npcs.CustomItems;
import org.lwjgl.opengl.GL11;

public class ItemWand extends ItemNpcInterface {
   public ItemWand(int par1) {
      super(par1);
      this.func_77637_a(CustomItems.tabMisc);
   }

   public boolean func_77636_d(ItemStack par1ItemStack) {
      return true;
   }

   public void renderSpecial() {
      GL11.glScalef(0.54F, 0.54F, 0.54F);
      GL11.glTranslatef(0.0F, 0.4F, -0.04F);
   }
}
