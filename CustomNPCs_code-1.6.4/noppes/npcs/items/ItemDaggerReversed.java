package noppes.npcs.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;
import org.lwjgl.opengl.GL11;

public class ItemDaggerReversed extends ItemDagger {
   private ItemDagger dagger;

   public ItemDaggerReversed(int par1, ItemDagger dagger, EnumToolMaterial tool) {
      super(par1, tool);
      this.dagger = dagger;
   }

   public void renderSpecial() {
      GL11.glScalef(0.6F, 0.6F, 0.6F);
      GL11.glTranslatef(-0.26F, 0.5F, 0.26F);
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 1.0F);
   }

   public boolean func_77629_n_() {
      return true;
   }

   public void func_94581_a(IconRegister par1IconRegister) {
      this.field_77791_bV = this.dagger.func_77617_a(0);
   }
}
