package noppes.npcs.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class ItemViolin extends ItemMusic {
   public ItemViolin(int par1) {
      super(par1);
   }

   public void renderSpecial() {
      GL11.glScalef(0.66F, 0.66F, 0.66F);
      GL11.glRotatef(-80.0F, 1.0F, 0.0F, 1.0F);
      GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(-0.2F, -0.9F, -0.7F);
   }

   public EnumAction func_77661_b(ItemStack par1ItemStack) {
      return EnumAction.bow;
   }

   public ItemStack func_77659_a(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      par3EntityPlayer.func_71008_a(par1ItemStack, this.func_77626_a(par1ItemStack));
      return super.func_77659_a(par1ItemStack, par2World, par3EntityPlayer);
   }

   public int func_77626_a(ItemStack par1ItemStack) {
      return 72000;
   }
}
