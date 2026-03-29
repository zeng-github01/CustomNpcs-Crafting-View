package noppes.npcs.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.constants.EnumGuiType;

public class ItemNpcMovingPath extends Item {
   public ItemNpcMovingPath(int i) {
      super(i - 26700 + CustomNpcs.ItemStartId);
      this.field_77777_bU = 1;
      this.func_77637_a(CustomItems.tab);
   }

   public ItemStack func_77659_a(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      EntityNPCInterface npc = this.getNpc(par1ItemStack, par2World);
      if (npc != null) {
         NoppesUtilServer.sendOpenGui(par3EntityPlayer, EnumGuiType.MovingPath, npc);
      }

      return par1ItemStack;
   }

   public boolean func_77648_a(ItemStack par1ItemStack, EntityPlayer player, World par3World, int x, int y, int z, int par7, float par8, float par9, float par10) {
      EntityNPCInterface npc = this.getNpc(par1ItemStack, par3World);
      if (npc == null) {
         return true;
      } else {
         List<int[]> list = npc.ai.getMovingPath();
         list.add(new int[]{x, y, z});
         player.func_71035_c("Added point x:" + x + " y:" + y + " z:" + z + " to npc " + npc.func_70023_ak());
         return true;
      }
   }

   private EntityNPCInterface getNpc(ItemStack item, World world) {
      if (!world.field_72995_K && item.field_77990_d != null) {
         Entity entity = world.func_73045_a(item.field_77990_d.func_74762_e("NPCID"));
         return entity != null && entity instanceof EntityNPCInterface ? (EntityNPCInterface)entity : null;
      } else {
         return null;
      }
   }

   public int func_82790_a(ItemStack par1ItemStack, int par2) {
      return 9127187;
   }

   public boolean func_77623_v() {
      return true;
   }

   @SideOnly(Side.CLIENT)
   public void func_94581_a(IconRegister par1IconRegister) {
      this.field_77791_bV = Item.field_77716_q.func_77617_a(0);
   }

   public Item func_77655_b(String name) {
      GameRegistry.registerItem(this, name);
      return super.func_77655_b(name);
   }
}
