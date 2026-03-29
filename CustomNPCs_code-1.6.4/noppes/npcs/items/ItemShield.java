package noppes.npcs.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import noppes.npcs.CustomItems;
import org.lwjgl.opengl.GL11;

public class ItemShield extends ItemNpcInterface {
   public ItemShield(int par1, EnumNpcToolMaterial material) {
      super(par1);
      this.func_77656_e(material.getMaxUses());
      this.func_77637_a(CustomItems.tabWeapon);
   }

   public void renderSpecial() {
      GL11.glScalef(0.6F, 0.6F, 0.6F);
      GL11.glTranslatef(0.0F, 0.0F, -0.26F);
      GL11.glRotatef(-6.0F, 0.0F, 1.0F, 0.0F);
   }

   public EnumAction func_77661_b(ItemStack par1ItemStack) {
      return EnumAction.block;
   }

   public ItemStack func_77659_a(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      par3EntityPlayer.func_71008_a(par1ItemStack, this.func_77626_a(par1ItemStack));
      return par1ItemStack;
   }

   public int func_77626_a(ItemStack par1ItemStack) {
      return 72000;
   }
}
