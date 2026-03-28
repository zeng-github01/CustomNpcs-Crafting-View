package noppes.npcs.items;

import org.lwjgl.opengl.GL11;

public class ItemBanjo extends ItemMusic {
   public ItemBanjo(int par1) {
      super(par1);
   }

   public void renderSpecial() {
      GL11.glScalef(0.85F, 0.85F, 0.85F);
      GL11.glTranslatef(0.0F, 0.4F, 0.0F);
      GL11.glRotatef(-90.0F, -1.0F, 0.0F, 1.0F);
   }
}
