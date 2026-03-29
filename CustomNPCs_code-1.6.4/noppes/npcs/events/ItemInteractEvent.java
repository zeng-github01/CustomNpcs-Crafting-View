package noppes.npcs.events;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.client.controllers.CloneController;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.permissions.CustomNpcsPermissions;

public class ItemInteractEvent {
   public static EntityVillager Merchant;

   @ForgeSubscribe
   public void invoke(EntityInteractEvent event) {
      ItemStack item = event.entityPlayer.func_71045_bC();
      if (item != null) {
         boolean isRemote = event.entityPlayer.field_70170_p.field_72995_K;
         boolean npcInteracted = event.target instanceof EntityNPCInterface;
         if (item.field_77993_c == CustomItems.wand.field_77779_bT && npcInteracted && !isRemote) {
            if (!CustomNpcsPermissions.Instance.hasPermission(event.entityPlayer.field_71092_bJ, "customnpcs.npc.edit")) {
               return;
            }

            event.setCanceled(true);
            NoppesUtilServer.sendOpenGui(event.entityPlayer, EnumGuiType.MainMenuDisplay, (EntityNPCInterface)event.target);
         } else if (item.field_77993_c == CustomItems.cloner.field_77779_bT && isRemote) {
            CloneController.toClone = event.target;
            CustomNpcs.proxy.openGui(0, 0, 0, EnumGuiType.MobSpawnerAdd, event.entityPlayer);
         } else if (item.field_77993_c == CustomItems.wand.field_77779_bT && event.target instanceof EntityVillager) {
            event.setCanceled(true);
            Merchant = (EntityVillager)event.target;
            if (!isRemote) {
               EntityPlayerMP player = (EntityPlayerMP)event.entityPlayer;
               player.openGui(CustomNpcs.instance, EnumGuiType.MerchantAdd.ordinal(), player.field_70170_p, 0, 0, 0);
               MerchantRecipeList merchantrecipelist = Merchant.func_70934_b(player);
               if (merchantrecipelist != null) {
                  NoppesUtilServer.sendData(player, EnumPacketType.MerchantAdd, merchantrecipelist);
               }
            }
         }

      }
   }
}
