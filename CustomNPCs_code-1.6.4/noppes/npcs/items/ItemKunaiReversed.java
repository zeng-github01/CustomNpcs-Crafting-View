package noppes.npcs.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;
import noppes.npcs.CustomItems;
import org.lwjgl.opengl.GL11;

public class ItemKunaiReversed extends ItemKunai {
   public ItemKunaiReversed(int par1, EnumToolMaterial tool) {
      super(par1, tool);
   }

   public void renderSpecial() {
      GL11.glScalef(0.4F, 0.4F, 0.4F);
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 1.0F);
      GL11.glTranslatef(0.4F, -0.9F, -0.4F);
   }

   public void func_94581_a(IconRegister par1IconRegister) {
      this.field_77791_bV = CustomItems.kunai.func_77617_a(0);
   }
}
