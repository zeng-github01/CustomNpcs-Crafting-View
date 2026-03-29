package noppes.npcs.items;

import net.minecraft.item.EnumToolMaterial;
import org.lwjgl.opengl.GL11;

public class ItemDagger extends ItemNpcWeaponInterface {
   public ItemDagger(int par1, EnumToolMaterial tool) {
      super(par1, tool);
   }

   public void renderSpecial() {
      GL11.glScalef(0.6F, 0.6F, 0.6F);
      GL11.glTranslatef(-0.05F, 0.32F, 0.05F);
   }

   public boolean func_77629_n_() {
      return true;
   }
}
