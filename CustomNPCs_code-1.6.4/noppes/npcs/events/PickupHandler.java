package noppes.npcs.events;

import cpw.mods.fml.common.IPickupNotifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import noppes.npcs.constants.EnumQuestType;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.PlayerQuestData;

public class PickupHandler implements IPickupNotifier {
   public void notifyPickup(EntityItem item, EntityPlayer player) {
      if (!player.field_70170_p.field_72995_K) {
         PlayerQuestData playerdata = PlayerDataController.instance.getPlayerData(player).questData;
         playerdata.checkQuestCompletion(player, EnumQuestType.Item);
      }
   }
}
