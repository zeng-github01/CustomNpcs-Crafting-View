package noppes.npcs.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.constants.EnumGuiType;

public class ItemNpcSpawner extends Item {
   public ItemNpcSpawner(int i) {
      super(i - 26700 + CustomNpcs.ItemStartId);
      this.field_77777_bU = 1;
      this.func_77637_a(CustomItems.tab);
   }

   public boolean func_77648_a(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
      if (par3World.field_72995_K) {
         CustomNpcs.proxy.openGui(par4, par5, par6, EnumGuiType.MobSpawner, par2EntityPlayer);
      }

      return true;
   }

   public int func_82790_a(ItemStack par1ItemStack, int par2) {
      return 9127187;
   }

   public boolean func_77623_v() {
      return true;
   }

   @SideOnly(Side.CLIENT)
   public void func_94581_a(IconRegister par1IconRegister) {
      this.field_77791_bV = Item.field_77695_f.func_77617_a(0);
   }

   public Item func_77655_b(String name) {
      GameRegistry.registerItem(this, name);
      return super.func_77655_b(name);
   }
}
