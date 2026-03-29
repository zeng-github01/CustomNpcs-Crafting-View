package noppes.npcs.items;

import org.lwjgl.opengl.GL11;

public class ItemThrowingShuriken extends ItemThrowingWeapon {
   public ItemThrowingShuriken(int par1) {
      super(par1);
   }

   public void renderSpecial() {
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      GL11.glTranslatef(-0.25F, 0.2F, 0.3F);
   }

   public boolean func_77629_n_() {
      return true;
   }
}
