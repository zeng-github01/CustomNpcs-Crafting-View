package noppes.npcs.items;

import net.minecraft.item.EnumToolMaterial;
import org.lwjgl.opengl.GL11;

public class ItemGlaive extends ItemNpcWeaponInterface {
   public ItemGlaive(int par1, EnumToolMaterial tool) {
      super(par1, tool);
   }

   public void renderSpecial() {
      GL11.glTranslatef(0.16F, -0.34F, -0.14F);
   }
}
