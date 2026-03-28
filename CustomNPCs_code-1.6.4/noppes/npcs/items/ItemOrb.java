package noppes.npcs.items;

import java.awt.Color;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.ItemStack;

public class ItemOrb extends ItemNpcInterface {
   public ItemOrb(int par1) {
      super(par1);
      this.func_77627_a(true);
   }

   public int func_82790_a(ItemStack par1ItemStack, int par2) {
      float[] color = EntitySheep.field_70898_d[par1ItemStack.func_77960_j()];
      return (new Color(color[0], color[1], color[2])).getRGB();
   }

   public boolean func_77623_v() {
      return true;
   }

   public void func_77633_a(int par1, CreativeTabs par2CreativeTabs, List par3List) {
      for(int var4 = 0; var4 < 16; ++var4) {
         par3List.add(new ItemStack(par1, 1, var4));
      }

   }
}
