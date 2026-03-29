package noppes.npcs.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.entity.EntityNPCHumanMale;
import noppes.npcs.permissions.CustomNpcsPermissions;

public class ItemNpcWand extends Item {
   public ItemNpcWand(int i) {
      super(i - 26700 + CustomNpcs.ItemStartId);
      this.field_77777_bU = 1;
      this.func_77637_a(CustomItems.tab);
   }

   public ItemStack func_77659_a(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      if (!par2World.field_72995_K) {
         return par1ItemStack;
      } else {
         CustomNpcs.proxy.openGui((EntityNPCInterface)null, EnumGuiType.NpcRemote);
         return par1ItemStack;
      }
   }

   public boolean func_77648_a(ItemStack par1ItemStack, EntityPlayer player, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
      if (par3World.field_72995_K) {
         return true;
      } else {
         if (CustomNpcs.OpsOnly && !MinecraftServer.func_71276_C().func_71203_ab().func_72376_i().contains(player.field_71092_bJ.toLowerCase())) {
            MinecraftServer.func_71276_C().func_71236_h(player.field_71092_bJ + ": tried to use custom npcs without being an op");
         } else if (CustomNpcsPermissions.Instance.hasPermission(player.field_71092_bJ, "customnpcs.npc.create")) {
            EntityNPCHumanMale npc = (EntityNPCHumanMale)EntityList.func_75620_a("npchumanmale", par3World);
            npc.startPos = new int[]{par4, par5, par6};
            npc.func_70012_b((double)((float)par4 + 0.5F), npc.getStartYPos(), (double)((float)par6 + 0.5F), player.field_70177_z, player.field_70125_A);
            par3World.func_72838_d(npc);
            npc.func_70606_j(npc.func_110138_aP());
            NoppesUtilServer.sendOpenGui(player, EnumGuiType.MainMenuDisplay, npc);
         }

         return true;
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
      this.field_77791_bV = Item.field_77689_P.func_77617_a(0);
   }

   public Item func_77655_b(String name) {
      GameRegistry.registerItem(this, name);
      return super.func_77655_b(name);
   }
}
