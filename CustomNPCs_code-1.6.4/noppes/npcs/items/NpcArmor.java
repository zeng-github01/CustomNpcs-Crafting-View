package noppes.npcs.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;

public class NpcArmor extends ItemArmor {
   private String texture;

   public NpcArmor(int par1, EnumArmorMaterial par2EnumArmorMaterial, int par4, String texture) {
      super(par1 - 26700 + CustomNpcs.ItemStartId, par2EnumArmorMaterial, 0, par4);
      this.texture = texture;
      this.func_77637_a(CustomItems.tabArmor);
   }

   public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer) {
      return this.field_77881_a == 2 ? "customnpcs:textures/armor/" + this.texture + "_2.png" : "customnpcs:textures/armor/" + this.texture + "_1.png";
   }

   public Item func_77655_b(String name) {
      GameRegistry.registerItem(this, name);
      return super.func_77655_b(name);
   }
}
