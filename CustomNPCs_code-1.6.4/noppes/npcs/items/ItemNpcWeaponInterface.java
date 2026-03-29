package noppes.npcs.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import org.lwjgl.opengl.GL11;

public class ItemNpcWeaponInterface extends ItemSword implements ItemRenderInterface {
   public ItemNpcWeaponInterface(int par1, EnumToolMaterial material) {
      super(par1 - 26700 + CustomNpcs.ItemStartId, material);
      this.func_77637_a(CustomItems.tab);
      CustomNpcs.proxy.registerItem(this.field_77779_bT);
      this.func_77637_a(CustomItems.tabWeapon);
   }

   public void renderSpecial() {
      GL11.glScalef(0.66F, 0.66F, 0.66F);
      GL11.glTranslatef(0.0F, 0.3F, 0.0F);
   }

   public Item func_77655_b(String name) {
      GameRegistry.registerItem(this, name);
      return super.func_77655_b(name);
   }
}
